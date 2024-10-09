package com.zzjee.api;


import static com.xiaoleilu.hutool.date.DateTime.now;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.rfid.entity.RfidBuseEntity;
import com.zzjee.wave.entity.WaveToDownEntity;
import com.zzjee.wave.entity.WaveToFjEntity;
import com.zzjee.wm.entity.*;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.web.system.pojo.base.TSNotice;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzjee.wm.page.WmOmNoticeHPage;
import com.zzjee.wmutil.wmUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 获取和删除token的请求地址，
 * 在Restful设计中其实就对应着登录和退出登录的资源映射
 *
 * @author scott
 * @date 2015/7/30.
 */
@Controller
@RequestMapping("/wmom")
public class
wmomController {
	private static final Logger logger = Logger.getLogger(wmomController.class);
//	@Autowired
//	private UserService userService;

	@Autowired
	SystemService systemService;


	@RequestMapping(value = "/alllist/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取全部订单列表信息", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<List<WmOmNoticeHPage>> alllist(@PathVariable("username") String username, @RequestParam int pageNumber, @RequestParam int pageSize, HttpServletRequest request) {
		CriteriaQuery query = new CriteriaQuery(WmOmNoticeHEntity.class);
		try {

			if(StringUtil.isNotEmpty(request.getParameter("omNoticeId"))){
				query.like("omNoticeId","%"+request.getParameter("omNoticeId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("delvMobile"))){
				query.like("delvMobile","%"+request.getParameter("delvMobile")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("delvMember"))){
				query.like("delvMember","%"+request.getParameter("delvMember")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("delvAddr"))){
				query.like("delvAddr","%"+request.getParameter("delvAddr")+"%");
			}
//			String orgcode = "";
			TSUser task = wmUtil.getsysorgcode(username);
			if (task != null) {
				query.like("reCarno", "%" + task.getUserName() + "%");
			}
		} catch (Exception e) {
		}
		query.add();
		List<WmOmNoticeHPage> listsize = this.systemService.getListByCriteriaQuery(query, false);
		query.setCurPage(pageNumber <= 0 ? 1 : pageNumber);
		query.setPageSize(pageSize < 1 ? 1 : pageSize);
		query.add();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createDate", "desc");
		query.setOrder(map);
		List<WmOmNoticeHPage> list = this.systemService.getListByCriteriaQuery(query, true);
		return Result.success(list, (long) listsize.size());
	}


	@RequestMapping(value = "/list/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取未完成订单列表信息", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<List<WmOmNoticeHPage>> list(@PathVariable("username") String username, @RequestParam int pageNumber, @RequestParam int pageSize, HttpServletRequest request) {
		CriteriaQuery query = new CriteriaQuery(WmOmNoticeHEntity.class);
		try {
			query.eq("omSta", Constants.wm_sta6);

			if(StringUtil.isNotEmpty(request.getParameter("omNoticeId"))){
				query.like("omNoticeId","%"+request.getParameter("omNoticeId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("delvMobile"))){
				query.like("delvMobile","%"+request.getParameter("delvMobile")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("delvMember"))){
				query.like("delvMember","%"+request.getParameter("delvMember")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("delvAddr"))){
				query.like("delvAddr","%"+request.getParameter("delvAddr")+"%");
			}
//			String orgcode = "";
			TSUser task = wmUtil.getsysorgcode(username);
			if (task != null) {
				query.like("reCarno", "%" + task.getUserName() + "%");
			}
		} catch (Exception e) {
		}
		query.add();
		List<WmOmNoticeHPage> listsize = this.systemService.getListByCriteriaQuery(query, false);
		query.setCurPage(pageNumber <= 0 ? 1 : pageNumber);
		query.setPageSize(pageSize < 1 ? 1 : pageSize);
		query.add();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createDate", "desc");
		query.setOrder(map);
		List<WmOmNoticeHPage> list = this.systemService.getListByCriteriaQuery(query, true);
		return Result.success(list, (long) listsize.size());
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据ID获取订单信息", notes = "根据ID获取订单信息", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
		WmOmNoticeHEntity task = null;
		try {
			task = systemService.get(WmOmNoticeHEntity.class, id);

		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		if (task == null) {
			try {
				task = systemService.findUniqueByProperty(WmOmNoticeHEntity.class, "omNoticeId", id);

			} catch (Exception e) {
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}
			if (task == null) {
				return Result.error("根据ID获取订单信息为空");
			}
		}
		WmOmNoticeHPage page = new WmOmNoticeHPage();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(task, page);
			Object orderNo0 = task.getOmNoticeId();
			String hql0 = "from WmOmNoticeIEntity where 1 = 1 AND omNoticeId = ? ";
			List<WmOmNoticeIEntity> WmOmNoticeIEntityList = this.systemService.findHql(hql0, orderNo0);
			page.setWmOmNoticeIList(WmOmNoticeIEntityList);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return Result.success(page);
	}


	@RequestMapping(value = "/getbin/{barcode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据barcode获取上架储位息", notes = "根据barcode获取上架储位息", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> getshangjiachuawei(@ApiParam(required = true, name = "barcode", value = "barcode") @PathVariable("barcode") String barcode
			, HttpServletRequest request) {
		String Hql = "from WmInQmIEntity where tinId =  ?  and binSta = ?";
		List<WmInQmIEntity>  listwmin = systemService.findHql(Hql,barcode,"N");

		if(listwmin==null){
			return	Result.error("不存在的托盘");
		}
		if(listwmin!=null&&listwmin.size()==0){
			return	Result.error("不存在的托盘");
		}
		if(listwmin!=null&&listwmin.size()>1){
			return	Result.error("存在重复的托盘号");
		}
		try{
			String weight = request.getParameter("weight");
			String volumn = request.getParameter("volumn");
			RfidBuseEntity rfidBuseEntity = new RfidBuseEntity();
			rfidBuseEntity.setRfidId1(barcode);
			rfidBuseEntity.setRfidId2(weight);
			rfidBuseEntity.setRfidId3(volumn);
			rfidBuseEntity.setCreateDate(now());
			systemService.save(rfidBuseEntity);
		}catch (Exception e){

		}

		return Result.success(listwmin.get(0));
	}
	@RequestMapping(value = "/toup/{barcode}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据barcode获取上架储位息", notes = "根据barcode获取上架储位息", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> toupapi(@ApiParam(required = true, name = "barcode", value = "barcode") @PathVariable("barcode") String barcode) {
		String Hql = "from WmInQmIEntity where tinId =  ?  and binSta = ?";
		List<WmInQmIEntity>  listwmin = systemService.findHql(Hql,barcode,"N");

		if(listwmin==null){
			return	Result.error("不存在的托盘");
		}
		if(listwmin!=null&&listwmin.size()>1){
			return	Result.error("存在重复的托盘号");
		}
		if(listwmin!=null&&listwmin.size()==0){
			return	Result.error("不存在的托盘");
		}
  		boolean toup = toup(listwmin.get(0).getId());
        if(!toup){
			Result.error("上架失败");
		}
		return Result.success("上架成功");
	}

	@RequestMapping(value = "/getxiajia", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取下架列表", notes = "获取下架列表", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> getxiajia() {
		String Hql = "from WmOmQmIEntity where   binSta = ?";
		List<WmOmQmIEntity>  listwmom = systemService.findHql(Hql,"N");

		if(listwmom==null){
			return	Result.error("不存在数据");
		}

		if(listwmom!=null&&listwmom.size()==0){
			return	Result.error("不存在数据");
		}

		List<WmOmQmIEntity>  listwmomnew = new ArrayList<>();
		for( WmOmQmIEntity t:  listwmom ){
			if(StringUtil.isNotEmpty(t.getBinId())){
				try{
					String chuhuokou = "";
					MdBinEntity mdBinEntity = systemService.findUniqueByProperty(MdBinEntity.class,"kuWeiBianMa",t.getBinId());
					chuhuokou = mdBinEntity.getMianJiDanWei();
					t.setRecDeg(chuhuokou);
					t.setPickNotice(mdBinEntity.getQuHuoCiXu());
				}catch (Exception e){
				}
			}
			listwmomnew.add(t);
		}


		//第一个写法
		Collections.sort(listwmomnew, new Comparator<WmOmQmIEntity>() {
			@Override
			public int compare(WmOmQmIEntity o1, WmOmQmIEntity o2) {
				return o2.getPickNotice().compareTo(o1.getPickNotice());
			}
		});

		return Result.success(listwmomnew);
	}
	@RequestMapping(value = "/getstatus", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取储位状态列表", notes = "获取储位状态列表", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> getstatus() {
		String Hql = "from RfidBuseEntity where   rfidType = ? and bpmStatus = ?";
		List<RfidBuseEntity>  listwmom = systemService.findHql(Hql,"CW","1");

		if(listwmom==null){
			return	Result.error("不存在数据");
		}

		if(listwmom!=null&&listwmom.size()==0){
			return	Result.error("不存在数据");
		}


		return Result.success(listwmom);
	}

	@RequestMapping(value = "/setxiajia/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "设置下架", notes = "设置下架", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> setxiajia(@ApiParam(required = true, name = "id", value = "id") @PathVariable("id") String id) {
		String Hql = "from WmOmQmIEntity where id =  ? and  binSta =  ? ";
		List<WmOmQmIEntity>  listxiajia = systemService.findHql(Hql,id,"N");
		if(listxiajia==null){
			return	Result.error("不存在下架");
		}
		if(listxiajia!=null&&listxiajia.size()==0){
			return	Result.error("不存在下架");
		}
		for(WmOmQmIEntity wmOmQmI: listxiajia){
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
			wmToDownGoods.setCreateDate(now());
			systemService.save(wmToDownGoods);
			wmOmQmI.setBinSta("Y");
			systemService.saveOrUpdate(wmOmQmI);
		}
		return Result.success("下架成功");
	}
	@RequestMapping(value = "/getmove", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取移动列表", notes = "获取移动列表", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> getmove() {
		String Hql = "from WmToMoveGoodsEntity where moveSta =  ? ";
		List<WmToMoveGoodsEntity>  listmove = systemService.findHql(Hql,"已确认");
		if(listmove==null){
			return	Result.error("不存在移动");
		}
		if(listmove!=null&&listmove.size()==0){
			return	Result.error("不存在移动");
		}
		List<WmToMoveGoodsEntity>  listmovenew = new ArrayList<>();
		//第一个写法
		for( WmToMoveGoodsEntity t:  listmove ){
			if(StringUtil.isNotEmpty(t.getBinFrom())){
				try{
					String chuhuokou = "";
					MdBinEntity mdBinEntity = systemService.findUniqueByProperty(MdBinEntity.class,"kuWeiBianMa",t.getBinFrom());
  					t.setToCusName(mdBinEntity.getQuHuoCiXu());
				}catch (Exception e){
				}
			}
			listmovenew.add(t);
		}
		Collections.sort(listmovenew, new Comparator<WmToMoveGoodsEntity>() {
			@Override
			public int compare(WmToMoveGoodsEntity o1, WmToMoveGoodsEntity o2) {
				return o2.getToCusName().compareTo(o1.getToCusName());
			}
		});
		return Result.success(listmovenew);
	}
	@RequestMapping(value = "/setmove/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "设置移动", notes = "设置移动", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> setmove(@ApiParam(required = true, name = "id", value = "id") @PathVariable("id") String id) {
		String Hql = "from WmToMoveGoodsEntity where id =  ? and  moveSta =  ? ";
		List<WmToMoveGoodsEntity>  listmove = systemService.findHql(Hql,id,"已确认");
		if(listmove==null){
			return	Result.error("不存在移动");
		}
		if(listmove!=null&&listmove.size()==0){
			return	Result.error("不存在移动");
		}
		for(WmToMoveGoodsEntity t:  listmove){
			t.setMoveSta("已完成");
			systemService.updateEntitie(t);
		}
		return Result.success("移动成功");
	}

	private boolean toup(String id ){
//		List<WmToUpGoodsEntity> wmToUpGoodsList = new ArrayList<WmToUpGoodsEntity>();
		String hql0 = "from WmInQmIEntity where binSta = 'N' and  id = ?";
		List<WmInQmIEntity> WmInQmIEntityList = systemService.findHql(hql0,
				id);// 获取行项目
		for (WmInQmIEntity wmInQmIEntity : WmInQmIEntityList) {

			try{
				WmToUpGoodsEntity wmToUpGoodsEntityold = systemService.findUniqueByProperty(WmToUpGoodsEntity.class,"orderIdI",wmInQmIEntity.getId());
				if (wmToUpGoodsEntityold!=null){
					continue;
				}
			}catch (Exception e){

			}

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
			wmToUpGoodsEntity.setWmToUpId(wmInQmIEntity.getId());
			wmToUpGoodsEntity.setCreateDate(now());
//			String sql = "select     md.suo_shu_ke_hu as cuscode from    md_bin md  where    md.ku_wei_bian_ma = '"
//					+ wmInQmIEntity.getBinId() + "'";
//			Map<String, Object> binMap = systemService.findOneForJdbc(sql);
			if (!wmUtil.checkbin(wmInQmIEntity.getBinId())) {
				return false;
			}

			try {

//				MvGoodsEntity mvgoods = new MvGoodsEntity();
				MvGoodsEntity mvgoods = systemService.findUniqueByProperty(
						MvGoodsEntity.class, "goodsCode",
						wmToUpGoodsEntity.getGoodsId());
				wmToUpGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
				wmToUpGoodsEntity.setGoodsUnit(mvgoods.getShlDanWei());

				if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
					try {
						wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(
								Double.parseDouble(mvgoods.getChlShl())
										* Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
					} catch (Exception e) {
						// TODO: handle exception
					}

				} else {
					wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity
							.getGoodsQua());
				}


			} catch (Exception e) {

			}


			wmInQmIEntity.setBinSta("Y");
			systemService.saveOrUpdate(wmInQmIEntity);
			//重复增加二次判断
			List<WmToUpGoodsEntity> listall = systemService.findByProperty(WmToUpGoodsEntity.class,"orderIdI",wmToUpGoodsEntity.getOrderIdI());
			if(listall!=null&&listall.size()>0){
				return true;
			}
			//重复增加二次判断

			systemService.save(wmToUpGoodsEntity);
		}
		return true;
	}
	@RequestMapping(value = "/goodsdownlist/{orderNo}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "订单出库商品信息", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<List<WmToDownGoodsEntity>> filelist(@PathVariable("orderNo") String orderNo, HttpServletRequest request) {
		String hql0 = "from WmToDownGoodsEntity where 1 = 1 AND orderId = ? ";
		List<WmToDownGoodsEntity> wmToDownGoodsEntitiesList = this.systemService.findHql(hql0, orderNo);
		return Result.success(wmToDownGoodsEntitiesList);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "回单订单", notes = "回单订单")
	public ResponseMessage<?> update(@RequestBody WmOmNoticeHPage wmOmNoticeHPage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		WmOmNoticeHEntity t = systemService.get(WmOmNoticeHEntity.class, wmOmNoticeHPage.getId());
		WmNoticeConfEntity confe = new WmNoticeConfEntity();
		confe.setBeizhu(wmOmNoticeHPage.getOmBeizhu());
		confe.setCreateBy(wmOmNoticeHPage.getCreateBy());
		confe.setHdData(now());
		confe.setCusCode(t.getCusCode());
		confe.setWmNoticeId(t.getOmNoticeId());
		systemService.save(confe);
		return Result.success("回单成功");
	}

	@RequestMapping(value="/getNotice/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="获取公告",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<TSNotice>> getNotice(@PathVariable("username") String username, HttpServletRequest request) {

		List<TSNotice> list = new ArrayList<>();
		TSUser task = wmUtil.getsysorgcode(username);
		List<Map<String, Object>> resultList2 = null;
//		String orgcode = "";
		//Integer isRead = null;
		if (task != null) {
			try {

				String sql = "SELECT notice.*,noticeRead.is_read as is_read FROM t_s_notice notice "
						+ "LEFT JOIN t_s_notice_read_user noticeRead ON  notice.id = noticeRead.notice_id "
						+ "WHERE noticeRead.del_flag = 0 and noticeRead.user_id = ? ";
				sql += " and noticeRead.is_read = ? ";
				sql += " ORDER BY noticeRead.create_time DESC ";
//				if (isRead == null || !(isRead == 1 || isRead == 0)) {
//					isRead = 0;
//				}
				List<Map<String, Object>> noticeList = systemService.findForJdbcParam(sql, 1, 10, task.getId(),Integer.valueOf(0) );//isRead.intValue()

				//将List转换成JSON存储
//				net.sf.json.JSONArray result = new net.sf.json.JSONArray();
				if (noticeList != null && noticeList.size() > 0) {
					for (int i = 0; i < noticeList.size(); i++) {
						TSNotice tsNotice = new TSNotice();
						tsNotice.setId(noticeList.get(i).get("id").toString());
						tsNotice.setNoticeTitle(noticeList.get(i).get("notice_title").toString());
						tsNotice.setNoticeContent(noticeList.get(i).get("notice_content").toString());
						list.add(tsNotice);
					}
				}
				//获取通知公告总数
				String sql2 = "SELECT count(notice.id) as count FROM t_s_notice notice "
						+ "LEFT JOIN t_s_notice_read_user noticeRead ON  notice.id = noticeRead.notice_id "
						+ "WHERE noticeRead.del_flag = 0 and noticeRead.user_id = ? "
						+ "and noticeRead.is_read = 0";
				resultList2 = systemService.findForJdbc(sql2, task.getId());


			}
			catch (Exception e){
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}
		}
		return Result.success(list, Long.parseLong(resultList2.get(0).get("count").toString()));

	}

	@RequestMapping(value = "/listwavedown/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取波次下架信息", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<List<WaveToDownEntity>> listwavedown(@PathVariable("username") String username,
																@RequestParam int pageNumber, @RequestParam int pageSize,
																HttpServletRequest request) {
		CriteriaQuery query = new CriteriaQuery(WaveToDownEntity.class);
		try {

			if(StringUtil.isNotEmpty(request.getParameter("binId"))){
				query.like("binId","%"+request.getParameter("binId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("goodsId"))){
				query.like("goodsId","%"+request.getParameter("goodsId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("waveId"))){
				query.like("waveId","%"+request.getParameter("waveId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("firstRq"))){
				query.like("firstRq","%"+request.getParameter("firstRq")+"%");
			}

		} catch (Exception e) {
		}
		query.add();
		List<WaveToDownEntity> listsize = this.systemService.getListByCriteriaQuery(query, false);
		query.setCurPage(pageNumber <= 0 ? 1 : pageNumber);
		query.setPageSize(pageSize < 1 ? 1 : pageSize);
		query.add();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("binId", "asc");
		query.setOrder(map);
		List<WaveToDownEntity> list = this.systemService.getListByCriteriaQuery(query, true);
		return Result.success(list, (long) listsize.size());
	}

	@RequestMapping(value = "/listwavefj/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取波次分拣信息", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<List<WaveToFjEntity>> listwavefj(@PathVariable("username") String username,
																@RequestParam int pageNumber, @RequestParam int pageSize,
																HttpServletRequest request) {
		CriteriaQuery query = new CriteriaQuery(WaveToFjEntity.class);
		try {

			if(StringUtil.isNotEmpty(request.getParameter("binId"))){
				query.like("binId","%"+request.getParameter("binId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("goodsId"))){
				query.like("goodsId","%"+request.getParameter("goodsId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("waveId"))){
				query.like("waveId","%"+request.getParameter("waveId")+"%");
			}
			if(StringUtil.isNotEmpty(request.getParameter("firstRq"))){
				query.like("firstRq","%"+request.getParameter("firstRq")+"%");
			}

		} catch (Exception e) {
		}
		query.add();
		List<WaveToFjEntity> listsize = this.systemService.getListByCriteriaQuery(query, false);
		query.setCurPage(pageNumber <= 0 ? 1 : pageNumber);
		query.setPageSize(pageSize < 1 ? 1 : pageSize);
		query.add();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("binId", "asc");
		query.setOrder(map);
		List<WaveToFjEntity> list = this.systemService.getListByCriteriaQuery(query, true);
		return Result.success(list, (long) listsize.size());
	}
	@RequestMapping(value = "/listwavedownsave/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取波次下架信息保存", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<?> listwavedownsave(@PathVariable("username") String username,
																@RequestParam String ids, @RequestParam String savestr1,
																HttpServletRequest request) {
		return Result.success("波次下架保存成功");
	}

	@RequestMapping(value = "/listwavefjsave/{username}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取波次分拣信息保存", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<?> listwavefjsave(@PathVariable("username") String username,
																@RequestParam String ids, @RequestParam String savestr1,
															HttpServletRequest request) {
		return Result.success("波次分拣保存成功");
	}
	@RequestMapping(value = "/rfidsave/{username}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "RFID保存", produces = "application/json", httpMethod = "POST")
	public ResponseMessage<?> rfid_save(@PathVariable("username") String username,
										@RequestBody wmientity wmientityin,
											 HttpServletRequest request) {
		List<wmi1entity> wmi1entityList = wmientityin.getWmi1List();
		String rfidType  = wmientityin.getWmX1();
		String rfidbuseno  = wmientityin.getWmX2();
		String rfidbusecon  = wmientityin.getWmX3();
		List<RfidBuseEntity>  rflist = new ArrayList<>();
		for(wmi1entity t: wmi1entityList){
			RfidBuseEntity rf = new RfidBuseEntity();
			rf.setCreateBy(username);
			rf.setCreateDate(now());
			rf.setRfidType(rfidType);
			rf.setRfidBuseno(rfidbuseno);
			rf.setRfidBusecont(rfidbusecon);
			rf.setRfidId1(t.getWmX1());
			rf.setRfidId2(t.getWmX2());
			rf.setRfidId3(t.getWmX3());
			rflist.add(rf);

		}
		try{
			systemService.batchSave(rflist);
		}catch (Exception e){
			Result.error("RFID保存失败");
		}
		return Result.success("RFID保存成功");
	}

	@RequestMapping(value = "/weightsave/{username}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "weight保存", produces = "application/json", httpMethod = "POST")
	public synchronized ResponseMessage<?> weight_save(@PathVariable("username") String username,
										  @RequestParam(value="searchstr", required=false)String searchstr ) {


			RfidBuseEntity rf = new RfidBuseEntity();
			rf.setCreateBy(username);
		rf.setCreateDate(now());
			rf.setRfidType("weight");
			rf.setRfidBuseno("1");
			Double d = Double.parseDouble(searchstr)/100;
			rf.setRfidBusecont(d.toString());


		try{
			systemService.save(rf);
		}catch (Exception e){
			Result.error("保存失败");
		}
		return Result.success("保存成功");
	}

}
