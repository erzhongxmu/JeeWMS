package com.base.modules.jeewms.service;

import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmToUpGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: wm_to_up_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
public interface IWmToUpGoodsService extends IService<WmToUpGoods> {

    /**
     * 查询库存到达临界点的数据
     * @return
     */
    List<MdGoods> getLowStockGoods();

}
