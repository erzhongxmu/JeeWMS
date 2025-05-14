package com.base.modules.jeeerp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPoItemMapper;
import com.base.modules.jeeerp.mapper.BusiPoMapper;
import com.base.modules.jeeerp.service.IBusiPaymentReceivedService;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPrdOrdService;
import com.base.modules.jeeerp.service.IConfErpService;
import com.base.modules.jeeerp.vo.BusiPoPage;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.el.ELClass;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
@Service
public class BusiPoServiceImpl extends ServiceImpl<BusiPoMapper, BusiPo> implements IBusiPoService {

	@Autowired
	private BusiPoMapper busiPoMapper;
	@Autowired
	private BusiPoItemMapper busiPoItemMapper;

	@Autowired
	private IBusiPoService busiPoService;

	@Autowired
	private IMdSupService mdSupService;
	@Autowired
	private IMdGoodsService mdGoodsService;
	@Autowired
	private IMdCusService mdCusService;
	@Autowired
	private IBusiPaymentReceivedService busiPaymentReceivedService;
	@Autowired
	private IBaPartTypeService baPartTypeService;
	@Autowired
	private IBaUnitService baUnitService;

	@Autowired
	private IMdSupService supService;

	@Autowired
	private IMdCusService cusService;

	@Autowired
	private IMdGoodsService goodsService;

	@Autowired
	private IBusiPrdOrdService busiPrdOrdService;

	@Autowired
	private IConfErpService confErpService;

	@Override
	@Transactional
	public void saveMain(BusiPo busiPo, List<BusiPoItem> busiPoItemList) {
		busiPoMapper.insert(busiPo);
		if(busiPoItemList!=null && busiPoItemList.size()>0) {
			for(BusiPoItem entity:busiPoItemList) {
				//外键设置
				entity.setLink02(busiPo.getQuery23());
				busiPoItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(BusiPo busiPo,List<BusiPoItem> busiPoItemList) {
		busiPoMapper.updateById(busiPo);

		//1.先删除子表数据
		busiPoItemMapper.deleteByMainId(busiPo.getQuery23());

		//2.子表数据重新插入
		if(busiPoItemList!=null && busiPoItemList.size()>0) {
			for(BusiPoItem entity:busiPoItemList) {
				//外键设置
				entity.setLink02(busiPo.getQuery23());
				busiPoItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		BusiPo busiPo = busiPoMapper.selectById(id);
		busiPoItemMapper.deleteByMainId(busiPo.getQuery23());
		busiPoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			BusiPo busiPo = busiPoMapper.selectById(id);
			if(busiPo!=null){
				busiPoItemMapper.deleteByMainId(busiPo.getQuery23());
				busiPoMapper.deleteById(id);
			}
		}
	}





	@Override
	@Transactional
	public List<BusiPoPage> CGHS(List<BusiPo> busiPoList) {
		List<BusiPoPage> BusiPoPageList2 = new ArrayList<>();
		for (BusiPo busiPo : busiPoList) {
			QueryWrapper<BusiPo> BusiPoqueryWrapper = new QueryWrapper<>();
			BusiPoqueryWrapper.lambda().eq(BusiPo::getQuery04,busiPo.getQuery04());
			List<BusiPo> list = busiPoService.list(BusiPoqueryWrapper);

			BusiPoPage obj = new BusiPoPage();
			BeanUtils.copyProperties(busiPo, obj);
			obj.setQuery05(busiPo.getQuery08()); // 供应商
			obj.setQuery06(busiPo.getQuery13()); // PO号
			obj.setQuery08(new SimpleDateFormat("yyyy-MM-dd").format(busiPo.getCreateTime())); // 日期
			obj.setQuery10(busiPo.getQuery16()); // 采购名
			// 查询制造商
			QueryWrapper<MdSup> MdSupqueryWrapper = new QueryWrapper<>();
			MdSupqueryWrapper.lambda().eq(MdSup::getGysBianMa,busiPo.getQuery08());
			MdSup one = mdSupService.getOne(MdSupqueryWrapper, false);
			// 查询供应商
			QueryWrapper<MdSup> MdSupqueryWrapper2 = new QueryWrapper<>();
			MdSupqueryWrapper2.lambda().eq(MdSup::getGysBianMa,busiPo.getQuery41());
			MdSup MdSupone2 = mdSupService.getOne(MdSupqueryWrapper2, false);
			obj.setQuery23(one.getShouJi()); // 联系电话
			obj.setQuery07(MdSupone2.getDiZhi()); // 地址
			obj.setQuery09(one.getZhuLianXiRen()); // 联系人
			obj.setQuery11(one.getShouJi()); // 联系电话
			if("是".equals(busiPo.getQuery25())){
				obj.setQuery19(one.getCorporateAccount()); // 银行信息
			}else {
				obj.setQuery19(one.getPersonalAccount()); // 银行信息
			}
			if (!"CNY".equals(busiPo.getQuery22())) {
				obj.setQuery19(one.getForeignAccount()); // 银行信息
			}
			obj.setQuery12(busiPo.getLink03() + "/" + busiPo.getQuery30()); // 销售单号
			List<BusiPoItem> busiPoItemList = new ArrayList<>();
			for (BusiPo po : list) {
				BusiPoItem busiPoItem = new BusiPoItem();
				// 查询商品
				QueryWrapper<MdGoods> MdGoodsqueryWrapper =  new QueryWrapper<>();
				MdGoodsqueryWrapper.lambda().eq(MdGoods::getShpBianMa,po.getQuery10());
				MdGoods one1 = mdGoodsService.getOne(MdGoodsqueryWrapper, false);
				// 查询客户
				QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
				MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,po.getQuery24());
				MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);

				String str = po.getQuery10() + "\n" + po.getQuery11() + "\n" + one1.getYwMingCheng() + "\n" + one2.getZhongWenQch()+ "\n" + po.getQuery32();
				busiPoItem.setQuery12(str); // 品名
				busiPoItem.setQuery14(new DecimalFormat("0").format(po.getNum01()).toString()); // 数量
				busiPoItem.setQuery15(po.getNum04().toString()); // 单价
				busiPoItem.setQuery17(po.getNum06().toString()); // 含税单价
				busiPoItem.setQuery16(new DecimalFormat("0.00").format(po.getNum05()).toString()); // 金额
				busiPoItem.setQuery18(new DecimalFormat("0.00").format(po.getNum07()).toString()); // 含税后金额
				busiPoItem.setQuery20(po.getQuery12()); // 单位
				if(po.getNum10() != null){
					busiPoItem.setQuery21(po.getNum10().toString() + "%"); // 税率
					busiPoItem.setQuery11(one.getShuiWuDeng()); // 订单ID
				}
				busiPoItem.setQuery13(one1.getShpMiaoShu()); // 产品描述
				busiPoItem.setQuery19(one1.getShpGuiGe()); // 规格型号
				busiPoItem.setQuery22(String.valueOf(new DecimalFormat("0.00").format(po.getNum07() - po.getNum05()))); // 税额

				QueryWrapper<ConfErp> queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(ConfErp::getQuery04,po.getQuery10());
				queryWrapper.lambda().eq(ConfErp::getQuery06,busiPo.getQuery08());
				queryWrapper.lambda().eq(ConfErp::getQuery01,"SHFL");
				List<ConfErp> list1 = confErpService.list(queryWrapper);
				if(CollectionUtil.isNotEmpty(list1)){
					busiPoItem.setQuery23(list1.get(0).getQuery07()); // 税收分类编码
					busiPoItem.setQuery24(list1.get(0).getQuery08()); // 税收品名
					busiPoItem.setQuery25(list1.get(0).getQuery09()); // 规格型号
					busiPoItem.setQuery26(list1.get(0).getQuery10()); // 单位
				}
				busiPoItemList.add(busiPoItem);
			}
			obj.setQuery25(busiPo.getQuery22()); // 币种
			obj.setQuery26(busiPo.getQuery21()); // 到货时间
			if(busiPo.getNum16() != null){
				obj.setQuery27(busiPo.getNum16().toString()); // 样品可退
			}else {
				obj.setQuery27("0"); // 样品可退
			}

			if(busiPo.getNum18() != null){
				obj.setQuery29(busiPo.getNum18().toString()); // 样品可退
			}else {
				obj.setQuery29("0"); // 样品可退
			}

			obj.setBusiPoItemList(busiPoItemList);
			BusiPoPageList2.add(obj);
		}
		return BusiPoPageList2;
	}
	@Override
	@Transactional
	public List<BusiPoPage> FKD(List<BusiPo> busiPoList) {
		List<BusiPoPage> BusiPoPageList2 = new ArrayList<>();
		for (BusiPo busiPo : busiPoList) {
			QueryWrapper<BusiPo> BusiPoqueryWrapper = new QueryWrapper<>();

			BusiPoqueryWrapper.lambda().eq(BusiPo::getQuery04,busiPo.getQuery04());
			List<BusiPo> list = busiPoService.list(BusiPoqueryWrapper);
			Double qty = 0.00;
			Double  unitprice = 0.00;
			Double totalPrice = 0.00;
			for (BusiPo po : list) {
				qty = qty + po.getNum01();
				totalPrice = totalPrice + po.getNum01() * po.getNum06();
			}
			unitprice = totalPrice/qty;
			// 查询供应商
			QueryWrapper<MdSup> MdSupqueryWrapper = new QueryWrapper<>();
			MdSupqueryWrapper.lambda().eq(MdSup::getGysBianMa,busiPo.getQuery08());
			MdSup one = mdSupService.getOne(MdSupqueryWrapper, false);
			// 查询客户
			QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,busiPo.getQuery24());
			MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
			BusiPoPage obj = new BusiPoPage();
			BeanUtils.copyProperties(busiPo, obj);
			// 查询商品
			QueryWrapper<MdGoods> MdGoodsqueryWrapper =  new QueryWrapper<>();
			MdGoodsqueryWrapper.lambda().eq(MdGoods::getShpBianMa,busiPo.getQuery10());
			MdGoods one1 = mdGoodsService.getOne(MdGoodsqueryWrapper, false);

			obj.setQuery05(one2.getZhongWenQch()); // 客户
			obj.setQuery06(busiPo.getQuery13()); // PO
			obj.setQuery07(busiPo.getQuery10() + "-" + busiPo.getQuery11()); // 商品编码-商品名称
			obj.setQuery08(busiPo.getQuery17()); // 形式发票号
			obj.setQuery09(new SimpleDateFormat("yyyy-MM-dd").format(busiPo.getCreateTime())); // 时间
			obj.setQuery10(busiPo.getLink03()); // 销售单号
			obj.setQuery11(busiPo.getQuery09()); // 供应商
			obj.setQuery12(busiPo.getQuery16()); // 采购人
			obj.setQuery13(one2.getZhongWenQch() + "-" + new SimpleDateFormat("yy").format(busiPo.getCreateTime()) +"M"+ new SimpleDateFormat("MM").format(busiPo.getCreateTime()) + "-" + busiPo.getQuery10() + " " + busiPo.getQuery11()); // 客户+商品编码+商品名称
			obj.setQuery14(one2.getXingYeFenLei()); // 企业属性
			obj.setQuery15(qty.toString()); // 数量
			obj.setQuery16(new DecimalFormat("0.00").format(unitprice)); // 单价
			if(!"CNY".equals(busiPo.getQuery22())){
				obj.setQuery17("USD Account"); // 外币账户
			}else {
				if("是".equals(busiPo.getQuery25())){
					obj.setQuery17("Company Account"); // 公司账户
				}else {
					obj.setQuery17("Personal Account"); // 私人账户
				}
			}
			// 查询打样
			QueryWrapper<BusiPo> BusiPoqueryWrapper2 = new QueryWrapper<>();
			BusiPoqueryWrapper2.lambda().eq(BusiPo::getLink02,busiPo.getQuery04());
			BusiPoqueryWrapper2.lambda().groupBy(BusiPo::getQuery04);
			List<BusiPo> list3 = busiPoService.list(BusiPoqueryWrapper2);
			if(CollectionUtil.isNotEmpty(list3)){
				BusiPo busiPo1 = list3.get(0);
				obj.setQuery18(busiPo1.getQuery27()+","+ busiPo1.getNum09()); // 是否退样版费
			}

			if("是".equals(busiPo.getQuery25())){
				obj.setQuery19(one.getCorporateAccount()); // 银行信息
				double v = busiPo.getNum09();
				if(busiPo.getQuery27() != null && "是".equals(busiPo.getQuery27()) && "CGD".equals(busiPo.getQuery01())){
					v = busiPo.getNum09();
					//  - busiPo.getNum16()
				}
				obj.setQuery20(String.format("%.2f", v)); // 含稅总金额
				obj.setQuery21(busiPo.getNum10().toString()); // 税率
			}else {
				obj.setQuery19(one.getPersonalAccount()); // 银行信息
				obj.setQuery20(busiPo.getNum08().toString()); // 不含稅总金额
				obj.setQuery21("0"); // 税率

			}

			if (!"CNY".equals(busiPo.getQuery22())) {
				obj.setQuery19(one.getForeignAccount()); // 银行信息
			}
			obj.setQuery22(one1.getClassification()); // 商品分类
			obj.setQuery23("PAYPALA# "+ busiPo.getQuery13()); // PO

			// 子表
			List<BusiPoItem> busiPoItemList = new ArrayList<>();
			BusiPoItem obj2 = new BusiPoItem();
			if("YP".equals(busiPo.getQuery01())){
				if("是".equals(busiPo.getQuery27())){
					obj2.setQuery23("PR02"); // 付款代码
				}else {
					obj2.setQuery23("PR03"); // 付款代码
				}
				obj2.setQuery24(busiPo.getNum09().toString()); // 付款金额
			}else {
				obj2.setQuery23("PR01"); // 付款代码
				double v2 = busiPo.getNum09() * 0.3;
				obj2.setQuery24(String.valueOf(v2)); // 付款金额
			}
//			QueryWrapper<BusiPaymentReceived> BusiPaymentqueryWrapper2 = new QueryWrapper<>();
//			BusiPaymentqueryWrapper2.lambda().eq(BusiPaymentReceived::getLink02,busiPo.getQuery04());
//			BusiPaymentqueryWrapper2.lambda().eq(BusiPaymentReceived::getQuery01,"CGFKJH");
//			BusiPaymentqueryWrapper2.lambda().groupBy(BusiPaymentReceived::getQuery04);
//			List<BusiPaymentReceived> list2 = busiPaymentReceivedService.list(BusiPaymentqueryWrapper2);
//			for (BusiPaymentReceived busiPaymentReceived : list2) {
//				BusiPoItem obj2 = new BusiPoItem();
//				obj2.setQuery23(busiPaymentReceived.getQuery26()); // 付款代码
//				if(busiPaymentReceived.getQuery25().equals("是")){
//					obj2.setQuery24(busiPaymentReceived.getNum09().toString()); // 付款金额
//				}else {
//					obj2.setQuery24(busiPaymentReceived.getNum08().toString()); // 付款金额
//				}
//				busiPoItemList.add(obj2);
//			}
			busiPoItemList.add(obj2);
			obj.setBusiPoItemList(busiPoItemList);

			BusiPoPageList2.add(obj);
		}
		return BusiPoPageList2;
	}

	@Override
	@Transactional
	public List<BusiPoPage> XSFP(List<BusiPaymentReceived> busiPaymentReceivedList) {
		List<BusiPoPage> BusiPoPageList2 = new ArrayList<>();
		for (BusiPaymentReceived busiPaymentReceived : busiPaymentReceivedList) {
			BusiPoPage obj = new BusiPoPage();
			// 查询客户
			QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,busiPaymentReceived.getQuery24());
			MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);

			BeanUtils.copyProperties(busiPaymentReceived, obj);
			obj.setQuery05(busiPaymentReceived.getQuery04()); // 发票单号
			obj.setQuery06(new SimpleDateFormat("yyyy-MM-dd").format(busiPaymentReceived.getCreateTime())); // 时间
			obj.setQuery07(one2.getZhongWenQch()); // 客户

//			obj.setQuery08("客户信息"); // 客户信息
			obj.setQuery09(""); // 地址信息1
			obj.setQuery10(one2.getDiZhi()); // 地址信息2
			String date = new SimpleDateFormat("yy/MM").format(busiPaymentReceived.getCreateTime());

			// 查询形式发票商品信息


				BusiPoItem obj2 = new BusiPoItem();
				// 查询商品
				QueryWrapper<MdGoods> MdGoodsqueryWrapper =  new QueryWrapper<>();
				MdGoodsqueryWrapper.lambda().eq(MdGoods::getShpBianMa,busiPaymentReceived.getQuery10());
				MdGoods one1 = mdGoodsService.getOne(MdGoodsqueryWrapper, false);
				// 查询商品分类
				QueryWrapper<BaPartType> BaPartTypequeryWrapper =  new QueryWrapper<>();
				BaPartTypequeryWrapper.lambda().eq(BaPartType::getAttr3,one1.getClassification());
				BaPartType one = baPartTypeService.getOne(BaPartTypequeryWrapper, false);
				obj2.setQuery12(one.getAttr4()); // 商品分类编码
				obj2.setQuery13(one2.getZhongWenQch() + "" + busiPaymentReceived.getQuery10()+" - " + busiPaymentReceived.getQuery11()); // 描述
				String query12 = busiPaymentReceived.getQuery12();
				QueryWrapper<BaUnit> queryWrapperUnit = new QueryWrapper<>();
				queryWrapperUnit.eq("unit_code",query12);
				BaUnit one3 = baUnitService.getOne(queryWrapperUnit, false);
				String unit = one3.getUnitEnName();
				obj2.setQuery14(busiPaymentReceived.getNum01().toString() + unit); // 数量
 				if("是".equals(busiPaymentReceived.getQuery25())){
					obj2.setQuery15(busiPaymentReceived.getNum06().toString()); // 单价
					obj2.setQuery16( busiPaymentReceived.getNum10()+" %"); // 税率
				}else {
					obj2.setQuery15(busiPaymentReceived.getNum05().toString()); // 单价
					obj2.setQuery16("0,000 % n/ (0,000)"); // 单价
				}
				obj2.setQuery17(busiPaymentReceived.getNum07().toString()); // 总金额

//			String Object = one2.getZhongWenQch() + " - " + date + " - "  + num + " - " + list1.get(0).getQuery11()  + " - " +  list1.get(0).getQuery10();
			obj.setQuery11(busiPaymentReceived.getText01()); // Object

			String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.util.Calendar cal=java.util.Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(format));
				cal.add(java.util.Calendar.MONTH, 1);
				System.out.println(sdf.format(cal.getTime()));
				obj.setQuery18(sdf.format(cal.getTime())); // 有效期
			} catch (Exception e) {
				e.printStackTrace();
			}
			obj.setQuery19(""); // 支付方式
			obj.setQuery20(""); // 付款方式
			obj.setQuery21(""); // 地址
 			BusiPoPageList2.add(obj);
		}
		return BusiPoPageList2;
	}

	@Override
	@Transactional
	public List<BusiPoPage> XSFPHZ(List<BusiPaymentReceived> busiPaymentReceivedList) {
		List<BusiPoPage> BusiPoPageList2 = new ArrayList<>();
		for (BusiPaymentReceived busiPaymentReceived : busiPaymentReceivedList) {
			// 查询采购单
			QueryWrapper<BusiPo> BusiPoqueryWrapper = new QueryWrapper<>();
			BusiPoqueryWrapper.lambda().eq(BusiPo::getQuery14,busiPaymentReceived.getQuery14());
//			BusiPoqueryWrapper.lambda().groupBy(BusiPo::getQuery04);
			BusiPo one = busiPoService.getOne(BusiPoqueryWrapper, false);
			// 查询客户
			QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
			MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,one.getQuery24());
			MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
			// 赋值
			BusiPoPage obj = new BusiPoPage();
			obj.setQuery05(busiPaymentReceived.getText01()); // 备注
			obj.setQuery06(one2.getZhongWenQch()); // 客户
			obj.setQuery07(one2.getXingYeFenLei()); // 区域
			obj.setQuery08(one.getQuery22()); // 币种
			obj.setQuery09(busiPaymentReceived.getNum09().toString()); // 发票总额
			obj.setQuery10(busiPaymentReceived.getNum07().toString()); // 应收款金额
			obj.setQuery11(""); // 状态
			obj.setQuery12(one.getLink03()); // 销售发票号
			obj.setQuery13(busiPaymentReceived.getNum03().toString()); // 待支付余额
			obj.setQuery14(busiPaymentReceived.getQuery13()); // PO号码
			obj.setQuery15(one.getNum09().toString()); // 采购单总金额
			obj.setQuery16(""); // 收到款的日期
			obj.setQuery17(busiPaymentReceived.getQuery19()); // 收款单号
			obj.setQuery18(""); // SELECT
			obj.setQuery19(one.getQuery17()); // 内部发票号
			obj.setText05(JSON.toJSONString(busiPaymentReceived));
			obj.setText04(JSON.toJSONString(one));
			BusiPoPageList2.add(obj);
		}
		return BusiPoPageList2;
	}

	@Override
	@Transactional
	public List<WmImNoticeI> CGZJ(String orderids) {
		QueryWrapper<BusiPo> BusiPoqueryWrapper = new QueryWrapper<>();
		BusiPoqueryWrapper.lambda().eq(BusiPo::getQuery04,orderids);
		List<BusiPo> list = busiPoService.list(BusiPoqueryWrapper);
		List<WmImNoticeI> wmImNoticeIS = new ArrayList<>();
		if(CollectionUtil.isNotEmpty(list)){
			for (BusiPo busiPo : list) {
				WmImNoticeI wmImNoticeI = new WmImNoticeI();
				List<MdSup> mdSups = supService.lambdaQuery().eq(MdSup::getGysBianMa, busiPo.getQuery08()).list();
				if (CollectionUtil.isNotEmpty(mdSups)) {
					wmImNoticeI.setSupName(mdSups.get(0).getZhongWenQch());
					wmImNoticeI.setDiZhi(mdSups.get(0).getDiZhi());
					wmImNoticeI.setZhuLianXiRen(mdSups.get(0).getZhuLianXiRen());
					wmImNoticeI.setShouJi(mdSups.get(0).getShouJi());
				}

				List<MdCus> mdCuses = cusService.lambdaQuery().eq(MdCus::getKeHuBianMa, busiPo.getQuery24()).list();
				if (CollectionUtil.isNotEmpty(mdCuses)) {
					wmImNoticeI.setCusName(mdCuses.get(0).getZhongWenQch());
					wmImNoticeI.setXingYeFenLei(mdCuses.get(0).getXingYeFenLei());
				}

				List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiPo.getQuery10()).list();
				if (CollectionUtil.isNotEmpty(mdGoods)) {
					MdGoods mdGoods1 = mdGoods.get(0);
					wmImNoticeI.setShpmiaoshu(mdGoods1.getShpMiaoShu());
					wmImNoticeI.setShpGuiGe(mdGoods1.getShpGuiGe());
					wmImNoticeI.setChpShuXing(mdGoods1.getClassification());
				}

				wmImNoticeI.setTotalamountvat(busiPo.getQuery15()); // 检验类型
				wmImNoticeI.setChukudate(""); // 到货日期
				wmImNoticeI.setPurchasename(busiPo.getQuery16()); // 采购人
				wmImNoticeI.setCreateBy(busiPo.getCreateBy()); // 创建人
				wmImNoticeI.setU8ReturnCode(busiPo.getLink03()); // 销售单号
				wmImNoticeI.setContractlno(busiPo.getQuery14()); // 子PO
				wmImNoticeI.setGoodsCount(busiPo.getNum01().toString()); // 数量
				wmImNoticeI.setGoodsCode(busiPo.getQuery10()); // 商品编码
				wmImNoticeI.setGoodsName(busiPo.getQuery11()); // 商品名称
				wmImNoticeI.setImBeizhu(JSON.toJSONString(busiPo));//实体返回
				wmImNoticeI.setRemark(busiPo.getText04());//验货指示
				wmImNoticeI.setQuery21(busiPo.getQuery18());//验货时间
				wmImNoticeIS.add(wmImNoticeI);
			}
		}
		return wmImNoticeIS;
	}


	@Override
	@Transactional
	public List<WmImNoticeI> ZJDD(String orderids) {
		QueryWrapper<BusiPrdOrd> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(BusiPrdOrd::getQuery04,orderids);
		List<BusiPrdOrd> list1 = busiPrdOrdService.list(queryWrapper);
		List<WmImNoticeI> wmImNoticeIS = new ArrayList<>();
		if(CollectionUtil.isNotEmpty(list1)){
			for (BusiPrdOrd busiPrdOrd : list1) {
				WmImNoticeI wmImNoticeI = new WmImNoticeI();
				List<MdSup> mdSups = supService.lambdaQuery().eq(MdSup::getGysBianMa, busiPrdOrd.getQuery08()).list();
				if (CollectionUtil.isNotEmpty(mdSups)) {
					wmImNoticeI.setSupName(mdSups.get(0).getZhongWenQch());
					wmImNoticeI.setDiZhi(mdSups.get(0).getDiZhi());
					wmImNoticeI.setZhuLianXiRen(mdSups.get(0).getZhuLianXiRen());
					wmImNoticeI.setShouJi(mdSups.get(0).getShouJi());
				}

				List<MdCus> mdCuses = cusService.lambdaQuery().eq(MdCus::getKeHuBianMa, busiPrdOrd.getQuery24()).list();
				if (CollectionUtil.isNotEmpty(mdCuses)) {
					wmImNoticeI.setCusName(mdCuses.get(0).getZhongWenQch());
					wmImNoticeI.setXingYeFenLei(mdCuses.get(0).getXingYeFenLei());
				}

				List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiPrdOrd.getQuery10()).list();
				if (CollectionUtil.isNotEmpty(mdGoods)) {
					MdGoods mdGoods1 = mdGoods.get(0);
					wmImNoticeI.setShpmiaoshu(mdGoods1.getShpMiaoShu());
					wmImNoticeI.setShpGuiGe(mdGoods1.getShpGuiGe());
					wmImNoticeI.setChpShuXing(mdGoods1.getClassification());
				}
				wmImNoticeI.setTotalamountvat(busiPrdOrd.getQuery15()); // 检验类型
				wmImNoticeI.setChukudate(busiPrdOrd.getQuery18()); // 到货日期
				wmImNoticeI.setPurchasename(busiPrdOrd.getQuery16()); // 采购人
				wmImNoticeI.setCreateBy(busiPrdOrd.getCreateBy()); // 创建人
				wmImNoticeI.setU8ReturnCode(busiPrdOrd.getLink03()); // 销售单号
				wmImNoticeI.setContractlno(busiPrdOrd.getQuery14()); // 子PO
				wmImNoticeI.setGoodsCount(busiPrdOrd.getNum01().toString()); // 数量
				wmImNoticeI.setGoodsCode(busiPrdOrd.getQuery10()); // 商品编码
				wmImNoticeI.setGoodsName(busiPrdOrd.getQuery11()); // 商品名称
				wmImNoticeI.setImBeizhu(JSON.toJSONString(busiPrdOrd));//实体返回
				wmImNoticeI.setRemark(busiPrdOrd.getText04());//验货指示
				wmImNoticeI.setQuery21(busiPrdOrd.getQuery21());//验货时间
				wmImNoticeI.setQuery34(busiPrdOrd.getQuery34());//验货时间
				wmImNoticeIS.add(wmImNoticeI);
			}
		}
		return wmImNoticeIS;
	}
}
