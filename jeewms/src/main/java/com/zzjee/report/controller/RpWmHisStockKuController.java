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
@Controller
@RequestMapping("/rpWmHisStockKuController")
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
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/report/rpWmHisStockKuList");
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
	public void datagrid(RpWmHisStockKuEntity rpWmHisStockKu,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RpWmHisStockKuEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmHisStockKu, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.rpWmHisStockKuService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除rp_wm_his_stock_ku
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		rpWmHisStockKu = systemService.getEntity(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
		message = "rp_wm_his_stock_ku删除成功";
		try{
			rpWmHisStockKuService.delete(rpWmHisStockKu);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_his_stock_ku删除失败";
			throw new BusinessException(e.getMessage());
		}
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
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_his_stock_ku删除成功";
		try{
			for(String id:ids.split(",")){
				RpWmHisStockKuEntity rpWmHisStockKu = systemService.getEntity(RpWmHisStockKuEntity.class, 
				id
				);
				rpWmHisStockKuService.delete(rpWmHisStockKu);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "rp_wm_his_stock_ku删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加rp_wm_his_stock_ku
	 * 
	 * @param ids
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
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "rp_wm_his_stock_ku更新成功";
		RpWmHisStockKuEntity t = rpWmHisStockKuService.get(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(rpWmHisStockKu, t);
			rpWmHisStockKuService.saveOrUpdate(t);
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
		if (StringUtil.isNotEmpty(rpWmHisStockKu.getId())) {
			rpWmHisStockKu = rpWmHisStockKuService.getEntity(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
			req.setAttribute("rpWmHisStockKuPage", rpWmHisStockKu);
		}
		return new ModelAndView("com/zzjee/report/rpWmHisStockKu-add");
	}
	/**
	 * rp_wm_his_stock_ku编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(RpWmHisStockKuEntity rpWmHisStockKu, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(rpWmHisStockKu.getId())) {
			rpWmHisStockKu = rpWmHisStockKuService.getEntity(RpWmHisStockKuEntity.class, rpWmHisStockKu.getId());
			req.setAttribute("rpWmHisStockKuPage", rpWmHisStockKu);
		}
		return new ModelAndView("com/zzjee/report/rpWmHisStockKu-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","rpWmHisStockKuController");
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
		CriteriaQuery cq = new CriteriaQuery(RpWmHisStockKuEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, rpWmHisStockKu, request.getParameterMap());
		List<RpWmHisStockKuEntity> rpWmHisStockKus = this.rpWmHisStockKuService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"rp_wm_his_stock_ku");
		modelMap.put(NormalExcelConstants.CLASS,RpWmHisStockKuEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("rp_wm_his_stock_ku列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,rpWmHisStockKus);
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
