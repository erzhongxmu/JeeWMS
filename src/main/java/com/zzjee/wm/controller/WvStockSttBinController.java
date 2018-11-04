package com.zzjee.wm.controller;
import com.zzjee.wm.entity.WmSttInGoodsEntity;
import com.zzjee.wm.entity.WvStockEntity;
import com.zzjee.wm.entity.WvStockSttBinEntity;
import com.zzjee.wm.service.WvStockSttBinServiceI;

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
 * @Description: 储位盘点
 * @author erzhongxmu
 * @date 2017-11-20 22:48:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wvStockSttBinController")
public class WvStockSttBinController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WvStockSttBinController.class);

	@Autowired
	private WvStockSttBinServiceI wvStockSttBinService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 储位盘点列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wvStockSttBinList");
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
	public void datagrid(WvStockSttBinEntity wvStockSttBin,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvStockSttBinEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvStockSttBin, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wvStockSttBinService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除储位盘点
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WvStockSttBinEntity wvStockSttBin, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wvStockSttBin = systemService.getEntity(WvStockSttBinEntity.class, wvStockSttBin.getId());
		message = "储位盘点删除成功";
		try{
			wvStockSttBinService.delete(wvStockSttBin);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "储位盘点删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除储位盘点
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "储位盘点删除成功";
		try{
			for(String id:ids.split(",")){
				WvStockSttBinEntity wvStockSttBin = systemService.getEntity(WvStockSttBinEntity.class, 
				id
				);
				wvStockSttBinService.delete(wvStockSttBin);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "储位盘点删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加储位盘点
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WvStockSttBinEntity wvStockSttBin, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "储位盘点添加成功";
		try{
			wvStockSttBinService.save(wvStockSttBin);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "储位盘点添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新储位盘点
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WvStockSttBinEntity wvStockSttBin, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "储位盘点更新成功";
		WvStockSttBinEntity t = wvStockSttBinService.get(WvStockSttBinEntity.class, wvStockSttBin.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wvStockSttBin, t);
			wvStockSttBinService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "储位盘点更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "dostt")
	@ResponseBody
	public AjaxJson doStt(WvStockSttBinEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成盘点单成功";
		WvStockSttBinEntity t = wvStockSttBinService.get(WvStockSttBinEntity.class, request
				.getParameter("id").toString());
		String sttId = "";
		sttId = DateUtils.date2Str(DateUtils.time_sdf);
		try {
			WmSttInGoodsEntity wmstt = new WmSttInGoodsEntity();
			wmstt.setSttId(sttId);
			wmstt.setBinId(t.getKuWeiBianMa());
			wmstt.setTinId(t.getBinId());
			wmstt.setCusCode(t.getCusCode());
			wmstt.setCusName(t.getZhongWenQch());
			wmstt.setGoodsId(t.getGoodsId());
			wmstt.setGoodsName(t.getShpMingCheng());
			wmstt.setGoodsProData(t.getGoodsProData());
			wmstt.setGoodsQua(t.getGoodsQua().toString());
			wmstt.setGoodsUnit(t.getGoodsUnit());
			wmstt.setGoodsBatch(t.getGoodsProData());
			wmstt.setSttType(request
				.getParameter("stttype").toString());//01 托盘盘点  02 储位盘点
			wmstt.setSttSta("计划中");
//			wmstt.setTinId(t.getBinId());
			systemService.save(wmstt);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成盘点单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 储位盘点新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WvStockSttBinEntity wvStockSttBin, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvStockSttBin.getId())) {
			wvStockSttBin = wvStockSttBinService.getEntity(WvStockSttBinEntity.class, wvStockSttBin.getId());
			req.setAttribute("wvStockSttBinPage", wvStockSttBin);
		}
		return new ModelAndView("com/zzjee/wm/wvStockSttBin-add");
	}
	/**
	 * 储位盘点编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WvStockSttBinEntity wvStockSttBin, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvStockSttBin.getId())) {
			wvStockSttBin = wvStockSttBinService.getEntity(WvStockSttBinEntity.class, wvStockSttBin.getId());
			req.setAttribute("wvStockSttBinPage", wvStockSttBin);
		}
		return new ModelAndView("com/zzjee/wm/wvStockSttBin-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wvStockSttBinController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WvStockSttBinEntity wvStockSttBin,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WvStockSttBinEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wvStockSttBin, request.getParameterMap());
		List<WvStockSttBinEntity> wvStockSttBins = this.wvStockSttBinService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"储位盘点");
		modelMap.put(NormalExcelConstants.CLASS,WvStockSttBinEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("储位盘点列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wvStockSttBins);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WvStockSttBinEntity wvStockSttBin,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"储位盘点");
    	modelMap.put(NormalExcelConstants.CLASS,WvStockSttBinEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("储位盘点列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WvStockSttBinEntity> listWvStockSttBinEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WvStockSttBinEntity.class,params);
				for (WvStockSttBinEntity wvStockSttBin : listWvStockSttBinEntitys) {
					wvStockSttBinService.save(wvStockSttBin);
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
	public List<WvStockSttBinEntity> list() {
		List<WvStockSttBinEntity> listWvStockSttBins=wvStockSttBinService.getList(WvStockSttBinEntity.class);
		return listWvStockSttBins;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WvStockSttBinEntity task = wvStockSttBinService.get(WvStockSttBinEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WvStockSttBinEntity wvStockSttBin, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvStockSttBinEntity>> failures = validator.validate(wvStockSttBin);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvStockSttBinService.save(wvStockSttBin);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wvStockSttBin.getId();
		URI uri = uriBuilder.path("/rest/wvStockSttBinController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WvStockSttBinEntity wvStockSttBin) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvStockSttBinEntity>> failures = validator.validate(wvStockSttBin);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wvStockSttBinService.saveOrUpdate(wvStockSttBin);
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
		wvStockSttBinService.deleteEntityById(WvStockSttBinEntity.class, id);
	}
}
