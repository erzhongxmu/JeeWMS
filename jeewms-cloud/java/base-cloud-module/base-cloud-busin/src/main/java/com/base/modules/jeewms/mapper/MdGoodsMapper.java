package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.modules.jeewms.entity.MdGoods;
import com.base.modules.jeewms.entity.WmsToShopStock;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.vo.DictModel;

import java.util.List;

/**
 * @Description: 商品信息
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
public interface MdGoodsMapper extends BaseMapper<MdGoods> {

    public List<DictModel> queryDictItemsByCode(@Param("code") String code);

    @Deprecated
    public List<DictModel> queryTableDictItemsByCode(@Param("table") String table,@Param("text") String text,@Param("code") String code);

    List<WmsToShopStock> stockQuery(List<String> skuIds);

}
