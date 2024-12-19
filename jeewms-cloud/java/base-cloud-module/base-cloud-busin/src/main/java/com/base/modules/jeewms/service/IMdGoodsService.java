package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.common.base.dto.WMSResult;
import com.base.common.base.dto.WMSStockQueryDTO;
import com.base.modules.jeewms.controller.dto.FrozenMdGoodDTO;
import com.base.modules.jeewms.controller.dto.WmOmNoticeHDTO;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.vo.GoodsRegisterRequest;

/**
 * @Description: 商品信息
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
public interface IMdGoodsService extends IService<MdGoods> {

    /**
     * 下单
     * @param wmOmNoticeH
     */
    void order(WmOmNoticeHDTO wmOmNoticeH);

    Result<?> add(MdGoods mdGoods);

    /**
     * 冻结/解冻
     * @param param
     */
    void frozen(FrozenMdGoodDTO param);

    void goodsToWMS(GoodsRegisterRequest goodsRegister);

    WMSResult stockQuery(WMSStockQueryDTO wmsStockQueryDTO);

}
