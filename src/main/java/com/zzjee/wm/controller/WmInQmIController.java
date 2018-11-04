package com.zzjee.wm.controller;

import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MdCusEntity;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmImNoticeHEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;
import com.zzjee.wm.entity.WmInQmIEntity;
import com.zzjee.wm.entity.WmToUpGoodsEntity;
import com.zzjee.wm.service.WmInQmIServiceI;
import com.zzjee.wm.service.WmToUpServiceI;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.*;
import com.zzjee.wmutil.wmUtil;
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
import org.jeecgframework.core.beanvalidator.BeanValidators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @Title: Controller
 * @Description: 收货登记
 * @author erzhongxmu
 * @date 2017-08-20 19:48:00
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/wmInQmIController")
public class WmInQmIController extends BaseController {
	/**
	 *
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(WmInQmIController.class);

	@Autowired
	private WmInQmIServiceI wmInQmIService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private WmToUpServiceI wmToUpService;

	/**
	 * 收货登记列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmInQmIList");
	}
	@RequestMapping(params = "tlist")
	public ModelAndView tlist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmIntQmIList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WmInQmIEntity wmInQmI, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmInQmIEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmInQmI, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("imNoticeId", "desc");  
		cq.setOrder(map1);  
		if (wmInQmI.getBinSta() == null) {
			cq.eq("binSta", "N");
		}
//		cq.like("imNoticeId", "RK%");
		cq.add();
		this.wmInQmIService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridt")
	public void datagridt(WmInQmIEntity wmInQmI, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmInQmIEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmInQmI, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("imNoticeId", "desc");  
		cq.setOrder(map1); 
		if (wmInQmI.getBinSta() == null) {
			cq.eq("binSta", "N");
		}
        cq.like("imNoticeId", "TH%");
		cq.add();
		this.wmInQmIService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 删除收货登记
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmInQmIEntity wmInQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmInQmI = systemService.getEntity(WmInQmIEntity.class, wmInQmI.getId());
		message = "收货登记删除成功";
		try {
			wmInQmIService.delete(wmInQmI);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "收货登记删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "dotoup")
	@ResponseBody
	public AjaxJson dotoup(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上架成功";
		try {

			List<WmToUpGoodsEntity> wmToUpGoodsList = new ArrayList<WmToUpGoodsEntity>();
			String hql0 = "from WmInQmIEntity where binSta = 'N' and  id = ?";
			List<WmInQmIEntity> WmInQmIEntityList = systemService.findHql(hql0,
					request.getParameter("id").toString());// 获取行项目
			for (WmInQmIEntity wmInQmIEntity : WmInQmIEntityList) {
				WmToUpGoodsEntity wmToUpGoodsEntity = new WmToUpGoodsEntity();
				wmToUpGoodsEntity.setGoodsId(wmInQmIEntity.getGoodsId());
				wmToUpGoodsEntity.setGoodsProData(wmInQmIEntity.getProData());
				wmToUpGoodsEntity.setGoodsBatch(wmInQmIEntity.getGoodsBatch());
				wmToUpGoodsEntity.setGoodsQua(wmInQmIEntity.getQmOkQuat());
				wmToUpGoodsEntity.setGoodsUnit(wmInQmIEntity.getGoodsUnit());
				wmToUpGoodsEntity.setOrderIdI(wmInQmIEntity.getId());
				wmToUpGoodsEntity.setOrderId(wmInQmIEntity.getImNoticeId());
				wmToUpGoodsEntity.setBinId(wmInQmIEntity.getTinId());
				wmToUpGoodsEntity.setKuWeiBianMa(wmInQmIEntity.getBinId());
				wmToUpGoodsEntity.setCusCode(wmInQmIEntity.getCusCode());
				wmToUpGoodsEntity.setGoodsName(wmInQmIEntity.getGoodsName());
				wmToUpGoodsEntity.setActTypeCode("01");
				String sql = "select     md.suo_shu_ke_hu as cuscode from    md_bin md  where    md.ku_wei_bian_ma = '"
						+ wmInQmIEntity.getBinId() + "'";
				Map<String, Object> binMap	 = systemService.findOneForJdbc(sql);
				if(binMap==null){
					j.setSuccess(false);
					message = "储位不存在";
					j.setMsg(message);
					return j;
					
				}

				try{

					MvGoodsEntity mvgoods = new MvGoodsEntity();
					mvgoods = systemService.findUniqueByProperty(
							MvGoodsEntity.class, "goodsCode",
							wmToUpGoodsEntity.getGoodsId());
					wmToUpGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
					wmToUpGoodsEntity.setGoodsUnit(mvgoods.getShlDanWei());

					if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
						try {
							wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(Long
									.parseLong(mvgoods.getChlShl())
									* Long.parseLong(wmToUpGoodsEntity.getGoodsQua())));
						} catch (Exception e) {
							// TODO: handle exception
						}

					} else {
						wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity
								.getGoodsQua());
					}


				}catch (Exception e){

				}


				wmInQmIEntity.setBinSta("Y");
				systemService.save(wmToUpGoodsEntity);
				systemService.saveOrUpdate(wmInQmIEntity);
			}

			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
			message = "上架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除收货登记
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "收货登记删除成功";
		try {
			for (String id : ids.split(",")) {
				WmInQmIEntity wmInQmI = systemService.getEntity(
						WmInQmIEntity.class, id);
				wmInQmIService.delete(wmInQmI);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "收货登记删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doGethuozhu", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson doGethuozhu(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		WmImNoticeHEntity wmImNoticeHEntity = systemService
				.findUniqueByProperty(WmImNoticeHEntity.class, "noticeId",
						oConvertUtils.getString(request
								.getParameter("noticeid")));
		if (wmImNoticeHEntity != null) {
			MdCusEntity md = systemService.findUniqueByProperty(
					MdCusEntity.class, "keHuBianMa",
					wmImNoticeHEntity.getCusCode());
			j.setObj(md);
		}

		return j;
	}

	@RequestMapping(params = "doGettext", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson doGettext(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		MdGoodsEntity mdgoods = new MdGoodsEntity();
		MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
				MvGoodsEntity.class, "goodsCode",
				oConvertUtils.getString(request.getParameter("goodsid")));
		if (mvgoods == null) {
			j.setSuccess(false);
			j.setMsg("不存在此商品");
		} else {
			mdgoods.setChlShl("0");
			Double goodsno =   0.00;
			System.out
					.println("*******************8"
							+ oConvertUtils.getString(request
									.getParameter("noticeid")));
			WmImNoticeHEntity wmImNoticeHEntity = systemService
					.findUniqueByProperty(WmImNoticeHEntity.class, "noticeId",
							oConvertUtils.getString(request
									.getParameter("noticeid")));
			String hql0 = "from WmImNoticeIEntity where 1 = 1 AND imNoticeId = ? and goodsCode = ? ";
			List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
					.findHql(hql0, wmImNoticeHEntity.getNoticeId(),
							oConvertUtils.getString(request
									.getParameter("goodsid")));// 获取行项目
			for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
				goodsno = Double.parseDouble(wmImNoticeIEntity.getGoodsCount())
						- Double.parseDouble(wmImNoticeIEntity.getGoodsQmCount());
				if (goodsno > 0) {
					Double quat1 =Double.parseDouble(mvgoods.getMpCengGao()) * Double.parseDouble(mvgoods.getMpDanCeng());
					if(quat1 < goodsno){
						goodsno = quat1;
					}
					mdgoods.setChlShl(goodsno.toString());
					break;
				}
			}
			if (goodsno <= 0) {
				j.setSuccess(false);
				j.setMsg("已经收货登记完毕");
			}
		}
		mdgoods.setShpMingCheng(mvgoods.getGoodsName());
		j.setObj(mdgoods);
		return j;
	}

	/**
	 * 添加收货登记
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmInQmIEntity wmInQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String flag = "N";
		message = "收货登记添加成功";
		try {
			if (Long.parseLong(wmInQmI.getQmOkQuat()) <= 0) {
				j.setSuccess(false);
				message = "数量不能为0";
				return j;
			}
		} catch (Exception e) {
			j.setSuccess(false);
			message = "数量 错误";
			return j;
			// TODO: handle exception
		}
		try {
			//托盘占用判断
			if("yes".equals(ResourceUtil.getConfigByName("usetuopan"))){
//				String tsql = "select bin_id  as tinid from wv_stock where kuctype = '库存' and  goods_qua <> 0  and bin_id = '"
//						+ wmInQmI.getTinId() + "'";
//				Map<String, Object> tinMap = systemService.findOneForJdbc(tsql);
//				if (tinMap != null) {
//					j.setSuccess(false);
//					message = "托盘已被占用";
//					j.setMsg(message);
//					return j;
//				}
			}else{
				if (StringUtil.isEmpty(wmInQmI.getTinId())){
					wmInQmI.setTinId(ResourceUtil.getConfigByName("tuopanma"));
				}
			}
			//托盘占用判断

			String flagchsh = "y";
			try {
				WmImNoticeIEntity wmImNoticeIEntity = systemService.get(WmImNoticeIEntity.class,wmInQmI.getImNoticeItem());
				if(wmImNoticeIEntity!=null){
					if("n".equals(ResourceUtil.getConfigByName("chaoshou"))){
						Long weiq = Long.parseLong(wmImNoticeIEntity
								.getGoodsCount())
								- Long.parseLong(wmImNoticeIEntity
								.getGoodsQmCount());
						if (Long.parseLong(wmInQmI.getQmOkQuat()) > weiq) {
							flagchsh = "n";
						}
					}

				}
			}catch (Exception e){

			}

					if("n".equals(flagchsh)){
						j.setSuccess(false);
						message = "不允许超收";
					}


			WmImNoticeHEntity wmImNoticeHEntity = systemService
					.findUniqueByProperty(WmImNoticeHEntity.class, "noticeId",
							wmInQmI.getImNoticeId());
			WmImNoticeIEntity wmimnotice = new WmImNoticeIEntity();
			if (wmImNoticeHEntity != null) {
				flag = "X";
				try{
					wmInQmI.setCusCode(wmImNoticeHEntity.getCusCode());
					String hql0 = "from WmImNoticeIEntity where 1 = 1 AND imNoticeId = ? ";
					List<WmImNoticeIEntity> wmImNoticeIEntityList = systemService
							.findHql(hql0, wmImNoticeHEntity.getNoticeId());// 获取行项目
					for (WmImNoticeIEntity wmImNoticeIEntity : wmImNoticeIEntityList) {
						if (wmImNoticeIEntity.getGoodsCode().equals(
								wmInQmI.getGoodsId())) {
							Long weiq = Long.parseLong(wmImNoticeIEntity
									.getGoodsCount())
									- Long.parseLong(wmImNoticeIEntity
									.getGoodsQmCount());
							if (Long.parseLong(wmInQmI.getQmOkQuat()) <= weiq) {
								wmimnotice = wmImNoticeIEntity;
								flag = "X";
								break;
							}
						}

					}
				}catch (Exception e){

				}

			} else {
				j.setSuccess(false);
				message = "收货通知不存在";
			}
			if (!flag.equals("X")) {
				j.setSuccess(false);
				message = "收货通知下此商品不存在或已经全部收货";
			}
			if (flag.equals("X")) {
				MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
						MvGoodsEntity.class, "goodsCode", wmInQmI.getGoodsId());
				if (mvgoods != null) {
					wmInQmI.setGoodsName(mvgoods.getGoodsName());
					try {
						wmInQmI.setTinTj(String.valueOf(Double.parseDouble(mvgoods
								.getTiJiCm())
								*  Long.parseLong(wmInQmI.getQmOkQuat())));
						wmInQmI.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
								.getZhlKg())
								* Long.parseLong(wmInQmI.getQmOkQuat())));
					} catch (Exception e) {
						// TODO: handle exception
					}

					wmInQmI.setGoodsUnit(mvgoods.getShlDanWei());
				}
				wmInQmI.setImNoticeItem(wmimnotice.getId());
				wmInQmI.setImQuat(wmimnotice.getGoodsCount());
				wmInQmI.setImCusCode(wmimnotice.getImCusCode());
//				wmInQmI.setBinId(wmInQmI.getImNoticeId());
				wmInQmIService.save(wmInQmI);
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "收货登记添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}





	/**
	 * 更新收货登记
	 *

	 * @return
	 */
	@RequestMapping(params = "dobatchUpdatedate")
	@ResponseBody
	public AjaxJson dobatchUpdatedate(WmInQmIEntity wmInQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "收货登记更新成功";
		WmInQmIEntity t = wmInQmIService.get(WmInQmIEntity.class,
				request.getParameter("id"));

		String batchdate = request.getParameter("batchdate");
		if(StringUtil.isEmpty(batchdate)){
			message = "日期不能为空";
			j.setSuccess(false);
		}else{
			t.setGoodsBatch(batchdate);
			t.setProData(batchdate);
			systemService.updateEntitie(t);
		}
		j.setMsg(message);
		return j;
	}









	/**
	 * 更新收货登记
	 *

	 * @return
	 */
	@RequestMapping(params = "dobatchUpdate")
	@ResponseBody
	public AjaxJson dobatchUpdate(WmInQmIEntity wmInQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "收货登记更新成功";
		WmInQmIEntity t = wmInQmIService.get(WmInQmIEntity.class,
				request.getParameter("id"));

		String binid = request.getParameter("binid");
		if(StringUtil.isEmpty(binid)){
			message = "储位不能为空";
			j.setSuccess(false);
		}else{
			String sql = "select   binid from wv_avabin where binid = '"
					+ binid
					+ "'";
			Map<String, Object> binMap =  systemService.findOneForJdbc(sql);
			if(binMap==null||binMap.isEmpty()){
				message = "储位不能用";
				j.setSuccess(false);
			}else{
				t.setBinId(binid);
				systemService.updateEntitie(t);
			}


		}
		j.setMsg(message);
		return j;
	}




	/**
	 * 更新收货登记
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmInQmIEntity wmInQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "收货登记更新成功";
		WmInQmIEntity t = wmInQmIService.get(WmInQmIEntity.class,
				wmInQmI.getId());
		try {
			if(StringUtil.isNotEmpty(wmInQmI.getBinId())){
			String sql = "select   binid from wv_avabin where binid = '"
					+ wmInQmI.getBinId()
					+ "'";
			Map<String, Object> binMap =  systemService.findOneForJdbc(sql);
			if(wmInQmI.getBinId().equals(t.getBinId())){//没有更改则绕过盘点
				binMap.put("bin", "1");
			}
			
			if (binMap != null) {
				sql = "select     md.suo_shu_ke_hu as cuscode from    md_bin md  where md.ting_yong = 'N' and   md.ku_wei_bian_ma = '"
						+ wmInQmI.getBinId() + "' limit 1";
				binMap = systemService.findOneForJdbc(sql);
				if (binMap != null) {
						MyBeanUtils.copyBeanNotNull2Bean(wmInQmI, t);
						t.setBaseUnit(null);
						wmInQmIService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE,
								Globals.Log_Leavel_INFO);
				} else {
					j.setSuccess(false);
					message = "储位不存在 或已停用";
					j.setMsg(message);
					return j;
				}
			} else {
				j.setSuccess(false);
				message = "储位已被占用";
				j.setMsg(message);
				return j;
			}
			}else{
				MyBeanUtils.copyBeanNotNull2Bean(wmInQmI, t);
				t.setBaseUnit(null);
				wmInQmIService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "收货登记更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 收货登记新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmInQmIEntity wmInQmI, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmInQmI.getId())) {
			wmInQmI = wmInQmIService.getEntity(WmInQmIEntity.class,
					wmInQmI.getId());
			req.setAttribute("wmInQmIPage", wmInQmI);
		}
		return new ModelAndView("com/zzjee/wm/wmInQmI-add");
	}

	
	@RequestMapping(params = "goAddBatch")
	public ModelAndView goAddBatch(WmInQmIEntity wmInQmI, HttpServletRequest req) {
		WmImNoticeIEntity WmImNoticeI = systemService.getEntity(WmImNoticeIEntity.class,
					 req.getParameter("id").toString());
		wmInQmI.setImNoticeId(WmImNoticeI.getImNoticeId());
		wmInQmI.setGoodsId(WmImNoticeI.getGoodsCode());
		Long quat = (long) 0;
		Long quat1 = (long) 0;
			try {
				quat = Long.parseLong(WmImNoticeI.getGoodsCount()) - Long.parseLong(WmImNoticeI.getGoodsQmCount());

			} catch (Exception e) {
				// TODO: handle exception
			}

		if(ResourceUtil.getConfigByName("giwq").equals("no")){
			MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
					MvGoodsEntity.class, "goodsCode",
					WmImNoticeI.getGoodsCode());
			if(mvgoods!=null){
				try {
					quat1 =Long.parseLong(mvgoods.getMpCengGao()) * Long.parseLong(mvgoods.getMpDanCeng());
					wmInQmI.setGoodsUnit(mvgoods.getShlDanWei());
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			if(quat1<quat){
				quat = quat1;
			}
		}

		wmInQmI.setQmOkQuat(Long.toString(quat));
		wmInQmI.setTinId(wmUtil.gettuopanma());

    	req.setAttribute("wmInQmIPage", wmInQmI);

		return new ModelAndView("com/zzjee/wm/wmInQmI-add");
	}
	
	/**
	 * 收货登记编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmInQmIEntity wmInQmI, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmInQmI.getId())) {
			wmInQmI = wmInQmIService.getEntity(WmInQmIEntity.class,
					wmInQmI.getId());
			req.setAttribute("wmInQmIPage", wmInQmI);
		}
		return new ModelAndView("com/zzjee/wm/wmInQmI-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "wmInQmIController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmInQmIEntity wmInQmI, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmInQmIEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				wmInQmI, request.getParameterMap());
		List<WmInQmIEntity> wmInQmIs = new ArrayList<WmInQmIEntity>();
		List<WmInQmIEntity> wmInQmIso = this.wmInQmIService
				.getListByCriteriaQuery(cq, false);
for (WmInQmIEntity wmInQmIEntity : wmInQmIso) {
	try {
		MvGoodsEntity mvgoods = new MvGoodsEntity();
		mvgoods = systemService.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", wmInQmIEntity.getGoodsId()) ;
		wmInQmIEntity.setGoodsName(mvgoods.getShpMingCheng());
	} catch (Exception e) {
		// TODO: handle exception
	}
	try {
        MdCusEntity md = systemService.findUniqueByProperty(MdCusEntity.class, "keHuBianMa", wmInQmIEntity.getCusCode());

		wmInQmIEntity.setCusName(md.getZhongWenQch());
	} catch (Exception e) {
		// TODO: handle exception
	}
	wmInQmIs.add(wmInQmIEntity);
}
		modelMap.put(NormalExcelConstants.FILE_NAME, "收货登记");
		modelMap.put(NormalExcelConstants.CLASS, WmInQmIEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收货登记列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, wmInQmIs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmInQmIEntity wmInQmI,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "收货登记");
		modelMap.put(NormalExcelConstants.CLASS, WmInQmIEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收货登记列表",
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
				List<WmInQmIEntity> listWmInQmIEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(),
								WmInQmIEntity.class, params);
				for (WmInQmIEntity wmInQmI : listWmInQmIEntitys) {
					wmInQmIService.save(wmInQmI);
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
		String hql = " from WmInQmIEntity where 1 = 1 and binSta = 'N'  ";
		D0.setOK(true);
		if(!StringUtil.isEmpty(searchstr)) {
			hql=hql+"  and imNoticeId like '%" + searchstr + "%'";
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

		List<WmInQmIEntity> listWmInQmIs = wmInQmIService.findHql(hql);

		List<WmInQmIEntity> result = new ArrayList<WmInQmIEntity>();
		int i = 0;
		for (WmInQmIEntity t :listWmInQmIs){

			i++;
			if(i>100){
				break;
			}
			result.add(t);
		}
		D0.setOK(true);
		D0.setObj(result);
		return new ResponseEntity(D0, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmInQmIEntity task = wmInQmIService.get(WmInQmIEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestParam String wmInQmIstr,
			UriComponentsBuilder uriBuilder) {
		ResultDO D0 = new  ResultDO();
        WmInQmIEntity wmInQmI = (WmInQmIEntity)JSONHelper.json2Object(wmInQmIstr,WmInQmIEntity.class);
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmInQmIEntity>> failures = validator
				.validate(wmInQmI);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		String flag = "y";
		try {
			WmImNoticeIEntity wmImNoticeIEntity = systemService.get(WmImNoticeIEntity.class,wmInQmI.getImNoticeItem());
			if(wmImNoticeIEntity!=null){
				wmInQmI.setGoodsId(wmImNoticeIEntity.getGoodsCode());
				try{
					WmImNoticeHEntity wmImNoticeHEntity = systemService.findUniqueByProperty(WmImNoticeHEntity.class,"noticeId",wmInQmI.getImNoticeId());
					wmInQmI.setCusCode(wmImNoticeHEntity.getCusCode());
					wmInQmI.setImCusCode(wmImNoticeHEntity.getImCusCode());
				}catch (Exception e){

				}

				if("n".equals(ResourceUtil.getConfigByName("chaoshou"))){
					Long weiq = Long.parseLong(wmImNoticeIEntity
							.getGoodsCount())
							- Long.parseLong(wmImNoticeIEntity
							.getGoodsQmCount());
					if (Long.parseLong(wmInQmI.getQmOkQuat()) > weiq) {
						flag = "n";
					}
				}
			}
			wmInQmI.setCreateDate(DateUtils.getDate());
			wmInQmI.setBinSta("N");
			if("no".equals(ResourceUtil.getConfigByName("usetuopan"))){
				wmInQmI.setTinId(ResourceUtil.getConfigByName("tuopanma"));
			}
			if(flag.equals("n")){
				D0.setErrorMsg("不允许超收");
				D0.setOK(false);
			}else{
				try{
					MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
							MvGoodsEntity.class, "goodsCode", wmInQmI.getGoodsId());
					if (mvgoods != null) {
						wmInQmI.setGoodsName(mvgoods.getGoodsName());
					}
				}catch (Exception e){

				}
				wmInQmIService.save(wmInQmI);
				D0.setOK(true);
			}
		} catch (Exception e) {
			D0.setOK(false);
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmInQmI.getId();
		return new ResponseEntity(D0, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmInQmIEntity wmInQmI) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmInQmIEntity>> failures = validator
				.validate(wmInQmI);
		if (!failures.isEmpty()) {
			return new ResponseEntity(
					BeanValidators.extractPropertyAndMessage(failures),
					HttpStatus.BAD_REQUEST);
		}

		// 保存
		try {
			wmInQmIService.saveOrUpdate(wmInQmI);
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
		wmInQmIService.deleteEntityById(WmInQmIEntity.class, id);
	}
}
