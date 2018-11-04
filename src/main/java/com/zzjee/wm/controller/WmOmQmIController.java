package com.zzjee.wm.controller;

import com.zzjee.wm.entity.*;
import com.zzjee.wm.page.Delrowpage;
import com.zzjee.wm.page.omqmpage;
import com.zzjee.wm.service.WmOmQmIServiceI;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.sms.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;

import org.jeecgframework.web.system.service.SystemService;
import com.zzjee.wmutil.wmUtil;


import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;

import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;


import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Description: 下架任务
 * @author erzhongxmu
 * @date 2017-09-11 14:57:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmOmQmIController")
public class WmOmQmIController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmOmQmIController.class);

	@Autowired
	private WmOmQmIServiceI wmOmQmIService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 下架任务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmOmQmIList");
	}
	@RequestMapping(params = "listd")
	public ModelAndView listd(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmOmQmIdList");
	}
	@RequestMapping(params = "assignlist")
	public ModelAndView assignlist(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/wm/wmOmQmIassignList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WmOmQmIEntity wmOmQmI,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmOmQmIEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmQmI, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		if(wmOmQmI.getBinSta()==null){
			cq.eq("binSta", "N");	
		}
		cq.add();
		this.wmOmQmIService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridassign")
	public void datagridassign(WmOmQmIEntity wmOmQmI,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmOmQmIEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmQmI, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		Map<String,Object> map1 = new HashMap<String,Object>();  
		map1.put("createDate", "desc");  
		cq.setOrder(map1); 
		cq.eq("binSta", "I");	
		cq.add();
		this.wmOmQmIService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "dogetbin", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson dogetbin(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		WmOmQmIEntity wmOmQmI = new WmOmQmIEntity();
		String goods = request.getParameter("goodsid");
		String tinid = request.getParameter("tinid");
		String tsql = "select  ws.ku_wei_bian_ma,  ws.goods_pro_data"
				+ "  from wv_stock ws, md_bin mb  where "
				+ "   ws.ku_wei_bian_ma = mb.ku_wei_bian_ma and mb.ting_yong <> 'Y' and (ws.kuctype = '库存' )"
				+ "   and ws.bin_id =  ? "
				+ "   and ws.goods_id =  ? "
				+ "   group by ws.ku_wei_bian_ma,ws.bin_id,ws.goods_id,mb.qu_huo_ci_xu, ws.goods_pro_data order by ws.goods_pro_data , ws.goods_qua ,mb.qu_huo_ci_xu,ws.create_date desc limit 1";

		List<Map<String, Object>> result = systemService.findForJdbc(tsql,tinid, goods);
		if (result.size() > 0) {
			wmOmQmI.setBinId(result.get(0).get("ku_wei_bian_ma").toString());
			wmOmQmI.setProData(result.get(0).get("goods_pro_data").toString());
		}
		j.setObj(wmOmQmI);
		return j;
	}

	
	@RequestMapping(params = "doassign")
	@ResponseBody
	public AjaxJson doassign(WmOmQmIEntity wmOmQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加到下架任务清单成功";
		WmOmQmIEntity t = wmOmQmIService.get(WmOmQmIEntity.class, request
				.getParameter("id").toString());
		if(!wmUtil.checkstcoka( t.getBinId(),t.getTinId(),t.getGoodsId(),t.getProData(),t.getBaseGoodscount())){
			message = "库存不足";
			j.setMsg(message);
			return j;
		}else{
			try {
				t.setBinSta("N");
				systemService.updateEntitie(t);;
				systemService.addLog(message, Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "添加到下架任务清单失败";
				throw new BusinessException(e.getMessage());
			}
		}
	
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doassignwave")
	@ResponseBody
	public AjaxJson dowavebatch(String ids, String waveid,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "波次生成成功";
		String waveId = "";
		waveId = DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
		try {
			for (String id : ids.split(",")) {
				WmOmQmIEntity t = wmOmQmIService.get(WmOmQmIEntity.class, id);
				try {
					t.setWaveId("BC"+waveId);
				    t.setBinSta("N");//波次直接设置为未下架
					systemService.updateEntitie(t);
					systemService.addLog(message, Globals.Log_Type_UPDATE,
							Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
					message = "波次生成失败";
					throw new BusinessException(e.getMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "添加到下架任务清单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;

	}

    @RequestMapping(params = "dodelwave")
    @ResponseBody
    public AjaxJson dodelwavebatch(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "波次删除成功";
        String waveId = "";
        waveId = DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
        try {
            for (String id : ids.split(",")) {
                WmOmQmIEntity t = wmOmQmIService.get(WmOmQmIEntity.class, id);
                try {
                    if("N".equals(t.getBinSta())){
                        t.setWaveId(null);
                        systemService.updateEntitie(t);
                        systemService.addLog(message, Globals.Log_Type_UPDATE,
                                Globals.Log_Leavel_INFO);
                    }
//                    t.setWaveId("BC"+waveId);
//                    t.setBinSta("N");//波次直接设置为未下架

                } catch (Exception e) {
                    e.printStackTrace();
                    message = "波次删除失败";
                    throw new BusinessException(e.getMessage());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "波次删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;

    }


	@RequestMapping(params = "doassignbatch")
	@ResponseBody
	public AjaxJson doassignbatch(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加到下架任务清单成功";
		try {
			for (String id : ids.split(",")) {
				WmOmQmIEntity t = wmOmQmIService.get(WmOmQmIEntity.class, id);
				try {
					if(!wmUtil.checkstcoka( t.getBinId(),t.getTinId(),t.getGoodsId(),t.getProData(),t.getBaseGoodscount())){
						message = "库存不足";
						j.setMsg(message);
						return j;
					}else{
						t.setBinSta("N");
						systemService.updateEntitie(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE,
								Globals.Log_Leavel_INFO);
					}

				} catch (Exception e) {
					e.printStackTrace();
					message = "添加到下架任务清单失败";
					throw new BusinessException(e.getMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "添加到下架任务清单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	/**
	 * 删除下架任务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmOmQmIEntity wmOmQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmOmQmI = systemService.getEntity(WmOmQmIEntity.class, wmOmQmI.getId());
		message = "下架任务删除成功";
		try{
			wmOmQmIService.delete(wmOmQmI);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "下架任务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	@RequestMapping(params = "dotodown")
	@ResponseBody
	public AjaxJson dotodown(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架成功";
		try {
			WmOmQmIEntity wmOmQmI = systemService.getEntity(
					WmOmQmIEntity.class, request.getParameter("id").toString());
			if (wmOmQmI != null&&wmOmQmI.getBinSta().equals("N")) {
				WmToDownGoodsEntity wmToDownGoods = new WmToDownGoodsEntity();
				wmToDownGoods.setBinIdFrom(wmOmQmI.getTinId());//下架托盘
				wmToDownGoods.setKuWeiBianMa(wmOmQmI.getBinId());//储位
				wmToDownGoods.setBinIdTo(wmOmQmI.getOmNoticeId());//到托盘
				wmToDownGoods.setCusCode(wmOmQmI.getCusCode());//货主
				wmToDownGoods.setGoodsId(wmOmQmI.getGoodsId());//
				wmToDownGoods.setGoodsProData(wmOmQmI.getProData());//生产日期
				wmToDownGoods.setOrderId(wmOmQmI.getOmNoticeId());//出货通知单
				wmToDownGoods.setOrderIdI(wmOmQmI.getId());//出货通知项目
				wmToDownGoods.setBaseUnit(wmOmQmI.getBaseUnit());//基本单位
				wmToDownGoods.setBaseGoodscount(wmOmQmI.getBaseGoodscount());//基本单位数量
				wmToDownGoods.setGoodsUnit(wmOmQmI.getGoodsUnit());//出货单位
				wmToDownGoods.setGoodsQua(wmOmQmI.getQmOkQuat());//出货数量
				wmToDownGoods.setGoodsQuaok(wmOmQmI.getQmOkQuat());//出货数量
				wmToDownGoods.setGoodsName(wmOmQmI.getGoodsName());//商品名称
				wmToDownGoods.setOmBeizhu(wmOmQmI.getOmBeizhu());//备注
				wmToDownGoods.setImCusCode(wmOmQmI.getImCusCode());//客户单号
				wmToDownGoods.setOrderType("01");//默认为01
				systemService.save(wmToDownGoods);
				wmOmQmI.setBinSta("Y");
				systemService.saveOrUpdate(wmOmQmI);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			} else {
				j.setSuccess(false);

				message = "下架任务找不到";
			}

		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
			message = "下架失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 批量删除下架任务
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架任务删除成功";
		try{
			for(String id:ids.split(",")){
				WmOmQmIEntity wmOmQmI = systemService.getEntity(WmOmQmIEntity.class, 
				id
				);
				wmOmQmIService.delete(wmOmQmI);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "下架任务删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加下架任务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmOmQmIEntity wmOmQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架任务添加成功";
		try{
			wmOmQmIService.save(wmOmQmI);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "下架任务添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 *
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(omqmpage page){
		String message = null;
		List<WmOmQmIEntity> demos=page.getOmqmrows();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(WmOmQmIEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					WmOmQmIEntity t =systemService.get(WmOmQmIEntity.class, jeecgDemo.getId());
					try {
						if(!wmUtil.checkstcok( jeecgDemo.getBinId(),jeecgDemo.getTinId(),jeecgDemo.getGoodsId(),jeecgDemo.getProData(),jeecgDemo.getBaseGoodscount())) {
						}else{
						message = "批量保存成功";
						MyBeanUtils.copyBeanNotNull2Bean(jeecgDemo, t);
						systemService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}



	/**
	 * 更新下架任务
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmOmQmIEntity wmOmQmI, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "下架任务更新成功";
		WmOmQmIEntity t = wmOmQmIService.get(WmOmQmIEntity.class, wmOmQmI.getId());
		try {
			if(!wmUtil.checkstcok( wmOmQmI.getBinId(),wmOmQmI.getTinId(),wmOmQmI.getGoodsId(),wmOmQmI.getProData(),wmOmQmI.getBaseGoodscount())){
				message = "库存不足";
				j.setMsg(message);
				return j;
			}else{
				double goods = Double.parseDouble(wmOmQmI.getBaseGoodscount());
					wmOmQmI.setQmOkQuat(wmOmQmI.getBaseGoodscount());
					wmOmQmI.setOmQuat(wmOmQmI.getBaseGoodscount());
					MyBeanUtils.copyBeanNotNull2Bean(wmOmQmI, t);
				wmOmQmIService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "下架任务更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 下架任务新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmOmQmIEntity wmOmQmI, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmOmQmI.getId())) {
			wmOmQmI = wmOmQmIService.getEntity(WmOmQmIEntity.class, wmOmQmI.getId());
			req.setAttribute("wmOmQmIPage", wmOmQmI);
		}
		return new ModelAndView("com/zzjee/wm/wmOmQmI-add");
	}
	/**
	 * 下架任务编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmOmQmIEntity wmOmQmI, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmOmQmI.getId())) {
			wmOmQmI = wmOmQmIService.getEntity(WmOmQmIEntity.class, wmOmQmI.getId());
			req.setAttribute("wmOmQmIPage", wmOmQmI);
		}
		return new ModelAndView("com/zzjee/wm/wmOmQmI-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmOmQmIController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmOmQmIEntity wmOmQmI,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmOmQmIEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmOmQmI, request.getParameterMap());
		List<WmOmQmIEntity> wmOmQmIs = this.wmOmQmIService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"下架任务");
		modelMap.put(NormalExcelConstants.CLASS,WmOmQmIEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("下架任务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmOmQmIs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmOmQmIEntity wmOmQmI,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"下架任务");
    	modelMap.put(NormalExcelConstants.CLASS,WmOmQmIEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("下架任务列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WmOmQmIEntity> listWmOmQmIEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmOmQmIEntity.class,params);
				for (WmOmQmIEntity wmOmQmI : listWmOmQmIEntitys) {
					wmOmQmIService.save(wmOmQmI);
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
	public List<WmOmQmIEntity> list() {
		List<WmOmQmIEntity> listWmOmQmIs=wmOmQmIService.getList(WmOmQmIEntity.class);
		return listWmOmQmIs;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmOmQmIEntity task = wmOmQmIService.get(WmOmQmIEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmOmQmIEntity wmOmQmI, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmOmQmIEntity>> failures = validator.validate(wmOmQmI);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmOmQmIService.save(wmOmQmI);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmOmQmI.getId();
		URI uri = uriBuilder.path("/rest/wmOmQmIController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmOmQmIEntity wmOmQmI) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmOmQmIEntity>> failures = validator.validate(wmOmQmI);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmOmQmIService.saveOrUpdate(wmOmQmI);
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
		wmOmQmIService.deleteEntityById(WmOmQmIEntity.class, id);
	}
}
