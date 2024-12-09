package com.zzjee.ba.controller;

import com.jeecg.demo.dao.JeecgMinidaoDao;
import com.zzjee.ba.entity.BaGoodsCategoryEntity;
import com.zzjee.ba.service.BaGoodsCategoryServiceI;
import com.zzjee.ba.vo.BaGoodsCategoryVoo;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title: Controller
 * @Description: 商品类目
 * @author onlineGenerator
 * @date 2021-08-25 17:16:36
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/baGoodsCategoryController")
public class BaGoodsCategoryController extends BaseController {
	/**
	 * Logger  for this class
	 */
	private static final Logger logger = Logger.getLogger(BaGoodsCategoryController.class);

	@Autowired
	private BaGoodsCategoryServiceI baGoodsCategoryService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private JeecgMinidaoDao jeecgMinidaoDao;


	/**
	 * 商品类目列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/ba/baGoodsCategoryList");
	}

	/**
	 * 父级DEMO下拉菜单
	 */
	@RequestMapping(params = "getComboTreeData")
	@ResponseBody
	public List<ComboTree> getComboTreeData(HttpServletRequest request, ComboTree comboTree1) {
		List<BaGoodsCategoryVoo> balist=new ArrayList<BaGoodsCategoryVoo>();
		balist = jeecgMinidaoDao.getAllBaGoodsCategorys();
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		if (balist.size() > 0) {
			for (BaGoodsCategoryVoo baGoodsCategoryVoo : balist) {
				ComboTree comboTree = new ComboTree();
				comboTree.setId(baGoodsCategoryVoo.getId());
				comboTree.setText(baGoodsCategoryVoo.getCategoryName());
				if (baGoodsCategoryVoo.getCategoryLevel() <=3) {
					comboTree.setState("closed");
				}
				if (StringUtil.isEmpty(baGoodsCategoryVoo.getPid())){
					comboTrees.add(comboTree);
				}else {
					ComboTree parent = findParent(comboTrees,baGoodsCategoryVoo.getPid());
					if(parent!=null){
						if(parent.getChildren()==null){
							parent.setChildren(new ArrayList<>());
						}
						parent.getChildren().add(comboTree);
					}
					else {
						comboTrees.add(comboTree);
					}
				}
			}
		}
		return comboTrees;
	}

	private ComboTree findParent(List<ComboTree> comboTrees, String pid) {
		ComboTree find = null;
		for(ComboTree comboTree:comboTrees){
			if(comboTree.getId().equals(pid)){
				find = comboTree;
				break;
			} else if(comboTree.getChildren()!=null) {
				find = findParent(comboTree.getChildren(),pid);
				if(find!=null){
					break;
				}
			}
		}
		return find;

	}
	@RequestMapping(params = "datagrid")
	public void datagrid(BaGoodsCategoryEntity baGoodsCategory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BaGoodsCategoryEntity.class, dataGrid);
		if(baGoodsCategory.getId()==null){
			cq.isNull("pid");
		}else{
			cq.eq("pid", baGoodsCategory.getId());
			baGoodsCategory.setId(null);
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baGoodsCategory, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_createTime_begin = request.getParameter("createTime_begin");
		String query_createTime_end = request.getParameter("createTime_end");
		if(StringUtil.isNotEmpty(query_createTime_begin)){
			cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
		}
		if(StringUtil.isNotEmpty(query_createTime_end)){
			cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.baGoodsCategoryService.getDataGridReturn(cq, true);
		TagUtil.treegrid(response, dataGrid);
	}

	/**
	 * 删除商品类目
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		baGoodsCategory = systemService.getEntity(BaGoodsCategoryEntity.class, baGoodsCategory.getId());
		message = "商品类目删除成功";
		try{
			baGoodsCategoryService.delete(baGoodsCategory);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除商品类目
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品类目删除成功";
		try{
			for(String id:ids.split(",")){
				BaGoodsCategoryEntity baGoodsCategory = systemService.getEntity(BaGoodsCategoryEntity.class,
				Integer.parseInt(id)
				);
				baGoodsCategoryService.delete(baGoodsCategory);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加商品类目
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品类目添加成功";
		try{
			if(baGoodsCategory.getPid()==null){
				baGoodsCategory.setPid(null);
			}
			baGoodsCategoryService.save(baGoodsCategory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新商品类目
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "商品类目更新成功";
		BaGoodsCategoryEntity t = baGoodsCategoryService.get(BaGoodsCategoryEntity.class, baGoodsCategory.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(baGoodsCategory, t);
			if(t.getPid()==null){
				t.setPid(null);
			}
			baGoodsCategoryService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 商品类目新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baGoodsCategory.getId())) {
			baGoodsCategory = baGoodsCategoryService.getEntity(BaGoodsCategoryEntity.class, baGoodsCategory.getId());
			req.setAttribute("baGoodsCategoryPage", baGoodsCategory);
		}
		return new ModelAndView("com/zzjee/ba/baGoodsCategory-add");
	}
	/**
	 * 商品类目编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BaGoodsCategoryEntity baGoodsCategory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baGoodsCategory.getId())) {
			baGoodsCategory = baGoodsCategoryService.getEntity(BaGoodsCategoryEntity.class, baGoodsCategory.getId());
			req.setAttribute("baGoodsCategoryPage", baGoodsCategory);
		}
		return new ModelAndView("com/zzjee/ba/baGoodsCategory-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","baGoodsCategoryController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BaGoodsCategoryEntity baGoodsCategory,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BaGoodsCategoryEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baGoodsCategory, request.getParameterMap());
		List<BaGoodsCategoryEntity> baGoodsCategorys = this.baGoodsCategoryService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"商品类目");
		modelMap.put(NormalExcelConstants.CLASS,BaGoodsCategoryEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品类目列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,baGoodsCategorys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BaGoodsCategoryEntity baGoodsCategory,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"商品类目");
    	modelMap.put(NormalExcelConstants.CLASS,BaGoodsCategoryEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("商品类目列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<BaGoodsCategoryEntity> listBaGoodsCategoryEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BaGoodsCategoryEntity.class,params);
				for (BaGoodsCategoryEntity baGoodsCategory : listBaGoodsCategoryEntitys) {
					baGoodsCategoryService.save(baGoodsCategory);
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
	public List<BaGoodsCategoryEntity> list() {
		List<BaGoodsCategoryEntity> listBaGoodsCategorys=baGoodsCategoryService.getList(BaGoodsCategoryEntity.class);
		return listBaGoodsCategorys;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		BaGoodsCategoryEntity task = baGoodsCategoryService.get(BaGoodsCategoryEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody BaGoodsCategoryEntity baGoodsCategory, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BaGoodsCategoryEntity>> failures = validator.validate(baGoodsCategory);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			baGoodsCategoryService.save(baGoodsCategory);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Integer id = baGoodsCategory.getId();
		URI uri = uriBuilder.path("/rest/baGoodsCategoryController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody BaGoodsCategoryEntity baGoodsCategory) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BaGoodsCategoryEntity>> failures = validator.validate(baGoodsCategory);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			baGoodsCategoryService.saveOrUpdate(baGoodsCategory);
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
		baGoodsCategoryService.deleteEntityById(BaGoodsCategoryEntity.class, id);
	}
}
