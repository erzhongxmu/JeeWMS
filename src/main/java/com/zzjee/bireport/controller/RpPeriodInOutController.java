package com.zzjee.bireport.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zzjee.bireport.entity.RpPeriodInOutEntity;
import com.zzjee.bireport.service.RpPeriodInOutServiceI;
import com.zzjee.wmutil.wmUtil;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 期间出货统计
 * @date 2019-01-17 12:55:46
 */
@Controller
@RequestMapping("/rpPeriodInOutController")
public class RpPeriodInOutController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(RpPeriodInOutController.class);
    @Autowired
    private RpPeriodInOutServiceI rpPeriodInOutService;
    /**
     * 期间出货统计列表 页面跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/zzjee/bireport/rpPeriodInOutList");
    }

    /**
     * easyui 处理AJAX请求以返回RpPeriodInOutEntity数据列表的datagrid
     *
     * @param rpPeriodInOut
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(RpPeriodInOutEntity rpPeriodInOut, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        rpPeriodInOut.setCreateDate(null);
        //创建CriteriaQuery对象，用于构建数据库查询
        CriteriaQuery cq = new CriteriaQuery(RpPeriodInOutEntity.class, dataGrid);
        //查询条件组装器
        try {
            //自定义追加查询条件
            //从请求中获取日期范围参数
            String query_datePeriod_begin = request.getParameter("createDate1_begin");
            String query_datePeriod_end = request.getParameter("createDate1_end");
            //打印日期范围参数
            System.out.println("query_datePeriod_begin:"+query_datePeriod_begin+" query_datePeriod_end:"+query_datePeriod_end);
            try {
                if (!StringUtil.isNotEmpty(query_datePeriod_begin)) {
                    query_datePeriod_begin = "2018-01-01";
                }
                if (!StringUtil.isNotEmpty(query_datePeriod_end)) {
                    query_datePeriod_end = "2099-01-01";
                }
                wmUtil.genrp(query_datePeriod_begin, query_datePeriod_end, ResourceUtil.getSessionUserName().getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            rpPeriodInOut.setCreateDate(null);
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpPeriodInOut, request.getParameterMap());
            cq.eq("username", ResourceUtil.getSessionUserName().getUserName());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.rpPeriodInOutService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 导出excel
     *
     * @param rpPeriodInOut
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(RpPeriodInOutEntity rpPeriodInOut, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        //自定义追加查询条件
        //从请求中获取日期范围参数
        String query_datePeriod_begin = request.getParameter("createDate1_begin");
        String query_datePeriod_end = request.getParameter("createDate1_end");
        try {
            //验证并设置默认日期范围
            if (!StringUtil.isNotEmpty(query_datePeriod_begin)) {
                query_datePeriod_begin = "2018-01-01";
            }
            if (!StringUtil.isNotEmpty(query_datePeriod_end)) {
                query_datePeriod_end = "2099-01-01";
            }
            wmUtil.genrp(query_datePeriod_begin, query_datePeriod_end, ResourceUtil.getSessionUserName().getUserName());
        } catch (Exception e) {
        }
        rpPeriodInOut.setCreateDate(null);
        rpPeriodInOut.setUsername(ResourceUtil.getSessionUserName().getUserName());
        //构建查询并获取数据
        CriteriaQuery cq = new CriteriaQuery(RpPeriodInOutEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpPeriodInOut, request.getParameterMap());

        //设置Excel导出参数
        List<RpPeriodInOutEntity> rpPeriodInOuts = this.rpPeriodInOutService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "期间出货统计");
        modelMap.put(NormalExcelConstants.CLASS, RpPeriodInOutEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("期间出货统计列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, rpPeriodInOuts);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }


}
