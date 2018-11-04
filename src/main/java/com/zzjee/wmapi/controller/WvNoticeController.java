package com.zzjee.wmapi.controller;
import com.zzjee.api.ResultDO;
import com.zzjee.wm.entity.WmInQmIEntity;
import com.zzjee.wmapi.entity.WvGiEntity;
import com.zzjee.wmapi.entity.WvGiNoticeEntity;
import com.zzjee.wmapi.entity.WvNoticeEntity;
import com.zzjee.wmapi.service.WvNoticeServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.wmUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
 * @Description: wv_notice
 * @author erzhongxmu
 * @date 2018-05-30 20:21:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wvNoticeController")
public class WvNoticeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WvNoticeController.class);

	@Autowired
	private WvNoticeServiceI wvNoticeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * wv_notice列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wmapi/wvNoticeList");
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
	public void datagrid(WvNoticeEntity wvNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvNoticeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvNotice, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wvNoticeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除wv_notice
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WvNoticeEntity wvNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wvNotice = systemService.getEntity(WvNoticeEntity.class, wvNotice.getId());
		message = "wv_notice删除成功";
		try{
			wvNoticeService.delete(wvNotice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "wv_notice删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除wv_notice
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_notice删除成功";
		try{
			for(String id:ids.split(",")){
				WvNoticeEntity wvNotice = systemService.getEntity(WvNoticeEntity.class, 
				id
				);
				wvNoticeService.delete(wvNotice);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "wv_notice删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加wv_notice
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WvNoticeEntity wvNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_notice添加成功";
		try{
			wvNoticeService.save(wvNotice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "wv_notice添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新wv_notice
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WvNoticeEntity wvNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_notice更新成功";
		WvNoticeEntity t = wvNoticeService.get(WvNoticeEntity.class, wvNotice.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wvNotice, t);
			wvNoticeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "wv_notice更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * wv_notice新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WvNoticeEntity wvNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvNotice.getId())) {
			wvNotice = wvNoticeService.getEntity(WvNoticeEntity.class, wvNotice.getId());
			req.setAttribute("wvNoticePage", wvNotice);
		}
		return new ModelAndView("com/zzjee/wmapi/wvNotice-add");
	}
	/**
	 * wv_notice编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WvNoticeEntity wvNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvNotice.getId())) {
			wvNotice = wvNoticeService.getEntity(WvNoticeEntity.class, wvNotice.getId());
			req.setAttribute("wvNoticePage", wvNotice);
		}
		return new ModelAndView("com/zzjee/wmapi/wvNotice-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wvNoticeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WvNoticeEntity wvNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WvNoticeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvNotice, request.getParameterMap());
		List<WvNoticeEntity> wvNotices = this.wvNoticeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"wv_notice");
		modelMap.put(NormalExcelConstants.CLASS,WvNoticeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wv_notice列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wvNotices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WvNoticeEntity wvNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"wv_notice");
    	modelMap.put(NormalExcelConstants.CLASS,WvNoticeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wv_notice列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WvNoticeEntity> listWvNoticeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WvNoticeEntity.class,params);
				for (WvNoticeEntity wvNotice : listWvNoticeEntitys) {
					wvNoticeService.save(wvNotice);
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
	public ResponseEntity<?>  list( @RequestParam(value="username", required=false) String username, @RequestParam(value="searchstr", required=false)String searchstr, @RequestParam(value="searchstr2", required=false)String searchstr2) {
//		return listWvGis;


		ResultDO D0 = new  ResultDO();
		String hql = " from WvNoticeEntity where 1 = 1 ";
		D0.setOK(true);
		if(!StringUtil.isEmpty(searchstr)) {
			hql=hql+"  and noticeId like '%" + searchstr + "%'";
		}
		if(!StringUtil.isEmpty(searchstr2)) {
			try{
				String shpbianma = wmUtil.getmdgoodsbytiaoma(searchstr2);
				if(StringUtil.isNotEmpty(shpbianma)){
					searchstr2=shpbianma;
				}
			}catch (Exception e){

			}
			hql=hql+"  and goodsCode like '%" + searchstr2 + "%'";
		}

		List<WvNoticeEntity> listWvNotices=wvNoticeService.findHql(hql);
		D0.setOK(true);

		List<WvNoticeEntity> result = new ArrayList<WvNoticeEntity>();
		int i = 0;
		for (WvNoticeEntity t :listWvNotices){

			i++;
			if(i>100){
				break;
			}
			result.add(t);
		}

		D0.setObj(result);
		return new ResponseEntity(D0, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WvNoticeEntity task = wvNoticeService.get(WvNoticeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WvNoticeEntity wvNotice, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvNoticeEntity>> failures = validator.validate(wvNotice);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvNoticeService.save(wvNotice);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wvNotice.getId();
		URI uri = uriBuilder.path("/rest/wvNoticeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WvNoticeEntity wvNotice) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvNoticeEntity>> failures = validator.validate(wvNotice);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvNoticeService.saveOrUpdate(wvNotice);
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
		wvNoticeService.deleteEntityById(WvNoticeEntity.class, id);
	}
}
