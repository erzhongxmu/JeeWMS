package com.zzjee.wm.controller;
import com.alibaba.fastjson.JSON;
import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmInQmIEntity;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import com.zzjee.wm.entity.WmToUpGoodsEntity;
import com.zzjee.wm.page.Delrowpage;
import com.zzjee.wm.page.wmtoupgoodspage;
import com.zzjee.wm.service.WmToUpGoodsServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmapi.entity.WvNoticeEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.CopyUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.*;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;

import java.io.OutputStream;

import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

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
 * @Description: 上架列表
 * @author erzhongxmu
 * @date 2017-09-11 15:08:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmToUpGoodsController")
public class WmToUpGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmToUpGoodsController.class);

	@Autowired
	private WmToUpGoodsServiceI wmToUpGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 上架列表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmToUpGoodsList");
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
	public void datagrid(WmToUpGoodsEntity wmToUpGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmToUpGoodsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmToUpGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		cq.add();
		this.wmToUpGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除上架列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmToUpGoodsEntity wmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmToUpGoods = systemService.getEntity(WmToUpGoodsEntity.class, wmToUpGoods.getId());
		message = "上架列表删除成功";
		try{
			wmToUpGoodsService.delete(wmToUpGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "上架列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除上架列表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上架列表删除成功";
		try{
			for(String id:ids.split(",")){
				WmToUpGoodsEntity wmToUpGoods = systemService.getEntity(WmToUpGoodsEntity.class, 
				id
				);
				wmToUpGoodsService.delete(wmToUpGoods);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "上架列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	 
		@RequestMapping(params = "doGettextzy",method ={RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public AjaxJson doGettextzy(HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", oConvertUtils.getString(request.getParameter("goodsid"))) ;
			if(mvgoods==null){
				j.setSuccess(false);
				j.setMsg("不存在此商品");
			}
			j.setObj(mvgoods);
			return j;
		}
	 

	/**
	 * 添加上架列表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmToUpGoodsEntity wmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上架列表添加成功";
		try{
			wmToUpGoods.setOrderId("ZY");
			MvGoodsEntity mvgoods = new MvGoodsEntity();
			mvgoods = systemService.findUniqueByProperty(
					MvGoodsEntity.class, "goodsCode",
					wmToUpGoods.getGoodsId());
			if(mvgoods!=null){
				wmToUpGoods.setGoodsName(mvgoods.getGoodsName());
				wmToUpGoods.setBaseUnit(mvgoods.getBaseunit());
			wmToUpGoods.setGoodsUnit(mvgoods.getShlDanWei());
			if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
				try {
					wmToUpGoods.setBaseGoodscount(String.valueOf(Long
							.parseLong(mvgoods.getChlShl())
							* Long.parseLong(wmToUpGoods.getGoodsQua())));
				} catch (Exception e) {
					// TODO: handle exception
				}

			} else {
				wmToUpGoods.setBaseGoodscount(wmToUpGoods
						.getGoodsQua());
			}
			wmToUpGoodsService.save(wmToUpGoods);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "上架列表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新上架列表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmToUpGoodsEntity wmToUpGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上架列表更新成功";
		WmToUpGoodsEntity t = wmToUpGoodsService.get(WmToUpGoodsEntity.class, wmToUpGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmToUpGoods, t);
			wmToUpGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "上架列表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * gengxin
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "updateRows")
	@ResponseBody
	public AjaxJson updateRows(wmtoupgoodspage page){
		String message = null;
		List<WmToUpGoodsEntity> demos=page.getUprows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmToUpGoodsEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmToUpGoodsEntity t =systemService.get(WmToUpGoodsEntity.class, jeecgDemo.getId());
					try {


						MyBeanUtils.copyBeanNotNull2Bean(jeecgDemo, t);

						systemService.saveOrUpdate(t);


//						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}

	/**
	 * 上架列表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmToUpGoodsEntity wmToUpGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmToUpGoods.getId())) {
			wmToUpGoods = wmToUpGoodsService.getEntity(WmToUpGoodsEntity.class, wmToUpGoods.getId());
			req.setAttribute("wmToUpGoodsPage", wmToUpGoods);
		}
		return new ModelAndView("com/zzjee/wm/wmToUpGoods-add");
	}
	/**
	 * 上架列表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmToUpGoodsEntity wmToUpGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmToUpGoods.getId())) {
			wmToUpGoods = wmToUpGoodsService.getEntity(WmToUpGoodsEntity.class, wmToUpGoods.getId());
			req.setAttribute("wmToUpGoodsPage", wmToUpGoods);
		}
		return new ModelAndView("com/zzjee/wm/wmToUpGoods-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmToUpGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmToUpGoodsEntity wmToUpGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmToUpGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmToUpGoods, request.getParameterMap());
		List<WmToUpGoodsEntity> wmToUpGoodss = this.wmToUpGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"上架列表");
		modelMap.put(NormalExcelConstants.CLASS,WmToUpGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("上架列表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmToUpGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmToUpGoodsEntity wmToUpGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"上架列表");
    	modelMap.put(NormalExcelConstants.CLASS,WmToUpGoodsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("上架列表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WmToUpGoodsEntity> listWmToUpGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmToUpGoodsEntity.class,params);
				for (WmToUpGoodsEntity wmToUpGoods : listWmToUpGoodsEntitys) {
					wmToUpGoodsService.save(wmToUpGoods);
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
	public ResponseEntity<?>  list(@RequestParam String username, @RequestParam String searchstr) {


		ResultDO D0 = new  ResultDO();
		List<WmToUpGoodsEntity> listWmToUpGoodss=wmToUpGoodsService.getList(WmToUpGoodsEntity.class);
		D0.setOK(true);
		D0.setObj(listWmToUpGoodss);
		return new ResponseEntity(D0, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmToUpGoodsEntity task = wmToUpGoodsService.get(WmToUpGoodsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestParam String wmToUpGoodsstr, UriComponentsBuilder uriBuilder) {
		ResultDO D0 = new  ResultDO();
		WmToUpGoodsEntity wmToUpGoods = (WmToUpGoodsEntity) JSONHelper.json2Object(wmToUpGoodsstr,WmToUpGoodsEntity.class);
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmToUpGoodsEntity>> failures = validator.validate(wmToUpGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			D0.setOK(true);
			WmInQmIEntity wmInQmIEntity = systemService.get(WmInQmIEntity.class,wmToUpGoods.getWmToUpId());
			if(wmInQmIEntity!=null){
				wmInQmIEntity.setBinSta("Y");
				systemService.updateEntitie(wmInQmIEntity);
			}
			wmToUpGoods.setGoodsName(wmInQmIEntity.getGoodsName());
			wmToUpGoods.setCreateDate(DateUtils.getDate());
			wmToUpGoodsService.save(wmToUpGoods);
		} catch (Exception e) {
			e.printStackTrace();
			D0.setOK(false);
		}

		return new ResponseEntity(D0, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmToUpGoodsEntity wmToUpGoods) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
//		Set<ConstraintViolation<WmToUpGoodsEntity>> failures = validator.validate(wmToUpGoods);
//		if (!failures.isEmpty()) {
//			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
//		}
		ResultDO D0 = new  ResultDO();
		//保存
		try{
			WmToUpGoodsEntity t = wmToUpGoodsService.get(WmToUpGoodsEntity.class,wmToUpGoods.getId());
			MyBeanUtils.copyBeanNotNull2Bean(wmToUpGoods, t);
			wmToUpGoodsService.saveOrUpdate(t);
			D0.setOK(true);

		} catch (Exception e) {
			e.printStackTrace();
			D0.setOK(false);
//			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity(D0, HttpStatus.OK);
		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
//		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		wmToUpGoodsService.deleteEntityById(WmToUpGoodsEntity.class, id);
	}
}
