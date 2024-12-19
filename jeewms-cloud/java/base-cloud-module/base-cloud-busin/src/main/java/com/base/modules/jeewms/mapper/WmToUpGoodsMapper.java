package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmToUpGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: wm_to_up_goods
 * @Author: base-boot
 * @Date: 2021-05-26
 * @Version: V1.0
 */
@Repository
public interface WmToUpGoodsMapper extends BaseMapper<WmToUpGoods> {

    /**
     * 查询到达库存临界点的商品
     *
     * @return
     */
    List<MdGoods> selectLowStockGoods();


}
