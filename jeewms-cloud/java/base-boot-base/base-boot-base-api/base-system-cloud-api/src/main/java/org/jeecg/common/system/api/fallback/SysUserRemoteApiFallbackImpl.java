package org.jeecg.common.system.api.fallback;

import com.base.common.system.vo.SysUserCacheInfo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.SysUserRemoteApi;
import org.jeecg.common.system.system.entity.SysPermission;
import org.jeecg.common.system.system.entity.SysPermissionDataRule;
import org.jeecg.common.system.system.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author scott
 * @date 2020/05/22
 */
@Slf4j
@Component
public class SysUserRemoteApiFallbackImpl implements SysUserRemoteApi {
    @Setter
    private Throwable cause;


    @Override
    public Result<Set<String>> getUserRolesSet(String username) {
        log.error("获取用户角色集合 {}", username, cause);
        return null;
    }

    @Override
    public Result<Set<String>> getUserPermissionsSet(String username) {
        log.error("获取用户权限集合 {}", username, cause);
        return null;
    }

    @Override
    public List<SysPermission> queryComponentPermission(String component) {
        return null;
    }

    @Override
    public List<SysPermission> queryRequestPermission(String method, String path) {
        return null;
    }

    @Override
    public List<SysPermissionDataRule> queryUserDataRule(String username, String permissionId) {
        return null;
    }

    @Override
    public SysUserCacheInfo getCacheUser(String username) {
        return null;
    }

    @Override
    public List<SysUser> getUserListByRoleCode(String roleCode) {
        return null;
    }

    @Override
    public List<SysUser> userList(List<String> userNameList) {
        return null;
    }
}
