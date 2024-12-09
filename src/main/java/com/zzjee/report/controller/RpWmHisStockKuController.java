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

import com.zzjee.report.entity.RpWmHisStockKuEntity;
import com.zzjee.report.service.RpWmHisStockKuServiceI;

/**
 * @Title: Controller
 * @Description: rp_wm_his_stock_ku
 * @author erzhongxmu
 * @date 2018-09-11 07:47:09
 * @version V1.0
 *
 */
@Controller    //用于处理HTTP请求
@RequestMapping("/rpWmHisStockKuController")  //映射请求的URL路径
//用于记录日志
public class RpWmHisStockKuController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RpWmHisStockKuController.class);
	@Autowired
	private RpWmHisStockKuServiceI rpWmHisStockKuService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * rp_wm_his_stock_ku列表 页面跳转
	 *
	 * @return
	 */
	//list方法的作用是跳转到名为"com/zzjee/report/rpWmHisStockKuList"的页面
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/report/rpWmHisStockKuList");
	}

	/**
	 * 处理数据表格请求的控制器方法。
	 * @param rpWmHisStockKu 实体对象
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 * @param dataGrid 分页数据对象
	 * 此方法根据提供的参数处理数据表格的请求。具体步骤如下：
	 * 1. 创建一个CriteriaQuery实例，用于构建查询语句。
	 * 2. 使用HqlGenerateUtil工具来组装HQL查询条件，根据request中的参数构建条件。
	 * 3. 防止异常情况，如果在组装查询条件过程中出现异常，将抛出一个BusinessException。
	 * 4. 将组装好的查询条件应用到查询中。
	 * 5. 调用rpWmHisStockKuService的getDataGridReturn方法执行数据查询并返回分页结果。
	 * 6. 使用TagUtil工具将查询结果以特定的格式输出到响应中。
	 *
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(RpWmHisStockKuEntity rpWmHisStockKu,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//创建CriteriaQuery对象，用于构建HQL查询语句
		CriteriaQuery cq = new CriteriaQuery(RpWmHisStockKuEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmHisStockKu, request.getParameterMap());
		try{
		// 执行追加操作
		}catch (Exception e) {
			//若出现异常，抛出一个带有原错误信息的BusinessException异常
			throw new BusinessException(e.getMessage());
		}
		//确保执行的查询语句
		cq.add();
		//调用rpWmHisStockKuService的getDataGridReturn方法执行查询操作
		this.rpWmHisStockKuService.getDataGridReturn(cq, true);
		//使用TagUtil的datagrid方法将查询结果转换为响应格式并发送给客户端
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除rp_wm_his_stock_ku数据
	 *
	 * @param rpWmHisStockKu 实体对象，包含待删除的数据（通过id）
	 * @param request HttpServletRequest 对象，用于获取请求参数
	 * @return AjaxJson 对象，包含处理结果信息
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest request) {
		String message = null;
		//创建AjaxJson对象，用于承载返回结果
		AjaxJson j = new AjaxJson();
		//尝试从数据库获取实体对象rpWmHisStockKu的详细信息
		rpWmHisStockKu = systemService.getEntity(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
		//设置删除成功的消息
		message = "rp_wm_his_stock_ku删除成功";
		try{
			//执行删除操作
			rpWmHisStockKuService.delete(rpWmHisStockKu);
			//记录操作日志
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			//若出现异常，则捕获异常并记录错误信息，同时抛出一个BusinessException异常
			e.printStackTrace();
			message = "rp_wm_his_stock_ku删除失败";
			throw new BusinessException(e.getMessage());
		}
		//将处理结果信息设置到AjaxJson对象中并返回
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除rp_wm_his_stock_ku
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		//初始化返回对象和信息字符串
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_his_stock_ku删除成功";
		try{
			for(String id:ids.split(",")){
				//通过ID获取实体
				RpWmHisStockKuEntity rpWmHisStockKu = systemService.getEntity(RpWmHisStockKuEntity.class, id);
				//执行删除操作
				rpWmHisStockKuService.delete(rpWmHisStockKu);
				//记录系统日志
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			//如果出现异常，打印堆栈信息并记录错误日志
			e.printStackTrace();
			//更新信息字符串为删除失败的信息
			message = "rp_wm_his_stock_ku删除失败";
			throw new BusinessException(e.getMessage());
		}
		//返回AjaxJson对象
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加rp_wm_his_stock_ku
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_his_stock_ku添加成功";
		try{
			rpWmHisStockKuService.save(rpWmHisStockKu);
			//记录日记
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_his_stock_ku添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新rp_wm_his_stock_ku
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_his_stock_ku更新成功";
		//根据传入的实体对象ID，获取指定的 RpWmHisStockKuEntity 对象
		RpWmHisStockKuEntity t = rpWmHisStockKuService.get(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
		try {
			//将传入对象的非空属性复制到目标对象
			MyBeanUtils.copyBeanNotNull2Bean(rpWmHisStockKu, t);
			//保存或更新目标对象
			rpWmHisStockKuService.saveOrUpdate(t);
			//添加日志记录
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "rp_wm_his_stock_ku更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * rp_wm_his_stock_ku新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest req) {
		//如果rpWmHisStockKu对象的ID不为空
		if (StringUtil.isNotEmpty(rpWmHisStockKu.getId())) {
			//根据ID获取对应的RpWmHisStockKuEntity对象
			rpWmHisStockKu = rpWmHisStockKuService.getEntity(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
			//将获取到的对象设置到请求属性中，属性名为 "rpWmHisStockKuPage"
			req.setAttribute("rpWmHisStockKuPage", rpWmHisStockKu);
		}
		//返回视图名称为 "com/zzjee/report/rpWmHisStockKu-add" 的 ModelAndView 对象
		return new ModelAndView("com/zzjee/report/rpWmHisStockKu-add");
	}

	/**
	 * rp_wm_his_stock_ku编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest req) {
		//如果rpWmHisStockKu对象的ID不为空
		if (StringUtil.isNotEmpty(rpWmHisStockKu.getId())) {
			//根据ID获取对应的RpWmHisStockKuEntity对象
			rpWmHisStockKu = rpWmHisStockKuService.getEntity(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
			//将获取到的对象设置到请求属性中，属性名为"rpWmHisStockKuPage"
			req.setAttribute("rpWmHisStockKuPage", rpWmHisStockKu);
		}
		//返回视图名称为"com/zzjee/report/rpWmHisStockKu-update"的ModelAndView对象
		return new ModelAndView("com/zzjee/report/rpWmHisStockKu-update");
	}

	/**
	 * 处理上传请求的处理方法。
	 *
	 * @param req HttpServletRequest 请求对象，用于获取和设置请求属性
	 * @return ModelAndView 对象，包含视图名称和附加的数据，用于页面跳转
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		//设置请求时控制器的名称为 rpWmHisStockKuController
		req.setAttribute("controller_name","rpWmHisStockKuController");
		// 返回指定的视图页面名称，用于前端页面加载时使用
		// 在这个上下文中，页面名称被设置为 'common/upload/pub_excel_upload'
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(RpWmHisStockKuEntity rpWmHisStockKu,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//创建CriteriaQuery对象，用于执行查询操作
		CriteriaQuery cq = new CriteriaQuery(RpWmHisStockKuEntity.class, dataGrid);
		//使用HqlGenerateUtil安装HQL查询语句
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmHisStockKu, request.getParameterMap());
		//执行查询，并获取所有结果
		List<RpWmHisStockKuEntity> rpWmHisStockKus = this.rpWmHisStockKuService.getListByCriteriaQuery(cq,false);
		//设置excel文件名称
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_his_stock_ku");
		//设置excel类对象
		modelMap.put(NormalExcelConstants.CLASS,RpWmHisStockKuEntity.class);
		//设置excel参数信息
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_his_stock_ku列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		//设置导出的数据列表
		modelMap.put(NormalExcelConstants.DATA_LIST,rpWmHisStockKus);
		//返回Excel视图页面
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(RpWmHisStockKuEntity rpWmHisStockKu,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_his_stock_ku");
    	modelMap.put(NormalExcelConstants.CLASS,RpWmHisStockKuEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_his_stock_ku列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	//@SuppressWarnings("unchecked")

	/**
	 * 导入 Excel 文件并将其内容保存到数据库中。
	 *
	 * @param request HttpServletRequest 对象，包含客户端请求信息
	 * @param response HttpServletResponse 对象，包含服务器响应信息
	 * @return 返回 AjaxJson 对象，包含操作结果信息
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		// 将HttpServletRequest转换为MultipartHttpServletRequest，以处理上传的文件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获取上传的文件Map，其中Key为文件名，Value为MultipartFile对象
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 遍历上传的文件Map，处理每一个上传的文件
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<RpWmHisStockKuEntity> listRpWmHisStockKuEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RpWmHisStockKuEntity.class,params);
				for (RpWmHisStockKuEntity rpWmHisStockKu : listRpWmHisStockKuEntitys) {
					rpWmHisStockKuService.save(rpWmHisStockKu);
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
	public List<RpWmHisStockKuEntity> list() {
		List<RpWmHisStockKuEntity> listRpWmHisStockKus=rpWmHisStockKuService.getList(RpWmHisStockKuEntity.class);
		return listRpWmHisStockKus;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		RpWmHisStockKuEntity task = rpWmHisStockKuService.get(RpWmHisStockKuEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody RpWmHisStockKuEntity rpWmHisStockKu, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmHisStockKuEntity>> failures = validator.validate(rpWmHisStockKu);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmHisStockKuService.save(rpWmHisStockKu);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = rpWmHisStockKu.getId();
		URI uri = uriBuilder.path("/rest/rpWmHisStockKuController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RpWmHisStockKuEntity rpWmHisStockKu) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmHisStockKuEntity>> failures = validator.validate(rpWmHisStockKu);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmHisStockKuService.saveOrUpdate(rpWmHisStockKu);
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
		rpWmHisStockKuService.deleteEntityById(RpWmHisStockKuEntity.class, id);
	}
}
