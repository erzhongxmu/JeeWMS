package com.base.modules.jeewms.service.impl;

import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmToUpGoods;
import com.base.modules.jeewms.mapper.WmToUpGoodsMapper;
import com.base.modules.jeewms.service.IWmToUpGoodsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: wm_to_up_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
@Service
public class WmToUpGoodsServiceImpl extends ServiceImpl<WmToUpGoodsMapper, WmToUpGoods> implements IWmToUpGoodsService {

    @Override
    public List<MdGoods> getLowStockGoods() {

        return this.getBaseMapper().selectLowStockGoods();
    }
}
