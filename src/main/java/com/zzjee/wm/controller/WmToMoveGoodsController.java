package com.zzjee.wm.controller;
import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.omqmpage;
import com.zzjee.wm.page.wmtomovepage;
import com.zzjee.wm.service.WmToMoveGoodsServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.wmUtil;
import org.apache.commons.collections.CollectionUtils;
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
 * @Description: 库存转移
 * @author erzhongxmu
 * @date 2017-09-08 21:03:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmToMoveGoodsController")
public class WmToMoveGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmToMoveGoodsController.class);

	@Autowired
	private WmToMoveGoodsServiceI wmToMoveGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 库存转移列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmToMoveGoodsList");
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
	public void datagrid(WmToMoveGoodsEntity wmToMoveGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmToMoveGoodsEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmToMoveGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		cq.add();
		this.wmToMoveGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除库存转移
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmToMoveGoodsEntity wmToMoveGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmToMoveGoods = systemService.getEntity(WmToMoveGoodsEntity.class, wmToMoveGoods.getId());
		message = "库存转移删除成功";
		try{
			wmToMoveGoods.setMoveSta("已删除");
			wmToMoveGoodsService.saveOrUpdate(wmToMoveGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "库存转移删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除库存转移
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchUpdate")
	@ResponseBody
	public AjaxJson doBatchUpdate(String ids,String moveSta,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "批量设置状态成功";
		try{
			for(String id:ids.split(",")){
				WmToMoveGoodsEntity wmToMoveGoods = systemService.getEntity(WmToMoveGoodsEntity.class,
						id
				);
				wmToMoveGoods.setMoveSta(moveSta);
				wmToMoveGoodsService.updateEntitie(wmToMoveGoods);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "批量设置状态失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除库存转移
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存转移删除成功";
		try{
			for(String id:ids.split(",")){
				WmToMoveGoodsEntity wmToMoveGoods = systemService.getEntity(WmToMoveGoodsEntity.class, 
				id
				);
				wmToMoveGoods.setMoveSta("已删除");
				wmToMoveGoodsService.updateEntitie(wmToMoveGoods);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "库存转移删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}




		@RequestMapping(params = "doGetstock", method = { RequestMethod.GET,
				RequestMethod.POST })
		@ResponseBody
		public AjaxJson doGetstock(HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			WmToMoveGoodsEntity  wmToMoveGoods = new WmToMoveGoodsEntity();
			
			
			
			j.setObj(wmToMoveGoods);
			return j;
		}
	 
	/**
	 * 添加库存转移
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmToMoveGoodsEntity wmToMoveGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存转移添加成功";
		try{
			wmToMoveGoods.setMoveSta("计划中");
			wmToMoveGoodsService.save(wmToMoveGoods);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "库存转移添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新库存转移
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmToMoveGoodsEntity wmToMoveGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "库存转移更新成功";
		WmToMoveGoodsEntity t = wmToMoveGoodsService.get(WmToMoveGoodsEntity.class, wmToMoveGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmToMoveGoods, t);
			wmToMoveGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "库存转移更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}



	/**
	 *
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(wmtomovepage page){
		String message = null;
		List<WmToMoveGoodsEntity> demos=page.getWmtomoverows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmToMoveGoodsEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmToMoveGoodsEntity t =systemService.get(WmToMoveGoodsEntity.class, jeecgDemo.getId());
					try {
						if(!wmUtil.checkstcok( jeecgDemo.getBinFrom(),jeecgDemo.getTinFrom(),jeecgDemo.getGoodsId(),jeecgDemo.getGoodsProData(),jeecgDemo.getBaseGoodscount())) {
						}else{
							message = "批量保存成功";
							t.setMoveSta("已完成");
							MyBeanUtils.copyBeanNotNull2Bean(jeecgDemo, t);
							systemService.saveOrUpdate(t);
							systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}




	/**
	 * 库存转移新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmToMoveGoodsEntity wmToMoveGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmToMoveGoods.getId())) {
			wmToMoveGoods = wmToMoveGoodsService.getEntity(WmToMoveGoodsEntity.class, wmToMoveGoods.getId());
			req.setAttribute("wmToMoveGoodsPage", wmToMoveGoods);
		}
		return new ModelAndView("com/zzjee/wm/wmToMoveGoods-add");
	}
	/**
	 * 库存转移编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmToMoveGoodsEntity wmToMoveGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmToMoveGoods.getId())) {
			wmToMoveGoods = wmToMoveGoodsService.getEntity(WmToMoveGoodsEntity.class, wmToMoveGoods.getId());
			req.setAttribute("wmToMoveGoodsPage", wmToMoveGoods);
		}
		return new ModelAndView("com/zzjee/wm/wmToMoveGoods-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmToMoveGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmToMoveGoodsEntity wmToMoveGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmToMoveGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmToMoveGoods, request.getParameterMap());
		List<WmToMoveGoodsEntity> wmToMoveGoodss = this.wmToMoveGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"库存转移");
		modelMap.put(NormalExcelConstants.CLASS,WmToMoveGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("库存转移列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmToMoveGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmToMoveGoodsEntity wmToMoveGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"库存转移");
    	modelMap.put(NormalExcelConstants.CLASS,WmToMoveGoodsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("库存转移列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WmToMoveGoodsEntity> listWmToMoveGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmToMoveGoodsEntity.class,params);
				for (WmToMoveGoodsEntity wmToMoveGoods : listWmToMoveGoodsEntitys) {
					if(StringUtil.isNotEmpty(wmToMoveGoods.getOrderIdI())){
						try{
							WmToMoveGoodsEntity t = systemService.findByProperty(WmToMoveGoodsEntity.class,"orderIdI",wmToMoveGoods.getOrderIdI()).get(0);
							if(t!=null){
								continue;
							}
						}catch (Exception e){

						}
					}
					wmToMoveGoods.setRunSta("计划中");
					wmToMoveGoodsService.save(wmToMoveGoods);
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
		String hql = " from WmToMoveGoodsEntity where 1 = 1 and moveSta = '计划中' ";
		D0.setOK(true);
		if(!StringUtil.isEmpty(searchstr)) {
			hql=hql+"  and binFrom like '%" + searchstr + "%'";
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

		List<WmToMoveGoodsEntity> listWmToMoveGoodss=wmToMoveGoodsService.findHql(hql);


		List<WmToMoveGoodsEntity> result = new ArrayList<WmToMoveGoodsEntity>();
		int i = 0;
		for (WmToMoveGoodsEntity t :listWmToMoveGoodss){

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
		WmToMoveGoodsEntity task = wmToMoveGoodsService.get(WmToMoveGoodsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmToMoveGoodsEntity wmToMoveGoods, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmToMoveGoodsEntity>> failures = validator.validate(wmToMoveGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmToMoveGoodsService.save(wmToMoveGoods);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmToMoveGoods.getId();
		URI uri = uriBuilder.path("/rest/wmToMoveGoodsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> update(@RequestParam String wmToMoveGoodsstr,
									UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		ResultDO D0 = new  ResultDO();
		WmToMoveGoodsEntity wmToMoveGoods  = (WmToMoveGoodsEntity)JSONHelper.json2Object(wmToMoveGoodsstr,WmToMoveGoodsEntity.class);
		//保存
		try{
			WmToMoveGoodsEntity t = systemService.get(WmToMoveGoodsEntity.class,wmToMoveGoods.getId());
			MyBeanUtils.copyBeanNotNull2Bean(wmToMoveGoods,t);
			wmToMoveGoodsService.saveOrUpdate(t);
			D0.setOK(true);

		} catch (Exception e) {
			e.printStackTrace();
			D0.setOK(false);

		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(D0, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		wmToMoveGoodsService.deleteEntityById(WmToMoveGoodsEntity.class, id);
	}
}
