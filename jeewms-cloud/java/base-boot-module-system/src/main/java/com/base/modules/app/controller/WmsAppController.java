package com.base.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.modules.app.service.IWmsAppFunctionService;
import com.base.modules.app.service.IWmsAppRoleService;
import com.base.modules.app.service.IWmsAppUserService;
import com.base.modules.mesapp.entity.WmsAppFunction;
import com.base.modules.mesapp.entity.WmsAppRole;
import com.base.modules.mesapp.entity.WmsAppUser;
import com.base.modules.system.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.SecurityUtils;
//import com.base.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SqlInjectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags="WMS-APP模块")
@RestController
@RequestMapping("/wmsapi/wmsapp")
@Slf4j
public class WmsAppController {

    @Autowired
    private IWmsAppUserService mesAppUserService;

    @Autowired
    private IWmsAppRoleService mesAppRoleService;

    @Autowired
    private IWmsAppFunctionService mesAppFunctionService;

    @Autowired
    private ISysDictService sysDictService;

//    @Autowired
//    ISysBaseAPI sysBaseAPI;
//
//    @GetMapping(value = "/sendRemindMessage")
//    public String sendRemindMessage(@RequestParam(name="userName",required=true) String userName,
//                                    @RequestParam(name="commandCode",required=true) String commandCode,
//                                    @RequestParam(name="productName",required=true) String productName) {
//        sysBaseAPI.sendSysAnnouncement("admin",userName,"制令单号为:"+commandCode+" 的上料消息提醒",""+productName+" 已经到达上料提醒比例！请注意及时更换！");
//        return "消息发送成功！";
//    }
//
//    @GetMapping(value = "/sendMaterLackRemind")
//    public String sendMaterLackRemind(@RequestParam(name="userName",required=true) String userName,
//                                    @RequestParam(name="title",required=true) String title,
//                                    @RequestParam(name="content",required=true) String content) {
//        sysBaseAPI.sendSysAnnouncement("admin",userName,title,content);
//        return "消息发送成功！";
//    }
//
//    @GetMapping(value = "/sendMessage")
//    public String sendMessage(@RequestParam(name="userName",required=true) String userName,
//                              @RequestParam(name="mName",required=true) String mName) {
//        sysBaseAPI.sendSysAnnouncement("admin",userName,""+mName+" 的库存到达临界点",""+mName+" 已经到达库存临界点！请及时购买！");
//        return "消息发送成功！";
//    }
//
//    @GetMapping(value = "/sendHeatMessage")
//    public String sendHeatMessage(@RequestParam(name="userName",required=true) String userName,
//                                  @RequestParam(name="orderName",required=true) String orderName,
//                                  @RequestParam(name="mName",required=true) String mName) {
//        sysBaseAPI.sendSysAnnouncement("admin",userName,""+orderName+" 的回温提醒",""+orderName+" 需要用到 "+mName+"！请及时取出回温！");
//        return "消息发送成功！";
//    }
//
//    @GetMapping(value = "/sendCheckMessage")
//    public String sendCheckMessage(@RequestParam(name="userName",required=true) String userName,
//                                   @RequestParam(name="mName",required=true) String mName,
//                                   @RequestParam(name="receiveNum",required = true) String receiveNum,
//                                   @RequestParam(name="unit",required = true) String unit) {
//        sysBaseAPI.sendSysAnnouncement("admin",userName,""+mName+" 的收货质检提醒",""+mName+"已经收到货物"+receiveNum+""+unit+"！请及时安排人员进行质检！");
//        return "消息发送成功！";
//    }



    /**
     * 通过id查询
     *
     * @return
     */
    @AutoLog(value = "主数据—APP功能模块查询")
    @ApiOperation(value="主数据—APP功能模块查询", notes="主数据—APP功能模块查询")
    @GetMapping(value = "/appfunctionList")
    public Result<?> appfunctionList() {
        // 获取登录用户信息
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser==null){
            return Result.error("未获取到当前登录用户的信息！");
        }
        //获取用户名(唯一)
        String username = sysUser.getUsername();
        System.err.println(sysUser.getUsername());
        //根据用户名，获取APP用户
        QueryWrapper<WmsAppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appuser_code",username);
        WmsAppUser mesAppUser = mesAppUserService.getOne(queryWrapper);
        if(mesAppUser==null){
            return Result.error("未找到APP用户信息！");
        }
        //获取用户角色Id
        String roleId =mesAppUser.getApproleId();
        System.err.println(roleId);
        //根据用户角色Id，获取APP角色集合
        QueryWrapper<WmsAppRole> wrapper = new QueryWrapper<>();
        wrapper.in("id",roleId.split(","));
        List<WmsAppRole> mesAppRole = mesAppRoleService.list(wrapper);
        if(mesAppRole==null){
            return Result.error("未找到APP角色信息！");
        }
        String funidstr = "";
        for (WmsAppRole role : mesAppRole) {
            //拼接获取的APP模块id
            funidstr = funidstr + ","+role.getAppmodelId();
            System.err.println(funidstr);
        }
        //根据APP模块id，获取APP功能模块集合
        QueryWrapper<WmsAppFunction> funcwrapper = new QueryWrapper<>();
        funcwrapper.lambda().in(WmsAppFunction::getId,funidstr.split(",")).eq(WmsAppFunction::getIfBind,"0").orderByAsc(WmsAppFunction::getAppmodelSort);
        List<WmsAppFunction> mesAppFunctions = mesAppFunctionService.list(funcwrapper);
        if(mesAppFunctions==null){
            return Result.error("未找到APP功能模块信息");
        }
        return Result.ok(mesAppFunctions);
    }

    /**
     * 获取单据编号
     *
     * @return
     */
    @AutoLog(value = "APP功能模块—获取单据编号")
    @ApiOperation(value="APP功能模块—获取单据编号", notes="APP功能模块—获取单据编号")
    @GetMapping(value = "/getdocketcode")
    public String getdocketcode(@RequestParam(name="docketype",required=false) String docketype) {
        String prefix ="MES";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        int random = RandomUtils.nextInt(90) + 10;
        String value = prefix + format.format(new Date()) + random;
        return value;
    }

    /**
     * 获取字典数据
     * @param dictCode 字典code
     * @param dictCode 表名,文本字段,code字段  | 举例：sys_user,realname,id
     * @return
     */
    @AutoLog(value = "APP功能模块—获取字典数据")
    @ApiOperation(value="APP功能模块—获取字典数据", notes="APP功能模块—获取字典数据")
    @RequestMapping(value = "/getDictItems/{dictCode}", method = RequestMethod.GET)
    public Result<List<DictModel>> getDictItems(@PathVariable String dictCode, @RequestParam(value = "sign",required = false) String sign, HttpServletRequest request) {
        log.info(" dictCode : "+ dictCode);
        Result<List<DictModel>> result = new Result<List<DictModel>>();
        List<DictModel> ls = null;
        try {
            if(dictCode.indexOf(",")!=-1) {
                //关联表字典（举例：sys_user,realname,id）
                String[] params = dictCode.split(",");

                if(params.length<3) {
                    result.error500("字典Code格式不正确！");
                    return result;
                }
                //SQL注入校验（只限制非法串改数据库）
                final String[] sqlInjCheck = {params[0],params[1],params[2]};
                SqlInjectionUtil.filterContent(sqlInjCheck);

                if(params.length==4) {
                    //SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
                    SqlInjectionUtil.specialFilterContent(params[3]);
                    ls = sysDictService.queryTableDictItemsByCodeAndFilter(params[0],params[1],params[2],params[3]);
                }else if (params.length==3) {
                    ls = sysDictService.queryTableDictItemsByCode(params[0],params[1],params[2]);
                }else{
                    result.error500("字典Code格式不正确！");
                    return result;
                }
            }else {
                //字典表
                ls = sysDictService.queryDictItemsByCode(dictCode);
            }

            result.setSuccess(true);
            result.setResult(ls);
            log.info(result.toString());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
            return result;
        }

        return result;
    }

//    @AutoLog(value = "APP功能模块—文件预览")
//    @ApiOperation(value="APP功能模块—文件预览", notes="APP功能模块—文件预览")
//    @GetMapping(value = "/preview")
//    public Result<?> preview(@RequestParam(name="fileUrl",required=true) String fileUrl) {
//       String filePreview = PREVIEW_URL+fileUrl;
//       return Result.ok(filePreview);
//    }

}
