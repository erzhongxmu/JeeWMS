package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmSttInGoods;
import com.base.modules.jeewms.entity.WmToDownGoods;
import com.base.modules.jeewms.entity.WmToUpGoods;
import com.base.modules.jeewms.mapper.WmSttInGoodsMapper;
import com.base.modules.jeewms.mapper.WvStockMapper;
import com.base.modules.jeewms.service.*;
import com.base.modules.util.ConstUtil;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description: wm_stt_in_goods
 * @Author: base-boot
 * @Date: 2021-05-26
 * @Version: V1.0
 */
@Service
public class WmSttInGoodsServiceImpl extends ServiceImpl<WmSttInGoodsMapper, WmSttInGoods> implements IWmSttInGoodsService {

    @Autowired
    private WmSttInGoodsMapper wmSttInGoodsMapper;
    @Autowired
    private IMvGoodsService mvGoodsService;
    @Autowired
    private WvStockMapper wvStockMapper;
    @Autowired
    private IWmToDownGoodsService wmToDownGoodsService;
    @Autowired
    private IWmToUpGoodsService wmToUpGoodsService;
    @Autowired
    private IMdGoodsService mdGoodsService;

    /**
     * 盘点差异过账
     *
     * @param pageNo
     * @param pageSize
     * @param wvStockStt
     * @return
     */
    @Override
    public IPage<WmSttInGoods> differentPageList(Integer pageNo, Integer pageSize, WmSttInGoods wmSttInGoods) {
        Page<WmSttInGoods> page = new Page<>(pageNo, pageSize);
        return wmSttInGoodsMapper.differentPageList(page, wmSttInGoods);
    }

    /**
     * 差异过账：生产上架、下架任务
     *
     * @param wmSttInGoodsParam
     */
    @Override
    public void doDifferent(WmSttInGoods wmSttInGoodsParam){

        WmSttInGoods wmSttInGoods = this.getById(wmSttInGoodsParam.getId());

        if ("已完成".equals(wmSttInGoods.getSttSta())) {

            if (new BigDecimal(wmSttInGoods.getGoodsQua()).compareTo(new BigDecimal(wmSttInGoods.getSttQua())) != 0) {
                try {
                    if (Double.parseDouble(wmSttInGoods.getGoodsQua()) > Double.parseDouble(wmSttInGoods.getSttQua())) {
                        //下架
                        String goodsqua = Double.toString((Double.parseDouble(wmSttInGoods.getGoodsQua()) - Double.parseDouble(wmSttInGoods.getSttQua())));
                        WmToDownGoods wmToDownGoods = new WmToDownGoods();

                        wmToDownGoods.setOrderId("ZY");
                        wmToDownGoods.setBinIdTo("PK");
                        wmToDownGoods.setDownSta("已复核");
                        wmToDownGoods.setGoodsQua(goodsqua);
                        wmToDownGoods.setGoodsQuaok(goodsqua);
                        wmToDownGoods.setRemarks("FXKW");
                        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmSttInGoods.getGoodsId()).one();
                        if (mdGoods != null) {
                            wmToDownGoods.setGoodsId(wmSttInGoods.getGoodsId());
                            wmToDownGoods.setBinIdFrom(wmSttInGoods.getTinId());
                            wmToDownGoods.setKuWeiBianMa(wmSttInGoods.getBinId());
                            wmToDownGoods.setGoodsProData(wmSttInGoods.getGoodsProData());
                            wmToDownGoods.setBaseUnit(mdGoods.getShlDanWei());
                            //批次
                            wmToDownGoods.setGoodsBatch(wmSttInGoods.getGoodsBatch());
                            wmToDownGoods.setGoodsUnit(mdGoods.getShlDanWei());
                            wmToDownGoods.setBaseGoodscount(wmToDownGoods
                                    .getGoodsQuaok());
                            wmToDownGoods.setCusCode(wmSttInGoods.getCusCode());

                            String gooodsId = wmToDownGoods.getGoodsId();
                            if (StringUtils.isEmpty(gooodsId)) {
                                throw new JeecgBootException("商品编码为空");
                            }
                            if (gooodsId.endsWith("l")) {
                                gooodsId = gooodsId.substring(0, gooodsId.length() - 1);
                            } else {
                                gooodsId = gooodsId;
                            }
                            //检查库存
                            double stock = wvStockMapper.getStack(wmToDownGoods.getKuWeiBianMa(), wmToDownGoods.getBinIdFrom(), gooodsId);
                            if (stock < Double.parseDouble(wmToDownGoods.getGoodsQua())) {
                                throw new JeecgBootException("库存不足");
                            }
                            wmToDownGoodsService.save(wmToDownGoods);
                            wmSttInGoods.setSttSta("已过帐");
                            this.saveOrUpdate(wmSttInGoods);
                        }
                    } else {
                        //上架
                        String goodsqua = Double.toString((Double.parseDouble(wmSttInGoods.getSttQua()) - Double.parseDouble(wmSttInGoods.getGoodsQua())));

                        WmToUpGoods wmToUpGoods = new WmToUpGoods();
                        wmToUpGoods.setOrderId("ZY");
                        MdGoods mdGoods = mdGoodsService.lambdaQuery().eq(MdGoods::getShpBianMa, wmSttInGoods.getGoodsId()).one();
                        if (mdGoods != null) {
                            wmToUpGoods.setBaseUnit(mdGoods.getShlDanWei());
                            wmToUpGoods.setGoodsUnit(mdGoods.getShlDanWei());
                            wmToUpGoods.setBaseGoodscount(goodsqua);
                            wmToUpGoods.setGoodsQua(goodsqua);
                            wmToUpGoods.setGoodsName(mdGoods.getShpMingCheng());
                            wmToUpGoods.setGoodsId(wmSttInGoods.getGoodsId());
                            wmToUpGoods.setBinId(wmSttInGoods.getTinId());
                            wmToUpGoods.setCusName(wmSttInGoods.getCusName());
                            wmToUpGoods.setTenantId(wmSttInGoods.getTenantId());
                            wmToUpGoods.setKuWeiBianMa(wmSttInGoods.getBinId());
                            wmToUpGoods.setCusCode(wmSttInGoods.getCusCode());
                            //批次
                            wmToUpGoods.setGoodsBatch(wmSttInGoods.getGoodsBatch());
                            wmToUpGoods.setGoodsProData(wmSttInGoods.getGoodsProData());
                            wmToUpGoodsService.save(wmToUpGoods);
                            wmSttInGoods.setSttSta("已过帐");
                            this.saveOrUpdate(wmSttInGoods);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        wmSttInGoods.setSttSta("已过帐");
        this.saveOrUpdate(wmSttInGoods);
    }

    @Override
    public Result<?> inventory(WmSttInGoods wmSttInGoods) {
        if (judge(wmSttInGoods)) {
            return Result.ok("已盘，请进行下一条的盘点");
        }
        //盘点接口
        try {
            Double.parseDouble(wmSttInGoods.getSttQua());
        } catch (Exception e) {
            return Result.error("盘点数量不正确");
        }
        wmSttInGoods.setSttSta(ConstUtil.wm_sta4);
        WmSttInGoods stt = wmSttInGoodsMapper.selectById(wmSttInGoods.getId());
        BigDecimal goodCount = new BigDecimal(stt.getGoodsQua());
        BigDecimal sttCount = new BigDecimal(wmSttInGoods.getSttQua());
        if (goodCount.compareTo(sttCount) == 0) {
            wmSttInGoods.setDiffQua("0");
        }else{
            wmSttInGoods.setRealQua(wmSttInGoods.getSttQua());
            wmSttInGoods.setDiffQua((goodCount.subtract(sttCount)).toString());
        }
        int row = wmSttInGoodsMapper.updateById(wmSttInGoods);
        if (row > 0) {
            return Result.ok("盘点完成");
        }
        return Result.ok("盘点未完成，请重新操作！！");
    }


    private boolean judge(WmSttInGoods wmSttInGoods) {
        WmSttInGoods wmSttIn = wmSttInGoodsMapper.selectById(wmSttInGoods.getId());
        if (ConstUtil.wm_sta4.equals(wmSttIn.getSttSta())) {
            return true;
        }
        return false;
    }
}
