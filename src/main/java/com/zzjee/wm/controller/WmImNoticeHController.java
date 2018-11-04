package com.zzjee.wm.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.*;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.WmNoticeImpPage;
import com.zzjee.wm.service.WmImNoticeHServiceI;
import com.zzjee.wm.page.WmImNoticeHPage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.*;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
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
import org.jeecgframework.core.util.BarcodeUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.QRcodeUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
import org.jeecgframework.web.system.sms.util.TuiSongMsgUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

/**
 * @Title: Controller
 * @Description: 进货通知抬头
 * @author erzhongxmu
 * @date 2017-08-15 23:17:33
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/wmImNoticeHController")
public class WmImNoticeHController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(WmImNoticeHController.class);

	@Autowired
	private WmImNoticeHServiceI wmImNoticeHService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 进货通知抬头列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmImNoticeHList");
	}
	@RequestMapping(params = "listqt")
	public ModelAndView listqt(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmImqtNoticeHList");
	}
	@RequestMapping(params = "tlist")
	public ModelAndView tlist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmImtNoticeHList");
	}
	@RequestMapping(params = "yklist")
	public ModelAndView yklist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmImykNoticeHList");
	}
	@RequestMapping(params = "cuslist")
	public ModelAndView cuslist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/cuswmImNoticeHList");
	}
	@RequestMapping(params = "custlist")
	public ModelAndView custlist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/cuswmImtNoticeHList");
	}
	@RequestMapping(params = "batchlist")//收货验收
	public ModelAndView batchlist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wminqmbatchList");
	}
	@RequestMapping(params = "tbatchlist")//退货验收
	public ModelAndView tbatchlist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmintqmbatchList");
	}

	@RequestMapping(params = "doPrintpage")
	public ModelAndView doPrint(String id,HttpServletRequest request) {
		WmImNoticeHEntity wmImNoticeHEntity = wmImNoticeHService.getEntity(WmImNoticeHEntity.class, id);
		request.setAttribute("wmImNoticeHPage", wmImNoticeHEntity);
		request.setAttribute("kprq",DateUtils.date2Str(wmImNoticeHEntity.getCreateDate(),DateUtils.date_sdf));
		request.setAttribute("comname", ResourceUtil.getConfigByName("comname"));

		if(StringUtil.isNotEmpty(wmImNoticeHEntity.getImCusCode())){
			request.setAttribute("noticeid", wmImNoticeHEntity.getImCusCode());
		}else{
			request.setAttribute("noticeid", wmImNoticeHEntity.getNoticeId());
		}

		try{
			MdSupEntity mdSupEntity = systemService.findUniqueByProperty(MdSupEntity.class,"gysBianMa",wmImNoticeHEntity.getSupCode());
			MdCusEntity mdcus = systemService.findUniqueByProperty(MdCusEntity.class,"keHuBianMa",wmImNoticeHEntity.getCusCode());

			request.setAttribute("cusname",wmImNoticeHEntity.getCusCode()+"-"+mdcus.getZhongWenQch());

			request.setAttribute("supname",mdSupEntity.getGysBianMa()+"-"+ mdSupEntity.getZhongWenQch());

		}catch (Exception e){

		}
		//获取参数
		Object id0 = wmImNoticeHEntity.getNoticeId();
		//===================================================================================
		//查询-产品
		Double tomsum = 0.00;
		Double  noticesum = 0.00;
		List<WmImNoticeIEntity> wmImNoticeIEntitynewList = new ArrayList<>();
			String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
			try {
				List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
						.findHql(hql0, id0);
				for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
					if (StringUtil.isEmpty(wmImNoticeIEntity.getBinPlan())){
						String hqlup = "from WmToUpGoodsEntity where 1 = 1 AND goodsId = ?  order by createDate desc";
						try {
							WmToUpGoodsEntity wmToUpGoodsEntity = 	(WmToUpGoodsEntity)systemService.findHql(hqlup,wmImNoticeIEntity.getGoodsCode()).get(0);
							wmImNoticeIEntity.setBinPlan(wmToUpGoodsEntity.getKuWeiBianMa());
						}catch (Exception e){

						}
					}
					wmImNoticeIEntitynewList.add(wmImNoticeIEntity);
				}

			request.setAttribute("wmImNoticeIList", wmImNoticeIEntitynewList);
		}catch (Exception e){

		}
		return new ModelAndView("com/zzjee/wm/print/imnotice-print");
	}

	@RequestMapping(params = "datagridbatch")
	public void datagridbatch(WmImNoticeIEntity wmImNoticeI,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeIEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeI);
		cq.eq("binPre", "N");
		cq.add();
		this.wmImNoticeHService.getDataGridReturn(cq, true);
		List<WmImNoticeIEntity> resultnew = new ArrayList<WmImNoticeIEntity>();
		List<WmImNoticeIEntity> resultold = dataGrid.getResults();
		for (WmImNoticeIEntity wmImNoticeIEntity : resultold) {
			try {
				if (Long.parseLong(wmImNoticeIEntity.getGoodsCount()) > Long.parseLong(wmImNoticeIEntity.getGoodsQmCount())) {
					resultnew.add(wmImNoticeIEntity);
				}
			} catch (Exception e) {
				wmImNoticeIEntity.setGoodsQmCount("0");
				resultnew.add(wmImNoticeIEntity);
			}


		}
		dataGrid.setResults(resultnew);
		dataGrid.setTotal(resultnew.size());
		TagUtil.datagrid(response, dataGrid);
	
	}
	@RequestMapping(params = "datagridtbatch")
	public void datagridtbatch(WmImNoticeIEntity wmImNoticeI,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeIEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeI);
		cq.eq("binPre", "N");
		cq.like("imNoticeId", "TH%");
		cq.add();
		this.wmImNoticeHService.getDataGridReturn(cq, true);
		List<WmImNoticeIEntity> resultnew = new ArrayList<WmImNoticeIEntity>();
		List<WmImNoticeIEntity> resultold = dataGrid.getResults();
		for (WmImNoticeIEntity wmImNoticeIEntity : resultold) {
			if (Long.parseLong(wmImNoticeIEntity.getGoodsCount()) > Long.parseLong(wmImNoticeIEntity.getGoodsQmCount())) {
				resultnew.add(wmImNoticeIEntity);
			}
		}
		dataGrid.setResults(resultnew);
		dataGrid.setTotal(resultnew.size());
		TagUtil.datagrid(response, dataGrid);
	
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeHEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeH);
		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("imData_begin1");
			String query_imData_end = request.getParameter("imData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			} 
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			} 
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("createDate", "desc");
		cq.setOrder(map1); 
		cq.add();

		this.wmImNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param
	 */

	@RequestMapping(params = "datagridqt")
	public void datagridqt(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeHEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeH);
		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("imData_begin1");
			String query_imData_end = request.getParameter("imData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			} 
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			} 
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
//		if (wmImNoticeH.getImSta() == null) {
//			cq.eq("imSta", Constants.wm_sta1);
//		}



		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}
		
//		Map<String,Object> map = new HashMap<String,Object>();  
//		map.put("imSta", "desc");  
//		cq.setOrder(map);  
		          
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");
		cq.setOrder(map1); 
		cq.eq("orderTypeCode", "09");
		cq.add();

		this.wmImNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	//退货
	@RequestMapping(params = "datagridt")
	public void datagridt(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeHEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeH);
		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("imData_begin1");
			String query_imData_end = request.getParameter("imData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			} 
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			} 
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		if (wmImNoticeH.getImSta() == null) {
			cq.eq("imSta", Constants.wm_sta1);
		}



		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}
		
//		Map<String,Object> map = new HashMap<String,Object>();  
//		map.put("imSta", "desc");  
//		cq.setOrder(map);  
		          
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");
		cq.setOrder(map1); 
		cq.eq("orderTypeCode", "03");
		cq.add();

		this.wmImNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	//退货
	@RequestMapping(params = "datagridyk")
	public void datagridyk(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeHEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeH);
		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("imData_begin1");
			String query_imData_end = request.getParameter("imData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			} 
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("imData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			} 
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		if (wmImNoticeH.getImSta() == null) {
			cq.eq("imSta", Constants.wm_sta1);
		}
		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());
		}

		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");
		cq.setOrder(map1); 
		cq.eq("orderTypeCode", "04");
		cq.add();

		this.wmImNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除进货通知抬头
	 * 
	 * @return
	 */
	@RequestMapping(params = "appor")
	@ResponseBody
	public AjaxJson doappor(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();

		String message = "审核成功";
		try {
			wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
					request.getParameter("id"));
			wmImNoticeH.setImSta(Constants.wm_sta1);
			wmImNoticeHService.saveOrUpdate(wmImNoticeH);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
			Object id0 = wmImNoticeH.getNoticeId();
			// ===================================================================================
			// 查询-进货通知明细
			String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
			try {
				List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
						.findHql(hql0, id0);
				for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
					wmImNoticeIEntity.setBinPre("N");
					systemService.updateEntitie(wmImNoticeIEntity);
				}
			}catch (Exception e) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	/**
	 * 删除进货通知抬头
	 * 
	 * @return
	 */
	@RequestMapping(params = "close")
	@ResponseBody
	public AjaxJson doclose(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();

		String message = "完成成功";
		try {
			wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
					request.getParameter("id"));
			wmImNoticeH.setImSta(Constants.wm_sta4);
			wmImNoticeHService.saveOrUpdate(wmImNoticeH);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
			Object id0 = wmImNoticeH.getNoticeId();
			// ===================================================================================
			// 查询-进货通知明细
			String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
			try {
				List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
						.findHql(hql0, id0);
				for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
					wmImNoticeIEntity.setBinPre("Y");
					systemService.updateEntitie(wmImNoticeIEntity);
				}
			}catch (Exception e) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "完成失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	
	/**
	 * 删除进货通知抬头
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
				wmImNoticeH.getId());
		String message = "进货通知删除成功";
		try {
			WmPlatIoEntity wmPlatIo = systemService.findUniqueByProperty(
					WmPlatIoEntity.class, "docId", wmImNoticeH.getNoticeId());// 删除月台计划
			if (wmPlatIo != null) {
				systemService.delete(wmPlatIo);
			}
			wmImNoticeH.setImSta(Constants.wm_sta3);
			wmImNoticeHService.saveOrUpdate(wmImNoticeH);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
			Object id0 = wmImNoticeH.getNoticeId();
			// ===================================================================================
			// 查询-进货通知明细
			
			if(wmImNoticeH.getOrderTypeCode().equals("04")){
				String 	tsql = "delete  from wm_in_qm_i where im_notice_id = ?";
				systemService.executeSql(tsql, wmImNoticeH.getNoticeId());	
			}
			String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
			try {
				List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
						.findHql(hql0, id0);
				for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
					wmImNoticeIEntity.setBinPre("Y");
					systemService.updateEntitie(wmImNoticeIEntity);
				}
			}catch (Exception e) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "进货通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	@RequestMapping(params = "doPrinthpid")
	@ResponseBody
	public void downReceiveExcelhpid(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response) {
		OutputStream fileOut = null;
		BufferedImage bufferImg = null;
		String codedFileName = null;
		wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
				wmImNoticeH.getId());// 获取抬头
		String hql0 = "from WmInQmIEntity where 1 = 1 AND imNoticeId = ? ";
		List<WmInQmIEntity> wmImQmIEntityList = systemService.findHql(
				hql0, wmImNoticeH.getNoticeId());// 获取行项目
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			StringBuffer sber = new StringBuffer();

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
//					.getNoticeId()));
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ wmImNoticeH.getNoticeId() + "hpid.xls");
//			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("货品ID");
			
			sheet.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）    
			sheet.setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）    
			sheet.setMargin(HSSFSheet.LeftMargin,0.1);// 页边距（左）    
			sheet.setMargin(HSSFSheet.RightMargin,0.1);// 页边距（右   
			sheet.setColumnWidth(0, 28 * 256);
			sheet.setColumnWidth(1, 29 * 256);
			sheet.setColumnWidth(2, 6 * 200);
			
			sheet.setColumnWidth(3, 14 * 256);
			sheet.setColumnWidth(4, 14 * 256);
			sheet.setColumnWidth(5, 6 * 256);
			// sheet.setColumnWidth(6, 8 * 256);
			// sheet.setColumnWidth(7, 8 * 256);
			// sheet.setColumnWidth(8, 8 * 256);
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性
			HSSFClientAnchor anchor = null;



			// 创建两种单元格格式
			CellStyle csh = wb.createCellStyle();
			CellStyle csh1 = wb.createCellStyle();
			CellStyle cs = wb.createCellStyle();
			CellStyle cs2 = wb.createCellStyle();
			CellStyle cs3 = wb.createCellStyle();
			CellStyle cs4 = wb.createCellStyle();
			CellStyle cs5 = wb.createCellStyle();
			// 创建两种字体
			Font f = wb.createFont();
			Font f2 = wb.createFont();
			Font fh = wb.createFont();
			fh.setFontHeightInPoints((short) 42);
			fh.setColor(IndexedColors.BLACK.getIndex());
			fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
			Font fh1 = wb.createFont();
			fh1.setFontHeightInPoints((short) 32);
			fh1.setColor(IndexedColors.BLACK.getIndex());
			fh1.setBoldweight(Font.BOLDWEIGHT_BOLD);
			// 创建第一种字体样式（用于列名）
			f.setFontHeightInPoints((short) 22);
			f.setColor(IndexedColors.BLACK.getIndex());
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// 创建第二种字体样式（用于值）
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.BLACK.getIndex());

			// Font f3=wb.createFont();
			// f3.setFontHeightInPoints((short) 10);
			// f3.setColor(IndexedColors.RED.getIndex());
			csh.setFont(fh);
			csh.setBorderLeft(CellStyle.BORDER_NONE);
			csh.setBorderRight(CellStyle.BORDER_NONE);
			csh.setBorderTop(CellStyle.BORDER_NONE);
			csh.setBorderBottom(CellStyle.BORDER_NONE);
			csh.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			csh1.setFont(fh1);
			csh1.setBorderLeft(CellStyle.BORDER_NONE);
			csh1.setBorderRight(CellStyle.BORDER_NONE);
			csh1.setBorderTop(CellStyle.BORDER_NONE);
			csh1.setBorderBottom(CellStyle.BORDER_NONE);
			csh1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			// 设置第一种单元格的样式（用于列名）
			cs.setFont(f);
			cs.setBorderLeft(CellStyle.BORDER_NONE);
			cs.setBorderRight(CellStyle.BORDER_NONE);
			cs.setBorderTop(CellStyle.BORDER_NONE);
			cs.setBorderBottom(CellStyle.BORDER_NONE);
			cs.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			// 设置第二种单元格的样式（用于值）
			cs2.setFont(f2);
			cs2.setBorderLeft(CellStyle.BORDER_NONE);
			cs2.setBorderRight(CellStyle.BORDER_NONE);
			cs2.setBorderTop(CellStyle.BORDER_NONE);
			cs2.setBorderBottom(CellStyle.BORDER_NONE);
			cs2.setWrapText(true);

			// cs2.setAlignment(CellStyle.BORDER_NONE);

			cs3.setFont(f2);
			cs3.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs3.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs3.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs3.setBorderBottom(CellStyle.BORDER_MEDIUM);
			// cs3.setAlignment(CellStyle.BORDER_HAIR);
			cs4.setFont(f2);
			cs4.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs4.setBorderBottom(CellStyle.BORDER_MEDIUM);

			cs5.setFont(f2);
			cs5.setBorderLeft(CellStyle.BORDER_NONE);
			cs5.setBorderRight(CellStyle.BORDER_NONE);
			cs5.setBorderTop(CellStyle.BORDER_NONE);
			cs5.setBorderBottom(CellStyle.BORDER_NONE);
			cs5.setWrapText(true);

			int cellsNum = 0;
			int pagesize = 1;
			int page = 0;
			short high =  600;
			for (WmInQmIEntity wmInQmIEntity : wmImQmIEntityList) {
				cellsNum++;
				MvGoodsEntity goods = systemService.findUniqueByProperty(
						MvGoodsEntity.class, "goodsCode",
						wmInQmIEntity.getGoodsId());
				Row rowColumnValue = sheet.createRow((short) page*pagesize+cellsNum); // 列名
				rowColumnValue.setHeight((short) 1000);
				Cell cell1 = rowColumnValue.createCell(0);
				try {
					cell1.setCellValue("库别："+wmInQmIEntity.getBinId().substring(0,2)+"  "+wmInQmIEntity.getQmOkQuat()+goods.getShlDanWei());

				} catch (Exception e) {
					// TODO: handle exception
				}
				cell1.setCellStyle(csh);
				CellRangeAddress c0 = new CellRangeAddress( page*pagesize+cellsNum,  page*pagesize+cellsNum, 0, 5);
				sheet.addMergedRegion(c0);
				
				 cellsNum++;
					Row rowColumnValue1 = sheet.createRow((short) page*pagesize+cellsNum); // 列名
					rowColumnValue1.setHeight( (short) 1000);
					Cell cell2 = rowColumnValue1.createCell(0);
				
					cell2.setCellValue( goods.getShpMingCheng());
					cell2.setCellStyle(csh1);
					CellRangeAddress c2 = new CellRangeAddress( page*pagesize+cellsNum,  page*pagesize+cellsNum, 0, 5);
					sheet.addMergedRegion(c2);
					cellsNum++;
				 //插入图片
					 try {
				 byteArrayOut = new ByteArrayOutputStream();
				 try {
					 bufferImg =
							 ImageIO.read(BarcodeUtil.generateToStream(wmInQmIEntity.getBinId().substring(0,2)));
				} catch (Exception e) {
					// TODO: handle exception
				}

				 ImageIO.write(bufferImg, "jpg", byteArrayOut);
				 anchor = new HSSFClientAnchor(0, 0, 0, 0,(short)2, page*pagesize+cellsNum,
				 (short)5, page*pagesize+cellsNum+4);	
				 patriarch.createPicture(anchor,
				 wb.addPicture(byteArrayOut.toByteArray(),
				 HSSFWorkbook.PICTURE_TYPE_JPEG));
					 } catch (Exception e) {
							// TODO: handle exception
						}
					Row rowColumnValue12 = sheet.createRow((short) page*pagesize+cellsNum); // 列名
					rowColumnValue12.setHeight((short) 1000);
					Cell cell13 = rowColumnValue12.createCell(0);
					cell13.setCellValue(goods.getGoodsCode() );
					cell13.setCellStyle(cs);
					CellRangeAddress c13 = new CellRangeAddress( page*pagesize+cellsNum,  page*pagesize+cellsNum, 0, 5);
					sheet.addMergedRegion(c13);
					cellsNum++;
			
				Row rowColumnValue2 = sheet.createRow((short) page*pagesize+cellsNum); // 列名
				rowColumnValue2.setHeight(high);
				Cell cell3 = rowColumnValue2.createCell(0);
				cell3.setCellValue("生产日期:"+wmInQmIEntity.getProData() );
				cell3.setCellStyle(cs);
				CellRangeAddress c3 = new CellRangeAddress( page*pagesize+cellsNum,  page*pagesize+cellsNum, 0, 5);
				sheet.addMergedRegion(c3);
				cellsNum++;
				Row rowColumnValue3 = sheet.createRow((short) page*pagesize+cellsNum); // 列名
				rowColumnValue3.setHeight(high);
				Cell cell4 = rowColumnValue3.createCell(0);
				try {
					Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
					cal.setTime(DateUtils.str2Date(wmInQmIEntity.getProData(), DateUtils.date_sdf));
					cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(goods.getBzhiQi()));//取当前日期的后一天.  
					cell4.setCellValue("到期日期:"+DateUtils.date2Str(cal.getTime(), DateUtils.date_sdf));
				} catch (Exception e) {
					// TODO: handle exception
				}
				cell4.setCellStyle(cs);
				CellRangeAddress c4 = new CellRangeAddress( page*pagesize+cellsNum,  page*pagesize+cellsNum, 0, 5);
				sheet.addMergedRegion(c4);
				cellsNum++;
				Row rowColumnValue4 = sheet.createRow((short) page*pagesize+cellsNum); // 列名
				rowColumnValue4.setHeight(high);
				Cell cell5 = rowColumnValue4.createCell(0);
				cell5.setCellValue("进货日期:"+ DateUtils.date2Str(wmInQmIEntity.getCreateDate() , DateUtils.date_sdf) );
				cell5.setCellStyle(cs);
				CellRangeAddress c5 = new CellRangeAddress( page*pagesize+cellsNum,  page*pagesize+cellsNum, 0, 5);
				sheet.addMergedRegion(c5);
		
				 page++;
			}
			fileOut = response.getOutputStream();
			 HSSFPrintSetup printSetup = sheet.getPrintSetup();   
			 printSetup.setLandscape(true);
			 printSetup.setPaperSize(HSSFPrintSetup.ENVELOPE_DL_PAPERSIZE);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 打印进货通知
	 * 
	 * @return
	 */

	@RequestMapping(params = "doPrint")
	@ResponseBody
	public void downReceiveExcel(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response) {
		OutputStream fileOut = null;
		BufferedImage bufferImg = null;
		String codedFileName = null;
		wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
				wmImNoticeH.getId());// 获取抬头
		String hql0 = "from WmImNoticeIEntity where 1 = 1 AND imNoticeId = ? ";
		List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService.findHql(
				hql0, wmImNoticeH.getNoticeId());// 获取行项目
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			StringBuffer sber = new StringBuffer();

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
					.getNoticeId()));
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ wmImNoticeH.getNoticeId() + ".xls");
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("收货通知");
			sheet.setColumnWidth(0, 5 * 256);
			sheet.setColumnWidth(1, 42 * 256);
			sheet.setColumnWidth(2, 10 * 200);
			sheet.setColumnWidth(3, 8 * 256);
			sheet.setColumnWidth(4, 8 * 256);
			sheet.setColumnWidth(5, 8 * 256);
			// sheet.setColumnWidth(6, 8 * 256);
			// sheet.setColumnWidth(7, 8 * 256);
			// sheet.setColumnWidth(8, 8 * 256);
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
					(short) 3, 1, (short) 6, 3);
			anchor.setAnchorType(3);
			// 插入图片
			patriarch
					.createPicture(anchor, wb.addPicture(
							byteArrayOut.toByteArray(),
							HSSFWorkbook.PICTURE_TYPE_JPEG));

			// 创建第一行
			Row row = sheet.createRow((short) 0); // 第一行空白

			// 创建两种单元格格式
			CellStyle cs = wb.createCellStyle();
			CellStyle cs2 = wb.createCellStyle();
			CellStyle cs3 = wb.createCellStyle();
			CellStyle cs4 = wb.createCellStyle();
			CellStyle cs5 = wb.createCellStyle();
			// 创建两种字体
			Font f = wb.createFont();
			Font f2 = wb.createFont();

			// 创建第一种字体样式（用于列名）
			f.setFontHeightInPoints((short) 16);
			f.setColor(IndexedColors.BLACK.getIndex());
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// 创建第二种字体样式（用于值）
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.BLACK.getIndex());

			// Font f3=wb.createFont();
			// f3.setFontHeightInPoints((short) 10);
			// f3.setColor(IndexedColors.RED.getIndex());

			// 设置第一种单元格的样式（用于列名）
			cs.setFont(f);
			cs.setBorderLeft(CellStyle.BORDER_NONE);
			cs.setBorderRight(CellStyle.BORDER_NONE);
			cs.setBorderTop(CellStyle.BORDER_NONE);
			cs.setBorderBottom(CellStyle.BORDER_NONE);
			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 设置第二种单元格的样式（用于值）
			cs2.setFont(f2);
			cs2.setBorderLeft(CellStyle.BORDER_NONE);
			cs2.setBorderRight(CellStyle.BORDER_NONE);
			cs2.setBorderTop(CellStyle.BORDER_NONE);
			cs2.setBorderBottom(CellStyle.BORDER_NONE);
			cs2.setWrapText(true);

			// cs2.setAlignment(CellStyle.BORDER_NONE);

			cs3.setFont(f2);
			cs3.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs3.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs3.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs3.setBorderBottom(CellStyle.BORDER_MEDIUM);
			// cs3.setAlignment(CellStyle.BORDER_HAIR);
			cs4.setFont(f2);
			cs4.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs4.setBorderBottom(CellStyle.BORDER_MEDIUM);

			cs5.setFont(f2);
			cs5.setBorderLeft(CellStyle.BORDER_NONE);
			cs5.setBorderRight(CellStyle.BORDER_NONE);
			cs5.setBorderTop(CellStyle.BORDER_NONE);
			cs5.setBorderBottom(CellStyle.BORDER_NONE);
			cs5.setWrapText(true);

			Row row1 = sheet.createRow((short) 1); // 第二行标题
			row1.setHeight((short) 700);
			Cell cellTitle = row1.createCell(0);
			cellTitle.setCellValue("收货通知");
			cellTitle.setCellStyle(cs);

			Row rowHead1 = sheet.createRow((short) 2); // 头部第一行
			Cell cellHead11 = rowHead1.createCell(0);
			cellHead11.setCellValue("通知单号：" + wmImNoticeH.getNoticeId());
			cellHead11.setCellStyle(cs2);

			Row rowHead2 = sheet.createRow((short) 3); // 头部第二行
			Cell cellHead21 = rowHead2.createCell(0);
			try {
				MdCusEntity md = systemService.findUniqueByProperty(
						MdCusEntity.class, "keHuBianMa",
						wmImNoticeH.getCusCode());
				if (md != null) {
					cellHead21.setCellValue("客户：" + wmImNoticeH.getCusCode()
							+ "-" + md.getZhongWenQch());
				} else {
					cellHead21.setCellValue("客户：" + wmImNoticeH.getCusCode());
				}
			} finally {

			}

			cellHead21.setCellStyle(cs2);

			Cell cellHead23 = rowHead2.createCell(2);
			cellHead23.setCellValue(" 计划送货时间：" + wmImNoticeH.getImData());
			cellHead23.setCellStyle(cs2);

			Row rowHead3 = sheet.createRow((short) 4); // 头部第三行
			Cell cellHead31 = rowHead3.createCell(0);
			cellHead31.setCellValue("司机：" + wmImNoticeH.getImCarDri()
					+ "  司机电话：" + wmImNoticeH.getImCarMobile());
			cellHead31.setCellStyle(cs2);

			Cell cellHead35 = rowHead3.createCell(2);
			cellHead35.setCellValue("车号：" + wmImNoticeH.getImCarNo() + "  备注："
					+ wmImNoticeH.getImBeizhu());
			cellHead35.setCellStyle(cs2);

			// 合并单元格
			CellRangeAddress c = new CellRangeAddress(0, 0, 0, 8); // 第一行空白
			CellRangeAddress c0 = new CellRangeAddress(1, 1, 0, 5);// 第二行标题
			CellRangeAddress c1 = new CellRangeAddress(2, 2, 0, 8);// 第三行通知单号
			CellRangeAddress c2 = new CellRangeAddress(3, 3, 0, 1);// 第四行客户
			CellRangeAddress c3 = new CellRangeAddress(3, 3, 2, 5);// 第四行客户送货时间
			CellRangeAddress c4 = new CellRangeAddress(4, 4, 0, 1);// 第五行客户
			CellRangeAddress c5 = new CellRangeAddress(4, 4, 2, 5);// 第五行客户送货时间
			// CellRangeAddress c4 = new CellRangeAddress(4, 4, 0, 1);
			// CellRangeAddress c5 = new CellRangeAddress(4, 4, 2, 3);
			// CellRangeAddress c6 = new CellRangeAddress(4, 4, 4, 5);
			// CellRangeAddress c7 = new CellRangeAddress(4, 4, 6, 6);

			sheet.addMergedRegion(c);
			sheet.addMergedRegion(c0);
			sheet.addMergedRegion(c1);
			sheet.addMergedRegion(c2);
			sheet.addMergedRegion(c3);
			sheet.addMergedRegion(c4);
			sheet.addMergedRegion(c5);
			// sheet.addMergedRegion(c6);
			// sheet.addMergedRegion(c7);

			Row rowColumnName = sheet.createRow((short) 5); // 列名
			String[] columnNames = { "序号", "商品详情", "数量", "体积cm³", "重量KG",
					"TiHi" };

			for (int i = 0; i < columnNames.length; i++) {
				Cell cell = rowColumnName.createCell(i);
				cell.setCellValue(columnNames[i]);
				cell.setCellStyle(cs3);
			}
			int cellsNum = 5;
			int cerconNo = 1;
			for (int i = 0; i < wmImNoticeIEntityList.size(); i++) {
				WmImNoticeIEntity entity = wmImNoticeIEntityList.get(i);
				cellsNum++;
				Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
				rowColumnValue.setHeight((short) 1000);
				Cell cell1 = rowColumnValue.createCell(0);
				cell1.setCellValue(cerconNo);
				cell1.setCellStyle(cs4);
				Cell cell2 = rowColumnValue.createCell(1);

				Cell cell6 = rowColumnValue.createCell(5);
				cell6.setCellStyle(cs3);
				try {
					MvGoodsEntity goods = systemService.findUniqueByProperty(
							MvGoodsEntity.class, "goodsCode",
							entity.getGoodsCode());
					if (goods != null) {
						cell2.setCellValue(new HSSFRichTextString(goods
								.getGoodsName()));
						cell2.setCellStyle(cs3);

						cell6.setCellValue(goods.getMpDanCeng() + "*"
								+ goods.getMpCengGao());
					}
				} finally {

				}
				Cell cell3 = rowColumnValue.createCell(2);
				cell3.setCellValue(entity.getGoodsCount());
				cell3.setCellStyle(cs4);

				Cell cell4 = rowColumnValue.createCell(3);
				cell4.setCellValue(entity.getGoodsFvol());
				cell4.setCellStyle(cs4);
				Cell cell5 = rowColumnValue.createCell(4);
				cell5.setCellValue(entity.getGoodsWeight());
				cell5.setCellStyle(cs4);


				cerconNo++;
			}
			Row rowColumnInfo = sheet.createRow((short) 2 + cellsNum); // 列名
			rowColumnInfo.createCell(0).setCellValue(
					"注:烦请按时送到"+ResourceUtil.getConfigByName("comaddr")+" 谢谢！");
			CellRangeAddress c15 = new CellRangeAddress(10 + cellsNum,
					10 + cellsNum, 0, 15);
			sheet.addMergedRegion(c15);
			fileOut = response.getOutputStream();
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	
	
	@RequestMapping(params = "doPrintysd")
	@ResponseBody
	public void downReceiveExcelysd(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response) {
		OutputStream fileOut = null;
		BufferedImage bufferImg = null;
		String codedFileName = null;
		wmImNoticeH = systemService.getEntity(WmImNoticeHEntity.class,
				wmImNoticeH.getId());// 获取抬头

		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			StringBuffer sber = new StringBuffer();

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
//					.getNoticeId()));
			bufferImg = QRcodeUtil.createImage(wmImNoticeH
					.getNoticeId());
		
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ wmImNoticeH.getNoticeId() + ".xls");
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("验收单");
			sheet.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）    
			sheet.setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）    
			sheet.setMargin(HSSFSheet.LeftMargin,0.3);// 页边距（左）
			sheet.setMargin(HSSFSheet.RightMargin,0.0);// 页边距（右
//			sheet.setDisplayGridlines(true);
		     //set print grid lines or not
//			sheet.setPrintGridlines(true);
			sheet.setColumnWidth(0, 5 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 25 * 256);
			sheet.setColumnWidth(3, 11 * 256);
			sheet.setColumnWidth(4, 5 * 256);
			sheet.setColumnWidth(5, 5 * 256);
			sheet.setColumnWidth(6, 7 * 256);
			sheet.setColumnWidth(7, 7 * 256);
			sheet.setColumnWidth(8, 9 * 256);
			sheet.setColumnWidth(9, 7 * 256);
			sheet.setColumnWidth(10, 3 * 256);
			// sheet.setColumnWidth(6, 8 * 256);
			// sheet.setColumnWidth(7, 8 * 256);
			// sheet.setColumnWidth(8, 8 * 256);
			
			// 创建两种单元格格式
						CellStyle cs = wb.createCellStyle();
						CellStyle cs1 = wb.createCellStyle();
						CellStyle cs2 = wb.createCellStyle();
						CellStyle cs3 = wb.createCellStyle();
						CellStyle cs4 = wb.createCellStyle();
						CellStyle cs5 = wb.createCellStyle();
						CellStyle cs51 = wb.createCellStyle();
						CellStyle cs52 = wb.createCellStyle();
						// 创建两种字体
						Font f = wb.createFont();
						Font f2 = wb.createFont();
						Font f5 = wb.createFont();
						// 创建第一种字体样式（用于列名）
						f.setFontHeightInPoints((short) 16);
						f.setColor(IndexedColors.BLACK.getIndex());
						f.setBoldweight(Font.BOLDWEIGHT_BOLD);

						// 创建第二种字体样式（用于值）
						f2.setFontHeightInPoints((short) 10);
						f2.setColor(IndexedColors.BLACK.getIndex());

	
						 f5.setFontHeightInPoints((short) 8);
						 f5.setColor(IndexedColors.BLACK.getIndex());

						// 设置第一种单元格的样式（用于列名）
						cs.setFont(f);
						cs.setBorderLeft(CellStyle.BORDER_NONE);
						cs.setBorderRight(CellStyle.BORDER_NONE);
						cs.setBorderTop(CellStyle.BORDER_NONE);
						cs.setBorderBottom(CellStyle.BORDER_NONE);
						cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						
						cs1.setFont(f2);
						cs1.setBorderLeft(CellStyle.BORDER_NONE);
						cs1.setBorderRight(CellStyle.BORDER_NONE);
						cs1.setBorderTop(CellStyle.BORDER_NONE);
						cs1.setBorderBottom(CellStyle.BORDER_NONE);
						cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);

						cs1.setWrapText(true);
						// 设置第二种单元格的样式（用于值）
						cs2.setFont(f2);
						cs2.setBorderLeft(CellStyle.BORDER_NONE);
						cs2.setBorderRight(CellStyle.BORDER_NONE);
						cs2.setBorderTop(CellStyle.BORDER_NONE);
						cs2.setBorderBottom(CellStyle.BORDER_NONE);
						cs2.setWrapText(true);

						// cs2.setAlignment(CellStyle.BORDER_NONE);

						cs3.setFont(f2);
						cs3.setBorderLeft(CellStyle.BORDER_MEDIUM);
						cs3.setBorderRight(CellStyle.BORDER_MEDIUM);
						cs3.setBorderTop(CellStyle.BORDER_MEDIUM);
						cs3.setBorderBottom(CellStyle.BORDER_MEDIUM);
						// cs3.setAlignment(CellStyle.BORDER_HAIR);
						cs4.setFont(f2);
						cs4.setBorderTop(CellStyle.BORDER_MEDIUM);
						cs4.setBorderBottom(CellStyle.BORDER_MEDIUM);

						cs5.setFont(f2);
						cs5.setBorderLeft(CellStyle.BORDER_THIN);
						cs5.setBorderRight(CellStyle.BORDER_THIN);
						cs5.setBorderTop(CellStyle.BORDER_THIN);
						cs5.setBorderBottom(CellStyle.BORDER_THIN);
						cs5.setWrapText(true);
						cs51.setFont(f2);
						cs51.setBorderLeft(CellStyle.BORDER_THIN);
						cs51.setBorderRight(CellStyle.BORDER_THIN);
						cs51.setBorderTop(CellStyle.BORDER_THIN);
						cs51.setBorderBottom(CellStyle.BORDER_THIN);
						cs51.setAlignment(HSSFCellStyle.ALIGN_CENTER);

						cs51.setWrapText(true);
			
						cs52.setFont(f5);
						cs52.setBorderLeft(CellStyle.BORDER_NONE);
						cs52.setBorderRight(CellStyle.BORDER_NONE);
						cs52.setBorderTop(CellStyle.BORDER_NONE);
						cs52.setBorderBottom(CellStyle.BORDER_NONE);
						cs52.setAlignment(HSSFCellStyle.ALIGN_CENTER);

						cs52.setWrapText(true);
						cs52.setRotation((short)255);
						
			int page = 0;
			int cerconNo = 1;
			String tsql = "SELECT wq.pro_data,wq.goods_unit,wq.rec_deg, mg.goods_code, mg.goods_id,mg.shp_ming_cheng,"
					+ "cast(sum(wq.qm_ok_quat) as signed) as goods_count,truncate(sum(wq.tin_tj),2) tin_tj ,truncate(sum(wq.tin_zhl),2) as tin_zhl,count(*) as tuopan   "
					+ "FROM wm_in_qm_i wq,mv_goods mg where wq.im_notice_id = ? and  wq.goods_id = mg.goods_code group by wq.im_notice_id, mg.goods_code,wq.pro_data";
			List<Map<String, Object>> result = systemService
					.findForJdbc(tsql, wmImNoticeH.getNoticeId());


			int size = result.size();
			int pagesize = 10;
			int pagecount = size%pagesize==0?size/pagesize:size/pagesize+1;
		      long sum = 0;
			double sumzl = 0;
       do {
  
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
					(short) 8, page*20+1, (short) 10, page*20+5);
			anchor.setAnchorType(2);
			// 插入图片
			patriarch
					.createPicture(anchor, wb.addPicture(
							byteArrayOut.toByteArray(),
							HSSFWorkbook.PICTURE_TYPE_JPEG));

			// 创建第一行
			Row row = sheet.createRow((short) page*20+0); // 第一行空白

			
			Row row1 = sheet.createRow((short) page*20+1); // 第二行标题
			row1.setHeight((short) 700);
			Cell cellTitle = row1.createCell(0);
			if(wmImNoticeH.getOrderTypeCode().equals("03")){
				cellTitle.setCellValue(ResourceUtil.getConfigByName("comname")+"退货验收单");
			}else if(wmImNoticeH.getOrderTypeCode().equals("01")){
				cellTitle.setCellValue(ResourceUtil.getConfigByName("comname")+"收货验收单");
			}else if(wmImNoticeH.getOrderTypeCode().equals("04")){
				cellTitle.setCellValue(ResourceUtil.getConfigByName("comname")+"越库单");
			}else if(wmImNoticeH.getOrderTypeCode().equals("09")){
				cellTitle.setCellValue(ResourceUtil.getConfigByName("comname")+"收货验收单");
			}
			
			
			cellTitle.setCellStyle(cs);

			Row rowHead1 = sheet.createRow((short) page*20+2); // 头部第一行
			Cell cellHead1 = rowHead1.createCell(0);
			cellHead1.setCellValue("公司地址："+ResourceUtil.getConfigByName("comaddr") );
			cellHead1.setCellStyle(cs1);
			
			Row rowHead2 = sheet.createRow((short) page*20+3); // 头部第二行
			Cell cellHead2 = rowHead2.createCell(0);
			cellHead2.setCellValue("电话："+ResourceUtil.getConfigByName("comtel") );
			cellHead2.setCellStyle(cs1);
			

			Row rowHead4 = sheet.createRow((short) page*20+4); // 头部第二行
			Cell cellHead4 = rowHead4.createCell(0);
			cellHead4.setCellValue("到货日期： " +DateUtils.date2Str(wmImNoticeH.getImData(), DateUtils.date_sdf) );
			cellHead4.setCellStyle(cs2);
			
			Cell cellHead42 = rowHead4.createCell(3);
			cellHead42.setCellValue("预约单号： " +wmImNoticeH.getNoticeId());
			cellHead42.setCellStyle(cs2);
			
			Row rowHead5 = sheet.createRow((short) page*20+5); // 头部第二行
			Cell cellHead5 = rowHead5.createCell(0);
			cellHead5.setCellValue("客户采购单号： "+wmImNoticeH.getImCusCode() );
			cellHead5.setCellStyle(cs2);
			
			Cell cellHead52 = rowHead5.createCell(3);
			cellHead52.setCellValue("月台： " +wmImNoticeH.getPlatformCode());
			cellHead52.setCellStyle(cs2);
			
			Row rowHead6 = sheet.createRow((short) page*20+6); // 头部第二行
			Cell cellHead6 = rowHead6.createCell(0);
	        MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmImNoticeH.getCusCode());

			cellHead6.setCellValue("客户名称： " +wmImNoticeH.getCusCode()+md.getZhongWenQch());
			cellHead6.setCellStyle(cs2);
			
			Cell cellHead62 = rowHead6.createCell(3);
			cellHead62.setCellValue("供应商/车号： "+wmImNoticeH.getImCarNo() );
			cellHead62.setCellStyle(cs2);
			
			Row rowHead7 = sheet.createRow((short) page*20+7); // 头部第二行
			Cell cellHead7 = rowHead7.createCell(0);
			cellHead7.setCellValue("客户电话： " +md.getDianHua());
			cellHead7.setCellStyle(cs2);
			
			Cell cellHead72 = rowHead7.createCell(3);
			cellHead72.setCellValue("打印时间： "+DateUtils.date2Str(DateUtils.getDate(), DateUtils.datetimeFormat) +"                        第"+(page+1)+"页");
			cellHead72.setCellStyle(cs2);
			

			// 合并单元格
			CellRangeAddress c = new CellRangeAddress(page*20+0, page*20+0, 0, 9); // 第一行空白
			CellRangeAddress c1 = new CellRangeAddress(page*20+1, page*20+1, 0, 8);// 第二行标题
			CellRangeAddress c2 = new CellRangeAddress(page*20+2, page*20+2, 0, 9);// 第三行地址
			CellRangeAddress c3 = new CellRangeAddress(page*20+3, page*20+3, 0, 9);// 第四行电话
			
			CellRangeAddress c4 = new CellRangeAddress(page*20+4, page*20+4, 0, 2);// 第5行 到货日期
			CellRangeAddress c42 = new CellRangeAddress(page*20+4, page*20+4, 3, 9);// 第5行预约单号
			CellRangeAddress c5 = new CellRangeAddress(page*20+5, page*20+5, 0, 2);// 第6行客户采购单号
			CellRangeAddress c52 = new CellRangeAddress(page*20+5, page*20+5, 3, 9);// 第6行月台
			CellRangeAddress c6 = new CellRangeAddress(page*20+6, page*20+6, 0, 2);// 第7行客户名称
			CellRangeAddress c62 = new CellRangeAddress(page*20+6, page*20+6, 3, 9);// 第7行车号
			CellRangeAddress c7 = new CellRangeAddress(page*20+7, page*20+7, 0, 2);//第7行客户电话
			CellRangeAddress c72 = new CellRangeAddress(page*20+7, page*20+7, 3, 9);//第7行打印时间
			sheet.addMergedRegion(c);
			sheet.addMergedRegion(c1);
			sheet.addMergedRegion(c2);
			sheet.addMergedRegion(c3);
			sheet.addMergedRegion(c4);
			sheet.addMergedRegion(c5);
			sheet.addMergedRegion(c6);
			sheet.addMergedRegion(c7);
			sheet.addMergedRegion(c42);
			sheet.addMergedRegion(c52);
			sheet.addMergedRegion(c62);
			sheet.addMergedRegion(c72);
			
			Cell cell73 = row.createCell(10);
			cell73.setCellValue("① 财务联 ② 客户联 ③司机联 ④回单联   ");
			cell73.setCellStyle(cs52);
			
			
			CellRangeAddress c73 = new CellRangeAddress(page*20+0, page*20+19, 10, 10);//第7行打印时间
			sheet.addMergedRegion(c73);
			
			Row rowColumnName = sheet.createRow((short) page*20+8); // 列名
			String[] columnNames = { "序号", "商品编码", "商品名称", "生产日期", "货温","单位", "数量", "毛重KG","体积cm³","备注" };
                   if(ResourceUtil.getConfigByName("systuopan").equals("yes")){
					String[]   columnNamest = { "序号", "商品编码", "商品名称", "生产日期", "货温","单位", "数量", "毛重KG","托盘数","备注" };
					   columnNames = columnNamest;
				   }
			for (int i = 0; i < columnNames.length; i++) {
				Cell cell = rowColumnName.createCell(i);
				cell.setCellValue(columnNames[i]);
				cell.setCellStyle(cs3);
			}
			

			int cellsNum = page*20+8;
			int oversize = 0;
            if(size==pagesize&&page==pagecount-1){
            	 oversize = 1;
            }
			for (int i = page*pagesize; i < (page+1)*pagesize + oversize; i++) {
             if(i< size){
 		
				cellsNum++;
				Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
				rowColumnValue.setHeight((short) 250);
				
						Cell cell1 = rowColumnValue.createCell(0);
						cell1.setCellValue(cerconNo);
						cell1.setCellStyle(cs51);
						Cell cell2 = rowColumnValue.createCell(1);
						cell2.setCellValue(result.get(i).get("goods_id")
								.toString());
						cell2.setCellStyle(cs5);

						Cell cell3 = rowColumnValue.createCell(2);
						cell3.setCellValue(result.get(i).get("shp_ming_cheng")
								.toString());
						cell3.setCellStyle(cs5);
                        try {
    						Cell cell4 = rowColumnValue.createCell(3);// 生产日期

    						cell4.setCellStyle(cs5);
    						cell4.setCellValue(result.get(i).get("pro_data")
    								.toString());
						} catch (Exception e) {
							// TODO: handle exception
						
						}
                        
					 try {
							Cell cell5 = rowColumnValue.createCell(4);// 温度
					
							cell5.setCellStyle(cs5);	
							cell5.setCellValue(result.get(i)
									.get("rec_deg").toString());
											} catch (Exception e) {
												// TODO: handle exception
											}
					 
					 try {
							Cell cell6 = rowColumnValue.createCell(5);// 单位
				
							cell6.setCellStyle(cs5);
							cell6.setCellValue(result.get(i).get("goods_unit")
									.toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
					 
					 try {
						 sum = sum + Long.parseLong(result.get(i).get("goods_count")
									.toString());
							Cell cell7 = rowColumnValue.createCell(6);// 数量
		
							cell7.setCellStyle(cs5);	
							cell7.setCellValue(result.get(i).get("goods_count")
									.toString());
						} catch (Exception e) {
							// TODO: handle exception
						}

						try {
							Cell cell8 = rowColumnValue.createCell(7);// 毛重
							 sumzl = sumzl + Double.parseDouble(result.get(i).get("tin_zhl")
										.toString());
							cell8.setCellStyle(cs5);
							cell8.setCellValue(result.get(i).get("tin_zhl")
									.toString());
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
						
							Cell cell9 = rowColumnValue.createCell(8);// 体积
						
							cell9.setCellStyle(cs5);
							if(ResourceUtil.getConfigByName("systuopan").equals("yes")){
								cell9.setCellValue(result.get(i).get("tuopan")
										.toString());
							}else{
								cell9.setCellValue(result.get(i).get("tin_tj")
										.toString());
							}


						} catch (Exception e) {
							// TODO: handle exception
						}


						Cell cell10 = rowColumnValue.createCell(9);// 备注
						cell10.setCellStyle(cs5);
				
				cerconNo++;
			}
             if(i== size){
            	
            	 cellsNum++;
 				Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
 				rowColumnValue.setHeight((short) 250);
 				Cell cell6 = rowColumnValue.createCell(6);// 备注
 				cell6.setCellValue(Long.toString(sum));
 				Cell cell7 = rowColumnValue.createCell(7);// 重量
 				cell7.setCellValue(Double.toString(sumzl));
//				cell6.setCellStyle(cs5);
 				Cell cell0 = rowColumnValue.createCell(0);// 合计
 				cell0.setCellValue("合计：");
//				cell0.setCellStyle(cs5);
				CellRangeAddress c15 = new CellRangeAddress( cellsNum,
						 cellsNum, 0, 5);
				sheet.addMergedRegion(c15);
            	 cerconNo++;
            	 
             }
             
           	 
             }
			Row rowColumnInfo = sheet.createRow((short) 1 + cellsNum); // 列名
			rowColumnInfo.setHeight((short) 250);
			rowColumnInfo.createCell(0).setCellValue(
					"验收人员：                               送货人员：                                客户/委托人：");
			CellRangeAddress c15 = new CellRangeAddress(1 + cellsNum,
					1 + cellsNum, 0, 9);
			sheet.addMergedRegion(c15);
			page++;
   	} while (page<pagecount);
			fileOut = response.getOutputStream();
			 HSSFPrintSetup printSetup = sheet.getPrintSetup();   
			 printSetup.setPaperSize(HSSFPrintSetup.QUARTO_PAPERSIZE);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 批量删除进货通知抬头
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "进货通知删除成功";
		try {
			for (String id : ids.split(",")) {
				WmImNoticeHEntity wmImNoticeH = systemService.getEntity(
						WmImNoticeHEntity.class, id);
				WmPlatIoEntity wmPlatIo = systemService.findUniqueByProperty(
						// 删除月台计划
						WmPlatIoEntity.class, "docId",
						wmImNoticeH.getNoticeId());
				if (wmPlatIo != null) {
					systemService.delete(wmPlatIo);
				}
				wmImNoticeH.setImSta(Constants.wm_sta3);
				wmImNoticeHService.saveOrUpdate(wmImNoticeH);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "进货通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加进货通知抬头
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmImNoticeHEntity wmImNoticeH,
			WmImNoticeHPage wmImNoticeHPage, HttpServletRequest request) {
		List<WmImNoticeIEntity> wmImNoticeIList = wmImNoticeHPage
				.getWmImNoticeIList();
		AjaxJson j = new AjaxJson();
		String message = "进货通知添加成功";
		try {

			String noticeid = getNextNoticeid(wmImNoticeH.getOrderTypeCode());
			wmImNoticeH.setNoticeId(noticeid);
			WmPlatIoEntity wmPlatIo = new WmPlatIoEntity();
			wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
			wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
			wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
			wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
			wmPlatIo.setPlatSta(Constants.wm_sta1);
			wmPlatIo.setPlatBeizhu("司机:" + wmImNoticeH.getImCarDri() + "电话:"
					+ wmImNoticeH.getImCarMobile());
			systemService.save(wmPlatIo);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", wmImNoticeH.getNoticeId());
			TSUser user = ResourceUtil.getSessionUserName();
			String roles = "";
			if (user != null) {
				List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				for (TSRoleUser ru : rUsers) {
					TSRole role = ru.getTSRole();
					roles += role.getRoleCode() + ",";
				}
				if (roles.length() > 0) {
					roles = roles.substring(0, roles.length() - 1);
				}
				if(roles.equals("CUS")){
					wmImNoticeH.setCusCode(user.getUserName());

				}
			}
			if(roles.equals("CUS")){
				wmImNoticeH.setImSta(Constants.wm_sta0);
			}else{
				wmImNoticeH.setImSta(Constants.wm_sta1);
			}
			if(wmImNoticeH.getCusCode()==null){
						if(roles.equals("CUS")){
						wmImNoticeH.setCusCode(user.getUserName());
			}
			}

			List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();
			for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIList) {
				if(!StringUtil.isEmpty(wmImNoticeIEntity.getGoodsCode())){
	
				try {
					String date[]=wmImNoticeIEntity.getGoodsCode().split("-");  
					wmImNoticeIEntity.setGoodsCode(date[0]);
					wmImNoticeIEntity.setGoodsName(date[1]);
				} catch (Exception e) {
					// TODO: handle exception
				}

				wmImNoticeIListnew.add(wmImNoticeIEntity);
				
				}
			}

			wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
		
			try {
				TuiSongMsgUtil.sendMessage("收货通知", Constants.SMS_SEND_TYPE_3,
						"RKYYTZ", map, "admin", ResourceUtil.getSessionUserName()
								.getUserName());
			} catch (Exception e) {
				// TODO: handle exception
			}

			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "进货通知添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
    @RequestMapping(params = "doGet")
    @ResponseBody
    public AjaxJson dogetfromother(String formDate, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "读取成功";
		if ("U8".equals(ResourceUtil.getConfigByName("interfacetype"))){
			if(StringUtil.isEmpty(formDate)){
				formDate = "2011-01-01";
			}
			yyUtil.getPo(formDate);

		}
		if ("UAS".equals(ResourceUtil.getConfigByName("interfacetype"))){
			String masterbill[] = {"XKN_TEST","XKN_TEST"};
			for(int m =0;m<masterbill.length;m++) {
				try {
					if (StringUtil.isEmpty(formDate)) {
						formDate = "2011-01-01";
					}
					String master = masterbill[m];
					String billclass[] = {"采购验收单", "销售退货单", "其它入库单", "其它采购入库单"};
					for (int i = 0; i < billclass.length; i++) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("lastUpdateTime", formDate);
						paramMap.put("pi_class", billclass[i]);
						paramMap.put("master", master);

						billResult billResult = wmIntUtil.getBillin(paramMap);
						for (int s = 0; s < billResult.getData().size(); s++) {
							String imcuscode = billResult.getData().get(s).getPiInoutno();
							if (StringUtil.isNotEmpty(imcuscode)) {
								WmImNoticeHEntity wmimh = systemService.findUniqueByProperty(WmImNoticeHEntity.class, "imCusCode", imcuscode);
								if (wmimh == null) {
									WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
									List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();

									wmImNoticeH.setOrderTypeCode("01");
									String noticeid = getNextNoticeid(wmImNoticeH.getOrderTypeCode());

									wmImNoticeH.setCusCode(ResourceUtil.getConfigByName("uas.cuscode"));
									wmImNoticeH.setNoticeId(noticeid);
									wmImNoticeH.setPlatformCode(Integer.toString(billResult.getData().get(s).getPiId()));
									wmImNoticeH.setPiClass(billResult.getData().get(s).getPiClass());
									wmImNoticeH.setPiMaster(master);
									wmImNoticeH.setSupCode(billResult.getData().get(s).getPiCardcode());
									MdSupEntity mdsup = systemService.findUniqueByProperty(MdSupEntity.class, "gysBianMa", wmImNoticeH.getSupCode());
									if (mdsup != null) {
										wmImNoticeH.setSupName(mdsup.getZhongWenQch());
									}
									wmImNoticeH.setImCusCode(imcuscode);
									wmImNoticeH.setSupName(billResult.getData().get(s).getPiReceivename());
									for (int k = 0; k < billResult.getData().get(s).getDetail().size(); k++) {
										WmImNoticeIEntity wmi = new WmImNoticeIEntity();
										wmi.setGoodsCode(billResult.getData().get(s).getDetail().get(k).getPdProdcode());
										MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
												MvGoodsEntity.class, "goodsCode", wmi.getGoodsCode());
										if (mvgoods != null) {
											wmi.setGoodsName(mvgoods.getGoodsName());
											wmi.setGoodsUnit(mvgoods.getShlDanWei());
										}
										wmi.setGoodsCount(Integer.toString(billResult.getData().get(s).getDetail().get(k).getPdInqty()));
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
										wmi.setOtherId(Integer.toString(billResult.getData().get(s).getDetail().get(k).getPdPdno()));
										wmImNoticeIListnew.add(wmi);
									}
									wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
								}
							} else {
								continue;
							}
						}
					}


					systemService.addLog(message, Globals.Log_Type_UPDATE,
							Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "读取失败";
					throw new BusinessException(e.getMessage());
				}
			}
		}



        j.setMsg(message);
        return j;
    }

	@RequestMapping(params = "doPost")
	@ResponseBody
	public AjaxJson dopost(String id,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "读取成功";
		WmImNoticeHEntity wmImNoticeH = wmImNoticeHService.getEntity(WmImNoticeHEntity.class, id);

		//获取参数
		Object id0 = wmImNoticeH.getNoticeId();
		//===================================================================================
		//查询-产品
		String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
		try{
			List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
					.findHql(hql0, id0);
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(WmImNoticeIEntity t:wmImNoticeIEntityList){

                List<WmInQmIEntity> WmInQmlist = new ArrayList<WmInQmIEntity>();
               String  hql = null;
                 hql = "from WmInQmIEntity t where t.imNoticeItem = ? ";

                WmInQmlist = systemService.findHql(hql, new Object[] { t.getId() });
                for(WmInQmIEntity qm:WmInQmlist){

                    Map<String,String> map = new HashMap<String,String>();
    //				[{"pd_pdno":1,"pd_outqty":"100","pi_class":"出货单","pi_id":50765226,"pi_inoutno":"JRS180800008"}]
                    map.put("pd_pdno",t.getOtherId());
                    map.put("pd_outqty",qm.getBaseGoodscount());
                    map.put("pi_class",wmImNoticeH.getPiClass());
                    map.put("pi_id",wmImNoticeH.getPlatformCode());
                    map.put("pi_inoutno",wmImNoticeH.getImCusCode());
                    map.put("pd_prodmadedate",qm.getProData());

                    MdGoodsEntity mvgoods  = systemService.findUniqueByProperty(
                            MdGoodsEntity.class, "shpBianMa",
                            qm.getGoodsId());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
                    Date date = dateFormat.parse(qm.getProData()); // 指定日期
                    if(mvgoods!=null){

                        Date newDate = addDate(date, Long.parseLong(mvgoods.getBzhiQi())); // 指定日期加上10天
                        map.put("pd_replydate",dateFormat.format(newDate));
                    }else{
                        Date newDate = addDate(date, 1); // 指定日期加上10天
                        map.put("pd_replydate ",qm.getProData());
                    }

                    list.add(map);

                }

			}
			String jsonStr = JSONArray.fromObject(list).toString();
			JSONArray ja = JSONArray.fromObject(jsonStr);
			resResult resResult = wmIntUtil.postBill(ja.toString(),wmImNoticeH.getPiMaster());
			j.setMsg(resResult.getDetailedMessage());
		}catch (Exception e){

		}

		j.setMsg(message);
		return j;
	}



    public static Date addDate(Date date, long day){
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

private String getNextNoticeid(String orderType){
		String noticeid=null;
	Map<String, Object> countMap = systemService
			.findOneForJdbc("SELECT count(*)+1 as count FROM wm_im_notice_h  t where  TO_DAYS(t.create_date) = TO_DAYS(NOW());");
	if (StringUtil.isEmpty(orderType)){
		orderType = "01";
	}
	if (countMap != null) {
		if(orderType.equals("03")){
			noticeid = "TH"
					+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
					+ "-"
					+ StringUtil.leftPad(
					((Long) countMap.get("count")).intValue(), 4,
					'0');
		}else if(orderType.equals("01")){
			noticeid = "RK"
					+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
					+ "-"
					+ StringUtil.leftPad(
					((Long) countMap.get("count")).intValue(), 4,
					'0');
		}else if(orderType.equals("04")){
			noticeid = "YK"
					+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
					+ "-"
					+ StringUtil.leftPad(
					((Long) countMap.get("count")).intValue(), 4,
					'0');
		}else if(orderType.equals("09")){
			noticeid = "QT"
					+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
					+ "-"
					+ StringUtil.leftPad(
					((Long) countMap.get("count")).intValue(), 4,
					'0');
		}

	}
		return  noticeid;
}
	/**
	 * 更新进货通知抬头
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmImNoticeHEntity wmImNoticeH,
			WmImNoticeHPage wmImNoticeHPage, HttpServletRequest request) {
		List<WmImNoticeIEntity> wmImNoticeIList = wmImNoticeHPage
				.getWmImNoticeIList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try {
				try {
					WmPlatIoEntity wmPlatIo = systemService.findUniqueByProperty(
							WmPlatIoEntity.class, "docId", wmImNoticeH.getNoticeId());
					if (wmPlatIo != null) {
						wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
						wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
						wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
						wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
						wmPlatIo.setPlatSta(Constants.wm_sta1);
						wmPlatIo.setPlatBeizhu("司机" + wmImNoticeH.getImCarDri() + "电话"
								+ wmImNoticeH.getImCarMobile());
						systemService.saveOrUpdate(wmPlatIo);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			if(wmImNoticeH.getCusCode()==null){
				TSUser user = ResourceUtil.getSessionUserName();
				String roles = "";
				if (user != null) {
					List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
					for (TSRoleUser ru : rUsers) {
						TSRole role = ru.getTSRole();
						roles += role.getRoleCode() + ",";
					}
					if (roles.length() > 0) {
						roles = roles.substring(0, roles.length() - 1);
					}
					if(roles.equals("CUS")){
						wmImNoticeH.setCusCode(user.getUserName());

					}
				}
			}
			
			
			wmImNoticeHService.updateMain(wmImNoticeH, wmImNoticeIList);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新进货通知抬头失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 进货通知抬头新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(ModelMap modelMap,WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmImNoticeH.getId())) {
			wmImNoticeH = wmImNoticeHService.getEntity(WmImNoticeHEntity.class,
					wmImNoticeH.getId());
			req.setAttribute("wmImNoticeHPage", wmImNoticeH);

		}else{
			wmImNoticeH.setOrderTypeCode(req.getParameter("orderTypeCode").toString());
			TSUser user = ResourceUtil.getSessionUserName();
			String roles = "";
			if (user != null) {
				List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				for (TSRoleUser ru : rUsers) {
					TSRole role = ru.getTSRole();
					roles += role.getRoleCode() + ",";
				}
				if (roles.length() > 0) {
					roles = roles.substring(0, roles.length() - 1);
				}
				if(roles.equals("CUS")){
					wmImNoticeH.setCusCode(user.getUserName());
					wmImNoticeH.setReadonly("readonly");
					wmImNoticeH.setWherecon("where cus_code = '"+user.getUserName()+"'");
				    modelMap.put("roleName", roles);
				    req.setAttribute("wmImNoticeHPage", wmImNoticeH);
					
				}else{
	   if(!StringUtil.isEmpty( wmImNoticeH.getCusCode())){
			wmImNoticeH.setWherecon("where cus_code = '"+wmImNoticeH.getCusCode()+"'");
	   }else{
		   wmImNoticeH.setWherecon("where 1 = 1");
	   }
					

				    req.setAttribute("wmImNoticeHPage", wmImNoticeH);
				}
			}	
		}
		

        
		
		return new ModelAndView("com/zzjee/wm/wmImNoticeH-add");
	}

	/**
	 * 进货通知抬头编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmImNoticeH.getId())) {
			wmImNoticeH = wmImNoticeHService.getEntity(WmImNoticeHEntity.class,
					wmImNoticeH.getId());
			
			
			TSUser user = ResourceUtil.getSessionUserName();
			String roles = "";
			if (user != null) {
				List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				for (TSRoleUser ru : rUsers) {
					TSRole role = ru.getTSRole();
					roles += role.getRoleCode() + ",";
				}
				if (roles.length() > 0) {
					roles = roles.substring(0, roles.length() - 1);
				}
				if(roles.equals("CUS")){
					wmImNoticeH.setCusCode(user.getUserName());
					wmImNoticeH.setReadonly("readonly");
					wmImNoticeH.setWherecon("where cus_code = '"+user.getUserName()+"'");				
				}else{
				
				wmImNoticeH.setWherecon("where 1 = 1");
				}
			}
			
			req.setAttribute("wmImNoticeHPage", wmImNoticeH);
		}
		return new ModelAndView("com/zzjee/wm/wmImNoticeH-update");
	}

	/**
	 * 加载明细列表[进货通知明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "wmImNoticeIList")
	public ModelAndView wmImNoticeIList(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest req) {

		// ===================================================================================
		// 获取参数
		Object id0 = wmImNoticeH.getNoticeId();
		// ===================================================================================
		// 查询-进货通知明细
		String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
		try {
			if(StringUtil.isNotEmpty(id0)){
				List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
						.findHql(hql0, id0);
				req.setAttribute("wmImNoticeIList", wmImNoticeIEntityList);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/zzjee/wm/wmImNoticeIList");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmImNoticeHEntity wmImNoticeH,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap map) {
		CriteriaQuery cq = new CriteriaQuery(WmImNoticeHEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmImNoticeH);
		try {
			TSUser user = ResourceUtil.getSessionUserName();
			String roles = "";
			if (user != null) {
				List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				for (TSRoleUser ru : rUsers) {
					TSRole role = ru.getTSRole();
					roles += role.getRoleCode() + ",";
				}
				if (roles.length() > 0) {
					roles = roles.substring(0, roles.length() - 1);
				}
				if(roles.equals("CUS")){
					cq.eq("cusCode", user.getUserName());
					
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		List<WmImNoticeHEntity> list = this.wmImNoticeHService
				.getListByCriteriaQuery(cq, false);
		List<WmImNoticeHPage> pageList = new ArrayList<WmImNoticeHPage>();
		if (list != null && list.size() > 0) {
			for (WmImNoticeHEntity entity : list) {
				try {
					WmImNoticeHPage page = new WmImNoticeHPage();
					MyBeanUtils.copyBeanNotNull2Bean(entity, page);
					Object id0 = entity.getNoticeId();
					String hql0 = "from WmImNoticeIEntity where 1 = 1 AND iM_NOTICE_ID = ? ";
					List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
							.findHql(hql0, id0);
					page.setWmImNoticeIList(wmImNoticeIEntityList);
					pageList.add(page);
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
			}
		}
		map.put(NormalExcelConstants.FILE_NAME, "进货通知抬头");
		map.put(NormalExcelConstants.CLASS, WmImNoticeHPage.class);
		map.put(NormalExcelConstants.PARAMS, new ExportParams("进货通知抬头列表",
				"导出人:admin", "导出信息"));
		map.put(NormalExcelConstants.DATA_LIST, pageList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 通过excel导入数据
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request,
			HttpServletResponse response) {
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
				List<WmNoticeImpPage> list = ExcelImportUtil.importExcel(
						file.getInputStream(), WmNoticeImpPage.class, params);

				String flag = "Y";
				String message="";
				for(WmNoticeImpPage wmt:list){
					MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
							MvGoodsEntity.class, "goodsCode", wmt.getGoodsId());
					if(mvgoods==null){
						flag = "N";
						message=message+wmt.getGoodsId();
					}
				}
				if("N".equals(flag)){
					j.setMsg(message+"不存在");
					return j;
				}

				List<WmNoticeImpPage> listheader =  ExcelImportUtil.importExcel(
						file.getInputStream(), WmNoticeImpPage.class, params);
				for(int i=0; i<listheader.size()-1;i++){
					for(int  k=listheader.size()-1;k>i;k--){
						if  (listheader.get(k).getImCusCode().equals(listheader.get(i).getImCusCode()))  {
							listheader.remove(k);
						}
					}
				}

				for(WmNoticeImpPage pageheader: listheader){
                   List<WmImNoticeHEntity>  wmimh = systemService.findByProperty(WmImNoticeHEntity.class, "imCusCode", pageheader.getImCusCode());
                   if(wmimh!=null&&wmimh.size()>0){
                       continue;
                   }
                    List<WmImNoticeIEntity> wmImNoticeIListnew = new ArrayList<WmImNoticeIEntity>();
                for (WmNoticeImpPage page : list) {
                	if(pageheader.getImCusCode().equals(page.getImCusCode())){
						WmImNoticeIEntity  wmi = new WmImNoticeIEntity();
						wmi.setGoodsCode(page.getGoodsId());
						MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
								MvGoodsEntity.class, "goodsCode", wmi.getGoodsCode());
						if (mvgoods != null) {
							wmi.setGoodsName(mvgoods.getGoodsName());
							wmi.setGoodsUnit(mvgoods.getShlDanWei());
						}
						try{
							wmi.setGoodsCount(page.getGoodsQua());
                            String[] args=page.getGoodsQua().split("\\.");
                            wmi.setGoodsCount(args[0]);
                        }catch (Exception e){

                        }

						wmi.setOtherId(page.getOtherId());
						wmImNoticeIListnew.add(wmi);
					}
				}

                WmImNoticeHEntity wmImNoticeH  = new WmImNoticeHEntity();
				wmImNoticeH.setOrderTypeCode(pageheader.getOrderTypeCode());
				String noticeid = getNextNoticeid(wmImNoticeH.getOrderTypeCode());
				wmImNoticeH.setCusCode(pageheader.getCusCode());
                wmImNoticeH.setNoticeId(noticeid);
				wmImNoticeH.setImData(pageheader.getImData());
                wmImNoticeH.setPlatformCode(pageheader.getCusCode());
                wmImNoticeH.setImBeizhu(pageheader.getImBeizhu() );
                wmImNoticeH.setSupCode(pageheader.getSupCode() );
                MdSupEntity mdsup  = systemService.findUniqueByProperty(MdSupEntity.class,"gysBianMa",wmImNoticeH.getSupCode());
                if(mdsup!=null){
                    wmImNoticeH.setSupName(mdsup.getZhongWenQch());
                }
                wmImNoticeH.setImCusCode(pageheader.getImCusCode());
                wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIListnew);
				}
                j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			} finally {
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
		map.put(NormalExcelConstants.FILE_NAME, "进货通知");
		map.put(NormalExcelConstants.CLASS, WmNoticeImpPage.class);
		map.put(NormalExcelConstants.PARAMS, new ExportParams("进货通知",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST, new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "wmImNoticeHController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<WmImNoticeHEntity> list() {
		List<WmImNoticeHEntity> listWmImNoticeHs = wmImNoticeHService
				.getList(WmImNoticeHEntity.class);
		return listWmImNoticeHs;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmImNoticeHEntity task = wmImNoticeHService.get(
				WmImNoticeHEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(
			@RequestBody WmImNoticeHPage wmImNoticeHPage,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		ResultDO D0 = new  ResultDO();

		Set<ConstraintViolation<WmImNoticeHPage>> failures = validator
				.validate(wmImNoticeHPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		List<WmImNoticeIEntity> wmImNoticeIList = wmImNoticeHPage
				.getWmImNoticeIList();

		WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmImNoticeH, wmImNoticeHPage);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		wmImNoticeHService.addMain(wmImNoticeH, wmImNoticeIList);
		D0.setOK(true);
		return new ResponseEntity(D0, HttpStatus.OK);

		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
//		String id = wmImNoticeHPage.getId();
//		URI uri = uriBuilder.path("/rest/wmImNoticeHController/" + id).build()
//				.toUri();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(uri);
//
//		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmImNoticeHPage wmImNoticeHPage) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmImNoticeHPage>> failures = validator
				.validate(wmImNoticeHPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		List<WmImNoticeIEntity> wmImNoticeIList = wmImNoticeHPage
				.getWmImNoticeIList();

		WmImNoticeHEntity wmImNoticeH = new WmImNoticeHEntity();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmImNoticeH, wmImNoticeHPage);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		wmImNoticeHService.updateMain(wmImNoticeH, wmImNoticeIList);

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		WmImNoticeHEntity wmImNoticeH = wmImNoticeHService.get(
				WmImNoticeHEntity.class, id);
		wmImNoticeHService.delMain(wmImNoticeH);
	}
}
