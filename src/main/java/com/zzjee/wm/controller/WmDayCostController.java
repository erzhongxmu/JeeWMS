package com.zzjee.wm.controller;
import com.zzjee.wm.entity.WmDayCostEntity;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import com.zzjee.wm.service.WmDayCostServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.wmUtil;
import org.apache.ibatis.jdbc.Null;
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
 * @Title: Controller  
 * @Description: 费用维护
 * @author erzhongxmu
 * @date 2017-10-15 15:30:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmDayCostController")
public class WmDayCostController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmDayCostController.class);

	@Autowired
	private WmDayCostServiceI wmDayCostService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 费用维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmDayCostList");
	}


	@RequestMapping(params = "listqf")
	public ModelAndView listqf(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmDayCostqfList");
	}
	@RequestMapping(params = "listsk")
	public ModelAndView listsk(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmDayCostskList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */




	@RequestMapping(params = "datagridqf")
	public void datagridqf(WmDayCostEntity wmDayCost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmDayCostEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmDayCost, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_costData_begin = request.getParameter("costData_begin");
			String query_costData_end = request.getParameter("costData_end");
			if(StringUtil.isNotEmpty(query_costData_begin)){
				cq.ge("costData", new SimpleDateFormat("yyyy-MM-dd").parse(query_costData_begin));
			}
			if(StringUtil.isNotEmpty(query_costData_end)){
				cq.le("costData", new SimpleDateFormat("yyyy-MM-dd").parse(query_costData_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}
		cq.eq("costJs","N");
		cq.add();
		this.wmDayCostService.getDataGridReturn(cq, true);
		try {


			List<WmDayCostEntity> resultold = dataGrid.getResults();
			Double dayCostYj = 0.0000;
			Double dayCostBhs = 0.0000;
			Double dayCostSe = 0.0000;
			Double dayCostHsj = 0.0000;
			String sdayCostYj = null;
			String sdayCostBhs = null;
			String sdayCostSe = null;
			String sdayCostHsj = null;
			for (WmDayCostEntity wmDayCostold : resultold) {
				dayCostYj = dayCostYj + Double.parseDouble(wmDayCostold.getDayCostYj());
				dayCostBhs = dayCostBhs + Double.parseDouble(wmDayCostold.getDayCostBhs());
				dayCostSe = dayCostSe + Double.parseDouble(wmDayCostold.getDayCostSe());
				dayCostHsj = dayCostHsj + Double.parseDouble(wmDayCostold.getDayCostHsj());
			}
			DecimalFormat df=new DecimalFormat(".##");

			sdayCostYj = df.format(dayCostYj);
			sdayCostBhs = df.format(dayCostBhs);
			sdayCostSe = df.format(dayCostSe);
			sdayCostHsj = df.format(dayCostHsj);
			dataGrid.setFooter("costName:合计,dayCostYj:"+sdayCostYj+",dayCostBhs:"+sdayCostBhs+",dayCostSe:"+sdayCostSe+",dayCostHsj:"+sdayCostHsj+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridsk")
	public void datagridsk(WmDayCostEntity wmDayCost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmDayCostEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmDayCost, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_costData_begin = request.getParameter("costData_begin");
			String query_costData_end = request.getParameter("costData_end");
			if(StringUtil.isNotEmpty(query_costData_begin)){
				cq.ge("costData", new SimpleDateFormat("yyyy-MM-dd").parse(query_costData_begin));
			}
			if(StringUtil.isNotEmpty(query_costData_end)){
				cq.le("costData", new SimpleDateFormat("yyyy-MM-dd").parse(query_costData_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}
		cq.eq("costJs","Y");
		cq.add();
		this.wmDayCostService.getDataGridReturn(cq, true);
		try {


			List<WmDayCostEntity> resultold = dataGrid.getResults();
			Double dayCostYj = 0.0000;
			Double dayCostBhs = 0.0000;
			Double dayCostSe = 0.0000;
			Double dayCostHsj = 0.0000;
			String sdayCostYj = null;
			String sdayCostBhs = null;
			String sdayCostSe = null;
			String sdayCostHsj = null;
			for (WmDayCostEntity wmDayCostold : resultold) {
				dayCostYj = dayCostYj + Double.parseDouble(wmDayCostold.getDayCostYj());
				dayCostBhs = dayCostBhs + Double.parseDouble(wmDayCostold.getDayCostBhs());
				dayCostSe = dayCostSe + Double.parseDouble(wmDayCostold.getDayCostSe());
				dayCostHsj = dayCostHsj + Double.parseDouble(wmDayCostold.getDayCostHsj());
			}
			DecimalFormat df=new DecimalFormat(".##");

			sdayCostYj = df.format(dayCostYj);
			sdayCostBhs = df.format(dayCostBhs);
			sdayCostSe = df.format(dayCostSe);
			sdayCostHsj = df.format(dayCostHsj);
			dataGrid.setFooter("costName:合计,dayCostYj:"+sdayCostYj+",dayCostBhs:"+sdayCostBhs+",dayCostSe:"+sdayCostSe+",dayCostHsj:"+sdayCostHsj+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "datagrid")
	public void datagrid(WmDayCostEntity wmDayCost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmDayCostEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmDayCost, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_costData_begin = request.getParameter("costData_begin");
		String query_costData_end = request.getParameter("costData_end");
		if(StringUtil.isNotEmpty(query_costData_begin)){
			cq.ge("costData", new SimpleDateFormat("yyyy-MM-dd").parse(query_costData_begin));
		}
		if(StringUtil.isNotEmpty(query_costData_end)){
			cq.le("costData", new SimpleDateFormat("yyyy-MM-dd").parse(query_costData_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}
		cq.add();
		this.wmDayCostService.getDataGridReturn(cq, true);
		try {
			

		List<WmDayCostEntity> resultold = dataGrid.getResults();
		Double dayCostYj = 0.0000; 
		Double dayCostBhs = 0.0000; 
		Double dayCostSe = 0.0000; 
		Double dayCostHsj = 0.0000; 
		String sdayCostYj = null; 
		String sdayCostBhs = null; 
		String sdayCostSe = null; 
		String sdayCostHsj = null; 
		for (WmDayCostEntity wmDayCostold : resultold) {
			dayCostYj = dayCostYj + Double.parseDouble(wmDayCostold.getDayCostYj());
			dayCostBhs = dayCostBhs + Double.parseDouble(wmDayCostold.getDayCostBhs());
			dayCostSe = dayCostSe + Double.parseDouble(wmDayCostold.getDayCostSe());
			dayCostHsj = dayCostHsj + Double.parseDouble(wmDayCostold.getDayCostHsj());
		} 
		DecimalFormat df=new DecimalFormat(".##");

		sdayCostYj = df.format(dayCostYj);
		sdayCostBhs = df.format(dayCostBhs);
		sdayCostSe = df.format(dayCostSe);
		sdayCostHsj = df.format(dayCostHsj);
		dataGrid.setFooter("costName:合计,dayCostYj:"+sdayCostYj+",dayCostBhs:"+sdayCostBhs+",dayCostSe:"+sdayCostSe+",dayCostHsj:"+sdayCostHsj+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除费用维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmDayCostEntity wmDayCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmDayCost = systemService.getEntity(WmDayCostEntity.class, wmDayCost.getId());
		message = "费用维护删除成功";
		try{
			wmDayCostService.delete(wmDayCost);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "费用维护删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除费用维护
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "费用维护删除成功";
		try{
			for(String id:ids.split(",")){
				WmDayCostEntity wmDayCost = systemService.getEntity(WmDayCostEntity.class, 
				id
				);
				wmDayCostService.delete(wmDayCost);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "费用维护删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加费用维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmDayCostEntity wmDayCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "费用维护添加成功";
		try{
			wmDayCostService.save(wmDayCost);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "费用维护添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新费用维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmDayCostEntity wmDayCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "费用维护更新成功";
		WmDayCostEntity t = wmDayCostService.get(WmDayCostEntity.class, wmDayCost.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmDayCost, t);
			wmDayCostService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "费用维护更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新费用维护
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "dopljq")
	@ResponseBody
	public AjaxJson dopljq(WmDayCostEntity wmDayCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "费用结清成功";
		WmDayCostEntity t = wmDayCostService.get(WmDayCostEntity.class, request.getParameter("id"));
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmDayCost, t);
			t.setCostJs("Y");
			wmDayCostService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "费用结清失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	@RequestMapping(params = "doplfjq")
	@ResponseBody
	public AjaxJson doplfjq(WmDayCostEntity wmDayCost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "费用反结清成功";
		WmDayCostEntity t = wmDayCostService.get(WmDayCostEntity.class, request.getParameter("id"));
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmDayCost, t);
			t.setCostJs("N");
			wmDayCostService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "费用反结清失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 费用维护新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmDayCostEntity wmDayCost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmDayCost.getId())) {
			wmDayCost = wmDayCostService.getEntity(WmDayCostEntity.class, wmDayCost.getId());
			req.setAttribute("wmDayCostPage", wmDayCost);
		}
		return new ModelAndView("com/zzjee/wm/wmDayCost-add");
	}
	/**
	 * 费用维护编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmDayCostEntity wmDayCost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmDayCost.getId())) {
			wmDayCost = wmDayCostService.getEntity(WmDayCostEntity.class, wmDayCost.getId());
			req.setAttribute("wmDayCostPage", wmDayCost);
		}
		return new ModelAndView("com/zzjee/wm/wmDayCost-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmDayCostController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmDayCostEntity wmDayCost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmDayCostEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmDayCost, request.getParameterMap());
		List<WmDayCostEntity> wmDayCosts = this.wmDayCostService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"费用维护");
		modelMap.put(NormalExcelConstants.CLASS,WmDayCostEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("费用维护列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmDayCosts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmDayCostEntity wmDayCost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"费用维护");
    	modelMap.put(NormalExcelConstants.CLASS,WmDayCostEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("费用维护列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
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
				List<WmDayCostEntity> listWmDayCostEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmDayCostEntity.class,params);
				for (WmDayCostEntity wmDayCost : listWmDayCostEntitys) {
					wmDayCostService.save(wmDayCost);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
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
	public List<WmDayCostEntity> list() {
		List<WmDayCostEntity> listWmDayCosts=wmDayCostService.getList(WmDayCostEntity.class);
		return listWmDayCosts;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmDayCostEntity task = wmDayCostService.get(WmDayCostEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmDayCostEntity wmDayCost, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmDayCostEntity>> failures = validator.validate(wmDayCost);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmDayCostService.save(wmDayCost);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmDayCost.getId();
		URI uri = uriBuilder.path("/rest/wmDayCostController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmDayCostEntity wmDayCost) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmDayCostEntity>> failures = validator.validate(wmDayCost);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmDayCostService.saveOrUpdate(wmDayCost);
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
		wmDayCostService.deleteEntityById(WmDayCostEntity.class, id);
	}
}
