package com.base.modules.jeewms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.base.common.api.vo.Result;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.RedisUtil;
import com.base.modules.jeewms.service.IBaUnitService;
import com.base.modules.jeewms.service.IWmImNoticeHService;
import com.base.modules.jeewms.service.IWmImNoticeIService;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import org.apache.commons.lang3.StringUtils;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import com.base.modules.util.NotNullUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.DoubleBinaryOperator;

@Service
public class SmsSendImpl {

    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;
    @Autowired
    private WmImNoticeIMapper wmImNoticeIMapper;
    @Autowired
    private MvGoodsMapper mvGoodsMapper;
    @Autowired
    private WmOmQmIMapper wmOmQmIMapper;
    @Autowired
    private WmOmQmIServiceImpl wmOmQmIService;
    @Autowired
    private WmInQmIMapper wmImQmIMapper;
    @Autowired
    private BaKwMapper baKwMapper;
    @Autowired
    private WmToUpGoodsMapper wmToUpGoodsMapper;
    @Autowired
    private WmToUpGoodsServiceImpl wmToUpGoodsService;
    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;
    @Autowired
    private WmToDownGoodsServiceImpl wmToDownGoodsService;
    @Autowired
    private WmToMoveGoodsMapper wmToMoveGoodsMapper;
    @Autowired
    private WmInQmIServiceImpl wmInQmIService;

    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IBaUnitService baUnitService;


    //入库
    @Value("${jeecg.pritIp}")
    private String pritIp;

    //箱唛
    @Value("${jeecg.pritIp2}")
    private String pritIp2;

    //出库
    @Value("${jeecg.pritIp3}")
    private String pritIp3;

    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;


    public void run() {

    }

    public void runothertask() {
        long start = System.currentTimeMillis();
        String hql = null;
        try {//更新收货已完成
            List<WmImNoticeI> WmInNoticeIlist = new ArrayList<WmImNoticeI>();
            QueryWrapper<WmImNoticeI> queryWrapper = new QueryWrapper<>();
            queryWrapper.ne("bin_pre", "Y");
            WmInNoticeIlist = wmImNoticeIMapper.selectList(queryWrapper);
            for (WmImNoticeI wmImNoticeIEntity : WmInNoticeIlist) {
                if (Double.parseDouble(wmImNoticeIEntity.getGoodsCount()) <= Double.parseDouble(wmImNoticeIEntity.getGoodsQmCount())) {
                    wmImNoticeIEntity.setBinPre("Y");
                    wmImNoticeIMapper.updateById(wmImNoticeIEntity);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {//查找储位 设置批量收货基本数量
            List<WmInQmI> WmInQmlist = new ArrayList<WmInQmI>();
            QueryWrapper<WmInQmI> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("bin_sta", "N");
            queryWrapper1.isNull("bin_id");
            WmInQmlist = wmImQmIMapper.selectList(queryWrapper1);
            for (WmInQmI wmInQmIEntity : WmInQmlist) {

                if (wmInQmIEntity.getImNoticeId().startsWith("YK")) {//越库任务
                    wmInQmIEntity.setBinId(wmInQmIEntity.getImNoticeId());
                    wmInQmIEntity.setBinSta("Y");
                    wmImQmIMapper.updateById(wmInQmIEntity);
                } else {
                    QueryWrapper<WmInQmI> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("tin_id", wmInQmIEntity.getTinId());
                    queryWrapper2.eq("bin_sta", "N");
                    queryWrapper2.isNull("bin_id");
                    Map<String, Object> binMap = null;
                    if (!wmImQmIMapper.selectMaps(queryWrapper2).isEmpty()) {
                        binMap = wmImQmIMapper.selectMaps(queryWrapper2).get(0);
                    }
                    if (binMap == null) {
                        if (wmInQmIEntity.getTinTj() == null) {
                            wmInQmIEntity.setTinTj("0");
                        }
                        String zuidatiji = null;
                        try {
                            if (StringUtil.isEmpty(wmInQmIEntity.getTinTj())) {
                                zuidatiji = "1";
                            } else {
                                zuidatiji = wmInQmIEntity.getTinTj();
                            }
                        } catch (Exception e) {
                            zuidatiji = "1";
                        }
                        String binplantuopan = "0";
                        try {
                            WmImNoticeI wmImNoticeIEntity = wmImNoticeIMapper.selectById(wmInQmIEntity.getImNoticeItem());

                            if (StringUtil.isNotEmpty(wmImNoticeIEntity.getBinPlan())) {
                                binplantuopan = wmImNoticeIEntity.getBinPlan();
                            }
                        } catch (Exception e) {

                        }
                        QueryWrapper<WmInQmI> queryWrapper3 = new QueryWrapper<>();
                        queryWrapper2.eq("bin_sta", "Y");
                        queryWrapper2.eq("im_notice_item", wmInQmIEntity.getImNoticeItem());
                        queryWrapper2.isNull("bin_id");
                        List<WmInQmI> WmInQmbinlist = wmImQmIMapper.selectList(queryWrapper3);

                        String lastbin = ""; //本单上一个储位
                        String lastbinfenzu = "";   //本单上一个储位分组
                        if (WmInQmbinlist != null && WmInQmbinlist.size() > 0) {
                            lastbin = WmInQmbinlist.get(0).getBinId();
                            QueryWrapper<BaKw> queryWrapper4 = new QueryWrapper<>();
                            queryWrapper4.eq("ku_wei_bian_ma", lastbin);
                            List<BaKw> mdblist = baKwMapper.selectList(queryWrapper4);
                            if (mdblist != null && mdblist.size() > 0) {
                                lastbinfenzu = mdblist.get(0).getMaxArea();
                            }
                        }
                        binMap = baKwMapper.findBinId(lastbinfenzu, zuidatiji, wmInQmIEntity.getGoodsId(), wmInQmIEntity.getCusCode(), "");
                        if (binMap != null) {
                            wmInQmIEntity.setBinId(binMap.get("binid").toString());
                            wmImQmIMapper.updateById(wmInQmIEntity);
                        } else {
                            binMap = baKwMapper.findBinId("", zuidatiji, wmInQmIEntity.getGoodsId(), wmInQmIEntity.getCusCode(), binplantuopan);
                            if (binMap != null) {
                                wmInQmIEntity.setBinId(binMap.get("binid").toString());
                                wmImQmIMapper.updateById(wmInQmIEntity);
                            }
                        }
                    }
                }
            }
            //更新基本数量和单位
            QueryWrapper<WmInQmI> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.isNull("base_unit");
            WmInQmlist = wmImQmIMapper.selectList(queryWrapper4);
            for (WmInQmI wmInQmIEntity : WmInQmlist) {
                MvGoods mvgoods = new MvGoods();
                mvgoods = findMvGoods(wmInQmIEntity.getGoodsId());
                if (mvgoods != null && StringUtils.isNotEmpty(mvgoods.getBaseunit())) {
                    wmInQmIEntity.setBaseUnit(NotNullUtils.tranObj2Str(mvgoods.getBaseunit()));
                }
                if (mvgoods != null && StringUtils.isNotEmpty(mvgoods.getBaseunit()) && StringUtils.isNotEmpty(mvgoods.getShlDanWei())
                        && !mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
                    try {
                        wmInQmIEntity.setBaseGoodscount(String.valueOf(Double.parseDouble(mvgoods.getChlShl())
                                * Double.parseDouble(wmInQmIEntity.getQmOkQuat())));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    try {
                        wmInQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
                                .getZhlKg()) * Double.parseDouble(wmInQmIEntity.getQmOkQuat())));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                } else {
                    wmInQmIEntity
                            .setBaseGoodscount(wmInQmIEntity.getQmOkQuat());
                    try {
                        wmInQmIEntity.setTinZhl(String.valueOf(Double.parseDouble(mvgoods
                                .getZhlKg())
                                * Double.parseDouble(wmInQmIEntity.getQmOkQuat())));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
                //wmInQmIEntity.setProData(new Date());
                wmInQmIService.saveOrUpdate(wmInQmIEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 更新上架商品基本单位基本数量
            List<WmToUpGoods> WmToUpGoodslist = new ArrayList<WmToUpGoods>();
            QueryWrapper<WmToUpGoods> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.isNull("base_unit");
            WmToUpGoodslist = wmToUpGoodsMapper.selectList(queryWrapper5);
            for (WmToUpGoods wmToUpGoodsEntity : WmToUpGoodslist) {
                MvGoods mvgoods = new MvGoods();
                try {
                    mvgoods = findMvGoods(wmToUpGoodsEntity.getGoodsId());
                    wmToUpGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
                    wmToUpGoodsEntity.setGoodsUnit(mvgoods.getShlDanWei());

                    if (!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())) {
                        try {
                            wmToUpGoodsEntity.setBaseGoodscount(String.valueOf(Double.parseDouble(mvgoods.getChlShl()) * Double.parseDouble(wmToUpGoodsEntity.getGoodsQua())));
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    } else {
                        wmToUpGoodsEntity.setBaseGoodscount(wmToUpGoodsEntity.getGoodsQua());
                    }
                } catch (Exception e) {

                }
                wmToUpGoodsService.saveOrUpdate(wmToUpGoodsEntity);
            }
            // 更新基本单位基本数量

        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            // 更新下架商品基本单位基本数量
            List<WmToDownGoods> WmToDownGoodslist = new ArrayList<WmToDownGoods>();
            QueryWrapper<WmToDownGoods> queryWrapper6 = new QueryWrapper<>();
            queryWrapper6.isNull("base_unit");
            WmToDownGoodslist = wmToDownGoodsMapper.selectList(queryWrapper6);
            for (WmToDownGoods wmToDownGoodsEntity : WmToDownGoodslist) {
                MvGoods mvgoods = new MvGoods();
                try {
                    mvgoods = findMvGoods(wmToDownGoodsEntity.getGoodsId());
                    wmToDownGoodsEntity.setGoodsUnit(mvgoods.getBaseunit());
                    wmToDownGoodsEntity.setGoodsQua(wmToDownGoodsEntity.getGoodsQuaok());
                    wmToDownGoodsEntity.setBaseGoodscount(wmToDownGoodsEntity.getGoodsQuaok());
                    wmToDownGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
                } catch (Exception e) {

                }
                wmToDownGoodsService.saveOrUpdate(wmToDownGoodsEntity);
            }

            // 更新下架商品基本单位基本数量


        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            // 更新库存转移
            List<WmToMoveGoods> WmToMoveGoodslist = new ArrayList<WmToMoveGoods>();
            QueryWrapper<WmToMoveGoods> queryWrapper7 = new QueryWrapper<>();
            queryWrapper7.isNull("order_id");
            queryWrapper7.eq("move_sta", "已完成");
            WmToMoveGoodslist = wmToMoveGoodsMapper.selectList(queryWrapper7);

            for (WmToMoveGoods wmToMoveGoodsEntity : WmToMoveGoodslist) {
                if (!checkstcok(wmToMoveGoodsEntity.getBinFrom(), wmToMoveGoodsEntity.getTinFrom(), wmToMoveGoodsEntity.getGoodsId(), wmToMoveGoodsEntity.getGoodsProData(), wmToMoveGoodsEntity.getGoodsQua())) {
                    wmToMoveGoodsEntity.setMoveSta("库存不足");
                    wmToMoveGoodsEntity.setRunSta("库存不足");
                    wmToMoveGoodsMapper.updateById(wmToMoveGoodsEntity);
                    continue;
                }
                QueryWrapper<BaKw> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("ku_wei_bian_ma", wmToMoveGoodsEntity.getBinTo());
                BaKw mdbin = baKwMapper.selectOne(queryWrapper4);
                if (mdbin == null) {
                    wmToMoveGoodsEntity.setMoveSta("库位不存在");
                    wmToMoveGoodsEntity.setRunSta("库位不存在");
                    wmToMoveGoodsMapper.updateById(wmToMoveGoodsEntity);
                    continue;
                }
                wmToMoveGoodsEntity.setRunSta("已完成");

                WmToDownGoods wmToDownGoods = new WmToDownGoods();
                wmToDownGoods.setCreateBy(wmToMoveGoodsEntity.getCreateBy());
                wmToDownGoods.setCreateTime(wmToMoveGoodsEntity.getCreateTime());
                wmToDownGoods.setCreateName(wmToMoveGoodsEntity.getCreateName());
                wmToDownGoods.setBinIdFrom(wmToMoveGoodsEntity.getTinFrom());//下架托盘
                wmToDownGoods.setKuWeiBianMa(wmToMoveGoodsEntity.getBinFrom());//储位
                wmToDownGoods.setBinIdTo("ZY");//到托盘
                wmToDownGoods.setCusCode(wmToMoveGoodsEntity.getCusCode());//货主
                wmToDownGoods.setGoodsId(wmToMoveGoodsEntity.getGoodsId());//
                wmToDownGoods.setGoodsProData(wmToMoveGoodsEntity.getGoodsProData());//生产日期
                wmToDownGoods.setGoodsBatch(wmToMoveGoodsEntity.getGoodsBatch());
                wmToDownGoods.setOrderId("ZY");//出货通知单
                if (StringUtil.isEmpty(wmToMoveGoodsEntity.getOrderIdI())) {
                    wmToDownGoods.setOrderIdI(wmToMoveGoodsEntity.getId());

                } else {
                    wmToDownGoods.setOrderIdI(wmToMoveGoodsEntity.getOrderIdI());

                }
                MvGoods mvgoods = new MvGoods();
                mvgoods = findMvGoods(wmToMoveGoodsEntity.getGoodsId());
                wmToDownGoods.setGoodsName(mvgoods.getGoodsName());
                if (StringUtil.isEmpty(wmToMoveGoodsEntity.getBaseGoodscount())) {
                    wmToDownGoods.setBaseGoodscount(wmToMoveGoodsEntity.getGoodsQua());
                } else {
                    wmToDownGoods.setBaseGoodscount(wmToMoveGoodsEntity.getBaseGoodscount());
                }
                wmToDownGoods.setBaseUnit(mvgoods.getBaseunit());//基本单位
                wmToDownGoods.setGoodsUnit(wmToMoveGoodsEntity.getGoodsUnit());//出货单位
                wmToDownGoods.setGoodsQua(wmToMoveGoodsEntity.getGoodsQua());//出货数量
                wmToDownGoods.setGoodsQuaok(wmToMoveGoodsEntity.getGoodsQua());//出货数量
                wmToDownGoods.setDownSta("已复核");
                wmToDownGoodsMapper.insert(wmToDownGoods);
                WmToUpGoods wmToUpGoodsEntity = new WmToUpGoods();
                wmToUpGoodsEntity.setCreateBy(wmToMoveGoodsEntity.getCreateBy());
                wmToUpGoodsEntity.setCreateTime(wmToMoveGoodsEntity.getCreateTime());
                wmToUpGoodsEntity.setCreateName(wmToMoveGoodsEntity.getCreateName());
                wmToUpGoodsEntity.setGoodsId(wmToMoveGoodsEntity.getGoodsId());
                if (StringUtil.isEmpty(wmToMoveGoodsEntity.getToGoodsProData())) {
                    wmToUpGoodsEntity.setGoodsProData(wmToMoveGoodsEntity.getGoodsProData());
                } else {
                    wmToUpGoodsEntity.setGoodsProData(wmToMoveGoodsEntity.getToGoodsProData());
                }
                wmToUpGoodsEntity.setGoodsBatch(wmToMoveGoodsEntity.getToGoodsBatch());
                wmToUpGoodsEntity.setGoodsQua(wmToMoveGoodsEntity.getGoodsQua());
                wmToUpGoodsEntity.setGoodsUnit(wmToMoveGoodsEntity.getGoodsUnit());
                if (StringUtil.isEmpty(wmToMoveGoodsEntity.getBaseGoodscount())) {
                    wmToUpGoodsEntity.setBaseGoodscount(wmToMoveGoodsEntity.getGoodsQua());
                } else {
                    wmToUpGoodsEntity.setBaseGoodscount(wmToMoveGoodsEntity.getBaseGoodscount());
                }
                wmToUpGoodsEntity.setBaseUnit(mvgoods.getBaseunit());
                wmToUpGoodsEntity.setGoodsName(mvgoods.getGoodsName());
                if (StringUtil.isEmpty(wmToMoveGoodsEntity.getOrderIdI())) {
                    wmToUpGoodsEntity.setOrderIdI(wmToMoveGoodsEntity.getId());
                } else {
                    wmToUpGoodsEntity.setOrderIdI(wmToMoveGoodsEntity.getOrderIdI());
                }
                wmToUpGoodsEntity.setOrderId("ZY");
                wmToUpGoodsEntity.setBinId(wmToMoveGoodsEntity.getTinId());
                wmToUpGoodsEntity.setKuWeiBianMa(wmToMoveGoodsEntity.getBinTo());
                wmToUpGoodsEntity.setCusCode(wmToMoveGoodsEntity.getToCusCode());
                wmToUpGoodsEntity.setActTypeCode("ZY");
                wmToUpGoodsMapper.insert(wmToUpGoodsEntity);
                if (!StringUtil.isNotEmpty(wmToMoveGoodsEntity.getOrderTypeCode())) {
                    wmToMoveGoodsEntity.setOrderTypeCode("KCZY");
                }
                wmToMoveGoodsEntity.setOrderId("ZY");
                wmToMoveGoodsMapper.updateById(wmToMoveGoodsEntity);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void rundowntask(String noticeId) {
            String key = "xjdsrw" + noticeId;
        System.out.println(key+"1，单号：=====" + new Date());

        try {// 生成下架任务
                System.out.println("下架定时任务开始执行：=====" + new Date());
                try {
                    String redisvalue = (String)redisUtil.get(key);
                    if (StringUtil.isNotEmpty(redisvalue)) {
                        System.out.println("上次下架定时任务还未结束：=====" + new Date());
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                List<WmOmNoticeI> WmOmNoticeIlist = new ArrayList<WmOmNoticeI>();
                QueryWrapper<WmOmNoticeI> queryWrapper8 = new QueryWrapper<>();
                queryWrapper8.eq("plan_sta", "N");
                queryWrapper8.eq("om_notice_id", noticeId);
                queryWrapper8.orderByDesc("create_time");
                WmOmNoticeIlist = wmOmNoticeIMapper.selectList(queryWrapper8);
                String prodate = null;
                String binom = "";
                String goodsbatch = "";
                String kwType = "";
                redisUtil.set(key, WmOmNoticeIlist.size() + DateUtils.formatDate(new Date()), 300);
                System.out.println(key+"2，开始执行：=====" + new Date());

            for (WmOmNoticeI wmOmNoticeIEntity : WmOmNoticeIlist) {
                System.out.println(key+"3，下架定时任务开始执行：=====" + new Date());
                prodate = null;
                    binom = "";
                    goodsbatch = "";
                    kwType = "";
                    System.out.println("wmOmNoticeIEntity+++++++++" + wmOmNoticeIEntity);
                    WmOmNoticeH wmOmNoticeH = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, wmOmNoticeIEntity.getOmNoticeId()).one();
                    try{
                        if ("19".equals(wmOmNoticeH.getOrderTypeCode()) && "退货".equals(wmOmNoticeH.getOrderTypes())) {
                            kwType = "不良品";
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        WvStock wvstock = wmOmNoticeIMapper.findStockSum1(wmOmNoticeIEntity.getGoodsId(),
                                wmOmNoticeIEntity.getTenantId(), null);
                        if (wvstock == null || wvstock.getGoodsQua() <= 0.01) {
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    binom = wmOmNoticeIEntity.getBinOm();

                    try {
                        if (ObjectUtils.isNotEmpty(wmOmNoticeIEntity.getGoodsProData())) {
                            prodate = DateUtils.date2Str(wmOmNoticeIEntity.getGoodsProData(), DateUtils.date_sdf.get());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ObjectUtils.isNotEmpty(wmOmNoticeIEntity.getGoodsBatch())) {
                        goodsbatch = wmOmNoticeIEntity.getGoodsBatch();
                    }
                    try {
                        double omcount = 0.00;
                        double omcountok = 0.00;
                        double omcountwq = 0.00;
                        List<WmOmQmI> WmOmQmIlist = new ArrayList<WmOmQmI>();
                        QueryWrapper<WmOmQmI> queryWrapper9 = new QueryWrapper<>();
                        queryWrapper9.eq("iom_notice_item", wmOmNoticeIEntity.getId());
                        queryWrapper9.eq("tenant_id", wmOmNoticeIEntity.getTenantId());
                        WmOmQmIlist = wmOmQmIMapper.selectList(queryWrapper9);
                        for (WmOmQmI wmOmQmIEntity : WmOmQmIlist) {

                            try {
                                omcountok = omcountok + Double.parseDouble(wmOmQmIEntity.getBaseGoodscount());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            omcount = Double.parseDouble(wmOmNoticeIEntity.getBaseGoodscount());// 总出货数量
                        } catch (Exception e) {
                            omcount = Double.parseDouble(wmOmNoticeIEntity.getGoodsQua());// 不存在总出货数量
                        }
                        omcountwq = omcount - omcountok;// 未清基本数量
                        System.out.println("未清基本数量===:" + omcountwq);
                        if (Double.doubleToLongBits(omcountwq) > Double.doubleToLongBits(0)) {// 如果数量大于0
                            List<Map<String, Object>> result = wmOmNoticeIMapper.findStock1(binom, null, wmOmNoticeIEntity.getGoodsId(), prodate, goodsbatch, wmOmNoticeIEntity.getCusCode(), null, wmOmNoticeIEntity.getTenantId(), kwType);

                            if (CollectionUtil.isEmpty(result)) {
                                continue;
                            }

                            List<Double> lists = new ArrayList<>();

                            try {
                                for (Map<String, Object> map : result) {
                                    Object goodsqua = map.get("goods_qua");
                                    lists.add(Convert.toDouble(goodsqua));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println(key+"3,1，存在库存开始计算：=====" + new Date());

                            if (lists.contains(omcountwq)) {
                                System.out.println(key+"4，整箱，下架定时任务开始执行：=====" + new Date());

                                for (Map<String, Object> map : result) {
                                    Double goodsqua = Convert.toDouble(map.get("goods_qua"));
                                    if (Double.doubleToLongBits(Double.valueOf(goodsqua)) != Double.doubleToLongBits(omcountwq)) {
                                        continue;
                                    } else {
                                        System.out.println(key+"4，1 整箱，分配开始：=====" + new Date());

                                        WmOmQmI wmOmQmIEntity = new WmOmQmI();
                                        wmOmQmIEntity.setCreateTime(new Date());
                                        wmOmQmIEntity.setImCusCode(wmOmNoticeH.getU8Djcode1());//装箱单号
                                        wmOmQmIEntity.setOmBeiZhu(wmOmNoticeIEntity.getOmBeiZhu());
                                        wmOmQmIEntity.setCreateBy(wmOmNoticeIEntity.getCreateBy());
                                        wmOmQmIEntity.setCreateName(wmOmNoticeIEntity.getCreateName());
                                        wmOmQmIEntity.setCusCode(wmOmNoticeIEntity.getCusCode());
                                        wmOmQmIEntity.setOmNoticeId(wmOmNoticeIEntity.getOmNoticeId());
                                        wmOmQmIEntity.setIomNoticeItem(wmOmNoticeIEntity.getId());
                                        wmOmQmIEntity.setBinSta("I");//预分配
                                        wmOmQmIEntity.setGoodsId(wmOmNoticeIEntity.getGoodsId());
                                        wmOmQmIEntity.setBarcode(wmOmNoticeIEntity.getBarcode());
                                        wmOmQmIEntity.setGoodsName(wmOmNoticeIEntity.getGoodsName());
                                        wmOmQmIEntity.setBaozhiqi(wmOmNoticeIEntity.getBzhiQi());
                                        wmOmQmIEntity.setGoodsUnit(wmOmNoticeIEntity.getGoodsUnit());
                                        wmOmQmIEntity.setTenantId(wmOmNoticeIEntity.getTenantId());
                                        wmOmQmIEntity.setBinId(NotNullUtils.tranObj2Str(map.get("ku_wei_bian_ma")));
                                        wmOmQmIEntity.setTinId(NotNullUtils.tranObj2Str(map.get("bin_id")));
                                        wmOmQmIEntity.setBaseUnit(NotNullUtils.tranObj2Str(map.get("base_unit")));
                                        wmOmQmIEntity.setGoodsBatch(NotNullUtils.tranObj2Str(map.get("goods_batch")));
                                        wmOmQmIEntity.setProData(NotNullUtils.tranObj2Str(map.get("goods_pro_data")));
                                        wmOmQmIEntity.setCusName(NotNullUtils.tranObj2Str(map.get("zhong_wen_qch")));
                                        wmOmQmIEntity.setGoodsName(NotNullUtils.tranObj2Str(map.get("shp_ming_cheng")));
                                        wmOmQmIEntity.setQmOkQuat(Double.toString(omcountwq));
                                        wmOmQmIEntity.setBaseGoodscount(Double.toString(omcountwq));
                                        wmOmQmIService.save(wmOmQmIEntity);
                                        System.out.println(key+"4，2 整箱，分配结束：=====" + new Date());

                                        omcountwq = 0;
                                    }
                                }
                            } else {
                                for (int i = 0; i < result.size(); i++) {
                                    System.out.println(key+"5，非整箱开始执行：=====" + new Date());

                                    try {
                                        double bin_qua = 0;
                                        try {
                                            bin_qua = Double.valueOf(result.get(i).get("goods_qua").toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            continue;
                                        }
                                        if (bin_qua <= 0.01) {
                                            continue;
                                        }
                                        System.out.println(key+"5，1非整箱，分配开始：=====" + new Date());

                                        WmOmQmI wmOmQmIEntity = new WmOmQmI();
                                        wmOmQmIEntity.setCreateTime(new Date());
                                        wmOmQmIEntity.setImCusCode(wmOmNoticeH.getU8Djcode1());//装箱单号
                                        wmOmQmIEntity.setOmBeiZhu(wmOmNoticeIEntity.getOmBeiZhu());
                                        wmOmQmIEntity.setCreateBy(wmOmNoticeIEntity.getCreateBy());
                                        wmOmQmIEntity.setCreateName(wmOmNoticeIEntity.getCreateName());
                                        wmOmQmIEntity.setCusCode(wmOmNoticeIEntity.getCusCode());
                                        wmOmQmIEntity.setOmNoticeId(wmOmNoticeIEntity.getOmNoticeId());
                                        wmOmQmIEntity.setIomNoticeItem(wmOmNoticeIEntity.getId());
                                        wmOmQmIEntity.setBinSta("I");//预分配
                                        wmOmQmIEntity.setGoodsId(wmOmNoticeIEntity.getGoodsId());
                                        wmOmQmIEntity.setBarcode(wmOmNoticeIEntity.getBarcode());
                                        wmOmQmIEntity.setGoodsName(wmOmNoticeIEntity.getGoodsName());
                                        wmOmQmIEntity.setBaozhiqi(wmOmNoticeIEntity.getBzhiQi());
                                        wmOmQmIEntity.setGoodsUnit(wmOmNoticeIEntity.getGoodsUnit());
                                        wmOmQmIEntity.setTenantId(wmOmNoticeIEntity.getTenantId());

                                        if (omcountwq > bin_qua) {
                                            wmOmQmIEntity.setBinId(NotNullUtils.tranObj2Str(result.get(i).get("ku_wei_bian_ma")));
                                            wmOmQmIEntity.setTinId(NotNullUtils.tranObj2Str(result.get(i).get("bin_id")));
                                            wmOmQmIEntity.setBaseUnit(NotNullUtils.tranObj2Str(result.get(i).get("base_unit")));
                                            wmOmQmIEntity.setGoodsBatch(NotNullUtils.tranObj2Str(result.get(i).get("goods_batch")));

                                            wmOmQmIEntity.setProData(NotNullUtils.tranObj2Str(result.get(i).get("goods_pro_data")));
                                            wmOmQmIEntity.setCusName(NotNullUtils.tranObj2Str(result.get(i).get("zhong_wen_qch")));
                                            wmOmQmIEntity.setGoodsName(NotNullUtils.tranObj2Str(result.get(i).get("shp_ming_cheng")));
                                            omcountwq = omcountwq - bin_qua;
                                            wmOmQmIEntity.setQmOkQuat(Double.toString(bin_qua));
                                            wmOmQmIEntity.setBaseGoodscount(Double.toString(bin_qua));
                                            wmOmQmIService.save(wmOmQmIEntity);
                                            System.out.println(key+"5，1，1非整箱，分配结束1：=====" + new Date());

                                        } else {

                                            wmOmQmIEntity.setBinId(NotNullUtils.tranObj2Str(result.get(i).get("ku_wei_bian_ma")));
                                            wmOmQmIEntity.setTinId(NotNullUtils.tranObj2Str(result.get(i).get("bin_id")));
                                            wmOmQmIEntity.setBaseUnit(NotNullUtils.tranObj2Str(result.get(i).get("base_unit")));
                                            wmOmQmIEntity.setGoodsBatch(NotNullUtils.tranObj2Str(result.get(i).get("goods_batch")));
                                            wmOmQmIEntity.setProData(NotNullUtils.tranObj2Str(result.get(i).get("goods_pro_data")));
                                            wmOmQmIEntity.setCusName(NotNullUtils.tranObj2Str(result.get(i).get("zhong_wen_qch")));
                                            wmOmQmIEntity.setGoodsName(NotNullUtils.tranObj2Str(result.get(i).get("shp_ming_cheng")));
                                            wmOmQmIEntity.setQmOkQuat(Double.toString(omcountwq));
                                            wmOmQmIEntity.setBaseGoodscount(Double.toString(omcountwq));
                                            wmOmQmIService.save(wmOmQmIEntity);
                                            System.out.println(key+"5，1，2非整箱，分配结束2：=====" + new Date());
                                            omcountwq = 0;
                                            break;
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (omcountwq <= 0.01) {
                            wmOmNoticeIEntity.setPlanSta("Y");//执行后设置为Y
                        } else {
                            wmOmNoticeIEntity.setPlanSta("N");//未清数量大于0，挂单，下次继续执行
                        }
                        wmOmNoticeIEntity.setWaitQua(omcountwq + "");
                        wmOmNoticeIMapper.updateById(wmOmNoticeIEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                redisUtil.del(key);
            System.out.println(key+"6，下架定时任务正常结束：=====" + new Date());

             } catch (Exception e) {
                e.printStackTrace();
            System.out.println(key+"7，下架定时任务异常结束：=====" + new Date());

            redisUtil.del(key);
            }
    }


    private MvGoods findMvGoods(String goodsId) {
        String goods = goodsId;
        QueryWrapper<MvGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_code", goods);
        MvGoods mvGoods = mvGoodsMapper.selectOne(queryWrapper);
        return mvGoods;
    }

    public boolean checkstcok(String binid, String tinid, String goodsid, String prodate, String basecount) {
        boolean flag = false;
        try {
            //查询库存信息
            List<Map<String, Object>> list = findStockList(binid, tinid, goodsid, prodate, null, null, null);
            if (list.size() > 0) {
                if (Double.parseDouble(list.get(0).get("goods_qua").toString()) >= Double.parseDouble(basecount)) {
                    flag = true;
                }
            }
        } catch (Exception e) {
        }
        return flag;
    }

    private List<Map<String, Object>> findStockList(String binId, String tinId, String goods, String prodate, String goodsbatch, String cuCode, String hiti) {
        List<Map<String, Object>> list = wmOmNoticeIMapper.findStock(binId, tinId, goods, prodate, goodsbatch, cuCode, hiti);
        return list;
    }


    public void labelPrinting(String id, List<String> tinids, String div,String query15) {
        WmImNoticeI imNoticeI = wmImNoticeIService.getById(id);
        WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeI.getImNoticeId()).one();

        List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, imNoticeI.getGoodsUnit()).list();
        BaUnit baUnit = new BaUnit();
        if (CollectionUtil.isNotEmpty(baUnits)) {
            baUnit = baUnits.get(0);
        }

        List<String> tids2 = new ArrayList<>();
        for (String tinid : tinids) {
            tids2.add(tinid);
            tids2.add(tinid);
        }
        synchronized (this) {
            for (String tinid : tids2) {
                Map map1 = new LinkedHashMap();
                //客户
                map1.put("data01", wmImNoticeH.getCusName());
                map1.put("data02", wmImNoticeH.getU8ReturnCode());
                map1.put("type", "ruku");
                Map map2 = new LinkedHashMap();
                List<Map> mapList = new ArrayList<>();
                map2.put("data01", imNoticeI.getGoodsCode());
                map2.put("data02", wmImNoticeH.getCusName());
                map2.put("data03", imNoticeI.getGoodsName());
//                if (baUnit != null) {
//                    map2.put("data04", imNoticeI.getShenqingsl() + " " + baUnit.getUnitEnName());
//                } else {
//                    map2.put("data04", imNoticeI.getShenqingsl());
//                }
                if (baUnit != null) {
                    map2.put("data04", query15 + " " + baUnit.getUnitEnName());
                } else {
                    map2.put("data04", query15);
                }
                map2.put("data05", imNoticeI.getContractlno());
                map2.put("data06", DateUtil.format(imNoticeI.getCreateTime(), "yyyy-MM-dd"));
                map2.put("data07", tinid);
                mapList.add(map2);
                map1.put("printitem", mapList);
                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String post = HttpUtil.post(pritIp + "util/uwms/client/print/ruku/admin", Json);
                System.out.println(post + "打印结果");
            }
        }
    }

    public void labelPrintings2(String id) {
        synchronized (this){
            String tinids = "T0000";
            String tinids1 = "T0000";
            WmOmQmI omQmI = wmOmQmIService.getById(id);
            WmOmNoticeH wmOmNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getOmNoticeId, omQmI.getOmNoticeId()).one();
            List<WmOmQmI> wmOmQmIS1 = wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeHS.getOmNoticeId()).orderByAsc(WmOmQmI::getCreateTime).list();
            List<WmOmNoticeH> omNoticeHS = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getU8Djcode1, wmOmNoticeHS.getU8Djcode1()).list();
            List<WmOmQmI> omQmIS = new ArrayList<>();
            for (WmOmNoticeH wmOmNoticeH : omNoticeHS) {
                List<WmOmQmI> wmOmQmIS = wmOmQmIService.lambdaQuery()
                        .eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId())
                        .orderByAsc(WmOmQmI::getCreateTime)
                        .list();
                if (CollectionUtil.isNotEmpty(wmOmQmIS)) {
                    for (WmOmQmI omQmIs : wmOmQmIS) {
                        Integer integer = Convert.toInt(tinids.substring(tinids.length() - 4, tinids.length())) + 1;
                        tinids = "T" + String.format("%04d", integer);
                        if (StringUtils.isEmpty(omQmIs.getFirstRq())) {
                            omQmIs.setFirstRq(tinids);
                            wmOmQmIService.updateById(omQmIs);
                        }
                    }
                }
            }
            for (WmOmQmI omQmIs : wmOmQmIS1) {
                Integer integer = Convert.toInt(tinids1.substring(tinids1.length() - 4, tinids1.length())) + 1;
                tinids1 = "T" + String.format("%04d", integer);
                if (StringUtils.isEmpty(omQmIs.getSecondRq())) {
                    omQmIs.setSecondRq(tinids1);
                    wmOmQmIService.updateById(omQmIs);
                }
            }
            omQmIS.addAll(wmOmQmIS1);
            int size = omQmIS.size();
            int lengths = 1;
            String omid = (String) redisUtil.get(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId());
            if (StringUtils.isNotEmpty(omid)) {
                String[] split = omid.split(",");
                List<String> list = Arrays.asList(split);
                int index = -1;
                index = list.indexOf(id);
                if (index == -1) {
                    lengths = split.length + 1;
                } else {
                    lengths = index + 1;
                }
                redisUtil.set(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId(), omid + "," + id);
            } else {
                redisUtil.set(omQmI.getOmNoticeId() + "-" + omQmI.getGoodsId(), id);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WmOmQmI omQmI2 = wmOmQmIService.getById(id);
            Integer integer = Convert.toInt(omQmI2.getSecondRq().substring(omQmI2.getSecondRq().length() - 4, tinids1.length()));
            Map map1 = new LinkedHashMap();
            map1.put("data01", wmOmNoticeHS.getU8Djcode1());
            map1.put("data02", omQmI2.getFirstRq());
//        map1.put("data03", lengths+"/"+size);
            map1.put("data03", integer + "/" + size);
            System.out.println(omQmI.getFirstRq() + "--------");
            JSONObject jsonObj = new JSONObject(map1);
            String Json = jsonObj.toString();

            for (int i = 0; i < 2; i++) {
                String post = HttpUtil.post(pritIp3 + "util/uwms/client/print/chuku/admin", Json);
                System.out.println(post + "---打印次数----" + i);
            }
        }

    }

    public void labelPrints(String id, List<String> tinids, String div,String query15) {
        synchronized (this){
            if (StringUtils.isNotEmpty(div)) {
                Integer divs = Convert.toInt(div);
                WmImNoticeI imNoticeI = wmImNoticeIService.getById(id);
                WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeI.getImNoticeId()).one();
                List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, imNoticeI.getGoodsUnit()).list();
                BaUnit baUnit = new BaUnit();
                if (CollectionUtil.isNotEmpty(baUnits)) {
                    baUnit = baUnits.get(0);
                }
                List<String> tids2 = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(tinids)) {
                    for (String tinid : tinids) {
                        tids2.add(tinid);
                        tids2.add(tinid);
                    }
                }
                Map map1 = new LinkedHashMap();
                //客户
                map1.put("data01", wmImNoticeH.getCusName());
                map1.put("data02", wmImNoticeH.getU8ReturnCode());
                map1.put("type", "xiangmai");

                Map map2 = new LinkedHashMap();
                List<Map> mapList = new ArrayList<>();
                map2.put("data01", imNoticeI.getGoodsCode());
                map2.put("data02", imNoticeI.getGoodsName());

//                if (baUnit != null) {
//                    map2.put("data03", imNoticeI.getShenqingsl() + " " + baUnit.getUnitEnName());
//                } else {
//                    map2.put("data03", imNoticeI.getShenqingsl());
//                }
                if (baUnit != null) {
                    map2.put("data03", query15 + " " + baUnit.getUnitEnName());
                } else {
                    map2.put("data03", query15);
                }
                map2.put("data04", imNoticeI.getContractlno());
                mapList.add(map2);
                map1.put("printitem", mapList);
                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
                if (CollectionUtil.isNotEmpty(tids2)) {
                    for (String tinid : tids2) {
//                    String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
                        String post = HttpUtil.post(pritIp2 + "util/uwms/client/print/ruku/admin", Json);
                        System.out.println("箱唛" + post);
                    }
                } else {
                    for (int i = 0; i < divs; i++) {
//                    String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
                        String post = HttpUtil.post(pritIp2 + "util/uwms/client/print/ruku/admin", Json);
                        System.out.println("箱唛" + post);
                    }
                }
            } else {
                WmImNoticeI imNoticeI = wmImNoticeIService.getById(id);
                WmImNoticeH wmImNoticeH = wmImNoticeHService.lambdaQuery().eq(WmImNoticeH::getNoticeId, imNoticeI.getImNoticeId()).one();
                List<BaUnit> baUnits = baUnitService.lambdaQuery().eq(BaUnit::getUnitZhName, imNoticeI.getGoodsUnit()).list();
                BaUnit baUnit = new BaUnit();
                if (CollectionUtil.isNotEmpty(baUnits)) {
                    baUnit = baUnits.get(0);
                }
                List<String> tids2 = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(tinids)) {
                    for (String tinid : tinids) {
                        tids2.add(tinid);
                        tids2.add(tinid);
                    }
                }
                Map map1 = new LinkedHashMap();
                //客户
                map1.put("data01", wmImNoticeH.getCusName());
                map1.put("data02", wmImNoticeH.getU8ReturnCode());
                map1.put("type", "xiangmai");

                Map map2 = new LinkedHashMap();
                List<Map> mapList = new ArrayList<>();
                map2.put("data01", imNoticeI.getGoodsCode());
                map2.put("data02", imNoticeI.getGoodsName());
                if (baUnit != null) {
                    map2.put("data03", imNoticeI.getShenqingsl() + " " + baUnit.getUnitEnName());
                } else {
                    map2.put("data03", imNoticeI.getShenqingsl());
                }
                map2.put("data04", imNoticeI.getContractlno());
                mapList.add(map2);
                map1.put("printitem", mapList);
                JSONObject jsonObj = new JSONObject(map1);
                String Json = jsonObj.toString();
                if (CollectionUtil.isNotEmpty(tids2)) {
                    for (String tinid : tids2) {
//                    String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
                        String post = HttpUtil.post(pritIp2 + "util/uwms/client/print/ruku/admin", Json);
                        System.out.println("箱唛" + post);
                    }
                } else {
//                String post = HttpUtil.post(pritIp2+"util/uwms/client/print/xiangmai/admin", Json);
                    String post = HttpUtil.post(pritIp2 + "util/uwms/client/print/ruku/admin", Json);
                    System.out.println("箱唛" + post);
                }
            }
        }

    }

    public WvStock findSumById(String goodsId) {
//        WvStock wvstock = new WvStock();
        WvStock wvstock = wmOmNoticeIMapper.findStockSum2(goodsId, null);
        return wvstock;
    }
}
