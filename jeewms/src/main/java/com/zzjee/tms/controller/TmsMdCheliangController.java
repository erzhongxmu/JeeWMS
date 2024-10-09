package com.zzjee.tms.controller;

import com.alibaba.fastjson.JSONArray;
import com.zzjee.tms.entity.TmsMdCheliangEntity;
import com.zzjee.tms.service.TmsMdCheliangServiceI;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmQmIEntity;
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
import org.jeecgframework.core.util.*;
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
 * @Description: 车辆管理
 * @author onlineGenerator
 * @date 2018-01-29 21:57:07
 * @version V1.0
 *
 */
@Api(value="TmsMdCheliang",description="车辆管理",tags="tmsMdCheliangController")
@Controller
@RequestMapping("/tmsMdCheliangController")
public class TmsMdCheliangController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TmsMdCheliangController.class);

	@Autowired
	private TmsMdCheliangServiceI tmsMdCheliangService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;



	/**
	 * 车辆管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsMdCheliangList");
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
	public void datagrid(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdCheliangEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdCheliang, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
//		TSUser user = ResourceUtil.getSessionUser();
//		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
//			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");
//
//		}
//		if(!StringUtil.isEmpty(user.getUserType())){
//			if(user.getUserType().equals("4")){
//				cq.eq("createBy",user.getUserName());
//			}
//
//		}
//		cq.eq("zhuangtai","Y");
		cq.add();
		this.tmsMdCheliangService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "doassignwave")
	@ResponseBody
	public AjaxJson dowavebatch(String ids, String waveid,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		try {
			String[]   charray = ids.split(",");
			int lenth = 1;
			try{
				lenth = charray.length;
			}catch (Exception e){

			}
			int aint =(int)Math.round(Math.random()*9);
			for (String id : ids.split(",")) {
				TmsMdCheliangEntity t = tmsMdCheliangService.get(TmsMdCheliangEntity.class, id);
				t.setChepaihao(Integer.toString(aint));
				aint++;
				tmsMdCheliangService.updateEntitie(t);

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "重排车号失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;

	}

	/**
	 * 删除车辆管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsMdCheliang = systemService.getEntity(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
		message = "车辆管理删除成功";
		try{
			tmsMdCheliang.setZhuangtai("N");
			tmsMdCheliangService.updateEntitie(tmsMdCheliang);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "车辆管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除车辆管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "车辆管理删除成功";
		try{
			for(String id:ids.split(",")){
				TmsMdCheliangEntity tmsMdCheliang = systemService.getEntity(TmsMdCheliangEntity.class,
				id
				);
				tmsMdCheliang.setZhuangtai("N");
				tmsMdCheliangService.updateEntitie(tmsMdCheliang);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "车辆管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加车辆管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "车辆管理添加成功";
		try{
			tmsMdCheliang.setZhuangtai("Y");
			tmsMdCheliangService.save(tmsMdCheliang);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "车辆管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新车辆管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "车辆管理更新成功";
		TmsMdCheliangEntity t = tmsMdCheliangService.get(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tmsMdCheliang, t);
			tmsMdCheliangService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "车辆管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 车辆管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsMdCheliang.getId())) {
			tmsMdCheliang = tmsMdCheliangService.getEntity(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
			req.setAttribute("tmsMdCheliangPage", tmsMdCheliang);
		}
		return new ModelAndView("com/zzjee/tms/tmsMdCheliang-add");
	}
	/**
	 * 车辆管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsMdCheliang.getId())) {
			tmsMdCheliang = tmsMdCheliangService.getEntity(TmsMdCheliangEntity.class, tmsMdCheliang.getId());
			req.setAttribute("tmsMdCheliangPage", tmsMdCheliang);
		}
		return new ModelAndView("com/zzjee/tms/tmsMdCheliang-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tmsMdCheliangController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdCheliangEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdCheliang, request.getParameterMap());
		List<TmsMdCheliangEntity> tmsMdCheliangs = this.tmsMdCheliangService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"车辆管理");
		modelMap.put(NormalExcelConstants.CLASS,TmsMdCheliangEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("车辆管理列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tmsMdCheliangs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TmsMdCheliangEntity tmsMdCheliang, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"车辆管理");
    	modelMap.put(NormalExcelConstants.CLASS,TmsMdCheliangEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("车辆管理列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
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
				List<TmsMdCheliangEntity> listTmsMdCheliangEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TmsMdCheliangEntity.class,params);
				for (TmsMdCheliangEntity tmsMdCheliang : listTmsMdCheliangEntitys) {
					tmsMdCheliangService.save(tmsMdCheliang);
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
	@ApiOperation(value="车辆管理列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TmsMdCheliangEntity>> list() {
		List<TmsMdCheliangEntity> listTmsMdCheliangs=tmsMdCheliangService.getList(TmsMdCheliangEntity.class);
		return Result.success(listTmsMdCheliangs);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取车辆管理信息",notes="根据ID获取车辆管理信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TmsMdCheliangEntity task = tmsMdCheliangService.get(TmsMdCheliangEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取车辆管理信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建车辆管理")
	public ResponseMessage<?> create(@ApiParam(name="车辆管理对象")@RequestBody TmsMdCheliangEntity tmsMdCheliang, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TmsMdCheliangEntity>> failures = validator.validate(tmsMdCheliang);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			tmsMdCheliangService.save(tmsMdCheliang);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("车辆管理信息保存失败");
		}
		return Result.success(tmsMdCheliang);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新车辆管理",notes="更新车辆管理")
	public ResponseMessage<?> update(@ApiParam(name="车辆管理对象")@RequestBody TmsMdCheliangEntity tmsMdCheliang) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TmsMdCheliangEntity>> failures = validator.validate(tmsMdCheliang);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			tmsMdCheliangService.saveOrUpdate(tmsMdCheliang);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新车辆管理信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新车辆管理信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除车辆管理")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			tmsMdCheliangService.deleteEntityById(TmsMdCheliangEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("车辆管理删除失败");
		}

		return Result.success();
	}
}
