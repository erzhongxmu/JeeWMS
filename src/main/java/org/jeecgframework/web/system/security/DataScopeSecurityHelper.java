package org.jeecgframework.web.system.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**
 * 用户、角色、功能和数据规则的授权上界检查。
 *
 * @author security
 */
public class DataScopeSecurityHelper {

    public static final String USER_MANAGE_URL = "userController.do?user";

    private static final String SQL_USER_HAS_ROLE =
            "select count(1) from t_s_role_user where userid=? and roleid=?";
    private static final String SQL_USER_HAS_ORG_ROLE =
            "select count(1) from t_s_role_org ro, t_s_user_org uo where ro.org_id=uo.org_id and uo.user_id=? and ro.role_id=?";
    private static final String SQL_USER_HAS_FUNCTION =
            "select count(1) from t_s_role_function rf, t_s_role_user ru where rf.roleid=ru.roleid and ru.userid=? and rf.functionid=?";
    private static final String SQL_USER_HAS_ORG_FUNCTION =
            "select count(1) from t_s_role_function rf, t_s_role_org ro, t_s_user_org uo where rf.roleid=ro.role_id and ro.org_id=uo.org_id and uo.user_id=? and rf.functionid=?";
    private static final String SQL_USER_HAS_FUNCTION_URL =
            "select count(1) from t_s_function f, t_s_role_function rf, t_s_role_user ru where f.id=rf.functionid and rf.roleid=ru.roleid and ru.userid=? and f.functionurl=?";
    private static final String SQL_USER_HAS_ORG_FUNCTION_URL =
            "select count(1) from t_s_function f, t_s_role_function rf, t_s_role_org ro, t_s_user_org uo where f.id=rf.functionid and rf.roleid=ro.role_id and ro.org_id=uo.org_id and uo.user_id=? and f.functionurl=?";
    private static final String SQL_USER_VISIBLE_BY_ORG =
            "select count(1) from t_s_user_org uo, t_s_depart d where uo.org_id=d.id and uo.user_id=? and d.org_code like ?";
    private static final String SQL_USER_DATA_RULES =
            "select rf.datarule from t_s_role_function rf, t_s_role_user ru where rf.roleid=ru.roleid and ru.userid=? and rf.functionid=? and rf.datarule is not null "
                    + "union select rf.datarule from t_s_role_function rf, t_s_role_org ro, t_s_user_org uo where rf.roleid=ro.role_id and ro.org_id=uo.org_id and uo.user_id=? and rf.functionid=? and rf.datarule is not null";
    private static final String HQL_VISIBLE_USERS_BY_ORG =
            "select distinct u from TSUser u, TSUserOrg uo, TSDepart d where u.id=uo.tsUser.id and uo.tsDepart.id=d.id and d.orgCode like ?";

    private final CommonService commonService;

    public DataScopeSecurityHelper(CommonService commonService) {
        this.commonService = commonService;
    }

    public TSUser getCurrentUser() {
        return ResourceUtil.getSessionUserName();
    }

    public boolean isAdmin(TSUser user) {
        return user != null && "admin".equals(user.getUserName());
    }

    public List<String> parseIds(String ids) {
        List<String> idList = new ArrayList<String>();
        if (StringUtil.isEmpty(ids)) {
            return idList;
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            String trimmedId = id.trim();
            if (StringUtil.isNotEmpty(trimmedId)) {
                idList.add(trimmedId);
            }
        }
        return idList;
    }

    public boolean canAccessUser(TSUser currentUser, String targetUserId) {
        if (isAdmin(currentUser)) {
            return StringUtil.isNotEmpty(targetUserId) && commonService.get(TSUser.class, targetUserId) != null;
        }
        if (currentUser == null) {
            return false;
        }
        if (StringUtil.isEmpty(targetUserId) || commonService.get(TSUser.class, targetUserId) == null) {
            return false;
        }
        if (targetUserId.equals(currentUser.getId())) {
            return true;
        }
        TSDepart currentDepart = getCurrentDepart(currentUser);
        if (currentDepart == null || StringUtil.isEmpty(currentDepart.getOrgCode())) {
            return false;
        }
        return hasRows(SQL_USER_VISIBLE_BY_ORG, targetUserId, currentDepart.getOrgCode() + "%");
    }

    public boolean canAssignDepart(TSUser currentUser, String departId) {
        if (isAdmin(currentUser)) {
            return StringUtil.isNotEmpty(departId) && commonService.get(TSDepart.class, departId) != null;
        }
        if (currentUser == null || StringUtil.isEmpty(departId)) {
            return false;
        }
        TSDepart targetDepart = commonService.get(TSDepart.class, departId);
        TSDepart currentDepart = getCurrentDepart(currentUser);
        return targetDepart != null
                && currentDepart != null
                && StringUtil.isNotEmpty(currentDepart.getOrgCode())
                && StringUtil.isNotEmpty(targetDepart.getOrgCode())
                && targetDepart.getOrgCode().startsWith(currentDepart.getOrgCode());
    }

    public boolean canManageRole(TSUser currentUser, String roleId) {
        if (isAdmin(currentUser)) {
            return StringUtil.isNotEmpty(roleId) && commonService.get(TSRole.class, roleId) != null;
        }
        return currentUser != null && roleExists(roleId) && currentUserHasRole(currentUser, roleId);
    }

    public boolean canAssignRole(TSUser currentUser, String roleId) {
        if (!canManageRole(currentUser, roleId)) {
            return false;
        }
        return isAdmin(currentUser) || roleAuthoritiesWithinCurrentUser(currentUser, roleId);
    }

    public boolean currentUserHasFunction(TSUser currentUser, String functionId) {
        if (isAdmin(currentUser)) {
            return StringUtil.isNotEmpty(functionId) && commonService.get(TSFunction.class, functionId) != null;
        }
        if (currentUser == null || StringUtil.isEmpty(functionId) || commonService.get(TSFunction.class, functionId) == null) {
            return false;
        }
        return hasRows(SQL_USER_HAS_FUNCTION, currentUser.getId(), functionId)
                || hasRows(SQL_USER_HAS_ORG_FUNCTION, currentUser.getId(), functionId);
    }

    public boolean hasFunctionAuth(TSUser currentUser, String functionUrl) {
        if (isAdmin(currentUser)) {
            return true;
        }
        if (currentUser == null || StringUtil.isEmpty(functionUrl)) {
            return false;
        }
        return hasRows(SQL_USER_HAS_FUNCTION_URL, currentUser.getId(), functionUrl)
                || hasRows(SQL_USER_HAS_ORG_FUNCTION_URL, currentUser.getId(), functionUrl);
    }

    public boolean canGrantDataRules(TSUser currentUser, String functionId, List<String> dataRuleIds) {
        if (isAdmin(currentUser)) {
            return dataRulesBelongToFunction(functionId, dataRuleIds);
        }
        if (!currentUserHasFunction(currentUser, functionId)) {
            return false;
        }
        if (dataRuleIds == null || dataRuleIds.isEmpty()) {
            return true;
        }
        Set<String> currentRuleIds = getDataRuleIds(currentUser, functionId);
        for (String dataRuleId : dataRuleIds) {
            if (!dataRuleBelongsToFunction(dataRuleId, functionId)) {
                return false;
            }
            if (currentRuleIds == null || !currentRuleIds.contains(dataRuleId)) {
                return false;
            }
        }
        return true;
    }

    public boolean dataRulesWithinCurrentUser(TSUser currentUser, String functionId, String dataRuleIds) {
        return canGrantDataRules(currentUser, functionId, parseIds(dataRuleIds));
    }

    public List<TSUser> findVisibleUsers(TSUser currentUser) {
        if (isAdmin(currentUser)) {
            return commonService.getList(TSUser.class);
        }
        if (currentUser == null) {
            return new ArrayList<TSUser>();
        }
        TSDepart currentDepart = getCurrentDepart(currentUser);
        if (currentDepart == null || StringUtil.isEmpty(currentDepart.getOrgCode())) {
            return new ArrayList<TSUser>();
        }
        return commonService.findHql(HQL_VISIBLE_USERS_BY_ORG, currentDepart.getOrgCode() + "%");
    }

    public TSDepart getCurrentDepart(TSUser currentUser) {
        if (currentUser == null || currentUser.getCurrentDepart() == null) {
            return null;
        }
        TSDepart currentDepart = currentUser.getCurrentDepart();
        if (StringUtil.isNotEmpty(currentDepart.getOrgCode())) {
            return currentDepart;
        }
        if (StringUtil.isEmpty(currentDepart.getId())) {
            return null;
        }
        return commonService.get(TSDepart.class, currentDepart.getId());
    }

    private boolean roleExists(String roleId) {
        return StringUtil.isNotEmpty(roleId) && commonService.get(TSRole.class, roleId) != null;
    }

    private boolean currentUserHasRole(TSUser currentUser, String roleId) {
        return hasRows(SQL_USER_HAS_ROLE, currentUser.getId(), roleId)
                || hasRows(SQL_USER_HAS_ORG_ROLE, currentUser.getId(), roleId);
    }

    private boolean roleAuthoritiesWithinCurrentUser(TSUser currentUser, String roleId) {
        List<TSRoleFunction> roleFunctions = commonService.findByProperty(TSRoleFunction.class, "TSRole.id", roleId);
        for (TSRoleFunction roleFunction : roleFunctions) {
            TSFunction function = roleFunction.getTSFunction();
            if (function == null || !currentUserHasFunction(currentUser, function.getId())) {
                return false;
            }
            if (!dataRulesWithinCurrentUser(currentUser, function.getId(), roleFunction.getDataRule())) {
                return false;
            }
        }
        return true;
    }

    private boolean dataRulesBelongToFunction(String functionId, List<String> dataRuleIds) {
        if (dataRuleIds == null || dataRuleIds.isEmpty()) {
            return true;
        }
        for (String dataRuleId : dataRuleIds) {
            if (!dataRuleBelongsToFunction(dataRuleId, functionId)) {
                return false;
            }
        }
        return true;
    }

    private boolean dataRuleBelongsToFunction(String dataRuleId, String functionId) {
        if (StringUtil.isEmpty(dataRuleId) || StringUtil.isEmpty(functionId)) {
            return false;
        }
        TSDataRule dataRule = commonService.get(TSDataRule.class, dataRuleId);
        return dataRule != null
                && dataRule.getTSFunction() != null
                && functionId.equals(dataRule.getTSFunction().getId());
    }

    private Set<String> getDataRuleIds(TSUser currentUser, String functionId) {
        Set<String> dataRuleIds = new HashSet<String>();
        List<Map<String, Object>> rows = commonService.findForJdbc(
                SQL_USER_DATA_RULES,
                currentUser.getId(), functionId, currentUser.getId(), functionId);
        for (Map<String, Object> row : rows) {
            Object dataRule = row.get("datarule");
            if (dataRule == null) {
                dataRule = row.get("DATARULE");
            }
            if (dataRule != null) {
                dataRuleIds.addAll(parseIds(String.valueOf(dataRule)));
            }
        }
        return dataRuleIds;
    }

    private boolean hasRows(String sql, Object... params) {
        Long count = commonService.getCountForJdbcParam(sql, params);
        return count != null && count > 0;
    }
}
