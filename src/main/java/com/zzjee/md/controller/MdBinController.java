package com.zzjee.md.controller;
import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.service.MdBinServiceI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.entity.TSSmsEntity;
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
 * @Description: 仓位定义
 * @author erzhongxmu
 * @date 2017-08-15 23:17:02
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mdBinController")
public class MdBinController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MdBinController.class);

	@Autowired
	private MdBinServiceI mdBinService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 仓位定义列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/md/mdBinList");
	}
	@RequestMapping(params = "listc")
	public ModelAndView listc(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/md/mdavabinlist");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(MdBinEntity mdBin,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MdBinEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mdBin, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.mdBinService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除仓位定义
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(MdBinEntity mdBin, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		mdBin = systemService.getEntity(MdBinEntity.class, mdBin.getId());
		message = "仓位停用成功";
		try{
			mdBin.setTingYong("Y");
			mdBinService.saveOrUpdate(mdBin);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "仓位停用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	@RequestMapping(params = "getbinall")
	@ResponseBody
	public AjaxJson getNoticeList(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			j.setObj(0);
			String tsql = "select * "
					+ "  from wv_bin_all ws  where 1 = 1 ";
			if(!StringUtil.isEmpty(req.getParameter("binstore"))){
				 tsql =  tsql  + " and ws.bin_store like  '%"+req.getParameter("binstore")+"%' ";
			}
			if(!StringUtil.isEmpty(req.getParameter("binid"))){
				 tsql =  tsql   + "   and ws.binid like  '%"+req.getParameter("binid")+"%' ";
			}
			if(!StringUtil.isEmpty(req.getParameter("des"))){
				 tsql =  tsql  + "  and  ws.des like  '%"+req.getParameter("des")+"%' ";
			}
					System.out.print(tsql);
			List<Map<String, Object>> resultt = systemService
					.findForJdbc(tsql);
//				list = this.tSSmsService.getMsgsList(curUser,curDate);
				//将List转换成JSON存储
				JSONArray result = new JSONArray();
		        if(resultt!=null && resultt.size()>0){
		        	for(int i=0;i<resultt.size();i++){
		    			JSONObject jsonParts = new JSONObject();
		    			jsonParts.put("bin_store", resultt.get(i).get("bin_store"));
		    			jsonParts.put("binid", resultt.get(i).get("binid"));
		    			jsonParts.put("des", resultt.get(i).get("des"));
		    			jsonParts.put("tincount", resultt.get(i).get("tincount"));
			    		result.add(jsonParts);	
		    		}
		        	j.setObj(resultt.size());
				
				
				Map<String,Object> attrs = new HashMap<String, Object>();
				attrs.put("messageList", result);
//				String tip = MutiLangUtil.getMutiLangInstance().getLang("message.tip");
//				attrs.put("tip", tip);
//				String seeAll = MutiLangUtil.getMutiLangInstance().getLang("message.seeAll");
//				attrs.put("seeAll", seeAll);
				j.setAttributes(attrs);
		    }
		} catch (Exception e) {
			j.setSuccess(false);
		}
		return j;
	}
	
	
	/**
	 * 批量删除仓位定义
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "仓位停用成功";
		try{
			for(String id:ids.split(",")){
				MdBinEntity mdBin = systemService.getEntity(MdBinEntity.class, 
				id
				);
				mdBin.setTingYong("Y");
				mdBinService.saveOrUpdate(mdBin);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "仓位停用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加仓位定义
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(MdBinEntity mdBin, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "仓位定义添加成功";
		try{

			MdBinEntity mdb = null;
			List<MdBinEntity> mdblist =	systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
			for (MdBinEntity t:mdblist){
				if(t.getBinStore().equals(mdBin.getBinStore())){
					mdb = t;
				}
			}

//		    MdBinEntity mdBin1 = systemService.findUniqueByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
            if(mdb ==null ){
    			mdBinService.save(mdBin);
    			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }else{
    			message = "库位编码或者库位条码已经存在";
                j.setSuccess(false);
            }
		}catch(Exception e){
			e.printStackTrace();
			message = "仓位定义添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新仓位定义
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(MdBinEntity mdBin, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "仓位定义更新成功";
		MdBinEntity t = mdBinService.get(MdBinEntity.class, mdBin.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(mdBin, t);
			mdBinService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "仓位定义更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 仓位定义新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(MdBinEntity mdBin, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mdBin.getId())) {
			mdBin = mdBinService.getEntity(MdBinEntity.class, mdBin.getId());
			req.setAttribute("mdBinPage", mdBin);
		}
		return new ModelAndView("com/zzjee/md/mdBin-add");
	}
	/**
	 * 仓位定义编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(MdBinEntity mdBin, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mdBin.getId())) {
			mdBin = mdBinService.getEntity(MdBinEntity.class, mdBin.getId());
			req.setAttribute("mdBinPage", mdBin);
		}
		return new ModelAndView("com/zzjee/md/mdBin-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","mdBinController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(MdBinEntity mdBin,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(MdBinEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mdBin, request.getParameterMap());
		List<MdBinEntity> mdBins = this.mdBinService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"仓位定义");
		modelMap.put(NormalExcelConstants.CLASS,MdBinEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("仓位定义列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,mdBins);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(MdBinEntity mdBin,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"仓位定义");
    	modelMap.put(NormalExcelConstants.CLASS,MdBinEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("仓位定义列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<MdBinEntity> listMdBinEntitys = ExcelImportUtil.importExcel(file.getInputStream(),MdBinEntity.class,params);
				for (MdBinEntity mdBin : listMdBinEntitys) {

					MdBinEntity mdb = null;
					List<MdBinEntity> mdblist =	systemService.findByProperty(MdBinEntity.class, "kuWeiBianMa", mdBin.getKuWeiBianMa());
					for (MdBinEntity t:mdblist){
						if(t.getBinStore().equals(mdBin.getBinStore())){
							mdb = t;
						}
					}
					if(mdb!=null){
						MyBeanUtils.copyBeanNotNull2Bean(mdBin, mdb);
						systemService.saveOrUpdate(mdb);
					}else{
						mdBinService.save(mdBin);
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
	public List<MdBinEntity> list() {
		List<MdBinEntity> listMdBins=mdBinService.getList(MdBinEntity.class);
		return listMdBins;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		MdBinEntity task = mdBinService.get(MdBinEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody MdBinEntity mdBin, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MdBinEntity>> failures = validator.validate(mdBin);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mdBinService.save(mdBin);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = mdBin.getId();
		URI uri = uriBuilder.path("/rest/mdBinController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody MdBinEntity mdBin) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<MdBinEntity>> failures = validator.validate(mdBin);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			mdBinService.saveOrUpdate(mdBin);
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
		mdBinService.deleteEntityById(MdBinEntity.class, id);
	}
}
