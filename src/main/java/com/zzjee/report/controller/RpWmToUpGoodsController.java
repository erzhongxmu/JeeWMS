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

import com.zzjee.report.entity.RpWmToUpGoodsEntity;
import com.zzjee.report.service.RpWmToUpGoodsServiceI;

/**
 * @Title: Controller
 * @Description: rp_wm_to_up_goods
 * @author erzhongxmu
 * @date 2018-09-11 07:47:17
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/rpWmToUpGoodsController")
public class RpWmToUpGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RpWmToUpGoodsController.class);
	//用于处理商品上架服务的业务逻辑
	@Autowired
	private RpWmToUpGoodsServiceI rpWmToUpGoodsService;
	//用于处理系统服务的业务逻辑
	@Autowired
	private SystemService systemService;
	//用于验证数据的有效性
	@Autowired
	private Validator validator;

	/**
	 * rp_wm_to_up_goods列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/report/rpWmToUpGoodsList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(RpWmToUpGoodsEntity rpWmToUpGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RpWmToUpGoodsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmToUpGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.rpWmToUpGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除rp_wm_to_up_goods
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//用于传递要删除的实体对象
		rpWmToUpGoods = systemService.getEntity(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
		message = "rp_wm_to_up_goods删除成功";
		try{
			rpWmToUpGoodsService.delete(rpWmToUpGoods);
			//记录日志
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_to_up_goods删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 处理带有参数 "doBatchDel" 的请求，
	 * 批量删除指定的实体对象，并记录日志。
	 * 将操作结果封装为 AjaxJson 对象，并返回给前端。
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		 //用于保存操作的消息。
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_up_goods删除成功";
		try{
			for(String id:ids.split(",")){
				//根据id从数据库中获取RpWmToUpGoodsEntity对象。
				RpWmToUpGoodsEntity rpWmToUpGoods = systemService.getEntity(RpWmToUpGoodsEntity.class, id);
				//调用rpWmToUpGoodsService的delete方法删除rpWmToUpGoods对象
				rpWmToUpGoodsService.delete(rpWmToUpGoods);
				//调用systemService的addLog方法记录日志
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_to_up_goods删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加rp_wm_to_up_goods
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_up_goods添加成功";
		try{
			//调用rpWmToUpGoodsService的save方法将rpWmToUpGoods对象保存到数据库中
			rpWmToUpGoodsService.save(rpWmToUpGoods);
			//调用systemService的addLog方法记录日志
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_to_up_goods添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新rp_wm_to_up_goods
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest request) {
		String message = null;
		//创建一个 AjaxJson 对象用于返回结果
		AjaxJson j = new AjaxJson();
		//设置消息内容为“rp_wm_to_up_goods更新成功”
		message = "rp_wm_to_up_goods更新成功";
		// 使用systemService的get方法根据RpWmToUpGoodsEntity类和给定的ID获取实体对象
		RpWmToUpGoodsEntity t = rpWmToUpGoodsService.get(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
		try {
			//使用MyBeanUtils.copyBeanNotNull2Bean方法将rpWmToUpGoods对象的非空属性值复制到t对象
			MyBeanUtils.copyBeanNotNull2Bean(rpWmToUpGoods, t);
			//使用rpWmToUpGoodsService的saveOrUpdate方法保存或更新实体对象
			rpWmToUpGoodsService.saveOrUpdate(t);
			//使用systemService的addLog方法添加日志信息
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			//打印异常堆栈信息
			e.printStackTrace();
			//更新消息为“rp_wm_to_up_goods更新失败”
			message = "rp_wm_to_up_goods更新失败";
			//抛出业务异常，并携带异常信息
			throw new BusinessException(e.getMessage());
		}
		//设置AjaxJson对象的消息
		j.setMsg(message);
		//返回 AjaxJson 对象
		return j;
	}


	/**
	 * rp_wm_to_up_goods新增页面跳转
	 *
	 * @return
	 */
	//用于跳转到新增页面
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmToUpGoods.getId())) {
			//获取对应的实体对象
			rpWmToUpGoods = rpWmToUpGoodsService.getEntity(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
			//获取和显示该实体对象的信息
			req.setAttribute("rpWmToUpGoodsPage", rpWmToUpGoods);
		}
		return new ModelAndView("com/zzjee/report/rpWmToUpGoods-add");
	}
	/**
	 * rp_wm_to_up_goods编辑页面跳转
	 *
	 * @return
	 */
	//用于跳转到编辑页面
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmToUpGoods.getId())) {
			//如果rpWmToUpGoods对象的id不为空，则从数据库中获取对应id的RpWmToUpGoodsEntity实体对象
			rpWmToUpGoods = rpWmToUpGoodsService.getEntity(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
			req.setAttribute("rpWmToUpGoodsPage", rpWmToUpGoods);
		}
		return new ModelAndView("com/zzjee/report/rpWmToUpGoods-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	//用于跳转到导入功能页面
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		//将名为"controller_name"的属性设置为"rpWmToUpGoodsController"，存储在HttpServletRequest对象req中
		req.setAttribute("controller_name","rpWmToUpGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RpWmToUpGoodsEntity rpWmToUpGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//数据查询和准备
		CriteriaQuery cq = new CriteriaQuery(RpWmToUpGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmToUpGoods, request.getParameterMap());
		//通过查询条件获取数据列表。
		List<RpWmToUpGoodsEntity> rpWmToUpGoodss = this.rpWmToUpGoodsService.getListByCriteriaQuery(cq,false);
		//设置导出的 Excel 文件名
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_to_up_goods");
		//设置导出数据的类型
		modelMap.put(NormalExcelConstants.CLASS,RpWmToUpGoodsEntity.class);
		//设置导出参数，包括 Excel 的标题和导出人信息
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_to_up_goods列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		//设置要导出的数据列表
		modelMap.put(NormalExcelConstants.DATA_LIST,rpWmToUpGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(RpWmToUpGoodsEntity rpWmToUpGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//设置导出文件名
    	modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_to_up_goods");
		//设置导出数据的类类型
    	modelMap.put(NormalExcelConstants.CLASS,RpWmToUpGoodsEntity.class);
		//设置导出参数，包括标题、导出人信息等
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_to_up_goods列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
		//设置空的数据列表，即导出一个空的 Excel 文件
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 处理文件导入（Excel）的HTTP POST请求。
	 *
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 * @return AjaxJson对象，用于返回导入结果的JSON响应信息。
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		//创建AjaxJson对象用于保存返回的信息
		AjaxJson j = new AjaxJson();
		//将请求转换为多部分请求（支持文件上传）
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//通过请求映射获取文件对象的映射集合
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		//遍历上传的文件
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取上传文件对象
			MultipartFile file = entity.getValue();
			//实例化导入参数对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//使用ExcelImportUtil导入Excel文件并转换为指定类型的对象列表
				List<RpWmToUpGoodsEntity> listRpWmToUpGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RpWmToUpGoodsEntity.class,params);
				//遍历导入的实体对象列表，逐个保存到数据库
				for (RpWmToUpGoodsEntity rpWmToUpGoods : listRpWmToUpGoodsEntitys) {
					//调用服务层方法保存实体对象
					rpWmToUpGoodsService.save(rpWmToUpGoods);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				//设置失败消息
				j.setMsg("文件导入失败！");
				//记录异常信息
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
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

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<RpWmToUpGoodsEntity> list() {
		//调用服务层获取所有 RpWmToUpGoodsEntity 实体对象的列表
		List<RpWmToUpGoodsEntity> listRpWmToUpGoodss=rpWmToUpGoodsService.getList(RpWmToUpGoodsEntity.class);
		return listRpWmToUpGoodss;
	}

	/**
	 * 根据指定的 id 参数获取 RpWmToUpGoodsEntity 实体对象，并返回。
	 * 如果没有找到对应数据，则返回 404 Not Found 错误码。
	 *
	 * @param id 用于获取特定对象的唯一标识
	 * @return ResponseEntity&lt;RpWmToUpGoodsEntity&gt; 包含请求的数据实体或者错误信息
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		//根据路径中的 id 参数，调用服务层获取对应的 RpWmToUpGoodsEntity 实体对象
		RpWmToUpGoodsEntity task = rpWmToUpGoodsService.get(RpWmToUpGoodsEntity.class, id);
		if (task == null) {
			//如果没有找到对应数据，则返回404 Not Found错误码
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		//如果找到数据，则返回数据实体及其状态码
		return new ResponseEntity(task, HttpStatus.OK);
	}

	/**
	 * 处理 POST 请求，创建新的 RpWmToUpGoodsEntity 实体对象。
	 * 使用 JSR303 Bean Validator 进行校验，如果校验失败返回 400 错误码及含 JSON 格式的错误信息。
	 * 成功保存后，返回包含状态码201 Created，并设置 Location 头部指向新创建对象的 URL。
	 *
	 * @param rpWmToUpGoods 需要创建的 RpWmToUpGoodsEntity 实体对象
	 * @param uriBuilder 用于构建和返回创建对象的 URL
	 * @return ResponseEntity&lt;Void&gt; 包含状态码、响应头和状态信息
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody RpWmToUpGoodsEntity rpWmToUpGoods, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmToUpGoodsEntity>> failures = validator.validate(rpWmToUpGoods);
		if (!failures.isEmpty()) {
			//如果校验出错，则返回400错误码及含JSON格式的错误信息
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存新的任务数据
		try{
			rpWmToUpGoodsService.save(rpWmToUpGoods);
		} catch (Exception e) {
			e.printStackTrace();
			//如果保存过程中出现异常，则返回 409 Conflict 错误码
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = rpWmToUpGoods.getId();
		URI uri = uriBuilder.path("/rest/rpWmToUpGoodsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		//返回状态码201 Created，表示成功创建新资源，并设置响应头
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	/**
	 * 处理PUT请求，用于更新rp_wm_to_up_goods实体数据。
	 * 请求体应包含要更新的实体数据，JSON格式。
	 * 使用JSR303 Bean Validator验证实体数据，验证失败返回400错误。
	 *
	 * @param rpWmToUpGoods 将要更新的实体对象
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RpWmToUpGoodsEntity rpWmToUpGoods) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmToUpGoodsEntity>> failures = validator.validate(rpWmToUpGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		try{
			//保存或更新实体数据
			rpWmToUpGoodsService.saveOrUpdate(rpWmToUpGoods);
		} catch (Exception e) {
			e.printStackTrace();
			//如果出现任何异常，返回无内容状态码204
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	/**
	 * 处理DELETE请求，用于删除指定id的rp_wm_to_up_goods实体。
	 *
	 * @param id 要删除的实体的id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		//调用服务方法根据id删除实体数据
		rpWmToUpGoodsService.deleteEntityById(RpWmToUpGoodsEntity.class, id);
	}
}
