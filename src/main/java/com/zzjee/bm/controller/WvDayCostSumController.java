package com.zzjee.bm.controller;
import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
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

import com.zzjee.bm.entity.WvDayCostSumEntity;
import com.zzjee.bm.service.WvDayCostSumServiceI;

/**   
 * @Title: Controller  
 * @Description: wv_day_cost_sum
 * @author erzhongxmu
 * @date 2017-10-21 21:08:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wvDayCostSumController")
public class WvDayCostSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WvDayCostSumController.class);

	@Autowired
	private WvDayCostSumServiceI wvDayCostSumService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * wv_day_cost_sum列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/bm/wvDayCostSumList");
	}
	@RequestMapping(params = "listy")
	public ModelAndView listy(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/bm/wvDayCostSumyList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */



	@RequestMapping(params = "datagridy")
	public void datagridy(WvDayCostSumEntity wvDayCostSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvDayCostSumEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvDayCostSum, request.getParameterMap());
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("costJs","Y");
		cq.add();
		this.wvDayCostSumService.getDataGridReturn(cq, true);

		try {
			List<WvDayCostSumEntity> resultold = dataGrid.getResults();
			Double dayCostYj = 0.0000;
			Double dayCostBhs = 0.0000;
			Double dayCostSe = 0.0000;
			Double dayCostHsj = 0.0000;
			String sdayCostYj = null;
			String sdayCostBhs = null;
			String sdayCostSe = null;
			String sdayCostHsj = null;
			for (WvDayCostSumEntity wmDayCostold : resultold) {
				dayCostYj = dayCostYj
						+ Double.parseDouble(wmDayCostold.getYuanj());
				dayCostBhs = dayCostBhs
						+ Double.parseDouble(wmDayCostold.getBhsj());
				dayCostSe = dayCostSe
						+ Double.parseDouble(wmDayCostold.getShuie());
				dayCostHsj = dayCostHsj
						+ Double.parseDouble(wmDayCostold.getHansj());
			}
			DecimalFormat df = new DecimalFormat(".##");
			sdayCostYj = df.format(dayCostYj);
			sdayCostBhs = df.format(dayCostBhs);
			sdayCostSe = df.format(dayCostSe);
			sdayCostHsj = df.format(dayCostHsj);
			dataGrid.setFooter("costUnit:合计,yuanj:" + sdayCostYj + ",bhsj:"
					+ sdayCostBhs + ",shuie:" + sdayCostSe + ",hansj:"
					+ sdayCostHsj + "");
		} catch (Exception e) {
			// TODO: handle exception
		}

		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "datagrid")
	public void datagrid(WvDayCostSumEntity wvDayCostSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvDayCostSumEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvDayCostSum, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wvDayCostSumService.getDataGridReturn(cq, true);
		
		try {
			List<WvDayCostSumEntity> resultold = dataGrid.getResults();
			Double dayCostYj = 0.0000;
			Double dayCostBhs = 0.0000;
			Double dayCostSe = 0.0000;
			Double dayCostHsj = 0.0000;
			String sdayCostYj = null;
			String sdayCostBhs = null;
			String sdayCostSe = null;
			String sdayCostHsj = null;
			for (WvDayCostSumEntity wmDayCostold : resultold) {
				dayCostYj = dayCostYj
						+ Double.parseDouble(wmDayCostold.getYuanj());
				dayCostBhs = dayCostBhs
						+ Double.parseDouble(wmDayCostold.getBhsj());
				dayCostSe = dayCostSe
						+ Double.parseDouble(wmDayCostold.getShuie());
				dayCostHsj = dayCostHsj
						+ Double.parseDouble(wmDayCostold.getHansj());
			}
			DecimalFormat df = new DecimalFormat(".##");
			sdayCostYj = df.format(dayCostYj);
			sdayCostBhs = df.format(dayCostBhs);
			sdayCostSe = df.format(dayCostSe);
			sdayCostHsj = df.format(dayCostHsj);
			dataGrid.setFooter("costUnit:合计,yuanj:" + sdayCostYj + ",bhsj:"
					+ sdayCostBhs + ",shuie:" + sdayCostSe + ",hansj:"
					+ sdayCostHsj + "");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除wv_day_cost_sum
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WvDayCostSumEntity wvDayCostSum, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wvDayCostSum = systemService.getEntity(WvDayCostSumEntity.class, wvDayCostSum.getId());
		message = "删除成功";
		try{
			wvDayCostSumService.delete(wvDayCostSum);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除wv_day_cost_sum
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		try{
			for(String id:ids.split(",")){
				WvDayCostSumEntity wvDayCostSum = systemService.getEntity(WvDayCostSumEntity.class, 
				id
				);
				wvDayCostSumService.delete(wvDayCostSum);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WvDayCostSumEntity wvDayCostSum, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			wvDayCostSumService.save(wvDayCostSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新wv_day_cost_sum
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WvDayCostSumEntity wvDayCostSum, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_day_cost_sum更新成功";
		WvDayCostSumEntity t = wvDayCostSumService.get(WvDayCostSumEntity.class, wvDayCostSum.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wvDayCostSum, t);
			wvDayCostSumService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "wv_day_cost_sum更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * wv_day_cost_sum新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WvDayCostSumEntity wvDayCostSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvDayCostSum.getId())) {
			wvDayCostSum = wvDayCostSumService.getEntity(WvDayCostSumEntity.class, wvDayCostSum.getId());
			req.setAttribute("wvDayCostSumPage", wvDayCostSum);
		}
		return new ModelAndView("com/zzjee/bm/wvDayCostSum-add");
	}
	/**
	 * wv_day_cost_sum编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WvDayCostSumEntity wvDayCostSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvDayCostSum.getId())) {
			wvDayCostSum = wvDayCostSumService.getEntity(WvDayCostSumEntity.class, wvDayCostSum.getId());
			req.setAttribute("wvDayCostSumPage", wvDayCostSum);
		}
		return new ModelAndView("com/zzjee/bm/wvDayCostSum-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wvDayCostSumController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WvDayCostSumEntity wvDayCostSum,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WvDayCostSumEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvDayCostSum, request.getParameterMap());
		List<WvDayCostSumEntity> wvDayCostSums = this.wvDayCostSumService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"wv_day_cost_sum");
		modelMap.put(NormalExcelConstants.CLASS,WvDayCostSumEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wv_day_cost_sum列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wvDayCostSums);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WvDayCostSumEntity wvDayCostSum,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"wv_day_cost_sum");
    	modelMap.put(NormalExcelConstants.CLASS,WvDayCostSumEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wv_day_cost_sum列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WvDayCostSumEntity> listWvDayCostSumEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WvDayCostSumEntity.class,params);
				for (WvDayCostSumEntity wvDayCostSum : listWvDayCostSumEntitys) {
					wvDayCostSumService.save(wvDayCostSum);
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
	public List<WvDayCostSumEntity> list() {
		List<WvDayCostSumEntity> listWvDayCostSums=wvDayCostSumService.getList(WvDayCostSumEntity.class);
		return listWvDayCostSums;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WvDayCostSumEntity task = wvDayCostSumService.get(WvDayCostSumEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WvDayCostSumEntity wvDayCostSum, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvDayCostSumEntity>> failures = validator.validate(wvDayCostSum);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvDayCostSumService.save(wvDayCostSum);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wvDayCostSum.getId();
		URI uri = uriBuilder.path("/rest/wvDayCostSumController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WvDayCostSumEntity wvDayCostSum) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvDayCostSumEntity>> failures = validator.validate(wvDayCostSum);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvDayCostSumService.saveOrUpdate(wvDayCostSum);
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
		wvDayCostSumService.deleteEntityById(WvDayCostSumEntity.class, id);
	}
}
