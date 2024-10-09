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

	@Autowired
	private RpWmToUpGoodsServiceI rpWmToUpGoodsService;
	@Autowired
	private SystemService systemService;
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
	 * @param user
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
		rpWmToUpGoods = systemService.getEntity(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
		message = "rp_wm_to_up_goods删除成功";
		try{
			rpWmToUpGoodsService.delete(rpWmToUpGoods);
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
	 * 批量删除rp_wm_to_up_goods
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_up_goods删除成功";
		try{
			for(String id:ids.split(",")){
				RpWmToUpGoodsEntity rpWmToUpGoods = systemService.getEntity(RpWmToUpGoodsEntity.class, 
				id
				);
				rpWmToUpGoodsService.delete(rpWmToUpGoods);
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
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_up_goods添加成功";
		try{
			rpWmToUpGoodsService.save(rpWmToUpGoods);
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
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_to_up_goods更新成功";
		RpWmToUpGoodsEntity t = rpWmToUpGoodsService.get(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(rpWmToUpGoods, t);
			rpWmToUpGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "rp_wm_to_up_goods更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * rp_wm_to_up_goods新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmToUpGoods.getId())) {
			rpWmToUpGoods = rpWmToUpGoodsService.getEntity(RpWmToUpGoodsEntity.class, rpWmToUpGoods.getId());
			req.setAttribute("rpWmToUpGoodsPage", rpWmToUpGoods);
		}
		return new ModelAndView("com/zzjee/report/rpWmToUpGoods-add");
	}
	/**
	 * rp_wm_to_up_goods编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmToUpGoodsEntity rpWmToUpGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmToUpGoods.getId())) {
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
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
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
		CriteriaQuery cq = new CriteriaQuery(RpWmToUpGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmToUpGoods, request.getParameterMap());
		List<RpWmToUpGoodsEntity> rpWmToUpGoodss = this.rpWmToUpGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_to_up_goods");
		modelMap.put(NormalExcelConstants.CLASS,RpWmToUpGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_to_up_goods列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
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
    	modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_to_up_goods");
    	modelMap.put(NormalExcelConstants.CLASS,RpWmToUpGoodsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_to_up_goods列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
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
				List<RpWmToUpGoodsEntity> listRpWmToUpGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),RpWmToUpGoodsEntity.class,params);
				for (RpWmToUpGoodsEntity rpWmToUpGoods : listRpWmToUpGoodsEntitys) {
					rpWmToUpGoodsService.save(rpWmToUpGoods);
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
	public List<RpWmToUpGoodsEntity> list() {
		List<RpWmToUpGoodsEntity> listRpWmToUpGoodss=rpWmToUpGoodsService.getList(RpWmToUpGoodsEntity.class);
		return listRpWmToUpGoodss;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		RpWmToUpGoodsEntity task = rpWmToUpGoodsService.get(RpWmToUpGoodsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody RpWmToUpGoodsEntity rpWmToUpGoods, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmToUpGoodsEntity>> failures = validator.validate(rpWmToUpGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmToUpGoodsService.save(rpWmToUpGoods);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = rpWmToUpGoods.getId();
		URI uri = uriBuilder.path("/rest/rpWmToUpGoodsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RpWmToUpGoodsEntity rpWmToUpGoods) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RpWmToUpGoodsEntity>> failures = validator.validate(rpWmToUpGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			rpWmToUpGoodsService.saveOrUpdate(rpWmToUpGoods);
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
		rpWmToUpGoodsService.deleteEntityById(RpWmToUpGoodsEntity.class, id);
	}
}
