package com.base.modules.demo.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.api.vo.Result;
import com.base.common.system.base.controller.BaseController;
import com.base.common.system.vo.LoginUser;
import com.base.modules.demo.test.entity.BaseOrderCustomer;
import com.base.modules.demo.test.entity.BaseOrderMain;
import com.base.modules.demo.test.entity.BaseOrderTicket;
import com.base.modules.demo.test.service.IBaseOrderCustomerService;
import com.base.modules.demo.test.service.IBaseOrderMainService;
import com.base.modules.demo.test.service.IBaseOrderTicketService;
import com.base.modules.demo.test.vo.BaseOrderMainPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 一对多示例（JEditableTable行编辑）
 * @Author: base-boot
 * @Date:2019-02-15
 * @Version: V2.0
 */
@RestController
@RequestMapping("/test/baseOrderMain")
@Slf4j
public class BaseOrderMainController extends BaseController<BaseOrderMain, IBaseOrderMainService> {

    @Autowired
    private IBaseOrderMainService baseOrderMainService;
    @Autowired
    private IBaseOrderCustomerService baseOrderCustomerService;
    @Autowired
    private IBaseOrderTicketService baseOrderTicketService;

    /**
     * 分页列表查询
     *
     * @param baseOrderMain
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(BaseOrderMain baseOrderMain, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<BaseOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(baseOrderMain, req.getParameterMap());
        Page<BaseOrderMain> page = new Page<BaseOrderMain>(pageNo, pageSize);
        IPage<BaseOrderMain> pageList = baseOrderMainService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param baseOrderMainPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BaseOrderMainPage baseOrderMainPage) {
        BaseOrderMain baseOrderMain = new BaseOrderMain();
        BeanUtils.copyProperties(baseOrderMainPage, baseOrderMain);
        baseOrderMainService.saveMain(baseOrderMain, baseOrderMainPage.getBaseOrderCustomerList(), baseOrderMainPage.getBaseOrderTicketList());
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param baseOrderMainPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> eidt(@RequestBody BaseOrderMainPage baseOrderMainPage) {
        BaseOrderMain baseOrderMain = new BaseOrderMain();
        BeanUtils.copyProperties(baseOrderMainPage, baseOrderMain);
        baseOrderMainService.updateCopyMain(baseOrderMain, baseOrderMainPage.getBaseOrderCustomerList(), baseOrderMainPage.getBaseOrderTicketList());
        return Result.ok("编辑成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        baseOrderMainService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.baseOrderMainService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BaseOrderMain baseOrderMain = baseOrderMainService.getById(id);
        return Result.ok(baseOrderMain);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryOrderCustomerListByMainId")
    public Result<?> queryOrderCustomerListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<BaseOrderCustomer> baseOrderCustomerList = baseOrderCustomerService.selectCustomersByMainId(id);
        return Result.ok(baseOrderCustomerList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryOrderTicketListByMainId")
    public Result<?> queryOrderTicketListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<BaseOrderTicket> baseOrderTicketList = baseOrderTicketService.selectTicketsByMainId(id);
        return Result.ok(baseOrderTicketList);
    }

    /**
     * 导出excel
     *
     * @param request
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BaseOrderMain baseOrderMain) {
        // Step.1 组装查询条件
        QueryWrapper<BaseOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(baseOrderMain, request.getParameterMap());
        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        List<BaseOrderMainPage> pageList = new ArrayList<BaseOrderMainPage>();

        List<BaseOrderMain> baseOrderMainList = baseOrderMainService.list(queryWrapper);
        for (BaseOrderMain orderMain : baseOrderMainList) {
            BaseOrderMainPage vo = new BaseOrderMainPage();
            BeanUtils.copyProperties(orderMain, vo);
            // 查询机票
            List<BaseOrderTicket> baseOrderTicketList = baseOrderTicketService.selectTicketsByMainId(orderMain.getId());
            vo.setBaseOrderTicketList(baseOrderTicketList);
            // 查询客户
            List<BaseOrderCustomer> baseOrderCustomerList = baseOrderCustomerService.selectCustomersByMainId(orderMain.getId());
            vo.setBaseOrderCustomerList(baseOrderCustomerList);
            pageList.add(vo);
        }

        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "一对多订单示例");
        // 注解对象Class
        mv.addObject(NormalExcelConstants.CLASS, BaseOrderMainPage.class);
        // 自定义表格参数
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("自定义导出Excel内容标题", "导出人:" + sysUser.getRealname(), "自定义Sheet名字"));
        // 导出数据列表
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(2);
            params.setNeedSave(true);
            try {
                List<BaseOrderMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BaseOrderMainPage.class, params);
                for (BaseOrderMainPage page : list) {
                    BaseOrderMain po = new BaseOrderMain();
                    BeanUtils.copyProperties(page, po);
                    baseOrderMainService.saveMain(po, page.getBaseOrderCustomerList(), page.getBaseOrderTicketList());
                }
                return Result.ok("文件导入成功！");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败：" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

}
