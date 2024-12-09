package com.zzjee.report.controller;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.zzjee.report.entity.RpWmUpAndDownEntity;
import com.zzjee.report.service.RpWmUpAndDownServiceI;

/**
 * @Title: Controller
 * @Description: rp_wm_up_and_down
 * @author erzhongxmu
 * @date 2018-09-11 07:47:13
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/rpWmUpAndDownController")
public class RpWmUpAndDownController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RpWmUpAndDownController.class);
	@Autowired
	private RpWmUpAndDownServiceI rpWmUpAndDownService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;         //注入Validator对象，用于进行数据验证

	/**
	 * rp_wm_up_and_down列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/report/rpWmUpAndDownList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RpWmUpAndDownEntity rpWmUpAndDown,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RpWmUpAndDownEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmUpAndDown, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.rpWmUpAndDownService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除rp_wm_up_and_down
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RpWmUpAndDownEntity rpWmUpAndDown, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//根据请求参数获取对应的 RpWmUpAndDownEntity 实体对象
		rpWmUpAndDown = systemService.getEntity(RpWmUpAndDownEntity.class, rpWmUpAndDown.getId());
		message = "rp_wm_up_and_down删除成功";
		try{
			//调用服务层方法删除 RpWmUpAndDownEntity 实体对象
			rpWmUpAndDownService.delete(rpWmUpAndDown);
			//记录操作日志
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_up_and_down删除失败";
			throw new BusinessException(e.getMessage());
		}
		//设置 AjaxJson 对象的消息
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除rp_wm_up_and_down
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_up_and_down删除成功";
		try{
			//根据每个 id 获取对应的 RpWmUpAndDownEntity 实体对象
			for(String id:ids.split(",")){
				RpWmUpAndDownEntity rpWmUpAndDown = systemService.getEntity(RpWmUpAndDownEntity.class,
				id
				);
				//调用服务层方法删除 RpWmUpAndDownEntity 实体对象
				rpWmUpAndDownService.delete(rpWmUpAndDown);
				//记录操作日志
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_up_and_down删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加rp_wm_up_and_down
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RpWmUpAndDownEntity rpWmUpAndDown, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_up_and_down添加成功";
		try{
			//调用rpWmUpAndDownService的save方法保存RpWmUpAndDownEntity实体对象
			rpWmUpAndDownService.save(rpWmUpAndDown);
			//记录操作日志
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_up_and_down添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新rp_wm_up_and_down
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmUpAndDownEntity rpWmUpAndDown, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_up_and_down更新成功";
		//根据传入的 rpWmUpAndDown 对象的 id 获取数据库中对应的实体对象 t
		RpWmUpAndDownEntity t = rpWmUpAndDownService.get(RpWmUpAndDownEntity.class, rpWmUpAndDown.getId());
		try {
			//将rpWmUpAndDown对象的非空属性拷贝到t对象中
			MyBeanUtils.copyBeanNotNull2Bean(rpWmUpAndDown, t);
			//调用服务层的保存或更新方法
			rpWmUpAndDownService.saveOrUpdate(t);
			//记录操作日志
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "rp_wm_up_and_down更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * rp_wm_up_and_down新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RpWmUpAndDownEntity rpWmUpAndDown, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmUpAndDown.getId())) {
			//如果传入的 rpWmUpAndDown 对象的 id 不为空，从数据库中获取对应的实体对象
			rpWmUpAndDown = rpWmUpAndDownService.getEntity(RpWmUpAndDownEntity.class, rpWmUpAndDown.getId());
			//将获取的实体对象设置到请求的属性中，通常用于在页面中显示已有数据
			req.setAttribute("rpWmUpAndDownPage", rpWmUpAndDown);
		}
		return new ModelAndView("com/zzjee/report/rpWmUpAndDown-add");
	}
	/**
	 * rp_wm_up_and_down编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmUpAndDownEntity rpWmUpAndDown, HttpServletRequest req) {
		//检查rpWmUpAndDown对象的 ID 是否不为空
		if (StringUtil.isNotEmpty(rpWmUpAndDown.getId())) {
			//如果ID不为空，则使用rpWmUpAndDownService的getEntity方法根据RpWmUpAndDownEntity类和给定的ID获取实体对象
			rpWmUpAndDown = rpWmUpAndDownService.getEntity(RpWmUpAndDownEntity.class, rpWmUpAndDown.getId());
			//将获取到的实体对象设置为请求属性"rpWmUpAndDownPage"
			req.setAttribute("rpWmUpAndDownPage", rpWmUpAndDown);
		}
		return new ModelAndView("com/zzjee/report/rpWmUpAndDown-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		//设置请求属性"controller_name"为"rpWmUpAndDownController"
		req.setAttribute("controller_name","rpWmUpAndDownController");
		//返回一个新的ModelAndView对象，视图名称为"common/upload/pub_excel_upload"
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出数据至Excel的处理方法。
	 *
	 * @param rpWmUpAndDown 导出数据的实体对象
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 * @param dataGrid 分页数据对象
	 * @param modelMap 模型映射对象，用于存储导出参数和结果
	 * @return 返回导出页面的视图名称
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RpWmUpAndDownEntity rpWmUpAndDown,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//创建CriteriaQuery对象，用于构建HQL查询条件
		CriteriaQuery cq = new CriteriaQuery(RpWmUpAndDownEntity.class, dataGrid);
		//生成HQL查询条件，用于根据请求参数过滤数据
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmUpAndDown, request.getParameterMap());
		//执行查询，获取符合条件的实体对象列表
		List<RpWmUpAndDownEntity> rpWmUpAndDowns = this.rpWmUpAndDownService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_up_and_down");
		modelMap.put(NormalExcelConstants.CLASS,RpWmUpAndDownEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_up_and_down列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,rpWmUpAndDowns);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出 Excel 操作（使用特定的逗号分隔参数 "exportXlsByT"）
	 *
	 * @param rpWmUpAndDown 需要导出的数据实体对象
	 * @param request HTTP 请求对象
	 * @param response HTTP 响应对象
	 * @param dataGrid 数据网格对象
	 * @param modelMap 模型属性映射对象
	 * @return "NormalExcelConstants.JEECG_EXCEL_VIEW" 视图名
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(RpWmUpAndDownEntity rpWmUpAndDown,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//设置Excel导出的文件名
    	modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_up_and_down");
		//设置 Excel 导出的类
    	modelMap.put(NormalExcelConstants.CLASS,RpWmUpAndDownEntity.class);
		//设置 Excel 导出的参数，包括标题、导出人和导出信息
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_up_and_down列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
		//设置数据列表为空，若非空则直接使用
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		//返回视图名，用于后续的导出处理
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导入 Excel 文件操作
	 *
	 * @param request HTTP 请求对象
	 * @param response HTTP 响应对象
	 * @return AjaxJson 结果对象
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		// 创建 AjaxJson 对象用于返回结果
		AjaxJson j = new AjaxJson();
		// 处理多部分请求，获取上传文件对象
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			//获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//导入Excel文件，并获取数据列表
				List<RpWmUpAndDownEntity> listRpWmUpAndDownEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RpWmUpAndDownEntity.class,params);
				//遍历数据列表，保存数据到数据库
				for (RpWmUpAndDownEntity rpWmUpAndDown : listRpWmUpAndDownEntitys) {
					rpWmUpAndDownService.save(rpWmUpAndDown);
				}
				//设置返回信息，表示导入成功
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				//如果出现异常，设置返回信息，表示导入失败，并记录异常堆栈信息
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				// 关闭文件输入流
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					//打印异常堆栈信息
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 根据GET请求方法获取一组 `RpWmUpAndDownEntity` 实体。
	 * 返回所有 `RpWmUpAndDownEntity` 实体的列表。
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<RpWmUpAndDownEntity> list() {
		//调用服务方法获取所有 RpWmUpAndDownEntity 实体
		List<RpWmUpAndDownEntity> listRpWmUpAndDowns=rpWmUpAndDownService.getList(RpWmUpAndDownEntity.class);
		//返回获取到的实体列表
		return listRpWmUpAndDowns;
	}

	/**
	 * 根据GET请求方法获取特定 `id` 的 `RpWmUpAndDownEntity` 实体。
	 *
	 * @param id 指定的实体ID
	 * @return 如果找到实体，返回该实体；如果未找到（404 Not Found状态），返回404状态的响应实体。
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		//调用服务方法根据给定的ID获取特定的RpWmUpAndDownEntity 实体
		RpWmUpAndDownEntity task = rpWmUpAndDownService.get(RpWmUpAndDownEntity.class, id);
		//如果实体不存在，则返回404 Not Found状态的响应实体
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		//如果实体存在，则返回实体和200 OK状态的响应实体
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody RpWmUpAndDownEntity rpWmUpAndDown, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmUpAndDownEntity>> failures = validator.validate(rpWmUpAndDown);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmUpAndDownService.save(rpWmUpAndDown);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = rpWmUpAndDown.getId();
		URI uri = uriBuilder.path("/rest/rpWmUpAndDownController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RpWmUpAndDownEntity rpWmUpAndDown) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmUpAndDownEntity>> failures = validator.validate(rpWmUpAndDown);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmUpAndDownService.saveOrUpdate(rpWmUpAndDown);
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
		rpWmUpAndDownService.deleteEntityById(RpWmUpAndDownEntity.class, id);
	}
}
