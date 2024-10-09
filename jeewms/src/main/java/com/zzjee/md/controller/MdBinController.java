package com.zzjee.md.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.zzjee.plc.controller.WmsPlcController;
import com.zzjee.rfid.entity.RfidBuseEntity;
import com.zzjee.wmutil.wmUtil;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.service.MdBinServiceI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author erzhongxmu
 * @version V1.0
 * @Title: Controller
 * @Description: 仓位定义
 * @date 2017-08-15 23:17:02
 */
@Controller
@RequestMapping("/mdBinController")
public class MdBinController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MdBinController.class);

    @Autowired
    private MdBinServiceI mdBinService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    @Autowired
    private WmsPlcController wmsPlcController;
    ExecutorService executor = Executors.newFixedThreadPool(8);


    /**
     * 仓位定义列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/md/mdBinList");
    }

    @RequestMapping(params = "listc")
    public ModelAndView listc(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/md/mdavabinlist");
    }

    @RequestMapping(params = "listagv")
    public ModelAndView listagv(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/md/mdbinagvlist");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(MdBinEntity mdBin, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(MdBinEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mdBin, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.mdBinService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除仓位定义
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(MdBinEntity mdBin, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        mdBin = systemService.getEntity(MdBinEntity.class, mdBin.getId());
        message = "仓位停用成功";
        try {

            if (wmUtil.checkishavestock("bin", mdBin.getKuWeiBianMa())) {
                message = "仓位停用成功，但是存在库存";
                j.setSuccess(false);
                j.setMsg(message);
                mdBin.setTingYong("Y");
                mdBinService.saveOrUpdate(mdBin);
                return j;
            } else {
                mdBinService.delete(mdBin);
            }
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "仓位停用失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除仓位定义
     *
     * @return
     */
    @RequestMapping(params = "doHad")
    @ResponseBody
    public AjaxJson doHad(MdBinEntity mdBin, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        mdBin = systemService.getEntity(MdBinEntity.class, mdBin.getId());
        message = "仓位同步有货成功";
        try {
//			mdBin.setTingYong("Y");
//			mdBinService.saveOrUpdate(mdBin);
            if (wmUtil.checkishavestock("bin", mdBin.getKuWeiBianMa())) {
                RfidBuseEntity rfidBuseEntity = new RfidBuseEntity();
                rfidBuseEntity.setRfidType("CW");
                rfidBuseEntity.setRfidId1(mdBin.getKuWeiBianMa());
                rfidBuseEntity.setRfidId2("Y");
                rfidBuseEntity.setBpmStatus("1");
                systemService.save(rfidBuseEntity);
                message = "仓位同步有货成功";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            } else {
                RfidBuseEntity rfidBuseEntity = new RfidBuseEntity();
                rfidBuseEntity.setRfidType("CW");
                rfidBuseEntity.setRfidId1(mdBin.getKuWeiBianMa());
                rfidBuseEntity.setRfidId2("N");
                rfidBuseEntity.setBpmStatus("1");
                systemService.save(rfidBuseEntity);
                message = "仓位同步无货成功";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "仓位同步失败功";
            throw new BusinessException(e.getMessage());
        }

    }


    @RequestMapping(params = "getbinall")
    @ResponseBody
    public AjaxJson getNoticeList(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            j.setObj(0);
            String tsql = "select * "
                    + "  from wv_bin_all ws  where 1 = 1 ";
            if (!StringUtil.isEmpty(req.getParameter("binstore"))) {
                tsql = tsql + " and ws.bin_store like  '%" + req.getParameter("binstore") + "%' ";
            }
            if (!StringUtil.isEmpty(req.getParameter("binid"))) {
                tsql = tsql + "   and ws.binid like  '%" + req.getParameter("binid") + "%' ";
            }
            if (!StringUtil.isEmpty(req.getParameter("des"))) {
                tsql = tsql + "  and  ws.des like  '%" + req.getParameter("des") + "%' ";
            }
            if (!StringUtil.isEmpty(req.getParameter("cengshu"))) {
                tsql = tsql + "  and  ws.znode like  '%" + req.getParameter("cengshu") + "%' ";
            }

            String hangshu = req.getParameter("hangshu");
            String type = req.getParameter("type");

            System.out.print(tsql);
            List<Map<String, Object>> resultt = systemService
                    .findForJdbc(tsql);
//				list = this.tSSmsService.getMsgsList(curUser,curDate);
            //将List转换成JSON存储
            JSONArray result = new JSONArray();
            if (resultt != null && resultt.size() > 0) {
                for (int i = 0; i < resultt.size(); i++) {
                    JSONObject jsonParts = new JSONObject();
                    jsonParts.put("bin_store", resultt.get(i).get("bin_store"));
                    jsonParts.put("binid", resultt.get(i).get("binid"));
                    jsonParts.put("des", resultt.get(i).get("des"));
                    jsonParts.put("tincount", resultt.get(i).get("tincount"));
                    try {

                        if ("fanxiang".equals(type)) {
                            try {
                                int hangshuint = Integer.parseInt(hangshu);
                                int xnode = Integer.parseInt(resultt.get(i).get("xnode").toString());

                                jsonParts.put("xnode", hangshuint + 1 - xnode);
                            } catch (Exception e) {

                            }


                        } else {
                            jsonParts.put("xnode", resultt.get(i).get("xnode"));

                        }

                        jsonParts.put("ynode", resultt.get(i).get("ynode"));

                        jsonParts.put("znode", resultt.get(i).get("znode"));

                        jsonParts.put("colour", resultt.get(i).get("colour"));

                    } catch (Exception e) {

                    }
                    result.add(jsonParts);
                }
                j.setObj(resultt.size());


                Map<String, Object> attrs = new HashMap<String, Object>();
                attrs.put("messageList", result);
//				String tip = MutiLangUtil.getMutiLangInstance().getLang("message.tip");
//				attrs.put("tip", tip);
//				String seeAll = MutiLangUtil.getMutiLangInstance().getLang("message.seeAll");
//				attrs.put("seeAll", seeAll);
                j.setAttributes(attrs);
            }
        } catch (Exception e) {
            j.setSuccess(false);
        }
        return j;
    }

    @RequestMapping(params = "getbinallagv")
    @ResponseBody
    public AjaxJson getbinallagv(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String hangshu = req.getParameter("hangshu");
        String type = req.getParameter("type");
        String binFrom = req.getParameter("binid");
        String binTo = req.getParameter("des");
        String startcom = req.getParameter("startcom");
        String midcom = req.getParameter("midcom");
        String endcom = req.getParameter("endcom");
        if ("diaodu".equals(type)||"diaoduu".equals(type)) {//调度需要方式指令
//      异步发送指令
            if (StringUtil.isEmpty(binFrom)&&"diaodu".equals(type)) {
                j.setMsg("开始储位为空");
                j.setSuccess(false);
                return j;
            }
            if (StringUtil.isEmpty(binTo)&&"diaodu".equals(type)) {
                j.setMsg("结束储位为空");
                j.setSuccess(false);
                return j;
            }
            try {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        runagv(binFrom, binTo, startcom, midcom, endcom, type);
                    }
                });
            } catch (Exception e) {
            }

        }

        try {
            j.setObj(0);
            String tsql = "select * "
                    + "  from wv_bin_all ws  where 1 = 1 ";
            if (!StringUtil.isEmpty(req.getParameter("binstore"))) {
                tsql = tsql + " and ws.bin_store like  '%" + req.getParameter("binstore") + "%' ";
            }

            if (!StringUtil.isEmpty(req.getParameter("cengshu"))) {
                tsql = tsql + "  and  ws.znode like  '%" + req.getParameter("cengshu") + "%' ";
            }


            System.out.print(tsql);
            List<Map<String, Object>> resultt = systemService
                    .findForJdbc(tsql);
//				list = this.tSSmsService.getMsgsList(curUser,curDate);
            //将List转换成JSON存储
            JSONArray result = new JSONArray();
            if (resultt != null && resultt.size() > 0) {
                for (int i = 0; i < resultt.size(); i++) {
                    JSONObject jsonParts = new JSONObject();
                    jsonParts.put("bin_store", resultt.get(i).get("bin_store"));
                    jsonParts.put("binid", resultt.get(i).get("binid"));
                    jsonParts.put("des", resultt.get(i).get("des"));
                    jsonParts.put("tincount", resultt.get(i).get("tincount"));
                    try {
                        if ("fanxiang".equals(type)) {
                            try {
                                int hangshuint = Integer.parseInt(hangshu);
                                int xnode = Integer.parseInt(resultt.get(i).get("xnode").toString());

                                jsonParts.put("xnode", hangshuint + 1 - xnode);
                            } catch (Exception e) {

                            }


                        } else {
                            jsonParts.put("xnode", resultt.get(i).get("xnode"));

                        }

                        jsonParts.put("xnode", resultt.get(i).get("xnode"));
                        jsonParts.put("ynode", resultt.get(i).get("ynode"));

                        jsonParts.put("znode", resultt.get(i).get("znode"));

                        jsonParts.put("colour", resultt.get(i).get("colour"));

                    } catch (Exception e) {

                    }
                    result.add(jsonParts);
                }
                j.setObj(resultt.size());


                Map<String, Object> attrs = new HashMap<String, Object>();
                attrs.put("messageList", result);
//				String tip = MutiLangUtil.getMutiLangInstance().getLang("message.tip");
//				attrs.put("tip", tip);
//				String seeAll = MutiLangUtil.getMutiLangInstance().getLang("message.seeAll");
//				attrs.put("seeAll", seeAll);
                j.setAttributes(attrs);
            }
        } catch (Exception e) {
            j.setSuccess(false);
        }


        return j;
    }

    public void runagv(String binfrom, String binto, String startcom, String midcom, String endcom, String type) {
        if ("diaodu".equals(type)) {
            List<MdBinEntity> mdblistfrom = systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", binfrom);
            List<MdBinEntity> mdblistto = systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", binto);
            MdBinEntity mdBinEntityfrom = mdblistfrom.get(0);
            MdBinEntity mdBinEntityto = mdblistto.get(0);
            String x0 = mdBinEntityfrom.getXnode();
            String x1 = mdBinEntityto.getXnode();
            int xStep = Integer.parseInt(x1) - Integer.parseInt(x0);
            String y0 = mdBinEntityfrom.getYnode();
            String y1 = mdBinEntityto.getYnode();
            int yStep = Integer.parseInt(y1) - Integer.parseInt(y0);
            String xstepNum = "1";
            String ystepNum = "1";
            String hxstepNum = "1";
            xstepNum = Integer.toString(xStep);
            ystepNum = Integer.toString(yStep);
            if (!"no".equals(startcom) && StringUtil.isNotEmpty(startcom)) {
                hxstepNum = "1";
                System.out.println("startcom,startcom:" + startcom);
                wmsPlcController.run("", startcom, hxstepNum);
            }


            if (y0.equals("01")) {
                System.out.println("1,runx:" + xstepNum);
                wmsPlcController.run("", "runx", xstepNum);
            } else {
                System.out.println("2,runy:" + ystepNum);

                wmsPlcController.run("", "runy", ystepNum);
            }


//        if(xStep>0 && yStep>0){
//            hxstepNum = "1";
//            System.out.println("3,change:"+hxstepNum);
//
//            wmsPlcController.run("","change",hxstepNum);
//        }
            if (!"no".equals(midcom) && StringUtil.isNotEmpty(midcom)) {
                hxstepNum = "1";
                System.out.println("midcom,midcom:" + midcom);
                wmsPlcController.run("", midcom, hxstepNum);
            }

            if (y0.equals("01")) {
                System.out.println("4,runy:" + ystepNum);
                wmsPlcController.run("", "runy", ystepNum);
            } else {
                System.out.println("5,runx:" + xstepNum);

                wmsPlcController.run("", "runx", xstepNum);
            }
            if (!"no".equals(endcom) && StringUtil.isNotEmpty(endcom)) {
                hxstepNum = "1";
                System.out.println("endcom,endcom:" + endcom);
                wmsPlcController.run("", endcom, hxstepNum);
            }
        } else {
            wmsPlcController.runu();

        }
    }

    /**
     * 批量删除仓位定义
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "仓位停用成功";
        try {
            for (String id : ids.split(",")) {
                MdBinEntity mdBin = systemService.getEntity(MdBinEntity.class,
                        id
                );
                mdBin.setTingYong("Y");
                if (wmUtil.checkishavestock("bin", mdBin.getKuWeiBianMa())) {
                    message = "仓位停用成功，但是存在库存";
                    mdBinService.updateEntitie(mdBin);
//					j.setSuccess(false);
//					j.setMsg(message);
//					return j;
                } else {
                    mdBinService.delete(mdBin);
                }
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "仓位停用失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加仓位定义
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(MdBinEntity mdBin, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "仓位定义添加成功";
        try {

            MdBinEntity mdb = null;
            List<MdBinEntity> mdblist = systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
            for (MdBinEntity t : mdblist) {
                if (t.getBinStore().equals(mdBin.getBinStore())) {
                    mdb = t;
                }
            }

//		    MdBinEntity mdBin1 = systemService.findUniqueByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
            if (mdb == null) {
                mdBinService.save(mdBin);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                message = "库位编码或者库位条码已经存在";
                j.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "仓位定义添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新仓位定义
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(MdBinEntity mdBin, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "仓位定义更新成功";
        MdBinEntity t = mdBinService.get(MdBinEntity.class, mdBin.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(mdBin, t);
            mdBinService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "仓位定义更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 仓位定义新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(MdBinEntity mdBin, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(mdBin.getId())) {
            mdBin = mdBinService.getEntity(MdBinEntity.class, mdBin.getId());
            req.setAttribute("mdBinPage", mdBin);
        }
        return new ModelAndView("com/zzjee/md/mdBin-add");
    }

    /**
     * 仓位定义编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(MdBinEntity mdBin, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(mdBin.getId())) {
            mdBin = mdBinService.getEntity(MdBinEntity.class, mdBin.getId());
            req.setAttribute("mdBinPage", mdBin);
        }
        return new ModelAndView("com/zzjee/md/mdBin-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "mdBinController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(MdBinEntity mdBin, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(MdBinEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mdBin, request.getParameterMap());
        List<MdBinEntity> mdBins = this.mdBinService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "仓位定义");
        modelMap.put(NormalExcelConstants.CLASS, MdBinEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("仓位定义列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, mdBins);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(MdBinEntity mdBin, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME, "仓位定义");
        modelMap.put(NormalExcelConstants.CLASS, MdBinEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("仓位定义列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
                List<MdBinEntity> listMdBinEntitys = ExcelImportUtil.importExcel(file.getInputStream(), MdBinEntity.class, params);
                for (MdBinEntity mdBin : listMdBinEntitys) {

                    MdBinEntity mdb = null;
                    List<MdBinEntity> mdblist = systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
                    for (MdBinEntity t : mdblist) {
                        if (t.getKuWeiBianMa().equals(mdBin.getKuWeiBianMa())) {
                            mdb = t;
                        }
                    }
                    if (mdb != null) {
                        MyBeanUtils.copyBeanNotNull2Bean(mdBin, mdb);
                        systemService.saveOrUpdate(mdb);
                    } else {
                        mdBinService.save(mdBin);
                    }
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
    public List<MdBinEntity> list() {
        List<MdBinEntity> listMdBins = mdBinService.getList(MdBinEntity.class);
        return listMdBins;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        MdBinEntity task = mdBinService.get(MdBinEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody MdBinEntity mdBin, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<MdBinEntity>> failures = validator.validate(mdBin);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            mdBinService.save(mdBin);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = mdBin.getId();
        URI uri = uriBuilder.path("/rest/mdBinController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody MdBinEntity mdBin) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<MdBinEntity>> failures = validator.validate(mdBin);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        try {
            mdBinService.saveOrUpdate(mdBin);
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
        mdBinService.deleteEntityById(MdBinEntity.class, id);
    }

    @RequestMapping(params = "addMdBin")
    @ResponseBody
    public void addMdBin() {
        List<MdBinEntity> mdBinEntityList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 30; k++) {
                    MdBinEntity mdBinEntity = new MdBinEntity();
                    String kuweibianma = "A" + String.format("%02d", i) + "-" + String.format("%02d", j) + "-" + String.format("%02d", k);
                    mdBinEntity.setKuWeiBianMa(kuweibianma);
                    mdBinEntity.setKuWeiTiaoMa(kuweibianma);
                    mdBinEntity.setKuWeiMingCheng("A1");
                    mdBinEntity.setKuWeiLeiXing("收货储位");
                    mdBinEntity.setKuWeiShuXing("常温");
                    mdBinEntity.setShangJiaCiXu(String.format("%02d", i) + String.format("%02d", j) + String.format("%02d", k));
                    mdBinEntity.setQuHuoCiXu(String.format("%02d", i) + String.format("%02d", j) + String.format("%02d", k));
                    mdBinEntity.setTiJiDanWei("0");
                    mdBinEntity.setZhongLiangDanWei("5");
                    mdBinEntity.setZuiDaTuoPan("2");
                    mdBinEntity.setTingYong("N");
                    mdBinEntity.setBinStore("BJTZ7");
                    mdBinEntity.setXnode(String.format("%02d", i));
                    mdBinEntity.setYnode(String.format("%02d", j));
                    mdBinEntity.setZnode(String.format("%02d", k));
                    mdBinEntityList.add(mdBinEntity);
                }
            }
        }
        mdBinService.batchSave(mdBinEntityList);

    }
}
