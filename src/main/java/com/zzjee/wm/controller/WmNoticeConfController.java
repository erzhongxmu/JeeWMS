package com.zzjee.wm.controller;
import com.zzjee.md.entity.MdCusEntity;
import com.zzjee.wm.entity.WmImNoticeHEntity;
import com.zzjee.wm.entity.WmNoticeConfEntity;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.service.WmNoticeConfServiceI;

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
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
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
 * @Description: 回单管理
 * @author erzhongxmu
 * @date 2017-08-15 23:18:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmNoticeConfController")
public class WmNoticeConfController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmNoticeConfController.class);

	@Autowired
	private WmNoticeConfServiceI wmNoticeConfService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 回单管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmNoticeConfList");
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
	public void datagrid(WmNoticeConfEntity wmNoticeConf,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmNoticeConfEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmNoticeConf, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("wmNoticeId", "desc");  
		cq.setOrder(map1); 
		cq.add();
		this.wmNoticeConfService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除回单管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmNoticeConfEntity wmNoticeConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmNoticeConf = systemService.getEntity(WmNoticeConfEntity.class, wmNoticeConf.getId());
		message = "回单管理删除成功";
		try{
			wmNoticeConfService.delete(wmNoticeConf);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回单管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "doGethuozhu",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doGethuozhu(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		WmOmNoticeHEntity wmOmNoticeHEntity = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", oConvertUtils.getString(request.getParameter("wmNoticeId")));
		if(wmOmNoticeHEntity!=null&&(!wmOmNoticeHEntity.getOmSta().equals("已删除")||!wmOmNoticeHEntity.getOmSta().equals("已完成"))){
	        MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmOmNoticeHEntity.getCusCode());
	        j.setObj(md);
		}else{
			j.setSuccess(false);
			j.setMsg("不允许此操作");
		}
		
		return j;
	}
	
	/**
	 * 批量删除回单管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "回单管理删除成功";
		try{
			for(String id:ids.split(",")){
				WmNoticeConfEntity wmNoticeConf = systemService.getEntity(WmNoticeConfEntity.class, 
				id
				);
				wmNoticeConfService.delete(wmNoticeConf);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "回单管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加回单管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmNoticeConfEntity wmNoticeConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "回单管理添加成功";
		try{
			
			WmOmNoticeHEntity wmOmNoticeHEntity = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", wmNoticeConf.getWmNoticeId());
            if(wmOmNoticeHEntity!=null){
            	if(wmOmNoticeHEntity.getOmSta().equals(Constants.wm_sta4)){
                	j.setSuccess(false);
            		j.setMsg("此出货单已完成");
            		return j;
            	}else{
                	wmOmNoticeHEntity.setOmSta(Constants.wm_sta4);
                	systemService.saveOrUpdate(wmOmNoticeHEntity);
                	wmNoticeConfService.save(wmNoticeConf);
            	}

            }else{
            	
            	j.setSuccess(false);
        		j.setMsg("不存在此出货单");
        		return j;
            }
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "回单管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新回单管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmNoticeConfEntity wmNoticeConf, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "回单管理更新成功";
		WmNoticeConfEntity t = wmNoticeConfService.get(WmNoticeConfEntity.class, wmNoticeConf.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmNoticeConf, t);
			wmNoticeConfService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "回单管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 回单管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmNoticeConfEntity wmNoticeConf, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmNoticeConf.getId())) {
			wmNoticeConf = wmNoticeConfService.getEntity(WmNoticeConfEntity.class, wmNoticeConf.getId());
			req.setAttribute("wmNoticeConfPage", wmNoticeConf);
		}
		return new ModelAndView("com/zzjee/wm/wmNoticeConf-add");
	}
	/**
	 * 回单管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmNoticeConfEntity wmNoticeConf, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmNoticeConf.getId())) {
			wmNoticeConf = wmNoticeConfService.getEntity(WmNoticeConfEntity.class, wmNoticeConf.getId());
			req.setAttribute("wmNoticeConfPage", wmNoticeConf);
		}
		return new ModelAndView("com/zzjee/wm/wmNoticeConf-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmNoticeConfController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmNoticeConfEntity wmNoticeConf,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmNoticeConfEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmNoticeConf, request.getParameterMap());
		List<WmNoticeConfEntity> wmNoticeConfs = this.wmNoticeConfService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"回单管理");
		modelMap.put(NormalExcelConstants.CLASS,WmNoticeConfEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("回单管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmNoticeConfs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmNoticeConfEntity wmNoticeConf,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"回单管理");
    	modelMap.put(NormalExcelConstants.CLASS,WmNoticeConfEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("回单管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WmNoticeConfEntity> listWmNoticeConfEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmNoticeConfEntity.class,params);
				for (WmNoticeConfEntity wmNoticeConf : listWmNoticeConfEntitys) {
					
					WmOmNoticeHEntity wmOmNoticeHEntity = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", wmNoticeConf.getWmNoticeId());
		            if(wmOmNoticeHEntity!=null){
		            	if(wmOmNoticeHEntity.getOmSta().equals(Constants.wm_sta4)){
		
		            	}else{
		                	wmOmNoticeHEntity.setOmSta(Constants.wm_sta4);
		                	systemService.saveOrUpdate(wmOmNoticeHEntity);
		            	}
		            }else{
		            	wmNoticeConfService.save(wmNoticeConf);
		            }
					
					
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
	public List<WmNoticeConfEntity> list() {
		List<WmNoticeConfEntity> listWmNoticeConfs=wmNoticeConfService.getList(WmNoticeConfEntity.class);
		return listWmNoticeConfs;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmNoticeConfEntity task = wmNoticeConfService.get(WmNoticeConfEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmNoticeConfEntity wmNoticeConf, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmNoticeConfEntity>> failures = validator.validate(wmNoticeConf);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmNoticeConfService.save(wmNoticeConf);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmNoticeConf.getId();
		URI uri = uriBuilder.path("/rest/wmNoticeConfController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmNoticeConfEntity wmNoticeConf) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmNoticeConfEntity>> failures = validator.validate(wmNoticeConf);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmNoticeConfService.saveOrUpdate(wmNoticeConf);
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
		wmNoticeConfService.deleteEntityById(WmNoticeConfEntity.class, id);
	}
}
