package com.zzjee.wm.controller;
import com.zzjee.ba.entity.BaCostConfEntity;
import com.zzjee.wm.entity.WmCusCostHEntity;
import com.zzjee.wm.service.WmCusCostHServiceI;
import com.zzjee.wm.page.WmCusCostHPage;
import com.zzjee.wm.entity.WmCusCostIEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

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
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;

import java.util.HashMap;
/**   
 * @Title: Controller
 * @Description: 客户费用
 * @author erzhongxmu
 * @date 2017-09-26 15:12:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmCusCostHController")
public class WmCusCostHController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmCusCostHController.class);

	@Autowired
	private WmCusCostHServiceI wmCusCostHService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;

	/**
	 * 客户费用列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmCusCostHList");
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
	public void datagrid(WmCusCostHEntity wmCusCostH,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmCusCostHEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmCusCostH);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wmCusCostHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除客户费用
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmCusCostHEntity wmCusCostH, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		wmCusCostH = systemService.getEntity(WmCusCostHEntity.class, wmCusCostH.getId());
		String message = "客户费用删除成功";
		try{
			wmCusCostHService.delMain(wmCusCostH);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户费用删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除客户费用
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "客户费用删除成功";
		try{
			for(String id:ids.split(",")){
				WmCusCostHEntity wmCusCostH = systemService.getEntity(WmCusCostHEntity.class,
				id
				);
				wmCusCostHService.delMain(wmCusCostH);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "客户费用删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加客户费用
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmCusCostHEntity wmCusCostH,WmCusCostHPage wmCusCostHPage, HttpServletRequest request) {
		List<WmCusCostIEntity> wmCusCostIList =  wmCusCostHPage.getWmCusCostIList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			wmCusCostHService.addMain(wmCusCostH, wmCusCostIList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "客户费用添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(wmCusCostH);
		return j;
	}
	/**
	 * 更新客户费用
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmCusCostHEntity wmCusCostH,WmCusCostHPage wmCusCostHPage, HttpServletRequest request) {
		List<WmCusCostIEntity> wmCusCostIList =  wmCusCostHPage.getWmCusCostIList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			wmCusCostHService.updateMain(wmCusCostH, wmCusCostIList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新客户费用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 客户费用新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmCusCostHEntity wmCusCostH, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmCusCostH.getId())) {
			wmCusCostH = wmCusCostHService.getEntity(WmCusCostHEntity.class, wmCusCostH.getId());
			req.setAttribute("wmCusCostHPage", wmCusCostH);
		}
		return new ModelAndView("com/zzjee/wm/wmCusCostH-add");
	}
	
	/**
	 * 客户费用编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmCusCostHEntity wmCusCostH, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmCusCostH.getId())) {
			wmCusCostH = wmCusCostHService.getEntity(WmCusCostHEntity.class, wmCusCostH.getId());
			req.setAttribute("wmCusCostHPage", wmCusCostH);
		}
		return new ModelAndView("com/zzjee/wm/wmCusCostH-update");
	}
	
	
	/**
	 * 加载明细列表[费用项目]
	 * 
	 * @return
	 */
	@RequestMapping(params = "wmCusCostIList")
	public ModelAndView wmCusCostIList(WmCusCostHEntity wmCusCostH, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = wmCusCostH.getId();
			//查询-费用项目
			String  hql0 = "from WmCusCostIEntity where 1 = 1 AND cUS_COST_ID = ? ";
		    try{
		    	List<WmCusCostIEntity> wmCusCostIEntityList = systemService.findHql(hql0,id0);
		    	if(wmCusCostIEntityList.size()==0){
		    	 hql0 = "from BaCostConfEntity where 1 =  1 ";
				try{
					List<BaCostConfEntity> baCostConfEntityList = systemService.findByQueryString(hql0) ;
					for (BaCostConfEntity baCostConfEntity : baCostConfEntityList) {
						WmCusCostIEntity  t = new WmCusCostIEntity();
						t.setCostCode(baCostConfEntity.getCostCode());
						t.setCostJg(baCostConfEntity.getCostJg());
						t.setCostSl(baCostConfEntity.getCostSl());
						t.setCostZk(baCostConfEntity.getCostZk());
						t.setCostBhs(baCostConfEntity.getCostBhs());
						t.setCostHs(baCostConfEntity.getCostHs());
						wmCusCostIEntityList.add(t);
					}
				}catch(Exception e){
					logger.info(e.getMessage());
				}
		    	}
				req.setAttribute("wmCusCostIList", wmCusCostIEntityList);
			}catch(Exception e){
				logger.info(e.getMessage());
			}
		//===================================================================================
		return new ModelAndView("com/zzjee/wm/wmCusCostIList");
	}

    /**
    * 导出excel
    *
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(WmCusCostHEntity wmCusCostH,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(WmCusCostHEntity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmCusCostH);
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<WmCusCostHEntity> list=this.wmCusCostHService.getListByCriteriaQuery(cq, false);
    	List<WmCusCostHPage> pageList=new ArrayList<WmCusCostHPage>();
        if(list!=null&&list.size()>0){
        	for(WmCusCostHEntity entity:list){
        		try{
        		WmCusCostHPage page=new WmCusCostHPage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    Object id0 = entity.getId();
				    String hql0 = "from WmCusCostIEntity where 1 = 1 AND cUS_COST_ID = ? ";
        	        List<WmCusCostIEntity> wmCusCostIEntityList = systemService.findHql(hql0,id0);
            		page.setWmCusCostIList(wmCusCostIEntityList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"客户费用");
        map.put(NormalExcelConstants.CLASS,WmCusCostHPage.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("客户费用列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

    /**
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
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
			params.setHeadRows(2);
			params.setNeedSave(true);
			try {
				List<WmCusCostHPage> list =  ExcelImportUtil.importExcel(file.getInputStream(), WmCusCostHPage.class, params);
				WmCusCostHEntity entity1=null;
				for (WmCusCostHPage page : list) {
					entity1=new WmCusCostHEntity();
					MyBeanUtils.copyBeanNotNull2Bean(page,entity1);
		            wmCusCostHService.addMain(entity1, page.getWmCusCostIList());
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
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"客户费用");
		map.put(NormalExcelConstants.CLASS,WmCusCostHPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("客户费用列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	* 导入功能跳转
	*
	* @return
	*/
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "wmCusCostHController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

 	
 	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<WmCusCostHEntity> list() {
		List<WmCusCostHEntity> listWmCusCostHs=wmCusCostHService.getList(WmCusCostHEntity.class);
		return listWmCusCostHs;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmCusCostHEntity task = wmCusCostHService.get(WmCusCostHEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}
 	
 	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmCusCostHPage wmCusCostHPage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmCusCostHPage>> failures = validator.validate(wmCusCostHPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<WmCusCostIEntity> wmCusCostIList =  wmCusCostHPage.getWmCusCostIList();
		
		WmCusCostHEntity wmCusCostH = new WmCusCostHEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(wmCusCostH,wmCusCostHPage);
		}catch(Exception e){
            logger.info(e.getMessage());
        }
		wmCusCostHService.addMain(wmCusCostH, wmCusCostIList);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmCusCostHPage.getId();
		URI uri = uriBuilder.path("/rest/wmCusCostHController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmCusCostHPage wmCusCostHPage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmCusCostHPage>> failures = validator.validate(wmCusCostHPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<WmCusCostIEntity> wmCusCostIList =  wmCusCostHPage.getWmCusCostIList();
		
		WmCusCostHEntity wmCusCostH = new WmCusCostHEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(wmCusCostH,wmCusCostHPage);
		}catch(Exception e){
            logger.info(e.getMessage());
        }
		wmCusCostHService.updateMain(wmCusCostH, wmCusCostIList);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		WmCusCostHEntity wmCusCostH = wmCusCostHService.get(WmCusCostHEntity.class, id);
		wmCusCostHService.delMain(wmCusCostH);
	}
	/**
	 * 获取文件附件信息
	 * 
	 * @param id wmCusCostH主键id
	 */
	@RequestMapping(params = "getFiles")
	@ResponseBody
	public AjaxJson getFiles(String id){
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		List<Map<String,Object>> files = new ArrayList<Map<String,Object>>(0);
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformField();//表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field==null?"":field);
			files.add(file);
		}
		AjaxJson j = new AjaxJson();
		j.setObj(files);
		return j;
	}
}
