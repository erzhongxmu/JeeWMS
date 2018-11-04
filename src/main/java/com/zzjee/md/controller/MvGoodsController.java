package com.zzjee.md.controller;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.md.service.MvGoodsServiceI;

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
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
 * @Description: 商品视图
 * @author erzhongxmu
 * @date 2017-11-02 23:59:31
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mvGoodsController")
public class MvGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MvGoodsController.class);

	@Autowired
	private MvGoodsServiceI mvGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	public String goodsCode ;
	public String coscode ;

	/**
	 * 商品视图列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(MvGoodsEntity mvGoods,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("com/zzjee/md/mvGoodsList");
		goodsCode =  oConvertUtils.getString(request.getParameter("ids")) ;
//		coscode =  oConvertUtils.getString(request.getParameter("coscode"));
//		mv.addObject("coscode", oConvertUtils.getString(request.getParameter("coscode")));
//		mv.addObject("goodsCode", mvGoods.getGoodsCode());
		return mv;
	
	}
	/**
	 * 删除销售订单
	 *
	 * @return
	 */
	@RequestMapping(params = "setvalue")
	@ResponseBody
	public AjaxJson setvalue(String cusCode, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		System.out.print("ResourceUtil.getSessionUserName().setBrowser(cusCode)*******"+		ResourceUtil.getSessionUserName().getBrowser());
		ResourceUtil.getSessionUserName().setBrowser(cusCode);
		System.out.print("ResourceUtil.getSessionUserName().setBrowser(cusCode)########"+		ResourceUtil.getSessionUserName().getBrowser());

		coscode = cusCode;
		return j;
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(MvGoodsEntity mvGoods,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MvGoodsEntity.class, dataGrid);
		//查询条件组装器

		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mvGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
			System.out.print("ResourceUtil.getSessionUserName().setBrowser(cusCode)data"+		ResourceUtil.getSessionUserName().getBrowser());

			if(!StringUtil.isEmpty(ResourceUtil.getSessionUserName().getBrowser())){
				cq.like("cusCode", ResourceUtil.getSessionUserName().getBrowser());
			}
			cq.add();
			if(!StringUtil.isEmpty(goodsCode)){
				cq.like("goodsName", "%"+goodsCode+"%");
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

//		TSUser user = ResourceUtil.getSessionUserName();
//		String roles = "";
		System.out.println("datagrid"+goodsCode);
	

//		if (user != null) {
//			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
//			for (TSRoleUser ru : rUsers) {
//				TSRole role = ru.getTSRole();
//				roles += role.getRoleCode() + ",";
//			}
//			if (roles.length() > 0) {
//				roles = roles.substring(0, roles.length() - 1);
//			}
//			if(roles.equals("CUS")){
//				cq.eq("cusCode", user.getUserName());
//				
//			}
//		}
		cq.add();
		this.mvGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除商品视图
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(MvGoodsEntity mvGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		mvGoods = systemService.getEntity(MvGoodsEntity.class, mvGoods.getId());
		message = "商品视图删除成功";
		try{
			mvGoodsService.delete(mvGoods);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品视图删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品视图
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品视图删除成功";
		try{
			for(String id:ids.split(",")){
				MvGoodsEntity mvGoods = systemService.getEntity(MvGoodsEntity.class, 
				id
				);
				mvGoodsService.delete(mvGoods);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "商品视图删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商品视图
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(MvGoodsEntity mvGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品视图添加成功";
		try{
			mvGoodsService.save(mvGoods);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品视图添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新商品视图
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(MvGoodsEntity mvGoods, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品视图更新成功";
		MvGoodsEntity t = mvGoodsService.get(MvGoodsEntity.class, mvGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(mvGoods, t);
			mvGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品视图更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 商品视图新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(MvGoodsEntity mvGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mvGoods.getId())) {
			mvGoods = mvGoodsService.getEntity(MvGoodsEntity.class, mvGoods.getId());
			req.setAttribute("mvGoodsPage", mvGoods);
		}
		return new ModelAndView("com/zzjee/md/mvGoods-add");
	}
	/**
	 * 商品视图编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(MvGoodsEntity mvGoods, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mvGoods.getId())) {
			mvGoods = mvGoodsService.getEntity(MvGoodsEntity.class, mvGoods.getId());
			req.setAttribute("mvGoodsPage", mvGoods);
		}
		return new ModelAndView("com/zzjee/md/mvGoods-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","mvGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(MvGoodsEntity mvGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(MvGoodsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mvGoods, request.getParameterMap());
		List<MvGoodsEntity> mvGoodss = this.mvGoodsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品视图");
		modelMap.put(NormalExcelConstants.CLASS,MvGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品视图列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,mvGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(MvGoodsEntity mvGoods,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"商品视图");
    	modelMap.put(NormalExcelConstants.CLASS,MvGoodsEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品视图列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<MvGoodsEntity> listMvGoodsEntitys = ExcelImportUtil.importExcel(file.getInputStream(),MvGoodsEntity.class,params);
				for (MvGoodsEntity mvGoods : listMvGoodsEntitys) {
					mvGoodsService.save(mvGoods);
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
	public List<MvGoodsEntity> list() {
		List<MvGoodsEntity> listMvGoodss=mvGoodsService.getList(MvGoodsEntity.class);
		return listMvGoodss;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		MvGoodsEntity task = mvGoodsService.get(MvGoodsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody MvGoodsEntity mvGoods, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MvGoodsEntity>> failures = validator.validate(mvGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mvGoodsService.save(mvGoods);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = mvGoods.getId();
		URI uri = uriBuilder.path("/rest/mvGoodsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody MvGoodsEntity mvGoods) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MvGoodsEntity>> failures = validator.validate(mvGoods);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mvGoodsService.saveOrUpdate(mvGoods);
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
		mvGoodsService.deleteEntityById(MvGoodsEntity.class, id);
	}
}
