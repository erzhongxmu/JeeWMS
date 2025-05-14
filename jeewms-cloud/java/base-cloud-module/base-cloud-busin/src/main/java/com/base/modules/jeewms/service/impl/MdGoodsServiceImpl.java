package com.base.modules.jeewms.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.common.base.dto.SkuInventory;
import com.base.common.base.dto.WMSResult;
import com.base.common.base.dto.WMSStockQueryDTO;
import com.base.common.util.DateUtils;
import com.base.modules.jeewms.controller.dto.FrozenMdGoodDTO;
import com.base.modules.jeewms.controller.dto.WmOmNoticeHDTO;
import com.base.modules.jeewms.entity.*;
import com.base.modules.jeewms.mapper.MdGoodsMapper;
import com.base.modules.jeewms.mapper.WmOmNoticeHMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.jeewms.vo.GoodsRegister;
import com.base.modules.jeewms.vo.GoodsRegisterRequest;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 商品信息
 * @Author: base-boot
 * @Date: 2021-05-17
 * @Version: V1.0
 */
@Service
public class MdGoodsServiceImpl extends ServiceImpl<MdGoodsMapper, MdGoods> implements IMdGoodsService {

    @Autowired
    private IMdGoodsService mdGoodsService;
    @Autowired
    private IWmOmNoticeHService wmOmNoticeHService;
    @Autowired
    private WmOmNoticeHMapper wmOmNoticeHMapper;
    @Autowired
    private IMvGoodsService mvGoodsService;
    @Autowired
    private IWmOmNoticeIService wmOmNoticeIService;
    @Autowired
    private MdGoodsMapper mdGoodsMapper;
    @Autowired
    private IBaPartTypeService baPartTypeService;

    /**
     * 下单
     *
     * @param wmOmNoticeH
     */
    @Override
    public void order(WmOmNoticeHDTO wmOmNoticeHPram) {

        MdGoods t = mdGoodsService.getById(wmOmNoticeHPram.getId());

        if (StringUtils.isEmpty(wmOmNoticeHPram.getImCusCode())) {
            throw new JeecgBootException("客户订单号不能为空");
        }
        if (StringUtils.isEmpty(wmOmNoticeHPram.getId())) {
            throw new JeecgBootException("id不能为空");
        }

        WmOmNoticeH wmOmNoticeH = null;
        List<WmOmNoticeH> wmomh = wmOmNoticeHService.lambdaQuery().eq(WmOmNoticeH::getImCusCode, wmOmNoticeHPram.getImCusCode()).list();
        if (wmomh != null && wmomh.size() > 0) {
            wmOmNoticeH = wmomh.get(0);
        } else {
            wmOmNoticeH = new WmOmNoticeH();
            wmOmNoticeH.setCreateBy(wmOmNoticeHPram.getCreateBy());
            wmOmNoticeH.setOrderTypeCode("11");
            wmOmNoticeH.setCusCode(t.getSuoShuKeHu());
            String noticeid = getNextNoticeId(wmOmNoticeH.getOrderTypeCode());
            wmOmNoticeH.setOmNoticeId(noticeid);
            wmOmNoticeH.setImCusCode(wmOmNoticeHPram.getImCusCode());
            wmOmNoticeH.setReCarno(wmOmNoticeHPram.getReCarno());
            wmOmNoticeH.setOmBeizhu(wmOmNoticeHPram.getImBeizhu());

            wmOmNoticeHService.save(wmOmNoticeH);
        }
        WmOmNoticeI wmi = new WmOmNoticeI();
        wmi.setGoodsId(t.getShpBianMa());
        wmi.setOmNoticeId(wmOmNoticeH.getOmNoticeId());
        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmi.getGoodsId()).one();
        if (mdGoods != null) {
            wmi.setGoodsName(t.getShpMingCheng());
            wmi.setGoodsUnit(t.getShlDanWei());
        }
        wmi.setCusCode(wmOmNoticeH.getCusCode());
        try {
            wmi.setGoodsQua(wmOmNoticeHPram.getGoodsQua());//
        } catch (Exception e) {

        }
        wmOmNoticeIService.save(wmi);
    }

    @Override
    public Result<?> add(MdGoods mdGoods) {
        QueryWrapper<MdGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shp_bian_ma", mdGoods.getShpBianMa());
        MdGoods mdGoods1 = mdGoodsMapper.selectOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(mdGoods1)) {
            return Result.ok("编码重复");
        }
        mdGoods.setShpBianMakh(mdGoods.getSuoShuKeHu());
        int row = mdGoodsMapper.insert(mdGoods);
        if (row > 0) {
            return Result.ok("添加成功");
        }
        return Result.ok("添加失败");
    }

    @Override
    public void frozen(FrozenMdGoodDTO param) {
        //查询商品
        MdGoods mdGoods = this.getById(param.getId());
        if (mdGoods == null) {
            throw new JeecgBootException("商品不存在");
        }
        switch (param.getFrozen()) {
            case "Y":
                if ("Y".equals(mdGoods.getFrozen())) {
                    throw new JeecgBootException("已冻结");
                }else {
                    mdGoods.setFrozen(param.getFrozen());
                }
                break;

            case "N":
                if ("N".equals(mdGoods.getFrozen())) {
                    throw new JeecgBootException("已解冻");
                }else {
                    mdGoods.setFrozen(param.getFrozen());
                }
                break;
            default:
                throw new JeecgBootException("解冻状态错误");
        }
        this.updateById(mdGoods);

    }

    @Override
    public void goodsToWMS(GoodsRegisterRequest goodsRegister) {
        BaPartType partType = baPartTypeService.getOne(Wrappers.<BaPartType>lambdaQuery().eq(BaPartType::getTypeName, "默认"));
        List<String> barcodeList = goodsRegister.getGoods().stream().map(GoodsRegister::getBarCode).collect(Collectors.toList());
        List<MdGoods> mdGoodsList = mdGoodsService.list(Wrappers.<MdGoods>lambdaQuery().select(MdGoods::getShpBianMa, MdGoods::getId).in(MdGoods::getShpBianMa, barcodeList));
        Map<String, String> goodsIdMap = mdGoodsList.stream().collect(Collectors.toMap(MdGoods::getShpBianMa, MdGoods::getId));
        for (GoodsRegister good : goodsRegister.getGoods()) {
            MdGoods mdGood = new MdGoods();
            //商品编码
            mdGood.setShpBianMa(good.getSkuId());
            //商品条形码
            mdGood.setShpTiaoMa(good.getBarCode());
            //商品唯一主键
            mdGood.setDnExternal(good.getSkuId());
            //商品唯一主键
            mdGood.setStyleCode(good.getSkuId());
            //商品名称
            mdGood.setShpMingCheng(good.getGoodsName());
            //商品料号
            mdGood.setCusNo(good.getMaterialNo());
            //商品税号
            mdGood.setHsCode(good.getGoodsNo());
            //币制
            mdGood.setCurr(good.getCurrency());
            //商品备案序号
            mdGood.setGoodsProductNo(String.valueOf(good.getSeqNo()));
            //商品规格型号
            mdGood.setShpXingHao(good.getModel());
            //申报单位
            mdGood.setGoodsUnit(good.getQuantityUnitCode());
            //法定第一计量单位
            mdGood.setExtend7(good.getLegalUnitCode());
            //法定第二计量单位
            mdGood.setExtend4(good.getSecondLegalUnitCode());
            //第一法定数量
            mdGood.setExtend5(String.valueOf(good.getLegalScaleFactor()));
            //第二法定数量
            mdGood.setExtend6(String.valueOf(good.getSecondLegalScaleFactor()));
            //商品产地国
            mdGood.setCountryOrigin(good.getOriginCountry());
            //商品品牌
            mdGood.setShpPinPai(good.getBrandName());
            //商品毛重
            mdGood.setZhlKgm(String.valueOf(good.getGrossWeight()));
            //商品净重
            mdGood.setZhlKg(String.valueOf(good.getNetWeight()));
            mdGood.setCreateBy("admin");
//            if (goodsIdMap.get(mdGood.getShpBianMa()) != null){
//                //mdGood.setUpdateTime(new Date());
//                mdGood.setId(goodsIdMap.get(mdGood.getShpBianMa()));
//                mdGoodsService.update(Wrappers.<MdGoods>lambdaUpdate().setEntity(mdGood));
//            }else{
//                mdGoodsService.save(mdGood);
//            }
            mdGood.setSuoShuKeHu("001");
            mdGood.setShpBianMakh(mdGood.getSuoShuKeHu());
            mdGood.setBomZw(good.getGoodsName());
            mdGood.setSnpTray(good.getModel());
            mdGood.setId(goodsIdMap.get(mdGood.getShpBianMa()));
            if (!Objects.isNull(partType)) {
                mdGood.setClassification(partType.getId());
            }
            mdGoodsService.saveOrUpdate(mdGood);
        }
    }

    @Override
    public WMSResult stockQuery(WMSStockQueryDTO wmsStockQueryDTO) {
        try {
            //查出wms商品的库存（良次品混合在一起）
            List<WmsToShopStock> goodsStock = this.baseMapper.stockQuery(wmsStockQueryDTO.getSku_ids());
            //过滤出良品库存
            List<WmsToShopStock> goodsCounts = goodsStock.stream().filter(wmsToShopStock -> "良品".equals(wmsToShopStock.getGoodsType())).collect(Collectors.toList());
            //过滤出次品库存
            List<WmsToShopStock> badCounts = goodsStock.stream().filter(wmsToShopStock -> "次品".equals(wmsToShopStock.getGoodsType())).collect(Collectors.toList());
            //List<WmsToShopStock> stockSumList = goodsCounts.stream().filter(wmsToShopStock -> wmsToShopStock.getStockType().equals("库存")).collect(Collectors.toList());
            //区分锁扣库存
            //List<WmsToShopStock> lockStockList = goodsCounts.stream().filter(wmsToShopStock -> wmsToShopStock.getStockType().equals("待下架")).collect(Collectors.toList());
            //生成以skuId为键的map集合库存
            Map<String, Integer> stockSumMap = goodsCounts.stream().collect(Collectors.toMap(WmsToShopStock::getSkuId, WmsToShopStock::getStock));
            //Map<String, Integer> lockStockMap = lockStockList.stream().collect(Collectors.toMap(WmsToShopStock::getSkuId, WmsToShopStock::getStock));
            Map<String, Integer> badCountMap = badCounts.stream().collect(Collectors.toMap(WmsToShopStock::getSkuId, WmsToShopStock::getStock));
            List<SkuInventory> skuInventoryList = new ArrayList<>();
            //组装每个skuId底下的数据
            for (String skuId : wmsStockQueryDTO.getSku_ids()) {
                SkuInventory skuInventory = new SkuInventory();
                skuInventory.setSku_id(skuId);
                skuInventory.setGood_count(stockSumMap.get(skuId) == null ? 0 : stockSumMap.get(skuId));
                skuInventory.setBad_count(badCountMap.get(skuId) == null ? 0 : badCountMap.get(skuId));
                skuInventory.setLock_sum(0);
                skuInventoryList.add(skuInventory);
            }
            WMSResult result = WMSResult.success();
            result.setData(skuInventoryList);
            return result;
        } catch (Exception e) {
            return WMSResult.error(e.getMessage());
        }
    }

    /**
     * 查询通知单id
     *
     * @param orderType
     * @return
     */
    String getNextNoticeId(String orderType) {


        String noticeid = null;
        int newcount = 1;

        try {
            newcount = wmOmNoticeHMapper.selectNoticeIdCount();
        } catch (Exception e) {
        }
        if (StringUtil.isEmpty(orderType)) {
            orderType = "11";
        }
        if ("19".equals(orderType)) {
            noticeid = "QTCK"
                    + DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else if ("11".equals(orderType)) {
            noticeid = "CK"
                    + DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        } else {
            noticeid = "QT"
                    + DateUtils.date2Str(new Date(), new SimpleDateFormat("yyyyMMdd"))
                    + "-"
                    + String.format("%04d", newcount);
        }
        return noticeid;
    }
}
