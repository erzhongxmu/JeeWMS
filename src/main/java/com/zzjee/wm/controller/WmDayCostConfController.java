package com.zzjee.wm.controller;
import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.entity.MdCusEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.wm.entity.WmDayCostConfEntity;
import com.zzjee.wm.entity.WmDayCostEntity;
import com.zzjee.wm.service.WmDayCostConfServiceI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.web.system.sms.util.task.CostTask;
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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
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
 * @Description: 计费日期配置
 * @author erzhongxmu
 * @date 2017-10-11 00:26:00
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmDayCostConfController")
public class WmDayCostConfController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmDayCostConfController.class);

	@Autowired
	private WmDayCostConfServiceI wmDayCostConfService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
  private CostTask task;

	/**
	 * 计费日期配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmDayCostConfList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WmDayCostConfEntity wmDayCostConf,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmDayCostConfEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmDayCostConf, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("costDate", "desc"); 
		cq.setOrder(map1); 
		cq.add();
		this.wmDayCostConfService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除计费日期配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmDayCostConfEntity wmDayCostConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmDayCostConf = systemService.getEntity(WmDayCostConfEntity.class, wmDayCostConf.getId());
		message = "计费日期配置删除成功";
		try{
			wmDayCostConf.setCostSf("N");
			wmDayCostConfService.saveOrUpdate(wmDayCostConf);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "计费日期配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除计费日期配置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "计费日期配置删除成功";
		try{
			for(String id:ids.split(",")){
				WmDayCostConfEntity wmDayCostConf = systemService.getEntity(WmDayCostConfEntity.class, 
				id
				);
				wmDayCostConf.setCostSf("N");
				wmDayCostConfService.saveOrUpdate(wmDayCostConf);
//				wmDayCostConfService.delete(wmDayCostConf);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "计费日期配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加计费日期配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmDayCostConfEntity wmDayCostConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "计费日期配置添加成功";
		try{
			wmDayCostConfService.save(wmDayCostConf);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "计费日期配置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 计算每日费用
	 * 
	 * @return
	 */
	@RequestMapping(params = "doCount")
	@ResponseBody
	public AjaxJson doCount(WmDayCostConfEntity wmDayCostConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "费用更新成功";
		try {
			
			WmDayCostConfEntity t = wmDayCostConfService.get(WmDayCostConfEntity.class, request.getParameter("id"));

			if(t.getCostSf()!=null&&t.getCostSf().equals("Y")){
				j.setMsg(message);
				message = "费用已经更新";
				return j;
			}
			try{
				task.costcount(DateUtils.date2Str(t.getCostDate(),DateUtils.date_sdf),"Y",t);
			}catch (Exception e){

			}
			t.setCostSf("Y");
			systemService.updateEntitie(t);
			
			//计算仓租
			// 再计算
		} catch (Exception e) {
			e.printStackTrace();
			message = "费用更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新计费日期配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmDayCostConfEntity wmDayCostConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "计费日期配置更新成功";
		WmDayCostConfEntity t = wmDayCostConfService.get(WmDayCostConfEntity.class, wmDayCostConf.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmDayCostConf, t);
			wmDayCostConfService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "计费日期配置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 计费日期配置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmDayCostConfEntity wmDayCostConf, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmDayCostConf.getId())) {
			wmDayCostConf = wmDayCostConfService.getEntity(WmDayCostConfEntity.class, wmDayCostConf.getId());
			req.setAttribute("wmDayCostConfPage", wmDayCostConf);
		}
		return new ModelAndView("com/zzjee/wm/wmDayCostConf-add");
	}
	/**
	 * 计费日期配置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmDayCostConfEntity wmDayCostConf, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmDayCostConf.getId())) {
			wmDayCostConf = wmDayCostConfService.getEntity(WmDayCostConfEntity.class, wmDayCostConf.getId());
			req.setAttribute("wmDayCostConfPage", wmDayCostConf);
		}
		return new ModelAndView("com/zzjee/wm/wmDayCostConf-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmDayCostConfController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmDayCostConfEntity wmDayCostConf,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmDayCostConfEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmDayCostConf, request.getParameterMap());
		List<WmDayCostConfEntity> wmDayCostConfs = this.wmDayCostConfService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"计费日期配置");
		modelMap.put(NormalExcelConstants.CLASS,WmDayCostConfEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("计费日期配置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmDayCostConfs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmDayCostConfEntity wmDayCostConf,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"计费日期配置");
    	modelMap.put(NormalExcelConstants.CLASS,WmDayCostConfEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("计费日期配置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WmDayCostConfEntity> listWmDayCostConfEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmDayCostConfEntity.class,params);
				for (WmDayCostConfEntity wmDayCostConf : listWmDayCostConfEntitys) {
					wmDayCostConfService.save(wmDayCostConf);
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
	public List<WmDayCostConfEntity> list() {
		List<WmDayCostConfEntity> listWmDayCostConfs=wmDayCostConfService.getList(WmDayCostConfEntity.class);
		return listWmDayCostConfs;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmDayCostConfEntity task = wmDayCostConfService.get(WmDayCostConfEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmDayCostConfEntity wmDayCostConf, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmDayCostConfEntity>> failures = validator.validate(wmDayCostConf);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmDayCostConfService.save(wmDayCostConf);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmDayCostConf.getId();
		URI uri = uriBuilder.path("/rest/wmDayCostConfController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmDayCostConfEntity wmDayCostConf) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmDayCostConfEntity>> failures = validator.validate(wmDayCostConf);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmDayCostConfService.saveOrUpdate(wmDayCostConf);
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
		wmDayCostConfService.deleteEntityById(WmDayCostConfEntity.class, id);
	}
}
