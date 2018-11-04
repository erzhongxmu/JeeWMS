package com.zzjee.wm.controller;
import com.zzjee.wm.entity.WmPlatIoEntity;
import com.zzjee.wm.service.WmPlatIoServiceI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.p3.core.common.utils.DateUtil;
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
 * @Description: 月台进出
 * @author erzhongxmu
 * @date 2017-08-15 23:20:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmPlatIoController")
public class WmPlatIoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmPlatIoController.class);

	@Autowired
	private WmPlatIoServiceI wmPlatIoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 月台进出列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmPlatIoList");
	}
	@RequestMapping(params = "listplan")
	public ModelAndView listplan(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmPlatIoplanList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WmPlatIoEntity wmPlatIo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmPlatIoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmPlatIo, request.getParameterMap());
		try{
		//自定义追加查询条件

		String query_planIndata_begin = DateUtils.date2Str(new Date(),DateUtils.date_sdf);
		Date today = new Date();
		Calendar c = Calendar.getInstance();  
        c.setTime(today);  
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天  
		String query_planIndata_end = DateUtils.date2Str(c.getTime(),DateUtils.date_sdf);;
		if(StringUtil.isNotEmpty(query_planIndata_begin)){
			cq.ge("planIndata", new SimpleDateFormat("yyyy-MM-dd").parse(query_planIndata_begin));
		}
		if(StringUtil.isNotEmpty(query_planIndata_end)){
			cq.le("planIndata", new SimpleDateFormat("yyyy-MM-dd").parse(query_planIndata_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.notEq("platSta", "完成");
		cq.add();
		this.wmPlatIoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagridplan")
	public void datagridplan(WmPlatIoEntity wmPlatIo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmPlatIoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmPlatIo, request.getParameterMap());
		try{
		//自定义追加查询条件

		String query_planIndata_begin = request.getParameter("planIndata_begin1"); ;

		String query_planIndata_end = request.getParameter("planIndata_end2"); ;
		if(StringUtil.isNotEmpty(query_planIndata_begin)){
			cq.ge("planIndata", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(query_planIndata_begin));
		}
		if(StringUtil.isNotEmpty(query_planIndata_end)){
			cq.le("planIndata", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(query_planIndata_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wmPlatIoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除月台进出
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmPlatIoEntity wmPlatIo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmPlatIo = systemService.getEntity(WmPlatIoEntity.class, wmPlatIo.getId());
		message = "月台进出删除成功";
		try{
			wmPlatIoService.delete(wmPlatIo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "月台进出删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除月台进出
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "月台进出删除成功";
		try{
			for(String id:ids.split(",")){
				WmPlatIoEntity wmPlatIo = systemService.getEntity(WmPlatIoEntity.class, 
				id
				);
				wmPlatIoService.delete(wmPlatIo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "月台进出删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加月台进出
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmPlatIoEntity wmPlatIo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "月台进出添加成功";
		try{
			wmPlatIo.setPlatSta("计划");
			wmPlatIoService.save(wmPlatIo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "月台进出添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新月台进出
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmPlatIoEntity wmPlatIo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "月台进出更新成功";
		WmPlatIoEntity t = wmPlatIoService.get(WmPlatIoEntity.class, wmPlatIo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmPlatIo, t);
			wmPlatIoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "月台进出更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新月台释放
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doOutplat")
	@ResponseBody
	public AjaxJson doOutplat(WmPlatIoEntity wmPlatIo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "月台释放成功";
		WmPlatIoEntity t = wmPlatIoService.get(WmPlatIoEntity.class, wmPlatIo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmPlatIo, t);
			t.setOutData(new Date());
			t.setPlatSta("完成");
			wmPlatIoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "月台释放失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新月台占用
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doOnplat")
	@ResponseBody
	public AjaxJson doOnplat(WmPlatIoEntity wmPlatIo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "月台占用成功";
		WmPlatIoEntity t = wmPlatIoService.get(WmPlatIoEntity.class, wmPlatIo.getId());
		String sql = "select count(*) as count from wm_plat_io  wp where  wp.plat_sta = '占用' and wp.plat_id  = '"+t.getPlatId()+"' and    TO_DAYS(wp.in_data) = TO_DAYS(NOW());";
		Map<String, Object> countMap = systemService
				.findOneForJdbc(sql);
		if(countMap!=null&&((Long) countMap.get("count")).intValue() > 0){
			j.setSuccess(false);
			message = "月台正在使用中";
			j.setMsg(message);
			return j;
		}

		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmPlatIo, t);
		    t.setInData(new Date());
		    t.setPlatSta("占用");
			wmPlatIoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "月台占用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 月台进出新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmPlatIoEntity wmPlatIo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmPlatIo.getId())) {
			wmPlatIo = wmPlatIoService.getEntity(WmPlatIoEntity.class, wmPlatIo.getId());
			req.setAttribute("wmPlatIoPage", wmPlatIo);
		}
		return new ModelAndView("com/zzjee/wm/wmPlatIo-add");
	}
	/**
	 * 月台进出编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmPlatIoEntity wmPlatIo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmPlatIo.getId())) {
			wmPlatIo = wmPlatIoService.getEntity(WmPlatIoEntity.class, wmPlatIo.getId());
			req.setAttribute("wmPlatIoPage", wmPlatIo);
		}
		return new ModelAndView("com/zzjee/wm/wmPlatIo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmPlatIoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmPlatIoEntity wmPlatIo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmPlatIoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmPlatIo, request.getParameterMap());
		List<WmPlatIoEntity> wmPlatIos = this.wmPlatIoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"月台进出");
		modelMap.put(NormalExcelConstants.CLASS,WmPlatIoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("月台进出列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmPlatIos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmPlatIoEntity wmPlatIo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"月台进出");
    	modelMap.put(NormalExcelConstants.CLASS,WmPlatIoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("月台进出列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WmPlatIoEntity> listWmPlatIoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmPlatIoEntity.class,params);
				for (WmPlatIoEntity wmPlatIo : listWmPlatIoEntitys) {
					wmPlatIoService.save(wmPlatIo);
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
	public List<WmPlatIoEntity> list() {
		List<WmPlatIoEntity> listWmPlatIos=wmPlatIoService.getList(WmPlatIoEntity.class);
		return listWmPlatIos;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmPlatIoEntity task = wmPlatIoService.get(WmPlatIoEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmPlatIoEntity wmPlatIo, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmPlatIoEntity>> failures = validator.validate(wmPlatIo);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmPlatIoService.save(wmPlatIo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmPlatIo.getId();
		URI uri = uriBuilder.path("/rest/wmPlatIoController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmPlatIoEntity wmPlatIo) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmPlatIoEntity>> failures = validator.validate(wmPlatIo);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmPlatIoService.saveOrUpdate(wmPlatIo);
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
		wmPlatIoService.deleteEntityById(WmPlatIoEntity.class, id);
	}
}
