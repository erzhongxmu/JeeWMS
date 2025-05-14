package com.base.modules.jeewms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.util.RedisUtil;
import com.base.modules.jeewms.entity.*;

import com.base.modules.jeewms.mapper.WmDayCostConfMapper;
import com.base.modules.jeewms.mapper.WmOmNoticeHMapper;
import com.base.modules.jeewms.mapper.WmOmNoticeIMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import com.base.common.util.DateUtils;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("costTask")
@Slf4j
public class CostTask {
	ExecutorService executor = Executors.newFixedThreadPool(8);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private IWmDayCostConfService wmDayCostConfService;

	@Autowired
	private WmDayCostConfMapper wmDayCostConfMapper;
	@Autowired
	private IWmDayCostService wmDayCostService;
	@Autowired
	private IBaCostService baCostService;

	@Autowired
	private IWmOmNoticeHService noticeHService;

	@Autowired
	private SmsSendImpl SmsSendImpl;

	@Autowired
	private WmOmNoticeIMapper wmOmNoticeIMapper;

	@Autowired
	private WmOmNoticeHMapper wmOmNoticeHMapper;

	@Autowired
	private RedisUtil redisUtil;


	//@Scheduled(cron = "0 */1 * * * ?")
	//@Scheduled(cron = "0 0 * * * ?")
	@Scheduled(cron="0 0/1 * * * ?")
	public void run1() {
		String key ="xjdsrwht";
			System.out.println("下架定时任务开始执行：=====" + new Date());
			try{
				String redisvalue = (String)redisUtil.get(key);
				if(com.alibaba.csp.sentinel.util.StringUtil.isNotEmpty(redisvalue))  {
					System.out.println("上次下架定时任务还未结束：=====" + new Date());
					return;
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		redisUtil.set(key,DateUtils.formatDate(new Date()), 600);

		QueryWrapper<WmOmNoticeI> queryWrapper8 = new QueryWrapper<>();
		queryWrapper8.eq("plan_sta", "N");
		queryWrapper8.orderByDesc("create_time");
		List<WmOmNoticeI> noticeIS = wmOmNoticeIMapper.selectList(queryWrapper8);
		HashMap<String,String> noticeNo = new HashMap<>(1024);

		for (WmOmNoticeI noticeI : noticeIS) {
			noticeNo.put(noticeI.getOmNoticeId(),noticeI.getOmNoticeId());
		}
		for (String s : noticeNo.keySet()) {
			try{
				SmsSendImpl.rundowntask(s);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		redisUtil.del(key);

	}
/*	public void rundowntasks() {
		try{
			executor.execute(new Runnable() {
				@Override
				public void run() {
					QueryWrapper<WmOmNoticeI> queryWrapper8 = new QueryWrapper<>();
					queryWrapper8.eq("plan_sta", "N");
					queryWrapper8.orderByDesc("create_time");
					List<WmOmNoticeI> noticeIS = wmOmNoticeIMapper.selectList(queryWrapper8);
					HashMap<String,String> noticeNo = new HashMap<>(1024);

					for (WmOmNoticeI noticeI : noticeIS) {
						noticeNo.put(noticeI.getOmNoticeId(),noticeI.getOmNoticeId());
					}
					for (String s : noticeNo.keySet()) {
						SmsSendImpl.rundowntask(s);
					}
				}
			});

		}catch (Exception e){
		}
	}*/



	//@Scheduled(cron = "0 0 * * * ?")
	@Scheduled(cron="0 0/10 * * * ?")
	public void stockout() {
		try{
			executor.execute(new Runnable() {
				@Override
				public void run() {
					  QueryWrapper<WmOmNoticeH> queryWrapper = new QueryWrapper<>();
					  queryWrapper.eq("qh_sta","缺货");
					List<WmOmNoticeH> wmOmNoticeHS = wmOmNoticeHMapper.selectList(queryWrapper);
					for (WmOmNoticeH wmOmNoticeH : wmOmNoticeHS) {
						 QueryWrapper<WmOmNoticeI> queryWrapper1 = new QueryWrapper<>();
						 queryWrapper1.eq("om_notice_id", wmOmNoticeH.getOmNoticeId());
						 queryWrapper1.ne("plan_sta","Y");
						List<WmOmNoticeI> wmOmNoticeIS = wmOmNoticeIMapper.selectList(queryWrapper1);
						System.out.println(wmOmNoticeIS);
						if(wmOmNoticeIS.isEmpty()){
							wmOmNoticeH.setQhSta("");
							wmOmNoticeHMapper.updateById(wmOmNoticeH);
						}
					}
				}
			});
		}catch (Exception e){

		}

	}







	//@Scheduled(cron="0 0 23 * * ?")
	@Scheduled(cron="0 0 23 * * ?")
	public void run() {
		long start = System.currentTimeMillis();
		String datestr = DateUtils.getDate("yyyy-MM-dd");
		log.info("===================計費定时任务开始===================");
		this.costcount(datestr,"N");//每天自动计算
		log.info("===================計費定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		log.info("总耗时" + times + "毫秒");
	}

	public void costtask() {
		long start = System.currentTimeMillis();

		String datestr = DateUtils.getDate("yyyy-MM-dd");
		log.info("===================計費定时任务开始===================");

		this.costcount(datestr,"N");//每天自动计算
		try{
			wmDayCostConfMapper.deleteBydate(DateUtils.getDate("yyyy-MM-dd"));

		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun10(datestr);//计算费用10

		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun20(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun30(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun40(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun50(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun60(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun70(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}
		try{
			this.costcountrun80(datestr);//计算费用10
		}catch (Exception e){
			e.printStackTrace();
		}

		log
				.info("===================計費定时任务结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		log.info("总耗时" + times + "毫秒");
	}


	public  void costcount(String datestr,String chongsuan){
		WmDayCostConf t = new WmDayCostConf();
		Map<String, String[]> parameterMap = new HashMap<>(1024);
		QueryWrapper<WmDayCostConf> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("cost_date",datestr);
		queryWrapper.eq("cost_sf","Y");
		List<WmDayCostConf> list = wmDayCostConfService.list(queryWrapper);
 			if(list!=null&&list.size()>0){
				return;
			}else{
				t.setCostDate(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()) );
				t.setCostSf("Y");
				t.setCreateBy("system");
				t.setCreateTime(t.getCostDate());
				wmDayCostConfService.save(t);
			}
		 String tsql = "call update_wm_his_stock('"+datestr+"')";
		try {
			jdbcTemplate.execute(tsql);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


//先删除


	public  void costcountrun10(String datestr ){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;

		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost10(datestr);
		//计算卸货费
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;

//			String jf_shp_lei  = resulthq.get(i).get("jf_shp_lei").toString();
			String jf_shp_lei  = null;
			try{
				jf_shp_lei = resulthq.get(i).get("jf_shp_lei").toString();
			}catch (Exception e){

			}
			if("10".equals(jf_shp_lei)){
				costcode = "501";// 卸货费/计费吨
				//更改为向上取整
//					costSlo = Math.ceil(Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000);
				costSlo = Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000;
				countunit = "吨";
			}
			if("20".equals(jf_shp_lei)){
				costcode = "502";// 卸货费/整托
				costSlo = 1.00;
				countunit = "托";
			}
			if("30".equals(jf_shp_lei)){
				costcode = "503";// 卸货费/轻抛5KG以下
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}
			if("40".equals(jf_shp_lei)){
				costcode = "504";// 卸货费/轻抛5KG以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}
			costSl = costSlo.toString();


			cuscode  = resulthq.get(i).get("cus_code").toString();
			//计算操作费和越库费用
			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);
			if(resultjg.size()>0){
				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//				String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算卸货费/吨

 	}
	public  void costcountrun20(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost20(datestr);
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			costcode = "70";// 过磅费/
			costSlo = 1.00;
			countunit = "托";

			costSl = costSlo.toString();


			cuscode  = resulthq.get(i).get("cus_code").toString();



			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);

			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算过磅费/托

	}
	public  void costcountrun30(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost30(datestr);
		Double goodscount = 0.00 ;
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;

			goodscount = Double.parseDouble(resulthq.get(i).get("goodscount").toString());

			if(goodscount>10){
				costcode = "602";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}else{
				costcode = "601";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}

			costSl = costSlo.toString();

			cuscode  = resulthq.get(i).get("cus_code").toString();
//3010
			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);
			if(resultjg.size()>0){
				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//						String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算进货分拣费
	}
	public  void costcountrun40(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost40(datestr);
		Double goodscount = 0.00 ;
		for (int i = 0; i < resulthq.size(); i++) {
			if(resulthq.get(i).get("order_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			goodscount = Double.parseDouble(resulthq.get(i).get("goodscount").toString());

			if(goodscount>10){
				costcode = "6021";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}else{
				costcode = "6011";// 10以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}

			costSl = costSlo.toString();

			cuscode  = resulthq.get(i).get("cus_code").toString();
//4010
			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);

			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("order_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);

			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//								String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算出货分拣费
	}
	public  void costcountrun50(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost50(datestr);
		for (int i = 0; i < resulthq.size(); i++) {
			if(resulthq.get(i).get("order_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			String jf_shp_lei  = null;
			try{
				jf_shp_lei = resulthq.get(i).get("jf_shp_lei").toString();
			}catch (Exception e){
			}
			if("10".equals(jf_shp_lei)){
					if(  Double.parseDouble(resulthq.get(i).get("hiti").toString()) ==  Double.parseDouble(resulthq.get(i).get("qmcount").toString())){
						costcode = "5011";// 卸货费/吨
						costSlo = Double.parseDouble(resulthq.get(i).get("grcount").toString())/1000;
						countunit = "吨";
					}else{
						costcode = "5031";// 卸货费/轻抛5KG以下
						if((Double.parseDouble(resulthq.get(i).get("hiti").toString()) / 2) <=  Double.parseDouble(resulthq.get(i).get("qmcount").toString())){
							costSlo = (Double.parseDouble(resulthq.get(i).get("hiti").toString())) - Double.parseDouble(resulthq.get(i).get("qmcount").toString()) ;
						}else{
							costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
						}

						countunit = "箱";
					}
			}
			if("20".equals(jf_shp_lei)){
				costcode = "5021";// 卸货费/整托
				costSlo = 1.00;
				countunit = "托";
			}
			if("30".equals(jf_shp_lei)){
				costcode = "5031";// 卸货费/轻抛5KG以下
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}
			if("40".equals(jf_shp_lei)){
				costcode = "5041";// 卸货费/轻抛5KG以上
				costSlo = Double.parseDouble(resulthq.get(i).get("qmcount").toString());
				countunit = "箱";
			}

			costSl = costSlo.toString();

			cuscode  = resulthq.get(i).get("cus_code").toString();
//5010
			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);

			if(resultjg.size()>0){


				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("order_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();

			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
 			WmDayCost.setCostUnit(countunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  costSlo;
			dayCostBhs  = cost_bhs *  costSlo;
			dayCostHsj  = cost_hs *  costSlo;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//				String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算装车费

	}
	public  void costcountrun60(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost60(datestr);
		costcode = "30";
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cuscode  = resulthq.get(i).get("cus_code").toString();
			cfwenceng = resulthq.get(i).get("cf_wen_ceng").toString();
			//

			Double recdeg = 0.00;

			try{
				recdeg = Double.parseDouble(resulthq.get(i).get("rec_deg").toString()) - Double.parseDouble(resulthq.get(i).get("jizhun_wendu").toString());

			}catch (Exception e){
				recdeg = 0 - Double.parseDouble(resulthq.get(i).get("jizhun_wendu").toString());

			}
			if(recdeg>25){
				costcode = "308";//复冻处置费/吨 温差大于25
			}else if(recdeg>=13&&recdeg<=25){
				costcode = "306";//复冻处置费/吨  温差 13 25
			}else if(recdeg>=10&&recdeg<13){
				costcode = "304";//复冻处置费/吨  温差10 13
			}else{
				continue;
			}

			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);
			if(resultjg.size()>0){
				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+resulthq.get(i).get("goods_id").toString();
			costSl = resulthq.get(i).get("grcount").toString();
			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);

			WmDayCost.setCostUnit("KG");
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg *  Double.parseDouble(resulthq.get(i).get("grcount").toString()) / 1000;
			dayCostBhs  = cost_bhs *  Double.parseDouble(resulthq.get(i).get("grcount").toString())  / 1000;
			dayCostHsj  =  cost_hs *  Double.parseDouble(resulthq.get(i).get("grcount").toString()) / 1000;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//				String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//复冻处置费/吨

	}public  void costcountrun70(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = null;
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost70(datestr);
		for (int i = 0; i < resulthq.size(); i++) {
			if( resulthq.get(i).get("im_notice_id").toString().startsWith("YK")||resulthq.get(i).get("im_notice_id").toString().startsWith("QT")){
				continue;
			}
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cuscode  = resulthq.get(i).get("cus_code").toString();
//            7010


			costcode = "80";
			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);

			if(resultjg.size()>0){
				cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg"));
				cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl"));
				cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs"));
				cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs"));
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("im_notice_id").toString()+"/"+ resulthq.get(i).get("tin_id").toString();
			costSl = "1";
			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);

			WmDayCost.setCostUnit("托");
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算服务费
	}
	public  void costcountrun80(String datestr){
		Double cost_jg = 0.0000;
		Double cost_sl = 0.0000;
		Double cost_bhs = 0.0000;
		Double cost_hs = 0.0000;
		Double dayCostYj = 0.0000;
		Double dayCostBhs = 0.0000;
		Double dayCostSe = 0.0000;
		Double dayCostHsj = 0.0000;
		Double costSlo = 0.0000;
		String ori = null;
		String costSl = null;
		String cuscode = null;
		String cfwenceng = null;
		String beizhu = null;
		String costcode = "1010";
		String countunit = null;
		List<Map<String, Object>> resulthq = wmDayCostConfMapper.getcost80(datestr);
		HashMap<String,Double>  cusmap = new  HashMap<String,Double>();
		HashMap<String,Double>  goodsmap = new  HashMap<String,Double>();
		HashMap<String,Double>  cdwcusmap = new  HashMap<String,Double>();
		HashMap<String,Double>  cdwgoodsmap = new  HashMap<String,Double>();
		String  jfunit = null;
		//仓租
		for (int i = 0; i < resulthq.size(); i++) {
			cost_jg = 0.0000;
			cost_sl = 0.0000;
			cost_bhs = 0.0000;
			cost_hs = 0.0000;
			dayCostYj = 0.0000;
			dayCostBhs = 0.0000;
			dayCostSe = 0.0000;
			dayCostHsj = 0.0000;
			ori = null;
			costSl = null;
			cuscode = null;
			cfwenceng = null;
			beizhu = null;
			cuscode  = resulthq.get(i).get("cus_code").toString();
			//仓租

			if(StringUtil.isBlankOrNull(cuscode)){
				continue;
			}

//			8010
			List<Map<String, Object>> resultjg = wmDayCostConfMapper.getcostconf(costcode,cuscode,datestr);

			if(resultjg.size()>0){

					cost_jg = Double.parseDouble((String) resultjg.get(0).get("cost_jg")) *  Double.parseDouble(costSl);
					cost_sl  = Double.parseDouble((String) resultjg.get(0).get("cost_sl")) *  Double.parseDouble(costSl);
					cost_bhs = Double.parseDouble((String) resultjg.get(0).get("cost_bhs")) *  Double.parseDouble(costSl);
					cost_hs = Double.parseDouble((String) resultjg.get(0).get("cost_hs")) *  Double.parseDouble(costSl);
				beizhu="";
			}else{
				beizhu = "费用合同无此费率";
				cost_bhs  = 0.00  ;
				dayCostBhs  = 0.00 ;
				cost_hs  = 0.00  ;
			}
			ori = resulthq.get(i).get("cus_code").toString()+"/"+ datestr;
//					costSl = "1";
			WmDayCost WmDayCost = new WmDayCost();
			WmDayCost.setCostJs("N");
			WmDayCost.setCreateBy("system");
			WmDayCost.setCreateTime(DateUtils.getDate());
			WmDayCost.setCostCode(costcode);
			WmDayCost.setCostName(getcostname(cuscode));

			WmDayCost.setCostData(DateUtils.str2Date(datestr,DateUtils.date_sdf.get()));
			WmDayCost.setCostOri(ori);
			WmDayCost.setCostSl(costSl);
			WmDayCost.setCostSta("已生成");
			WmDayCost.setCusCode(cuscode);
			WmDayCost.setCostUnit(jfunit);
			WmDayCost.setBeizhu(beizhu);
			dayCostYj  = cost_jg  ;
			dayCostBhs  = cost_bhs ;
			dayCostHsj  = cost_hs  ;
			dayCostSe  = dayCostHsj -  dayCostBhs ;
			DecimalFormat df=new DecimalFormat("#.00");
//					String st=df.format(d);
			WmDayCost.setDayCostYj(df.format(dayCostYj));
			WmDayCost.setDayCostBhs(df.format(dayCostBhs));
			WmDayCost.setDayCostSe(df.format(dayCostSe));
			WmDayCost.setDayCostHsj(df.format(dayCostHsj));
			wmDayCostService.save(WmDayCost);
		}//计算仓租



	}

	public String getcostname(String costcode){
		String costname= "";
		try{
			BaCost bacost = new BaCost();
			QueryWrapper<BaCost> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("cost_code",costcode);
			List<BaCost> list = baCostService.list(queryWrapper);
			costname = list.get(0).getCostName();
		}catch (Exception e){

		}
		return costname;
	}


}
