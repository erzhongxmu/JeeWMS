package com.zzjee.wm.controller;

import com.zzjee.wm.entity.BaStoreAreaEntity;
import com.zzjee.wm.service.BaStoreAreaServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
@Controller
@RequestMapping("/baStoreAreaController")
public class BaStoreAreaController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BaStoreAreaController.class);

    @Autowired
    private BaStoreAreaServiceI baStoreAreaService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    /**
     * ba_store_area列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/wm/baStoreAreaList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(BaStoreAreaEntity baStoreArea, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(BaStoreAreaEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baStoreArea, request.getParameterMap());
        try {
            //自定义追加查询条件
            String query_createDate_begin = request.getParameter("createDate_begin");
            String query_createDate_end = request.getParameter("createDate_end");
            // 如果createDate_begin不为空，则添加大于等于该日期的条件
            if (StringUtil.isNotEmpty(query_createDate_begin)) {
                cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
            }
            // 如果createDate_end不为空，则添加小于等于该日期的条件
            if (StringUtil.isNotEmpty(query_createDate_end)) {
                cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        // 调用wmOmQmIService的getDataGridReturn方法执行查询并返回结果
        this.baStoreAreaService.getDataGridReturn(cq, true);
        // 将查询结果封装成DataGrid对象并返回给前端
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除ba_store_area
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(BaStoreAreaEntity baStoreArea, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        // 通过ID获取实体类对象
        baStoreArea = systemService.getEntity(BaStoreAreaEntity.class, baStoreArea.getId());
        message = "ba_store_area删除成功";
        try {
            // 调用服务层方法删除实体
            baStoreAreaService.delete(baStoreArea);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除ba_store_area
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "ba_store_area删除成功";
        try {
            // 添加日志记录，记录类型为删除，级别为信息
            for (String id : ids.split(",")) {
                BaStoreAreaEntity baStoreArea = systemService.getEntity(BaStoreAreaEntity.class,
                        id
                );
                // 调用baStoreAreaService的delete方法删除baStoreArea对象
                baStoreAreaService.delete(baStoreArea);
                // 添加日志记录，记录类型为删除，级别为信息
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            // 如果发生异常，打印堆栈跟踪信息
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * 添加ba_store_area
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(BaStoreAreaEntity baStoreArea, HttpServletRequest request) {
        // 定义一个String类型的变量message用于存储消息内容
        String message = null;
        // 创建AjaxJson对象j，用于封装返回给前端的数据
        AjaxJson j = new AjaxJson();
        // 设置默认的消息内容为"ba_store_area添加成功"
        message = "ba_store_area添加成功";
        try {
            // 调用baStoreAreaService的save方法保存传入的BaStoreAreaEntity对象到数据库
            baStoreAreaService.save(baStoreArea);
            // 记录操作日志，类型为插入操作，级别为INFO
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 抛出业务异常，并附带异常信息
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * 更新ba_store_area
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(BaStoreAreaEntity baStoreArea, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "ba_store_area更新成功";
        BaStoreAreaEntity t = baStoreAreaService.get(BaStoreAreaEntity.class, baStoreArea.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(baStoreArea, t);
            baStoreAreaService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ba_store_area新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(BaStoreAreaEntity baStoreArea, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(baStoreArea.getId())) {
            baStoreArea = baStoreAreaService.getEntity(BaStoreAreaEntity.class, baStoreArea.getId());
            req.setAttribute("baStoreAreaPage", baStoreArea);
        }
        return new ModelAndView("com/zzjee/wm/baStoreArea-add");
    }

    /**
     * ba_store_area编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(BaStoreAreaEntity baStoreArea, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(baStoreArea.getId())) {
            baStoreArea = baStoreAreaService.getEntity(BaStoreAreaEntity.class, baStoreArea.getId());
            req.setAttribute("baStoreAreaPage", baStoreArea);
        }
        return new ModelAndView("com/zzjee/wm/baStoreArea-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "baStoreAreaController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(BaStoreAreaEntity baStoreArea, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(BaStoreAreaEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baStoreArea, request.getParameterMap());
        List<BaStoreAreaEntity> baStoreAreas = this.baStoreAreaService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "ba_store_area");
        modelMap.put(NormalExcelConstants.CLASS, BaStoreAreaEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("ba_store_area列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, baStoreAreas);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(BaStoreAreaEntity baStoreArea, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "ba_store_area");
        modelMap.put(NormalExcelConstants.CLASS, BaStoreAreaEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("ba_store_area列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<BaStoreAreaEntity> listBaStoreAreaEntitys = ExcelImportUtil.importExcel(file.getInputStream(), BaStoreAreaEntity.class, params);
                for (BaStoreAreaEntity baStoreArea : listBaStoreAreaEntitys) {
                    baStoreAreaService.save(baStoreArea);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<BaStoreAreaEntity> list() {
        List<BaStoreAreaEntity> listBaStoreAreas = baStoreAreaService.getList(BaStoreAreaEntity.class);
        return listBaStoreAreas;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        BaStoreAreaEntity task = baStoreAreaService.get(BaStoreAreaEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody BaStoreAreaEntity baStoreArea, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<BaStoreAreaEntity>> failures = validator.validate(baStoreArea);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            baStoreAreaService.save(baStoreArea);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = baStoreArea.getId();
        URI uri = uriBuilder.path("/rest/baStoreAreaController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody BaStoreAreaEntity baStoreArea) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<BaStoreAreaEntity>> failures = validator.validate(baStoreArea);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            baStoreAreaService.saveOrUpdate(baStoreArea);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        //按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        baStoreAreaService.deleteEntityById(BaStoreAreaEntity.class, id);
    }
}
