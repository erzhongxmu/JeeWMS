package com.zzjee.tms.controller;

import com.alibaba.fastjson.JSONArray;
import com.zzjee.tms.entity.TmsMdDzEntity;
import com.zzjee.tms.service.TmsMdDzServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title: Controller
 * @Description: 客户地址
 * @author onlineGenerator
 * @date 2018-01-29 21:57:16
 * @version V1.0
 *
 */
@Api(value="TmsMdDz",description="客户地址",tags="tmsMdDzController")
@Controller
@RequestMapping("/tmsMdDzController")
public class TmsMdDzController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TmsMdDzController.class);

	@Autowired
	private TmsMdDzServiceI tmsMdDzService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 客户地址列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsMdDzList");
	}
	@RequestMapping(params = "listchoose")
	public ModelAndView listchoose(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsMdDzChoose");
	}
	/**
	 * easyui AJAX请求数据
	 * @param request 请求
	 * @param response 响应
	 * @param dataGrid
	 * @param tmsMdDz
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdDzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdDz, request.getParameterMap());
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");
		}
		if(!StringUtil.isEmpty(user.getUserType())){
			if("4".equals(user.getUserType())){
				cq.eq("username",user.getUserName());
			}
		}
		cq.add();
		this.tmsMdDzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridchoose")
	public void datagridchoose(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdDzEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdDz, request.getParameterMap());
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");

		}
		if(!StringUtil.isEmpty(user.getUserType())){
			if("4".equals(user.getUserType())){
				cq.eq("username",user.getUserName());
			}
		}
		cq.add();
		this.tmsMdDzService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 删除客户地址
	 * @return j
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TmsMdDzEntity tmsMdDz, HttpServletRequest request) {
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		tmsMdDz = systemService.getEntity(TmsMdDzEntity.class, tmsMdDz.getId());
		message = "客户地址删除成功";
		try{
			tmsMdDz.setZhuangtai("N");
			tmsMdDzService.updateEntitie(tmsMdDz);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回结果
		return j;
	}

	/**
	 * 批量删除客户地址
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "客户地址删除成功";
		try{
			for(String id:ids.split(",")){
				TmsMdDzEntity tmsMdDz = systemService.getEntity(TmsMdDzEntity.class,
				id
				);
				tmsMdDz.setZhuangtai("N");
				tmsMdDzService.updateEntitie(tmsMdDz);
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
	 * 添加客户地址
	 * @param tmsMdDz
	 * @param request
	 * @return 返回AjaxJson对象
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TmsMdDzEntity tmsMdDz, HttpServletRequest request) {
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		message = "客户地址添加成功";
		try{
			tmsMdDzService.save(tmsMdDz);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回AjaxJson对象
		return j;
	}

	/**
	 * 更新客户地址
	 * @param request 请求
	 * @param tmsMdDz
	 * @return j
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TmsMdDzEntity tmsMdDz, HttpServletRequest request) {
		String message = null;
		// 创建对象
		AjaxJson j = new AjaxJson();
		message = "客户地址更新成功";
		TmsMdDzEntity t = tmsMdDzService.get(TmsMdDzEntity.class, tmsMdDz.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tmsMdDz, t);
			tmsMdDzService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			// 抛出异常
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		// 返回AjaxJson对象
		return j;
	}


	/**
	 * 客户地址新增页面跳转
	 * @param req 请求
	 * @param tmsMdDz 实体
	 * @return ModelAndView
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TmsMdDzEntity tmsMdDz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsMdDz.getId())) {
			tmsMdDz = tmsMdDzService.getEntity(TmsMdDzEntity.class, tmsMdDz.getId());
			req.setAttribute("tmsMdDzPage", tmsMdDz);
		}
		return new ModelAndView("com/zzjee/tms/tmsMdDz-add");
	}
	/**
	 * 客户地址编辑页面跳转
	 * @param req 请求
	 * @param tmsMdDz
	 * @return ModelAndView
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TmsMdDzEntity tmsMdDz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsMdDz.getId())) {
			tmsMdDz = tmsMdDzService.getEntity(TmsMdDzEntity.class, tmsMdDz.getId());
			req.setAttribute("tmsMdDzPage", tmsMdDz);
		}
		return new ModelAndView("com/zzjee/tms/tmsMdDz-update");
	}

	/**
	 * 导入功能跳转
	 * @param req 请求
	 * @return ModelAndView
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tmsMdDzController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * @param request 请求
	 * @param tmsMdDz
	 * @param response 响应
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TmsMdDzEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsMdDz, request.getParameterMap());
		List<TmsMdDzEntity> tmsMdDzs = this.tmsMdDzService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"客户地址");
		modelMap.put(NormalExcelConstants.CLASS,TmsMdDzEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户地址列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tmsMdDzs);
		// 返回结果
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 模板
	 * @param request 请求
	 * @param response 响应
	 * @param tmsMdDz TmsMdDzEntity类型的实体
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TmsMdDzEntity tmsMdDz, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		// 设置Excel文件的标题，此处为"客户地址"
    	modelMap.put(NormalExcelConstants.FILE_NAME,"客户地址");
		// 设置要导出的实体类类型
    	modelMap.put(NormalExcelConstants.CLASS,TmsMdDzEntity.class);
		// 设置导出参数ExportParams，包含标题、描述和作者信息
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("客户地址列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
		// 设置数据列表
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		// 返回视图名称，指明使用JEECG框架的Excel导出视图进行渲染和导出
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		// 创建对象
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取单个上传文件对象
			MultipartFile file = entity.getValue();
			// 创建导入参数对象，设置标题行、头部行、是否需要保存等参数
			ImportParams params = new ImportParams();
			params.setTitleRows(2); // 设置标题行有两行
			params.setHeadRows(1);	// 设置设头部行有一行
			params.setNeedSave(true);	// 表示导入的数据需要被保存
			try {
				// 使用ExcelImportUtil工具类从InputStream导入数据到指定的实体类型
				List<TmsMdDzEntity> listTmsMdDzEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TmsMdDzEntity.class,params);
				// 遍历导入的实体列表，将每个实体保存到数据库
				for (TmsMdDzEntity tmsMdDz : listTmsMdDzEntitys) {
					tmsMdDzService.save(tmsMdDz);
				}
				// 设置AjaxJson对象的消息，表示文件导入成功。
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				// 如果在导入过程中出现异常，则设置AjaxJson对象的消息，表示文件导入失败
				j.setMsg("文件导入失败！");
				// 记录异常信息，便于后续问题排查
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					// 尝试关闭文件流，防止资源泄露
					file.getInputStream().close();
				} catch (IOException e) {
					// 打印异常信息
					e.printStackTrace();
				}
			}
		}
		// 返回封装了操作结果和消息的AjaxJson对象
		return j;
	}
}
