package com.zzjee.sys.controller;
import com.zzjee.sys.entity.SysParaEntity;
import com.zzjee.sys.service.SysParaServiceI;
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
 * @Title: Controller  
 * @Description: 全局参数
 * @author onlineGenerator
 * @date 2018-10-28 23:04:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sysParaController")
public class SysParaController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SysParaController.class);

	@Autowired
	private SysParaServiceI sysParaService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 全局参数列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/sys/sysParaList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request 请求
	 * @param response 响应
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SysParaEntity sysPara,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SysParaEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sysPara, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sysParaService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除全局参数
	 * @return j
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SysParaEntity sysPara, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sysPara = systemService.getEntity(SysParaEntity.class, sysPara.getId());
		message = "全局参数删除成功";
		try{
			sysParaService.delete(sysPara);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "全局参数删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除全局参数
	 * @return j
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "全局参数删除成功";
		try{
			for(String id:ids.split(",")){
				SysParaEntity sysPara = systemService.getEntity(SysParaEntity.class, 
				id
				);
				sysParaService.delete(sysPara);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "全局参数删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加全局参数
	 * @param sysPara
	 * @param request
	 * @return j
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(SysParaEntity sysPara, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "全局参数添加成功";
		try{
			sysParaService.save(sysPara);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "全局参数添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新全局参数
	 * @param sysPara
	 * @param request
	 * @return j
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SysParaEntity sysPara, HttpServletRequest request) {
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		message = "全局参数更新成功";
		SysParaEntity t = sysParaService.get(SysParaEntity.class, sysPara.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sysPara, t);
			sysParaService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			// 抛出异常
			e.printStackTrace();
			message = "全局参数更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回AjaxJson对象
		return j;
	}

	/**
	 * 全局参数新增页面跳转
	 * @param req 请求
	 * @return ModelAndView
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(SysParaEntity sysPara, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sysPara.getId())) {
			sysPara = sysParaService.getEntity(SysParaEntity.class, sysPara.getId());
			req.setAttribute("sysParaPage", sysPara);
		}
		return new ModelAndView("com/zzjee/sys/sysPara-add");
	}
	/**
	 * 全局参数编辑页面跳转
	 * @param req 请求
	 * @param sysPara 实体
	 * @return ModelAndView
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SysParaEntity sysPara, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sysPara.getId())) {
			sysPara = sysParaService.getEntity(SysParaEntity.class, sysPara.getId());
			req.setAttribute("sysParaPage", sysPara);
		}
		return new ModelAndView("com/zzjee/sys/sysPara-update");
	}
	
	/**
	 * 导入功能跳转
	 * @param req 请求
	 * @return ModelAndView
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","sysParaController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * @param request 请求
	 * @param response 响应
	 * @return JEECG_EXCEL_VIEW
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(SysParaEntity sysPara,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(SysParaEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sysPara, request.getParameterMap());
		List<SysParaEntity> sysParas = this.sysParaService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"全局参数");
		modelMap.put(NormalExcelConstants.CLASS,SysParaEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("全局参数列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,sysParas);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel模板，导出SysParaEntity数据到Excel文件
	 * @param request HttpServletRequest对象，提供对当前HTTP请求的访问
	 * @param sysPara SysParaEntity对象，用于查询或过滤要导出的数据.
	 * @param response HttpServletResponse对象，用于发送响应到客户端.
	 * @param dataGrid DataGrid对象，可能包含分页和排序信息.
	 * @param modelMap ModelMap对象，用于将模型属性放入model，以供视图使用.
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(SysParaEntity sysPara,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"全局参数");
    	modelMap.put(NormalExcelConstants.CLASS,SysParaEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("全局参数列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		//创建对象
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 遍历fileMap
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取上传文件对象
			MultipartFile file = entity.getValue();
			// 创建导入参数对象，设置标题行、头部行、是否需要保存等参数
			ImportParams params = new ImportParams();
			params.setTitleRows(2);	// 设置标题行有两行
			params.setHeadRows(1);	// 设置头部行有一行
			params.setNeedSave(true); // 表示导入的数据需要被保存
			try {
				List<SysParaEntity> listSysParaEntitys = ExcelImportUtil.importExcel(file.getInputStream(),SysParaEntity.class,params);
				for (SysParaEntity sysPara : listSysParaEntitys) {
					sysParaService.save(sysPara);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				// 如果在导入过程中出现异常，则设置AjaxJson对象的消息，表示文件导入失败
				j.setMsg("文件导入失败！");
				// 记录异常信息，便于后续问题排查
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					// 尝试关闭文件流，防止资源泄露
					file.getInputStream().close();
				} catch (IOException e) {
					// 抛出异常
					e.printStackTrace();
				}
			}
		}
		// 返回结果
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<SysParaEntity> list() {
		List<SysParaEntity> listSysParas=sysParaService.getList(SysParaEntity.class);
		// 返回列表
		return listSysParas;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		SysParaEntity task = sysParaService.get(SysParaEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}
	/**
	 * 控制器方法用于创建系统参数实体（SysParaEntity）。
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody SysParaEntity sysPara, UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，确保SysParaEntity对象符合预定义的约束规则。
		// 如果校验失败，获取所有校验失败的信息集合。
		Set<ConstraintViolation<SysParaEntity>> failures = validator.validate(sysPara);
		if (!failures.isEmpty()) {
			// 捕获并处理任何可能发生的异常，这里只是简单地打印堆栈跟踪
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		try{
			// 调用服务层的save方法来保存SysParaEntity对象到数据库。
			sysParaService.save(sysPara);
		} catch (Exception e) {
			// 捕获并处理任何可能发生的异常，这里只是打印堆栈跟踪
			e.printStackTrace();
			// 返回一个没有内容的响应，HTTP状态码为204（NO_CONTENT）
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = sysPara.getId();
		URI uri = uriBuilder.path("/rest/sysParaController/" + id).build().toUri();
		// 设置Location头，指向新创建资源的位置
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		// 返回一个表示资源已创建的响应，HTTP状态码为201（CREATED），并设置Location头。
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody SysParaEntity sysPara) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<SysParaEntity>> failures = validator.validate(sysPara);
		// 判断failures是否为空
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		// 尝试保存或更新实体
		try{
			// 调用服务层的save方法来保存或更新SysParaEntity对象到数据库。
			sysParaService.saveOrUpdate(sysPara);
		} catch (Exception e) {
			// 抛出异常
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		// 根据Restful约定，返回204 No Content状态码，表示请求已成功但没有返回数据。
		// 也可以选择返回200 OK状态码。
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	/**
	 * 删除指定ID的系统参数实体。
	 * @param id 要删除的实体的唯一标识符
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		// 删除具有给定ID的系统参数实体
		sysParaService.deleteEntityById(SysParaEntity.class, id);
	}
}
