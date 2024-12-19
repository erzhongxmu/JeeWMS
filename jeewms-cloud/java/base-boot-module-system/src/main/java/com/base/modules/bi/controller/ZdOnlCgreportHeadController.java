package com.base.modules.bi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.common.system.vo.LoginUser;
import com.base.modules.bi.entity.zdOnlCgreportHead;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import com.base.modules.bi.service.IzdOnlCgreportHeadService;
import com.base.modules.bi.service.IzdOnlCgreportItemService;
import com.base.modules.bi.service.IzdOnlCgreportParamService;
import com.base.modules.bi.vo.zdOnlCgreportHeadPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: onl_cgreport_head
 * @Author: wms-cloud
 * @Date: 2020-12-09
 * @Version: V1.0
 */
@Api(tags = "大屏主表")
@RestController
@RequestMapping("/onl_cgreport_head/onlCgreportHead")
@Slf4j
public class ZdOnlCgreportHeadController {
    @Autowired
    private IzdOnlCgreportHeadService onlCgreportHeadService;
    @Autowired
    private IzdOnlCgreportParamService onlCgreportParamService;
    @Autowired
    private IzdOnlCgreportItemService onlCgreportItemService;

    /**
     * 分页列表查询
     *
     * @param zdOnlCgreportHead
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "onl_cgreport_head-分页列表查询")
    @ApiOperation(value = "onl_cgreport_head-分页列表查询", notes = "onl_cgreport_head-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(zdOnlCgreportHead zdOnlCgreportHead,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<zdOnlCgreportHead> queryWrapper = QueryGenerator.initQueryWrapper(zdOnlCgreportHead, req.getParameterMap());
        Page<zdOnlCgreportHead> page = new Page<zdOnlCgreportHead>(pageNo, pageSize);
        IPage<zdOnlCgreportHead> pageList = onlCgreportHeadService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param zdOnlCgreportHeadPage
     * @return
     */
    @AutoLog(value = "onl_cgreport_head-添加")
    @ApiOperation(value = "onl_cgreport_head-添加", notes = "onl_cgreport_head-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody zdOnlCgreportHeadPage zdOnlCgreportHeadPage) {
        zdOnlCgreportHead zdOnlCgreportHead = new zdOnlCgreportHead();
        BeanUtils.copyProperties(zdOnlCgreportHeadPage, zdOnlCgreportHead);
        onlCgreportHeadService.saveMain(zdOnlCgreportHead, zdOnlCgreportHeadPage.getZdOnlCgreportParamList(), zdOnlCgreportHeadPage.getZdOnlCgreportItemList());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param zdOnlCgreportHeadPage
     * @return
     */
    @AutoLog(value = "onl_cgreport_head-编辑")
    @ApiOperation(value = "onl_cgreport_head-编辑", notes = "onl_cgreport_head-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody zdOnlCgreportHeadPage zdOnlCgreportHeadPage) {
        zdOnlCgreportHead zdOnlCgreportHead = new zdOnlCgreportHead();
        BeanUtils.copyProperties(zdOnlCgreportHeadPage, zdOnlCgreportHead);
        zdOnlCgreportHead zdOnlCgreportHeadEntity = onlCgreportHeadService.getById(zdOnlCgreportHead.getId());
        if (zdOnlCgreportHeadEntity == null) {
            return Result.error("未找到对应数据");
        }
        onlCgreportHeadService.updateMain(zdOnlCgreportHead, zdOnlCgreportHeadPage.getZdOnlCgreportParamList(), zdOnlCgreportHeadPage.getZdOnlCgreportItemList());
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "onl_cgreport_head-通过id删除")
    @ApiOperation(value = "onl_cgreport_head-通过id删除", notes = "onl_cgreport_head-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        onlCgreportHeadService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "onl_cgreport_head-批量删除")
    @ApiOperation(value = "onl_cgreport_head-批量删除", notes = "onl_cgreport_head-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.onlCgreportHeadService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "onl_cgreport_head-通过id查询")
    @ApiOperation(value = "onl_cgreport_head-通过id查询", notes = "onl_cgreport_head-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        zdOnlCgreportHead zdOnlCgreportHead = onlCgreportHeadService.getById(id);
        if (zdOnlCgreportHead == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(zdOnlCgreportHead);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "onl_cgreport_param通过主表ID查询")
    @ApiOperation(value = "onl_cgreport_param主表ID查询", notes = "onl_cgreport_param-通主表ID查询")
    @GetMapping(value = "/queryOnlCgreportParamByMainId")
    public Result<?> queryOnlCgreportParamListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<zdOnlCgreportParam> zdOnlCgreportParamList = onlCgreportParamService.selectByMainId(id);
        return Result.OK(zdOnlCgreportParamList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "onl_cgreport_item通过主表ID查询")
    @ApiOperation(value = "onl_cgreport_item主表ID查询", notes = "onl_cgreport_item-通主表ID查询")
    @GetMapping(value = "/queryOnlCgreportItemByMainId")
    public Result<?> queryOnlCgreportItemListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<zdOnlCgreportItem> zdOnlCgreportItemList = onlCgreportItemService.selectByMainId(id);
        return Result.OK(zdOnlCgreportItemList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param zdOnlCgreportHead
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, zdOnlCgreportHead zdOnlCgreportHead) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<zdOnlCgreportHead> queryWrapper = QueryGenerator.initQueryWrapper(zdOnlCgreportHead, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //Step.2 获取导出数据
        List<zdOnlCgreportHead> queryList = onlCgreportHeadService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<zdOnlCgreportHead> zdOnlCgreportHeadList = new ArrayList<zdOnlCgreportHead>();
        if (oConvertUtils.isEmpty(selections)) {
            zdOnlCgreportHeadList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            zdOnlCgreportHeadList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }

        // Step.3 组装pageList
        List<zdOnlCgreportHeadPage> pageList = new ArrayList<zdOnlCgreportHeadPage>();
        for (zdOnlCgreportHead main : zdOnlCgreportHeadList) {
            zdOnlCgreportHeadPage vo = new zdOnlCgreportHeadPage();
            BeanUtils.copyProperties(main, vo);
            List<zdOnlCgreportParam> zdOnlCgreportParamList = onlCgreportParamService.selectByMainId(main.getId());
            vo.setZdOnlCgreportParamList(zdOnlCgreportParamList);
            List<zdOnlCgreportItem> zdOnlCgreportItemList = onlCgreportItemService.selectByMainId(main.getId());
            vo.setZdOnlCgreportItemList(zdOnlCgreportItemList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "onl_cgreport_head列表");
        mv.addObject(NormalExcelConstants.CLASS, zdOnlCgreportHeadPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("onl_cgreport_head数据", "导出人:" + sysUser.getRealname(), "onl_cgreport_head"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
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
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<zdOnlCgreportHeadPage> list = ExcelImportUtil.importExcel(file.getInputStream(), zdOnlCgreportHeadPage.class, params);
                for (zdOnlCgreportHeadPage page : list) {
                    zdOnlCgreportHead po = new zdOnlCgreportHead();
                    BeanUtils.copyProperties(page, po);
                    onlCgreportHeadService.saveMain(po, page.getZdOnlCgreportParamList(), page.getZdOnlCgreportItemList());
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }

    /**
     * 大屏展示数据查询
     */
    @SneakyThrows
    @ApiOperation(value = "大屏展示数据查询", notes = "大屏展示数据查询")
    @GetMapping("/BigScreenBi")
    public Object BigScreenBi(@RequestParam(name = "Id", required = true) String Id) {
        Object json = onlCgreportHeadService.BigScreenBiById(Id);
        return json;
    }

    /**
     * 大屏展示数据柱状图查询
     */
    @SneakyThrows
    @ApiOperation(value = "大屏展示数据柱状图查询", notes = "大屏展示数据柱状图查询")
    @GetMapping("/BigScreenBiHistogram")
    public Object BigScreenBiHistogram(@RequestParam(name = "Id", required = true) String Id) {
        Object json = onlCgreportHeadService.BigScreenBiHistogram(Id);
        return json;
    }

    @SneakyThrows
    @ApiOperation(value = "大屏返回list", notes = "大屏返回list")
    @GetMapping("/queryList")
    public String queryList(@RequestParam(name = "Id") String Id) {
        return onlCgreportHeadService.queryBiData(Id);
    }

    @SneakyThrows
    @ApiOperation(value = "大屏返回list", notes = "大屏返回list")
    @GetMapping("/queryOne")
    public String queryOne(@RequestParam(name = "Id") String Id) {
        return onlCgreportHeadService.queryOne(Id);
    }
}

