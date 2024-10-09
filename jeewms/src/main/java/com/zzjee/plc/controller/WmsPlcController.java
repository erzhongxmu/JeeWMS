package com.zzjee.plc.controller;

import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Profinet.Omron.OmronCipNet;
import HslCommunication.Profinet.Omron.OmronHostLinkOverTcp;
import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
import com.zzjee.plc.entity.WmsPlcEntity;
import com.zzjee.plc.service.WmsPlcServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wm.entity.WmToMoveGoodsEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: PLC指令
 * @date 2022-09-12 18:33:25
 */
@Controller
@RequestMapping("/wmsPlcController")
public class WmsPlcController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WmsPlcController.class);

    @Autowired
    private WmsPlcServiceI wmsPlcService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;


    /**
     * PLC指令列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/plc/wmsPlcList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(WmsPlcEntity wmsPlc, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WmsPlcEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmsPlc, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.wmsPlcService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除PLC指令
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(WmsPlcEntity wmsPlc, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        wmsPlc = systemService.getEntity(WmsPlcEntity.class, wmsPlc.getId());
        message = "PLC指令删除成功";
        try {
            wmsPlcService.delete(wmsPlc);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "PLC指令删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除PLC指令
     *
     * @return
     */
    @RequestMapping(params = "dotoup")
    @ResponseBody
    public AjaxJson dotoup(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "PLC指令执行成功";
        try {
            for (String id : ids.split(",")) {
                run(id, "", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "PLC指令执行失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    public void runu() {
        WmsPlcEntity wmsPlc = null;
        String hql = "";
        List<WmsPlcEntity> wmsPlcEntityList = new ArrayList<WmsPlcEntity>();
        hql = "from WmsPlcEntity t where  t.comNo =  ? ";
        wmsPlcEntityList = systemService.findHql(hql, "runu");
        if (!CollectionUtils.isEmpty(wmsPlcEntityList)) {
            wmsPlc = wmsPlcEntityList.get(0);
        }
        String[] coms = wmsPlc.getComCons().split(";");
        for (String com : coms) {
            String comid[] = com.split(",");
            String comno = comid[0];
            String comstep = comid[1];
            run("", comno, comstep);
        }

    }
    public void run(String id, String comNo, String stepNum) {
        System.out.println("id:" + id + ";comNo:" + comNo + ";stepNum:" + stepNum);
        if (stepNum.equals("0")) {
            return;
        }
        WmsPlcEntity wmsPlc = null;
        if (StringUtil.isNotEmpty(id)) {
            wmsPlc = systemService.getEntity(WmsPlcEntity.class, id);
        }
        if (StringUtil.isNotEmpty(comNo)) {

            String hql = "";
            List<WmsPlcEntity> wmsPlcEntityList = new ArrayList<WmsPlcEntity>();
            hql = "from WmsPlcEntity t where  t.comNo =  ? ";
            wmsPlcEntityList = systemService.findHql(hql, comNo);
            if (!CollectionUtils.isEmpty(wmsPlcEntityList)) {
                wmsPlc = wmsPlcEntityList.get(0);
            }
        }
        if (wmsPlc != null) {
            long start = System.currentTimeMillis();
            SiemensPLCS siemensPLCS = SiemensPLCS.S200Smart;
            SiemensS7Net siemensS7Net = null;
            siemensS7Net = new SiemensS7Net(siemensPLCS);
            siemensS7Net.setIpAddress(wmsPlc.getPlcIp());
            siemensS7Net.setPort(Integer.parseInt(wmsPlc.getPlcPort()));
            OperateResult connect = siemensS7Net.ConnectServer();
            if (connect.IsSuccess) {
                System.out.println("connect success");
            } else {
                System.out.println("connect error");
                try {
                    siemensS7Net.ConnectClose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String comCons = wmsPlc.getComCons();
            String query01 = wmsPlc.getQuery01();
            String query02 = wmsPlc.getQuery02();
            Float stepNumrun = Float.valueOf("1");
            if (StringUtil.isNotEmpty(stepNum)) {
                stepNumrun = Float.parseFloat(stepNum);
            } else {
                stepNumrun = Float.parseFloat(wmsPlc.getSetpNum());
            }
            Float stepTime = Float.parseFloat(wmsPlc.getSetpTime());
            comCons = StringUtils.replace(comCons, "{query01}", query01);
            comCons = StringUtils.replace(comCons, "{query02}", query02);
            String[] coms = comCons.split(";");
            for (String com : coms) {
                System.out.println("指令：" + com);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] split = com.split(",");
                String defaultAddress = split[1];
                if (split[0].equals("boolean")) {
                    if (split[2].equals("false")) {
                        siemensS7Net.Write(defaultAddress, false);
                    } else {
                        siemensS7Net.Write(defaultAddress, true);
                    }
                } else if (split[0].equals("float")) {
                    Float runfloat = Float.parseFloat(split[2]) * stepNumrun;
                    System.out.println("runfloat：" + Math.abs(runfloat));
                    siemensS7Net.Write(defaultAddress, Math.abs(runfloat));
                } else if (split[0].equals("-float")) {
                    Float runfloat = Float.parseFloat(split[2]) * stepNumrun;
                    System.out.println("runfloat：" + runfloat);
                    siemensS7Net.Write(defaultAddress, runfloat);
                } else if (split[0].equals("int")) {
                    Float runfloat = Float.parseFloat(split[2]) * stepNumrun;
                    Float abs = Math.abs(runfloat);
                    int runint = abs.intValue();
                    System.out.println("runint：" + runint);
                    siemensS7Net.Write(defaultAddress, runint);
                } else if (split[0].equals("-int")) {
                    Float runfloat = Float.parseFloat(split[2]) * stepNumrun;
                    int runint = runfloat.intValue();
                    System.out.println("runint：" + runint);
                    siemensS7Net.Write(defaultAddress, runint);
                }
            }
            //执行完指令等待时间
            try {
                Float sleeptime = Math.abs(stepNumrun * stepTime);
                Thread.sleep(sleeptime.longValue());
            } catch (Exception e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();
            long times = end - start;
            org.jeecgframework.core.util.LogUtil.info(wmsPlc.getComRemark() + "总耗时" + times + "毫秒" + comCons);

        }


    }

    /**
     * 批量删除PLC指令
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "PLC指令删除成功";
        try {
            for (String id : ids.split(",")) {
                WmsPlcEntity wmsPlc = systemService.getEntity(WmsPlcEntity.class,
                        id
                );
                wmsPlcService.delete(wmsPlc);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "PLC指令删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加PLC指令
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(WmsPlcEntity wmsPlc, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "PLC指令添加成功";
        try {
            wmsPlcService.save(wmsPlc);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "PLC指令添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新PLC指令
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(WmsPlcEntity wmsPlc, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "PLC指令更新成功";
        WmsPlcEntity t = wmsPlcService.get(WmsPlcEntity.class, wmsPlc.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(wmsPlc, t);
            wmsPlcService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "PLC指令更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * PLC指令新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(WmsPlcEntity wmsPlc, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(wmsPlc.getId())) {
            wmsPlc = wmsPlcService.getEntity(WmsPlcEntity.class, wmsPlc.getId());
            req.setAttribute("wmsPlcPage", wmsPlc);
        }
        return new ModelAndView("com/zzjee/plc/wmsPlc-add");
    }

    /**
     * PLC指令编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(WmsPlcEntity wmsPlc, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(wmsPlc.getId())) {
            wmsPlc = wmsPlcService.getEntity(WmsPlcEntity.class, wmsPlc.getId());
            req.setAttribute("wmsPlcPage", wmsPlc);
        }
        return new ModelAndView("com/zzjee/plc/wmsPlc-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "wmsPlcController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(WmsPlcEntity wmsPlc, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(WmsPlcEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmsPlc, request.getParameterMap());
        List<WmsPlcEntity> wmsPlcs = this.wmsPlcService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "PLC指令");
        modelMap.put(NormalExcelConstants.CLASS, WmsPlcEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("PLC指令列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, wmsPlcs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(WmsPlcEntity wmsPlc, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "PLC指令");
        modelMap.put(NormalExcelConstants.CLASS, WmsPlcEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("PLC指令列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<WmsPlcEntity> listWmsPlcEntitys = ExcelImportUtil.importExcel(file.getInputStream(), WmsPlcEntity.class, params);
                for (WmsPlcEntity wmsPlc : listWmsPlcEntitys) {
                    wmsPlcService.save(wmsPlc);
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
    public List<WmsPlcEntity> list() {
        List<WmsPlcEntity> listWmsPlcs = wmsPlcService.getList(WmsPlcEntity.class);
        return listWmsPlcs;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        WmsPlcEntity task = wmsPlcService.get(WmsPlcEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody WmsPlcEntity wmsPlc, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WmsPlcEntity>> failures = validator.validate(wmsPlc);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            wmsPlcService.save(wmsPlc);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = wmsPlc.getId();
        URI uri = uriBuilder.path("/rest/wmsPlcController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody WmsPlcEntity wmsPlc) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WmsPlcEntity>> failures = validator.validate(wmsPlc);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            wmsPlcService.saveOrUpdate(wmsPlc);
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
        wmsPlcService.deleteEntityById(WmsPlcEntity.class, id);
    }
}
