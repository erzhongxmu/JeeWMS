package com.base.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.aspect.annotation.AutoLog;
import com.base.common.system.base.controller.BaseController;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.modules.system.entity.BaseScheduleInfo;
import com.base.modules.system.service.IBaseScheduleInfoService;
import com.base.modules.system.service.ISysUserService;
import com.base.modules.system.vo.SysUserDepVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static com.base.modules.system.util.DateUtil.getDaysOfMonth;
/**
* @Description: base_schedule_info
* @Author: base-boot
* @Date:   2022-11-19
* @Version: V1.0
*/
@Api(tags="base_schedule_info")
@RestController
@RequestMapping("/sys/baseScheduleInfo")
@Slf4j
public class BaseScheduleInfoController extends BaseController<BaseScheduleInfo, IBaseScheduleInfoService> {
   @Autowired
   private IBaseScheduleInfoService baseScheduleInfoService;
    @Autowired
    private ISysUserService userService;
   /**
    * 分页列表查询
    *
    * @param baseScheduleInfo
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "base_schedule_info-分页列表查询")
   @ApiOperation(value="base_schedule_info-分页列表查询", notes="base_schedule_info-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList2(BaseScheduleInfo baseScheduleInfo,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<BaseScheduleInfo> queryWrapper = QueryGenerator.initQueryWrapper(baseScheduleInfo, req.getParameterMap());
       Page<BaseScheduleInfo> page = new Page<BaseScheduleInfo>(pageNo, pageSize);
       IPage<BaseScheduleInfo> pageList = baseScheduleInfoService.page(page, queryWrapper);
       return Result.OK(pageList);
   }
    /**
     * 回显排班日历
     *
     * @param baseScheduleInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "回显排班日历-回显排班日历")
    @ApiOperation(value="回显排班日历-回显排班日历", notes="回显排班日历-回显排班日历")
    @GetMapping(value = "/echoList")
    public Result<?> echoList(BaseScheduleInfo baseScheduleInfo,
                              @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                              @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                              HttpServletRequest req) {
        if (StringUtils.isBlank(baseScheduleInfo.getPlanDate())){
            baseScheduleInfo.setPlanDate(new SimpleDateFormat("yyyyMM").format(new Date()));
        }
        List<SysUserDepVo> users = userService.getDepNamesByUser(baseScheduleInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        int Simple = 0;
        try {
            Simple = getDaysOfMonth(sdf.parse(baseScheduleInfo.getPlanDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (SysUserDepVo user : users) {
            List<BaseScheduleInfo> list2 = new ArrayList<>();
            for (int i = 0; i < Simple; i++) {
                int indx = i + 1;
                String str2 = "";
                if(indx < 10){
                    str2 = "0"+ indx;
                }else {
                    str2 = indx + "";
                }
                String str = baseScheduleInfo.getPlanDate() + "-" + str2;
                LambdaQueryWrapper<BaseScheduleInfo> qwSch = new LambdaQueryWrapper<>();
                qwSch.eq(BaseScheduleInfo::getPlanDate,str);
                qwSch.eq(BaseScheduleInfo::getUserNo,user.getUserId());
                BaseScheduleInfo one = baseScheduleInfoService.getOne(qwSch,false);
                if(one == null){
                    BaseScheduleInfo baseScheduleInfo1 = new BaseScheduleInfo();
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    baseScheduleInfo1.setId(uuid);
                    baseScheduleInfo1.setPlanDate(str);
                    baseScheduleInfo1.setUserNo(user.getUserId());
                    baseScheduleInfo1.setPbType("0");
                    list2.add(baseScheduleInfo1);
                }else {
                    list2.add(one);
                }
            }
            user.setScheduleInfo(list2);
        }
        return Result.OK(users);
    }



    /**
     *   添加
     *
     * @param baseScheduleInfos
     * @return
     */
    @AutoLog(value = "工厂日历排班表-编辑")
    @ApiOperation(value="工厂日历排班表-编辑", notes="工厂日历排班表-编辑")
    @PostMapping(value = "/editSchedInfoList")
    public Result<?> editSchedInfoList(@RequestBody List<BaseScheduleInfo> baseScheduleInfos) {
        for (BaseScheduleInfo baseScheduleInfo : baseScheduleInfos) {
            LambdaQueryWrapper<BaseScheduleInfo> qwSch = new LambdaQueryWrapper<>();
            qwSch.eq(BaseScheduleInfo::getId,baseScheduleInfo.getId());
            BaseScheduleInfo one = baseScheduleInfoService.getOne(qwSch,false);
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            baseScheduleInfo.setAttr3(loginUser.getRealname());
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            baseScheduleInfo.setAttr4(format);
            if(one == null){
                baseScheduleInfoService.save(baseScheduleInfo);
            }else {
                baseScheduleInfoService.updateById(baseScheduleInfo);
            }
        }
        return Result.OK("添加成功！");
    }


    /**
     *   复制
     *
     * @param source 源时间
     * @param target 目标时间
     * @param userIds 用户ID
     * @return
     */
    @AutoLog(value = "工厂日历排班表-复制")
    @ApiOperation(value="工厂日历排班表-复制", notes="工厂日历排班表-复制")
    @GetMapping(value = "/copyPb")
    public Result<?> copyPb(
            @RequestParam(name="source", required=true) String source,
            @RequestParam(name="target", required=true) String target,
            @RequestParam(name="userIds", required=true) String userIds,
            HttpServletRequest req
    ) {
        QueryWrapper<BaseScheduleInfo> queryWrapper = new QueryWrapper();
        queryWrapper.in("user_no",userIds);
        queryWrapper.like("plan_date",source);
        List<BaseScheduleInfo> list = baseScheduleInfoService.list(queryWrapper);
        for (BaseScheduleInfo baseScheduleInfo : list) {
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            baseScheduleInfo.setAttr3(loginUser.getRealname());
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            baseScheduleInfo.setAttr4(format);
            baseScheduleInfo.setPlanDate(baseScheduleInfo.getPlanDate().replace(source,target));
            baseScheduleInfo.setId("");
            baseScheduleInfoService.save(baseScheduleInfo);
        }
        return Result.OK("添加成功！");
    }













   /**
    *   添加
    *
    * @param baseScheduleInfo
    * @return
    */
   @AutoLog(value = "base_schedule_info-添加")
   @ApiOperation(value="base_schedule_info-添加", notes="base_schedule_info-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody BaseScheduleInfo baseScheduleInfo) {
       baseScheduleInfoService.save(baseScheduleInfo);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param baseScheduleInfo
    * @return
    */
   @AutoLog(value = "base_schedule_info-编辑")
   @ApiOperation(value="base_schedule_info-编辑", notes="base_schedule_info-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody BaseScheduleInfo baseScheduleInfo) {
       baseScheduleInfoService.updateById(baseScheduleInfo);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "base_schedule_info-通过id删除")
   @ApiOperation(value="base_schedule_info-通过id删除", notes="base_schedule_info-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       baseScheduleInfoService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "base_schedule_info-批量删除")
   @ApiOperation(value="base_schedule_info-批量删除", notes="base_schedule_info-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.baseScheduleInfoService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "base_schedule_info-通过id查询")
   @ApiOperation(value="base_schedule_info-通过id查询", notes="base_schedule_info-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       BaseScheduleInfo baseScheduleInfo = baseScheduleInfoService.getById(id);
       if(baseScheduleInfo==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(baseScheduleInfo);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param baseScheduleInfo
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, BaseScheduleInfo baseScheduleInfo) {
       return super.exportXls(request, baseScheduleInfo, BaseScheduleInfo.class, "base_schedule_info");
   }

   /**
     * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
   @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
   public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
       return super.importExcel(request, response, BaseScheduleInfo.class);
   }

}
