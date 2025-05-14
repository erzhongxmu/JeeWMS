package com.base.modules.jeeerp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.system.vo.LoginUser;
import com.base.modules.client.ISysBaseAPIClient;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPrdOrdItemMapper;
import com.base.modules.jeeerp.mapper.BusiOrdCraftMapper;
import com.base.modules.jeeerp.mapper.BusiPrdOrdMapper;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPrdOrdService;
import com.base.modules.jeeerp.vo.BusiPrdOrdPage;
import com.base.modules.util.DayiUtils;
import com.base.modules.util.GenerateCodeUtil;
import com.base.modules.util.PltnPushWms;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: busi_prd_ord
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class BusiPrdOrdServiceImpl extends ServiceImpl<BusiPrdOrdMapper, BusiPrdOrd> implements IBusiPrdOrdService {

	@Autowired
	private BusiPrdOrdMapper busiPrdOrdMapper;
	@Autowired
	private BusiPrdOrdItemMapper busiPrdOrdItemMapper;
	@Autowired
	private BusiOrdCraftMapper busiOrdCraftMapper;
	@Autowired
	private GenerateCodeUtil generateCodeUtil;
	@Autowired
	private PltnPushWms pltnPushWms;
	@Autowired
	private ISysBaseAPIClient sysBaseAPIClient;
	@Autowired
	private IBusiPoService busiPoService;

	@Override
	@Transactional
	public void saveMain(BusiPrdOrd busiPrdOrd, List<BusiPrdOrdItem> busiPrdOrdItemList,List<BusiOrdCraft> busiOrdCraftList) {

		busiPrdOrdMapper.insert(busiPrdOrd);

		if(busiPrdOrdItemList!=null && busiPrdOrdItemList.size()>0) {
			for(BusiPrdOrdItem entity:busiPrdOrdItemList) {
				//外键设置
				entity.setNum02(busiPrdOrd.getNum01());
				entity.setNum03(new Double(0));
				entity.setLink02(busiPrdOrd.getQuery04());
				busiPrdOrdItemMapper.insert(entity);
			}
		}
		if(busiOrdCraftList!=null && busiOrdCraftList.size()>0) {
			for(BusiOrdCraft entity:busiOrdCraftList) {
				//外键设置
				entity.setLink02(busiPrdOrd.getQuery04());
				busiOrdCraftMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(BusiPrdOrd busiPrdOrd,List<BusiPrdOrdItem> busiPrdOrdItemList,List<BusiOrdCraft> busiOrdCraftList) {
		busiPrdOrdMapper.updateById(busiPrdOrd);

		//1.先删除子表数据
		busiPrdOrdItemMapper.deleteByMainId(busiPrdOrd.getQuery04());
		busiOrdCraftMapper.deleteByMainId(busiPrdOrd.getQuery04());

		//2.子表数据重新插入
		if(busiPrdOrdItemList!=null && busiPrdOrdItemList.size()>0) {
			for(BusiPrdOrdItem entity:busiPrdOrdItemList) {
				//外键设置
				entity.setLink02(busiPrdOrd.getQuery04());
				busiPrdOrdItemMapper.insert(entity);
			}
		}
		if(busiOrdCraftList!=null && busiOrdCraftList.size()>0) {
			for(BusiOrdCraft entity:busiOrdCraftList) {
				//外键设置
				entity.setLink02(busiPrdOrd.getQuery04());
				busiOrdCraftMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		BusiPrdOrd busiPrdOrd = busiPrdOrdMapper.selectById(id);
		busiPrdOrdItemMapper.deleteByMainId(busiPrdOrd.getQuery23());
		busiOrdCraftMapper.deleteByMainId(busiPrdOrd.getQuery23());
		busiPrdOrdMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			BusiPrdOrd busiPrdOrd = busiPrdOrdMapper.selectById(id);
			busiPrdOrdItemMapper.deleteByMainId(busiPrdOrd.getQuery23());
			busiOrdCraftMapper.deleteByMainId(busiPrdOrd.getQuery23());
			busiPrdOrdMapper.deleteById(id);
		}
	}

	@Override
	@Transactional
	public void BusiPrdOrdPageAdd(BusiPrdOrdPage busiPrdOrdPage) {
		// 生成加工订单
		BusiPrdOrd busiPrdOrd = new BusiPrdOrd();
		String code = generateCodeUtil.generateCode("busi_prd_ord", busiPrdOrdPage.getQuery01());
		busiPrdOrdPage.setQuery04(code);
		boolean kucunbuzu = false;
		if ("ZJDD".equals(busiPrdOrdPage.getQuery01())) {
			busiPrdOrdPage.setNum02(busiPrdOrdPage.getNum01());
			// 库存不足不可以建单
//            List<BusiPrdOrdItem> busiPrdOrdItemList1 = busiPrdOrdPage.getBusiPrdOrdItemList();
//            for (BusiPrdOrdItem busiPrdOrdItem : busiPrdOrdItemList1) {
//                String query10 = busiPrdOrdItem.getQuery10();// 商品编码
////				String query14 = busiPrdOrdItem.getQuery14();// 批次
//                Double num01 = busiPrdOrdItem.getNum01();// 数量
//                List<WvStockSttQuery> list = wvStockSttQueryService.lambdaQuery()
//                        .eq(WvStockSttQuery::getKuctype, "库存")
////						.eq(WvStockSttQuery::getGoodsBatch,query14)
//                        .eq(WvStockSttQuery::getGoodsId, query10)
//                        .list();
//                if (CollectionUtil.isNotEmpty(list)) {
//                    int num = 0;
//                    for (WvStockSttQuery wvStockSttQuery : list) {
//                        num += wvStockSttQuery.getGoodsQua();
//                    }
//                    if (num < num01) {
//                        kucunbuzu = true;
//                    }
//                } else {
//                    kucunbuzu = true;
//                }
//            }
		} else {
			String PO = generateCodeUtil.generateCode("busi_prd_ord", "SO");
			busiPrdOrdPage.setQuery13(PO);
		}
		BeanUtils.copyProperties(busiPrdOrdPage, busiPrdOrd);
		busiPrdOrd.setNum03(new Double(0));
		busiPrdOrd.setQuery02("计划中");
		this.saveMain(busiPrdOrd, busiPrdOrdPage.getBusiPrdOrdItemList(), busiPrdOrdPage.getBusiOrdCraftList());

		// 生成生产订单
		busiPrdOrdPage.setLink01("加工订单");
		BusiPrdOrd busiPrdOrd2 = new BusiPrdOrd();
		BeanUtils.copyProperties(busiPrdOrdPage, busiPrdOrd2);
		busiPrdOrd2.setId("");
		busiPrdOrd2.setQuery01("SCDD");
		busiPrdOrd2.setQuery19("");//加工费默认为空
		String code2 = generateCodeUtil.generateCode("busi_prd_ord", "SCDD");
		busiPrdOrd2.setQuery04(code2);
		if(!kucunbuzu){
			busiPrdOrd2.setQuery02("已推送");
			busiPrdOrd2.setQuery02("已推送");
		}else{
			busiPrdOrd2.setQuery02("未推送");
			busiPrdOrd2.setQuery02("未推送");
		}
		busiPrdOrd2.setNum02(busiPrdOrd2.getNum01());
		busiPrdOrd2.setNum03(new Double(0));

		busiPrdOrd2.setLink02(code);
		List<BusiPrdOrdItem> busiPrdOrdItem = busiPrdOrdPage.getBusiPrdOrdItemList();
		for (BusiPrdOrdItem prdOrdItem : busiPrdOrdItem) {
			prdOrdItem.setId("");
		}
		this.saveMain(busiPrdOrd2, busiPrdOrdItem, busiPrdOrdPage.getBusiOrdCraftList());
		// 推送生产订单到WMS //库存不足不允许推送
		if (!kucunbuzu) {
			pltnPushWms.materialsPutout(busiPrdOrd2,busiPrdOrdPage.getQuery01());
		}
		if ("JG".equals(busiPrdOrdPage.getQuery01())) {
			// 生成生产完工
			BusiPrdOrd busiPrdOrd3 = new BusiPrdOrd();
			BeanUtils.copyProperties(busiPrdOrdPage, busiPrdOrd3);
			busiPrdOrd3.setId("");
			busiPrdOrd3.setQuery01("SCWG");
			busiPrdOrd3.setQuery02("未推送");
			String code3 = generateCodeUtil.generateCode("busi_prd_ord", "SCWG");
			String sonPO = generateCodeUtil.getSonPO(1, busiPrdOrd3.getQuery13());
			busiPrdOrd3.setQuery04(code3);
			busiPrdOrd3.setLink02(code);
			busiPrdOrd3.setQuery14(sonPO);
			busiPrdOrd3.setNum02(busiPrdOrd3.getNum01());
			busiPrdOrd3.setNum03(new Double(0));
			BusiPrdOrdItem busiPrdOrdItemObj = new BusiPrdOrdItem();
			List<BusiPrdOrdItem> busiPrdOrdItemList = new ArrayList<>();
			busiPrdOrdItemList.add(busiPrdOrdItemObj);
			this.saveMain(busiPrdOrd3, busiPrdOrdItemList, busiPrdOrdPage.getBusiOrdCraftList());
		} else {
			// 生成质检完工单
			BusiPrdOrd busiPrdOrd3 = new BusiPrdOrd();
			BeanUtils.copyProperties(busiPrdOrdPage, busiPrdOrd3);
			busiPrdOrd3.setId("");
			busiPrdOrd3.setQuery01("SCWG");
			busiPrdOrd3.setQuery02("未推送");
			String code3 = generateCodeUtil.generateCode("busi_prd_ord", "SCWG");
			busiPrdOrd3.setQuery04(code3);
			busiPrdOrd3.setLink02(code);
			busiPrdOrd3.setNum02(busiPrdOrd3.getNum01());
			busiPrdOrd3.setNum03(new Double(0));
			List<BusiPrdOrdItem> busiPrdOrdItem2 = busiPrdOrdPage.getBusiPrdOrdItemList();
			for (BusiPrdOrdItem prdOrdItem : busiPrdOrdItem2) {
				prdOrdItem.setId("");
			}
			this.saveMain(busiPrdOrd3, busiPrdOrdItem2, busiPrdOrdPage.getBusiOrdCraftList());

//			生成质检排班
			if (busiPrdOrd2.getQuery21() != null && busiPrdOrd2.getQuery21() != "") {
				String[] split = busiPrdOrd2.getQuery21().split("~");
				List<String> betweenDate = DayiUtils.getBetweenDate(split[0], split[1]);
				for (String s : betweenDate) {
					BaseScheduleInfo baseScheduleInfo = new BaseScheduleInfo();
					baseScheduleInfo.setPlanDate(s); // 日期
					baseScheduleInfo.setUserNo(busiPrdOrd.getQuery25()); // 质检
					baseScheduleInfo.setAttr1("仓库验货"); // 地点
					// 工作内容
					QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
					queryWrapper.lambda().eq(BusiPo::getQuery14, busiPrdOrd.getQuery14());
					List<BusiPo> list = busiPoService.list(queryWrapper);
					String str = "";
					if (CollectionUtil.isNotEmpty(list)) {
						str = list.get(0).getQuery09() + " - 质检" + busiPrdOrd.getQuery14() + " - 品名：" + busiPrdOrd.getQuery11() + " - 数量：" + busiPrdOrdPage.getBusiPrdOrdItemList().get(0).getNum01().toString();
					} else {
						str = " 质检" + busiPrdOrd.getQuery14() + " - 品名：" + busiPrdOrd.getQuery11() + " - 数量：" + busiPrdOrdPage.getBusiPrdOrdItemList().get(0).getNum01().toString();
					}
					baseScheduleInfo.setAttr2(str);
					baseScheduleInfo.setPbType("1");
					// 指派人
					LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
					baseScheduleInfo.setAttr3(login.getRealname());
					// 指派日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String time = sdf.format(now);
					baseScheduleInfo.setAttr4(time);
					baseScheduleInfo.setAttr5("采购：" + busiPrdOrd.getQuery16() + " 跟单：" + busiPrdOrd.getQuery28());
					sysBaseAPIClient.add(baseScheduleInfo);
				}
			}
		}

	}

}
