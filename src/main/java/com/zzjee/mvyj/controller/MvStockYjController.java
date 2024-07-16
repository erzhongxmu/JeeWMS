package com.zzjee.mvyj.controller;
import com.zzjee.mvyj.entity.MvStockYjEntity;
import com.zzjee.mvyj.service.MvStockYjServiceI;

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
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
 * @Description: 效期预警
 * @author erzhongxmu
 * @date 2017-09-17 22:13:08
 * @version V1.0
 */
/**
 * 控制器类，处理与 MvStockYj 相关的请求
 */
@Controller
@RequestMapping("/mvStockYjController")
public class MvStockYjController extends BaseController {
	/**
	 * 日志记录器
	 */
	private static final Logger logger = Logger.getLogger(MvStockYjController.class);
	/**
	 * MvStockYjServiceI 服务接口，用于处理 MvStockYj 相关的业务逻辑
	 */
	@Autowired
	private MvStockYjServiceI mvStockYjService;
	/**
	 * SystemService，用于处理系统相关的逻辑
	 */
	@Autowired
	private SystemService systemService;
	/**
	 * 校验器，用于验证输入的数据
	 */
	@Autowired
	private Validator validator;
	


	/**
	 * 效期预警列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/mvyj/mvStockYjList");
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
	public void datagrid(MvStockYjEntity mvStockYj,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//创建一个CriteriaQuery对象，指定实体类为MvStockYjEntity，数据网格为dataGrid
		CriteriaQuery cq = new CriteriaQuery(MvStockYjEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mvStockYj, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//获取当前登录用户信息
		TSUser user = ResourceUtil.getSessionUserName();
		//检查用户的角色
		String roles = "";
		//如果用户存在，获取与用户的关联角色，并构建角色代码的字符串
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleCode() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			//判断角色是否为"CUS"，如果是，则添加特定的查询条件
			if(roles.equals("CUS")){
				cq.eq("cusCode", user.getUserName());
				
			}
		}
		cq.add();
//		Map<String,Object> map1 = new HashMap<String,Object>(); 
//		map1.put("dqr", "desc");  
//		cq.setOrder(map1); 
		this.mvStockYjService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除效期预警
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(MvStockYjEntity mvStockYj, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//获取指定的 MvStockYjEntity 对象
		mvStockYj = systemService.getEntity(MvStockYjEntity.class, mvStockYj.getId());
		message = "效期预警删除成功";
		try{
			//删除 MvStockYjEntity 对象
			mvStockYjService.delete(mvStockYj);
			//添加日志记录
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "效期预警删除失败";
			throw new BusinessException(e.getMessage());
		}
		//设置返回消息
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除效期预警
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		//设置消息
		message = "效期预警删除成功";
		try{
			//遍历提供的ids，并删除对应的MvStockYjEntity对象
			for(String id:ids.split(",")){
				MvStockYjEntity mvStockYj = systemService.getEntity(MvStockYjEntity.class, 
				id
				);
				mvStockYjService.delete(mvStockYj);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "效期预警删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加效期预警
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(MvStockYjEntity mvStockYj, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//设置消息
		message = "效期预警添加成功";
		try{
			//保存MvStockYjEntity对象
			mvStockYjService.save(mvStockYj);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "效期预警添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新效期预警
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(MvStockYjEntity mvStockYj, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//设置消息
		message = "效期预警更新成功";
		//获取要更新的MvStockYjEntity对象
		MvStockYjEntity t = mvStockYjService.get(MvStockYjEntity.class, mvStockYj.getId());
		try {
			//将mvStockYj的非空属性复制到t对象中
			MyBeanUtils.copyBeanNotNull2Bean(mvStockYj, t);
			//保存或更新t对象
			mvStockYjService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "效期预警更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 效期预警新增页面跳转
	 * 要新增的MvStockYjEntity对象
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(MvStockYjEntity mvStockYj, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mvStockYj.getId())) {
			//如果mvStockYj的id不为空，则从数据库中获取对应的实体对象
			mvStockYj = mvStockYjService.getEntity(MvStockYjEntity.class, mvStockYj.getId());
			req.setAttribute("mvStockYjPage", mvStockYj);
		}
		return new ModelAndView("com/zzjee/mvyj/mvStockYj-add");
	}
	/**
	 * 效期预警编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(MvStockYjEntity mvStockYj, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mvStockYj.getId())) {
			//如果mvStockYj的id不为空，则从数据库中获取对应的实体对象
			mvStockYj = mvStockYjService.getEntity(MvStockYjEntity.class, mvStockYj.getId());
			req.setAttribute("mvStockYjPage", mvStockYj);
		}
		return new ModelAndView("com/zzjee/mvyj/mvStockYj-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","mvStockYjController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(MvStockYjEntity mvStockYj,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(MvStockYjEntity.class, dataGrid);
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleCode() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
			if(roles.equals("CUS")){
				cq.eq("cusCode", user.getUserName());
				
			}
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mvStockYj, request.getParameterMap());
		List<MvStockYjEntity> mvStockYjs = this.mvStockYjService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"效期预警");
		modelMap.put(NormalExcelConstants.CLASS,MvStockYjEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("效期预警列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,mvStockYjs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(MvStockYjEntity mvStockYj,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		//设置导出文件名
    	modelMap.put(NormalExcelConstants.FILE_NAME,"效期预警");
		//设置导出数据对象的类型
    	modelMap.put(NormalExcelConstants.CLASS,MvStockYjEntity.class);
		//设置导出参数，包括导出文件的标题、导出人和导出信息
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("效期预警列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
		//设置导出数据列表，这里使用一个空的ArrayList，实际导出的数据需要根据具体情况进行设置
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		//返回视图名称，用于导出Excel文件
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		//获取上传的文件对象
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();
			//设置导入参数
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//导入Excel文件，并返回一个包含导入数据的列表
				List<MvStockYjEntity> listMvStockYjEntitys = ExcelImportUtil.importExcel(file.getInputStream(),MvStockYjEntity.class,params);
				//遍历导入的数据列表，并保存到数据库中
				for (MvStockYjEntity mvStockYj : listMvStockYjEntitys) {
					mvStockYjService.save(mvStockYj);
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
	public List<MvStockYjEntity> list() {
		List<MvStockYjEntity> listMvStockYjs=mvStockYjService.getList(MvStockYjEntity.class);
		return listMvStockYjs;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		MvStockYjEntity task = mvStockYjService.get(MvStockYjEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody MvStockYjEntity mvStockYj, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MvStockYjEntity>> failures = validator.validate(mvStockYj);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mvStockYjService.save(mvStockYj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = mvStockYj.getId();
		URI uri = uriBuilder.path("/rest/mvStockYjController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody MvStockYjEntity mvStockYj) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MvStockYjEntity>> failures = validator.validate(mvStockYj);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mvStockYjService.saveOrUpdate(mvStockYj);
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
		mvStockYjService.deleteEntityById(MvStockYjEntity.class, id);
	}
}
