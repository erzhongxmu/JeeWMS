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

import com.zzjee.report.entity.RpWmInQmEntity;
import com.zzjee.report.service.RpWmInQmServiceI;

/**
 * @Title: Controller
 * @Description: rp_wm_in_qm
 * @author erzhongxmu
 * @date 2018-09-11 07:46:59
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/rpWmInQmController")
public class RpWmInQmController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RpWmInQmController.class);   //用于记录日志
    //类型为 RpWmInQmServiceI 的服务组件
	@Autowired
	private RpWmInQmServiceI rpWmInQmService;
	//类型为 SystemService 的服务组件
	@Autowired
	private SystemService systemService;
	//类型为 Validator 的验证器组件
	@Autowired
	private Validator validator;

	/**
	 * rp_wm_in_qm列表 页面跳转
	 *
	 * @return
	 */
	//处理HTTP请求的/list路径
	@RequestMapping(params = "list")
	//用于获取HTTP请求的相关信息
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/report/rpWmInQmList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(RpWmInQmEntity rpWmInQm,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RpWmInQmEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmInQm, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.rpWmInQmService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除rp_wm_in_qm
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RpWmInQmEntity rpWmInQm, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//获取指定的 RpWmInQmEntity 对象
		rpWmInQm = systemService.getEntity(RpWmInQmEntity.class, rpWmInQm.getId());
		message = "rp_wm_in_qm删除成功";
		try{
			//删除 RpWmInQmEntity 对象
			rpWmInQmService.delete(rpWmInQm);
			//添加日志记录
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_in_qm删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除rp_wm_in_qm
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")   //请求参数为"doBatchDel"的请求映射到该方法
	@ResponseBody      //作为HTTP响应的内容
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		 //创建一个用于返回结果的AjaxJson对象
		AjaxJson j = new AjaxJson();
		message = "rp_wm_in_qm删除成功";
		//尝试执行批量删除操作
		try{
			for(String id:ids.split(",")){
				//根据id获取要删除的实体对象。
				RpWmInQmEntity rpWmInQm = systemService.getEntity(RpWmInQmEntity.class,
				id
				);
				//删除获取到的实体对象。
				rpWmInQmService.delete(rpWmInQm);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			//捕捉并打印异常堆栈跟踪
			e.printStackTrace();
			message = "rp_wm_in_qm删除失败";
			throw new BusinessException(e.getMessage());
		}
		//设置AjaxJson对象的消息属性
		j.setMsg(message);
		//返回AjaxJson对象作为HTTP响应的内容
		return j;
	}

	/**
	 * 添加rp_wm_in_qm
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody         //将方法的返回值自动转换为 JSON 并写入 HTTP 响应体
	public AjaxJson doAdd(RpWmInQmEntity rpWmInQm, HttpServletRequest request) {
		String message = null;
		//创建一个 AjaxJson 对象用于返回结果
		AjaxJson j = new AjaxJson();
		//设置消息内容为“rp_wm_in_qm添加成功”
		message = "rp_wm_in_qm添加成功";
		try{
			//保存rpWmInQm对象到数据库
			rpWmInQmService.save(rpWmInQm);
			//记录日志，日志类型为插入，日志级别为信息
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			//打印异常堆栈信息
			e.printStackTrace();
			//更新消息为“rp_wm_in_qm添加失败”
			message = "rp_wm_in_qm添加失败";
			throw new BusinessException(e.getMessage());
		}
		//设置AjaxJson对象的消息
		j.setMsg(message);
		//返回AjaxJson对象
		return j;
	}

	/**
	 * 更新rp_wm_in_qm
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmInQmEntity rpWmInQm, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_in_qm更新成功";
		RpWmInQmEntity t = rpWmInQmService.get(RpWmInQmEntity.class, rpWmInQm.getId());
		try {
			//将需要更新的属性复制到原始对象中，忽略空值
			MyBeanUtils.copyBeanNotNull2Bean(rpWmInQm, t);
			//更新对象并保存
			rpWmInQmService.saveOrUpdate(t);
			//记录日志
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "rp_wm_in_qm更新失败";
			//抛出业务异常，提示更新失败
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 处理跳转至添加页面的请求。
	 * @param rpWmInQm 实体对象
	 * 此方法在接收到带有"goAdd"参数的请求时执行，其作用是获取传入实体对象（rpWmInQm）的信息，并在请求（request）对象中设置相关数据。如果实体对象的id非空，会从数据库查询对应实体对象的信息并填充到请求对象中，便于后续页面使用这些信息。否则，直接返回默认的添加页面。
	 *
	 * @param rpWmInQm 实体对象，包含待操作的对象信息
	 * @return ModelAndView，包含视图名，用于指示跳转到哪个页面
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RpWmInQmEntity rpWmInQm, HttpServletRequest req) {
		//如果传入的实体对象的id非空，则从数据库获取该实体对象并填充到request中
		if (StringUtil.isNotEmpty(rpWmInQm.getId())) {
			//如果id不为空，则获取对应的实体对象并填充到request中
			rpWmInQm = rpWmInQmService.getEntity(RpWmInQmEntity.class, rpWmInQm.getId());
			// 将获取到的实体对象设置到request中，便于后续页面操作
			req.setAttribute("rpWmInQmPage", rpWmInQm);
		}
		//返回指定的跳转页面
		return new ModelAndView("com/zzjee/report/rpWmInQm-add");
	}

	/**
	 * 跳转至更新页面
	 *
	 * @param rpWmInQm 实体类对象，包含要更新的数据
	 * @param req HttpServletRequest 对象，用于获取请求参数
	 * @return ModelAndView 对象，包含视图名和模型信息
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmInQmEntity rpWmInQm, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmInQm.getId())) {
			//根据ID获取实体对象
			rpWmInQm = rpWmInQmService.getEntity(RpWmInQmEntity.class, rpWmInQm.getId());
			//将获取到的实体对象放入模型对象中
			req.setAttribute("rpWmInQmPage", rpWmInQm);
		}
		//返回视图名
		return new ModelAndView("com/zzjee/report/rpWmInQm-update");
	}

	/**
	 * 导入功能的跳转页面
	 *
	 * @return ModelAndView 对象，包含视图名和控制器名称
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		//将控制器名称放入模型对象中
		req.setAttribute("controller_name","rpWmInQmController");
		//返回视图名
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 操作导出Excel
	 *
	 * @param rpWmInQm 实体类对象，包含导出的数据条件
	 * @param request HttpServletRequest 对象，用于获取请求参数
	 * @param response HttpServletResponse 对象，用于响应导出的Excel文件
	 * @return String 视图名
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RpWmInQmEntity rpWmInQm,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//创建CriteriaQuery对象，用于构建HQL查询语句
		CriteriaQuery cq = new CriteriaQuery(RpWmInQmEntity.class, dataGrid);
		//使用HqlGenerateUtil方法，根据实体对象和请求参数组装查询条件
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmInQm, request.getParameterMap());
		//执行查询，获取符合条件的数据列表
		List<RpWmInQmEntity> rpWmInQms = this.rpWmInQmService.getListByCriteriaQuery(cq,false);
		//定义文件名、导出类、导出参数和数据列表等信息
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_in_qm");
		modelMap.put(NormalExcelConstants.CLASS,RpWmInQmEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_in_qm列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,rpWmInQms);
		//返回视图名，表示后续的处理逻辑
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 处理导出指定报告类型（T）的rp_wm_in_qm实体列表的请求。
	 * 使用Excel形式返回结果。
	 *
	 * @param rpWmInQm 实体对象，包含需要在Excel中导出的数据
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 * @param dataGrid 分页参数对象，用于处理分页和排序逻辑
	 * @param modelMap 模型映射对象
	 * @return 页面视图，通常用于返回一个HTML页面路径
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(RpWmInQmEntity rpWmInQm,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//设置Excel导出的文件名
    	modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_in_qm");
		//设置导出的实体类
    	modelMap.put(NormalExcelConstants.CLASS,RpWmInQmEntity.class);
		//设置Excel导出的参数，包含报告标题、导出说明等信息
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_in_qm列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
		//设置需要导出的实体列表数据
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		//返回页面视图用于展示导出结果，通常返回一个表示Excel页面路径的字符串
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

//	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		//创建一个新的AjaxJson实例，用于存储文件导入的结果信息
		AjaxJson j = new AjaxJson();
		//将HttpServletRequest对象转换为MultipartHttpServletRequest对象，以便处理文件上传
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//获取上传的文件Map，其中Key为文件名，Value为MultipartFile对象
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		//遍历上传的文件Map，处理每一个上传的文件
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<RpWmInQmEntity> listRpWmInQmEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RpWmInQmEntity.class,params);
				for (RpWmInQmEntity rpWmInQm : listRpWmInQmEntitys) {
					rpWmInQmService.save(rpWmInQm);
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
	public List<RpWmInQmEntity> list() {
		List<RpWmInQmEntity> listRpWmInQms=rpWmInQmService.getList(RpWmInQmEntity.class);
		return listRpWmInQms;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		RpWmInQmEntity task = rpWmInQmService.get(RpWmInQmEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody RpWmInQmEntity rpWmInQm, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmInQmEntity>> failures = validator.validate(rpWmInQm);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmInQmService.save(rpWmInQm);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = rpWmInQm.getId();
		URI uri = uriBuilder.path("/rest/rpWmInQmController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RpWmInQmEntity rpWmInQm) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmInQmEntity>> failures = validator.validate(rpWmInQm);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmInQmService.saveOrUpdate(rpWmInQm);
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
		rpWmInQmService.deleteEntityById(RpWmInQmEntity.class, id);
	}
}
