package com.zzjee.wave.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.wave.entity.WaveToDownEntity;
import com.zzjee.wave.entity.WaveToFjEntity;
import com.zzjee.wave.service.WaveToFjServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmQmIEntity;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;

import java.io.OutputStream;

import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/waveToFjController")
public class WaveToFjController extends BaseController {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WaveToFjController.class);

    @Autowired
    private WaveToFjServiceI waveToFjService;
    @Autowired
    private SystemService systemService;


    /**
     * wave_to_fj列表 页面跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/wave/waveToFjList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(WaveToFjEntity waveToFj, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WaveToFjEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, waveToFj, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.waveToFjService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除wave_to_fj
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(WaveToFjEntity waveToFj, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        // 调用systemService的getEntity方法，传入WaveToFjEntity.class和waveToFj对象的id作为参数，获取对应的实体对象，并将其赋值给waveToFj变量
        waveToFj = systemService.getEntity(WaveToFjEntity.class, waveToFj.getId());
        message = "wave_to_fj";
        try {
            waveToFjService.delete(waveToFj);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "wave_to_fj";
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除wave_to_fj
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "wave_to_fj";
        try {
            // 遍历传入的id字符串，以逗号分隔
            for (String id : ids.split(",")) {
                // 根据id获取WaveToFjEntity实体
                WaveToFjEntity waveToFj = systemService.getEntity(WaveToFjEntity.class,
                        id
                );
                waveToFjService.delete(waveToFj);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "wave_to_fj";
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * 添加wave_to_fj
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(WaveToFjEntity waveToFj, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "wave_to_fj添加成功";
        try {
            // 调用waveToFjService的save方法保存waveToFj对象
            waveToFjService.save(waveToFj);
            // 调用systemService的addLog方法记录日志，传入message、Globals.Log_Type_INSERT和Globals.Log_Leavel_INFO作为参数
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            // 如果发生异常，打印异常堆栈信息
            e.printStackTrace();
            message = "wave_to_fj添加失败";
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * 更新wave_to_fj
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(WaveToFjEntity waveToFj, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "wave_to_fj更新成功";
        WaveToFjEntity t = waveToFjService.get(WaveToFjEntity.class, waveToFj.getId());
        try {
            // 将waveToFj对象的属性复制到t对象中，只复制非空属性
            MyBeanUtils.copyBeanNotNull2Bean(waveToFj, t);
            // 保存或更新t对象
            waveToFjService.saveOrUpdate(t);
            // 记录日志，类型为更新，级别为信息
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            // 如果发生异常，打印异常堆栈信息
            e.printStackTrace();
            message = "wave_to_fj更新失败";
            // 抛出一个BusinessException异常，传入异常信息
            throw new BusinessException(e.getMessage());
        }
        // 将message设置为j对象的msg属性
        j.setMsg(message);
        return j;
    }

    /**
     * wave_to_fj新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(WaveToFjEntity waveToFj, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(waveToFj.getId())) {
            waveToFj = waveToFjService.getEntity(WaveToFjEntity.class, waveToFj.getId());
            req.setAttribute("waveToFjPage", waveToFj);
        }
        return new ModelAndView("com/zzjee/wave/waveToFj-add");
    }

    /**
     * wave_to_fj编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(WaveToFjEntity waveToFj, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(waveToFj.getId())) {
            waveToFj = waveToFjService.getEntity(WaveToFjEntity.class, waveToFj.getId());
            req.setAttribute("waveToFjPage", waveToFj);
        }
        return new ModelAndView("com/zzjee/wave/waveToFj-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "waveToFjController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(WaveToFjEntity waveToFj, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(WaveToFjEntity.class, dataGrid);
        // 根据请求参数和实体类属性生成HQL查询语句
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, waveToFj, request.getParameterMap());
        // 根据查询条件获取WaveToFjEntity对象列表
        List<WaveToFjEntity> waveToFjs = this.waveToFjService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "wave_to_fj");
        modelMap.put(NormalExcelConstants.CLASS, WaveToFjEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("wave_to_fj列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        // 将查询结果放入modelMap中
        modelMap.put(NormalExcelConstants.DATA_LIST, waveToFjs);
        // 返回视图名称，用于跳转到导出Excel的页面
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(WaveToFjEntity waveToFj, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        // 设置 Excel 文件名
        modelMap.put(NormalExcelConstants.FILE_NAME, "wave_to_fj");
        // 指定导出的数据实体类类型
        modelMap.put(NormalExcelConstants.CLASS, WaveToFjEntity.class);
        // 设置导出参数，包括标题、作者和描述
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("wave_to_fj列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        // 设置数据列表为空列表，此处应根据实际需求填充数据
        modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
        // 返回视图名称，用于展示或下载 Excel 文件
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
                List<WaveToFjEntity> listWaveToFjEntitys = ExcelImportUtil.importExcel(file.getInputStream(), WaveToFjEntity.class, params);
                for (WaveToFjEntity waveToFj : listWaveToFjEntitys) {
                    waveToFjService.save(waveToFj);
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
    public List<WaveToFjEntity> list() {
        List<WaveToFjEntity> listWaveToFjs = waveToFjService.getList(WaveToFjEntity.class);
        return listWaveToFjs;
    }

    @RequestMapping(value = "/list/tofj", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> list(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "searchstr", required = false) String searchstr,//波次号
                                  @RequestParam(value = "searchstr2", required = false) String searchstr2,//一次容器
                                  @RequestParam(value = "searchstr3", required = false) String searchstr3,//商品编码
                                  @RequestParam(value = "searchstr4", required = false) String searchstr4,//二次容器
                                  @RequestParam(value = "searchstr5", required = false) String searchstr5)//排序方式
    {
        ResultDO D0 = new ResultDO();
        D0.setOK(true);
        String hql = "from WaveToDownEntity where waveId = ?  ";

        List<WaveToFjEntity> listWaveToFjs = new ArrayList<>();
        List<WaveToFjEntity> listWaveToFjsnew = new ArrayList<>();
        if (StringUtil.isEmpty(searchstr5) || "null".equals(searchstr5)) {
            searchstr5 = "asc";
        }
        String orderby = "order by secondRq  " + searchstr5;
        if (StringUtil.isEmpty(searchstr) && StringUtil.isEmpty(searchstr2)) {
            hql = "from WaveToFjEntity  where 1 =  1  " + orderby;
            listWaveToFjs = waveToFjService.findHql(hql);
        }
        if (StringUtil.isNotEmpty(searchstr) && StringUtil.isEmpty(searchstr2)) {
            hql = "from WaveToFjEntity where waveId = ?  " + orderby;
            listWaveToFjs = waveToFjService.findHql(hql, searchstr);
        }
        if (StringUtil.isEmpty(searchstr) && StringUtil.isNotEmpty(searchstr2)) {
            hql = "from WaveToFjEntity where firstRq = ?  " + orderby;
            listWaveToFjs = waveToFjService.findHql(hql, searchstr2);
        }
        if (StringUtil.isNotEmpty(searchstr) && StringUtil.isNotEmpty(searchstr2)) {
            hql = "from WaveToFjEntity where waveId = ? and  firstRq = ?" + orderby;
            listWaveToFjs = waveToFjService.findHql(hql, searchstr, searchstr2);
        }

//		List<WaveToFjEntity> listWaveToFjs=waveToFjService.getList(WaveToFjEntity.class);
        String omnoticeid = "1";
        String siji = "";
        String chehao = "";
        System.out.println("11111searchstr3=" + searchstr3);

        for (WaveToFjEntity t : listWaveToFjs) {
            System.out.println("searchstr4=" + searchstr4);
            if (StringUtil.isNotEmpty(searchstr4)) {
                if (!StringUtil.strPos(t.getSecondRq(), searchstr4)) {
                    continue;
                }
            }

            if (StringUtil.isNotEmpty(searchstr3)) {
                System.out.println("t.getGoodsId()=" + t.getGoodsId());
                System.out.println("searchstr3=" + searchstr3);
                try {
                    if (!(StringUtil.strPos(t.getGoodsId(), searchstr3) || StringUtil.strPos(t.getShpTiaoMa(), searchstr3))) {
                        continue;
                    }
                } catch (Exception e) {

                }

            }
            WaveToFjEntity t1 = new WaveToFjEntity();
            try {
                MyBeanUtils.copyBean2Bean(t1, t);

            } catch (Exception e) {

            }
            try {
                if (omnoticeid.equals(t.getOmNoticeId())) {
                    t1.setBy1(siji);//司机
                    t1.setBy2(chehao);//车号
                } else {
                    WmOmNoticeHEntity wmom = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", t.getOmNoticeId());
                    omnoticeid = t.getOmNoticeId();
                    siji = wmom.getReMember();
                    chehao = wmom.getReCarno();
                    t1.setBy1(siji);//司机
                    t1.setBy2(chehao);//车号
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            listWaveToFjsnew.add(t1);
        }

        D0.setObj(listWaveToFjsnew);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        WaveToFjEntity task = waveToFjService.get(WaveToFjEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestParam String waveToFjstr, UriComponentsBuilder uriBuilder) {
        ResultDO D0 = new ResultDO();
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        WaveToFjEntity waveToFj = (WaveToFjEntity) JSONHelper.json2Object(waveToFjstr, WaveToFjEntity.class);
        //保存
        try {
            String omnoticeid = "";
            WmOmQmIEntity wmOmQmI = systemService.getEntity(
                    WmOmQmIEntity.class, waveToFj.getId());
            if (wmOmQmI != null && "H".equals(wmOmQmI.getBinSta())) {
                wmOmQmI.setBinSta("Y");
                wmOmQmI.setSecondRq(waveToFj.getSecondRq());
                wmOmQmI.setUpdateBy(waveToFj.getCreateBy());//分拣人
                systemService.saveOrUpdate(wmOmQmI);
                String hql = "From WmOmQmIEntity where omNoticeId = ? and  binSta = ?";
                List<WmOmQmIEntity> listom = systemService.findHql(hql, wmOmQmI.getOmNoticeId(), "H");
                omnoticeid = wmOmQmI.getOmNoticeId();
                for (WmOmQmIEntity tom : listom) {
                    tom.setSecondRq(waveToFj.getSecondRq());
                    systemService.saveOrUpdate(tom);
                }
                WmOmNoticeHEntity wmom = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", omnoticeid);
                wmom.setOmSta("操作中");

                systemService.updateEntitie(wmom);
                D0.setOK(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            D0.setOK(false);

            return new ResponseEntity(D0, HttpStatus.OK);
        }
        return new ResponseEntity(D0, HttpStatus.OK);
    }

    @RequestMapping(value = "/jsonfj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody WaveToFjEntity waveToFj) {
        ResultDO D0 = new ResultDO();
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        //保存
        try {
            WmOmQmIEntity wmOmQmI = systemService.getEntity(
                    WmOmQmIEntity.class, waveToFj.getId());
            if (wmOmQmI != null && "H".equals(wmOmQmI.getBinSta())) {
                wmOmQmI.setBinSta("Y");
                systemService.saveOrUpdate(wmOmQmI);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(waveToFj, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        waveToFjService.deleteEntityById(WaveToFjEntity.class, id);
    }
}
