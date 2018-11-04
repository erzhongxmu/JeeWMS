package com.zzjee.wm.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.wm.entity.WmSttInGoodsEntity;
import com.zzjee.wm.entity.WmToMoveGoodsEntity;
import com.zzjee.wm.entity.WmToUpGoodsEntity;
import com.zzjee.wm.entity.WvStockEntity;
import com.zzjee.wm.service.WvStockServiceI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmutil.wmUtil;
import org.apache.log4j.Logger;
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

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.jeecgframework.core.util.ExceptionUtil;
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
 * @Description: 生成盘点单
 * @author erzhongxmu
 * @date 2017-09-08 22:26:27
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/wvStockController")
public class WvStockController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(WvStockController.class);

	@Autowired
	private WvStockServiceI wvStockService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 生成盘点单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wvStockList");
	}
	@RequestMapping(params = "movelist")
	public ModelAndView movelist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wvStockmoveList");
	}
	@RequestMapping(params = "tpmovelist")
	public ModelAndView tpmovelist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wvStocktmoveList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridkczy")
	public void datagridkczy(WvStockEntity wvStock, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvStockEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wvStock, request.getParameterMap());
		try {
			// 自定义追加查询条件
			String query_goodsProData_begin = request
					.getParameter("goodsProData_begin");
			String query_goodsProData_end = request
					.getParameter("goodsProData_end");
			if (StringUtil.isNotEmpty(query_goodsProData_begin)) {
				cq.ge("goodsProData",
						Integer.parseInt(query_goodsProData_begin));
			}
			if (StringUtil.isNotEmpty(query_goodsProData_end)) {
				cq.le("goodsProData", Integer.parseInt(query_goodsProData_end));
			}

			String query_createData_begin = request
					.getParameter("lastMove_begin1");
			String query_createData_end = request.getParameter("lastMove_end2");
			if (StringUtil.isNotEmpty(query_createData_begin)) {
				cq.ge("lastMove", DateUtils.str2Date(query_createData_begin,
						DateUtils.datetimeFormat));
			}
			if (StringUtil.isNotEmpty(query_createData_end)) {
				cq.le("lastMove", DateUtils.str2Date(query_createData_end,
						DateUtils.datetimeFormat));
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("id", "desc");  
		cq.setOrder(map1);
		cq.eq("kuctype", "库存");
		// cq.ge("goodsQua", 1);
		cq.add();
		this.wvStockService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagrid")
	public void datagrid(WvStockEntity wvStock, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WvStockEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wvStock, request.getParameterMap());
		try {
			// 自定义追加查询条件
			String query_goodsProData_begin = request
					.getParameter("goodsProData_begin");
			String query_goodsProData_end = request
					.getParameter("goodsProData_end");
			if (StringUtil.isNotEmpty(query_goodsProData_begin)) {
				cq.ge("goodsProData",
						Integer.parseInt(query_goodsProData_begin));
			}
			if (StringUtil.isNotEmpty(query_goodsProData_end)) {
				cq.le("goodsProData", Integer.parseInt(query_goodsProData_end));
			}

			String query_createData_begin = request
					.getParameter("lastMove_begin1");
			String query_createData_end = request.getParameter("lastMove_end2");
			if (StringUtil.isNotEmpty(query_createData_begin)) {
				cq.ge("lastMove", DateUtils.str2Date(query_createData_begin,
						DateUtils.datetimeFormat));
			}
			if (StringUtil.isNotEmpty(query_createData_end)) {
				cq.le("lastMove", DateUtils.str2Date(query_createData_end,
						DateUtils.datetimeFormat));
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("id", "desc");  
		cq.setOrder(map1); 
		cq.eq("kuctype", "库存");
		// cq.ge("goodsQua", 1);
		cq.add();
		this.wvStockService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除生成盘点单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WvStockEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wvStock = systemService.getEntity(WvStockEntity.class, wvStock.getId());
		message = "生成盘点单删除成功";
		try {
			wvStockService.delete(wvStock);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成盘点单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除生成盘点单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "盘点单删除成功";
		try {
			for (String id : ids.split(",")) {
				WvStockEntity wvStock = systemService.getEntity(
						WvStockEntity.class, id);
				wvStockService.delete(wvStock);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "盘点单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加生成盘点单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WvStockEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "盘点单添加成功";
		try {
			wvStockService.save(wvStock);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "盘点单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doBatchStt")
	@ResponseBody
	public AjaxJson doBatchStt(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成盘点单成功";
		String sttId = "";
		sttId = DateUtils.date2Str(DateUtils.time_sdf);
		try {
			for (String id : ids.split(",")) {

				WvStockEntity t = wvStockService.get(WvStockEntity.class, id);
				try {
					WmSttInGoodsEntity wmstt = new WmSttInGoodsEntity();
					wmstt.setSttId(sttId);
					wmstt.setBinId(t.getKuWeiBianMa());
					wmstt.setCusCode(t.getCusCode());
					wmstt.setCusName(t.getZhongWenQch());
					wmstt.setGoodsId(t.getGoodsId());
					wmstt.setGoodsName(t.getShpMingCheng());
					wmstt.setGoodsProData(t.getGoodsProData());
					wmstt.setGoodsQua(t.getGoodsQua().toString());
					wmstt.setGoodsUnit(t.getGoodsUnit());
					wmstt.setGoodsBatch(t.getGoodsProData());
					wmstt.setSttType(request
							.getParameter("stttype").toString());
					wmstt.setSttSta("计划中");
					wmstt.setTinId(t.getBinId());
					systemService.save(wmstt);
					systemService.addLog(message, Globals.Log_Type_UPDATE,
							Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "生成盘点单失败";
					throw new BusinessException(e.getMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成盘点单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "dostt")
	@ResponseBody
	public AjaxJson doStt(WvStockEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成盘点单成功";
		WvStockEntity t = wvStockService.get(WvStockEntity.class, request
				.getParameter("id").toString());
		String sttId = "";
		sttId = DateUtils.date2Str(DateUtils.time_sdf);
		try {
			WmSttInGoodsEntity wmstt = new WmSttInGoodsEntity();
			wmstt.setSttId(sttId);
			wmstt.setBinId(t.getKuWeiBianMa());
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
			wmstt.setTinId(t.getBinId());
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

	
	@RequestMapping(params = "doBatchSttkczy")
	@ResponseBody
	public AjaxJson doBatchSttkczy(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成库存转移单成功";
		try {
			for (String id : ids.split(",")) {

				WvStockEntity t = wvStockService.get(WvStockEntity.class, id);
				try {
					WmToMoveGoodsEntity wmtomove = new WmToMoveGoodsEntity();
					wmtomove.setOrderTypeCode("KCZY");
					wmtomove.setBinFrom(t.getKuWeiBianMa());
					wmtomove.setCusCode(t.getCusCode());
					wmtomove.setCusName(t.getZhongWenQch());
					wmtomove.setToCusCode(t.getCusCode());
					wmtomove.setToCusName(t.getZhongWenQch());
					wmtomove.setGoodsId(t.getGoodsId());
					wmtomove.setGoodsName(t.getShpMingCheng());
					wmtomove.setGoodsProData(t.getGoodsProData());
					wmtomove.setGoodsQua(t.getGoodsQua().toString());
					wmtomove.setGoodsUnit(t.getGoodsUnit());
					wmtomove.setBaseGoodscount(t.getGoodsQua().toString());
					wmtomove.setBaseUnit(t.getGoodsUnit());
					wmtomove.setMoveSta("计划中");
					wmtomove.setRunSta("计划中");
					wmtomove.setTinFrom(t.getBinId());
					wmtomove.setTinId(t.getBinId());
					systemService.save(wmtomove);
					systemService.addLog(message, Globals.Log_Type_UPDATE,
							Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "生成库存转移单失败";
					throw new BusinessException(e.getMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成库存转移单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "dosttkczy")
	@ResponseBody
	public AjaxJson doSttkczy(WvStockEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成库存转移单成功";
		WvStockEntity t = wvStockService.get(WvStockEntity.class, request
				.getParameter("id").toString());
		try {
			WmToMoveGoodsEntity wmtomove = new WmToMoveGoodsEntity();
			wmtomove.setOrderTypeCode("KCZY");
			wmtomove.setBinFrom(t.getKuWeiBianMa());
			wmtomove.setCusCode(t.getCusCode());
			wmtomove.setCusName(t.getZhongWenQch());
			wmtomove.setToCusCode(t.getCusCode());
			wmtomove.setToCusName(t.getZhongWenQch());
			wmtomove.setGoodsId(t.getGoodsId());
			wmtomove.setGoodsName(t.getShpMingCheng());
			wmtomove.setGoodsProData(t.getGoodsProData());
			wmtomove.setGoodsQua(t.getGoodsQua().toString());
			wmtomove.setGoodsUnit(t.getGoodsUnit());
			wmtomove.setBaseGoodscount(t.getGoodsQua().toString());
			wmtomove.setBaseUnit(t.getGoodsUnit());
			wmtomove.setMoveSta("计划中");
			wmtomove.setRunSta("计划中");
			wmtomove.setTinFrom(t.getBinId());
			wmtomove.setTinId(t.getBinId());
			systemService.save(wmtomove);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成库存转移单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "doBatchStttpzy")
	@ResponseBody
	public AjaxJson doBatchStttpzy(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String moveTinid = request.getParameter("moveTinid");
		message = "生成托盘转移单成功";
		try {
			for (String id : ids.split(",")) {

				WvStockEntity t = wvStockService.get(WvStockEntity.class, id);
				try {
					WmToMoveGoodsEntity wmtomove = new WmToMoveGoodsEntity();
					wmtomove.setOrderTypeCode("TPZY");
					wmtomove.setBinFrom(t.getKuWeiBianMa());
					wmtomove.setBinTo(t.getKuWeiBianMa());
					wmtomove.setCusCode(t.getCusCode());
					wmtomove.setCusName(t.getZhongWenQch());
					wmtomove.setToCusCode(t.getCusCode());
					wmtomove.setToCusName(t.getZhongWenQch());
					wmtomove.setGoodsId(t.getGoodsId());
					wmtomove.setGoodsName(t.getShpMingCheng());
					wmtomove.setGoodsProData(t.getGoodsProData());
					wmtomove.setGoodsQua(t.getGoodsQua().toString());
					wmtomove.setGoodsUnit(t.getGoodsUnit());
					wmtomove.setBaseGoodscount(t.getGoodsQua().toString());
					wmtomove.setBaseUnit(t.getGoodsUnit());
					if(StringUtil.isEmpty(moveTinid)){
						wmtomove.setMoveSta("计划中");
					}else{
						wmtomove.setMoveSta("已完成");
						wmtomove.setTinId(moveTinid);
					}
					wmtomove.setTinFrom(t.getBinId());
					wmtomove.setRunSta("计划中");
					systemService.save(wmtomove);
					systemService.addLog(message, Globals.Log_Type_UPDATE,
							Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "生成托盘转移单失败";
					throw new BusinessException(e.getMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成托盘转移单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "dostttpzy")
	@ResponseBody
	public AjaxJson doStttpzy(WvStockEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成托盘转移单成功";
		WvStockEntity t = wvStockService.get(WvStockEntity.class, request
				.getParameter("id").toString());
		try {
			WmToMoveGoodsEntity wmtomove = new WmToMoveGoodsEntity();
			wmtomove.setOrderTypeCode("TPZY");
			wmtomove.setBinFrom(t.getKuWeiBianMa());
			wmtomove.setBinTo(t.getKuWeiBianMa());
			wmtomove.setCusCode(t.getCusCode());
			wmtomove.setCusName(t.getZhongWenQch());
			wmtomove.setToCusCode(t.getCusCode());
			wmtomove.setToCusName(t.getZhongWenQch());
			wmtomove.setGoodsId(t.getGoodsId());
			wmtomove.setGoodsName(t.getShpMingCheng());
			wmtomove.setGoodsProData(t.getGoodsProData());
			wmtomove.setGoodsQua(t.getGoodsQua().toString());
			wmtomove.setGoodsUnit(t.getGoodsUnit());
			wmtomove.setBaseGoodscount(t.getGoodsQua().toString());
			wmtomove.setBaseUnit(t.getGoodsUnit());
			wmtomove.setMoveSta("计划中");
			wmtomove.setTinFrom(t.getBinId());
			wmtomove.setRunSta("计划中");

			systemService.save(wmtomove);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成托盘转移单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	
	
	
	/**
	 * 更新生成盘点单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WvStockEntity wvStock, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "生成盘点单更新成功";
		WvStockEntity t = wvStockService.get(WvStockEntity.class,
				wvStock.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wvStock, t);
			wvStockService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生成盘点单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 生成盘点单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WvStockEntity wvStock, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvStock.getId())) {
			wvStock = wvStockService.getEntity(WvStockEntity.class,
					wvStock.getId());
			req.setAttribute("wvStockPage", wvStock);
		}
		return new ModelAndView("com/zzjee/wm/wvStock-add");
	}

	/**
	 * 生成盘点单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WvStockEntity wvStock, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wvStock.getId())) {
			wvStock = wvStockService.getEntity(WvStockEntity.class,
					wvStock.getId());
			req.setAttribute("wvStockPage", wvStock);
		}
		return new ModelAndView("com/zzjee/wm/wvStock-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "wvStockController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WvStockEntity wvStock, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WvStockEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wvStock, request.getParameterMap());
		String query_createData_begin = request.getParameter("lastMove_begin1");
		String query_createData_end = request.getParameter("lastMove_end2");
		try {
			// 自定义追加查询条件
			String query_goodsProData_begin = request
					.getParameter("goodsProData_begin");
			String query_goodsProData_end = request
					.getParameter("goodsProData_end");
			if (StringUtil.isNotEmpty(query_goodsProData_begin)) {
				cq.ge("goodsProData",
						Integer.parseInt(query_goodsProData_begin));
			}
			if (StringUtil.isNotEmpty(query_goodsProData_end)) {
				cq.le("goodsProData", Integer.parseInt(query_goodsProData_end));
			}

			if (StringUtil.isNotEmpty(query_createData_begin)) {
				cq.ge("lastMove", DateUtils.str2Date(query_createData_begin,
						DateUtils.datetimeFormat));
			}
			if (StringUtil.isNotEmpty(query_createData_end)) {
				cq.le("lastMove", DateUtils.str2Date(query_createData_end,
						DateUtils.datetimeFormat));
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("kuctype", "库存");
		List<WvStockEntity> wvStocksold = new ArrayList<WvStockEntity>();
		List<WvStockEntity> wvStocks = this.wvStockService
				.getListByCriteriaQuery(cq, false);
		for (WvStockEntity wvStockEntity : wvStocks) {
			if (StringUtil.isNotEmpty(query_createData_begin)&&StringUtil.isNotEmpty(query_createData_end)) {
			if (wvStockEntity.getLastMove().getTime() >= DateUtils.str2Date(
					query_createData_begin, DateUtils.datetimeFormat).getTime()
					&& wvStockEntity.getLastMove().getTime() <= DateUtils
							.str2Date(query_createData_end,
									DateUtils.datetimeFormat).getTime()) {
				wvStocksold.add(wvStockEntity);
			}
			}else if(StringUtil.isNotEmpty(query_createData_begin)){
				if (wvStockEntity.getLastMove().getTime() >= DateUtils.str2Date(
						query_createData_begin, DateUtils.datetimeFormat).getTime()) {
					wvStocksold.add(wvStockEntity);
				}
			}else if(StringUtil.isNotEmpty(query_createData_end)){
				if (wvStockEntity.getLastMove().getTime() <= DateUtils.str2Date(
						query_createData_end, DateUtils.datetimeFormat).getTime()) {
					wvStocksold.add(wvStockEntity);
				}
			}else{
				wvStocksold.add(wvStockEntity);
			}
			
			
		}
	
		modelMap.put(NormalExcelConstants.FILE_NAME, "生成盘点单");
		modelMap.put(NormalExcelConstants.CLASS, WvStockEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("生成盘点单列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, wvStocksold);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WvStockEntity wvStock,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "生成盘点单");
		modelMap.put(NormalExcelConstants.CLASS, WvStockEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("生成盘点单列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
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
				List<WvStockEntity> listWvStockEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(),
								WvStockEntity.class, params);
				for (WvStockEntity wvStock : listWvStockEntitys) {
					wvStockService.save(wvStock);
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

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?>  list( @RequestParam(value="username", required=false) String username, @RequestParam(value="searchstr", required=false)String searchstr, @RequestParam(value="searchstr2", required=false)String searchstr2) {
//		return listWvGis;


		ResultDO D0 = new  ResultDO();
		String hql = " from WvStockEntity where 1 = 1   ";
		D0.setOK(true);
		if(!StringUtil.isEmpty(searchstr)) {
			hql=hql+"  and kuWeiBianMa like '%" + searchstr + "%'";
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

		List<WvStockEntity> listWvStocks = wvStockService.findHql(hql);


		List<WvStockEntity> result = new ArrayList<WvStockEntity>();
		int i = 0;
		for (WvStockEntity t :listWvStocks){

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
		WvStockEntity task = wvStockService.get(WvStockEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WvStockEntity wvStock,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvStockEntity>> failures = validator
				.validate(wvStock);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		try {
			wvStockService.save(wvStock);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wvStock.getId();
		URI uri = uriBuilder.path("/rest/wvStockController/" + id).build()
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WvStockEntity wvStock) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WvStockEntity>> failures = validator
				.validate(wvStock);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		try {
			wvStockService.saveOrUpdate(wvStock);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		wvStockService.deleteEntityById(WvStockEntity.class, id);
	}
}
