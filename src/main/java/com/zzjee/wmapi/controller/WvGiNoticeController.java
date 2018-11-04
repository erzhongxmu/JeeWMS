package com.zzjee.wmapi.controller;
import com.alibaba.fastjson.JSONObject;
import com.zzjee.api.ResultDO;
import com.zzjee.wmapi.entity.WvGiEntity;
import com.zzjee.wmapi.entity.WvGiNoticeEntity;
import com.zzjee.wmapi.entity.WvNoticeEntity;
import com.zzjee.wmapi.service.WvGiNoticeServiceI;
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
 * @Description: wv_gi_notice
 * @author erzhongxmu
 * @date 2018-05-30 20:20:38
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wvGiNoticeController")
public class WvGiNoticeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WvGiNoticeController.class);

	@Autowired
	private WvGiNoticeServiceI wvGiNoticeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * wv_gi_notice列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wmapi/wvGiNoticeList");
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
	public void datagrid(WvGiNoticeEntity wvGiNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvGiNoticeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvGiNotice, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wvGiNoticeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除wv_gi_notice
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WvGiNoticeEntity wvGiNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wvGiNotice = systemService.getEntity(WvGiNoticeEntity.class, wvGiNotice.getId());
		message = "wv_gi_notice删除成功";
		try{
			wvGiNoticeService.delete(wvGiNotice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "wv_gi_notice删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除wv_gi_notice
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_gi_notice删除成功";
		try{
			for(String id:ids.split(",")){
				WvGiNoticeEntity wvGiNotice = systemService.getEntity(WvGiNoticeEntity.class, 
				id
				);
				wvGiNoticeService.delete(wvGiNotice);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "wv_gi_notice删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加wv_gi_notice
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WvGiNoticeEntity wvGiNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_gi_notice添加成功";
		try{
			wvGiNoticeService.save(wvGiNotice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "wv_gi_notice添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新wv_gi_notice
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WvGiNoticeEntity wvGiNotice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wv_gi_notice更新成功";
		WvGiNoticeEntity t = wvGiNoticeService.get(WvGiNoticeEntity.class, wvGiNotice.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wvGiNotice, t);
			wvGiNoticeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "wv_gi_notice更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * wv_gi_notice新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WvGiNoticeEntity wvGiNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvGiNotice.getId())) {
			wvGiNotice = wvGiNoticeService.getEntity(WvGiNoticeEntity.class, wvGiNotice.getId());
			req.setAttribute("wvGiNoticePage", wvGiNotice);
		}
		return new ModelAndView("com/zzjee/wmapi/wvGiNotice-add");
	}
	/**
	 * wv_gi_notice编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WvGiNoticeEntity wvGiNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvGiNotice.getId())) {
			wvGiNotice = wvGiNoticeService.getEntity(WvGiNoticeEntity.class, wvGiNotice.getId());
			req.setAttribute("wvGiNoticePage", wvGiNotice);
		}
		return new ModelAndView("com/zzjee/wmapi/wvGiNotice-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wvGiNoticeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WvGiNoticeEntity wvGiNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WvGiNoticeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvGiNotice, request.getParameterMap());
		List<WvGiNoticeEntity> wvGiNotices = this.wvGiNoticeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"wv_gi_notice");
		modelMap.put(NormalExcelConstants.CLASS,WvGiNoticeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wv_gi_notice列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wvGiNotices);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WvGiNoticeEntity wvGiNotice,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"wv_gi_notice");
    	modelMap.put(NormalExcelConstants.CLASS,WvGiNoticeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wv_gi_notice列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WvGiNoticeEntity> listWvGiNoticeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WvGiNoticeEntity.class,params);
				for (WvGiNoticeEntity wvGiNotice : listWvGiNoticeEntitys) {
					wvGiNoticeService.save(wvGiNotice);
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
	public ResponseEntity<?>  list() {

//		public ResponseEntity<?>  list(@RequestParam String username, @RequestParam String searchstr) {
		ResultDO D0 = new  ResultDO();
		List<WvGiNoticeEntity> listWvGiNotices=wvGiNoticeService.getList(WvGiNoticeEntity.class);
		D0.setOK(true);
		D0.setObj(listWvGiNotices);
		return new ResponseEntity(D0, HttpStatus.OK);
	}
	@RequestMapping(value = "/search",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?>  lists( @RequestParam(value="username", required=false) String username, @RequestParam(value="searchstr", required=false)String searchstr, @RequestParam(value="searchstr2", required=false)String searchstr2) {
//		return listWvGis;


		ResultDO D0 = new  ResultDO();
		String hql = " from WvGiNoticeEntity where 1 = 1 ";
		D0.setOK(true);
		if(!StringUtil.isEmpty(searchstr)) {
			hql=hql+"  and omNoticeId like '%" + searchstr + "%'";
		}
		if(!StringUtil.isEmpty(searchstr2)) {
			try{
				String shpbianma = wmUtil.getmdgoodsbytiaoma(searchstr2);
				if(StringUtil.isNotEmpty(shpbianma)){
					searchstr2=shpbianma;
				}
			}catch (Exception e){

			}
			hql=hql+"  and goodsId like '%" + searchstr2 + "%'";
		}

		List<WvGiNoticeEntity> listWvGiNotices=wvGiNoticeService.findHql(hql);
//		public ResponseEntity<?>  list(@RequestParam String username, @RequestParam String searchstr) {
//		ResultDO D0 = new  ResultDO();
//		List<WvGiNoticeEntity> listWvGiNotices=wvGiNoticeService.getList(WvGiNoticeEntity.class);
		D0.setOK(true);


		List<WvGiNoticeEntity> result = new ArrayList<WvGiNoticeEntity>();
		int i = 0;
		for (WvGiNoticeEntity t :listWvGiNotices){

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
		WvGiNoticeEntity task = wvGiNoticeService.get(WvGiNoticeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WvGiNoticeEntity wvGiNotice, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvGiNoticeEntity>> failures = validator.validate(wvGiNotice);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvGiNoticeService.save(wvGiNotice);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wvGiNotice.getId();
		URI uri = uriBuilder.path("/rest/wvGiNoticeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WvGiNoticeEntity wvGiNotice) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvGiNoticeEntity>> failures = validator.validate(wvGiNotice);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvGiNoticeService.saveOrUpdate(wvGiNotice);
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
		wvGiNoticeService.deleteEntityById(WvGiNoticeEntity.class, id);
	}
}
