package com.base.modules.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiOrdCraftMapper;
import com.base.modules.jeeerp.mapper.BusiPoItemMapper;
import com.base.modules.jeeerp.mapper.BusiPrdOrdItemMapper;
import com.base.modules.jeeerp.mapper.BusiPrdOrdMapper;
import com.base.modules.jeeerp.service.IBusiOmService;
import com.base.modules.jeeerp.service.IBusiPoItemService;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.jeeerp.service.IBusiPrdOrdService;
import com.base.modules.jeeerp.service.impl.BusiOmServiceImpl;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.IMdCusService;
import com.base.modules.jeewms.service.IMdGoodsService;
import com.base.modules.jeewms.service.IWmImNoticeHService;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.vo.WmImNoticeHPage;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PltnPushWms {
    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;

    @Autowired
    private IMdGoodsService goodsService;

    @Autowired
    private IWmImNoticeHService wmImNoticeHService;

    @Autowired
    private BusiPrdOrdMapper busiPrdOrdMapper;
    @Autowired
    private BusiPrdOrdItemMapper busiPrdOrdItemMapper;
    @Autowired
    private IBusiPoItemService busiPoItemService;
    @Autowired
    private PltnSetState pltnSetState;
    @Autowired
    private IBusiOmService busiOmService;
    @Autowired
    private IBusiPoService busiPoService;

    @Autowired
    private IMdCusService mdCusService;
    // 推送采购入库
    @Transactional
    public void putInOrder(List<BusiPoItem> busiPoItemPage) {
        // 获取list第一条
        BusiPoItem pOitem =  busiPoItemPage.get(0);
        WmImNoticeHPage wmImNoticeHPage = new WmImNoticeHPage();
        // 订单类型：采购入库、其他入库
        if("入库预约".equals(pOitem.getQuery18())){
            wmImNoticeHPage.setOrderType("06");
        }else {
            wmImNoticeHPage.setOrderType("09");
        }
        wmImNoticeHPage.setPiClass(pOitem.getQuery18());
        // 采购单号
        wmImNoticeHPage.setU8Dhcode(pOitem.getQuery04());
        // 入库类型
        wmImNoticeHPage.setOrderTypes(pOitem.getQuery18());

        // 内部发票号
        wmImNoticeHPage.setAstreanum(pOitem.getQuery17());
        // 销售单号
        wmImNoticeHPage.setU8ReturnCode(pOitem.getLink03());
        QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
        MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,pOitem.getQuery24());
        MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
        // 客户编码
        wmImNoticeHPage.setCusCode(pOitem.getQuery24());
        // 客户名称
        wmImNoticeHPage.setCusName(one2.getZhongWenQch());
        // 仓库
        wmImNoticeHPage.setStoreCode(pOitem.getQuery07());
        // 主PO号
        wmImNoticeHPage.setImBeizhu(pOitem.getQuery13());
        // 单据备注
        wmImNoticeHPage.setOrderRemark(pOitem.getText01());
        // 预计到货时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ImData = null;
        if(pOitem.getQuery21() != null && pOitem.getQuery21() != ""){
            try {
                ImData = simpleDateFormat.parse(pOitem.getQuery21());
                wmImNoticeHPage.setImData(ImData);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 采购人
        wmImNoticeHPage.setPurchasename(pOitem.getQuery16());
        // 跟单
        wmImNoticeHPage.setPiMaster(pOitem.getQuery28());
        // 业务员
        wmImNoticeHPage.setPlatformCode(pOitem.getQuery30());
        // 税点
        if(pOitem.getNum10() != null){
            wmImNoticeHPage.setImCarNo(pOitem.getNum10().toString());
        }else {
            wmImNoticeHPage.setImCarNo("0");
        }
        // 供应商编码
        wmImNoticeHPage.setSupCode(pOitem.getQuery08());

        List<WmImNoticeI> noticeIList = new ArrayList<>();
        for (BusiPoItem busiPoItem : busiPoItemPage) {
            WmImNoticeI noticeIItem = new WmImNoticeI();
            // 商品编码
            noticeIItem.setGoodsCode(busiPoItem.getQuery10());
            // 商品名称
            noticeIItem.setGoodsName(busiPoItem.getQuery11());
            // 单位
            noticeIItem.setGoodsUnit(busiPoItem.getQuery12());
            // 商品中文名称
            List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiPoItem.getQuery10()).list();
            if(CollectionUtil.isNotEmpty(mdGoods)){
                noticeIItem.setYwMingCheng(mdGoods.get(0).getYwMingCheng());
            }
            // 子PO号
            noticeIItem.setContractlno(busiPoItem.getQuery14());
            // 计划数量
            if(busiPoItem.getNum01() != null){
                noticeIItem.setGoodsCount(busiPoItem.getNum01().toString());
            }
            // 检验类型
            noticeIItem.setTotalamountvat(busiPoItem.getQuery15());
            // 单价
            noticeIItem.setUnitPrice(new BigDecimal(busiPoItem.getNum06()));
            // 备注
            noticeIItem.setImBeizhu(busiPoItem.getText02());
            // 子PO号
            noticeIItem.setGoodsBatch(busiPoItem.getQuery14());
            noticeIList.add(noticeIItem);
        }
        List<WmImNoticeH> imNoticeHS = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getImBeizhu, wmImNoticeHPage.getImBeizhu()).list();
        if (CollectionUtil.isNotEmpty(imNoticeHS)){
            for (WmImNoticeH imNoticeH : imNoticeHS) {
                if (!"07".equals(imNoticeH.getOrderType()) && !"09".equals(imNoticeH.getOrderType())){
                    if (imNoticeH.getOrderType().equals(wmImNoticeHPage.getOrderType())){
                        throw new JeecgBootException("主PO号已存在，请重新输入");
                    }
                }
            }
        }
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        wmImNoticeHService.saveMain(wmImNoticeH, noticeIList);
    }

    // 推送销售出库
    @Transactional
    public void putoutOrder(List<BusiOmItem> busiOmItemPage) {
        //	获取list第一条
        BusiOmItem Omitem =  busiOmItemPage.get(0);
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();


//        List<WmOmNoticeH> omNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getRemarks, Omitem.getQuery13()).list();
//        if (CollectionUtil.isNotEmpty(omNoticeHS)){
//             throw new JeecgBootException("主PO号已存在，不要重复推送");
//        }
        //	订单类型:销售出库、其他出库
        if("出库预约".equals(Omitem.getQuery20())){
            wmOmNoticeH.setOrderTypeCode("12");
        }else {
            wmOmNoticeH.setOrderTypeCode("19");
        }
        //	客户编码
        wmOmNoticeH.setCusCode(Omitem.getQuery08());
        //	客户编码
        wmOmNoticeH.setOrderTypes(Omitem.getQuery20());
        // OMS单号
        wmOmNoticeH.setU8Djcode2(Omitem.getQuery04());
        wmOmNoticeH.setRelevanceNoType("出库预约");
        // OMS采购单号
        wmOmNoticeH.setU8Id(Omitem.getLink02());
        //要求交货时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 仓库
        wmOmNoticeH.setStoreCode(Omitem.getQuery07());

        Date ImData = null;
        try {
            if(StringUtils.isNotEmpty(Omitem.getQuery21()) && Omitem.getQuery21() != ""){
                ImData = simpleDateFormat.parse(Omitem.getQuery21());
                wmOmNoticeH.setDelvData(ImData);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 装箱单号
        wmOmNoticeH.setU8Djcode1(Omitem.getQuery17());
        // 出货方式
        wmOmNoticeH.setShipmentWay(Omitem.getQuery18());
        // 出货地址
        wmOmNoticeH.setShipmentAddress(Omitem.getQuery19());
        // 主PO号
        wmOmNoticeH.setOmBeizhu(Omitem.getText01());
        // 备注
        wmOmNoticeH.setRemarks(Omitem.getQuery13());

        // 跟单
        wmOmNoticeH.setDelvAddr(Omitem.getQuery28());
        // 业务员
        wmOmNoticeH.setPiMaster(Omitem.getQuery30());
        // 税点
        if(Omitem.getNum10() != null){
            wmOmNoticeH.setDelvMember(Omitem.getNum10().toString());
        }

        // 采购单号
        QueryWrapper<WmImNoticeH> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WmImNoticeH::getImBeizhu,Omitem.getQuery13());
        List<WmImNoticeH> list = wmImNoticeHService.list(queryWrapper);
        String  strArr = "";
        if(CollectionUtil.isNotEmpty(list)){
            for (WmImNoticeH busiPo : list) {
                if(strArr == ""){
                    strArr = busiPo.getNoticeId();
                }else {
                    strArr = strArr + "," +  busiPo.getNoticeId();
                }
            }
        }
        wmOmNoticeH.setU8ReturnCode(strArr);
        List<WmOmNoticeI> wmImNoticeIList = new ArrayList<>();
        for (BusiOmItem busiOmItem : busiOmItemPage) {
            WmOmNoticeI wmOmNoticeI  = new WmOmNoticeI();
            // 商品编码
            wmOmNoticeI.setGoodsId(busiOmItem.getQuery10());
            //	商品名称
            wmOmNoticeI.setGoodsName(busiOmItem.getQuery11());
            //	单位
            wmOmNoticeI.setGoodsUnit(busiOmItem.getQuery12());
            // 商品中文名称
            List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiOmItem.getQuery10()).list();
            if(CollectionUtil.isNotEmpty(mdGoods)){
                wmOmNoticeI.setYwMingCheng(mdGoods.get(0).getYwMingCheng());
            }
            //	计划数量
            if(busiOmItem.getNum01() != null){
                wmOmNoticeI.setGoodsQua(busiOmItem.getNum01().toString());
            }
            //	计划数量
            wmOmNoticeI.setCheckname(busiOmItem.getQuery16());
            // 子PO号
            wmOmNoticeI.setGoodsBatch(busiOmItem.getQuery14());
            //	备注
            wmOmNoticeI.setOmBeiZhu(busiOmItem.getText02());
            wmImNoticeIList.add(wmOmNoticeI);
        }
        wmOmNoticeHService.saveMain(wmOmNoticeH, wmImNoticeIList);
    }

    // 推送生产领料
    @Transactional
    public void materialsPutout(BusiPrdOrd busiPrdOrd,String str) {
        QueryWrapper<BusiPrdOrdItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusiPrdOrdItem::getLink02,busiPrdOrd.getQuery04());
        List<BusiPrdOrdItem> busiPrdOrdItems = busiPrdOrdItemMapper.selectList(queryWrapper);
        // 获取list第一条
        WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
        // 订单类型:生产领料
        wmOmNoticeH.setOrderTypeCode("13");
        // 采购包装日期
        wmOmNoticeH.setPackingDate(busiPrdOrd.getQuery03());
        // OMS单号
        wmOmNoticeH.setU8Djcode2(busiPrdOrd.getQuery04());
        // 仓库
        wmOmNoticeH.setStoreCode(busiPrdOrd.getQuery07());

        wmOmNoticeH.setRelevanceNoType("生产订单");
        // 期限
        wmOmNoticeH.setStopDate(busiPrdOrd.getQuery21());
        // 客户
        if(busiPrdOrd.getQuery24() != "" && busiPrdOrd.getQuery24() != null){
            QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
            MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,busiPrdOrd.getQuery24());
            MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
            wmOmNoticeH.setOmBeizhu(one2.getZhongWenQch());
        }
        if("ZJDD".equals(str)){
            wmOmNoticeH.setRemarks(busiPrdOrd.getQuery14());
        }else {
            wmOmNoticeH.setRemarks(busiPrdOrd.getQuery13());
        }
        wmOmNoticeH.setReMember(busiPrdOrd.getQuery29()); // 采购包装指示
        wmOmNoticeH.setReMobile(busiPrdOrd.getQuery30()); // 箱唛
        wmOmNoticeH.setFinishedGoodsCode(busiPrdOrd.getQuery10()); // 成品编码
        List<WmOmNoticeI> wmImNoticeIList = new ArrayList<>();
        for (BusiPrdOrdItem busiPrdOrdItem : busiPrdOrdItems) {
            WmOmNoticeI wmOmNoticeI  = new WmOmNoticeI();
            // 商品编码
            wmOmNoticeI.setGoodsId(busiPrdOrdItem.getQuery10());
            // 商品名称
            wmOmNoticeI.setGoodsName(busiPrdOrdItem.getQuery11());
            // 单位
            wmOmNoticeI.setGoodsUnit(busiPrdOrdItem.getQuery12());
            // 商品中文名称
            List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiPrdOrdItem.getQuery10()).list();
            if(CollectionUtil.isNotEmpty(mdGoods)){
                wmOmNoticeI.setYwMingCheng(mdGoods.get(0).getYwMingCheng());
            }
            // 数量
            if(busiPrdOrdItem.getNum01() != null){
                wmOmNoticeI.setGoodsQua(busiPrdOrdItem.getNum01().toString());
            }
            // 批次
            wmOmNoticeI.setGoodsBatch(busiPrdOrdItem.getQuery14());
            // 备注
            wmOmNoticeI.setOmBeiZhu(busiPrdOrdItem.getText01());
            wmImNoticeIList.add(wmOmNoticeI);
        }
        wmOmNoticeHService.saveMain(wmOmNoticeH, wmImNoticeIList);
    }

    // 推送生产入库
    @Transactional
    public void finishedPutin(List<BusiPrdOrd> busiPrdOrdPage) {
        // 获取list第一条
        BusiPrdOrd prdOrd =  busiPrdOrdPage.get(0);
        WmImNoticeHPage wmImNoticeHPage = new WmImNoticeHPage();
        // 订单类型：生产入库
        wmImNoticeHPage.setOrderType("07");
        // 客户编码
        wmImNoticeHPage.setCusCode(prdOrd.getQuery24());
////        // 客户名称
        try{
            QueryWrapper<MdCus> MdCusqueryWrapper = new QueryWrapper<>();
            MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa, prdOrd.getQuery24());
            MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
            wmImNoticeHPage.setCusName(one2.getZhongWenQch());
        }catch (Exception e){

        }

//         OMS单号
        wmImNoticeHPage.setU8Dhcode(prdOrd.getQuery04());
        //销售订单号
        wmImNoticeHPage.setU8ReturnCode(prdOrd.getLink03());
        // 仓库
        wmImNoticeHPage.setStoreCode(prdOrd.getQuery07());
        // 供应商编码
        QueryWrapper<BusiPo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda().eq(BusiPo::getQuery14,prdOrd.getQuery14());
        BusiPo one = busiPoService.getOne(queryWrapper2, false);
        if(one != null){
            wmImNoticeHPage.setSupCode(one.getQuery08());
        }
        wmImNoticeHPage.setPiClass("生产完工");
        // 内部发票号
        wmImNoticeHPage.setAstreanum(prdOrd.getQuery17());
        QueryWrapper<BusiOm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusiOm::getQuery04,prdOrd.getLink03());
        List<BusiOm> list = busiOmService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            BusiOm busiOm = list.get(0);
            // 销售单号
            wmImNoticeHPage.setU8ReturnCode(prdOrd.getLink03());
            // 客户编码
            wmImNoticeHPage.setCusCode(busiOm.getQuery08());
            // 客户名称
            wmImNoticeHPage.setCusName(busiOm.getQuery09());
        }
        // 采购人
        wmImNoticeHPage.setPurchasename(prdOrd.getQuery16());
        // 主PO号
        wmImNoticeHPage.setImBeizhu(prdOrd.getQuery13());

        // 税率
        wmImNoticeHPage.setTaxRate(prdOrd.getQuery35());

        // 批次
        wmImNoticeHPage.setWhereCon("");
        List<WmImNoticeI> noticeIList = new ArrayList<>();
        for (BusiPrdOrd busiPrdOrd : busiPrdOrdPage) {
            WmImNoticeI noticeIItem = new WmImNoticeI();
            // 商品编码
            noticeIItem.setGoodsCode(busiPrdOrd.getQuery10());
            // 商品名称
            noticeIItem.setGoodsName(busiPrdOrd.getQuery11());
            // 单位
            noticeIItem.setGoodsUnit(busiPrdOrd.getQuery12());
            // 商品中文名称
            List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiPrdOrd.getQuery10()).list();
            if(CollectionUtil.isNotEmpty(mdGoods)){
                noticeIItem.setYwMingCheng(mdGoods.get(0).getYwMingCheng());
            }
            // 子PO号
            noticeIItem.setContractlno(busiPrdOrd.getQuery14());
            // 计划数量
            if(busiPrdOrd.getNum01() != null){
                noticeIItem.setGoodsCount(busiPrdOrd.getNum01().toString());
            }
            // 检验类型
            noticeIItem.setTotalamountvat(busiPrdOrd.getQuery15());
//            // 单价
            noticeIItem.setUnitPrice(new BigDecimal(busiPrdOrd.getQuery33()));
            // 备注
            noticeIItem.setImBeizhu(busiPrdOrd.getText02());
            // 子PO号
            noticeIItem.setGoodsBatch(busiPrdOrd.getQuery14());
            noticeIList.add(noticeIItem);
            busiPrdOrd.setQuery02("已推送");
            busiPrdOrdMapper.updateById(busiPrdOrd);
        }


        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        wmImNoticeHService.saveMain(wmImNoticeH, noticeIList);
    }



    // 推送样品入库
    @Transactional
    public void sampleOrder(List<BusiPoItem> busiPoItem) {
        // 获取list第一条
        BusiPoItem prdOrd =  busiPoItem.get(0);
        WmImNoticeHPage wmImNoticeHPage = new WmImNoticeHPage();
        // 订单类型：生产入库
        wmImNoticeHPage.setOrderType("06");
        // 客户编码
        wmImNoticeHPage.setCusCode(prdOrd.getQuery24());
        QueryWrapper<MdCus> MdCusqueryWrapper =  new QueryWrapper<>();
        MdCusqueryWrapper.lambda().eq(MdCus::getKeHuBianMa,prdOrd.getQuery24());
        MdCus one2 = mdCusService.getOne(MdCusqueryWrapper, false);
        // 客户名称
        wmImNoticeHPage.setCusName(one2.getZhongWenQch());
        // 供应商编码
        wmImNoticeHPage.setSupCode(prdOrd.getQuery08());
        // 采购单号
        wmImNoticeHPage.setU8Dhcode(prdOrd.getQuery04());
        wmImNoticeHPage.setPiClass("来样管理");
        // 销售单号
        wmImNoticeHPage.setU8ReturnCode(prdOrd.getLink03());
        // 内部发票号
        wmImNoticeHPage.setAstreanum(prdOrd.getQuery17());
        // 采购人
        wmImNoticeHPage.setPurchasename(prdOrd.getQuery16());
        // 主PO号
        wmImNoticeHPage.setImBeizhu(prdOrd.getQuery13());
        // 批次
        wmImNoticeHPage.setWhereCon("");
        // 仓库
        wmImNoticeHPage.setStoreCode(prdOrd.getQuery07());
        // 采购人
        wmImNoticeHPage.setPurchasename(prdOrd.getQuery16());
        // 跟单
        wmImNoticeHPage.setPiMaster(prdOrd.getQuery28());
        // 业务员
        wmImNoticeHPage.setPlatformCode(prdOrd.getQuery30());
        // 税点
        if(prdOrd.getNum10() != null){
            wmImNoticeHPage.setImCarNo(prdOrd.getNum10().toString());
        }else {
            wmImNoticeHPage.setImCarNo("0");
        }
        // 预计到货时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ImData = null;
        if(StringUtils.isNotEmpty(prdOrd.getQuery21())){
            try {
                ImData = simpleDateFormat.parse(prdOrd.getQuery21());
                wmImNoticeHPage.setImData(ImData);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<WmImNoticeI> noticeIList = new ArrayList<>();
        for (BusiPoItem busiPrdOrd : busiPoItem) {
            WmImNoticeI noticeIItem = new WmImNoticeI();
            // 商品编码
            noticeIItem.setGoodsCode(busiPrdOrd.getQuery10());
            // 商品名称
            noticeIItem.setGoodsName(busiPrdOrd.getQuery11());
            // 单位
            noticeIItem.setGoodsUnit(busiPrdOrd.getQuery12());
            // 商品中文名称
            List<MdGoods> mdGoods = goodsService.lambdaQuery().eq(MdGoods::getShpBianMa, busiPrdOrd.getQuery10()).list();
            if(CollectionUtil.isNotEmpty(mdGoods)){
                noticeIItem.setYwMingCheng(mdGoods.get(0).getYwMingCheng());
            }
            // 子PO号
            noticeIItem.setContractlno(busiPrdOrd.getQuery14());
            // 计划数量
            if(busiPrdOrd.getNum01() != null){
                noticeIItem.setGoodsCount(busiPrdOrd.getNum01().toString());
            }
            // 检验类型
            noticeIItem.setTotalamountvat(busiPrdOrd.getQuery15());
//            // 单价
            noticeIItem.setUnitPrice(new BigDecimal(busiPrdOrd.getNum06()));
            // 备注
            noticeIItem.setImBeizhu(busiPrdOrd.getText02());
            // 子PO号
            noticeIItem.setGoodsBatch(busiPrdOrd.getQuery14());
            noticeIList.add(noticeIItem);
        }
        QueryWrapper<BusiPoItem> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(prdOrd.getLink02())){
            String[] split = prdOrd.getLink02().split("-");
            queryWrapper.lambda().like(BusiPoItem::getLink02,split[0]);
            queryWrapper.lambda().eq(BusiPoItem::getQuery01,"DYJD");
            queryWrapper.lambda().isNull(BusiPoItem::getQuery02);
            List<BusiPoItem> list = busiPoItemService.list(queryWrapper);
            if(CollectionUtil.isEmpty(list)){
                pltnSetState.setState("入库中","CGD",split[0]);
            }
        }
      /*  List<WmImNoticeH> imNoticeHS = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getImBeizhu, wmImNoticeHPage.getImBeizhu()).list();
        if (CollectionUtil.isNotEmpty(imNoticeHS)){
            for (WmImNoticeH imNoticeH : imNoticeHS) {
                if (!imNoticeH.getOrderType().equals("07") && !imNoticeH.getOrderType().equals("09")){
                    if (imNoticeH.getOrderType().equals(wmImNoticeHPage.getOrderType())){
                        throw new JeecgBootException("主PO号已存在，请不要重复推送");
                    }
                }
            }
        }*/
        WmImNoticeH wmImNoticeH = new WmImNoticeH();
        BeanUtils.copyProperties(wmImNoticeHPage, wmImNoticeH);
        wmImNoticeHService.saveMain(wmImNoticeH, noticeIList);
    }

}
