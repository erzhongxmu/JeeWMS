package org.jeecgframework.core.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.SysContextSqlConvert;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * 权限拦截器
 *
 * @author admin
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
    private SystemService systemService;
    private List<String> excludeUrls;
    private List<String> excludeContainUrls;

    public List<String> getExcludeContainUrls() {
        return excludeContainUrls;
    }

    public void setExcludeContainUrls(List<String> excludeContainUrls) {
        this.excludeContainUrls = excludeContainUrls;
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 在controller后拦截
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在controller前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String requestPath = ResourceUtil.getRequestPath(request); // 用户访问的资源地址
        //logger.info("-----authInterceptor----requestPath------" + requestPath);
        // 步骤一： 判断是否是排除拦截请求，直接返回 TRUE
        if (requestPath.matches("^rest/[a-zA-Z0-9_/]+$")) {
            return true;
        }
        if (excludeUrls.contains(requestPath)) {
            return true;
        } else if (moHuContain(excludeContainUrls, requestPath)) {
            return true;
        } else {
            // 步骤二： 权限控制，优先重组请求 URL（考虑 online 请求前缀一致问题）
            String clickFunctionId = request.getParameter("clickFunctionId");
            Client client = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId());
            TSUser currLoginUser = client != null ? client.getUser() : null;
            if (client != null && currLoginUser != null) {
                if ("cgAutoListController.do?datagrid".equals(requestPath)) {
                    // 对添加的参数进行验证和过滤，防止恶意代码注入
                    requestPath += "&configId=" + validateAndSanitize(request.getParameter("configId"));
                }
                if ("cgAutoListController.do?list".equals(requestPath)) {
                    // 对添加的参数进行验证和过滤，防止恶意代码注入
                    requestPath += "&id=" + validateAndSanitize(request.getParameter("id"));
                }

                if (requestPath.endsWith("?olstylecode=")) {
                    requestPath = requestPath.replace("?olstylecode=", "");
                }

                // 步骤三： 根据重组请求 URL,进行权限授权判断
                if ((!hasMenuAuth(requestPath, clickFunctionId, currLoginUser)) && !"admin".equals(currLoginUser.getUserName())) {
                    response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/loginController.do?noAuth");
                    return false;
                }

                // 解决 rest 风格下权限失效问题
                String functionId = "";
                String uri = request.getRequestURI().substring(request.getContextPath().length() + 1);
                String realRequestPath = null;
                if (uri.endsWith(".do") || uri.endsWith(".action")) {
                    realRequestPath = requestPath;
                } else {
                    realRequestPath = uri;
                }

                if (realRequestPath.indexOf("autoFormController/af/") > -1 && realRequestPath.indexOf("?") != -1) {
                    realRequestPath = realRequestPath.substring(0, realRequestPath.indexOf("?"));
                }

                List<TSFunction> functions = systemService.findByProperty(TSFunction.class, "functionUrl", realRequestPath);
                if (functions.size() > 0) {
                    functionId = functions.get(0).getId();
                }

                // Step.1 第一部分处理页面表单和列表的页面控件权限（页面表单字段 + 页面按钮等控件）
                if (!oConvertUtils.isEmpty(functionId)) {
                    // 获取菜单对应的页面控制权限（包括表单字段和操作按钮）
                    Set<String> operationCodes = systemService.getOperationCodesByUserIdAndFunctionId(currLoginUser.getId(), functionId);
                    request.setAttribute(Globals.OPERATIONCODES, operationCodes);
                }
                if (!oConvertUtils.isEmpty(functionId)) {

                    List<TSOperation> newall = new ArrayList<TSOperation>();
                    String hasOperSql = "SELECT operation FROM t_s_role_function fun, t_s_role_user role WHERE  " +
                            "fun.functionid='" + functionId + "' AND fun.operation is not null  AND fun.roleid=role.roleid AND role.userid='" + currLoginUser.getId() + "' ";
                    List<String> hasOperList = this.systemService.findListbySql(hasOperSql);
                    for (String operationIds : hasOperList) {
                        for (String operationId : operationIds.split(",")) {
                            operationId = operationId.replaceAll(" ", "");
                            TSOperation operation = systemService.get(TSOperation.class, operationId);
                            Boolean bol = operation != null && operation.getOperationcode() != null &&
                                    (operation.getOperationcode().startsWith("#") || operation.getOperationcode().startsWith("."));
                            if (bol) {
                                newall.add(operation);
                            }
                        }
                    }
                    request.setAttribute(Globals.NOAUTO_OPERATIONCODES, newall);

                    // Step.2 第二部分处理列表数据级权限 (菜单数据规则集合)
                    List<TSDataRule> MENU_DATA_AUTHOR_RULES = new ArrayList<TSDataRule>();
                    String MENU_DATA_AUTHOR_RULE_SQL = "";

                    // 数据权限规则的查询
                    Set<String> dataruleCodes = systemService.getOperationCodesByUserIdAndDataId(currLoginUser.getId(), functionId);
                    request.setAttribute("dataRulecodes", dataruleCodes);
                    for (String dataRuleId : dataruleCodes) {
                        TSDataRule dataRule = systemService.getEntity(TSDataRule.class, dataRuleId);
                        MENU_DATA_AUTHOR_RULES.add(dataRule);
                        MENU_DATA_AUTHOR_RULE_SQL += SysContextSqlConvert.setSqlModel(dataRule);
                    }
                    JeecgDataAutorUtils.installDataSearchConditon(request, MENU_DATA_AUTHOR_RULES); // 菜单数据规则集合
                    JeecgDataAutorUtils.installDataSearchConditon(request, MENU_DATA_AUTHOR_RULE_SQL); // 菜单数据规则 sql

                }
                return true;
            } else {
                forward(request, response);
                return false;
            }

        }
    }


    private boolean moHuContain(List<String> list, String key) {
        for (String str : list) {
            if (key.contains(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对输入参数进行验证和过滤
     *
     * @param param 输入参数
     * @return 过滤和验证后的参数
     */
    private String validateAndSanitize(String param) {
        if (param == null) {
            return "";
        }
        // 移除可能导致 SQL 注入或其他安全问题的字符
        return param.replaceAll("[;&'\"\\(\\)=]+", "");
    }


    /**
     * 判断用户是否有菜单访问权限
     *
     * @param requestPath
     * @param clickFunctionId
     * @param currLoginUser
     * @return
     */
    private boolean hasMenuAuth(String requestPath, String clickFunctionId, TSUser currLoginUser) {
        String userid = currLoginUser.getId();

        //step.1 先判断请求是否配置菜单，没有配置菜单默认不作权限控制
        String hasMenuSql = "select count(*) from t_s_function where functiontype = 0 and functionurl = '" + requestPath + "'";
        Long hasMenuCount = systemService.getCountForJdbc(hasMenuSql);
        if (hasMenuCount <= 0) {
            return true;
        }

        //step.2 判断菜单是否有角色权限
        Long authSize = Long.valueOf(0);
        String sql = "SELECT count(*) FROM t_s_function f,t_s_role_function  rf,t_s_role_user ru " +
                " WHERE f.id=rf.functionid AND rf.roleid=ru.roleid AND " +
                "ru.userid='" + userid + "' AND f.functionurl = '" + requestPath + "'";
        authSize = this.systemService.getCountForJdbc(sql);
        if (authSize <= 0) {
            //step.3 判断菜单是否有组织机构角色权限
            String orgId = currLoginUser.getCurrentDepart().getId();
            Long orgAuthSize = Long.valueOf(0);
            String functionOfOrgSql = "SELECT count(*) from t_s_function f, t_s_role_function rf, t_s_role_org ro  " +
                    "WHERE f.ID=rf.functionid AND rf.roleid=ro.role_id " +
                    "AND ro.org_id='" + orgId + "' AND f.functionurl = '" + requestPath + "'";
            orgAuthSize = this.systemService.getCountForJdbc(functionOfOrgSql);
            return orgAuthSize > 0;
        } else {
            return true;
        }

    }


    /**
     * 转发
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "forword")
    public ModelAndView forword(HttpServletRequest request) {
        return new ModelAndView(new RedirectView("loginController.do?login"));
    }

    private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //超时，未登陆页面跳转
        //response.sendRedirect(request.getServletContext().getContextPath()+"/loginController.do?login");

        response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/webpage/login/timeout.jsp");

        //request.getRequestDispatcher("loginController.do?login").forward(request, response);

    }

}
