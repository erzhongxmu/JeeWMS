package com.zzjee.wm.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MvCusEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;
import com.zzjee.wm.entity.WmOmQmIEntity;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import com.zzjee.wm.page.Delrowpage;
import com.zzjee.wm.service.WmToDownGoodsServiceI;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zzjee.wmapi.entity.WvGiNoticeEntity;
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
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
import com.zzjee.wmutil.wmUtil;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import static com.xiaoleilu.hutool.date.DateTime.now;

/**
 * @Title: Controller
 * @Description: 下架商品明细
 * @author erzhongxmu
 * @date 2017-08-25 10:40:39
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/wmToDownGoodsController")
public class WmToDownGoodsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(WmToDownGoodsController.class);

	@Autowired
	private WmToDownGoodsServiceI wmToDownGoodsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 下架商品明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmToDownGoodsList");
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
	public void datagrid(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmToDownGoodsEntity.class,
				dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmToDownGoods, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		cq.add();
		this.wmToDownGoodsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagridwave")
	public void wavedatagridzzfh(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmToDownGoodsEntity.class,
				dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmToDownGoods, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		cq.isNull("downSta");
		cq.add();
		this.wmToDownGoodsService.getDataGridReturn(cq, true);
		List<WmToDownGoodsEntity> resultnew = new ArrayList<WmToDownGoodsEntity>();
		List<WmToDownGoodsEntity> resultold = dataGrid.getResults();
		for (WmToDownGoodsEntity wmToDownGoodsEntity : resultold) {
			wmToDownGoodsEntity.setGoodsQua(wmToDownGoodsEntity.getGoodsQuaok());
			wmToDownGoodsEntity.setGoodsQuaok("");
			resultnew.add(wmToDownGoodsEntity);
			
		}
		dataGrid.setResults(resultnew);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagridzzfh")
	public void datagridzzfh(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmToDownGoodsEntity.class,
				dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmToDownGoods, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		cq.isNull("downSta");
		cq.add();
		this.wmToDownGoodsService.getDataGridReturn(cq, true);
		List<WmToDownGoodsEntity> resultnew = new ArrayList<WmToDownGoodsEntity>();
		List<WmToDownGoodsEntity> resultold = dataGrid.getResults();
		for (WmToDownGoodsEntity wmToDownGoodsEntity : resultold) {
			wmToDownGoodsEntity.setGoodsQua(wmToDownGoodsEntity.getGoodsQuaok());
			wmToDownGoodsEntity.setGoodsQuaok("");
			resultnew.add(wmToDownGoodsEntity);
			
		}
		dataGrid.setResults(resultnew);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 删除下架商品明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmToDownGoods = systemService.getEntity(WmToDownGoodsEntity.class,
				wmToDownGoods.getId());
		message = "下架商品明细删除成功";
		try {
			wmToDownGoodsService.delete(wmToDownGoods);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "下架商品明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除下架商品明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架商品明细删除成功";
		try {
			for (String id : ids.split(",")) {
				WmToDownGoodsEntity wmToDownGoods = systemService.getEntity(
						WmToDownGoodsEntity.class, id);
				wmToDownGoodsService.delete(wmToDownGoods);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "下架商品明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 行编辑列表
	 */
	//JeecgListDemoController.do?rowListDemo
	@RequestMapping(params = "delList")
	public ModelAndView delListDemo(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/omconf");
	}
	@RequestMapping(params = "wavedelList")
	public ModelAndView wavedelListDemo(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wavedel_rowedtior");
	}


	/**
	 * 更新下架商品明细
	 *
	 * @return
	 */
	@RequestMapping(params = "dofubatch")
	@ResponseBody
	public AjaxJson dofubatch(String id,
							 HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "批量复核成功";
		WmToDownGoodsEntity t = wmToDownGoodsService.get(
				WmToDownGoodsEntity.class, id);
		try {
			t.setDownSta(Constants.wm_sta5);//直接修改状态
			wmToDownGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "批量复核失败";
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
	public AjaxJson updateRows(Delrowpage page){
		String message = null;
		List<WmToDownGoodsEntity> demos=page.getDownrows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmToDownGoodsEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmToDownGoodsEntity t =systemService.get(WmToDownGoodsEntity.class, jeecgDemo.getId());
					try {
						MyBeanUtils.copyBeanNotNull2Bean(jeecgDemo, t);
						systemService.saveOrUpdate(t);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}

	/**
	 * 装车复核
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(Delrowpage page){
		String message = null;
		List<WmToDownGoodsEntity> demos=page.getDownrows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmToDownGoodsEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmToDownGoodsEntity t =systemService.get(WmToDownGoodsEntity.class, jeecgDemo.getId());
					try {
						message = "装车复核成功";
						MyBeanUtils.copyBeanNotNull2Bean(jeecgDemo, t);
						t.setDownSta(Constants.wm_sta5);
						systemService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}
	
	
	@RequestMapping(params = "doGetcusname",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doGetcusname(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		MvCusEntity mvcus = systemService.findUniqueByProperty(MvCusEntity.class, "cusCode", oConvertUtils.getString(request.getParameter("cusCode"))) ;
		if(mvcus==null){
			j.setSuccess(false);
			j.setMsg("不存在货主");
		}
		j.setObj(mvcus);
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
			}else{
									String sql = "select  ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
                                     +" from wv_stock ws, md_bin mb  where "
                                    +" ws.ku_wei_bian_ma = mb.ku_wei_bian_ma and mb.ting_yong <> 'Y' and ws.kuctype = '库存'"
                                    +" and ws.goods_id = ? "
                                    +"  group by ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,mb.qu_huo_ci_xu, ws.goods_pro_data order by ws.goods_pro_data , ws.goods_qua, mb.qu_huo_ci_xu";
						 Object objs = null;
						List<Map<String, Object>> result =  systemService.findForJdbc(sql, request.getParameter("goodsid"));
					
						if(result!=null){
						
						}else{
							j.setSuccess(false);
							j.setMsg("商品没有库存");
						}
			}
			j.setObj(mvgoods);
			return j;
		}
	
	
	@RequestMapping(params = "doGettext",method ={RequestMethod.GET, RequestMethod.POST})
		@ResponseBody
		public AjaxJson doGettext(HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			MvGoodsEntity mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", oConvertUtils.getString(request.getParameter("goodsid"))) ;
			if(mvgoods==null){
				j.setSuccess(false);
				j.setMsg("不存在此商品");
			}else{
				Long goodsno =  (long) 0;
				WmOmNoticeHEntity wmOmNoticeHEntity = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", oConvertUtils.getString(request.getParameter("noticeid")));
				String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND omNoticeId = ? and goodsId = ? ";
				List<WmOmNoticeIEntity> wmOmNoticeIEntityList = systemService
						.findHql(hql0, wmOmNoticeHEntity.getOmNoticeId(),oConvertUtils.getString(request.getParameter("goodsid")));//获取行项目
				for (WmOmNoticeIEntity wmOmNoticeIEntity : wmOmNoticeIEntityList) {
					goodsno = (Long.valueOf(wmOmNoticeIEntity.getGoodsQua()) -  Long.valueOf(wmOmNoticeIEntity.getGoodsQuaok())) * Long.valueOf(mvgoods.getChlShl());
					if(goodsno > 0 ){				
						String sql = "select  ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
                                     +" from wv_stock ws, md_bin mb  where "
                                    +" ws.ku_wei_bian_ma = mb.ku_wei_bian_ma and mb.ting_yong <> 'Y' and ws.kuctype = '库存'"
                                    +" and ws.goods_id = ? "
		                            +" and ws.cus_code =  ? "
                                    +"  group by ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,mb.qu_huo_ci_xu, ws.goods_pro_data order by ws.goods_pro_data , ws.goods_qua, mb.qu_huo_ci_xu";
						 Object objs = null;
						List<Map<String, Object>> result =  systemService.findForJdbc(sql, wmOmNoticeIEntity.getGoodsId(),wmOmNoticeHEntity.getCusCode());
						mvgoods.setChlShl(String.valueOf(goodsno));
						if(result!=null){
							for(int i = 0;i<result.size();i++ ){
								if(Long.valueOf(result.get(i).get("goods_qua").toString())>0){
									if(Long.valueOf(result.get(i).get("goods_qua").toString())>=goodsno){
										mvgoods.setTiJiCm(String.valueOf(goodsno));//数量
										mvgoods.setZhlKg("本次可发完");//还需数量
									}else{
										Long wq = goodsno - Long.valueOf(result.get(i).get("goods_qua").toString());
										mvgoods.setTiJiCm(result.get(i).get("goods_qua").toString());//数量setChcDanWei
										mvgoods.setZhlKg("还需要发"+wq.toString());//还需数量 setChlKongZhi

									}
									mvgoods.setGoodsName(result.get(i).get("shp_ming_cheng").toString());//名称
									mvgoods.setBzhiQi(result.get(i).get("ku_wei_bian_ma").toString());//库位
									mvgoods.setCfWenCeng(result.get(i).get("bin_id").toString());//托盘
								
									mvgoods.setChlShl(result.get(i).get("goods_pro_data").toString());//生成日期 setChDanPin
									break;
								}
							
						}
						}else{
							j.setSuccess(false);
							j.setMsg("商品没有库存");
						}
		
						break;
					}
				}
				if(goodsno <=0){
					j.setSuccess(false);
					j.setMsg("已经发货完毕");
				}
			}
			j.setObj(mvgoods);
			return j;
		}

	/**
	 * 添加下架商品明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架商品明细添加成功";
		try {
			wmToDownGoods.setOrderId("ZY");
			wmToDownGoods.setBinIdTo("PK");
			wmToDownGoods.setDownSta("已复核");
			wmToDownGoods.setGoodsQuaok(wmToDownGoods.getGoodsQua());
			MvGoodsEntity mvgoods = new MvGoodsEntity();
			mvgoods = systemService.findUniqueByProperty(
					MvGoodsEntity.class, "goodsCode",
					wmToDownGoods.getGoodsId());
			if(mvgoods!=null){
				wmToDownGoods.setGoodsName(mvgoods.getGoodsName());
			wmToDownGoods.setBaseUnit(mvgoods.getBaseunit());
			wmToDownGoods.setGoodsUnit(mvgoods.getShlDanWei());
			if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
				try {
					wmToDownGoods.setBaseGoodscount(String.valueOf(Long
							.parseLong(mvgoods.getChlShl())
							* Long.parseLong(wmToDownGoods.getGoodsQua())));
				} catch (Exception e) {
					// TODO: handle exception
				}

			} else {
				wmToDownGoods.setBaseGoodscount(wmToDownGoods
						.getGoodsQua());
			}
			
			if(!wmUtil.checkstcok(wmToDownGoods.getKuWeiBianMa(),wmToDownGoods.getBinIdFrom(),wmToDownGoods.getGoodsId(),wmToDownGoods.getGoodsProData(),wmToDownGoods.getGoodsQua())){
				message = "库存不足";
				j.setMsg(message);
				return j;
	
			};
			wmToDownGoodsService.save(wmToDownGoods);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "下架商品明细添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新下架商品明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架商品明细更新成功";
		WmToDownGoodsEntity t = wmToDownGoodsService.get(
				WmToDownGoodsEntity.class, wmToDownGoods.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmToDownGoods, t);
			wmToDownGoodsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "下架商品明细更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 下架商品明细新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmToDownGoods.getId())) {
			wmToDownGoods = wmToDownGoodsService.getEntity(
					WmToDownGoodsEntity.class, wmToDownGoods.getId());
			req.setAttribute("wmToDownGoodsPage", wmToDownGoods);
		}
		return new ModelAndView("com/zzjee/wm/wmToDownGoods-add");
	}

	/**
	 * 下架商品明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmToDownGoods.getId())) {
			wmToDownGoods = wmToDownGoodsService.getEntity(
					WmToDownGoodsEntity.class, wmToDownGoods.getId());
			req.setAttribute("wmToDownGoodsPage", wmToDownGoods);
		}
		return new ModelAndView("com/zzjee/wm/wmToDownGoods-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "wmToDownGoodsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmToDownGoodsEntity.class,
				dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmToDownGoods, request.getParameterMap());
		List<WmToDownGoodsEntity> wmToDownGoodss = this.wmToDownGoodsService
				.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "下架商品明细");
		modelMap.put(NormalExcelConstants.CLASS, WmToDownGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("下架商品明细列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, wmToDownGoodss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmToDownGoodsEntity wmToDownGoods,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "下架商品明细");
		modelMap.put(NormalExcelConstants.CLASS, WmToDownGoodsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("下架商品明细列表",
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
				List<WmToDownGoodsEntity> listWmToDownGoodsEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(),
								WmToDownGoodsEntity.class, params);
				for (WmToDownGoodsEntity wmToDownGoods : listWmToDownGoodsEntitys) {
					wmToDownGoodsService.save(wmToDownGoods);
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
	public List<WmToDownGoodsEntity> list() {
		List<WmToDownGoodsEntity> listWmToDownGoodss = wmToDownGoodsService
				.getList(WmToDownGoodsEntity.class);
		return listWmToDownGoodss;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmToDownGoodsEntity task = wmToDownGoodsService.get(
				WmToDownGoodsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestParam String wmToDownGoodsstr,
			UriComponentsBuilder uriBuilder) {
		ResultDO D0 = new  ResultDO();
		WmToDownGoodsEntity wmToDownGoods  = (WmToDownGoodsEntity)JSONHelper.json2Object(wmToDownGoodsstr,WmToDownGoodsEntity.class);
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		WvGiNoticeEntity t = new WvGiNoticeEntity();
		Set<ConstraintViolation<WmToDownGoodsEntity>> failures = validator
				.validate(wmToDownGoods);
		if (!failures.isEmpty()) {
			D0.setOK(false);

		}
       try{
		    t = systemService.get(WvGiNoticeEntity.class,wmToDownGoods.getOrderIdI());
		   if(t!=null&&StringUtil.isEmpty(wmToDownGoods.getBinIdFrom())){
		   	wmToDownGoods.setGoodsName(t.getShpMingCheng());
			   wmToDownGoods.setBinIdFrom(t.getTinId());
		   }
	   }catch (Exception e){

	   }
		// 保存
		try {
			wmToDownGoods.setCreateDate(DateUtils.getDate());
			if(wmUtil.checkstcokk(wmToDownGoods.getCusCode(),wmToDownGoods.getKuWeiBianMa(),wmToDownGoods.getBinIdFrom(),wmToDownGoods.getGoodsId(),wmToDownGoods.getGoodsProData(),wmToDownGoods.getBaseGoodscount()))
			{
				WmOmQmIEntity wmOmQmIEntity = systemService.get(WmOmQmIEntity.class,t.getId());
				wmToDownGoods.setImCusCode(wmOmQmIEntity.getImCusCode());
				wmToDownGoods.setOmBeizhu(wmOmQmIEntity.getOmBeizhu());
				wmToDownGoodsService.save(wmToDownGoods);
				D0.setOK(true);
			}else{
				D0.setOK(false);
				D0.setErrorMsg("库存不足");
			};

		} catch (Exception e) {
			e.printStackTrace();
			D0.setOK(false);
			return new ResponseEntity(D0,HttpStatus.OK);
		}

		return new ResponseEntity(D0, HttpStatus.OK);

//		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> update(@RequestParam String wmToDownGoodsstr,
									UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		ResultDO D0 = new  ResultDO();

		WmToDownGoodsEntity wmToDownGoods  = (WmToDownGoodsEntity)JSONHelper.json2Object(wmToDownGoodsstr,WmToDownGoodsEntity.class);
		WmToDownGoodsEntity t = wmToDownGoodsService.get(WmToDownGoodsEntity.class,wmToDownGoods.getId());
		// 保存
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmToDownGoods, t);
			t.setDownSta(Constants.wm_sta5);
			t.setUpdateDate(now());
			wmToDownGoodsService.saveOrUpdate(t);
			D0.setOK(true);
		} catch (Exception e) {
			e.printStackTrace();
			D0.setOK(false);
		}

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(D0, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		wmToDownGoodsService.deleteEntityById(WmToDownGoodsEntity.class, id);
	}
}
