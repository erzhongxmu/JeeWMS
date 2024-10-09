package com.zzjee.tms.controller;

import com.alibaba.fastjson.JSONArray;
import com.zzjee.tms.entity.TmsMdCheliangEntity;
import com.zzjee.tms.service.TmsMdCheliangServiceI;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmQmIEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 车辆管理
 * @date 2018-01-29 21:57:07
 */
@Api(value = "TmsMdCheliang", description = "车辆管理", tags = "tmsMdCheliangController")
@Controller
@RequestMapping("/tmsMdCheliangController")
public class TmsMdCheliangController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TmsMdCheliangController.class);

    @Autowired
    private TmsMdCheliangServiceI tmsMdCheliangService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;

    /**
     * 车辆管理列表 页面跳转
     * @param request 请求
     * @return ModelAndView
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/tms/tmsMdCheliangList");
    }

    /**
     * easyui AJAX请求数据
     * @param request 请求
     * @param response 响应
     * @param dataGrid
     * @param tmsMdCheliang 实体
     * @return 返回AjaxJson对象
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TmsMdCheliangEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdCheliang, request.getParameterMap());
        cq.add();
        this.tmsMdCheliangService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doassignwave")
    @ResponseBody
    public AjaxJson dowavebatch(String ids, String waveid, HttpServletRequest request) {
        String message = null;
        // 创建对象
        AjaxJson j = new AjaxJson();
        try {
            int aint = (int) Math.round(Math.random() * 9);
            for (String id : ids.split(",")) {
                TmsMdCheliangEntity t = tmsMdCheliangService.get(TmsMdCheliangEntity.class, id);
                t.setChepaihao(Integer.toString(aint));
                aint++;
                tmsMdCheliangService.updateEntitie(t);
            }
        } catch (Exception e) {
            // 抛出异常信息
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        // 返回AjaxJson对象
        return j;

    }

    /**
     * 删除车辆管理
     * @param request 请求
     * @return j
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request) {
        String message = null;
        // 创建对象
        AjaxJson j = new AjaxJson();
        tmsMdCheliang = systemService.getEntity(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
        message = "车辆管理删除成功";
        try {
            tmsMdCheliang.setZhuangtai("N");
            tmsMdCheliangService.updateEntitie(tmsMdCheliang);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            // 抛出异常
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        // 返回AjaxJson对象
        return j;
    }

    /**
     * 批量删除车辆管理
     * @param request 请求
     * @param ids
     * @return j
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        // 创建对象
        AjaxJson j = new AjaxJson();
        message = "车辆管理删除成功";
        try {
            for (String id : ids.split(",")) {
                TmsMdCheliangEntity tmsMdCheliang = systemService.getEntity(TmsMdCheliangEntity.class,
                        id);
                tmsMdCheliang.setZhuangtai("N");
                tmsMdCheliangService.updateEntitie(tmsMdCheliang);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            // 抛出异常信息
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        // 返回 AjaxJson对象
        return j;
    }


    /**
     * 添加车辆管理
     * @param tmsMdCheliang 实体
     * @param request 请求
     * @return j
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request) {
        String message = null;
        // 创建对象
        AjaxJson j = new AjaxJson();
        message = "车辆管理添加成功";
        try {
            tmsMdCheliang.setZhuangtai("Y");
            tmsMdCheliangService.save(tmsMdCheliang);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出异常信息
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        // 返回结果
        return j;
    }

    /**
     * 更新车辆管理
     * @param tmsMdCheliang
     * @param request 请求
     * @return j
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request) {
        String message = null;
        // 创建对象
        AjaxJson j = new AjaxJson();
        message = "车辆管理更新成功";
        TmsMdCheliangEntity t = tmsMdCheliangService.get(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tmsMdCheliang, t);
            tmsMdCheliangService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            // 抛出异常信息
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        // 返回结果
        return j;
    }

    /**
     * 车辆管理新增页面跳转
     * @param tmsMdCheliang
     * @param req 请求
     * @return  ModelAndView
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tmsMdCheliang.getId())) {
            tmsMdCheliang = tmsMdCheliangService.getEntity(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
            req.setAttribute("tmsMdCheliangPage", tmsMdCheliang);
        }
        return new ModelAndView("com/zzjee/tms/tmsMdCheliang-add");
    }

    /**
     * 车辆管理编辑页面跳转
     * @param tmsMdCheliang
     * @param req 请求
     * @return ModelAndView
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tmsMdCheliang.getId())) {
            tmsMdCheliang = tmsMdCheliangService.getEntity(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
            req.setAttribute("tmsMdCheliangPage", tmsMdCheliang);
        }
        return new ModelAndView("com/zzjee/tms/tmsMdCheliang-update");
    }

    /**
     * 导入功能跳转
     * @param req 请求
     * @return ModelAndView
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tmsMdCheliangController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel列表
     * @param request 请求
     * @param response 响应
     * @param tmsMdCheliang 实体
     * @return JEECG_EXCEL_VIEW
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TmsMdCheliangEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdCheliang, request.getParameterMap());
        List<TmsMdCheliangEntity> tmsMdCheliangs = this.tmsMdCheliangService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "车辆管理");
        modelMap.put(NormalExcelConstants.CLASS, TmsMdCheliangEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("车辆管理列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tmsMdCheliangs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel模板
     * @param request 请求
     * @param tmsMdCheliang 实体
     * @param response 响应
     * @return JEECG_EXCEL_VIEW
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "车辆管理");
        modelMap.put(NormalExcelConstants.CLASS, TmsMdCheliangEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("车辆管理列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        // 创建对象
        AjaxJson j = new AjaxJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 遍历fileMap
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        // 使用entrySet进行增强for循环，遍历map
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传的文件对象
            MultipartFile file = entity.getValue();
            // 创建导入参数对象，设置标题行、头部行、是否需要保存等参数
            ImportParams params = new ImportParams();
            params.setTitleRows(2); // 设置标题行：两行
            params.setHeadRows(1);  // 设置头部行：一行
            params.setNeedSave(true);   // 表示导入的数据需要被保存
            try {
                List<TmsMdCheliangEntity> listTmsMdCheliangEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TmsMdCheliangEntity.class, params);
                for (TmsMdCheliangEntity tmsMdCheliang : listTmsMdCheliangEntitys) {
                    tmsMdCheliangService.save(tmsMdCheliang);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                // 如果在导入过程中出现异常，则设置AjaxJson对象的消息，表示文件导入失败
                j.setMsg("文件导入失败！");
                // 记录异常信息，便于后续问题排查
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    // 尝试关闭文件流，防止资源泄露
                    file.getInputStream().close();
                } catch (IOException e) {
                    // 如果出现异常，打印堆栈跟踪
                    e.printStackTrace();
                }
            }
        }
        // 返回结果
        return j;
    }

}
