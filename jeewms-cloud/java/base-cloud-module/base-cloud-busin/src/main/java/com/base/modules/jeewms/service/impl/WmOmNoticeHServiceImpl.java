package com.base.modules.jeewms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.base.dto.CancelOrderInfo;
import com.base.common.base.dto.WMSResult;
import com.base.common.system.vo.LoginUser;
import com.base.common.util.DateUtils;
import com.base.common.util.RedisUtil;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.*;
import com.base.modules.jeewms.pdaapi.ApiEntity;
import com.base.modules.jeewms.service.IMdGoodsItemService;
import com.base.modules.jeewms.service.IWmOmNoticeHService;
import com.base.modules.jeewms.service.IWmOmQmIService;
import com.base.modules.jeewms.service.IWvStockSttService;
import com.base.modules.jeewms.vo.EditBatchWmOmNoticeHVo;
import com.base.modules.util.ConstUtil;
import com.base.modules.util.NotNullUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.QRcodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.base.modules.util.ConstUtil.*;

/**
 * @Description: 出货通知抬头
 * @Author: base-boot
 * @Date: 2021-05-18
 * @Version: V1.0
 */
@Service
public class WmOmNoticeHServiceImpl extends ServiceImpl<WmOmNoticeHMapper, WmOmNoticeH> implements IWmOmNoticeHService {
    ExecutorService executor = Executors.newFixedThreadPool(8);
    @Autowired
    private WmOmNoticeHMapper wmOmNoticeHMapper;
    @Autowired
    private WmOmNoticeIMapper wmOmNoticeIMapper;
    @Autowired
    private WmNoticeConfMapper wmNoticeConfMapper;
    @Autowired
    private MdCusMapper mdCusMapper;
    @Autowired
    private MdCusOtherMapper mdCusOtherMapper;
    @Autowired
    private MvGoodsMapper mvGoodsMapper;
    @Autowired
    private WmOmQmIMapper wmOmQmIMapper;


    @Autowired
    private IWmOmQmIService wmOmQmIService;
    @Autowired
    private SmsSendImpl SmsSendImpl;
    @Autowired
    private WmPlatIoMapper wmPlatIoMapper;
    @Autowired
    private WmToDownGoodsMapper wmToDownGoodsMapper;
    @Autowired
    private MdGoodsMapper mdGoodsMapper;

    @Autowired
    private SmsSendImpl smsSend;

    @Autowired
    IWvStockSttService wvStockSttService;
    @Autowired
    private IMdGoodsItemService mdGoodsItemService;

    @Autowired
    private RedisUtil  redisUtil;
    @Override
    @Transactional
    public void saveMain(WmOmNoticeH wmOmNoticeH, List<WmOmNoticeI> wmOmNoticeIList) {
        synchronized (this){
            if (StringUtils.isEmpty(wmOmNoticeH.getOrderTypeCode())) {
                wmOmNoticeH.setOrderTypeCode("11");
            }
            if (StringUtils.isEmpty(wmOmNoticeH.getOmSta())) {
                wmOmNoticeH.setOmSta(ConstUtil.wm_sta1);
            }
//        if (StringUtils.isEmpty(wmOmNoticeH.getOmNoticeId())) {
//            throw new JeecgBootException("出库单号为空");
//        }
            //添加月台
            AddWmPlatIo(wmOmNoticeH, wmOmNoticeH.getOmNoticeId());
            //查询cus
            MdCus mdCus = mdCusMapper.selectOne(new QueryWrapper<MdCus>().lambda().eq(MdCus::getKeHuBianMa, wmOmNoticeH.getCusCode()));
            if (mdCus != null) {
                wmOmNoticeH.setCusName(mdCus.getZhongWenQch());
                wmOmNoticeH.setDelvMobile(mdCus.getShouJi());
            }
            //查询第三方客户
            QueryWrapper<MdCusOther> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ke_hu_bian_ma", wmOmNoticeH.getOcusCode());
            MdCusOther mdCusOther = mdCusOtherMapper.selectOne(queryWrapper);
            if (ObjectUtil.isNotEmpty(mdCusOther)) {
                wmOmNoticeH.setOcusCode(wmOmNoticeH.getOcusCode());
                wmOmNoticeH.setOcusName(mdCusOther.getZhongWenQch());
            }
            String noticeid = getNoticeId(wmOmNoticeH);
            wmOmNoticeH.setOmNoticeId(noticeid);
            wmOmNoticeHMapper.insert(wmOmNoticeH);
            Boolean flag = false;
            if (wmOmNoticeIList != null && wmOmNoticeIList.size() > 0) {
                for (WmOmNoticeI entity : wmOmNoticeIList) {
                    entity.setCheckname(StringUtil.isNotEmpty(entity.getCheckname())?entity.getCheckname():"0");
                    entity.setBaseGoodscount(entity.getGoodsQua());
                    entity.setFlag(wmOmNoticeH.getFlag());
                    entity.setTenantId(wmOmNoticeH.getTenantId());
                    //验证库存
                    // TODO: 2021/12/22  做配置项 是否检查库存
                    if (!checkstcoka(entity)) {
                        flag = true;
                    }
                    entity = findWmOmNoticeIE(entity, wmOmNoticeH, wmOmNoticeH.getOmNoticeId());
                    wmOmNoticeIMapper.insert(entity);
                }
            }
            if(flag){
                wmOmNoticeH.setQhSta("缺货");
                wmOmNoticeHMapper.updateById(wmOmNoticeH);
            }
            try{
                SmsSendImpl.rundowntask(wmOmNoticeH.getOmNoticeId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    @Transactional
    public String addSw(WmOmNoticeH omNoticeH, List<WmOmNoticeI> wmOmNoticeIList) {
        synchronized (this){
            omNoticeH.setFlag("Y");
            String noticeid = "";
            omNoticeH.setWmOmNoticeIList(wmOmNoticeIList);
            noticeid = getNoticeId(omNoticeH);
            if (StringUtils.isEmpty(omNoticeH.getOrderTypeCode())) {
                omNoticeH.setOrderTypeCode("11");
            }
            if (StringUtils.isEmpty(omNoticeH.getOmSta())) {
                omNoticeH.setOmSta(ConstUtil.wm_sta1);
            }

            //添加月台
            AddWmPlatIo(omNoticeH, omNoticeH.getOmNoticeId());
            omNoticeH.setOmNoticeId(noticeid);
            //查询cus

            //查询第三方客户
            QueryWrapper<MdCusOther> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ke_hu_bian_ma", omNoticeH.getOcusCode());
            MdCusOther mdCusOther = mdCusOtherMapper.selectOne(queryWrapper);
            if (ObjectUtil.isNotEmpty(mdCusOther)) {
                omNoticeH.setOcusCode(omNoticeH.getOcusCode());
                omNoticeH.setOcusName(mdCusOther.getZhongWenQch());
            }
            wmOmNoticeHMapper.insert(omNoticeH);
            if (omNoticeH.getWmOmNoticeIList() != null && omNoticeH.getWmOmNoticeIList().size() > 0) {
                for (WmOmNoticeI entity : omNoticeH.getWmOmNoticeIList()) {

                    MdCus mdCus = mdCusMapper.selectOne(new QueryWrapper<MdCus>().lambda().eq(MdCus::getKeHuBianMa, entity.getCusCode()));
                    if (mdCus != null) {
                        entity.setCusName(mdCus.getZhongWenQch());
                    }

                    entity.setBaseGoodscount(entity.getGoodsQua());
                    entity.setFlag(omNoticeH.getFlag());
                    entity.setTenantId(omNoticeH.getTenantId());
                    entity = findWmOmNoticeIE1(entity, omNoticeH, omNoticeH.getOmNoticeId());
                    wmOmNoticeIMapper.insert(entity);
                }
            }
            try{
                SmsSendImpl.rundowntask(omNoticeH.getOmNoticeId());
            }catch (Exception e){
             e.printStackTrace();
            }
            return noticeid;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveMain1(WmOmNoticeH wmOmNoticeH, List<WmOmNoticeI> wmOmNoticeIList) {
        String msg = "库存满足，建单成功";
        String noticeid = getNoticeId(wmOmNoticeH);
        if (StringUtils.isEmpty(wmOmNoticeH.getOrderTypeCode())) {
            wmOmNoticeH.setOrderTypeCode("11");
        }
        if (StringUtils.isEmpty(wmOmNoticeH.getOmSta())) {
            wmOmNoticeH.setOmSta(ConstUtil.wm_sta1);
        }
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
        Boolean flag = false;
        if (wmOmNoticeIList != null && wmOmNoticeIList.size() > 0) {
            for (WmOmNoticeI entity : wmOmNoticeIList) {
                entity.setBaseGoodscount(entity.getGoodsQua());
                entity.setFlag(wmOmNoticeH.getFlag());
                //验证库存
                if (!checkstcoka(entity)) {
                    flag = true;
                }
                entity = findWmOmNoticeIE(entity, wmOmNoticeH, wmOmNoticeH.getOmNoticeId());
                wmOmNoticeIMapper.insert(entity);
            }
        }
        if(flag){
            wmOmNoticeH.setQhSta("缺货");
            wmOmNoticeHMapper.updateById(wmOmNoticeH);
            msg =  "库存不足,已生成缺货列表";
        }
        try{
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    SmsSendImpl.rundowntask(wmOmNoticeH.getOmNoticeId());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
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
        if (StringUtils.isNotEmpty(wmOmNoticeH.getCusCode())){
           entity.setCusCode(wmOmNoticeH.getCusCode());
        }
        entity.setPlanSta(ConstUtil.wm_n);
        entity.setGoodsQuaok("0");
        entity.setOmNoticeId(wmOmNoticeH.getOmNoticeId());
        entity.setImCusCode(wmOmNoticeH.getImCusCode());
//        entity.setOmBeiZhu(wmOmNoticeH.getOmBeizhu());
        entity.setOmSta(ConstUtil.wm_sta7);
//        entity.setOmNoticeId(noticeid);
        MdGoods mdGoods = findMvGoods(entity.getGoodsId());
        if (mdGoods != null) {
            entity.setGoodsId(mdGoods.getShpBianMa());
            entity.setGoodsName(mdGoods.getShpMingCheng());
            //huowu = huowu + mvgoods.getGoodsName();
             entity.setChpShuXing(mdGoods.getChpShuXing());//产品属性
            String goodsname = mdGoods.getShpMingCheng();

            //多单位  cez
            List<MdGoodsItem> listmdgoodsitem = mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getShpBianMa, mdGoods.getShpBianMa()).list();
            String itembaseunit = "";
            String basegoodscount = "";
            String itembarcode = "";
            Boolean itemflag = false;
            for (MdGoodsItem mdGoodsItem : listmdgoodsitem) {
                if(mdGoodsItem.getUnit2().equals(entity.getGoodsUnit())){
                    itembaseunit = mdGoodsItem.getUnit1();
                    itembarcode = mdGoodsItem.getSttr1();
                    Double basegoods = Double.parseDouble(mdGoodsItem.getNum2())*Double.parseDouble(entity.getGoodsQua());
                    basegoodscount = basegoods.toString();
                    goodsname = mdGoodsItem.getShpMingCheng();

                    itemflag = true;
                }

            }
            //多单位   cez

            //多单位   cez
            if(itemflag){
                entity.setBarcode(itembarcode);
                entity.setBaseUnit(itembaseunit);
                entity.setBaseGoodscount(basegoodscount);
            }else{
                entity.setBarcode(mdGoods.getShpTiaoMa());
                entity.setBaseUnit(mdGoods.getJshDanWei());
                entity.setBaseGoodscount(entity.getGoodsQua());
            }
            entity.setGoodsName(goodsname);
            //多单位   cez


//            entity.setBarcode(mdGoods.getShpTiaoMa());//商品条码
//            entity.setBaseUnit(mdGoods.getSnpTray());
//            entity.setGoodsUnit(mdGoods.getSnpTray());
//            if (StringUtil.isNotEmpty(mdGoods.getJshDanWei()) && StringUtil.isNotEmpty(mdGoods.getShlDanWei()) ){
//                if (!mdGoods.getJshDanWei().equals(mdGoods.getShlDanWei())) {
//                    entity.setBaseGoodscount(String.valueOf(Double.parseDouble(mdGoods.getChlShl()) * Double.parseDouble(entity.getGoodsQua())));
//                }else {
//                    entity.setBaseGoodscount(entity.getGoodsQua());
//                }
//            }else {
//                entity.setBaseGoodscount(entity.getGoodsQua());
//            }
/*            tiji = tiji + Double.parseDouble(entity.getBaseGoodscount()) * Double.parseDouble(mvgoods.getTiJiCm());
            zhongl = zhongl + Double.parseDouble(entity.getBaseGoodscount()) * Double.parseDouble(mvgoods.getZhlKg());
            jishu = jishu + Double.parseDouble(entity.getBaseGoodscount());*/

        }
        return entity;
    }

    private WmOmNoticeI findWmOmNoticeIE1(WmOmNoticeI entity, WmOmNoticeH wmOmNoticeH, String noticeid) {
        Double jishu = 0.00;
        Double tiji = 0.00;
        Double zhongl = 0.00;
        Double chang = 0.00;
        Double kuan = 0.00;
        Double gao = 0.00;
        String huowu = "";
        //外键设置
//        entity.setCusCode(wmOmNoticeH.getCusCode());
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

            entity.setChpShuXing(mdGoods.getChpShuXing());//产品属性
            String goodsname = mdGoods.getShpMingCheng();

            //多单位  cez
            List<MdGoodsItem> listmdgoodsitem = mdGoodsItemService.lambdaQuery().eq(MdGoodsItem::getShpBianMa, mdGoods.getShpBianMa()).list();
            String itembaseunit = "";
            String basegoodscount = "";
            String itembarcode = "";

            Boolean itemflag = false;
            for (MdGoodsItem mdGoodsItem : listmdgoodsitem) {
                if(mdGoodsItem.getUnit2().equals(entity.getGoodsUnit())){
                    itembaseunit = mdGoodsItem.getUnit1();
                    itembarcode = mdGoodsItem.getSttr1();
                    Double basegoods = Double.parseDouble(mdGoodsItem.getNum2())*Double.parseDouble(entity.getGoodsQua());
                    basegoodscount = basegoods.toString();
                    goodsname = mdGoodsItem.getShpMingCheng();
                    itemflag = true;
                }

            }
            //多单位   cez

            //多单位   cez
            if(itemflag){
                entity.setBarcode(itembarcode);
                entity.setBaseUnit(itembaseunit);
                entity.setBaseGoodscount(basegoodscount);
            }else{
                entity.setBarcode(mdGoods.getShpTiaoMa());
                entity.setBaseUnit(mdGoods.getJshDanWei());
                entity.setBaseGoodscount(entity.getGoodsQua());
            }
            /*entity.setBarcode(mdGoods.getShpTiaoMa());
            entity.setBaseUnit(mdGoods.getShlDanWei());
            entity.setBaseGoodscount(entity.getGoodsQua());*/
            entity.setGoodsName(goodsname);
            //多单位   cez
        }
        return entity;
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

    private String getNoticeId(WmOmNoticeH wmOmNoticeH) {
        String noticeid = null;
        String today = DateUtils.date2Str(new SimpleDateFormat("yyyy-MM-dd"));

        List<WmOmNoticeH> list = this.lambdaQuery().eq(WmOmNoticeH::getOrderTypeCode, wmOmNoticeH.getOrderTypeCode()).apply("date_format(create_time,'%Y-%m-%d') = '" + today + "'").list();
        Integer max = 0;
        if (CollectionUtil.isNotEmpty(list)){
            List<Integer> list1 = new ArrayList<>();
            for (WmOmNoticeH noticeH : list) {
                String noticeId = noticeH.getOmNoticeId();
                String s = StringUtils.split(noticeId, "-")[3];
                Integer integer = Convert.toInt(s);
                list1.add(integer);
            }
            max = Collections.max(list1);
        }

        int newcount = max + 1;
//        int newcount = count + 1;

        if ("19".equals(wmOmNoticeH.getOrderTypeCode())) {
            noticeid = "QTCK"
                    + today
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("11".equals(wmOmNoticeH.getOrderTypeCode())) {
            noticeid = "QT"
                    + today
                    + "-"
                    + String.format("%04d", newcount);
        }else if ("13".equals(wmOmNoticeH.getOrderTypeCode())) {
            noticeid = "SC"
                    + today
                    + "-"
                    + String.format("%04d", newcount);
        } else {
            noticeid = "CK"
                    + today
                    + "-"
                    + String.format("%04d", newcount);
        }
        return noticeid;
    }

    /*@Override
    public void updateMain(WmOmNoticeH wmOmNoticeH, List<WmOmNoticeI> wmOmNoticeIList) {
        wmOmNoticeHMapper.updateById(wmOmNoticeH);
        //1.先删除子表数据
        wmOmNoticeIMapper.deleteByMainId(wmOmNoticeH.getOmNoticeId());
        //2.子表数据重新插入
        Boolean flag = false;
        if (wmOmNoticeIList != null && wmOmNoticeIList.size() > 0) {
            for (WmOmNoticeI entity : wmOmNoticeIList) {
                if (checkstcoka(entity)) {
                    flag = true;
                }
                //外键设置
                entity = findWmOmNoticeIE(entity, wmOmNoticeH, wmOmNoticeH.getId());
                wmOmNoticeIMapper.insert(entity);
            }
        }
        if(flag){
            wmOmNoticeH.setQhSta("");
            wmOmNoticeHMapper.updateById(wmOmNoticeH);
        }
        try{
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    SmsSendImpl.rundowntask(wmOmNoticeH.getOmNoticeId());
                }
            });
        }catch (Exception e){
        }
    }*/


    @Override
    public void updateMain(WmOmNoticeH wmOmNoticeH, List<WmOmNoticeI> wmOmNoticeIList) {
        wmOmNoticeHMapper.updateById(wmOmNoticeH);

        //2.子表数据重新插入
        Boolean flag = false;
        if (wmOmNoticeIList != null && wmOmNoticeIList.size() > 0) {
            for (WmOmNoticeI entity : wmOmNoticeIList) {
                if("Y".equals(entity.getPlanSta())){
                    //外键设置
                    //entity = findWmOmNoticeIE(entity, wmOmNoticeH, wmOmNoticeH.getId());
                    wmOmNoticeIMapper.updateById(entity);
                }else {
                    //1.先删除子表数据
                    wmOmNoticeIMapper.deleteById(entity.getId());
                    if (checkstcoka(entity)) {
                        flag = true;
                    }
                    //外键设置
                    entity = findWmOmNoticeIE(entity, wmOmNoticeH, wmOmNoticeH.getId());
                    wmOmNoticeIMapper.insert(entity);

                    if(flag){
                        wmOmNoticeH.setQhSta("");
                        wmOmNoticeHMapper.updateById(wmOmNoticeH);
                    }else {
//                        throw new JeecgBootException("库存不足无法生成下架任务");
                            wmOmNoticeH.setQhSta("缺货");
                            wmOmNoticeHMapper.updateById(wmOmNoticeH);
                    }

                }
            }
            try{
                SmsSendImpl.rundowntask(wmOmNoticeH.getOmNoticeId());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
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
        List<Map<String, Object>> list = wmOmNoticeIMapper.findStock(binId, tinId, goods, prodate, null, null, null);
        return list;
    }

    @Override
    @Transactional
    public void delMain(String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        wmOmNoticeIMapper.deleteByMainId(wmOmNoticeH.getOmNoticeId());
        wmOmNoticeHMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delMains(String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        //查询是否已经收货
        if (wmOmQmIService.lambdaQuery().eq(WmOmQmI::getOmNoticeId, wmOmNoticeH.getOmNoticeId()).count() > 0) {
            throw new JeecgBootException("部分商品已任务确认，不允许删除");
        }
        wmOmNoticeIMapper.deleteByMainId(wmOmNoticeH.getOmNoticeId());
        wmOmNoticeHMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            //查询删除信息主表
            WmOmNoticeH en = wmOmNoticeHMapper.selectById(id);
            if (ObjectUtil.isNotEmpty(en)) {
                wmOmNoticeIMapper.deleteByMainId(en.getOmNoticeId());
                wmOmNoticeHMapper.deleteById(id);
                QueryWrapper<WmOmQmI> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(WmOmQmI::getOmNoticeId, en.getOmNoticeId());
                List<WmOmQmI> wmOmQmIList = wmOmQmIMapper.selectList(queryWrapper);
                if (!wmOmQmIList.isEmpty()) {
                    wmOmQmIMapper.delete(queryWrapper);
                    QueryWrapper<WmToDownGoods> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.lambda().eq(WmToDownGoods::getOrderId, en.getOmNoticeId());
                    List<WmToDownGoods> wmToDownGoods = wmToDownGoodsMapper.selectList(queryWrapper1);
                    if (wmToDownGoods.isEmpty()) {
                        wmToDownGoodsMapper.delete(queryWrapper1);
                    }
                }

            }

        }
    }

    /**
     * @param list
     * @return
     * @Describe 批量回单-批量回单修改
     * @Author zly
     * @Create Date 2021/5/21
     */
    @Override
    public Result<?> editBatchReceipt(List<EditBatchWmOmNoticeHVo> list) {
        int eer = list.size();
        for (EditBatchWmOmNoticeHVo vo : list) {
            //获取单据信息
            WmOmNoticeH en = wmOmNoticeHMapper.selectById(vo.getId());
            //添加回单信息
            WmNoticeConf wmNoticeConf = new WmNoticeConf();
            wmNoticeConf.setBeizhu(en.getOmBeizhu());
            wmNoticeConf.setHdData(en.getDelvData());
            wmNoticeConf.setCusCode(en.getCusCode());
            wmNoticeConf.setWmNoticeId(en.getOmNoticeId());
            wmNoticeConfMapper.insert(wmNoticeConf);
            //修改单据状态
            en.setOmSta("完成");
            int row = wmOmNoticeHMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("回单成功");
        }
        return Result.ok("回单失败" + eer + "条");
    }

    @Override
    public void downReceiveExcel(String id, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);//获取抬头
        QueryWrapper<WmOmNoticeI> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("om_notice_id", wmOmNoticeH.getOmNoticeId());
        List<WmOmNoticeI> wmOmNoticeIEntityList = wmOmNoticeIMapper.selectList(queryWrapper);//获取行项目
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = QRcodeUtil.createImage(wmOmNoticeH.getOmNoticeId());
            // 进行转码，使其支持中文文件名
            response.setHeader("content-disposition", "attachment;filename=" + wmOmNoticeH.getOmNoticeId() + ".xls");
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("出货通知");
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 10 * 200);
            sheet.setColumnWidth(3, 8 * 256);
            sheet.setColumnWidth(4, 8 * 256);
            sheet.setColumnWidth(5, 8 * 256);
            sheet.setColumnWidth(6, 8 * 256);
            sheet.setColumnWidth(7, 8 * 256);
            sheet.setColumnWidth(8, 25 * 256);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 7, 1, (short) 9, 3);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

            // 创建第一行
            Row row = sheet.createRow((short) 0);   //第一行空白


            // 创建两种单元格格式
            CellStyle cs = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            CellStyle cs3 = wb.createCellStyle();
            CellStyle cs4 = wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            Font f2 = wb.createFont();

            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 16);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBold(true);

            // 创建第二种字体样式（用于值）
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.BLACK.getIndex());

            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(BorderStyle.NONE);
            cs.setBorderRight(BorderStyle.NONE);
            cs.setBorderTop(BorderStyle.NONE);
            cs.setBorderBottom(BorderStyle.NONE);
            cs.setAlignment(HorizontalAlignment.CENTER);
            // 设置第二种单元格的样式（用于值）
            cs2.setFont(f2);
            cs2.setBorderLeft(BorderStyle.NONE);
            cs2.setBorderRight(BorderStyle.NONE);
            cs2.setBorderTop(BorderStyle.NONE);
            cs2.setBorderBottom(BorderStyle.NONE);
            cs2.setWrapText(true);

//	        cs2.setAlignment(BorderStyle.NONE);


            cs3.setFont(f2);
            cs3.setBorderLeft(BorderStyle.MEDIUM);
            cs3.setBorderRight(BorderStyle.MEDIUM);
            cs3.setBorderTop(BorderStyle.MEDIUM);
            cs3.setBorderBottom(BorderStyle.MEDIUM);
//	        cs3.setAlignment(CellStyle.BORDER_HAIR);
            cs4.setFont(f2);
            cs4.setBorderTop(BorderStyle.MEDIUM);
            cs4.setBorderBottom(BorderStyle.MEDIUM);
            Row row1 = sheet.createRow((short) 1);   //第二行标题
            row1.setHeight((short) 700);
            Cell cellTitle = row1.createCell(0);
            cellTitle.setCellValue("仓储管理出货通知");
            cellTitle.setCellStyle(cs);


            Row rowHead1 = sheet.createRow((short) 2);  //头部第一行
            Cell cellHead11 = rowHead1.createCell(0);
            cellHead11.setCellValue("通知单号：" + wmOmNoticeH.getOmNoticeId());
            cellHead11.setCellStyle(cs2);


            Row rowHead2 = sheet.createRow((short) 3);  //头部第二行
            Cell cellHead21 = rowHead2.createCell(0);
            try {
                QueryWrapper<MdCus> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
                MdCus md = mdCusMapper.selectOne(queryWrapper1);
                if (md != null) {
                    cellHead21.setCellValue("客户：" + wmOmNoticeH.getCusCode() + "/" + md.getZhongWenQch());
                } else {
                    cellHead21.setCellValue("客户：" + wmOmNoticeH.getCusCode());
                }
            } finally {

            }

            cellHead21.setCellStyle(cs2);

            Cell cellHead23 = rowHead2.createCell(5);
            cellHead23.setCellValue(" 计划出货时间：" + wmOmNoticeH.getDelvData());
            cellHead23.setCellStyle(cs2);


            Row rowHead3 = sheet.createRow((short) 4);  //头部第三行
            Cell cellHead31 = rowHead3.createCell(0);
            cellHead31.setCellValue("司机：" + wmOmNoticeH.getReMember() + "  司机电话：" + wmOmNoticeH.getReMobile());
            cellHead31.setCellStyle(cs2);


            Cell cellHead35 = rowHead3.createCell(5);
            cellHead35.setCellValue("车号：" + wmOmNoticeH.getReCarno() + "  备注：" + wmOmNoticeH.getOmBeizhu());
            cellHead35.setCellStyle(cs2);

            Row rowHead4 = sheet.createRow((short) 5);  //头部第三行
            Cell cellHead41 = rowHead4.createCell(0);
            cellHead41.setCellValue("收货人：" + wmOmNoticeH.getDelvMember() + "  电话：" + wmOmNoticeH.getDelvMobile());
            cellHead31.setCellStyle(cs2);


            Cell cellHead45 = rowHead4.createCell(5);
            cellHead45.setCellValue("收货地址：" + wmOmNoticeH.getDelvAddr());
            cellHead45.setCellStyle(cs2);

            //合并单元格
            CellRangeAddress c = new CellRangeAddress(0, 0, 0, 8); //第一行空白
            CellRangeAddress c0 = new CellRangeAddress(1, 1, 0, 8);//第二行标题
            CellRangeAddress c1 = new CellRangeAddress(2, 2, 0, 8);//第三行通知单号
            CellRangeAddress c2 = new CellRangeAddress(3, 3, 0, 4);//第四行客户
            CellRangeAddress c3 = new CellRangeAddress(3, 3, 5, 8);//第四行客户送货时间
            CellRangeAddress c4 = new CellRangeAddress(4, 4, 0, 4);//第五行客户
            CellRangeAddress c5 = new CellRangeAddress(4, 4, 5, 8);//第五行客户送货时间
            CellRangeAddress c6 = new CellRangeAddress(5, 5, 0, 4);//第五行客户
            CellRangeAddress c7 = new CellRangeAddress(5, 5, 5, 8);//第五行客户送货时间


            sheet.addMergedRegion(c);
            sheet.addMergedRegion(c0);
            sheet.addMergedRegion(c1);
            sheet.addMergedRegion(c2);
            sheet.addMergedRegion(c3);
            sheet.addMergedRegion(c4);
            sheet.addMergedRegion(c5);
            sheet.addMergedRegion(c6);
            sheet.addMergedRegion(c7);


            Row rowColumnName = sheet.createRow((short) 6);  //列名
            String[] columnNames = {"序号", "商品编码", "商品描述", "数量", "单位", "生产日期", "实收数量", " ", "条码"};

            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = rowColumnName.createCell(i);
                cell.setCellValue(columnNames[i]);
                cell.setCellStyle(cs3);
            }
            int cellsNum = 6;
            int cerconNo = 1;
            for (int i = 0; i < wmOmNoticeIEntityList.size(); i++) {
                WmOmNoticeI entity = wmOmNoticeIEntityList.get(i);
                cellsNum++;
                Row rowColumnValue = sheet.createRow((short) cellsNum);  //列名
                rowColumnValue.setHeight((short) 1000);
                Cell cell1 = rowColumnValue.createCell(0);
                cell1.setCellValue(cerconNo);
                cell1.setCellStyle(cs3);
                Cell cell2 = rowColumnValue.createCell(1);
                cell2.setCellValue(entity.getGoodsId());
                cell2.setCellStyle(cs3);
                Cell cell3 = rowColumnValue.createCell(2);
                cell3.setCellStyle(cs3);
                Cell cell8 = rowColumnValue.createCell(7);
                cell8.setCellValue(entity.getBinOm());
                cell8.setCellStyle(cs4);
                Cell cell5 = rowColumnValue.createCell(4);

                cell5.setCellStyle(cs3);
                try {
                    QueryWrapper<MvGoods> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("goods_code", entity.getGoodsId());
                    MvGoods goods = mvGoodsMapper.selectOne(queryWrapper2);
                    if (goods != null) {
                        cell3.setCellValue(goods.getGoodsName());
                        cell5.setCellValue(goods.getShlDanWei());
                    }
                } finally {

                }
                Cell cell4 = rowColumnValue.createCell(3);
                cell4.setCellValue(entity.getGoodsQua());
                cell4.setCellStyle(cs3);

                Cell cell6 = rowColumnValue.createCell(5);
                cell6.setCellValue(DateUtils.date2Str(entity.getGoodsProData(), DateUtils.date_sdf.get()));
                cell6.setCellStyle(cs3);
                Cell cell7 = rowColumnValue.createCell(6);

                cell7.setCellStyle(cs3);
                Cell cell9 = rowColumnValue.createCell(8);
                cell9.setCellStyle(cs4);
                //插入图片

                byteArrayOut = new ByteArrayOutputStream();
                bufferImg = QRcodeUtil.createImage(entity.getGoodsId());
                ImageIO.write(bufferImg, "jpg", byteArrayOut);

                anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 8, cellsNum, (short) 9, cellsNum + 1);

                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                cerconNo++;
            }
            Row rowColumnInfo = sheet.createRow((short) 2 + cellsNum);  //列名
            rowColumnInfo.createCell(0).setCellValue("注:烦请按时到谢谢！");
            CellRangeAddress c15 = new CellRangeAddress(10 + cellsNum, 10 + cellsNum, 0, 15);
            sheet.addMergedRegion(c15);
            fileOut = response.getOutputStream();
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doPrintckd(String id, HttpServletResponse response) {
        OutputStream fileOut = null;
        BufferedImage bufferImg = null;
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);//获取抬头
        QueryWrapper<WmOmNoticeI> queryWrapper = new QueryWrapper<>();
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = QRcodeUtil.createImage(wmOmNoticeH
                    .getOmNoticeId());

            // 进行转码，使其支持中文文件名
//			codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + wmOmNoticeH.getOmNoticeId() + ".xls");
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("出库单");
            sheet.setMargin(HSSFSheet.TopMargin, 0.1);// 页边距（上）
            sheet.setMargin(HSSFSheet.BottomMargin, 1.5);// 页边距（下）
            sheet.setMargin(HSSFSheet.LeftMargin, 0.3);// 页边距（左）
            sheet.setMargin(HSSFSheet.RightMargin, 0.0);// 页边距（右
//			sheet.setDisplayGridlines(true);
            //set print grid lines or not
//			sheet.setPrintGridlines(true);
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 15 * 256);
            sheet.setColumnWidth(2, 25 * 256);
            sheet.setColumnWidth(3, 11 * 256);
            sheet.setColumnWidth(4, 5 * 256);
            sheet.setColumnWidth(5, 5 * 256);
            sheet.setColumnWidth(6, 7 * 256);
            sheet.setColumnWidth(7, 7 * 256);
            sheet.setColumnWidth(8, 9 * 256);
            sheet.setColumnWidth(9, 7 * 256);
            sheet.setColumnWidth(10, 3 * 256);
            // sheet.setColumnWidth(6, 8 * 256);
            // sheet.setColumnWidth(7, 8 * 256);
            // sheet.setColumnWidth(8, 8 * 256);

            // 创建两种单元格格式
            CellStyle cs = wb.createCellStyle();
            CellStyle cs1 = wb.createCellStyle();
            CellStyle cs2 = wb.createCellStyle();
            CellStyle cs3 = wb.createCellStyle();
            CellStyle cs4 = wb.createCellStyle();
            CellStyle cs5 = wb.createCellStyle();
            CellStyle cs5r = wb.createCellStyle();

            CellStyle cs51 = wb.createCellStyle();
            CellStyle cs52 = wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            Font f2 = wb.createFont();
            Font f5 = wb.createFont();
            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 16);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBold(true);

            // 创建第二种字体样式（用于值）
            f2.setFontHeightInPoints((short) 10);
            f2.setColor(IndexedColors.BLACK.getIndex());


            f5.setFontHeightInPoints((short) 8);
            f5.setColor(IndexedColors.BLACK.getIndex());

            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(BorderStyle.NONE);
            cs.setBorderRight(BorderStyle.NONE);
            cs.setBorderTop(BorderStyle.NONE);
            cs.setBorderBottom(BorderStyle.NONE);
            cs.setAlignment(HorizontalAlignment.CENTER);

            cs1.setFont(f2);
            cs1.setBorderLeft(BorderStyle.NONE);
            cs1.setBorderRight(BorderStyle.NONE);
            cs1.setBorderTop(BorderStyle.NONE);
            cs1.setBorderBottom(BorderStyle.NONE);
            cs1.setAlignment(HorizontalAlignment.CENTER);

            cs1.setWrapText(true);
            // 设置第二种单元格的样式（用于值）
            cs2.setFont(f2);
            cs2.setBorderLeft(BorderStyle.NONE);
            cs2.setBorderRight(BorderStyle.NONE);
            cs2.setBorderTop(BorderStyle.NONE);
            cs2.setBorderBottom(BorderStyle.NONE);
            cs2.setWrapText(true);

            // cs2.setAlignment(BorderStyle.NONE);

            cs3.setFont(f2);
            cs3.setBorderLeft(BorderStyle.MEDIUM);
            cs3.setBorderRight(BorderStyle.MEDIUM);
            cs3.setBorderTop(BorderStyle.MEDIUM);
            cs3.setBorderBottom(BorderStyle.MEDIUM);
            // cs3.setAlignment(CellStyle.BORDER_HAIR);
            cs4.setFont(f2);
            cs4.setBorderTop(BorderStyle.MEDIUM);
            cs4.setBorderBottom(BorderStyle.MEDIUM);

            cs5.setFont(f2);
            cs5.setBorderLeft(BorderStyle.THIN);
            cs5.setBorderRight(BorderStyle.THIN);
            cs5.setBorderTop(BorderStyle.THIN);
            cs5.setBorderBottom(BorderStyle.THIN);
            cs5.setWrapText(true);


            cs5r.setFont(f2);
            cs5r.setBorderLeft(BorderStyle.THIN);
            cs5r.setBorderRight(BorderStyle.THIN);
            cs5r.setBorderTop(BorderStyle.THIN);
            cs5r.setBorderBottom(BorderStyle.THIN);
            cs5r.setWrapText(true);
            cs5r.setAlignment(HorizontalAlignment.RIGHT);
            cs51.setFont(f2);
            cs51.setBorderLeft(BorderStyle.THIN);
            cs51.setBorderRight(BorderStyle.THIN);
            cs51.setBorderTop(BorderStyle.THIN);
            cs51.setBorderBottom(BorderStyle.THIN);
            cs51.setAlignment(HorizontalAlignment.CENTER);

            cs51.setWrapText(true);

            cs52.setFont(f5);
            cs52.setBorderLeft(BorderStyle.NONE);
            cs52.setBorderRight(BorderStyle.NONE);
            cs52.setBorderTop(BorderStyle.NONE);
            cs52.setBorderBottom(BorderStyle.NONE);
            cs52.setAlignment(HorizontalAlignment.CENTER);

            cs52.setWrapText(true);
            cs52.setRotation((short) 255);

            int page = 0;
            int cerconNo = 1;
            List<Map<String, Object>> result = wmOmNoticeHMapper.findById(wmOmNoticeH.getOmNoticeId());


            int size = result.size();
            if (size < 1) {
                result = wmOmNoticeHMapper.findByOmNoticeId(wmOmNoticeH.getOmNoticeId());
                size = result.size();
            }
            int pagesize = 10;
            int pagecount = size % pagesize == 0 ? size / pagesize : size / pagesize + 1;
            double sum = 0;
            double sumxs = 0;
            double sumzl = 0;
            do {

                // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 8, page * 20 + 1, (short) 10, page * 20 + 5);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                // 插入图片
                patriarch
                        .createPicture(anchor, wb.addPicture(
                                byteArrayOut.toByteArray(),
                                HSSFWorkbook.PICTURE_TYPE_JPEG));

                // 创建第一行
                Row row = sheet.createRow((short) page * 20 + 0); // 第一行空白


                Row row1 = sheet.createRow((short) page * 20 + 1); // 第二行标题
                row1.setHeight((short) 700);
                Cell cellTitle = row1.createCell(0);
                cellTitle.setCellValue("出库单");
                cellTitle.setCellStyle(cs);

                Row rowHead1 = sheet.createRow((short) page * 20 + 2); // 头部第一行
                Cell cellHead1 = rowHead1.createCell(0);
                cellHead1.setCellValue("公司地址：");
                cellHead1.setCellStyle(cs1);

                Row rowHead2 = sheet.createRow((short) page * 20 + 3); // 头部第二行
                Cell cellHead2 = rowHead2.createCell(0);
                cellHead2.setCellValue("电话：");
                cellHead2.setCellStyle(cs1);


                Row rowHead4 = sheet.createRow((short) page * 20 + 4); // 头部第二行
                Cell cellHead4 = rowHead4.createCell(0);
                cellHead4.setCellValue("出库日期： " + DateUtils.date2Str(wmOmNoticeH.getDelvData(), DateUtils.date_sdf.get()));
                cellHead4.setCellStyle(cs2);

                Cell cellHead42 = rowHead4.createCell(3);
                cellHead42.setCellValue("出库单号： " + wmOmNoticeH.getOmNoticeId());
                cellHead42.setCellStyle(cs2);

                Row rowHead5 = sheet.createRow((short) page * 20 + 5); // 头部第二行
                Cell cellHead5 = rowHead5.createCell(0);
                cellHead5.setCellValue("客户单号： ");
                cellHead5.setCellStyle(cs2);

                Cell cellHead52 = rowHead5.createCell(3);
                cellHead52.setCellValue("车号： " + wmOmNoticeH.getReCarno());
                cellHead52.setCellStyle(cs2);

                Row rowHead6 = sheet.createRow((short) page * 20 + 6); // 头部第二行
                Cell cellHead6 = rowHead6.createCell(0);
                QueryWrapper<MdCus> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
                MdCus md = mdCusMapper.selectOne(queryWrapper1);

                /*cellHead6.setCellValue("客户名称： " + wmOmNoticeH.getCusCode() + md.getZhongWenQch());
                cellHead6.setCellStyle(cs2);*/

                Cell cellHead62 = rowHead6.createCell(3);
                cellHead62.setCellValue("收货人： " + wmOmNoticeH.getDelvMember() + "   电话:" + wmOmNoticeH.getDelvMobile());
                cellHead62.setCellStyle(cs2);

                Row rowHead7 = sheet.createRow((short) page * 20 + 7); // 头部第二行
                Cell cellHead7 = rowHead7.createCell(0);
                cellHead7.setCellValue("收货地址： " + wmOmNoticeH.getDelvAddr());
                cellHead7.setCellStyle(cs2);

                Cell cellHead72 = rowHead7.createCell(5);
                cellHead72.setCellValue("打印时间： " + DateUtils.date2Str(DateUtils.getDate(), DateUtils.datetimeFormat.get()) + "   第" + (page + 1) + "页");
                cellHead72.setCellStyle(cs2);


                // 合并单元格
                CellRangeAddress c = new CellRangeAddress(page * 20 + 0, page * 20 + 0, 0, 9); // 第一行空白
                CellRangeAddress c1 = new CellRangeAddress(page * 20 + 1, page * 20 + 1, 0, 8);// 第二行标题
                CellRangeAddress c2 = new CellRangeAddress(page * 20 + 2, page * 20 + 2, 0, 9);// 第三行地址
                CellRangeAddress c3 = new CellRangeAddress(page * 20 + 3, page * 20 + 3, 0, 9);// 第四行电话

                CellRangeAddress c4 = new CellRangeAddress(page * 20 + 4, page * 20 + 4, 0, 2);// 第5行 到货日期
                CellRangeAddress c42 = new CellRangeAddress(page * 20 + 4, page * 20 + 4, 3, 9);// 第5行预约单号
                CellRangeAddress c5 = new CellRangeAddress(page * 20 + 5, page * 20 + 5, 0, 2);// 第6行客户采购单号
                CellRangeAddress c52 = new CellRangeAddress(page * 20 + 5, page * 20 + 5, 3, 9);// 第6行月台
                CellRangeAddress c6 = new CellRangeAddress(page * 20 + 6, page * 20 + 6, 0, 2);// 第7行客户名称
                CellRangeAddress c62 = new CellRangeAddress(page * 20 + 6, page * 20 + 6, 3, 9);// 第7行车号
                CellRangeAddress c7 = new CellRangeAddress(page * 20 + 7, page * 20 + 7, 0, 4);//第7行客户电话
                CellRangeAddress c72 = new CellRangeAddress(page * 20 + 7, page * 20 + 7, 5, 9);//第7行打印时间
                sheet.addMergedRegion(c);
                sheet.addMergedRegion(c1);
                sheet.addMergedRegion(c2);
                sheet.addMergedRegion(c3);
                sheet.addMergedRegion(c4);
                sheet.addMergedRegion(c5);
                sheet.addMergedRegion(c6);
                sheet.addMergedRegion(c7);
                sheet.addMergedRegion(c42);
                sheet.addMergedRegion(c52);
                sheet.addMergedRegion(c62);
                sheet.addMergedRegion(c72);

                Cell cell73 = row.createCell(10);
                cell73.setCellValue("① 财务联 ② 客户联 ③司机联 ④回单联                                   ");
                cell73.setCellStyle(cs52);


                CellRangeAddress c73 = new CellRangeAddress(page * 20, page * 20 + 19, 10, 10);//第7行打印时间
                sheet.addMergedRegion(c73);

                Row rowColumnName = sheet.createRow((short) page * 20 + 8); // 列名
                String[] columnNames = {"序号", "商品编码", "商品名称", "生产日期", "品质", "箱数", "拆零数", "毛重/KG", "体积/cm³", "备注"};

                for (int i = 0; i < columnNames.length; i++) {
                    Cell cell = rowColumnName.createCell(i);
                    cell.setCellValue(columnNames[i]);
                    cell.setCellStyle(cs3);
                }
                int cellsNum = page * 20 + 8;
                int oversize = 0;
                if (size == pagesize && page == pagecount - 1) {
                    oversize = 1;
                }
                for (int i = page * pagesize; i < (page + 1) * pagesize + oversize; i++) {
                    if (i < size) {
                        cellsNum++;
                        Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
                        rowColumnValue.setHeight((short) 250);
                        Cell cell1 = rowColumnValue.createCell(0);
                        cell1.setCellValue(cerconNo);
                        cell1.setCellStyle(cs51);
                        Cell cell2 = rowColumnValue.createCell(1);
                        cell2.setCellValue(result.get(i).get("goods_id").toString());
                        cell2.setCellStyle(cs5);
                        Cell cell3 = rowColumnValue.createCell(2);
                        cell3.setCellValue(result.get(i).get("shp_ming_cheng").toString());
                        cell3.setCellStyle(cs5);
                        try {
                            Cell cell4 = rowColumnValue.createCell(3);// 生产日期
                            cell4.setCellValue(result.get(i).get("pro_data").toString());
                            cell4.setCellStyle(cs5r);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // TODO: handle exception
                        }

                        try {
                            Cell cell5 = rowColumnValue.createCell(4);// 品质
                            cell5.setCellValue("");
                            cell5.setCellStyle(cs5);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // TODO: handle exception
                        }

                        try {
                            long xs = (long) Math.floor(Double.parseDouble(result.get(i).get("goods_count").toString()) / Double.parseDouble(result.get(i).get("chl_shl").toString()));
                            sumxs = sumxs + xs;
                            Cell cell6 = rowColumnValue.createCell(5);// 单位
                            cell6.setCellValue(xs);
                            cell6.setCellStyle(cs5);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // TODO: handle exception
                        }

                        try {
                            double bs = Double.parseDouble(result.get(i).get("goods_count").toString()) % Double.parseDouble(result.get(i).get("chl_shl").toString());
                            sum = sum + bs;
                            Cell cell7 = rowColumnValue.createCell(6);// 数量
                            cell7.setCellValue(bs);
                            cell7.setCellStyle(cs5);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // TODO: handle exception
                        }
                        Cell cell8 = rowColumnValue.createCell(7);// 毛重
                        try {
                            double zhl = Double.parseDouble(result.get(i).get("tin_zhl").toString()) * Double.parseDouble(result.get(i).get("goods_count").toString());
                            sumzl = sumzl + zhl;
                            cell8.setCellValue(zhl);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // TODO: handle exception
                        }
                        cell8.setCellStyle(cs5);
                        Cell cell9 = rowColumnValue.createCell(8);// 体积

                        cell9.setCellStyle(cs5);
                        Cell cell10 = rowColumnValue.createCell(9);// 备注
                        cell10.setCellStyle(cs5);
                        cerconNo++;
                    }
                    if (i == size) {
                        cellsNum++;
                        Row rowColumnValue = sheet.createRow((short) cellsNum); // 列名
                        rowColumnValue.setHeight((short) 250);
                        Cell cell5 = rowColumnValue.createCell(5);// 备注
                        cell5.setCellValue(Double.toString(sumxs));
                        Cell cell6 = rowColumnValue.createCell(6);// 备注
                        cell6.setCellValue(Double.toString(sum));
                        Cell cell7 = rowColumnValue.createCell(7);// 重量合计
                        cell7.setCellValue(Double.toString(sumzl));
                        Cell cell0 = rowColumnValue.createCell(0);// 合计
                        cell0.setCellValue("合计：");
                        CellRangeAddress c15 = new CellRangeAddress(cellsNum,
                                cellsNum, 0, 4);
                        sheet.addMergedRegion(c15);
                        cerconNo++;

                    }
                }
                Row rowColumnInfo = sheet.createRow((short) 1 + cellsNum); // 列名
                rowColumnInfo.setHeight((short) 250);
                rowColumnInfo.createCell(0).setCellValue(
                        "  发货人员：                               配送司机：                               收货人员：	");
                CellRangeAddress c15 = new CellRangeAddress(1 + cellsNum,
                        1 + cellsNum, 0, 9);
                sheet.addMergedRegion(c15);

                Row rowColumnInfo2 = sheet.createRow((short) 2 + cellsNum); // 列名
                rowColumnInfo2.setHeight((short) 250);
                rowColumnInfo2.createCell(0).setCellValue(
                        "  发货时间：                               收货时间：                               收货单位盖章：	");
                CellRangeAddress c152 = new CellRangeAddress(2 + cellsNum,
                        2 + cellsNum, 0, 9);
                sheet.addMergedRegion(c152);
                page++;
            } while (page < pagecount);
            fileOut = response.getOutputStream();
            HSSFPrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setPaperSize(HSSFPrintSetup.A5_PAPERSIZE);

            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   /* @Override
    public Result<?> doPrintpage(String id) {
        //修改状态
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        wmOmNoticeH.setPrintStatus("已打印");
        wmOmNoticeHMapper.updateById(wmOmNoticeH);
        //创建返回数据集
        Map<String, Object> map = new HashMap<>(1024);
        map.put("wmOmNoticeHPage", wmOmNoticeH);
        map.put("kprq", DateUtils.date2Str(wmOmNoticeH.getCreateTime(), DateUtils.date_sdf.get()));
        if (StringUtil.isNotEmpty(wmOmNoticeH.getImCusCode())) {
            map.put("noticeid", wmOmNoticeH.getImCusCode());
        } else {
            map.put("noticeid", wmOmNoticeH.getOmNoticeId());
        }
        QueryWrapper<MdCus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCus md = mdCusMapper.selectOne(queryWrapper1);

        QueryWrapper<MdCusOther> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCusOther mdCusOther = mdCusOtherMapper.selectOne(queryWrapper2);
        if (mdCusOther != null) {
            map.put("cusname", wmOmNoticeH.getCusCode() + "-" + md.getZhongWenQch());
        }

        if (ObjectUtil.isNotEmpty(mdCusOther)) {
            map.put("ocusname", wmOmNoticeH.getOcusCode() + "-" + mdCusOther.getZhongWenQch());
        } else {
//            map.put("ocusname", wmOmNoticeH.getOcusCode());
            map.put("ocusname", wmOmNoticeH.getCusName());
        }

        //获取参数
        Object id0 = wmOmNoticeH.getOmNoticeId();

        Double tomsum = 0.00;
        Double noticesum = 0.00;
        Double tijisum = 0.00;
        Double zhlsum = 0.00;

        QueryWrapper<WmOmQmI> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("om_notice_id", id0);
        queryWrapper3.gt("qm_ok_quat", "0");
        //queryWrapper3.orderByAsc("bin_id");
        List<WmOmQmI> wmOmQmIList = wmOmQmIMapper.selectList(queryWrapper3);





        DecimalFormat dfsum = new DecimalFormat(".##");
        for (WmOmQmI tom : wmOmQmIList) {

            tomsum = tomsum + Double.parseDouble(tom.getBaseGoodscount());
            if (StringUtils.isNotEmpty(tom.getTinTj())) {
                tijisum = tijisum + Double.parseDouble(tom.getTinTj());
                zhlsum = zhlsum + Double.parseDouble(tom.getTinZhl());
                tom.setTinZhl(dfsum.format(Double.parseDouble(tom.getTinZhl())));

                tom.setTinTj(dfsum.format(Double.parseDouble(tom.getTinTj())));
            }
            tom.setBaseGoodscount(getdouble(tom.getBaseGoodscount()));
            QueryWrapper<MvGoods> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("goods_code", tom.getGoodsId());
            MvGoods goods = mvGoodsMapper.selectOne(queryWrapper4);
            WvStock sumById = smsSend.findSumById(tom.getGoodsId());
            if (sumById != null) {
                tom.setAllStock(sumById.getGoodsQua());
            }

            WmOmNoticeI noticeI = wmOmNoticeIMapper.selectById(tom.getIomNoticeItem());
            if (noticeI != null) {
                tom.setOmBeiZhu(noticeI.getOmBeiZhu());
            }


            if (ObjectUtil.isNotEmpty(goods)) {
                tom.setShpGuiGe(goods.getShpGuiGe());
            }
            int shpguige = 0;
            if (StringUtil.isNotEmpty(goods.getShpGuiGe())) {
                shpguige = Integer.parseInt(goods.getShpGuiGe());
            }
            if (shpguige != 0) {
                Double xianhshu = Math.floor(Double.parseDouble(tom.getBaseGoodscount()) / shpguige);
                Double jianshu = Double.parseDouble(tom.getBaseGoodscount()) % shpguige;
                long xiangshuint = Math.round(xianhshu);
                if (xianhshu > 0) {
                    tom.setPickNotice(xiangshuint + "整" + jianshu + tom.getBaseUnit());
                } else {
                    tom.setPickNotice(tom.getBaseGoodscount() + tom.getBaseUnit());
                }
            }
        }
        QueryWrapper<WmOmNoticeI> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("om_notice_id", wmOmNoticeH.getOmNoticeId());
        List<WmOmNoticeI> wmOmNoticeIEntityList = wmOmNoticeIMapper.selectList(queryWrapper);//获取行项目
        for (WmOmNoticeI tnotice : wmOmNoticeIEntityList) {
            noticesum = noticesum + Double.parseDouble(tnotice.getBaseGoodscount());
        }
        if (Double.doubleToLongBits(noticesum) != Double.doubleToLongBits(tomsum)) {
            map.put("jianhuoremark", "订单：" + dfsum.format(noticesum) + " 拣货：" + dfsum.format(tomsum));
        } else {
            map.put("jianhuoremark", "全部拣货，共" + dfsum.format(noticesum));
        }
        String tijiunit = "立方分米";
        String zhongliangunit = "公斤";

        map.put("tijisum", dfsum.format(tijisum) + tijiunit);
        map.put("zhlsum", dfsum.format(zhlsum) + zhongliangunit);
        map.put("wmOmQmIList", wmOmQmIList);
        return Result.ok(map);
    }*/


    @Override
    public Result<?> doPrintpage(String id) {
        //修改状态
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        wmOmNoticeH.setPrintStatus("已打印");
        wmOmNoticeHMapper.updateById(wmOmNoticeH);
        //创建返回数据集
        Map<String, Object> map = new HashMap<>(1024);
        map.put("wmOmNoticeHPage", wmOmNoticeH);
        map.put("kprq", DateUtils.date2Str(wmOmNoticeH.getCreateTime(), DateUtils.date_sdf.get()));
        if (StringUtil.isNotEmpty(wmOmNoticeH.getImCusCode())) {
            map.put("noticeid", wmOmNoticeH.getImCusCode());
        } else {
            map.put("noticeid", wmOmNoticeH.getOmNoticeId());
        }
        QueryWrapper<MdCus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCus md = mdCusMapper.selectOne(queryWrapper1);

        QueryWrapper<MdCusOther> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCusOther mdCusOther = mdCusOtherMapper.selectOne(queryWrapper2);
        if (mdCusOther != null) {
            map.put("cusname", wmOmNoticeH.getCusCode() + "-" + md.getZhongWenQch());
        }

        if (ObjectUtil.isNotEmpty(mdCusOther)) {
            map.put("ocusname", wmOmNoticeH.getOcusCode() + "-" + mdCusOther.getZhongWenQch());
        } else {
//            map.put("ocusname", wmOmNoticeH.getOcusCode());
            map.put("ocusname", wmOmNoticeH.getCusName());
        }

        //获取参数
        Object id0 = wmOmNoticeH.getOmNoticeId();
        QueryWrapper<WmOmQmI> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("om_notice_id", id0);
        queryWrapper3.gt("qm_ok_quat", "0");
        //queryWrapper3.orderByAsc("bin_id");
        List<WmOmQmI> wmOmQmIList = wmOmQmIMapper.selectList(queryWrapper3);
        map.put("wmOmQmIList", wmOmQmIList);
        return Result.ok(map);
    }

    @Override
    public Result<?> doPrintpage1(String id) {
        //修改状态
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        wmOmNoticeH.setPrintStatus("已打印");
        wmOmNoticeHMapper.updateById(wmOmNoticeH);
        //创建返回数据集
        Map<String, Object> map = new HashMap<>(1024);
        map.put("wmOmNoticeHPage", wmOmNoticeH);
        map.put("kprq", DateUtils.date2Str(wmOmNoticeH.getCreateTime(), DateUtils.date_sdf.get()));
        if (StringUtil.isNotEmpty(wmOmNoticeH.getImCusCode())) {
            map.put("noticeid", wmOmNoticeH.getImCusCode());
        } else {
            map.put("noticeid", wmOmNoticeH.getOmNoticeId());
        }
        QueryWrapper<MdCus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCus md = mdCusMapper.selectOne(queryWrapper1);

        QueryWrapper<MdCusOther> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCusOther mdCusOther = mdCusOtherMapper.selectOne(queryWrapper2);
        map.put("imcuscode",wmOmNoticeH.getU8Djcode1());
        map.put("cusname", wmOmNoticeH.getCusCode() + "-" + md.getZhongWenQch());
        if (ObjectUtil.isNotEmpty(mdCusOther)) {
            map.put("ocusname", wmOmNoticeH.getOcusCode() + "-" + mdCusOther.getZhongWenQch());
        } else {
            map.put("ocusname", wmOmNoticeH.getOcusCode());
        }
        QueryWrapper<WmOmNoticeI> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("om_notice_id", wmOmNoticeH.getOmNoticeId());
        List<WmOmNoticeI> wmOmNoticeIEntityList = wmOmNoticeIMapper.selectList(queryWrapper);//获取行项目
        map.put("wmOmQmIList", wmOmNoticeIEntityList);
        return Result.ok(map);
    }

    @Override
    public Result<?> doPrintpageckd(String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        //创建返回数据集
        Map<String, Object> map = new HashMap<>(1024);
        map.put("area", "");
        map.put("tel", "");
        map.put("delvData", DateUtils.date2Str(wmOmNoticeH.getDelvData(), DateUtils.date_sdf.get()));
        map.put("omNoticeId", wmOmNoticeH.getOmNoticeId());
        map.put("imCusCode", wmOmNoticeH.getImCusCode());
        map.put("reCarno", wmOmNoticeH.getReCarno());
        QueryWrapper<MdCus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ke_hu_bian_ma", wmOmNoticeH.getCusCode());
        MdCus md = mdCusMapper.selectOne(queryWrapper1);
        map.put("zhongWenQch", wmOmNoticeH.getCusCode() + (md != null ? md.getZhongWenQch():""));
        map.put("delvMobile", wmOmNoticeH.getDelvMobile());
        map.put("delvMember", wmOmNoticeH.getDelvMember());
        map.put("delvAddr", wmOmNoticeH.getDelvAddr());
        map.put("printingTime", DateUtils.date2Str(DateUtils.getDate(), DateUtils.datetimeFormat.get()));
        map.put("omBeizhu", wmOmNoticeH.getOmBeizhu());
        List<Map<String, Object>> listitem = new ArrayList<>();
        List<Map<String, Object>> result = wmOmNoticeHMapper.findById(wmOmNoticeH.getOmNoticeId());
        int size = result.size();
        if (size < 1) {
            result = wmOmNoticeHMapper.findByOmNoticeId(wmOmNoticeH.getOmNoticeId());
            size = result.size();
        }
        long sumxs = 0;
        Double sum = 0.00;
        Double sumzl = 0.00;
        int cerconNo = 0;
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> map1 = new HashMap<>(1024);
            cerconNo++;
            map1.put("cerconNo", Integer.toString(cerconNo));
            map1.put("goodsId", result.get(i).get("goods_id") != null ? result.get(i).get("goods_id").toString() : "");
            map1.put("shpTiaoMa", result.get(i).get("shp_tiao_ma") != null ? result.get(i).get("shp_tiao_ma").toString() : "");
            map1.put("shpMingCheng", result.get(i).get("shp_ming_cheng").toString() != null ? result.get(i).get("shp_ming_cheng").toString() : "");
            map1.put("manufactureDate", result.get(i).get("pro_data").toString() != null ? result.get(i).get("pro_data").toString() : "");
            map1.put("baseUnit", result.get(i).get("base_unit").toString() != null ? result.get(i).get("base_unit").toString() : "");
            String id1 = result.get(i).get("order_id_i").toString();
            WmOmQmI wmOmQmI = wmOmQmIMapper.selectById(id1);
            WmOmNoticeI noticeI = wmOmNoticeIMapper.selectById(wmOmQmI.getIomNoticeItem());
            map1.put("itemText", noticeI.getOmBeiZhu());
            long xs = 0;
            if (StringUtils.isNotEmpty(NotNullUtils.tranObj2Str(result.get(i).get("chl_shl")))) {
                xs = (long) Math.floor(Double.parseDouble(result.get(i).get("goods_count").toString()) / Double.parseDouble(result.get(i).get("chl_shl").toString()));
            }

            sumxs = sumxs + xs;
            map1.put("numberOfCases", Long.toString(xs));

            sum = sum + Double.parseDouble(result.get(i).get("goods_count")
                    .toString());
            map1.put("number", result.get(i).get("goods_count")
                    .toString());
            double zhl = 0;
            if (StringUtils.isNotEmpty(NotNullUtils.tranObj2Str(result.get(i).get("tin_zhl")))) {
                zhl = Double.parseDouble(result.get(i).get("tin_zhl")
                        .toString()) * Double.parseDouble(result.get(i).get("goods_count").toString());
            }
            sumzl = sumzl + zhl;
            map1.put("weight", Double.toString(zhl));
            double tij = 0;
            if (StringUtils.isNotEmpty(NotNullUtils.tranObj2Str(result.get(i).get("tin_tj")))) {
                tij = Double.parseDouble(result.get(i).get("tin_tj")
                        .toString()) * Double.parseDouble(result.get(i).get("goods_count").toString());
            }
            map1.put("volume", Double.toString(tij));
            listitem.add(map1);
        }
        map.put("totalQuantity", sum.toString());
        map.put("totalWeight", sumzl.toString());
        map.put("listitem", listitem);
        return Result.ok(map);
    }

    public static String getdouble(String inDouble) {
        Double mainWastage = Double.parseDouble(inDouble);
        //主材损耗费取整
        if (mainWastage.intValue() - mainWastage == 0) {//判断是否符合取整条件
            return String.valueOf(mainWastage.intValue());
        } else {
            return String.valueOf(mainWastage);
        }
    }


    @Override
    public void insertAnalys(List<List<Object>> listob) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
               try {
                   //一个个的取值放入到对应的属性setXX()中
                   List<WmOmNoticeI> wmOmNoticeIList = new ArrayList<>();
                   List<WmOmNoticeH> wmOmNoticeHList = new ArrayList<>();
                   //声明变量，接受数据
                   String empName = null;
                   for (int i = 0; i < listob.size(); i++) {
                       List<Object> lo = listob.get(i);
                       WmOmNoticeI wmOmNoticeI = new WmOmNoticeI();
                       WmOmNoticeH wmOmNoticeH = new WmOmNoticeH();
                       //wmOmNoticeI.setOmNoticeId(String.valueOf(lo.get(5)).trim());
                       wmOmNoticeI.setImCusCode(String.valueOf(lo.get(3)).trim());
                       wmOmNoticeI.setImCusCode(String.valueOf(lo.get(3)).trim());
                       wmOmNoticeI.setCusCode(String.valueOf(lo.get(0)).trim());
                       wmOmNoticeI.setCusName(String.valueOf(lo.get(1)).trim());
                       wmOmNoticeI.setBaseGoodscount(String.valueOf(lo.get(6)).trim());
                       wmOmNoticeI.setGoodsQua(String.valueOf(lo.get(6)).trim());
                       wmOmNoticeI.setGoodsId(String.valueOf(lo.get(4)).trim());
                       wmOmNoticeI.setGoodsName(String.valueOf(lo.get(5)).trim());
                       wmOmNoticeI.setGoodsUnit(String.valueOf(lo.get(7)).trim());
                       wmOmNoticeIList.add(wmOmNoticeI);

                       wmOmNoticeH.setOmNoticeId(String.valueOf(lo.get(5)).trim());
                       try {
                           wmOmNoticeH.setDelvData(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(lo.get(2)).trim()));
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }
                       wmOmNoticeH.setCusCode(String.valueOf(lo.get(3)).trim());
                       wmOmNoticeH.setOmSta(ConstUtil.wm_sta1);
                       wmOmNoticeH.setOrderTypeCode("11");
                       wmOmNoticeH.setImCusCode(String.valueOf(lo.get(3)).trim());
                       wmOmNoticeHList.add(wmOmNoticeH);
                   }
                   List<WmOmNoticeH> res = wmOmNoticeHList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getImCusCode()))), ArrayList::new));
                   for (WmOmNoticeH wmOmNoticeH : res) {
                       String noticeid = getNoticeId(wmOmNoticeH);
                       wmOmNoticeH.setOmNoticeId(noticeid);
                       wmOmNoticeHMapper.insert(wmOmNoticeH);
                       List<WmOmNoticeI> list = wmOmNoticeIList.stream().filter(ls -> ls.getImCusCode().equals(wmOmNoticeH.getImCusCode())).collect(Collectors.toList());
                       if (list != null && list.size() > 0) {
                           for (WmOmNoticeI entity : list) {
                               entity = findWmOmNoticeIE(entity, wmOmNoticeH, noticeid);
                               wmOmNoticeIMapper.insert(entity);
                           }
                       }
                   }
                   SmsSendImpl.rundowntask("");
               }catch (Exception e) {
                   e.printStackTrace();
               }
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WMSResult declCancel(String src) {
        JSONObject jsonObject = JSON.parseObject(src);
        String orderId = jsonObject.getString("order_id");
        List<WmOmNoticeH> wmOmNoticeHS = wmOmNoticeHMapper.selectList(Wrappers.<WmOmNoticeH>lambdaQuery().select(WmOmNoticeH::getId,WmOmNoticeH::getOmSta,WmOmNoticeH::getImCusCode).eq(WmOmNoticeH::getImCusCode, orderId));
        List<WmOmQmI> wmOmQmIS = wmOmQmIMapper.selectList(Wrappers.<WmOmQmI>lambdaQuery().select(WmOmQmI::getId, WmOmQmI::getBaseGoodscount, WmOmQmI::getImCusCode).eq(WmOmQmI::getImCusCode, orderId));
        if (!CollectionUtils.isEmpty(wmOmQmIS) && !CollectionUtils.isEmpty(wmOmNoticeHS)) {
            List<WmToDownGoods> wmToDownGoods = wmToDownGoodsMapper.selectList(Wrappers.<WmToDownGoods>lambdaQuery().select(WmToDownGoods::getId, WmToDownGoods::getDownSta, WmToDownGoods::getBaseGoodscount, WmToDownGoods::getImCusCode).eq(WmToDownGoods::getImCusCode, orderId));
            if (!CollectionUtils.isEmpty(wmToDownGoods)) {
                for (WmToDownGoods wmToDownGood : wmToDownGoods) {
                    if ("已拣货".equals(wmToDownGood.getDownSta())) {
                        throw new JeecgBootException("已拣货，不能取消");
                    }
                    wmToDownGood.setBaseGoodscount("0");
                    wmToDownGoodsMapper.updateById(wmToDownGood);
                }
            }
            for (WmOmNoticeH wmOmNoticeH : wmOmNoticeHS) {
                if (wmOmNoticeH.getOmSta().equals(wm_sta6) || wmOmNoticeH.getOmSta().equals(wm_sta9)) {
                    throw new JeecgBootException("已复核或已拣货，不能取消！");
                }
                if (wmOmNoticeH.getOmSta().equals(wm_sta4)) {
                    throw new JeecgBootException("订单已完成出库，不能取消");
                }
                if (wmOmNoticeH.getOmSta().equals(wm_sta3)) {
                    throw new JeecgBootException("订单已取消，不能重复取消！");
                }
                wmOmNoticeH.setOmSta(wm_sta3);
                wmOmNoticeHMapper.updateById(wmOmNoticeH);
            }
            for (WmOmQmI wmOmQmI : wmOmQmIS) {
                wmOmQmI.setBaseGoodscount("0");
                wmOmQmIMapper.updateById(wmOmQmI);
            }

            //取消cds推送
            CancelOrderInfo cancelOrderInfo = CancelOrderInfo.builder()
                    .orderId(orderId).reason("订单取消")
                    .build();
            return WMSResult.success("订单取消成功!");
        }
        return WMSResult.error("订单取消失败，wms没有该订单数据!");
    }

    @Override
    public WmOmNoticeH getByDeliveryId(String deliveryId) {
        return baseMapper.getByDeliveryId(deliveryId);
    }

    @Override
    public Result<?> doPrintBqList(String id) {
        WmOmNoticeH wmOmNoticeH = wmOmNoticeHMapper.selectById(id);
        QueryWrapper<WmOmQmI> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("om_notice_id",wmOmNoticeH.getOmNoticeId());
        List<WmOmQmI> wmOmQmIList = wmOmQmIMapper.selectList(queryWrapper);
        List<ApiEntity> apiEntityList = new ArrayList<>();
        for(WmOmQmI wmOmQmI : wmOmQmIList){
            ApiEntity apiEntity = new ApiEntity();
            apiEntity.setId(wmOmQmI.getId());
            apiEntity.setQuery01(wmOmNoticeH.getU8Djcode1());
            apiEntity.setQuery02(wmOmNoticeH.getOcusName());
            apiEntity.setQuery03(DateUtils.formatDate(wmOmQmI.getUpdateTime(),"yyyy/MM/dd HH:mm"));
            apiEntity.setQuery04(wmOmQmI.getTinId());
            apiEntityList.add(apiEntity);
        }
        return Result.ok(apiEntityList);
    }
}
