package com.base.modules.jeewms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.WmImNoticeHPage;
import com.base.modules.util.ConstUtil;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: U8消息推送
 * @Author: base-boot
 * @Date:   2021-11-22
 * @Version: V1.0
 */
@Service
public class WmU8PushMessageServiceImpl extends ServiceImpl<WmU8PushMessageMapper, WmU8PushMessage> implements IWmU8PushMessageService {

    public static final String wm_sta0 = "初始化";
    public static final String wm_sta1 = "计划中";
    public static final String wm_sta2 = "操作中";
    public static final String wm_sta3 = "已删除";
    public static final String wm_sta4 = "已完成";
    public static final String wm_sta5 = "复核中";
    public static final String wm_sta6 = "复核完成";

    @Autowired
    private WmU8PushMessageMapper wmU8PushMessageMapper;
    @Autowired
    private IWmPlatIoService wmPlatIoService;
    @Autowired
    private IWmImNoticeIService wmImNoticeIService;
    @Autowired
    private IWmInQmIService wmInQmIService;
    @Autowired
    private IMdCusService mdCusService;
    @Autowired
    private WmImNoticeHMapper wmImNoticeHMapper;
    @Autowired
    private IWmImNoticeHService wmImNoticeHService;
    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IBaKwService baKwService;

    @Autowired
    private WmOmNoticeHMapper wmOmNoticeHMapper;
    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;
    @Autowired
    private MdCusMapper mdCusMapper;
    @Autowired
    private MdCusOtherMapper mdCusOtherMapper;
    @Autowired
    private SmsSendImpl SmsSendImpl;
    @Autowired
    private WmPlatIoMapper wmPlatIoMapper;
    @Autowired
    private MdGoodsMapper mdGoodsMapper;
    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;


    @Override
    @Transactional
    public Result<?> sendWms(WmImNoticeHPage wmImNoticeHPage, WmU8PushMessage wmU8PushMessage) {

        return Result.ok("推送成功！");
    }

    @Override
    @Transactional
    public void saveCaigouruku(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        //查询今天入库量
        int count = wmImNoticeHService.lambdaQuery()
                .eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode())
                .apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'")
                .count();
        int newcount = count + 1;
        String noticeid = "RK";
        if("06".equals(wmImNoticeH.getOrderTypeCode())){
            noticeid = "CGRK";
        }else if("07".equals(wmImNoticeH.getOrderTypeCode())){
            noticeid = "SCRK";
        }else if("09".equals(wmImNoticeH.getOrderTypeCode())){
            noticeid = "QTRK";
        }
        noticeid = noticeid
                + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                + "-"
                + String.format("%04d", newcount);
        wmImNoticeH.setNoticeId(noticeid);
        wmImNoticeH.setImSta(wm_sta1);
        //查询供应商
        MdCus mdCus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();
        if (mdCus != null) {
            wmImNoticeH.setCusName(mdCus.getZhongWenQch());
        }
        wmImNoticeHMapper.insert(wmImNoticeH);

        //月台
        WmPlatIo wmPlatIo = new WmPlatIo();
        wmPlatIo.setCarno(wmImNoticeH.getImCarNo());
        wmPlatIo.setDocId(wmImNoticeH.getNoticeId());
        wmPlatIo.setPlanIndata(wmImNoticeH.getImData());
        wmPlatIo.setPlatId(wmImNoticeH.getPlatformCode());
        wmPlatIo.setPlatSta(wm_sta1);
        wmPlatIo.setPlatBeizhu("司机:" + wmImNoticeH.getImCarDri() + "电话:"
                + wmImNoticeH.getImCarMobile());
        wmPlatIoService.save(wmPlatIo);

        if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
            List<WmImNoticeI> noticeIList = packNoticeI(wmImNoticeH, wmImNoticeIList);
            wmImNoticeIService.saveBatch(noticeIList);
        }
    }

    @Override
    @Transactional
    public void saveXiaoshoufahuo(WmOmNoticeH wmOmNoticeH, List<WmOmNoticeI> wmOmNoticeIList) {
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        int count = wmOmNoticeHService.lambdaQuery().apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").count();
        int newcount = count + 1;
        String noticeid = "";
        if("12".equals(wmOmNoticeH.getOrderTypeCode())){
            noticeid = "XSCK"
                    + today
                    + "-"
                    + String.format("%04d", newcount);
        }else if("13".equals(wmOmNoticeH.getOrderTypeCode())||"14".equals(wmOmNoticeH.getOrderTypeCode())){
            noticeid = "LLCK"
                    + today
                    + "-"
                    + String.format("%04d", newcount);
        }
        if (StringUtils.isEmpty(wmOmNoticeH.getOmSta())) {
            wmOmNoticeH.setOmSta(ConstUtil.wm_sta1);
        }
        //添加月台
        AddWmPlatIo(wmOmNoticeH, wmOmNoticeH.getOmNoticeId());
        wmOmNoticeH.setOmNoticeId(noticeid);
        //查询cus
        MdCus mdCus = mdCusMapper.selectOne(new QueryWrapper<MdCus>().lambda().eq(MdCus::getKeHuBianMa, wmOmNoticeH.getCusCode()));
        if (mdCus != null) {
            wmOmNoticeH.setCusName(mdCus.getZhongWenQch());
        }
        //查询第三方客户
        QueryWrapper<MdCusOther> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ke_hu_bian_ma", wmOmNoticeH.getOcusCode());
        MdCusOther mdCusOther = mdCusOtherMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(mdCusOther)) {
            wmOmNoticeH.setOcusCode(wmOmNoticeH.getOcusCode());
            wmOmNoticeH.setOcusName(mdCusOther.getZhongWenQch());
        }
        wmOmNoticeHMapper.insert(wmOmNoticeH);
        if (wmOmNoticeIList != null && wmOmNoticeIList.size() > 0) {
            for (WmOmNoticeI entity : wmOmNoticeIList) {
                entity.setBaseGoodscount(entity.getGoodsQua());
                entity.setFlag(wmOmNoticeH.getFlag());
                //验证库存
//                if (!checkstcoka(entity)) {
//                    throw new RuntimeException("库存不足");
//                }
                entity = findWmOmNoticeIE(entity, wmOmNoticeH, wmOmNoticeH.getOmNoticeId());
                wmOmNoticeIMapper.insert(entity);
            }
        }
//        SmsSendImpl.rundowntask();
    }

    @Override
    @Transactional
    public void saveCaigoudingdan(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));
        //查询今天入库量
        int count = wmImNoticeHService.lambdaQuery()
                .eq(WmImNoticeH::getOrderTypeCode, wmImNoticeH.getOrderTypeCode())
                .apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'")
                .count();
        int newcount = count + 1;
        String noticeid = "CGDD"
                + DateUtils.date2Str(new SimpleDateFormat("yyyyMMdd"))
                + "-"
                + String.format("%04d", newcount);
        wmImNoticeH.setNoticeId(noticeid);
//        wmImNoticeH.setImSta(wm_sta4);//直接完成
        //查询供应商
        MdCus mdCus = mdCusService.lambdaQuery().eq(MdCus::getKeHuBianMa, wmImNoticeH.getCusCode()).one();
        if (mdCus != null) {
            wmImNoticeH.setCusName(mdCus.getZhongWenQch());
        }
        wmImNoticeHMapper.insert(wmImNoticeH);

        if (wmImNoticeIList != null && wmImNoticeIList.size() > 0) {
            for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                wmImNoticeI.setCusCode(wmImNoticeH.getCusCode());
                //查询商品
                MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).one();
                wmImNoticeI.setGoodsCode(mdGoods.getShpBianMa());
                wmImNoticeI.setGoodsName(mdGoods.getShpMingCheng());
                wmImNoticeI.setGoodsUnit(mdGoods.getSnpTray());
                wmImNoticeI.setGoodsUnit(mdGoods.getShlDanWei());
                wmImNoticeI.setBaseUnit(mdGoods.getJshDanWei());
                wmImNoticeI.setBinPre("H");
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                wmImNoticeI.setImBeizhu(wmImNoticeH.getImBeizhu());
                wmImNoticeI.setImCusCode(wmImNoticeH.getImCusCode());
                wmImNoticeIService.save(wmImNoticeI);
            }
        }
    }

    private List<WmImNoticeI> packNoticeI(WmImNoticeH wmImNoticeH, List<WmImNoticeI> wmImNoticeIList) {
        List<WmImNoticeI> wmImNoticeIReturnList = new ArrayList<>();
        for (WmImNoticeI wmImNoticeI : wmImNoticeIList) {
            //外键设置
            wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
            wmImNoticeI.setCusCode(wmImNoticeH.getCusCode());
//                wmImNoticeIMapper.insert(entity);
            //查询商品
            MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).one();
            long hiti = 0;
            if (StringUtil.isEmpty(wmImNoticeI.getBinPlan()) && StringUtils.isNotEmpty(mdGoods.getMpCengGao()) && StringUtils.isNotEmpty(mdGoods.getMpDanCeng()) && StringUtils.isNotEmpty(mdGoods.getChlShl())) {
                hiti = Long.parseLong(wmImNoticeI.getGoodsCount()) / (Long.parseLong(mdGoods.getMpCengGao()) * Long.parseLong(mdGoods.getMpDanCeng()) * Long.parseLong(mdGoods.getChlShl()));
                wmImNoticeI.setBinPlan(Long.toString(hiti));
            }

            if (StringUtils.isNotEmpty(wmImNoticeI.getBinPlan())) {
                //查询库位
//                BaKw baKw = baKwService.lambdaQuery().eq(BaKw::getKwCode, wmImNoticeI.getBinPlan()).one();
//                if (!checkFitKw(baKw, mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmImNoticeI.getGoodsCode()).one())) {
//                    throw new JeecgBootException("该库位不能存放此商品类型！");
//                }
            }

            wmImNoticeI.setGoodsCode(mdGoods.getShpBianMa());
            wmImNoticeI.setGoodsName(mdGoods.getShpMingCheng());
            wmImNoticeI.setGoodsUnit(mdGoods.getSnpTray());
            if (StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(mdGoods.getTiJiCm()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setGoodsFvol(String.valueOf(Double.parseDouble(mdGoods.getTiJiCm()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            }
            wmImNoticeI.setBarcode(mdGoods.getShpTiaoMa());
            wmImNoticeI.setChpShuXing(mdGoods.getChpShuXing());
            if (StringUtils.isNotEmpty(mdGoods.getJshDanWei()) && (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei())) && StringUtils.isNotEmpty(mdGoods.getChlShl()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setBaseGoodscount(String.valueOf(Double.parseDouble(mdGoods.getChlShl()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            } else {
                wmImNoticeI.setBaseGoodscount(wmImNoticeI.getGoodsCount());
            }
            wmImNoticeI.setGoodsUnit(mdGoods.getShlDanWei());
            wmImNoticeI.setBaseUnit(mdGoods.getJshDanWei());
            if (StringUtils.isNotEmpty(mdGoods.getZhlKg()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount()) && StringUtils.isNotEmpty(wmImNoticeI.getGoodsCount())) {
                wmImNoticeI.setGoodsWeight(String.valueOf(Double.parseDouble(mdGoods.getZhlKg()) * Double.parseDouble(wmImNoticeI.getGoodsCount())));
            }
            if ("04".equals(wmImNoticeH.getOrderTypeCode())) {//越库任务
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setBinPre("Y");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                WmInQmI wmInQmI = new WmInQmI();
                wmInQmI.setBinId(wmImNoticeH.getNoticeId());//仓位
                wmInQmI.setImNoticeId(wmImNoticeH.getNoticeId());//通知单号
                wmInQmI.setImNoticeItem(wmImNoticeI.getId());
                wmInQmI.setBinSta("Y");
                wmInQmI.setCusCode(wmImNoticeH.getCusCode());
                ;
                wmInQmI.setTinId(wmImNoticeH.getNoticeId());
                wmInQmI.setTinTj(wmImNoticeI.getGoodsFvol());
                wmInQmI.setTinZhl(wmImNoticeI.getGoodsWeight());
                wmInQmI.setGoodsId(wmImNoticeI.getGoodsCode());
                wmInQmI.setGoodsUnit(wmImNoticeI.getGoodsUnit());
                wmInQmI.setQmOkQuat(wmImNoticeI.getGoodsCount());
                wmInQmI.setImQuat(wmImNoticeI.getGoodsCount());
                wmInQmI.setBaseQmcount(wmImNoticeI.getBaseGoodscount());
                wmInQmI.setBaseUnit(wmImNoticeI.getBaseUnit());
                wmInQmI.setProData(new Date());
                wmInQmI.setGoodsBatch(new SimpleDateFormat("yyyy-MM-dd").format(wmInQmI.getProData()));
                wmInQmI.setBaseGoodscount(wmImNoticeI.getBaseGoodscount());
                wmInQmI.setImCusCode(wmImNoticeH.getImCusCode());
                wmInQmIService.save(wmInQmI);
            } else {
                wmImNoticeI.setBinPre("N");
                wmImNoticeI.setGoodsQmCount("0");
                wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
                wmImNoticeI.setImBeizhu(wmImNoticeH.getImBeizhu());
                wmImNoticeI.setImCusCode(wmImNoticeH.getImCusCode());
            }
            wmImNoticeIReturnList.add(wmImNoticeI);
        }
        return wmImNoticeIReturnList;
    }

    public boolean checkFitKw(BaKw baKw, MdGoods mdGoods) {
        if (baKw != null && StringUtils.isNotEmpty(baKw.getPartType())) {
            if (mdGoods == null) {
                return true;
            }
            List<String> partTypeList = Arrays.asList(baKw.getPartType().split(","));

            return partTypeList.contains(mdGoods.getClassification());

        } else {
            return true;
        }
    }

    private void AddWmPlatIo(WmOmNoticeH wmOmNoticeH, String noticeid) {
        WmPlatIo wmPlatIo = new WmPlatIo();
        wmPlatIo.setCarno(wmOmNoticeH.getReCarno());
        wmPlatIo.setDocId(noticeid);
        wmPlatIo.setPlanIndata(wmOmNoticeH.getDelvData());
        wmPlatIo.setPlatId(wmOmNoticeH.getOmPlatNo());
        wmPlatIo.setPlatSta(ConstUtil.wm_sta1);
        wmPlatIo.setPlatBeizhu("司机:" + wmOmNoticeH.getReMember() + "电话:" + wmOmNoticeH.getReMobile());
        wmPlatIoMapper.insert(wmPlatIo);
    }

    private WmOmNoticeI findWmOmNoticeIE(WmOmNoticeI entity, WmOmNoticeH wmOmNoticeH, String noticeid) {
        Double jishu = 0.00;
        Double tiji = 0.00;
        Double zhongl = 0.00;
        Double chang = 0.00;
        Double kuan = 0.00;
        Double gao = 0.00;
        String huowu = "";
        //外键设置
        entity.setCusCode(wmOmNoticeH.getCusCode());
        entity.setPlanSta(ConstUtil.wm_n);
        entity.setGoodsQuaok("0");
        entity.setOmNoticeId(wmOmNoticeH.getOmNoticeId());
        entity.setImCusCode(wmOmNoticeH.getImCusCode());
        entity.setOmBeiZhu(wmOmNoticeH.getOmBeizhu());
        entity.setOmSta(ConstUtil.wm_sta7);
//        entity.setOmNoticeId(noticeid);
        MdGoods mdGoods = findMvGoods(entity.getGoodsId());
        if (mdGoods != null) {
            entity.setGoodsId(mdGoods.getShpBianMa());
            entity.setGoodsName(mdGoods.getShpMingCheng());
            //huowu = huowu + mvgoods.getGoodsName();
            // entity.setGoodsName(mvgoods.getGoodsName());
            entity.setChpShuXing(mdGoods.getChpShuXing());//产品属性
            entity.setBarcode(mdGoods.getShpTiaoMa());//商品条码
//            entity.setBaseUnit(mdGoods.getSnpTray());
            entity.setGoodsUnit(mdGoods.getSnpTray());
            if (mdGoods.getJshDanWei() != mdGoods.getShlDanWei()) {
                entity.setBaseGoodscount(String.valueOf(Double.parseDouble(mdGoods.getChlShl()) * Double.parseDouble(entity.getGoodsQua())));
            } else {
                entity.setBaseGoodscount(entity.getGoodsQua());
            }
/*            tiji = tiji + Double.parseDouble(entity.getBaseGoodscount()) * Double.parseDouble(mvgoods.getTiJiCm());
            zhongl = zhongl + Double.parseDouble(entity.getBaseGoodscount()) * Double.parseDouble(mvgoods.getZhlKg());
            jishu = jishu + Double.parseDouble(entity.getBaseGoodscount());*/

        }
        return entity;
    }

    private MdGoods findMvGoods(String goodsId) {
        return mdGoodsMapper.selectOne(new QueryWrapper<MdGoods>().lambda().eq(MdGoods::getShpBianMa,goodsId));
    }

    private boolean checkstcoka(WmOmNoticeI wmOmNoticeI) {
        String goods = null;
        if (!StringUtil.isEmpty(wmOmNoticeI.getGoodsId())) {
            if (wmOmNoticeI.getGoodsId().endsWith("l")) {
                goods = wmOmNoticeI.getGoodsId().substring(0, wmOmNoticeI.getGoodsId().length() - 1);
            } else {
                goods = wmOmNoticeI.getGoodsId();
            }
        }
        //查询库存信息
        List<Map<String, Object>> list = findStockList(wmOmNoticeI.getBinOm(), wmOmNoticeI.getBinId(), goods, null, wmOmNoticeI.getCusCode());
        if (list.size() > 0) {
            BigDecimal totalMoney = list.stream().map(map -> new BigDecimal(Double.parseDouble(map.get("goods_qua").toString()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (Double.parseDouble(totalMoney.toString()) >= Double.parseDouble(wmOmNoticeI.getBaseGoodscount())) {
                return true;
            }
        }
        return false;
    }

    private List<Map<String, Object>> findStockList(String binId, String tinId, String goods, String prodate, String cusCode) {
        List<Map<String, Object>> list = wmOmNoticeIMapper.findStock(binId, tinId, goods, prodate, null,null, null);
        return list;
    }
}
