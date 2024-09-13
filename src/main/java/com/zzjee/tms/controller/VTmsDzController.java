package com.zzjee.tms.controller;

import com.alibaba.fastjson.JSONArray;
import com.zzjee.tms.entity.VTmsDzEntity;
import com.zzjee.tms.service.VTmsDzServiceI;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
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
 * @Title: Controller
 * @Description: v_tms_dz
 * @author onlineGenerator
 * @date 2018-08-08 01:31:13
 * @version V1.0
 * 控制器类，用于处理与v_tms_dz相关的请求
 */
@Api(value="VTmsDz",description="v_tms_dz",tags="vTmsDzController")
@Controller
@RequestMapping("/vTmsDzController")
public class VTmsDzController extends BaseController {
	/**
	 * 日志记录器
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VTmsDzController.class);

	/**
	 * 注入vTmsDz服务接口
	 */
	@Autowired
	private VTmsDzServiceI vTmsDzService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * v_tms_dz列表 页面跳转
	 * @param request HTTP请求
	 * @return 返回ModelAndView对象
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/vTmsDzList");
	}

	/**
	 * 处理easyui AJAX请求数据
	 * @param vTmsDz 实体对象
	 * @param request HTTP请求
	 * @param response HTTP响应
	 * @param dataGrid 数据网格
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(VTmsDzEntity vTmsDz, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(VTmsDzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vTmsDz, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			// 抛出异常
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.vTmsDzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除v_tms_dz
	 * @param vTmsDz 实体对象
	 * @param request HTTP请求
	 * @return 返回AjaxJson对象
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(VTmsDzEntity vTmsDz, HttpServletRequest request) {
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		vTmsDz = systemService.getEntity(VTmsDzEntity.class, vTmsDz.getId());
		message = "v_tms_dz删除成功";
		try{
			vTmsDzService.delete(vTmsDz);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			// 抛出异常
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回AjaxJson对象
		return j;
	}

	/**
	 * 批量删除 v_tms_dz记录
	 * @param ids 记录ID字符串
	 * @param request HTTP请求
	 * @return 返回AjaxJson对象
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		message = "v_tms_dz删除成功";
		try{
			for(String id:ids.split(",")){
				VTmsDzEntity vTmsDz = systemService.getEntity(VTmsDzEntity.class, id);
				vTmsDzService.delete(vTmsDz);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			// 抛出异常
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回AjaxJson对象
		return j;
	}


	/**
	 * 添加v_tms_dz记录
	 * @param vTmsDz 实体对象
	 * @param request HTTP请求
	 * @return 返回AjaxJson对象
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(VTmsDzEntity vTmsDz, HttpServletRequest request) {
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		message = "v_tms_dz添加成功";
		try{
			vTmsDzService.save(vTmsDz);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			// 抛出异常
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回AjaxJson对象
		return j;
	}

	/**
	 * 更新v_tms_dz记录
	 * @param vTmsDz 实体对象
	 * @param request HTTP请求
	 * @return 返回AjaxJson对象
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(VTmsDzEntity vTmsDz, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "v_tms_dz更新成功";
		VTmsDzEntity t = vTmsDzService.get(VTmsDzEntity.class, vTmsDz.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vTmsDz, t);
			vTmsDzService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
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
	 * v_tms_dz新增页面跳转功能
	 * @param vTmsDz 实体对象
	 * @param req HTTP请求
	 * @return 返回ModelAndView对象
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(VTmsDzEntity vTmsDz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vTmsDz.getId())) {
			vTmsDz = vTmsDzService.getEntity(VTmsDzEntity.class, vTmsDz.getId());
			req.setAttribute("vTmsDzPage", vTmsDz);
		}
		return new ModelAndView("com/zzjee/tms/vTmsDz-add");
	}
	/**
	 * 跳转到v_tms_dz编辑页面
	 * @param vTmsDz 实体对象
	 * @param req HTTP请求
	 * @return	返回ModelAndView对象
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(VTmsDzEntity vTmsDz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vTmsDz.getId())) {
			vTmsDz = vTmsDzService.getEntity(VTmsDzEntity.class, vTmsDz.getId());
			req.setAttribute("vTmsDzPage", vTmsDz);
		}
		return new ModelAndView("com/zzjee/tms/vTmsDz-update");
	}

	/**
	 * 导入功能跳转
	 * @param  req HTTP请求
	 * @return 返回 ModelAndView对象
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","vTmsDzController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel文件
	 * @param vTmsDz 实体对象
	 * @param request HTTP请求
	 * @param response HTTP响应
	 * @param dataGrid 数据网格
	 * @param modelMap 模型映射
	 * @return JEECG_EXCEL_VIEW返回视图名称
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(VTmsDzEntity vTmsDz, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(VTmsDzEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vTmsDz, request.getParameterMap());
		List<VTmsDzEntity> vTmsDzs = this.vTmsDzService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"v_tms_dz");
		modelMap.put(NormalExcelConstants.CLASS,VTmsDzEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("v_tms_dz列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,vTmsDzs);
		// 返回结果
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

}
