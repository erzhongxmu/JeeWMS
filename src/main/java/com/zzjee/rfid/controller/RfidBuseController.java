package com.zzjee.rfid.controller;
import com.zzjee.rfid.entity.RfidBuseEntity;
import com.zzjee.rfid.service.RfidBuseServiceI;
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
 * @Description: RFID表
 * @author onlineGenerator
 * @date 2020-06-19 06:54:21
 * @version V1.0
 *
 */
@Controller
//用于映射控制器的方法到特定的 URL
@RequestMapping("/rfidBuseController")
public class RfidBuseController extends BaseController {
	/**
	 * Logger for this class
	 */
	//记录日志信息
	private static final Logger logger = Logger.getLogger(RfidBuseController.class);
    //记录日志信息
	@Autowired
	private RfidBuseServiceI rfidBuseService;
	/**使用 @Autowired 进行自动装配，
	 * 注入一个 SystemService 服务对象
	 */
	@Autowired
	private SystemService systemService;
	/**使用 @Autowired 注解的实例，
	 * 用于自动注入一个 Validator 对象
	 */
	@Autowired
	private Validator validator;

	/**
	 * RFID表列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")    //处理HTTP GET请
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/rfid/rfidBuseList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RfidBuseEntity rfidBuse,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RfidBuseEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rfidBuse, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_createDate_begin = request.getParameter("createDate_begin");
		String query_createDate_end = request.getParameter("createDate_end");
		if(StringUtil.isNotEmpty(query_createDate_begin)){
			cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
		}
		if(StringUtil.isNotEmpty(query_createDate_end)){
			cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.rfidBuseService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除RFID表
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RfidBuseEntity rfidBuse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//获取要删除的RfidBuseEntity实体
		rfidBuse = systemService.getEntity(RfidBuseEntity.class, rfidBuse.getId());
		message = "RFID表删除成功";
		//尝试执行删除操作，将记录设置为"del"状态，更新数据并记录日志
		try{
			rfidBuse.setBpmStatus("del");
			rfidBuseService.updateEntitie(rfidBuse);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "RFID表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除RFID表
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "RFID表删除成功";
		 //尝试执行批量删除操作，将记录设置为"del"状态，更新数据并记录日志
		try{
			for(String id:ids.split(",")){
				//获取要删除的RfidBuseEntity实体
				RfidBuseEntity rfidBuse = systemService.getEntity(RfidBuseEntity.class, id);
 				rfidBuse.setBpmStatus("del");
				//更新实体数据，将其状态更新为删除标记
				rfidBuseService.updateEntitie(rfidBuse);
				//记录操作日志
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "RFID表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加RFID表
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RfidBuseEntity rfidBuse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "RFID表添加成功";
		//尝试执行添加操作，保存RfidBuseEntity实体并记录日志
		try{
			//保存RfidBuseEntity实体
			rfidBuseService.save(rfidBuse);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "RFID表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新RFID表
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RfidBuseEntity rfidBuse, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "RFID表更新成功";
		//获取要更新的RFidBuseEntity实体
		RfidBuseEntity t = rfidBuseService.get(RfidBuseEntity.class, rfidBuse.getId());
		try {
			//复制rfidBuse实体的非空属性到t实体
			MyBeanUtils.copyBeanNotNull2Bean(rfidBuse, t);
			//保存或更新t实体
			rfidBuseService.saveOrUpdate(t);
			//记录操作日志
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "RFID表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * RFID表新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")    //注解处理HTTP GET请求，参数为"goAdd"
	//接收RfidBuseEntity实体类和HttpServletRequest对象
	public ModelAndView goAdd(RfidBuseEntity rfidBuse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rfidBuse.getId())) {
			//从RFID表中获取对应ID的RFidBuseEntity实体
			rfidBuse = rfidBuseService.getEntity(RfidBuseEntity.class, rfidBuse.getId());
			//将RFidBuseEntity实体设置到请求的属性中
			req.setAttribute("rfidBusePage", rfidBuse);
		}
		return new ModelAndView("com/zzjee/rfid/rfidBuse-add");
	}

	/**
	 * RFID表编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")    //注解处理HTTP GET请求，参数为"goUpdate"
	public ModelAndView goUpdate(RfidBuseEntity rfidBuse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rfidBuse.getId())) {
			//从RFID表中获取对应ID的RFidBuseEntity实体
			rfidBuse = rfidBuseService.getEntity(RfidBuseEntity.class, rfidBuse.getId());
			//将RFidBuseEntity实体设置到请求的属性中
			req.setAttribute("rfidBusePage", rfidBuse);
		}
		return new ModelAndView("com/zzjee/rfid/rfidBuse-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		//接收HttpServletRequest对象
		req.setAttribute("controller_name","rfidBuseController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出数据至Excel的处理方法。
	 *
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 * @param dataGrid 分页数据对象
	 * @param modelMap 模型映射对象，用于存储导出参数和结果
	 * @return 返回导出页面的视图名称
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RfidBuseEntity rfidBuse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(RfidBuseEntity.class, dataGrid);
		//根据RFidBuseEntity实体和请求参数生成HQL查询
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rfidBuse, request.getParameterMap());
		//根据生成的HQL查询获取RFidBuseEntity列表
		List<RfidBuseEntity> rfidBuses = this.rfidBuseService.getListByCriteriaQuery(cq,false);
		//设置导出文件名
		modelMap.put(NormalExcelConstants.FILE_NAME,"RFID表");
		//设置导出数据的类型
		modelMap.put(NormalExcelConstants.CLASS,RfidBuseEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("RFID表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		//将RFidBuseEntity列表设置到ModelMap中
		modelMap.put(NormalExcelConstants.DATA_LIST,rfidBuses);
		//返回导出页面的视图名称
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")    //注解处理HTTP GET请求，参数为"exportXlsByT"
	public String exportXlsByT(RfidBuseEntity rfidBuse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//设置导出文件名
    	modelMap.put(NormalExcelConstants.FILE_NAME,"RFID表");
    	modelMap.put(NormalExcelConstants.CLASS,RfidBuseEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("RFID表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));  //设置导出参数
		//设置导出数据的列表
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
			//获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			//设置表头行数
			params.setTitleRows(2);
			//设置数据行数
			params.setHeadRows(1);
			//设置是否保存到数据库
			params.setNeedSave(true);
			try {
				List<RfidBuseEntity> listRfidBuseEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RfidBuseEntity.class,params);
				for (RfidBuseEntity rfidBuse : listRfidBuseEntitys) {
					//将导入的数据保存到数据库
					rfidBuseService.save(rfidBuse);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				//记录异常信息
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

	/**
	 * 处理HTTP GET请求以获取所有的RFID表列表.
	 * 将获取的列表作为JSON数据直接作为响应返回给客户端.
	 *
	 * @return ResponseEntity<List<RfidBuseEntity>> 类型的数据
	 */
	@RequestMapping(method = RequestMethod.GET)      //注解处理HTTP GET请求
	@ResponseBody     //作为HTTP响应的内容返回给客户端
	public List<RfidBuseEntity> list() {
		//获取RFID表列表
		List<RfidBuseEntity> listRfidBuses=rfidBuseService.getList(RfidBuseEntity.class);
		return listRfidBuses;
	}

	/**
	 * 根据RFID表的ID获取RFID表信息.
	 *
	 * @param id RFID表的ID
	 * @return ResponseEntity<?> 类型的数据，可能包含具体RFID表信息或错误响应
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		//根据ID获取RFID表信息
		RfidBuseEntity task = rfidBuseService.get(RfidBuseEntity.class, id);
		if (task == null) {
			//如果找不到对应ID的RFID表信息，返回一个表示404 Not Found的响应
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		//如果找到对应ID的RFID表信息，返回信息及其状态码
		return new ResponseEntity(task, HttpStatus.OK);
	}

	/**
	 * 处理POST请求，用于创建一个新的 `RfidBuseEntity` 实体。
	 * 请求体应包含要创建的实体数据，JSON格式。
	 * 使用JSR303 Bean Validator对实体数据进行验证，验证失败返回400错误。
	 *
	 * @param rfidBuse 要创建的 `RfidBuseEntity` 实体对象
	 * @param uriBuilder 用于构建URI的 `UriComponentsBuilder` 实例
	 * @return 如果创建成功，返回响应实体，HTTP状态码为201 Created；如果创建失败，返回响应实体，HTTP状态码为400 Bad Request。
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody RfidBuseEntity rfidBuse, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RfidBuseEntity>> failures = validator.validate(rfidBuse);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		try{
			//保存实体数据
			rfidBuseService.save(rfidBuse);
		} catch (Exception e) {
			e.printStackTrace();
			//如果保存过程中出现任何异常，返回无内容状态码204
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = rfidBuse.getId();
		URI uri = uriBuilder.path("/rest/rfidBuseController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	/**
	 * 导入 Excel 文件并将其内容保存到数据库中。
	 *
	 * @return 返回 AjaxJson 对象，包含操作结果信息
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RfidBuseEntity rfidBuse) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RfidBuseEntity>> failures = validator.validate(rfidBuse);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		try{
			//保存实体数据
			rfidBuseService.saveOrUpdate(rfidBuse);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	/**
	 * 处理基于资源ID的DELETE请求的控制器方法。
	 *
	 * 该控制器用于删除ID指定的RfidBuseEntity实体。
	 *
	 * @param id `String`类型ID，作为请求路径参数。
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		rfidBuseService.deleteEntityById(RfidBuseEntity.class, id);
	}
}
