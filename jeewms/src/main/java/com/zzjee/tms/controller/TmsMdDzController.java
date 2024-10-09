package com.zzjee.tms.controller;

import com.alibaba.fastjson.JSONArray;
import com.zzjee.tms.entity.TmsMdDzEntity;
import com.zzjee.tms.service.TmsMdDzServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**   
 * @Title: Controller  
 * @Description: 客户地址
 * @author onlineGenerator
 * @date 2018-01-29 21:57:16
 * @version V1.0   
 *
 */
@Api(value="TmsMdDz",description="客户地址",tags="tmsMdDzController")
@Controller
@RequestMapping("/tmsMdDzController")
public class TmsMdDzController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TmsMdDzController.class);

	@Autowired
	private TmsMdDzServiceI tmsMdDzService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 客户地址列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsMdDzList");
	}
	@RequestMapping(params = "listchoose")
	public ModelAndView listchoose(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsMdDzChoose");
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
	public void datagrid(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdDzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdDz, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");

		}
		if(!StringUtil.isEmpty(user.getUserType())){
			if(user.getUserType().equals("4")){
				cq.eq("username",user.getUserName());
			}

		}
		cq.add();
		this.tmsMdDzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridchoose")
	public void datagridchoose(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdDzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdDz, request.getParameterMap());
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");

		}
		if(!StringUtil.isEmpty(user.getUserType())){
			if(user.getUserType().equals("4")){
				cq.eq("username",user.getUserName());
			}

		}
		cq.add();
		this.tmsMdDzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 删除客户地址
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TmsMdDzEntity tmsMdDz, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsMdDz = systemService.getEntity(TmsMdDzEntity.class, tmsMdDz.getId());
		message = "客户地址删除成功";
		try{
			tmsMdDz.setZhuangtai("N");
			tmsMdDzService.updateEntitie(tmsMdDz);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户地址删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除客户地址
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户地址删除成功";
		try{
			for(String id:ids.split(",")){
				TmsMdDzEntity tmsMdDz = systemService.getEntity(TmsMdDzEntity.class,
				id
				);
				tmsMdDz.setZhuangtai("N");
				tmsMdDzService.updateEntitie(tmsMdDz);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "客户地址删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加客户地址
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TmsMdDzEntity tmsMdDz, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户地址添加成功";
		try{
			tmsMdDzService.save(tmsMdDz);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户地址添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新客户地址
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TmsMdDzEntity tmsMdDz, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户地址更新成功";
		TmsMdDzEntity t = tmsMdDzService.get(TmsMdDzEntity.class, tmsMdDz.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tmsMdDz, t);
			tmsMdDzService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "客户地址更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 客户地址新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TmsMdDzEntity tmsMdDz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsMdDz.getId())) {
			tmsMdDz = tmsMdDzService.getEntity(TmsMdDzEntity.class, tmsMdDz.getId());
			req.setAttribute("tmsMdDzPage", tmsMdDz);
		}
		return new ModelAndView("com/zzjee/tms/tmsMdDz-add");
	}
	/**
	 * 客户地址编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TmsMdDzEntity tmsMdDz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsMdDz.getId())) {
			tmsMdDz = tmsMdDzService.getEntity(TmsMdDzEntity.class, tmsMdDz.getId());
			req.setAttribute("tmsMdDzPage", tmsMdDz);
		}
		return new ModelAndView("com/zzjee/tms/tmsMdDz-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tmsMdDzController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdDzEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdDz, request.getParameterMap());
		List<TmsMdDzEntity> tmsMdDzs = this.tmsMdDzService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"客户地址");
		modelMap.put(NormalExcelConstants.CLASS,TmsMdDzEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户地址列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tmsMdDzs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"客户地址");
    	modelMap.put(NormalExcelConstants.CLASS,TmsMdDzEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户地址列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
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
				List<TmsMdDzEntity> listTmsMdDzEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TmsMdDzEntity.class,params);
				for (TmsMdDzEntity tmsMdDz : listTmsMdDzEntitys) {
					tmsMdDzService.save(tmsMdDz);
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
	@ApiOperation(value="客户地址列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TmsMdDzEntity>> list() {
		List<TmsMdDzEntity> listTmsMdDzs=tmsMdDzService.getList(TmsMdDzEntity.class);
		return Result.success(listTmsMdDzs);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取客户地址信息",notes="根据ID获取客户地址信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TmsMdDzEntity task = tmsMdDzService.get(TmsMdDzEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取客户地址信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建客户地址")
	public ResponseMessage<?> create(@ApiParam(name="客户地址对象")@RequestBody TmsMdDzEntity tmsMdDz, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TmsMdDzEntity>> failures = validator.validate(tmsMdDz);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			tmsMdDzService.save(tmsMdDz);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("客户地址信息保存失败");
		}
		return Result.success(tmsMdDz);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新客户地址",notes="更新客户地址")
	public ResponseMessage<?> update(@ApiParam(name="客户地址对象")@RequestBody TmsMdDzEntity tmsMdDz) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TmsMdDzEntity>> failures = validator.validate(tmsMdDz);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			tmsMdDzService.saveOrUpdate(tmsMdDz);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新客户地址信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新客户地址信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除客户地址")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			tmsMdDzService.deleteEntityById(TmsMdDzEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("客户地址删除失败");
		}

		return Result.success();
	}
}
