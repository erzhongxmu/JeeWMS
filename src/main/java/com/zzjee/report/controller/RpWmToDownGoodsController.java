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

import com.zzjee.report.entity.RpWmToDownGoodsEntity;
import com.zzjee.report.service.RpWmToDownGoodsServiceI;

/**
 * @Title: Controller
 * @Description: rp_wm_to_down_goods
 * @author erzhongxmu
 * @date 2018-09-11 07:47:22
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/rpWmToDownGoodsController")
public class RpWmToDownGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	//日志记录器，用于记录类的相关日志信息
	private static final Logger logger = Logger.getLogger(RpWmToDownGoodsController.class);
	//使用@Autowired注解注入了RP仓库商品下架服务接口
	@Autowired
	private RpWmToDownGoodsServiceI rpWmToDownGoodsService;
	//使用@Autowired注解注入了系统服务接口
	@Autowired
	private SystemService systemService;
	//使用@Autowired注解注入了验证器接口
	@Autowired
	private Validator validator;

	/**
	 * rp_wm_to_down_goods列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		//创建一个新的ModelAndView对象，并指定视图名称为"com/zzjee/report/rpWmToDownGoodsList"
		return new ModelAndView("com/zzjee/report/rpWmToDownGoodsList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(RpWmToDownGoodsEntity rpWmToDownGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RpWmToDownGoodsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmToDownGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//将cq对象添加到查询条件中
		cq.add();
		//调用rpWmToDownGoodsService的getDataGridReturn方法，传入cq对象和true参数
		this.rpWmToDownGoodsService.getDataGridReturn(cq, true);
		//使用TagUtil工具类将dataGrid对象转换为JSON格式，并写入HttpServletResponse对象中
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除rp_wm_to_down_goods
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RpWmToDownGoodsEntity rpWmToDownGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//使用systemService的getEntity方法根据RpWmToDownGoodsEntity类和给定的ID获取实体对象
		rpWmToDownGoods = systemService.getEntity(RpWmToDownGoodsEntity.class, rpWmToDownGoods.getId());
		message = "rp_wm_to_down_goods删除成功";
		try{
			//使用rpWmToDownGoodsService的delete方法删除实体对象
			rpWmToDownGoodsService.delete(rpWmToDownGoods);
			//使用systemService的addLog方法添加日志信息
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			//打印异常堆栈信息
			e.printStackTrace();
			message = "rp_wm_to_down_goods删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除rp_wm_to_down_goods
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_down_goods删除成功";
		try{
			for(String id:ids.split(",")){
				//使用systemService的getEntity方法根据RpWmToDownGoodsEntity类和给定的ID获取实体对象
				RpWmToDownGoodsEntity rpWmToDownGoods = systemService.getEntity(RpWmToDownGoodsEntity.class, id);
				//调用rpWmToDownGoodsService的delete方法删除rpWmToDownGoods对象
				rpWmToDownGoodsService.delete(rpWmToDownGoods);
				//使用systemService的addLog方法添加日志信息
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			//打印异常堆栈信息
			e.printStackTrace();
			message = "rp_wm_to_down_goods删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加rp_wm_to_down_goods
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RpWmToDownGoodsEntity rpWmToDownGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_down_goods添加成功";
		try{
			//调用rpWmToDownGoodsService的save方法保存rpWmToDownGoods对象
			rpWmToDownGoodsService.save(rpWmToDownGoods);
			//使用systemService的addLog方法添加日志信息
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_to_down_goods添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新rp_wm_to_down_goods
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmToDownGoodsEntity rpWmToDownGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_down_goods更新成功";
		//使用rpWmToDownGoodsService的get方法根据RpWmToDownGoodsEntity类和给定的ID获取实体对象
		RpWmToDownGoodsEntity t = rpWmToDownGoodsService.get(RpWmToDownGoodsEntity.class, rpWmToDownGoods.getId());
		try {
			//使用MyBeanUtils的copyBeanNotNull2Bean方法将rpWmToDownGoods对象的非空属性复制到t对象中
			MyBeanUtils.copyBeanNotNull2Bean(rpWmToDownGoods, t);
			//调用rpWmToDownGoodsService的saveOrUpdate方法保存或更新t对象
			rpWmToDownGoodsService.saveOrUpdate(t);
			//使用systemService的addLog方法添加日志信息
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "rp_wm_to_down_goods更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * rp_wm_to_down_goods新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RpWmToDownGoodsEntity rpWmToDownGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmToDownGoods.getId())) {
			rpWmToDownGoods = rpWmToDownGoodsService.getEntity(RpWmToDownGoodsEntity.class, rpWmToDownGoods.getId());
			//将rpWmToDownGoodsPage属性设置为rpWmToDownGoods对象
			req.setAttribute("rpWmToDownGoodsPage", rpWmToDownGoods);
		}
		//返回一个新的ModelAndView对象，视图名称为"com/zzjee/report/rpWmToDownGoods-add"
		return new ModelAndView("com/zzjee/report/rpWmToDownGoods-add");
	}

	/**
	 * rp_wm_to_down_goods编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmToDownGoodsEntity rpWmToDownGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmToDownGoods.getId())) {
			rpWmToDownGoods = rpWmToDownGoodsService.getEntity(RpWmToDownGoodsEntity.class, rpWmToDownGoods.getId());
			//将rpWmToDownGoodsPage属性设置为rpWmToDownGoods对象
			req.setAttribute("rpWmToDownGoodsPage", rpWmToDownGoods);
		}
		//返回一个新的ModelAndView对象，视图名称为"com/zzjee/report/rpWmToDownGoods-add"
		return new ModelAndView("com/zzjee/report/rpWmToDownGoods-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		//将"controller_name"属性设置为"rpWmToDownGoodsController"
		req.setAttribute("controller_name","rpWmToDownGoodsController");
		//返回一个新的ModelAndView对象，视图名称为"common/upload/pub_excel_upload"
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RpWmToDownGoodsEntity rpWmToDownGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//创建一个CriteriaQuery对象，用于构建查询条件
		CriteriaQuery cq = new CriteriaQuery(RpWmToDownGoodsEntity.class, dataGrid);
		//使用HqlGenerateUtil的installHql方法根据请求参数map，将查询条件安装到cq对象中
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmToDownGoods, request.getParameterMap());
		//使用rpWmToDownGoodsService的getListByCriteriaQuery方法根据cq对象获取符合条件的RpWmToDownGoodsEntity对象列表
		List<RpWmToDownGoodsEntity> rpWmToDownGoodss = this.rpWmToDownGoodsService.getListByCriteriaQuery(cq,false);
		//将文件名设置为"rp_wm_to_down_goods"
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_to_down_goods");
		//将类设置为RpWmToDownGoodsEntity.class
		modelMap.put(NormalExcelConstants.CLASS,RpWmToDownGoodsEntity.class);
		//创建一个ExportParams对象，设置导出参数，包括导出标题、导出人等信息
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_to_down_goods列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		//将数据列表设置为rpWmToDownGoodss
		modelMap.put(NormalExcelConstants.DATA_LIST,rpWmToDownGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(RpWmToDownGoodsEntity rpWmToDownGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//将文件名设置为"rp_wm_to_down_goods"
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_to_down_goods");
		//将类设置为RpWmToDownGoodsEntity.class
		modelMap.put(NormalExcelConstants.CLASS,RpWmToDownGoodsEntity.class);
		//创建一个ExportParams对象，设置导出参数，包括导出标题、导出人等信息
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_to_down_goods列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
		//创建一个空的ArrayList作为数据列表
		modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		//将HttpServletRequest对象转换为MultipartHttpServletRequest对象，以获取上传的文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//获取上传的文件Map，其中Key为文件名，Value为MultipartFile对象
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		//遍历上传的文件Map
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();         //获取上传文件对象
			//创建一个ImportParams对象，设置导入参数，包括标题行数、表头行数和是否需要保存
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//使用ExcelImportUtil的importExcel方法导入Excel文件
				List<RpWmToDownGoodsEntity> listRpWmToDownGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RpWmToDownGoodsEntity.class,params);
				//遍历RpWmToDownGoodsEntity对象列表，并将每个对象保存到数据库中
				for (RpWmToDownGoodsEntity rpWmToDownGoods : listRpWmToDownGoodsEntitys) {
					rpWmToDownGoodsService.save(rpWmToDownGoods);
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
	public List<RpWmToDownGoodsEntity> list() {
		//调用rpWmToDownGoodsService的getList方法，获取RpWmToDownGoodsEntity对象列表
		List<RpWmToDownGoodsEntity> listRpWmToDownGoodss=rpWmToDownGoodsService.getList(RpWmToDownGoodsEntity.class);
		return listRpWmToDownGoodss;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		//调用rpWmToDownGoodsService的get方法，根据id获取RpWmToDownGoodsEntity对象
		RpWmToDownGoodsEntity task = rpWmToDownGoodsService.get(RpWmToDownGoodsEntity.class, id);
		//如果获取的对象为空，返回一个HTTP状态码为404的ResponseEntity对象
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		//返回包含RpWmToDownGoodsEntity对象的ResponseEntity对象，HTTP状态码为200
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody         //将方法的返回值自动转换为 JSON 并写入 HTTP 响应体
	public ResponseEntity<?> create(@RequestBody RpWmToDownGoodsEntity rpWmToDownGoods, UriComponentsBuilder uriBuilder) {
		//使用JSR303 Bean Validator进行字段验证，如果验证失败返回400错误码及错误信息
		Set<ConstraintViolation<RpWmToDownGoodsEntity>> failures = validator.validate(rpWmToDownGoods);
		if (!failures.isEmpty()) {
			//将验证失败的信息提取出来并返回，状态码为400 BAD_REQUEST
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		//尝试保存RpWmToDownGoodsEntity对象到数据库
		try{
			rpWmToDownGoodsService.save(rpWmToDownGoods);
		} catch (Exception e) {
			//打印异常堆栈信息
			e.printStackTrace();
			//如果保存失败，返回状态码为204 NO_CONTENT
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的URL
		//获取新创建对象的ID
		String id = rpWmToDownGoods.getId();
		//构建 URI，路径为"/rest/rpWmToDownGoodsController/{id}"
		URI uri = uriBuilder.path("/rest/rpWmToDownGoodsController/" + id).build().toUri();

		HttpHeaders headers = new HttpHeaders();
		//设置Location头为新建对象的URI
		headers.setLocation(uri);
		//返回状态码为201 CREATED，并且包含Location头
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	/**
	 * 更新RpWmToDownGoodsEntity对象的HTTP PUT请求处理方法.
	 *
	 * @param rpWmToDownGoods 更新对象的实体类
	 * @return ResponseEntity，状态码为204（无内容）或400（校验错误）
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RpWmToDownGoodsEntity rpWmToDownGoods) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmToDownGoodsEntity>> failures = validator.validate(rpWmToDownGoods);
		if (!failures.isEmpty()) {
			//如果校验失败，返回带有错误信息的JSON响应和对应的HTTP错误状态码
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		//尝试更新RpWmToDownGoodsEntity对象到数据库
		try{
			rpWmToDownGoodsService.saveOrUpdate(rpWmToDownGoods);
		} catch (Exception e) {
			//如果在更新过程中发生异常，捕获异常并打印堆栈跟踪.
			e.printStackTrace();
			//返回无内容的HTTP响应状态码和默认的响应实体，表示请求已完成但无返回内容.
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	//将HTTP DELETE请求映射到此方法，路径变量id指定要删除的实体对象的ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		//使用服务层方法删除指定ID的对象。
		rpWmToDownGoodsService.deleteEntityById(RpWmToDownGoodsEntity.class, id);
	}
}
