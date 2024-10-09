package com.zzjee.tms.controller;

import com.alibaba.fastjson.JSONArray;
import com.xiaoleilu.hutool.date.DateTime;
import com.zzjee.api.ResultDO;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.rfid.entity.RfidBuseEntity;
import com.zzjee.tms.entity.DdPage;
import com.zzjee.tms.entity.TmsMdCheliangEntity;
import com.zzjee.tms.entity.TmsMdDzEntity;
import com.zzjee.tms.entity.TmsYwDingdanEntity;
import com.zzjee.tms.service.TmsYwDingdanServiceI;
import com.zzjee.wm.entity.*;
import com.zzjee.wm.service.WmOmNoticeHServiceI;
import com.zzjee.wmutil.wmUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

import static com.xiaoleilu.hutool.date.DateUtil.now;

/**
 * @Title: Controller
 * @Description: 运输订单
 * @author onlineGenerator
 * @date 2018-01-29 21:57:25
 * @version V1.0
 *
 */
@Api(value="TmsYwDingdan",description="运输订单",tags="tmsYwDingdanController")
@Controller
@RequestMapping("/tmsYwDingdanController")
public class TmsYwDingdanController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TmsYwDingdanController.class);

	@Autowired
	private TmsYwDingdanServiceI tmsYwDingdanService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;

	@Autowired
	private WmOmNoticeHServiceI wmOmNoticeHService;

	/**
	 * 运输订单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanList");
	}


	/**
	 * 运输订单列表 页面跳转 派车
	 *
	 * @return
	 */
	@RequestMapping(params = "listpc")
	public ModelAndView listpc(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanpcList");
	}


	/**
	 * 运输订单列表 页面跳转 装车
	 *
	 * @return
	 */
	@RequestMapping(params = "listzc")
	public ModelAndView listzc(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanzcList");
	}


	/**
	 * 运输订单列表 页面跳转 回单
	 *
	 * @return
	 */
	@RequestMapping(params = "listhd")
	public ModelAndView listhd(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanhdList");
	}

	/**
	 * 运输订单列表 页面跳转 回单
	 *
	 * @return
	 */
	@RequestMapping(params = "listjs")
	public ModelAndView listjs(HttpServletRequest request) {
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanjsList");
	}


	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsYwDingdanEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsYwDingdan, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_sdsj_begin = request.getParameter("sdsj_begin");
		String query_sdsj_end = request.getParameter("sdsj_end");
		if(StringUtil.isNotEmpty(query_sdsj_begin)){
			cq.ge("sdsj", Integer.parseInt(query_sdsj_begin));
		}
		if(StringUtil.isNotEmpty(query_sdsj_end)){
			cq.le("sdsj", Integer.parseInt(query_sdsj_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");

		}
		cq.eq("zhuangtai","已下单");
//		cq.notEq("zhuangtai","已派车");
//		cq.notEq("zhuangtai","已装车");

		cq.add();
		this.tmsYwDingdanService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	@RequestMapping(params = "datagridpc")
	public void datagridpc(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsYwDingdanEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsYwDingdan, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_sdsj_begin = request.getParameter("sdsj_begin");
			String query_sdsj_end = request.getParameter("sdsj_end");
			if(StringUtil.isNotEmpty(query_sdsj_begin)){
				cq.ge("sdsj", Integer.parseInt(query_sdsj_begin));
			}
			if(StringUtil.isNotEmpty(query_sdsj_end)){
				cq.le("sdsj", Integer.parseInt(query_sdsj_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("zhuangtai","已下单");
//		TSUser user = ResourceUtil.getSessionUser();
//		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
//			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");
//
//		}
//		if(!StringUtil.isEmpty(user.getUserType())){
//			if(user.getUserType().equals("4")){
//				cq.eq("username",user.getUserName());
//			}
//
//		}
		cq.add();
		this.tmsYwDingdanService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridzc")
	public void datagridzc(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsYwDingdanEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsYwDingdan, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_sdsj_begin = request.getParameter("sdsj_begin");
			String query_sdsj_end = request.getParameter("sdsj_end");
			if(StringUtil.isNotEmpty(query_sdsj_begin)){
				cq.ge("sdsj", Integer.parseInt(query_sdsj_begin));
			}
			if(StringUtil.isNotEmpty(query_sdsj_end)){
				cq.le("sdsj", Integer.parseInt(query_sdsj_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("zhuangtai","已派车");
		TSUser user = ResourceUtil.getSessionUser();
//		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
//			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");
//
//		}
//		if(!StringUtil.isEmpty(user.getUserType())){
//			if(user.getUserType().equals("4")){
//				cq.eq("siji",user.getUserName());
//			}
//
//		}
//		if(!StringUtil.isEmpty(user.getUserType())){
//			if(user.getUserType().equals("4")){
//				cq.eq("username",user.getUserName());
//			}
//
//		}

		cq.add();
		this.tmsYwDingdanService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridhd")
	public void datagridhd(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsYwDingdanEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsYwDingdan, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_sdsj_begin = request.getParameter("sdsj_begin");
			String query_sdsj_end = request.getParameter("sdsj_end");
			if(StringUtil.isNotEmpty(query_sdsj_begin)){
				cq.ge("sdsj", Integer.parseInt(query_sdsj_begin));
			}
			if(StringUtil.isNotEmpty(query_sdsj_end)){
				cq.le("sdsj", Integer.parseInt(query_sdsj_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("zhuangtai","已装车");
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");

		}
		if(!StringUtil.isEmpty(user.getUserType())){
			if(user.getUserType().equals("4")){
				cq.eq("username",user.getUserName());
			}

		}
		cq.add();
		this.tmsYwDingdanService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "datagridjs")
	public void datagridjs(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TmsYwDingdanEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsYwDingdan, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_sdsj_begin = request.getParameter("sdsj_begin");
			String query_sdsj_end = request.getParameter("sdsj_end");
			if(StringUtil.isNotEmpty(query_sdsj_begin)){
				cq.ge("sdsj", Integer.parseInt(query_sdsj_begin));
			}
			if(StringUtil.isNotEmpty(query_sdsj_end)){
				cq.le("sdsj", Integer.parseInt(query_sdsj_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.eq("zhuangtai","已回单");
		TSUser user = ResourceUtil.getSessionUser();
		if(!StringUtil.isEmpty(user.getCurrentDepart().getOrgCode())){
			cq.like("sysOrgCode",user.getCurrentDepart().getOrgCode()+"%");

		}
//		if(!StringUtil.isEmpty(user.getUserType())){
//			if(user.getUserType().equals("4")){
//				cq.eq("username",user.getUserName());
//			}
//
//		}
		cq.add();
		this.tmsYwDingdanService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 保存新增/更新的行数据
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(DdPage page){
		String message = null;
		List<TmsYwDingdanEntity> demos=page.getDemos();
		AjaxJson j = new AjaxJson();
		if(CollectionUtils.isNotEmpty(demos)){
			for(TmsYwDingdanEntity jeecgDemo:demos){
				if (StringUtil.isNotEmpty(jeecgDemo.getId())) {
					TmsYwDingdanEntity t =tmsYwDingdanService.get(TmsYwDingdanEntity.class, jeecgDemo.getId());
					try {
						message = jeecgDemo.getId()+ "被更新成功";
						MyBeanUtils.copyBeanNotNull2Bean(jeecgDemo, t);
						if(StringUtil.isEmpty(t.getYjsdsj())){
							t.setYjsdsj(now());
						}
						t.setZhuangtai("已回单");
						t.setSdsj(now());
						tmsYwDingdanService.saveOrUpdate(t);
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return j;
	}

	@RequestMapping(params = "doPrintpage")
	public ModelAndView doPrint(String ids,HttpServletRequest request) {
		List<WmTmsNoticeIEntity>  arrayoutlist = new ArrayList<>();
		for(String id1:ids.split(",")) {
			TmsYwDingdanEntity tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class,
					id1
			);

				try{
					String omnoticeid = tmsYwDingdan.getFadh();
					List<WmTmsNoticeIEntity> listtemp = systemService.findByProperty(WmTmsNoticeIEntity.class,"omNoticeId",omnoticeid);
                   for(WmTmsNoticeIEntity t: listtemp){
//                   	<td class=xl65 style='border:1.0pt solid black;text-align: center'>收货人</td>
//			<td class=xl65 style='border:1.0pt solid black;text-align: center'>收货人电话</td>
//			<td class=xl65 style='border:1.0pt solid black;text-align: center'>车号</td>
//			<td class=xl65 style='border:1.0pt solid black;text-align: center'>收货人地址</td>
                   	t.setBaseUnit(tmsYwDingdan.getShouhuoren());
                   	t.setBaseGoodscount(tmsYwDingdan.getShrsj());
                   	t.setPlanSta(tmsYwDingdan.getChehao());
                   	t.setBinId(tmsYwDingdan.getShrdh());
					   arrayoutlist.add(t);
				   }
				}catch (Exception e){

			}

 		}
		request.setAttribute("arrayoutlist",arrayoutlist);
		return new ModelAndView("com/zzjee/tms/chukumingxi");

	}
	/**
	 * 删除运输订单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
		message = "运输订单删除成功";
		try{
			tmsYwDingdan.setZhuangtai("已删除");
			tmsYwDingdanService.updateEntitie(tmsYwDingdan);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}




	@RequestMapping(params = "dopc")
	@ResponseBody
	public AjaxJson dopc(String id, String siji, String chehao, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
 		message = "运输订单派车成功";
 		try{
			for(String id1:id.split(",")) {
				TmsYwDingdanEntity tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class,
						id1
				);

				tmsYwDingdan.setZhuangtai("已派车");
				tmsYwDingdan.setSiji(siji);
				tmsYwDingdan.setChehao(chehao);
				tmsYwDingdanService.updateEntitie(tmsYwDingdan);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
			Map<String,String> maphz = new HashMap<>();
			Map<String,String> mapsfoderid = new HashMap<>();

			for(String id1:id.split(",")) {//计算总货主和单数
				TmsYwDingdanEntity tmsYwDingdan1 = systemService.getEntity(TmsYwDingdanEntity.class,
						id1);
				WmTmsNoticeHEntity wmsom = systemService.findUniqueByProperty(WmTmsNoticeHEntity.class,"omNoticeId",tmsYwDingdan1.getFadh());
				try{
					wmsom.setReMobile(wmsom.getReMember());
					wmsom.setReMember(siji);
				}catch (Exception e){

				}


				if(!maphz.containsKey(wmsom.getCusCode())){
					maphz.put(wmsom.getCusCode(),wmsom.getOmNoticeId());
					mapsfoderid.put(wmsom.getCusCode(),wmsom.getOmBeizhu());
				}else{
					String ys = maphz.get(wmsom.getCusCode());
					ys = ys+";"+wmsom.getOmNoticeId();
					maphz.put(wmsom.getCusCode(),ys);
				   String 	 sforderid = mapsfoderid.get(wmsom.getCusCode());
				   sforderid = sforderid+";"+wmsom.getOmBeizhu();
				   mapsfoderid.put(wmsom.getCusCode(),sforderid);

				}
			}
			Set<String> keySets = maphz.keySet();
			Iterator<String> ki = keySets.iterator();
 			while(ki.hasNext()){
				WmOmNoticeHEntity wmOmNoticeH = new WmOmNoticeHEntity();
				wmOmNoticeH.setReCarno(chehao);//车号
				wmOmNoticeH.setReMember(siji);//司机
				String mapkey = ki.next();
				String mapvv = maphz.get(mapkey);
				String sforderid = mapsfoderid.get(mapkey);
				String[]   strva= mapvv.split(";");
				String sqla = "(" ;
				for(int a = 0;a<strva.length;a++){
					if(a != strva.length -1){
						sqla =  sqla + "'" +strva[a]+"',";
					}else{
						sqla =  sqla + "'" +strva[a]+"'";
					}
				}
			sqla =		sqla + ") ";
			String tsql = "SELECT goods_id,sum(goods_qua) as goodsqua FROM wms.tms_om_notice_i where om_notice_id in " +
					sqla+
					"  group by goods_id";

				List<Map<String, Object>> resultz = systemService
						.findForJdbc(tsql);
				System.out.print("****************tsqlz" + tsql);
				String noticeid = wmUtil.getNextomNoticeId("");
				wmOmNoticeH.setOmNoticeId(noticeid);
				List<WmOmNoticeIEntity> wmOmNoticeIListnew = new ArrayList<>();
				wmOmNoticeH.setCusCode(mapkey);
				wmOmNoticeH.setPiClass(sforderid);//三方单号
				if (resultz != null && resultz.size() > 0) {
					for (int i = 0; i < resultz.size(); i++) {
						WmOmNoticeIEntity t = new  WmOmNoticeIEntity();
						String  goods_id = "";
						try {
							goods_id=  resultz.get(i)
									.get("goods_id").toString() ;
						} catch (Exception e) {

						}
						String bin_qua =  resultz.get(i)
								.get("goodsqua").toString();
						t.setGoodsId(goods_id);
						t.setGoodsQua(bin_qua);
						wmOmNoticeIListnew.add(t);
					}
				}
				wmOmNoticeHService.addMain(wmOmNoticeH, wmOmNoticeIListnew);
			}
			}catch(Exception e){
			e.printStackTrace();
			message = "运输订单派车失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}



	@RequestMapping(value = "/list/songhuo",  method = RequestMethod.GET)   //总订单
	@ResponseBody
	public ResponseEntity<?> list(@RequestParam(value="username", required=false) String username,
								  @RequestParam(value="searchstr", required=false)String searchstr,
								  @RequestParam(value="searchstr2", required=false)String searchstr2) {
		ResultDO D0 = new  ResultDO();
		D0.setOK(true);
		String hql="from WmTmsNoticeHEntity  ";
		System.out.println("/list/username"+username );
		System.out.println("/list/searchstr"+searchstr );

		List<WmTmsNoticeHEntity> listWaveToDowns =new ArrayList<>();
		if(StringUtil.isNotEmpty(searchstr)&&!"null".equals(searchstr)){
			hql="from WmTmsNoticeHEntity where  omSta = ? and  reMember = ? and  delvMobile like ?";
			listWaveToDowns = wmOmNoticeHService.findHql(hql,"复核完成",username,"%"+searchstr+"%");
		}else{
			hql="from WmTmsNoticeHEntity where omSta = ? and reMember = ? ";
			listWaveToDowns = wmOmNoticeHService.findHql(hql,"复核完成",username);

		}
		D0.setObj(listWaveToDowns);
		System.out.println("/list/songhuolistWaveToDowns==="+listWaveToDowns.toString()+listWaveToDowns.size());
		return new ResponseEntity(D0, HttpStatus.OK);
	}

    @RequestMapping(value = "/listdetail/songhuo",  method = RequestMethod.GET)   //总订单
    @ResponseBody
    public ResponseEntity<?> listdetail(
                                  @RequestParam(value="omnoticeid", required=false)String omnoticeid) {
        ResultDO D0 = new  ResultDO();
        D0.setOK(true);
		System.out.println("/list/omNoticeId"+omnoticeid );

		String hql="from WmTmsNoticeIEntity  where omNoticeId = ? order by goodsId";
        List<WmTmsNoticeIEntity> listWaveToDowns =new ArrayList<>();
        listWaveToDowns = wmOmNoticeHService.findHql(hql,omnoticeid);
        for(WmTmsNoticeIEntity t: listWaveToDowns){
			try{
				t.setGoodsQua( (int) Math.round(Double.parseDouble(t.getGoodsQua()))+"");
				t.setBaseGoodscount( (int) Math.round(Double.parseDouble(t.getBaseGoodscount()))+"");
				t.setBaseUnit("份");
				t.setGoodsUnit("份");
			}catch (Exception e){
			}
		}
//		tout.setPiClass( (int) Math.round(Double.parseDouble(dt2.getBaseGoodscount()))  + "份");

		D0.setObj(listWaveToDowns);
        try{
			System.out.println("/listdetail/songhuolistWaveToDowns==="+listWaveToDowns.get(0).toString()+listWaveToDowns.size());

		}catch (Exception e){

		}

		return new ResponseEntity(D0, HttpStatus.OK);
    }


    @RequestMapping(value = "/update/songhuo",  method = RequestMethod.GET)   //总订单
    @ResponseBody
    public ResponseEntity<?> updatesonghuo(
            @RequestParam(value="omnoticeid", required=false)String omnoticeid,
			@RequestParam(value="remark", required=false)String remark) {
        ResultDO D0 = new  ResultDO();
        D0.setOK(true);
        String hql="from WmTmsNoticeHEntity  where  omNoticeId = ?";
        List<WmTmsNoticeHEntity> listWaveToDowns =new ArrayList<>();
        listWaveToDowns = wmOmNoticeHService.findHql(hql,omnoticeid);

        for(WmTmsNoticeHEntity t: listWaveToDowns){
            t.setOmSta("已送货");
            wmOmNoticeHService.updateEntitie(t);
        }
        D0.setObj(listWaveToDowns);
        return new ResponseEntity(D0, HttpStatus.OK);
    }

	@RequestMapping(value = "/saveweizhi/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "保存位置", notes = "保存位置", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> saveweizhi(@ApiParam(required = true, name = "username", value = "username") @PathVariable("username") String username
			, HttpServletRequest request) {

		try{
			String jingdu = request.getParameter("jingdu");
			String weidu = request.getParameter("weidu");
			RfidBuseEntity rfidBuseEntity = new RfidBuseEntity();
			rfidBuseEntity.setRfidId1(username);
			rfidBuseEntity.setRfidId2(jingdu);
			rfidBuseEntity.setRfidId3(weidu);
			rfidBuseEntity.setCreateDate(DateTime.now());
			systemService.save(rfidBuseEntity);
			return Result.success(rfidBuseEntity);

		}catch (Exception e){
			return Result.error("保存失败");

		}

	}@RequestMapping(value = "/getweizhi/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取位置", notes = "获取位置", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> getweizhi(@ApiParam(required = true, name = "username", value = "username") @PathVariable("username") String username
			, HttpServletRequest request) {

		try{
            String hqlom = "from WmTmsNoticeHEntity where omBeizhu = ?";
            List<WmTmsNoticeHEntity> listom = systemService.findHql(hqlom,username);
            String sji = "siji";
            String orderstatus = "";

            try{
				orderstatus = listom.get(0).getOmSta();
			}catch (Exception e){

			}
			try{
				String hqlomh = "from WmOmNoticeHEntity where piClass like '%" +username+
						"%'";
				List<WmOmNoticeHEntity> listomh = systemService.findHql(hqlomh);
				if(StringUtil.isNotEmpty(orderstatus)){
					orderstatus = orderstatus+"-"+listomh.get(0).getOmSta();

				}else{
					orderstatus =  listomh.get(0).getOmSta();

				}
			}catch (Exception e){

			}
            try{
				sji = listom.get(0).getReMember();
			}catch (Exception e){

			}
			String hql = "from RfidBuseEntity where rfidId1 = ? order by createDate desc";
			List<RfidBuseEntity> lista = systemService.findHql(hql,sji);
			if(lista!=null&&lista.size()>0){
				RfidBuseEntity out = lista.get(0);
				try{

					TmsMdCheliangEntity tmsMdCheliangEntity = systemService.findUniqueByProperty(TmsMdCheliangEntity.class,"username",sji);
					out.setRfidBuseno(tmsMdCheliangEntity.getZhuangtai());
					out.setRfidBusecont(tmsMdCheliangEntity.getBeizhu());
					out.setRfidType(orderstatus);
				}catch (Exception e){

				}
				return Result.success(out);
			}else{
				return Result.error(orderstatus+"-暂无司机位置");

			}

		}catch (Exception e){
			return Result.error("保存失败");

		}

	}

	/**
	 *  取消派车
	 *
	 * @return
	 */
	@RequestMapping(params = "doDelpc")
	@ResponseBody
	public AjaxJson doDelpc(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
		message = "运输订单取消派车成功";
		try{
			tmsYwDingdan.setZhuangtai("已下单");
			tmsYwDingdanService.updateEntitie(tmsYwDingdan);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单取消派车失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 *  取消装车
	 *
	 * @return
	 */
	@RequestMapping(params = "dozc")
	@ResponseBody
	public AjaxJson dozc(String id, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		TmsYwDingdanEntity tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class, id);
		message = "运输订单到WMS成功";
		try{
			tmsYwDingdan.setZhuangtai("已装车");
			tmsYwDingdanService.updateEntitie(tmsYwDingdan);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单到WMS失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}



	/**
	 *  取消装车
	 *
	 * @return
	 */
	@RequestMapping(params = "doDelzc")
	@ResponseBody
	public AjaxJson doDelzc(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
		message = "运输订单取消装车成功";
		try{
			tmsYwDingdan.setZhuangtai("已派车");
			tmsYwDingdanService.updateEntitie(tmsYwDingdan);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单取消装车失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 *  取消回单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDelhd")
	@ResponseBody
	public AjaxJson doDelhd(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
		message = "运输订单取消回单成功";
		try{
			tmsYwDingdan.setZhuangtai("已装车");
			tmsYwDingdanService.updateEntitie(tmsYwDingdan);
//			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFuO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单取消回单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 *  取消派车
	 *
	 * @return
	 */
	@RequestMapping(params = "doDeljs")
	@ResponseBody
	public AjaxJson doDeljs(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
		message = "运输订单取消结算成功";
		try{
			tmsYwDingdan.setZhuangtai("已回单");
			tmsYwDingdanService.updateEntitie(tmsYwDingdan);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单取消结算失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除运输订单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "运输订单删除成功";
		try{
			for(String id:ids.split(",")){
				TmsYwDingdanEntity tmsYwDingdan = systemService.getEntity(TmsYwDingdanEntity.class,
				id
				);
				tmsYwDingdan.setZhuangtai("已删除");
				tmsYwDingdanService.updateEntitie(tmsYwDingdan);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加运输订单
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "运输订单添加成功";
		try{
			Map<String, Object> countMap = systemService
					.findOneForJdbc("SELECT count(*)+1 as count FROM tms_yw_dingdan  t where  TO_DAYS(t.create_date) = TO_DAYS(NOW());");
			String noticeid = null;
			if (countMap != null) {

				noticeid = DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd)
													+ StringUtil.leftPad(
							((Long) countMap.get("count")).intValue(), 4,
							'0');
			}
			tmsYwDingdan.setFadh(noticeid);
//			try{
//				if(StringUtil.isEmpty(tmsYwDingdan.getUsername())){
//					TmsMdDzEntity tmsdz = new TmsMdDzEntity();
//					tmsdz.setUsername(ResourceUtil.getSessionUser().getUserName());
//					tmsdz.setLianxiren(tmsYwDingdan.getFahuoren());
//					tmsdz.setDianhua(tmsYwDingdan.getFhrdh());
//					tmsdz.setXiangxidizhi(tmsYwDingdan.getFhrdz());
//					systemService.save(tmsdz);
//					TmsMdDzEntity tmsdzs = new TmsMdDzEntity();
//					tmsdzs.setUsername(ResourceUtil.getSessionUser().getUserName());
//					tmsdzs.setLianxiren(tmsYwDingdan.getShouhuoren());
//					tmsdzs.setDianhua(tmsYwDingdan.getShrsj());
//					tmsdzs.setXiangxidizhi(tmsYwDingdan.getShrdh());
//					systemService.save(tmsdzs);
//				}
//			}catch (Exception e){
//
//			}
			try{
			if(StringUtil.isNotEmpty(tmsYwDingdan.getFahuoren())){
				String sfhr[];
				sfhr = StringUtil.splitString(tmsYwDingdan.getFahuoren(),"-");
				if(sfhr.length==4){
					tmsYwDingdan.setUsername(sfhr[0]);
					tmsYwDingdan.setFahuoren(sfhr[1]);
					tmsYwDingdan.setFhrdh(sfhr[2]);
					tmsYwDingdan.setFhrdz(sfhr[3]);
				}
				if(sfhr.length==3){

					tmsYwDingdan.setFahuoren(sfhr[0]);
					tmsYwDingdan.setFhrdh(sfhr[1]);
					tmsYwDingdan.setFhrdz(sfhr[2]);
					TmsMdDzEntity tmsdz = new TmsMdDzEntity();
					tmsdz.setUsername(ResourceUtil.getSessionUser().getUserName());
					tmsdz.setLianxiren(tmsYwDingdan.getFahuoren());
					tmsdz.setDianhua(tmsYwDingdan.getFhrdh());
					tmsdz.setXiangxidizhi(tmsYwDingdan.getFhrdz());
					systemService.save(tmsdz);
				}
			}
			}catch (Exception e){

			}
			try{
			if(StringUtil.isNotEmpty(tmsYwDingdan.getShouhuoren())){
				String sshr[];
				sshr = StringUtil.splitString(tmsYwDingdan.getShouhuoren(),"-");
				if(sshr.length==4){
					tmsYwDingdan.setShouhuoren(sshr[1]);
					tmsYwDingdan.setShrsj(sshr[2]);
					tmsYwDingdan.setShrdh(sshr[3]);
				}
				if(sshr.length==3){
					tmsYwDingdan.setShouhuoren(sshr[0]);
					tmsYwDingdan.setShrsj(sshr[1]);
					tmsYwDingdan.setShrdh(sshr[2]);
					TmsMdDzEntity tmsdzs = new TmsMdDzEntity();
					tmsdzs.setUsername(ResourceUtil.getSessionUser().getUserName());
					tmsdzs.setLianxiren(tmsYwDingdan.getShouhuoren());
					tmsdzs.setDianhua(tmsYwDingdan.getShrsj());
					tmsdzs.setXiangxidizhi(tmsYwDingdan.getShrdh());
					systemService.save(tmsdzs);
				}
			}
		}catch (Exception e){

			}
			GregorianCalendar ca = new GregorianCalendar();
			if(ca.get(GregorianCalendar.AM_PM) == 0){
				tmsYwDingdan.setBy1("上午");
			}else{
				tmsYwDingdan.setBy1("下午");
			}
			tmsYwDingdanService.save(tmsYwDingdan);

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "运输订单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(tmsYwDingdan);
		return j;
	}

	/**
	 * 更新运输订单
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "运输订单更新成功";
		TmsYwDingdanEntity t = tmsYwDingdanService.get(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tmsYwDingdan, t);
			tmsYwDingdanService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "运输订单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 运输订单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsYwDingdan.getId())) {
			tmsYwDingdan = tmsYwDingdanService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
//			req.setAttribute("tmsYwDingdanPage", tmsYwDingdan);
		}
		TSUser user = ResourceUtil.getSessionUser();

		if(!StringUtil.isEmpty(user.getUserType())){
			if(user.getUserType().equals("4")){
				tmsYwDingdan.setUsername(user.getUserName());
				tmsYwDingdan.setXdrmz(user.getRealName());
			}

		}

		req.setAttribute("tmsYwDingdanPage", tmsYwDingdan);

		return new ModelAndView("com/zzjee/tms/tmsYwDingdan-add");
	}
	/**
	 * 运输订单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TmsYwDingdanEntity tmsYwDingdan, String id, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsYwDingdan.getId())|| StringUtil.isNotEmpty(id)){
			if(StringUtil.isNotEmpty(id)){
				tmsYwDingdan.setId(id);
			}
			tmsYwDingdan = tmsYwDingdanService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
			req.setAttribute("tmsYwDingdanPage", tmsYwDingdan);
		}
		return new ModelAndView("com/zzjee/tms/tmsYwDingdan-update");
	}

	@RequestMapping(params = "goUpdatehd")
	public ModelAndView goUpdatehd(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsYwDingdan.getId())) {
			tmsYwDingdan = tmsYwDingdanService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
//			tmsYwDingdan.setZhuangtai("已回单");
			req.setAttribute("tmsYwDingdanPage", tmsYwDingdan);
		}
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanhd-update");
	}
	@RequestMapping(params = "goUpdatejs")
	public ModelAndView goUpdatejs(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tmsYwDingdan.getId())) {
			tmsYwDingdan = tmsYwDingdanService.getEntity(TmsYwDingdanEntity.class, tmsYwDingdan.getId());
//			tmsYwDingdan.setZhuangtai("已回单");
			req.setAttribute("tmsYwDingdanPage", tmsYwDingdan);
		}
		return new ModelAndView("com/zzjee/tms/tmsYwDingdanjs-update");
	}
	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tmsYwDingdanController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TmsYwDingdanEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tmsYwDingdan, request.getParameterMap());
		List<TmsYwDingdanEntity> tmsYwDingdans = this.tmsYwDingdanService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"运输订单");
		modelMap.put(NormalExcelConstants.CLASS,TmsYwDingdanEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("运输订单列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tmsYwDingdans);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TmsYwDingdanEntity tmsYwDingdan, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"运输订单");
    	modelMap.put(NormalExcelConstants.CLASS,TmsYwDingdanEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("运输订单列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
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
				List<TmsYwDingdanEntity> listTmsYwDingdanEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TmsYwDingdanEntity.class,params);
				for (TmsYwDingdanEntity tmsYwDingdan : listTmsYwDingdanEntitys) {
					tmsYwDingdanService.save(tmsYwDingdan);
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
	 * 获取文件附件信息
	 *
	 * @param id tmsYwDingdan主键id
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
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="运输订单列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TmsYwDingdanEntity>> list() {
		List<TmsYwDingdanEntity> listTmsYwDingdans=tmsYwDingdanService.getList(TmsYwDingdanEntity.class);
		return Result.success(listTmsYwDingdans);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取运输订单信息",notes="根据ID获取运输订单信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		TmsYwDingdanEntity task = tmsYwDingdanService.get(TmsYwDingdanEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取运输订单信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建运输订单")
	public ResponseMessage<?> create(@ApiParam(name="运输订单对象")@RequestBody TmsYwDingdanEntity tmsYwDingdan, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TmsYwDingdanEntity>> failures = validator.validate(tmsYwDingdan);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			tmsYwDingdanService.save(tmsYwDingdan);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("运输订单信息保存失败");
		}
		return Result.success(tmsYwDingdan);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新运输订单",notes="更新运输订单")
	public ResponseMessage<?> update(@ApiParam(name="运输订单对象")@RequestBody TmsYwDingdanEntity tmsYwDingdan) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TmsYwDingdanEntity>> failures = validator.validate(tmsYwDingdan);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			tmsYwDingdanService.saveOrUpdate(tmsYwDingdan);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新运输订单信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新运输订单信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除运输订单")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" + id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			tmsYwDingdanService.deleteEntityById(TmsYwDingdanEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("运输订单删除失败");
		}

		return Result.success();
	}
}
