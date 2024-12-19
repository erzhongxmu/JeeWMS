package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeewms.entity.WmSttInGoods;
import com.base.modules.jeewms.entity.WvStock;
import com.base.modules.jeewms.entity.WvStockStt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WvStock1Mapper extends BaseMapper<WvStock> {


    double getStack(@Param("binId") String binId, @Param("tinId") String tinId,@Param("gooodsId") String gooodsId);

    /**
     * pda查询库存
     * @param page
     * @param wvStock
     * @return
     */
    IPage<WvStock> pdaStockList(Page<WvStock> page,@Param("wvStock") WvStock wvStock);

    WvStock getKwByTinId(String tinId);

    List<WvStock> getTinIdListByBinId(String binId);

    WvStock selectKCByBinIdAndTindId(@Param("binId") String binId,@Param("tinId") String tinId,@Param("goodsId") String goodsId);

    /**
     * 查询库存
     * @param kwCode
     * @param goodsId
     * @return
     */
    List<WvStock> getStockByKwAndGoodsId(@Param("kwCode") String kwCode);

    /**
     *
     * @param id
     * @return
     */
    WvStock selectStockById(String id);

}
