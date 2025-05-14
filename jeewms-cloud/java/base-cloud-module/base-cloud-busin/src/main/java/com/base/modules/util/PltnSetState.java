package com.base.modules.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.system.vo.LoginUser;
import com.base.modules.jeeerp.entity.*;
import com.base.modules.jeeerp.mapper.BusiPrdOrdItemMapper;
import com.base.modules.jeeerp.mapper.BusiPrdOrdMapper;
import com.base.modules.jeeerp.service.*;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.WmImNoticeHPage;
import com.google.gson.internal.$Gson$Preconditions;
import me.zhyd.oauth.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class PltnSetState {
    @Autowired
    private IBusiPoService busiPoService;
    @Autowired
    private IBusiOmService busiOmService;
    @Autowired
    private IBusiPrdOrdService busiPrdOrdService;
    @Autowired
    private IBusiPaymentReceivedService busiPaymentReceivedService;
    @Autowired
    private IBusiPrdOrdItemService busiPrdOrdItemService;

    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;

    /**
     * /**
     * OMS操作修改订单状态
     *
     * @param State     状态
     * @param ordertype 类型
     * @param orderCode 单号
     * @return
     */
    @Transactional
    public void setState(String State, String ordertype, String orderCode) {
        if ("CGFKJH".equals(ordertype) || "CGD".equals(ordertype) || "YPFKJH".equals(ordertype) || "RKYY".equals(ordertype)) { // 采购付款计划,新增采购单,样品付款计划
            QueryWrapper<BusiPo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPo::getQuery04, orderCode);
            List<BusiPo> list = busiPoService.list(queryWrapper);
            for (BusiPo busiPo : list) {
                if(!"已完成".equals(busiPo.getQuery02())){
                    busiPo.setQuery02(State);
                    busiPoService.updateById(busiPo);
                }
             }
        } else if ("XSSKJH".equals(ordertype) || "XSD".equals(ordertype) || "CKYY".equals(ordertype)) { // 销售收款计划，销售单状态
            QueryWrapper<BusiOm> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiOm::getQuery04, orderCode);
            List<BusiOm> list = busiOmService.list(queryWrapper);
            for (BusiOm busiOm : list) {
                busiOm.setQuery02(State);
                busiOmService.updateById(busiOm);
            }
        } else if ("JG".equals(ordertype)) { // 加工订单状态
            QueryWrapper<BusiPrdOrd> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPrdOrd::getQuery04, orderCode);
            List<BusiPrdOrd> list = busiPrdOrdService.list(queryWrapper);
            for (BusiPrdOrd busiPrdOrd : list) {
                busiPrdOrd.setQuery02(State);
                busiPrdOrdService.updateById(busiPrdOrd);
            }
        } else if ("SKD".equals(ordertype) || "FKD".equals(ordertype)) { // 修改计划收款单状态/修改付款单状态
            QueryWrapper<BusiPaymentReceived> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(BusiPaymentReceived::getQuery04, orderCode);
            List<BusiPaymentReceived> list = busiPaymentReceivedService.list(queryWrapper);
            for (BusiPaymentReceived busiPaymentReceived : list) {
                busiPaymentReceived.setQuery02(State);
                busiPaymentReceivedService.updateById(busiPaymentReceived);
            }
        }
    }

    /**
     * wms入库修改OMS状态
     *
     * @param type              类型
     * @param wmImNoticeH       WMS入库单
     * @param wmToUpGoodsEntity WMS上架数据
     * @return
     */
    public void wmsPoSetState(String type, WmImNoticeH wmImNoticeH, WmToUpGoods wmToUpGoodsEntity) {
        if ("number".equals(type)) {
            if ("06".equals(wmImNoticeH.getOrderTypeCode())) {
                // 修改采购的入库数量
                QueryWrapper<BusiPo> QueryWrapper = new QueryWrapper<>();
                QueryWrapper.lambda().eq(BusiPo::getQuery14, wmToUpGoodsEntity.getGoodsBatch());
                BusiPo one = busiPoService.getOne(QueryWrapper, false);
                if (one != null) {
                    double num03 = 0;
                    if (one.getNum03() != null) {
                        num03 = one.getNum03(); //
                    }
                    double v = Double.parseDouble(wmToUpGoodsEntity.getBaseGoodscount()); // 当前入库数量
                    num03 = num03 + v;
                    one.setNum03(num03);
                    one.setNum02(one.getNum01() - num03);
                    busiPoService.updateById(one);
                }
            } else if ("07".equals(wmImNoticeH.getOrderTypeCode())) {
                //  修改加工的入库数量
                QueryWrapper<BusiPrdOrd> QueryWrapper = new QueryWrapper<>();
                QueryWrapper.lambda().eq(BusiPrdOrd::getQuery14, wmToUpGoodsEntity.getGoodsBatch());
                QueryWrapper.lambda().eq(BusiPrdOrd::getQuery01, "SCWG");
                BusiPrdOrd one = busiPrdOrdService.getOne(QueryWrapper, false);
                if (one != null) {
                    double num03 = 0;
                    if (one.getNum03() != null) {
                        num03 = one.getNum03(); //
                    }
                    double v = Double.parseDouble(wmToUpGoodsEntity.getBaseGoodscount()); // 当前入库数量
                    num03 = num03 + v;
                    one.setNum03(num03);
                    one.setNum02(one.getNum01() - num03);
                    busiPrdOrdService.updateById(one);
                }
            }
        } else if ("state".equals(type)) {
            if ("06".equals(wmImNoticeH.getOrderTypeCode())) {
                // 修改采购的入库状态
                QueryWrapper<BusiPo> QueryWrapper = new QueryWrapper<>();
                QueryWrapper.lambda().eq(BusiPo::getQuery13, wmImNoticeH.getImBeizhu());
                List<BusiPo> list = busiPoService.list(QueryWrapper);
                if (CollectionUtil.isNotEmpty(list)) {
                    double num02 = 0;
                    for (BusiPo busiPo : list) {
                        if (busiPo.getNum02() != null) {
                            if (!(Math.abs(busiPo.getNum02()) < 0.000001)) {
                                num02 = busiPo.getNum02();
                            }
                        }
                    }
                    if (Math.abs(num02) < 0.000001) {
                        for (BusiPo busiPo : list) {
                            if(!"已完成".equals(busiPo.getQuery02()) && !"打样已完成".equals(busiPo.getQuery02())){
                                busiPo.setQuery02("已完成");
                                busiPoService.updateById(busiPo);
                            }
                        }
                    }
                }

            } else if ("07".equals(wmImNoticeH.getOrderTypeCode())) {
                // 修改加工的入库状态
                QueryWrapper<BusiPrdOrd> QueryWrapper = new QueryWrapper<>();
                QueryWrapper.lambda().eq(BusiPrdOrd::getQuery13, wmImNoticeH.getImBeizhu());
                QueryWrapper.lambda().eq(BusiPrdOrd::getQuery01, "SCWG");
                List<BusiPrdOrd> list = busiPrdOrdService.list(QueryWrapper);

                if (CollectionUtil.isNotEmpty(list)) {
                    double num02 = 0;
                    for (BusiPrdOrd busiPo : list) {
                        if (busiPo.getNum02() != null) {
                            if (!(Math.abs(busiPo.getNum02()) < 0.000001)) {
                                num02 = busiPo.getNum02();
                            }
                        }
                    }
                    if (Math.abs(num02) < 0.000001) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = simpleDateFormat.format(new Date());
                        for (BusiPrdOrd busiPo : list) {
                            busiPo.setQuery02("已完成");
                            if (StringUtils.isEmpty(busiPo.getQuery22())) {
                                busiPo.setQuery22(format);
                            }
                            busiPrdOrdService.updateById(busiPo);
                        }
                        // 更新包装单或者质检单状态跟完成时间
                        QueryWrapper<BusiPrdOrd> QueryWrapper2 = new QueryWrapper<>();
                        QueryWrapper2.lambda().eq(BusiPrdOrd::getQuery13, wmImNoticeH.getImBeizhu());
                        QueryWrapper2.lambda().and(wq -> {
                            wq.eq(BusiPrdOrd::getQuery01, "ZJDD");
                            wq.or().eq(BusiPrdOrd::getQuery01, "JG");
                        });
                        BusiPrdOrd one = busiPrdOrdService.getOne(QueryWrapper2, false);
                        one.setQuery02("已完成");
                        if (StringUtils.isEmpty(one.getQuery22())) {
                            one.setQuery22(format);
                        }
                        busiPrdOrdService.updateById(one);
                    }
                }
            }
        }
    }

    /**
     * wms出库修改OMS状态
     *
     * @param type          类型
     * @param wmOmNoticeH   WMS出库单
     * @param wmToDownGoods WMS下架数据
     * @return
     */
    @Transactional
    public void wmsOmSetState(String type, WmOmNoticeH wmOmNoticeH, WmToDownGoods wmToDownGoods) {
        if ("number".equals(type)) {
            if ("12".equals(wmOmNoticeH.getOrderTypeCode())) { // 采购或者样品推过来的出库单
                // 更新busi_po出库数量
                QueryWrapper<BusiPo> QueryWrapper = new QueryWrapper<>();
                QueryWrapper.lambda().eq(BusiPo::getQuery14, wmToDownGoods.getGoodsBatch());
                QueryWrapper.lambda().eq(BusiPo::getQuery01, "CGD");
                BusiPo one1 = busiPoService.getOne(QueryWrapper, false);
                if (one1 != null) {
                    double num20 = 0;
                    QueryWrapper<WmToDownGoods> QueryWrapperdown = new QueryWrapper<>();
                    QueryWrapperdown.lambda().eq(WmToDownGoods::getGoodsBatch, wmToDownGoods.getGoodsBatch());
                    QueryWrapperdown.lambda().isNull(WmToDownGoods::getActTypeCode);
                    QueryWrapperdown.lambda().like(WmToDownGoods::getOrderId, "CK");
                    List<WmToDownGoods> list = wmToDownGoodsService.list(QueryWrapperdown);
                    for (WmToDownGoods toDownGoods : list) {
                        double v = Double.parseDouble(toDownGoods.getBaseGoodscount());
                        num20 = num20 + v;
                    }
                    one1.setNum20(num20);
                    one1.setNum19(one1.getNum01() - num20);
                    busiPoService.updateById(one1);
                }
            } else if ("13".equals(wmOmNoticeH.getOrderTypeCode())) { // 加工推过来的领料单
                // 查询更新busi_prd_ord领料数量
                if (!StringUtil.isBlankOrNull(wmOmNoticeH.getRemarks())) {

                    QueryWrapper<BusiPrdOrd> QueryWrapper = new QueryWrapper<>();
                    QueryWrapper.lambda().eq(BusiPrdOrd::getQuery13, wmOmNoticeH.getRemarks());
                    QueryWrapper.lambda().eq(BusiPrdOrd::getQuery01, "SCDD");
                    BusiPrdOrd one = busiPrdOrdService.getOne(QueryWrapper, false);
                    if (one != null) {
                        QueryWrapper<BusiPrdOrdItem> QueryWrapper2 = new QueryWrapper<>();
                        QueryWrapper2.lambda().eq(BusiPrdOrdItem::getLink02, one.getQuery04());
                        QueryWrapper2.lambda().eq(BusiPrdOrdItem::getQuery10, wmToDownGoods.getGoodsId());
                        BusiPrdOrdItem one1 = busiPrdOrdItemService.getOne(QueryWrapper2, false);
                        if (one1 != null) {
                            double num03 = 0;
                            QueryWrapper<WmToDownGoods> QueryWrapperdown = new QueryWrapper<>();
                            QueryWrapperdown.lambda().eq(WmToDownGoods::getOrderId, wmToDownGoods.getOrderId());
                            QueryWrapperdown.lambda().eq(WmToDownGoods::getGoodsId, wmToDownGoods.getGoodsId());
                            QueryWrapperdown.lambda().isNull(WmToDownGoods::getActTypeCode);

                            QueryWrapperdown.lambda().ne(WmToDownGoods::getOrderType, "CWZY");
                            List<WmToDownGoods> list = wmToDownGoodsService.list(QueryWrapperdown);
                            for (WmToDownGoods toDownGoods : list) {
                                double v = Double.parseDouble(toDownGoods.getBaseGoodscount());
                                num03 = num03 + v;
                            }
                            one1.setNum03(num03);
                            one1.setNum02(one1.getNum01() - num03);
                            busiPrdOrdItemService.updateById(one1);
                        }
                    }
                }
            }
        } else if ("state".equals(type)) {
            if ("12".equals(wmOmNoticeH.getOrderTypeCode())) { // 采购或者样品推过来的出库单
                if (!StringUtil.isBlankOrNull(wmOmNoticeH.getRemarks())) {
                    QueryWrapper<BusiPo> QueryWrapper = new QueryWrapper<>();
                    QueryWrapper.lambda().eq(BusiPo::getQuery13, wmOmNoticeH.getRemarks());
                    QueryWrapper.lambda().eq(BusiPo::getQuery01, "CGD");
                    List<BusiPo> list = busiPoService.list(QueryWrapper);
                    Boolean isallfinish = true;
                    if (CollectionUtil.isNotEmpty(list)) {
                        double num20 = 0;
                        for (BusiPo busiPo : list) {
                            try {
                                num20 = busiPo.getNum20();
                                if (num20 < busiPo.getNum01()) {
                                    isallfinish = false;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                isallfinish = false;
                            }

                        }
                        if (isallfinish) {
                            for (BusiPo busiPo : list) {
                                LoginUser login = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                                busiPo.setQuery39(login.getUsername());
                                busiPo.setQuery02("已完成");
                                busiPoService.updateById(busiPo);
                            }
                        }
                    }
                }

            } else if ("13".equals(wmOmNoticeH.getOrderTypeCode())) { // 加工推过来的领料单
                if (!StringUtil.isBlankOrNull(wmOmNoticeH.getRemarks())) {

                    QueryWrapper<BusiPrdOrd> QueryWrapper = new QueryWrapper<>();
                    QueryWrapper.lambda().eq(BusiPrdOrd::getQuery13, wmOmNoticeH.getRemarks());
                    QueryWrapper.lambda().eq(BusiPrdOrd::getQuery01, "SCDD");
                    BusiPrdOrd one = busiPrdOrdService.getOne(QueryWrapper, false);
                    if (one != null) {
                        QueryWrapper<BusiPrdOrdItem> QueryWrapper2 = new QueryWrapper<>();
                        QueryWrapper2.lambda().eq(BusiPrdOrdItem::getLink02, one.getQuery04());
                        List<BusiPrdOrdItem> list = busiPrdOrdItemService.list(QueryWrapper2);
                        if (CollectionUtil.isNotEmpty(list)) {
                            Boolean isallfinish = true;
                            for (BusiPrdOrdItem busiPo : list) {
                                double num03 = 0;
                                try {
                                    num03 = busiPo.getNum03();
                                    if (num03 < busiPo.getNum01()) {
                                        isallfinish = false;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    isallfinish = false;
                                }
                            }
                            if (isallfinish) {
                                one.setQuery02("已完成");
                                busiPrdOrdService.updateById(one);
                                // 更新包装单或者质检单状态跟完成时间
                                QueryWrapper<BusiPrdOrd> QueryWrapper3 = new QueryWrapper<>();
                                QueryWrapper3.lambda().eq(BusiPrdOrd::getQuery13, wmOmNoticeH.getRemarks());
                                QueryWrapper3.lambda().and(wq -> {
                                    wq.eq(BusiPrdOrd::getQuery01, "ZJDD");
                                    wq.or().eq(BusiPrdOrd::getQuery01, "JG");
                                });
                                BusiPrdOrd one2 = busiPrdOrdService.getOne(QueryWrapper3, false);
                                one2.setQuery02("领料完成");
                                busiPrdOrdService.updateById(one2);
                            }
                        }
                    }
                }
            }
        }
    }
}
