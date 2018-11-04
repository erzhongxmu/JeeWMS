package com.zzjee.wm.controller;
import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.*;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.*;
import com.zzjee.wm.service.WmOmNoticeHServiceI;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.*;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
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
import org.jsoup.helper.DataUtil;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.net.URI;

import jdk.nashorn.internal.ir.TryNode;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Title: Controller
 * @Description: 出货通知
 * @author erzhongxmu
 * @date 2017-08-15 23:18:59
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/wmOmNoticeHController")
public class WmOmNoticeHController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmOmNoticeHController.class);

	@Autowired
	private WmOmNoticeHServiceI wmOmNoticeHService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 出货通知列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmOmNoticeHList");
	}
	/**
	 * 出货通知列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "listitem")
	public ModelAndView listitem(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmOmNoticeitemList");
	}



	@RequestMapping(params = "datagriditem")
	public void datagriditem(WmOmNoticeIEntity wmOmNoticeitem,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmOmNoticeIEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmNoticeitem);

		try {
			// 自定义追加查询条件

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
		this.wmOmNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "saveOmnotice")
	@ResponseBody
	public AjaxJson saveOmnotice(wmomnoticeipage page){
		String message = null;
		List<WmOmNoticeIEntity> demos=page.getWmomnoticeirows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmOmNoticeIEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmOmNoticeIEntity t =systemService.get(WmOmNoticeIEntity.class, jeecgDemo.getId());
					try {
						message = "保存成功";
						t.setBinId(jeecgDemo.getBinId());
						t.setPlanSta(jeecgDemo.getPlanSta());
						t.setGoodsProData(jeecgDemo.getGoodsProData());
						t.setBaseGoodscount(jeecgDemo.getBaseGoodscount());
						t.setGoodsQua(jeecgDemo.getGoodsQua());
						systemService.updateEntitie(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}


	@RequestMapping(params = "listqt")
	public ModelAndView listqt(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmOmqtNoticeHList");
	}

	@RequestMapping(params = "cuslist")
	public ModelAndView cuslist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/cuswmOmNoticeHList");
	}

	@RequestMapping(params = "batchconflist")
	public ModelAndView batchconf_rowedtior(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/batchconf_rowedtior");
	}


	@RequestMapping(params = "doPrintpage")
	public ModelAndView doPrint(String id,HttpServletRequest request) {
		WmOmNoticeHEntity wmOmNoticeHEntity = wmOmNoticeHService.getEntity(WmOmNoticeHEntity.class, id);
		wmOmNoticeHEntity.setPrintStatus("已打印");
		systemService.updateEntitie(wmOmNoticeHEntity);
		request.setAttribute("wmOmNoticeHPage", wmOmNoticeHEntity);
		request.setAttribute("kprq",DateUtils.date2Str(wmOmNoticeHEntity.getCreateDate(),DateUtils.date_sdf));
		request.setAttribute("comname", ResourceUtil.getConfigByName("comname"));

		if(StringUtil.isNotEmpty(wmOmNoticeHEntity.getImCusCode())){
			request.setAttribute("noticeid", wmOmNoticeHEntity.getImCusCode());
		}else{
			request.setAttribute("noticeid", wmOmNoticeHEntity.getOmNoticeId());
		}

		try{
			MdCusEntity mdcus = systemService.findUniqueByProperty(MdCusEntity.class,"keHuBianMa",wmOmNoticeHEntity.getCusCode());
			MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class,"keHuBianMa",wmOmNoticeHEntity.getOcusCode());
			request.setAttribute("cusname",wmOmNoticeHEntity.getCusCode()+"-"+ mdcus.getZhongWenQch());

			request.setAttribute("ocusname",wmOmNoticeHEntity.getOcusCode()+"-"+ mdcusother.getZhongWenQch());

		}catch (Exception e){

		}
		//获取参数
		Object id0 = wmOmNoticeHEntity.getOmNoticeId();
		//===================================================================================
		//查询-产品
		String hql0 = "from WmOmQmIEntity where 1 = 1 AND omNoticeId = ? order by binId";
		Double tomsum = 0.00;
		Double  noticesum = 0.00;
		try{
			List<WmOmQmIEntity> wmOmQmIEntityList = systemService.findHql(hql0, id0);//获取行项目

			try{
				for(WmOmQmIEntity tom:wmOmQmIEntityList){
					tomsum = tomsum + Double.parseDouble(tom.getBaseGoodscount());
				}
			}catch ( Exception e){

			}

			String hqlnotice = "from WmOmNoticeIEntity where 1 = 1 AND oM_NOTICE_ID = ? ";
			List<WmOmNoticeIEntity> wmOmNoticeIEntityList = systemService.findHql(hqlnotice,id0);
			for(WmOmNoticeIEntity tnotice:wmOmNoticeIEntityList){
				noticesum = noticesum + Double.parseDouble(tnotice.getBaseGoodscount());
			}
			if(Double.doubleToLongBits(noticesum) != Double.doubleToLongBits(tomsum)){
				request.setAttribute("jianhuoremark", "订单："+Double.toString(noticesum)+" 拣货："+Double.toString(tomsum));
			}else{
				request.setAttribute("jianhuoremark", "全部拣货，共"+Double.toString(noticesum));
			}
			request.setAttribute("wmOmQmIList", wmOmQmIEntityList);
		}catch (Exception e){

		}
		return new ModelAndView("com/zzjee/wm/print/jianhuorenwu-print");
	}
//
	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */


	@RequestMapping(params = "datagridbatchconf")
	public void datagridbatchconf(WmOmNoticeHEntity wmOmNoticeH,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmOmNoticeHEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmNoticeH);

		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("delvData_begin1");
			String query_imData_end = request.getParameter("delvData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			}
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}


		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());

		}

		cq.eq("omSta", Constants.wm_sta6);//"复核完成"
		cq.add();
		this.wmOmNoticeHService.getDataGridReturn(cq, true);
		List<WmOmNoticeHEntity> resultnew = new ArrayList<WmOmNoticeHEntity>();
		List<WmOmNoticeHEntity> resultold = dataGrid.getResults();
		for (WmOmNoticeHEntity WmOmNoticeH : resultold) {
			WmOmNoticeH.setDelvData(null);
			resultnew.add(WmOmNoticeH);

		}
		dataGrid.setResults(resultnew);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(confrowpage page){
		String message = null;
		List<WmOmNoticeHEntity> demos=page.getDownrows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmOmNoticeHEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmOmNoticeHEntity t =systemService.get(WmOmNoticeHEntity.class, jeecgDemo.getId());
					try {
						message = "回单成功";
						WmNoticeConfEntity confe = new WmNoticeConfEntity();
						confe.setBeizhu(jeecgDemo.getOmBeizhu());
						confe.setHdData(jeecgDemo.getDelvData());
						confe.setCusCode(jeecgDemo.getCusCode());
						confe.setWmNoticeId(jeecgDemo.getOmNoticeId());
						systemService.save(confe);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}
	@RequestMapping(params = "datagridqt")
	public void datagridqt(WmOmNoticeHEntity wmOmNoticeH,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmOmNoticeHEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmNoticeH);

		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("delvData_begin1");
			String query_imData_end = request.getParameter("delvData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			}
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());

		}
		if (wmOmNoticeH.getOmSta() == null) {
			cq.notEq("omSta", Constants.wm_sta4);
		}
		cq.like("omNoticeId", "QT%");
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("omSta", "desc");
//		cq.setOrder(map);
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("omSta", "asc");
//		cq.setOrder(map2);
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("createDate", "desc");
		cq.setOrder(map1);

		cq.add();
		this.wmOmNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagrid")
	public void datagrid(WmOmNoticeHEntity wmOmNoticeH,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmOmNoticeHEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmNoticeH);

		try {
			// 自定义追加查询条件
			String query_imData_begin = request.getParameter("delvData_begin1");
			String query_imData_end = request.getParameter("delvData_end2");

			if (StringUtil.isNotEmpty(query_imData_begin)) {
				cq.ge("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_begin));
			}
			if (StringUtil.isNotEmpty(query_imData_end)) {
				cq.le("delvData", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
						.parse(query_imData_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
			cq.eq("cusCode", wmUtil.getCusCode());

		}
		if (wmOmNoticeH.getOmSta() == null) {
			cq.notEq("omSta", Constants.wm_sta4);
		}
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("createDate", "desc");
		cq.setOrder(map1);
		cq.add();
		this.wmOmNoticeHService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "docheck")
	@ResponseBody
	public AjaxJson docheck(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "成功";
		try {

			String goods = null;
			String goodsid = request.getParameter("goodscode").toString();
			if(!StringUtil.isEmpty(goodsid)){
				if(goodsid.endsWith("l")){
					goods = goodsid.substring(0,goodsid.length() - 1);
				}else{
					goods = goodsid;
				}

			}
			String sql = "select sum(base_goodscount) as qua from wv_stock t where  t.goods_id =  '"
					+ goods + "'";
			Map<String, Object> binMap	 = systemService.findOneForJdbc(sql);
			if(binMap!=null){
				if(Long.parseLong(binMap.get("qua").toString())< Long.parseLong(request.getParameter("goodsqua").toString())){
					j.setSuccess(false);
					message = request.getParameter("goodscode").toString() +"库存为"+binMap.get("qua").toString();
					j.setMsg(message);
					return j;
				}
			}
		} catch (Exception e) {
			j.setSuccess(false);
			message = request.getParameter("goodscode").toString() +"库存为0";
			j.setMsg(message);
			return j;
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 删除出货通知
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmOmNoticeHEntity wmOmNoticeH, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		wmOmNoticeH = systemService.getEntity(WmOmNoticeHEntity.class, wmOmNoticeH.getId());
		String message = "出货通知删除成功";
		try{
			wmOmNoticeH.setOmSta("已删除");
			Object id0 = wmOmNoticeH.getOmNoticeId();
			//===================================================================================
			//1.查询出数据库的明细数据-出货商品明细
			String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND oM_NOTICE_ID = ? ";
			List<WmOmNoticeIEntity> wmOmNoticeIOldList = systemService.findHql(hql0,id0);
			for (WmOmNoticeIEntity wmOmNoticeIEntity : wmOmNoticeIOldList) {
				wmOmNoticeIEntity.setOmSta("已删除");
				wmOmNoticeIEntity.setPlanSta("Y");
				systemService.saveOrUpdate(wmOmNoticeIEntity);
			}
			wmOmNoticeHService.saveOrUpdate(wmOmNoticeH);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "出货通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除出货通知
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "出货通知删除成功";
		try{
			for(String id:ids.split(",")){
				WmOmNoticeHEntity wmOmNoticeH = systemService.getEntity(WmOmNoticeHEntity.class,
						id
				);
				wmOmNoticeHService.delMain(wmOmNoticeH);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "出货通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加出货通知
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmOmNoticeHEntity wmOmNoticeH,WmOmNoticeHPage wmOmNoticeHPage, HttpServletRequest request) {
		List<WmOmNoticeIEntity> wmOmNoticeIList =  wmOmNoticeHPage.getWmOmNoticeIList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			String noticeid = getNextNoticeId(wmOmNoticeH.getOrderTypeCode());
			WmPlatIoEntity wmPlatIo = new WmPlatIoEntity();
			wmPlatIo.setCarno(wmOmNoticeH.getReCarno());
			wmPlatIo.setDocId(noticeid);
			wmPlatIo.setPlanIndata(wmOmNoticeH.getDelvData());
			wmPlatIo.setPlatId(wmOmNoticeH.getOmPlatNo());
			wmPlatIo.setPlatSta(Constants.wm_sta1);
			wmPlatIo.setPlatBeizhu("司机:" + wmOmNoticeH.getReMember() + "电话:"
					+ wmOmNoticeH.getReMobile());
			systemService.save(wmPlatIo);
			wmOmNoticeH.setOmNoticeId(noticeid);
			wmOmNoticeH.setOmSta(Constants.wm_sta1);
			if(wmOmNoticeH.getCusCode()==null){
				if(StringUtil.isNotEmpty(wmUtil.getCusCode())){
					wmOmNoticeH.setCusCode(wmUtil.getCusCode());

				}
			}
			List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
			for (WmOmNoticeIEntity wmomNoticeIEntity : wmOmNoticeIList) {
				if(!StringUtil.isEmpty(wmomNoticeIEntity.getGoodsId())){
					try {
						String date[]=wmomNoticeIEntity.getGoodsId().split("-");
						wmomNoticeIEntity.setGoodsId(date[0]);
						wmomNoticeIEntity.setGoodsName(date[1]);
					} catch (Exception e) {
						// TODO: handle exception
					}

					wmomNoticeIListnew.add(wmomNoticeIEntity);
				}

			}

			if(StringUtil.isNotEmpty( wmOmNoticeH.getOcusCode())){
				String datecuso[]= wmOmNoticeH.getOcusCode().split("-");
				MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class, "keHuBianMa", datecuso[0]);
				if (mdcusother != null) {
					wmOmNoticeH.setOcusName(mdcusother.getZhongWenQch());
				}
			}

			wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
			Map<String ,Object> map = new HashMap<String ,Object>();
			map.put("id", wmOmNoticeH.getOmNoticeId());
			try {
				TuiSongMsgUtil.sendMessage("出货通知", Constants.SMS_SEND_TYPE_3, "CKYYTZ", map, "admin", ResourceUtil.getSessionUserName().getUserName());

			} catch (Exception e) {
				// TODO: handle exception
			}


			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "出货通知添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	private String getNextNoticeId(String orderType){
		Map<String, Object> countMap = systemService
				.findOneForJdbc("SELECT count(*)+1 as count FROM wm_om_notice_h  t where  TO_DAYS(t.create_date) = TO_DAYS(NOW());");
		String noticeid = null;
		if (StringUtil.isEmpty(orderType)){
			orderType = "11";
		}
		if(orderType.equals("19")){
			noticeid = "QT"
					+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
					+ "-"
					+ StringUtil.leftPad(
					((Long) countMap.get("count")).intValue(), 4,
					'0');
		}else {
			noticeid = "CK"
					+ DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
					+ "-"
					+ StringUtil.leftPad(
					((Long) countMap.get("count")).intValue(), 4,
					'0');
		}
		return  noticeid;
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
			yyUtil.getSdvl(formDate);

		}
		if ("UAS".equals(ResourceUtil.getConfigByName("interfacetype"))) {
			String masterbill[] = {"XKN_TEST", "XKN_TEST"};
			for (int m = 0; m < masterbill.length; m++) {

				try {
					if (StringUtil.isEmpty(formDate)) {
						formDate = "2011-01-01";
					}
					String master = masterbill[m];
					String billclass[] = {"出货单", "采购退货单", "拨出单", "其它出库单", "其它采购出库单"};
					for (int i = 0; i < billclass.length; i++) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("lastUpdateTime", formDate);
						paramMap.put("pi_class", billclass[i]);
						paramMap.put("master", master);

						sdresult billResult = wmIntUtil.getsdBillin(paramMap);
						for (int s = 0; s < billResult.getData().size(); s++) {
							String imcuscode = billResult.getData().get(s).getPiInoutno();
							if (StringUtil.isNotEmpty(imcuscode)) {
								WmOmNoticeHEntity wmimh = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "imCusCode", imcuscode);
								if (wmimh == null) {
									WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
									List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();

									wmOmNoticeH.setOmPlatNo(Integer.toString(billResult.getData().get(s).getPiId()));
									wmOmNoticeH.setOrderTypeCode("11");
									wmOmNoticeH.setCusCode(ResourceUtil.getConfigByName("uas.cuscode"));
									String noticeid = getNextNoticeId(wmOmNoticeH.getOrderTypeCode());
									wmOmNoticeH.setOmNoticeId(noticeid);
									wmOmNoticeH.setPiClass(billResult.getData().get(s).getPiClass());
									wmOmNoticeH.setPiMaster(master);
									wmOmNoticeH.setOcusCode(billResult.getData().get(s).getPiCardcode());
									MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class, "keHuBianMa", wmOmNoticeH.getOcusCode());
									if (mdcusother != null) {
										wmOmNoticeH.setOcusName(mdcusother.getZhongWenQch());
									}
									wmOmNoticeH.setImCusCode(imcuscode);
									for (int k = 0; k < billResult.getData().get(s).getDetail().size(); k++) {
										WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
										wmi.setGoodsId(billResult.getData().get(s).getDetail().get(k).getPdProdcode());
										MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
												MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
										if (mvgoods != null) {
											wmi.setGoodsName(mvgoods.getGoodsName());
											wmi.setGoodsUnit(mvgoods.getShlDanWei());
										}
										wmi.setGoodsProData(DateUtils.str2Date(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate(), DateUtils.date_sdf));
										wmi.setGoodsQua(Integer.toString(billResult.getData().get(s).getDetail().get(k).getPdPurcoutqty()));
//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
										wmi.setOtherId(Integer.toString(billResult.getData().get(s).getDetail().get(k).getPdPdno()));

										wmomNoticeIListnew.add(wmi);
									}
									wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
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
		WmOmNoticeHEntity wmOmNoticeHEntity = wmOmNoticeHService.getEntity(WmOmNoticeHEntity.class, id);
		//获取参数
		Object id0 = wmOmNoticeHEntity.getOmNoticeId();
		//===================================================================================
		//查询-产品
		String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND omNoticeId = ? ";
		try{
			List<WmOmNoticeIEntity> wmOmNoticeIEntityList = systemService
					.findHql(hql0, id0);//获取行项目
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(WmOmNoticeIEntity t:wmOmNoticeIEntityList){
				Map<String,String> map = new HashMap<String,String>();
//				[{"pd_pdno":1,"pd_outqty":"100","pi_class":"出货单","pi_id":50765226,"pi_inoutno":"JRS180800008"}]
				map.put("pd_pdno",t.getOtherId());
				map.put("pd_outqty",t.getGoodsQua());
				map.put("pi_class",wmOmNoticeHEntity.getPiClass());
				map.put("pi_id",wmOmNoticeHEntity.getOmPlatNo());
				map.put("pi_inoutno",wmOmNoticeHEntity.getImCusCode());
				list.add(map);
			}
			String jsonStr = JSONArray.fromObject(list).toString();
			JSONArray ja = JSONArray.fromObject(jsonStr);
			resResult resResult = wmIntUtil.postBill(ja.toString(),wmOmNoticeHEntity.getPiMaster());
			j.setMsg(resResult.getDetailedMessage());
		}catch (Exception e){

		}

		j.setMsg(message);
		return j;
	}




	/**
	 * 打印出货通知
	 *
	 * @return
	 */


	@RequestMapping(params = "doPrint")
	@ResponseBody
	public void downReceiveExcel(WmOmNoticeHEntity wmOmNoticeH,HttpServletRequest request,HttpServletResponse response){
		OutputStream fileOut = null;
		BufferedImage bufferImg = null;
		String codedFileName = null;
		wmOmNoticeH = systemService.getEntity(WmOmNoticeHEntity.class,
				wmOmNoticeH.getId());//获取抬头
		String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND omNoticeId = ? ";
		List<WmOmNoticeIEntity> wmOmNoticeIEntityList = systemService
				.findHql(hql0, wmOmNoticeH.getOmNoticeId());//获取行项目
		//先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			StringBuffer sber=new StringBuffer();
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmOmNoticeH.getOmNoticeId()));
			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="+wmOmNoticeH.getOmNoticeId()+".xls");
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("出货通知");
			sheet.setColumnWidth(0, 5 * 256);
			sheet.setColumnWidth(1, 10 * 256);
			sheet.setColumnWidth(2, 10 * 200);
			sheet.setColumnWidth(3, 8 * 256);
			sheet.setColumnWidth(4, 8 * 256);
			sheet.setColumnWidth(5, 8 * 256);
			sheet.setColumnWidth(6, 8 * 256);
			sheet.setColumnWidth(7, 8 * 256);
			sheet.setColumnWidth(8, 25 * 256);
			//画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			//anchor主要用于设置图片的属性
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short)7, 1, (short)9, 3);
			anchor.setAnchorType(3);
			//插入图片
			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));



			// 创建第一行
			Row row = sheet.createRow((short) 0);   //第一行空白


			// 创建两种单元格格式
			CellStyle cs = wb.createCellStyle();
			CellStyle cs2 = wb.createCellStyle();
			CellStyle cs3 = wb.createCellStyle();
			CellStyle cs4 = wb.createCellStyle();
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

//	        Font f3=wb.createFont();  
//	        f3.setFontHeightInPoints((short) 10);  
//	        f3.setColor(IndexedColors.RED.getIndex());  

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

//	        cs2.setAlignment(CellStyle.BORDER_NONE); 


			cs3.setFont(f2);
			cs3.setBorderLeft(CellStyle.BORDER_MEDIUM);
			cs3.setBorderRight(CellStyle.BORDER_MEDIUM);
			cs3.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs3.setBorderBottom(CellStyle.BORDER_MEDIUM);
//	        cs3.setAlignment(CellStyle.BORDER_HAIR); 
			cs4.setFont(f2);
			cs4.setBorderTop(CellStyle.BORDER_MEDIUM);
			cs4.setBorderBottom(CellStyle.BORDER_MEDIUM);
			Row row1 = sheet.createRow((short) 1);   //第二行标题
			row1.setHeight((short)700);
			Cell cellTitle = row1.createCell(0);
			cellTitle.setCellValue(ResourceUtil.getConfigByName("comname")+"出货通知");
			cellTitle.setCellStyle(cs);


			Row rowHead1=sheet.createRow((short) 2);  //头部第一行
			Cell cellHead11 = rowHead1.createCell(0);
			cellHead11.setCellValue("通知单号："+wmOmNoticeH.getOmNoticeId());
			cellHead11.setCellStyle(cs2);


			Row rowHead2=sheet.createRow((short) 3);  //头部第二行
			Cell cellHead21 = rowHead2.createCell(0);
			try{
				MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmOmNoticeH.getCusCode());
				if(md!=null){
					cellHead21.setCellValue("客户："+wmOmNoticeH.getCusCode()+"/"+md.getZhongWenQch());
				}else{
					cellHead21.setCellValue("客户："+wmOmNoticeH.getCusCode());
				}
			}finally{

			}

			cellHead21.setCellStyle(cs2);

			Cell cellHead23 = rowHead2.createCell(5);
			cellHead23.setCellValue(" 计划出货时间："+wmOmNoticeH.getDelvData());
			cellHead23.setCellStyle(cs2);


			Row rowHead3=sheet.createRow((short) 4);  //头部第三行
			Cell cellHead31 = rowHead3.createCell(0);
			cellHead31.setCellValue("司机："+wmOmNoticeH.getReMember()+"  司机电话："+wmOmNoticeH.getReMobile());
			cellHead31.setCellStyle(cs2);


			Cell cellHead35 = rowHead3.createCell(5);
			cellHead35.setCellValue("车号："+wmOmNoticeH.getReCarno()+"  备注："+wmOmNoticeH.getOmBeizhu());
			cellHead35.setCellStyle(cs2);

			Row rowHead4=sheet.createRow((short) 5);  //头部第三行
			Cell cellHead41 = rowHead4.createCell(0);
			cellHead41.setCellValue("收货人："+wmOmNoticeH.getDelvMember()+"  电话："+wmOmNoticeH.getDelvMobile());
			cellHead31.setCellStyle(cs2);


			Cell cellHead45 = rowHead4.createCell(5);
			cellHead45.setCellValue("收货地址："+wmOmNoticeH.getDelvAddr());
			cellHead45.setCellStyle(cs2);

			//合并单元格
			CellRangeAddress   c = new CellRangeAddress(0, 0, 0, 8); //第一行空白
			CellRangeAddress   c0 = new CellRangeAddress(1, 1, 0, 8);//第二行标题
			CellRangeAddress   c1 = new CellRangeAddress(2, 2, 0, 8);//第三行通知单号
			CellRangeAddress   c2 = new CellRangeAddress(3, 3, 0, 4);//第四行客户
			CellRangeAddress   c3 = new CellRangeAddress(3, 3, 5, 8);//第四行客户送货时间
			CellRangeAddress   c4 = new CellRangeAddress(4, 4, 0, 4);//第五行客户
			CellRangeAddress   c5 = new CellRangeAddress(4, 4, 5, 8);//第五行客户送货时间
			CellRangeAddress   c6 = new CellRangeAddress(5, 5, 0, 4);//第五行客户
			CellRangeAddress   c7 = new CellRangeAddress(5, 5, 5, 8);//第五行客户送货时间
//	        CellRangeAddress   c4 = new CellRangeAddress(4, 4, 0, 1);
//	        CellRangeAddress   c5 = new CellRangeAddress(4, 4, 2, 3);
//	        CellRangeAddress   c6 = new CellRangeAddress(4, 4, 4, 5);
//	        CellRangeAddress   c7 = new CellRangeAddress(4, 4, 6, 6);

			sheet.addMergedRegion(c);
			sheet.addMergedRegion(c0);
			sheet.addMergedRegion(c1);
			sheet.addMergedRegion(c2);
			sheet.addMergedRegion(c3);
			sheet.addMergedRegion(c4);
			sheet.addMergedRegion(c5);
			sheet.addMergedRegion(c6);
			sheet.addMergedRegion(c7);


			Row rowColumnName=sheet.createRow((short) 6);  //列名
			String [] columnNames={"序号","商品编码","商品描述","数量","单位","生产日期","实收数量"," ","条码"};

			for(int i=0;i<columnNames.length;i++){
				Cell cell = rowColumnName.createCell(i);
				cell.setCellValue(columnNames[i]);
				cell.setCellStyle(cs3);
			}
			int cellsNum=6;
			int cerconNo=1;
			for(int i=0;i<wmOmNoticeIEntityList.size() ;i++){
				WmOmNoticeIEntity entity = wmOmNoticeIEntityList.get(i);
				cellsNum++;
				Row rowColumnValue=sheet.createRow((short) cellsNum);  //列名
				rowColumnValue.setHeight((short)1000);
				Cell cell1 = rowColumnValue.createCell(0);
				cell1.setCellValue(cerconNo);
				cell1.setCellStyle(cs3);
				Cell cell2 = rowColumnValue.createCell(1);
				cell2.setCellValue(entity.getGoodsId());
				cell2.setCellStyle(cs3);
				Cell cell3 = rowColumnValue.createCell(2);
				cell3.setCellStyle(cs3);
				Cell cell8 = rowColumnValue.createCell(7);
				cell8.setCellValue(entity.getBinOm());
				cell8.setCellStyle(cs4);
				Cell cell5 = rowColumnValue.createCell(4);

				cell5.setCellStyle(cs3);
				try{
					MvGoodsEntity goods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", entity.getGoodsId());
					if(goods!=null){
						cell3.setCellValue(goods.getGoodsName());
						cell5.setCellValue(goods.getShlDanWei());
					}
				}finally{

				}
				Cell cell4 = rowColumnValue.createCell(3);
				cell4.setCellValue(entity.getGoodsQua());
				cell4.setCellStyle(cs3);

				Cell cell6 = rowColumnValue.createCell(5);
				cell6.setCellValue(DateUtils.date2Str(entity.getGoodsProData(),DateUtils.date_sdf));
				cell6.setCellStyle(cs3);
				Cell cell7 = rowColumnValue.createCell(6);
//				           cell7.setCellValue(entity.getGoodsWeight());
				cell7.setCellStyle(cs3);
				Cell cell9 = rowColumnValue.createCell(8);
				cell9.setCellStyle(cs4);
				//插入图片

				byteArrayOut = new ByteArrayOutputStream();
				bufferImg = ImageIO.read(BarcodeUtil.generateToStream(entity.getGoodsId()));
				ImageIO.write(bufferImg, "jpg", byteArrayOut);
				if(cellsNum == 6){
					anchor = new HSSFClientAnchor(0, 0, 0, 0,(short)8, cellsNum, (short)9, cellsNum+1);
				}else{
					anchor = new HSSFClientAnchor(0, 0, 0, 0,(short)8, cellsNum, (short)9, cellsNum+1);
				}


				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));


				cerconNo++;
			}
			Row rowColumnInfo=sheet.createRow((short) 2+cellsNum);  //列名
			rowColumnInfo.createCell(0).setCellValue("注:烦请按时到"+ResourceUtil.getConfigByName("comaddr")+" 谢谢！");
			CellRangeAddress   c15 = new CellRangeAddress(10+cellsNum, 10+cellsNum, 0, 15);
			sheet.addMergedRegion(c15);
			fileOut = response.getOutputStream();
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fileOut != null){
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}



	@RequestMapping(params = "doPrintckd")
	@ResponseBody
	public void doPrintckd(WmOmNoticeHEntity wmOmNoticeH,
						   HttpServletRequest request, HttpServletResponse response) {
		OutputStream fileOut = null;
		BufferedImage bufferImg = null;
		String codedFileName = null;
		wmOmNoticeH = systemService.getEntity(WmOmNoticeHEntity.class,
				wmOmNoticeH.getId());//获取抬头


		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			StringBuffer sber = new StringBuffer();

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//			bufferImg = ImageIO.read(BarcodeUtil.generateToStream(wmImNoticeH
//					.getNoticeId()));
			bufferImg = QRcodeUtil.createImage(wmOmNoticeH
					.getOmNoticeId());

			// 进行转码，使其支持中文文件名
			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ wmOmNoticeH.getOmNoticeId() + ".xls");
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("出库单");
			sheet.setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）    
			sheet.setMargin(HSSFSheet.BottomMargin,1.5);// 页边距（下）    
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
//			String tsql = "SELECT wq.pro_data,wq.base_unit,wq.rec_deg, mg.goods_code, mg.goods_id,mg.shp_ming_cheng,cast(sum(wq.base_goodscount) as signed) as goods_count,cast(sum(wq.tin_tj) as signed) tin_tj ,cast(sum(wq.tin_zhl)  as signed) tin_zhl "
//					+" FROM wm_om_qm_i wq,mv_goods mg where wq.om_notice_id = ? "
//					+" and  wq.goods_id = mg.goods_code group by wq.om_notice_id, mg.goods_code,wq.pro_data";
			String tsql = "SELECT wq.goods_pro_data as pro_data,wq.base_unit, mg.goods_code, mg.goods_id,mg.shp_ming_cheng,cast(sum(wq.base_goodscount) as signed) as goods_count,mg.chl_shl,cast(mg.ti_ji_cm/mg.chl_shl as signed) tin_tj ,cast(mg.zhl_kg/mg.chl_shl  as signed) tin_zhl  "
					+" FROM wm_to_down_goods wq,mv_goods mg where wq.order_id =  ? "
					+" and  wq.goods_id = mg.goods_code group by wq.order_id, mg.goods_code,wq.goods_pro_data";

			List<Map<String, Object>> result = systemService
					.findForJdbc(tsql, wmOmNoticeH.getOmNoticeId());


			int size = result.size();
			if(size<1){
				tsql = "SELECT wq.pro_data,wq.base_unit, mg.goods_code, mg.goods_id,mg.shp_ming_cheng,cast(sum(wq.base_goodscount) as signed) as goods_count,mg.chl_shl,cast(mg.ti_ji_cm/mg.chl_shl as signed) tin_tj ,cast(mg.zhl_kg/mg.chl_shl  as signed) tin_zhl "
						+" FROM wm_om_qm_i wq,mv_goods mg where wq.om_notice_id = ? "
						+" and  wq.goods_id = mg.goods_code group by wq.om_notice_id, mg.goods_code,wq.pro_data";
				result = systemService
						.findForJdbc(tsql, wmOmNoticeH.getOmNoticeId());
				size = result.size();
			}
			int pagesize = 10;
			int pagecount = size%pagesize==0?size/pagesize:size/pagesize+1;
			long sum = 0;
			long sumxs = 0;
			long sumzl = 0;
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
				cellTitle.setCellValue(ResourceUtil.getConfigByName("comname")+"出库单");
				cellTitle.setCellStyle(cs);

				Row rowHead1 = sheet.createRow((short) page*20+2); // 头部第一行
				Cell cellHead1 = rowHead1.createCell(0);
				cellHead1.setCellValue("公司地址："+ResourceUtil.getConfigByName("comaddr") );
				cellHead1.setCellStyle(cs1);

				Row rowHead2 = sheet.createRow((short) page*20+3); // 头部第二行
				Cell cellHead2 = rowHead2.createCell(0);
				cellHead2.setCellValue("电话："+ ResourceUtil.getConfigByName("comtel"));
				cellHead2.setCellStyle(cs1);


				Row rowHead4 = sheet.createRow((short) page*20+4); // 头部第二行
				Cell cellHead4 = rowHead4.createCell(0);
				cellHead4.setCellValue("出库日期： " +DateUtils.date2Str(wmOmNoticeH.getDelvData(), DateUtils.date_sdf) );
				cellHead4.setCellStyle(cs2);

				Cell cellHead42 = rowHead4.createCell(3);
				cellHead42.setCellValue("出库单号： " +wmOmNoticeH.getOmNoticeId());
				cellHead42.setCellStyle(cs2);

				Row rowHead5 = sheet.createRow((short) page*20+5); // 头部第二行
				Cell cellHead5 = rowHead5.createCell(0);
				cellHead5.setCellValue("客户单号： " );
				cellHead5.setCellStyle(cs2);

				Cell cellHead52 = rowHead5.createCell(3);
				cellHead52.setCellValue("车号： " +wmOmNoticeH.getReCarno());
				cellHead52.setCellStyle(cs2);

				Row rowHead6 = sheet.createRow((short) page*20+6); // 头部第二行
				Cell cellHead6 = rowHead6.createCell(0);
				MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmOmNoticeH.getCusCode());

				cellHead6.setCellValue("客户名称： " +wmOmNoticeH.getCusCode()+md.getZhongWenQch());
				cellHead6.setCellStyle(cs2);

				Cell cellHead62 = rowHead6.createCell(3);
				cellHead62.setCellValue("收货人： "+wmOmNoticeH.getDelvMember()+"   电话:"+wmOmNoticeH.getDelvMobile() );
				cellHead62.setCellStyle(cs2);

				Row rowHead7 = sheet.createRow((short) page*20+7); // 头部第二行
				Cell cellHead7 = rowHead7.createCell(0);
				cellHead7.setCellValue("收货地址： " +wmOmNoticeH.getDelvAddr());
				cellHead7.setCellStyle(cs2);

				Cell cellHead72 = rowHead7.createCell(5);
				cellHead72.setCellValue("打印时间： "+DateUtils.date2Str(DateUtils.getDate(), DateUtils.datetimeFormat) +"   第"+(page+1)+"页");
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
				CellRangeAddress c7 = new CellRangeAddress(page*20+7, page*20+7, 0, 4);//第7行客户电话
				CellRangeAddress c72 = new CellRangeAddress(page*20+7, page*20+7, 5, 9);//第7行打印时间
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
				cell73.setCellValue("① 财务联 ② 客户联 ③司机联 ④回单联                                   ");
				cell73.setCellStyle(cs52);


				CellRangeAddress c73 = new CellRangeAddress(page*20, page*20+19, 10, 10);//第7行打印时间
				sheet.addMergedRegion(c73);

				Row rowColumnName = sheet.createRow((short) page*20+8); // 列名
				String[] columnNames = { "序号", "商品编码", "商品名称", "生产日期", "品质","箱数", "拆零数", "毛重/KG","体积/cm³","备注" };

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
				for (int i = page*pagesize; i < (page+1)*pagesize+oversize; i++) {
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
							cell4.setCellValue(result.get(i).get("pro_data")
									.toString());
							cell4.setCellStyle(cs5);
						} catch (Exception e) {
							// TODO: handle exception

						}

						try {
							Cell cell5 = rowColumnValue.createCell(4);// 品质
							cell5.setCellValue("");
							cell5.setCellStyle(cs5);
						} catch (Exception e) {
							// TODO: handle exception
						}

						try {

							long  xs = (long) Math.floor(Long.parseLong(result.get(i).get("goods_count")
									.toString()) / Long.parseLong(result.get(i).get("chl_shl")
									.toString()));
							sumxs = sumxs  + xs;
							Cell cell6 = rowColumnValue.createCell(5);// 单位
							cell6.setCellValue(xs);
							cell6.setCellStyle(cs5);
						} catch (Exception e) {
							// TODO: handle exception
						}

						try {
							long bs =						  Long.parseLong(result.get(i).get("goods_count")
									.toString()) % Long.parseLong(result.get(i).get("chl_shl")
									.toString());
							sum = sum + bs;
							Cell cell7 = rowColumnValue.createCell(6);// 数量
							cell7.setCellValue(bs);
							cell7.setCellStyle(cs5);
						} catch (Exception e) {
							// TODO: handle exception
						}
						Cell cell8 = rowColumnValue.createCell(7);// 毛重
						try {

							long zhl = Long.parseLong(result.get(i).get("tin_zhl")
									.toString()) * Long.parseLong(result.get(i).get("goods_count").toString());
							sumzl = sumzl + zhl;
							cell8.setCellValue(zhl);

						} catch (Exception e) {
							// TODO: handle exception
						}
						cell8.setCellStyle(cs5);
						Cell cell9 = rowColumnValue.createCell(8);// 体积
						try {
							long tij = Long.parseLong(result.get(i).get("tin_tj")
									.toString()) * Long.parseLong(result.get(i).get("goods_count").toString());


							cell9.setCellValue(tij);

						} catch (Exception e) {
							// TODO: handle exception
						}
						cell9.setCellStyle(cs5);

						Cell cell10 = rowColumnValue.createCell(9);// 备注
						cell10.setCellStyle(cs5);

						cerconNo++;
					}
					if(i== size){

						cellsNum++;
						Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
						rowColumnValue.setHeight((short) 250);
						Cell cell5 = rowColumnValue.createCell(5);// 备注
						cell5.setCellValue(Long.toString(sumxs));
						Cell cell6 = rowColumnValue.createCell(6);// 备注
						cell6.setCellValue(Long.toString(sum));
						Cell cell7 = rowColumnValue.createCell(7);// 重量合计
						cell7.setCellValue(Long.toString(sumzl));
//				cell6.setCellStyle(cs5);
						Cell cell0 = rowColumnValue.createCell(0);// 合计
						cell0.setCellValue("合计：");
//				cell0.setCellStyle(cs5);
						CellRangeAddress c15 = new CellRangeAddress( cellsNum,
								cellsNum, 0, 4);
						sheet.addMergedRegion(c15);
						cerconNo++;

					}


				}
				Row rowColumnInfo = sheet.createRow((short) 1 + cellsNum); // 列名
				rowColumnInfo.setHeight((short) 250);
				rowColumnInfo.createCell(0).setCellValue(
						"  发货人员：                               配送司机：                               收货人员：	");
				CellRangeAddress c15 = new CellRangeAddress(1 + cellsNum,
						1 + cellsNum, 0, 9);
				sheet.addMergedRegion(c15);

				Row rowColumnInfo2 = sheet.createRow((short) 2 + cellsNum); // 列名
				rowColumnInfo2.setHeight((short) 250);
				rowColumnInfo2.createCell(0).setCellValue(
						"  发货时间：                               收货时间：                               收货单位盖章：	");
				CellRangeAddress c152 = new CellRangeAddress(2 + cellsNum,
						2 + cellsNum, 0, 9);
				sheet.addMergedRegion(c152);
				page++;
			} while (page<pagecount);
			fileOut = response.getOutputStream();
			HSSFPrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setPaperSize(HSSFPrintSetup.A5_PAPERSIZE);

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
	 * 更新出货通知
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmOmNoticeHEntity wmOmNoticeH,WmOmNoticeHPage wmOmNoticeHPage, HttpServletRequest request) {
		List<WmOmNoticeIEntity> wmOmNoticeIList =  wmOmNoticeHPage.getWmOmNoticeIList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{

			if(wmOmNoticeH.getCusCode()==null){
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
						wmOmNoticeH.setCusCode(user.getUserName());

					}
				}
			}


			wmOmNoticeHService.updateMain(wmOmNoticeH, wmOmNoticeIList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新出货通知失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 出货通知新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmOmNoticeHEntity wmOmNoticeH, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmOmNoticeH.getId())) {
			wmOmNoticeH = wmOmNoticeHService.getEntity(WmOmNoticeHEntity.class, wmOmNoticeH.getId());
			req.setAttribute("wmOmNoticeHPage", wmOmNoticeH);
		}else{
			wmOmNoticeH.setOrderTypeCode(req.getParameter("orderTypeCode").toString());

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
					wmOmNoticeH.setCusCode(user.getUserName());
					wmOmNoticeH.setReadonly("readonly");
					wmOmNoticeH.setWherecon("where cus_code = '"+user.getUserName()+"'");
					req.setAttribute("wmOmNoticeHPage", wmOmNoticeH);

				}else{
					if(!StringUtil.isEmpty( wmOmNoticeH.getCusCode())){
						wmOmNoticeH.setWherecon("where cus_code = '"+wmOmNoticeH.getCusCode()+"'");
					}else{
						wmOmNoticeH.setWherecon("where 1 = 1");
					}
					req.setAttribute("wmOmNoticeHPage", wmOmNoticeH);
				}
			}

		}
		return new ModelAndView("com/zzjee/wm/wmOmNoticeH-add");
	}

	/**
	 * 出货通知编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmOmNoticeHEntity wmOmNoticeH, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmOmNoticeH.getId())) {
			wmOmNoticeH = wmOmNoticeHService.getEntity(WmOmNoticeHEntity.class, wmOmNoticeH.getId());
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
					wmOmNoticeH.setCusCode(user.getUserName());
					wmOmNoticeH.setReadonly("readonly");
					wmOmNoticeH.setWherecon("where cus_code = '"+user.getUserName()+"'");
				}
			}
			req.setAttribute("wmOmNoticeHPage", wmOmNoticeH);
		}
		return new ModelAndView("com/zzjee/wm/wmOmNoticeH-update");
	}


	/**
	 * 加载明细列表[出货商品明细]
	 *
	 * @return
	 */
	@RequestMapping(params = "wmOmNoticeIList")
	public ModelAndView wmOmNoticeIList(WmOmNoticeHEntity wmOmNoticeH, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = wmOmNoticeH.getOmNoticeId();
		//===================================================================================
		//查询-出货商品明细
		String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND oM_NOTICE_ID = ? ";
		try{
			if(StringUtil.isNotEmpty(id0)){
				List<WmOmNoticeIEntity> wmOmNoticeIEntityList = systemService.findHql(hql0,id0);
				req.setAttribute("wmOmNoticeIList", wmOmNoticeIEntityList);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/zzjee/wm/wmOmNoticeIList");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmOmNoticeHEntity wmOmNoticeH,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
		CriteriaQuery cq = new CriteriaQuery(WmOmNoticeHEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmNoticeH);
		try{

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

			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		List<WmOmNoticeHEntity> list=this.wmOmNoticeHService.getListByCriteriaQuery(cq, false);
		List<WmOmNoticeHPage> pageList=new ArrayList<WmOmNoticeHPage>();
		if(list!=null&&list.size()>0){
			for(WmOmNoticeHEntity entity:list){
				try{
					WmOmNoticeHPage page=new WmOmNoticeHPage();
					MyBeanUtils.copyBeanNotNull2Bean(entity,page);
					Object id0 = entity.getOmNoticeId();
					String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND oM_NOTICE_ID = ? ";
					List<WmOmNoticeIEntity> wmOmNoticeIEntityList = systemService.findHql(hql0,id0);
					page.setWmOmNoticeIList(wmOmNoticeIEntityList);
					pageList.add(page);
				}catch(Exception e){
					logger.info(e.getMessage());
				}
			}
		}
		map.put(NormalExcelConstants.FILE_NAME,"出货通知");
		map.put(NormalExcelConstants.CLASS,WmOmNoticeHPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("出货通知列表", "导出人:admin",
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
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<WmNoticeImpPage> list =  ExcelImportUtil.importExcel(file.getInputStream(), WmNoticeImpPage.class, params);

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
				for(int i=0;i<listheader.size()-1;i++){
					for(int  k=listheader.size()-1;k>i;k--){
						if(listheader.get(k).getImCusCode().equals(listheader.get(i).getImCusCode()))  {
							listheader.remove(k);
						}
					}
				}
				for(WmNoticeImpPage pageheader: listheader) {
					List<WmOmNoticeHEntity>  wmomh = systemService.findByProperty(WmOmNoticeHEntity.class, "imCusCode", pageheader.getImCusCode());
					if(wmomh!=null&&wmomh.size()>0){
						continue;
					}

					List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
					for (WmNoticeImpPage page : list) {
						if(pageheader.getImCusCode().equals(page.getImCusCode())) {
							WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
							wmi.setGoodsId(page.getGoodsId());
							MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
									MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
							if (mvgoods != null) {
								wmi.setGoodsName(mvgoods.getGoodsName());
								wmi.setGoodsUnit(mvgoods.getShlDanWei());
							}
							try{
								wmi.setGoodsQua(page.getGoodsQua());
								String[] args=page.getGoodsQua().split("\\.");
								wmi.setGoodsQua(args[0]);
							}catch (Exception e){

							}

//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
							wmi.setOtherId(page.getOtherId());
							wmi.setBinId(page.getBinId());
							wmi.setBinOm(page.getBinOm());
							if(StringUtil.isNotEmpty(page.getGoodsProData())){
								wmi.setGoodsProData(DateUtils.str2Date(page.getGoodsProData(),DateUtils.date_sdf));
							}
							wmomNoticeIListnew.add(wmi);
						}
					}
					WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();

					wmOmNoticeH.setDelvData(pageheader.getImData());
					wmOmNoticeH.setOrderTypeCode(pageheader.getOrderTypeCode());
					wmOmNoticeH.setCusCode(pageheader.getCusCode());
					String noticeid = getNextNoticeId(wmOmNoticeH.getOrderTypeCode());
					wmOmNoticeH.setOmNoticeId(noticeid);
					wmOmNoticeH.setOmBeizhu(pageheader.getImBeizhu());
					wmOmNoticeH.setOcusCode(pageheader.getSupCode());
					wmOmNoticeH.setDelvAddr(pageheader.getDelvAddr());
					MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class, "keHuBianMa", wmOmNoticeH.getOcusCode());
					if (mdcusother != null) {
						wmOmNoticeH.setOcusName(mdcusother.getZhongWenQch());
					}
					wmOmNoticeH.setImCusCode(pageheader.getImCusCode());
					wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
				}
				//
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
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(params = "importExcel2", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel2(HttpServletRequest request, HttpServletResponse response) {
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
				List<WmOmNoticeImpPage> listfirst =  ExcelImportUtil.importExcel(file.getInputStream(), WmOmNoticeImpPage.class, params);
				List<WmNoticeImpPage> list =  new ArrayList<WmNoticeImpPage>();
				List<WmNoticeImpPage> listheader = new ArrayList<WmNoticeImpPage>();
				for(WmOmNoticeImpPage t:listfirst){
					WmNoticeImpPage wmNoticeImpPage = new WmNoticeImpPage();
					wmNoticeImpPage.setOrderTypeCode("11");
					wmNoticeImpPage.setImCusCode(t.getImCusCode());//三方单号
					wmNoticeImpPage.setImBeizhu(t.getImBeizhu());//备注
					try{
						wmNoticeImpPage.setGoodsId(wmUtil.getGoodsId(t.getCusCode(),t.getGoodsId(),t.getGoodsUnit()).get("goodsCode"));
//						wmNoticeImpPage.setCusCode(wmUtil.getGoodsId(t.getGoodsId(),t.getGoodsUnit()).get("cusCode"));
					}catch (Exception e){

					}
					wmNoticeImpPage.setGoodsName(t.getGoodsName());//商品名称
					wmNoticeImpPage.setGoodsQua(t.getGoodsQua());//数量
					wmNoticeImpPage.setGoodsUnit(t.getGoodsUnit());//单位
					list.add(wmNoticeImpPage);
					listheader.add(wmNoticeImpPage);
				}
//第二类简单模板

				String flag = "Y";
				String message="";
				if(!"yes".equals(ResourceUtil.getConfigByName("nocheck"))){
					for(WmNoticeImpPage wmt:list){
						MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
								MvGoodsEntity.class, "goodsCode", wmt.getGoodsId());
						if(mvgoods==null){
							flag = "N";
							message=message+wmt.getGoodsName();
						}
					}
					if("N".equals(flag)){
						j.setMsg(message+"不存在");
						return j;
					}

				}

				for(int i=0;i<listheader.size()-1;i++){
					for(int  k=listheader.size()-1;k>i;k--){
						if(listheader.get(k).getImCusCode().equals(listheader.get(i).getImCusCode()))  {
							listheader.remove(k);
						}
					}
				}
				for(WmNoticeImpPage pageheader: listheader) {
					List<WmOmNoticeHEntity>  wmomh = systemService.findByProperty(WmOmNoticeHEntity.class, "imCusCode", pageheader.getImCusCode());
					if(wmomh!=null&&wmomh.size()>0){
						continue;
					}

					List<WmOmNoticeIEntity> wmomNoticeIListnew = new ArrayList<WmOmNoticeIEntity>();
					for (WmNoticeImpPage page : list) {
						if(pageheader.getImCusCode().equals(page.getImCusCode())) {
							WmOmNoticeIEntity wmi = new WmOmNoticeIEntity();
							wmi.setGoodsId(page.getGoodsId());
							MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
									MvGoodsEntity.class, "goodsCode", wmi.getGoodsId());
							if (mvgoods != null) {
								wmi.setGoodsName(mvgoods.getGoodsName());
								wmi.setGoodsUnit(mvgoods.getShlDanWei());
							}
							try{
								wmi.setGoodsQua(page.getGoodsQua());
								String[] args=page.getGoodsQua().split("\\.");
								wmi.setGoodsQua(args[0]);
							}catch (Exception e){

							}

//                               wmi.setGoodsPrdData(billResult.getData().get(s).getDetail().get(k).getPdProdmadedate2User());
							wmi.setOtherId(page.getOtherId());
							wmi.setBinOm(page.getBinOm());
							wmi.setBinId(page.getBinId());
							if(StringUtil.isNotEmpty(page.getGoodsProData())){
								wmi.setGoodsProData(DateUtils.str2Date(page.getGoodsProData(),DateUtils.date_sdf));
							}
							wmomNoticeIListnew.add(wmi);
						}
					}
					WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();

					wmOmNoticeH.setDelvData(pageheader.getImData());
					wmOmNoticeH.setOrderTypeCode(pageheader.getOrderTypeCode());
					wmOmNoticeH.setCusCode(pageheader.getCusCode());
					String noticeid = getNextNoticeId(wmOmNoticeH.getOrderTypeCode());
					wmOmNoticeH.setOmNoticeId(noticeid);
					wmOmNoticeH.setOmBeizhu(pageheader.getImBeizhu());
					wmOmNoticeH.setOcusCode(pageheader.getSupCode());
					wmOmNoticeH.setDelvAddr(pageheader.getDelvAddr());
					MdCusOtherEntity mdcusother = systemService.findUniqueByProperty(MdCusOtherEntity.class, "keHuBianMa", wmOmNoticeH.getOcusCode());
					if (mdcusother != null) {
						wmOmNoticeH.setOcusName(mdcusother.getZhongWenQch());
					}
					wmOmNoticeH.setImCusCode(pageheader.getImCusCode());
					wmOmNoticeHService.addMain(wmOmNoticeH, wmomNoticeIListnew);
				}
				//
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
		map.put(NormalExcelConstants.FILE_NAME,"出货通知");
		map.put(NormalExcelConstants.CLASS,WmNoticeImpPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("出货通知", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 */
	@RequestMapping(params = "exportXlsByT2")
	public String exportXlsByT2(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"出货通知");
		map.put(NormalExcelConstants.CLASS,WmOmNoticeImpPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("出货通知", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
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
		req.setAttribute("controller_name", "wmOmNoticeHController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload2")
	public ModelAndView upload2(HttpServletRequest req) {
		req.setAttribute("controller_name", "wmOmNoticeHController");
		return new ModelAndView("common/upload/pub_excel_upload2");
	}






	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<WmOmNoticeHEntity> list() {
		List<WmOmNoticeHEntity> listWmOmNoticeHs=wmOmNoticeHService.getList(WmOmNoticeHEntity.class);
		return listWmOmNoticeHs;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmOmNoticeHEntity task = wmOmNoticeHService.get(WmOmNoticeHEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmOmNoticeHPage wmOmNoticeHPage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		ResultDO D0 = new  ResultDO();
		Set<ConstraintViolation<WmOmNoticeHPage>> failures = validator.validate(wmOmNoticeHPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<WmOmNoticeIEntity> wmOmNoticeIList =  wmOmNoticeHPage.getWmOmNoticeIList();

		WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(wmOmNoticeH,wmOmNoticeHPage);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		wmOmNoticeHService.addMain(wmOmNoticeH, wmOmNoticeIList);
		D0.setOK(true);
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		return new ResponseEntity(D0, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmOmNoticeHPage wmOmNoticeHPage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmOmNoticeHPage>> failures = validator.validate(wmOmNoticeHPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<WmOmNoticeIEntity> wmOmNoticeIList =  wmOmNoticeHPage.getWmOmNoticeIList();

		WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(wmOmNoticeH,wmOmNoticeHPage);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		wmOmNoticeHService.updateMain(wmOmNoticeH, wmOmNoticeIList);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		WmOmNoticeHEntity wmOmNoticeH = wmOmNoticeHService.get(WmOmNoticeHEntity.class, id);
		wmOmNoticeHService.delMain(wmOmNoticeH);
	}
}
