package com.zzjee.uniapp.controller;
import com.zzjee.uniapp.entity.WmsAppFunctionEntity;
import com.zzjee.uniapp.service.WmsAppFunctionServiceI;
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
 * @Description: APP功能
 * @author onlineGenerator
 * @date 2022-06-13 08:41:27
 * @version V1.0
 */

@Controller
@RequestMapping("/wmsAppFunctionController")
public class WmsAppFunctionController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmsAppFunctionController.class);

	@Autowired
	private WmsAppFunctionServiceI wmsAppFunctionService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * APP功能列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/uniapp/wmsAppFunctionList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(WmsAppFunctionEntity wmsAppFunction,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmsAppFunctionEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmsAppFunction, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wmsAppFunctionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除APP功能
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmsAppFunctionEntity wmsAppFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		// 通过systemService的getEntity方法获取WmsAppFunctionEntity类的实例，并将其赋值给wmsAppFunction变量
		// 参数为WmsAppFunctionEntity.class和wmsAppFunction.getId()，分别表示实体类类型和实体的ID
		wmsAppFunction = systemService.getEntity(WmsAppFunctionEntity.class, wmsAppFunction.getId());
		message = "APP功能删除成功";
		try{
			// 调用wmsAppFunctionService的delete方法，传入wmsAppFunction对象进行删除操作
			wmsAppFunctionService.delete(wmsAppFunction);
			// 调用wmsAppFunctionService的delete方法，传入wmsAppFunction对象进行删除操作
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			// 打印异常堆栈信息
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		// 将message设置为j对象的msg属性
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除APP功能
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "APP功能删除成功";
		try{
			for(String id:ids.split(",")){
				// 根据id获取WmsAppFunctionEntity实例
				WmsAppFunctionEntity wmsAppFunction = systemService.getEntity(WmsAppFunctionEntity.class,
				id
				);
				// 调用wmsAppFunctionService的delete方法删除wmsAppFunction实例
				wmsAppFunctionService.delete(wmsAppFunction);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		 // 将message设置为j对象的msg属性
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加APP功能
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmsAppFunctionEntity wmsAppFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "APP功能添加成功";
		try{
			wmsAppFunctionService.save(wmsAppFunction);
			// 添加日志记录，类型为删除，级别为信息
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		// 将message设置为j对象的msg属性
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新APP功能
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmsAppFunctionEntity wmsAppFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "APP功能更新成功";
		// 根据传入的wmsAppFunction对象的id获取对应的WmsAppFunctionEntity对象t
		WmsAppFunctionEntity t = wmsAppFunctionService.get(WmsAppFunctionEntity.class, wmsAppFunction.getId());
		try {
			// 将wmsAppFunction对象的属性复制到t对象中，只复制非空属性
			MyBeanUtils.copyBeanNotNull2Bean(wmsAppFunction, t);
			wmsAppFunctionService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		// 将message设置为j对象的msg属性
		j.setMsg(message);
		return j;
	}

	/**
	 * APP功能新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmsAppFunctionEntity wmsAppFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmsAppFunction.getId())) {
			wmsAppFunction = wmsAppFunctionService.getEntity(WmsAppFunctionEntity.class, wmsAppFunction.getId());
			req.setAttribute("wmsAppFunctionPage", wmsAppFunction);
		}
		return new ModelAndView("com/zzjee/uniapp/wmsAppFunction-add");
	}

	/**
	 * APP功能编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmsAppFunctionEntity wmsAppFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmsAppFunction.getId())) {
			wmsAppFunction = wmsAppFunctionService.getEntity(WmsAppFunctionEntity.class, wmsAppFunction.getId());
			req.setAttribute("wmsAppFunctionPage", wmsAppFunction);
		}
		return new ModelAndView("com/zzjee/uniapp/wmsAppFunction-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmsAppFunctionController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmsAppFunctionEntity wmsAppFunction,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmsAppFunctionEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmsAppFunction, request.getParameterMap());
		List<WmsAppFunctionEntity> wmsAppFunctions = this.wmsAppFunctionService.getListByCriteriaQuery(cq,false);
		// 设置导出的Excel文件名
		modelMap.put(NormalExcelConstants.FILE_NAME,"APP功能");
		// 设置导出的实体类类型
		modelMap.put(NormalExcelConstants.CLASS,WmsAppFunctionEntity.class);
		// 设置导出参数，包括标题、导出人和导出信息
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("APP功能列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		// 将查询到的数据列表放入modelMap中
		modelMap.put(NormalExcelConstants.DATA_LIST,wmsAppFunctions);
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
	public String exportXlsByT(WmsAppFunctionEntity wmsAppFunction,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		// 设置导出的Excel文件名
    	modelMap.put(NormalExcelConstants.FILE_NAME,"APP功能");
		// 设置导出的数据实体类
    	modelMap.put(NormalExcelConstants.CLASS,WmsAppFunctionEntity.class);
		// 设置导出参数，包括标题、导出人和导出信息
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("APP功能列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
		// 设置导出的数据列表，这里使用空列表作为示例
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		// 返回视图名称，用于跳转到导出Excel的页面
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 遍历文件映射
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				// 使用ExcelImportUtil工具类导入Excel文件，并将结果转换为WmsAppFunctionEntity列表
				List<WmsAppFunctionEntity> listWmsAppFunctionEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmsAppFunctionEntity.class,params);
				// 遍历导入的实体列表，并保存到数据库
				for (WmsAppFunctionEntity wmsAppFunction : listWmsAppFunctionEntitys) {
					wmsAppFunctionService.save(wmsAppFunction);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				// 如果导入过程中发生异常，设置导入失败的提示信息，并记录错误日志
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				// 最后关闭文件输入流
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
	public List<WmsAppFunctionEntity> list() {
		List<WmsAppFunctionEntity> listWmsAppFunctions=wmsAppFunctionService.getList(WmsAppFunctionEntity.class);
		return listWmsAppFunctions;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmsAppFunctionEntity task = wmsAppFunctionService.get(WmsAppFunctionEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmsAppFunctionEntity wmsAppFunction, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmsAppFunctionEntity>> failures = validator.validate(wmsAppFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		//保存
		try{
			wmsAppFunctionService.save(wmsAppFunction);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmsAppFunction.getId();
		URI uri = uriBuilder.path("/rest/wmsAppFunctionController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmsAppFunctionEntity wmsAppFunction) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmsAppFunctionEntity>> failures = validator.validate(wmsAppFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		//保存
		try{
			wmsAppFunctionService.saveOrUpdate(wmsAppFunction);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	/**
	 * 使用Spring MVC注解定义一个处理HTTP DELETE请求的方法
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		// 调用服务层的方法，根据id删除对应的实体记录
		wmsAppFunctionService.deleteEntityById(WmsAppFunctionEntity.class, id);
	}
}
