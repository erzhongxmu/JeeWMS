package com.base.modules.approle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.system.vo.LoginUser;
import com.base.modules.approle.entity.MesAppFunction;
import com.base.modules.approle.entity.MesAppRole;
import com.base.modules.approle.entity.MesAppUser;
import com.base.modules.approle.service.IMesAppFunctionService;
import com.base.modules.approle.service.IMesAppRoleService;
import com.base.modules.approle.service.IMesAppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags="MES-APP模块")
@RestController
@RequestMapping("/jeewms/mesapp")
@Slf4j
public class MesAppController {

    @Autowired
    private IMesAppUserService mesAppUserService;

    @Autowired
    private IMesAppRoleService mesAppRoleService;

    @Autowired
    private IMesAppFunctionService mesAppFunctionService;



    /**
     * 通过id查询
     *
     * @return
     */
    @AutoLog(value = "主数据—APP功能模块查询")
    @ApiOperation(value="主数据—APP功能模块查询", notes="主数据—APP功能模块查询")
    @GetMapping(value = "/appfunctionList")
    public Result<?> appfunctionList(MesAppFunction mesAppFunction,
                                     HttpServletRequest req) {
        // 获取登录用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser==null){
            return Result.error("未获取到当前登录用户的信息！");
        }
        //获取用户名(唯一)
        String username = sysUser.getUsername();
        System.err.println(sysUser.getUsername());
        //根据用户名，获取APP用户
        QueryWrapper<MesAppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("appuser_code",username);
        MesAppUser mesAppUser = mesAppUserService.getOne(queryWrapper);
        if(mesAppUser==null){
            return Result.error("未找到APP用户信息！");
        }
        //获取用户角色Id
        String roleId =mesAppUser.getApproleId();
        System.err.println(roleId);
        //根据用户角色Id，获取APP角色集合
        QueryWrapper<MesAppRole> wrapper = new QueryWrapper<>();
        wrapper.in("id",roleId.split(","));
        List<MesAppRole> mesAppRole = mesAppRoleService.list(wrapper);
        if(mesAppRole==null){
            return Result.error("未找到APP角色信息！");
        }
        String funidstr = "";
        for (MesAppRole role : mesAppRole) {
            //拼接获取的APP模块id
            funidstr = funidstr + ","+role.getAppmodelId();
            System.err.println(funidstr);
        }
        //根据APP模块id，获取APP功能模块集合
        QueryWrapper<MesAppFunction> funcwrapper = QueryGenerator.initQueryWrapper(mesAppFunction, req.getParameterMap());
        funcwrapper.in("id",funidstr.split(","));
        funcwrapper.eq("if_bind","0");
//        funcwrapper.eq("if_bind","0");
        funcwrapper.orderByAsc("appmodel_sort");
        List<MesAppFunction> mesAppFunctions = mesAppFunctionService.list(funcwrapper);
        if(mesAppFunctions==null){
            return Result.error("未找到APP功能模块信息");
        }
        return Result.ok(mesAppFunctions);
    }


}
