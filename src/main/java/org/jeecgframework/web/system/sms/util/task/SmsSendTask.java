package org.jeecgframework.web.system.sms.util.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.service.TSSmsServiceI;
import com.zzjee.wmutil.wmUtil;

import com.zzjee.md.entity.MdBinEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;
import com.zzjee.wm.entity.WmInQmIEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;
import com.zzjee.wm.entity.WmOmQmIEntity;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import com.zzjee.wm.entity.WmToMoveGoodsEntity;
import com.zzjee.wm.entity.WmToUpGoodsEntity;

/**
 *
 * @ClassName:SmsSendTask 所有信息的发送定时任务类
 * @Description: TODO
 * @author Comsys-skyCc cmzcheng@gmail.com
 * @date 2014-11-13 下午5:06:34
 *
 */
@Service("smsSendTask")
public class SmsSendTask {

	@Autowired
	private TSSmsServiceI tSSmsService;
	@Autowired
	private SystemService systemService;

	/* @Scheduled(cron="0 0/1 * * * ?") */
	public void run() {
		long start = System.currentTimeMillis();
		String run = ResourceUtil.getConfigByName("timerun");
		if(!run.equals("run")){
			return;
		}
		org.jeecgframework.core.util.LogUtil
				.info("===================消息中间件定时任务开始===================");
		try {//发送消息
			tSSmsService.send();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		String hql = null;
		try {//更新收货已完成
			List<WmImNoticeIEntity> WmInNoticeIlist = new ArrayList<WmImNoticeIEntity>();
			hql = "from WmImNoticeIEntity t where t.binPre<>?  ";
			WmInNoticeIlist = systemService.findHql(hql,  "Y" );
			for (WmImNoticeIEntity wmImNoticeIEntity : WmInNoticeIlist) {
				if (Long.parseLong(wmImNoticeIEntity.getGoodsCount()) <= Long
						.parseLong(wmImNoticeIEntity.getGoodsQmCount())) {
					wmImNoticeIEntity.setBinPre("Y");
					systemService.saveOrUpdate(wmImNoticeIEntity);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {//查找储位 设置收货登记基本数量
			List<WmInQmIEntity> WmInQmlist = new ArrayList<WmInQmIEntity>();
			hql = null;
			hql = "from WmInQmIEntity t where t.binSta=? and (t.binId is null or t.binId = '' )  ";

			WmInQmlist = systemService.findHql(hql, new Object[] { "N" });
			for (WmInQmIEntity wmInQmIEntity : WmInQmlist) {

				if(wmInQmIEntity.getImNoticeId().startsWith("YK")){//越库任务
					wmInQmIEntity.setBinId(wmInQmIEntity.getImNoticeId());
					wmInQmIEntity.setBinSta("Y");
					systemService.updateEntitie(wmInQmIEntity);
				}else{
					String sql = "select bin_id  as binid from wm_in_qm_i t where  t.tin_id =  '"
							+ wmInQmIEntity.getTinId()
							+ " ' and t.bin_sta = 'N' and  ( t.bin_id <> '' or t.bin_id <> null)  limit 1";
					Map<String, Object> binMap  = systemService.findOneForJdbc(sql);
					if(binMap==null){
						if(wmInQmIEntity.getTinTj()==null){
							wmInQmIEntity.setTinTj("0");
						}
						String zuidatiji = null;
						try {
							if(StringUtil.isEmpty( wmInQmIEntity.getTinTj())){
								zuidatiji = "1";
							}else{
								zuidatiji = wmInQmIEntity.getTinTj();
							}
						} catch (Exception e) {
							zuidatiji = "1";
						}

						sql = "select  binid     from          wv_avabin      where  ku_wei_lei_xing <> '不良品区'  and   zui_da_ti_ji >"
								+ zuidatiji
								+ "  and ku_wei_shu_xing = (select cf_wen_ceng from mv_goods  where goods_code =  '"
								+ wmInQmIEntity.getGoodsId()
								+ "')"
								+ "  and  locate( (select chp_shu_xing from mv_goods  where goods_code =  '"
								+ wmInQmIEntity.getGoodsId()
								+ "') ,chp_shu_xing ) <> 0 "
								+ " and (suo_shu_ke_hu = '' or suo_shu_ke_hu = '"
								+ wmInQmIEntity.getCusCode()
								+ "') "
								+ "order by suo_shu_ke_hu desc,  shang_jia_ci_xu,binid     limit 1";
						binMap = systemService.findOneForJdbc(sql);
					}

					if (binMap != null) {
						wmInQmIEntity.setBinId(binMap.get("binid").toString());
						systemService.updateEntitie(wmInQmIEntity);
					}
				}
			}
			//更新基本数量和单位
			hql = "from WmInQmIEntity  t where  t.baseUnit is null  ";
			WmInQmlist = systemService.findByQueryString(hql);
			for (WmInQmIEntity wmInQmIEntity : WmInQmlist) {
				MvGoodsEntity mvgoods = new MvGoodsEntity();
				mvgoods = systemService.findUniqueByProperty(
						MvGoodsEntity.class, "goodsCode",
						wmInQmIEntity.getGoodsId());
				wmInQmIEntity.setBaseUnit(mvgoods.getBaseunit());
				if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
					try {
						wmInQmIEntity.setBaseGoodscount(String.valueOf(Long
								.parseLong(mvgoods.getChlShl())
								* Long.parseLong(wmInQmIEntity.getQmOkQuat())));
					} catch (Exception e) {
						// TODO: handle exception
					}

					try {
						wmInQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
								.getZhlKg()) * Long.parseLong(wmInQmIEntity.getQmOkQuat())));
					} catch (Exception e) {
						// TODO: handle exception
					}

				} else {
					wmInQmIEntity
							.setBaseGoodscount(wmInQmIEntity.getQmOkQuat());
					try {
						wmInQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
								.getZhlKg())
								* Long.parseLong(wmInQmIEntity.getQmOkQuat())));
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
				if("no".equals(ResourceUtil.getConfigByName("scrqon"))){
					if(StringUtil.isNotEmpty(ResourceUtil.getConfigByName("scrq"))){
						wmInQmIEntity.setProData(ResourceUtil.getConfigByName("scrq"));
					}
				}

				if (StringUtil.isNotEmpty(wmUtil.getscrp())){
					wmInQmIEntity.setProData(wmUtil.getscrp());
				}
				systemService.saveOrUpdate(wmInQmIEntity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// 更新上架商品基本单位基本数量
			List<WmToUpGoodsEntity> WmToUpGoodslist = new ArrayList<WmToUpGoodsEntity>();
			hql = "from WmToUpGoodsEntity t where  t.baseUnit is null or  t.baseUnit = ''  ";
			WmToUpGoodslist = systemService.findByQueryString(hql);
			for (WmToUpGoodsEntity wmToUpGoodsEntity : WmToUpGoodslist) {
				MvGoodsEntity mvgoods = new MvGoodsEntity();
				try{
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
				systemService.saveOrUpdate(wmToUpGoodsEntity);
			}
			// 更新基本单位基本数量

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			// 更新下架商品基本单位基本数量
			List<WmToDownGoodsEntity> WmToDownGoodslist = new ArrayList<WmToDownGoodsEntity>();
			hql = "from WmToDownGoodsEntity t where  t.baseUnit is null  ";
			WmToDownGoodslist = systemService.findByQueryString(hql);
			for (WmToDownGoodsEntity wmToDownGoodsEntity : WmToDownGoodslist) {
				MvGoodsEntity mvgoods = new MvGoodsEntity();
				try{
					mvgoods = systemService.findUniqueByProperty(
							MvGoodsEntity.class, "goodsCode",
							wmToDownGoodsEntity.getGoodsId());
					wmToDownGoodsEntity.setGoodsUnit(mvgoods.getBaseunit());
					wmToDownGoodsEntity.setGoodsQua(wmToDownGoodsEntity.getGoodsQuaok());
					wmToDownGoodsEntity.setBaseGoodscount(wmToDownGoodsEntity.getGoodsQuaok());
					wmToDownGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
				}catch (Exception e){

				}
				systemService.saveOrUpdate(wmToDownGoodsEntity);
			}

			// 更新下架商品基本单位基本数量


		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// 更新库存转移
			List<WmToMoveGoodsEntity> WmToMoveGoodslist = new ArrayList<WmToMoveGoodsEntity>();
			hql = "from WmToMoveGoodsEntity t where  t.orderId is null and t.moveSta = '已完成' ";
			WmToMoveGoodslist = systemService.findByQueryString(hql);

			for (WmToMoveGoodsEntity wmToMoveGoodsEntity : WmToMoveGoodslist) {
				if(!wmUtil.checkstcok(wmToMoveGoodsEntity.getBinFrom(),wmToMoveGoodsEntity.getTinFrom(),wmToMoveGoodsEntity.getGoodsId(),wmToMoveGoodsEntity.getGoodsProData(),wmToMoveGoodsEntity.getGoodsQua())){
					wmToMoveGoodsEntity.setMoveSta("库存不足");
					wmToMoveGoodsEntity.setRunSta("库存不足");
					systemService.saveOrUpdate(wmToMoveGoodsEntity);
					continue;
				};
				MdBinEntity mdbin = systemService.findUniqueByProperty(MdBinEntity.class, "kuWeiBianMa", wmToMoveGoodsEntity.getBinTo());
				if(mdbin==null){
					wmToMoveGoodsEntity.setMoveSta("储位不存在");
					wmToMoveGoodsEntity.setRunSta("储位不存在");
					systemService.saveOrUpdate(wmToMoveGoodsEntity);
					continue;
				}
				wmToMoveGoodsEntity.setRunSta("已完成");

				WmToDownGoodsEntity wmToDownGoods = new WmToDownGoodsEntity();
				wmToDownGoods.setCreateBy(wmToMoveGoodsEntity.getCreateBy());
				wmToDownGoods.setCreateDate(wmToMoveGoodsEntity.getCreateDate());
				wmToDownGoods.setCreateName(wmToMoveGoodsEntity.getCreateName());
				wmToDownGoods.setBinIdFrom(wmToMoveGoodsEntity.getTinFrom());//下架托盘
				wmToDownGoods.setKuWeiBianMa(wmToMoveGoodsEntity.getBinFrom());//储位
				wmToDownGoods.setBinIdTo("ZY");//到托盘
				wmToDownGoods.setCusCode(wmToMoveGoodsEntity.getCusCode());//货主
				wmToDownGoods.setGoodsId(wmToMoveGoodsEntity.getGoodsId());//
				wmToDownGoods.setGoodsProData(wmToMoveGoodsEntity.getGoodsProData());//生产日期
				wmToDownGoods.setOrderId("ZY");//出货通知单
				if(StringUtil.isEmpty(wmToMoveGoodsEntity.getOrderIdI())){
					wmToDownGoods.setOrderIdI(wmToMoveGoodsEntity.getId());

				}else{
					wmToDownGoods.setOrderIdI(wmToMoveGoodsEntity.getOrderIdI());

				}
				MvGoodsEntity mvgoods = new MvGoodsEntity();
				mvgoods = systemService.findUniqueByProperty(
						MvGoodsEntity.class, "goodsCode",
						wmToMoveGoodsEntity.getGoodsId());
				wmToDownGoods.setGoodsName(mvgoods.getGoodsName());
				if(StringUtil.isEmpty(wmToMoveGoodsEntity.getBaseGoodscount())){
					wmToDownGoods.setBaseGoodscount(wmToMoveGoodsEntity.getGoodsQua());
				}else{
					wmToDownGoods.setBaseGoodscount(wmToMoveGoodsEntity.getBaseGoodscount());
				}
				wmToDownGoods.setBaseUnit(mvgoods.getBaseunit());//基本单位
				wmToDownGoods.setGoodsUnit(wmToMoveGoodsEntity.getGoodsUnit());//出货单位
				wmToDownGoods.setGoodsQua(wmToMoveGoodsEntity.getGoodsQua());//出货数量
				wmToDownGoods.setGoodsQuaok(wmToMoveGoodsEntity.getGoodsQua());//出货数量
				wmToDownGoods.setDownSta("已复核");
				systemService.save(wmToDownGoods);
				WmToUpGoodsEntity wmToUpGoodsEntity = new WmToUpGoodsEntity();
				wmToUpGoodsEntity.setCreateBy(wmToMoveGoodsEntity.getCreateBy());
				wmToUpGoodsEntity.setCreateDate(wmToMoveGoodsEntity.getCreateDate());
				wmToUpGoodsEntity.setCreateName(wmToMoveGoodsEntity.getCreateName());
				wmToUpGoodsEntity.setGoodsId(wmToMoveGoodsEntity.getGoodsId());
				if(StringUtil.isEmpty(wmToMoveGoodsEntity.getToGoodsProData())){
					wmToUpGoodsEntity.setGoodsProData(wmToMoveGoodsEntity.getGoodsProData());
				}else{
					wmToUpGoodsEntity.setGoodsProData(wmToMoveGoodsEntity.getToGoodsProData());
				}
				wmToUpGoodsEntity.setGoodsBatch(wmToMoveGoodsEntity.getGoodsProData());
				wmToUpGoodsEntity.setGoodsQua(wmToMoveGoodsEntity.getGoodsQua());
				wmToUpGoodsEntity.setGoodsUnit(wmToMoveGoodsEntity.getGoodsUnit());
				if(StringUtil.isEmpty(wmToMoveGoodsEntity.getBaseGoodscount())){
					wmToUpGoodsEntity.setBaseGoodscount(wmToMoveGoodsEntity.getGoodsQua());
				}else{
					wmToUpGoodsEntity.setBaseGoodscount(wmToMoveGoodsEntity.getBaseGoodscount());
				}
				wmToUpGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
				wmToUpGoodsEntity.setGoodsName(mvgoods.getGoodsName());
				if(StringUtil.isEmpty(wmToMoveGoodsEntity.getOrderIdI())){
					wmToUpGoodsEntity.setOrderIdI(wmToMoveGoodsEntity.getId());
				}else{
					wmToUpGoodsEntity.setOrderIdI(wmToMoveGoodsEntity.getOrderIdI());
				}
				wmToUpGoodsEntity.setOrderId("ZY");
				wmToUpGoodsEntity.setBinId(wmToMoveGoodsEntity.getTinId());
				wmToUpGoodsEntity.setKuWeiBianMa(wmToMoveGoodsEntity.getBinTo());
				wmToUpGoodsEntity.setCusCode(wmToMoveGoodsEntity.getToCusCode());
				wmToUpGoodsEntity.setActTypeCode("ZY");
				systemService.save(wmToUpGoodsEntity);
				if(!StringUtil.isNotEmpty(wmToMoveGoodsEntity.getOrderTypeCode())){
					wmToMoveGoodsEntity.setOrderTypeCode("KCZY");
				}
				wmToMoveGoodsEntity.setOrderId("ZY");
				systemService.saveOrUpdate(wmToMoveGoodsEntity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.rundowntask();//下架任务
		org.jeecgframework.core.util.LogUtil
				.info("===================消息中间件定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("总耗时" + times + "毫秒");
	}
	public  void rundowntask() {
		try {// 生成下架任务
			List<WmOmNoticeIEntity> WmOmNoticeIlist = new ArrayList<WmOmNoticeIEntity>();
			String			hql = "from WmOmNoticeIEntity t where  t.planSta = 'N' order by goodsId ,omNoticeId";
			WmOmNoticeIlist = systemService.findByQueryString(hql);
			String  usetuopan  = ResourceUtil.getConfigByName("usetuopan");
			String  prodate = null;
			String  tuopanma ="";
			String binom = "";
			if("no".equals(usetuopan)){
				tuopanma = ResourceUtil.getConfigByName("tuopanma");
			}
			for (WmOmNoticeIEntity wmOmNoticeIEntity : WmOmNoticeIlist) {
				binom ="";
				binom = wmOmNoticeIEntity.getBinOm();
				prodate = null;
				if(StringUtil.isNotEmpty(wmOmNoticeIEntity.getGoodsProData())){
					prodate= DateUtils.date2Str(wmOmNoticeIEntity.getGoodsProData(),DateUtils.date_sdf);
				}
				if(StringUtil.isNotEmpty(wmOmNoticeIEntity.getBinId())){
					tuopanma  = wmOmNoticeIEntity.getBinId();
				}else{
					if("no".equals(usetuopan)){
						tuopanma = ResourceUtil.getConfigByName("tuopanma");
					}else{
						tuopanma="";
					}
				}
				try {
					long omcount = 0;
					long omcountok = 0;
					long omcountwq = 0;
					List<WmOmQmIEntity> WmOmQmIlist = new ArrayList<WmOmQmIEntity>();
					hql = "from WmOmQmIEntity t where  t.iomNoticeItem = ?  ";
					WmOmQmIlist = systemService.findHql(hql,
							new Object[]{wmOmNoticeIEntity.getId()});
					for (WmOmQmIEntity wmOmQmIEntity : WmOmQmIlist) {
						try {
							omcountok = omcountok
									+ Long.parseLong(wmOmQmIEntity.getBaseGoodscount());
						} catch (Exception e) {

						}

					}
					omcount = Long.parseLong(wmOmNoticeIEntity.getBaseGoodscount());// 总出货数量
					omcountwq = omcount - omcountok;// 未清基本数量
					if (omcountwq > 0) {// 如果数量大于0
						WmOmQmIEntity wmOmQmIEntity = new WmOmQmIEntity();
						wmOmQmIEntity.setCreateDate(new Date());
						wmOmQmIEntity.setImCusCode(wmOmNoticeIEntity.getImCusCode());
						wmOmQmIEntity.setOmBeizhu(wmOmNoticeIEntity.getOmBeizhu());
						wmOmQmIEntity.setCreateBy(wmOmNoticeIEntity.getCreateBy());
						wmOmQmIEntity.setCreateName(wmOmNoticeIEntity.getCreateName());
						wmOmQmIEntity.setCusCode(wmOmNoticeIEntity.getCusCode());
						wmOmQmIEntity.setOmNoticeId(wmOmNoticeIEntity
								.getOmNoticeId());
						wmOmQmIEntity.setIomNoticeItem(wmOmNoticeIEntity.getId());
						wmOmQmIEntity.setBinSta("I");//预分配
						if (ResourceUtil.getConfigByName("autocon").equals("yes")) {
							wmOmQmIEntity.setBinSta("N");//预分配
						}
						MvGoodsEntity mvgoods = new MvGoodsEntity();
						String goods = null;
						try {
							if (wmOmNoticeIEntity.getGoodsId().endsWith("l")) {
								goods = wmOmNoticeIEntity.getGoodsId().substring(0, wmOmNoticeIEntity.getGoodsId().length() - 1);
								System.out.print("11111111I" + goods);
							} else {
								goods = wmOmNoticeIEntity.getGoodsId();
								System.out.print("22222" + goods);

							}
						} catch (Exception e) {

						}

						try {
							mvgoods = systemService.findUniqueByProperty(
									MvGoodsEntity.class, "goodsCode",
									goods);
							wmOmQmIEntity.setGoodsId(mvgoods.getGoodsId());
							wmOmQmIEntity.setBarCode(mvgoods.getShpTiaoMa());
							wmOmQmIEntity.setGoodsName(mvgoods.getGoodsName());
							wmOmQmIEntity.setBaoZhiq(mvgoods.getBzhiQi());
							wmOmQmIEntity
									.setGoodsUnit(mvgoods.getBaseunit());
						} catch (Exception e) {

						}

						String tsql = "select ws.base_unit,ws.zhong_wen_qch, ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
								+ "  from wv_stock ws, md_bin mb  where "
								+ "   ws.ku_wei_bian_ma = mb.ku_wei_bian_ma  and mb.ku_wei_lei_xing = '良品区' and mb.ting_yong <> 'Y' and (ws.kuctype = '库存' or ws.kuctype = '待下架')"
								;
						if(StringUtil.isNotEmpty(tuopanma)) {
							tsql = tsql + " and ws.bin_id = '"+tuopanma + "' ";
						}
						if(StringUtil.isNotEmpty(prodate)) {
							tsql = tsql + " and ws.goods_pro_data = '"+prodate + "' ";
						}

						if(StringUtil.isNotEmpty(binom)) {
							tsql = tsql + " and ws.ku_wei_bian_ma = '"+binom + "' ";
						}

						tsql = tsql + "   and ws.goods_id = ? "
								+ "   and ws.cus_code =  ? "
								+ "   group by ws.ku_wei_bian_ma,ws.bin_id,ws.goods_id,mb.qu_huo_ci_xu, ws.goods_pro_data  having sum(ws.base_goodscount) > 0 order by ws.goods_pro_data , ws.goods_qua ,mb.qu_huo_ci_xu,ws.create_date desc ";
						List<Map<String, Object>> resultt = new ArrayList<Map<String, Object>>();
						System.out.print(tsql);
						if(!"off".equals(ResourceUtil.getConfigByName("hiti"))) {
							//不启用HITI 此处不操作
							try {
								resultt = systemService
										.findForJdbc(tsql, mvgoods.getGoodsId(), wmOmQmIEntity.getCusCode());
							} catch (Exception e) {

							}
							if (resultt != null && resultt.size() > 0) {
								String goodprodata = null;
								try {
									goodprodata = resultt.get(0).get("goods_pro_data").toString();

								} catch (Exception e) {

								}
								String hiti = "0";
								try {
									hiti = Long.toString(Long.parseLong(mvgoods.getMpCengGao()) * Long.parseLong(mvgoods.getMpDanCeng()) * Long.parseLong(mvgoods.getChlShl()));
								} catch (Exception e) {
								}
								if (Long.parseLong(hiti) <= omcountwq) {
									String tsqlz = "select ws.base_unit,ws.zhong_wen_qch, ws.ku_wei_bian_ma,ws.bin_id,ws.shp_ming_cheng,cast(sum(ws.base_goodscount) as signed) as goods_qua, mb.qu_huo_ci_xu, ws.goods_pro_data"
											+ "  from wv_stock ws, md_bin mb  where "
											+ "   ws.ku_wei_bian_ma = mb.ku_wei_bian_ma  and mb.ku_wei_lei_xing = '良品区' and mb.ting_yong <> 'Y' and (ws.kuctype = '库存' or ws.kuctype = '待下架')";
									if (StringUtil.isNotEmpty(tuopanma)) {
										tsqlz = tsqlz + "  and  ws.bin_id = '" + tuopanma + "' ";
									}

									tsqlz = tsqlz
											+ "   and ws.goods_id = ? "
											+ "   and ws.cus_code =  ? ";
									if (StringUtil.isEmpty(goodprodata)) {
										tsqlz = tsqlz
												+ "   and ws.goods_pro_data = '" + goodprodata + "'";
									}
									if(StringUtil.isNotEmpty(binom)) {
										tsqlz = tsqlz + " and ws.ku_wei_bian_ma = '"+binom + "' ";
									}
									tsqlz = tsqlz
											+ "   and (ws.base_goodscount + 0) =  ? "
											+ "   group by ws.ku_wei_bian_ma,ws.bin_id,ws.goods_id,mb.qu_huo_ci_xu, ws.goods_pro_data having sum(ws.base_goodscount) > 0 order by ws.goods_pro_data , ws.goods_qua ,mb.qu_huo_ci_xu,ws.create_date desc";
									List<Map<String, Object>> resultz = systemService
											.findForJdbc(tsqlz, mvgoods.getGoodsId(), wmOmQmIEntity.getCusCode(), hiti);
									System.out.print("****************tsqlz" + tsqlz);

									if (resultz != null && resultz.size() > 0) {
										for (int i = 0; i < resultz.size(); i++) {
											try {
												Long bin_qua = Long.valueOf(resultz.get(i)
														.get("goods_qua").toString());
												if (omcountwq >= bin_qua && omcountwq > 0) {
													wmOmQmIEntity.setBinId(resultz.get(i)
															.get("ku_wei_bian_ma").toString());
													wmOmQmIEntity.setTinId(resultz.get(i)
															.get("bin_id").toString());
													wmOmQmIEntity.setBaseUnit(resultz.get(i)
															.get("base_unit").toString());
													wmOmQmIEntity
															.setBaseGoodscount(resultz.get(i)
																	.get("goods_qua")
																	.toString());
													wmOmQmIEntity.setProData(resultz.get(i)
															.get("goods_pro_data").toString());
													try{
														wmOmQmIEntity.setCusName(resultz.get(i)
																.get("zhong_wen_qch").toString());
														wmOmQmIEntity.setGoodsName(resultz.get(i)
																.get("shp_ming_cheng").toString());
													}catch (Exception e){

													}

													omcountwq = omcountwq - bin_qua;
													if (wmOmNoticeIEntity.getGoodsUnit()
															.equals(wmOmNoticeIEntity
																	.getBaseUnit())) {
														wmOmQmIEntity.setQmOkQuat(Long.toString(bin_qua));
														try {
															wmOmQmIEntity.setTinTj(String.valueOf(Double.parseDouble(mvgoods
																	.getTiJiCm()) / Double.parseDouble(mvgoods
																	.getChlShl())
																	* Long.parseLong(wmOmQmIEntity.getQmOkQuat())));
															wmOmQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
																	.getZhlKg()) / Double.parseDouble(mvgoods
																	.getChlShl())
																	* Long.parseLong(wmOmQmIEntity.getQmOkQuat())));
														} catch (Exception e) {
															// TODO: handle exception
														}
													} else {
														try {
															wmOmQmIEntity.setTinTj(String.valueOf(Double.parseDouble(mvgoods
																	.getTiJiCm())
																	* Long.parseLong(wmOmQmIEntity.getQmOkQuat()) / Long.parseLong(mvgoods
																	.getChlShl())));
															wmOmQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
																	.getZhlKg())
																	* Long.parseLong(wmOmQmIEntity.getQmOkQuat()) / Long.parseLong(mvgoods
																	.getChlShl())));
														} catch (Exception e) {
															// TODO: handle exception
														}
														try {

															wmOmQmIEntity
																	.setQmOkQuat(Long.toString(bin_qua));

														} catch (Exception e) {
															// TODO: handle exception
														}
													}
													systemService.save(wmOmQmIEntity);
												}
											} catch (Exception e) {
											}
										}
									}
								}
							}
						}
						List<Map<String, Object>> result = systemService
								.findForJdbc(tsql, mvgoods.getGoodsId(), wmOmQmIEntity.getCusCode());
						if (result != null && result.size() > 0) {
							for (int i = 0; i < result.size(); i++) {
								try {
									Long bin_qua = Long.valueOf(result.get(i)
											.get("goods_qua").toString());
									if (bin_qua > 0 && omcountwq > 0) {
										if (omcountwq > bin_qua) {
											wmOmQmIEntity.setBinId(result.get(i)
													.get("ku_wei_bian_ma").toString());
											wmOmQmIEntity.setTinId(result.get(i)
													.get("bin_id").toString());
											wmOmQmIEntity.setBaseUnit(result.get(i)
													.get("base_unit").toString());
											wmOmQmIEntity
													.setBaseGoodscount(result.get(i)
															.get("goods_qua")
															.toString());
											wmOmQmIEntity.setProData(result.get(i)
													.get("goods_pro_data").toString());
											wmOmQmIEntity.setCusName(result.get(i)
													.get("zhong_wen_qch").toString());
											wmOmQmIEntity.setGoodsName(result.get(i)
													.get("shp_ming_cheng").toString());
											omcountwq = omcountwq - bin_qua;
											if (wmOmNoticeIEntity.getGoodsUnit()
													.equals(wmOmNoticeIEntity
															.getBaseUnit())) {
												wmOmQmIEntity.setQmOkQuat(Long.toString(bin_qua));
												try {
													wmOmQmIEntity.setTinTj(String.valueOf(Double.parseDouble(mvgoods
															.getTiJiCm()) / Double.parseDouble(mvgoods
															.getChlShl())
															* Long.parseLong(wmOmQmIEntity.getQmOkQuat())));
													wmOmQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
															.getZhlKg()) / Double.parseDouble(mvgoods
															.getChlShl())
															* Long.parseLong(wmOmQmIEntity.getQmOkQuat())));
												} catch (Exception e) {
													// TODO: handle exception
												}
											} else {

												try {
													wmOmQmIEntity.setTinTj(String.valueOf(Double.parseDouble(mvgoods
															.getTiJiCm())
															* Long.parseLong(wmOmQmIEntity.getQmOkQuat()) / Long.parseLong(mvgoods
															.getChlShl())));
													wmOmQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
															.getZhlKg())
															* Long.parseLong(wmOmQmIEntity.getQmOkQuat()) / Long.parseLong(mvgoods
															.getChlShl())));
												} catch (Exception e) {
													// TODO: handle exception
												}
												try {

													wmOmQmIEntity
															.setQmOkQuat(Long.toString(bin_qua
															));

												} catch (Exception e) {
													// TODO: handle exception
												}
											}
											systemService.save(wmOmQmIEntity);
										} else {
											wmOmQmIEntity.setBinId(result.get(i)
													.get("ku_wei_bian_ma").toString());
											wmOmQmIEntity.setTinId(result.get(i)
													.get("bin_id").toString());
											wmOmQmIEntity.setBaseUnit(result.get(i)
													.get("base_unit").toString());
											wmOmQmIEntity.setBaseGoodscount(Long
													.toString(omcountwq));
											wmOmQmIEntity.setProData(result.get(i)
													.get("goods_pro_data").toString());
											wmOmQmIEntity.setCusName(result.get(i)
													.get("zhong_wen_qch").toString());
											wmOmQmIEntity.setGoodsName(result.get(i)
													.get("shp_ming_cheng").toString());
											if (wmOmNoticeIEntity.getGoodsUnit()
													.equals(wmOmNoticeIEntity
															.getBaseUnit())) {
												wmOmQmIEntity.setQmOkQuat(Long.toString(omcountwq));
												try {
													wmOmQmIEntity.setTinTj(String.valueOf(Double.parseDouble(mvgoods
															.getTiJiCm()) / Double.parseDouble(mvgoods
															.getChlShl())
															* Long.parseLong(wmOmQmIEntity.getQmOkQuat())));
													wmOmQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
															.getZhlKg()) / Double.parseDouble(mvgoods
															.getChlShl())
															* Long.parseLong(wmOmQmIEntity.getQmOkQuat())));
												} catch (Exception e) {
													// TODO: handle exception
												}

											} else {

												try {
													wmOmQmIEntity.setTinTj(String.valueOf(Double.parseDouble(mvgoods
															.getTiJiCm())
															* omcountwq / Long.parseLong(mvgoods
															.getChlShl())));
													wmOmQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
															.getZhlKg())
															* omcountwq / Long.parseLong(mvgoods
															.getChlShl())));
												} catch (Exception e) {
													// TODO: handle exception
												}


												try {

													wmOmQmIEntity
															.setQmOkQuat(Long.toString(omcountwq));

												} catch (Exception e) {
													// TODO: handle exception
												}
											}
											systemService.save(wmOmQmIEntity);
											wmOmNoticeIEntity.setPlanSta("Y");
											systemService
													.saveOrUpdate(wmOmNoticeIEntity);
											break;
										}
									}
								}catch (Exception e){

								}
								//catch  jieshu
							}



						}

					}
					wmOmNoticeIEntity.setPlanSta("Y");//执行后设置为Y
					systemService
							.saveOrUpdate(wmOmNoticeIEntity);
				}
				catch (Exception e){

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
