package com.base.modules.jeewms.mapper;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.base.modules.jeeerp.vo.BusiQueryPage;
import com.base.modules.jeewms.entity.WmToDownGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: 下架明细
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
@Repository
public interface WmToDownGoodsMapper extends BaseMapper<WmToDownGoods> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WmToDownGoods> selectByMainId(@Param("mainId") String mainId);


    IPage<WmToDownGoods> orderPicking(@Param("iPage") IPage<WmToDownGoods> page, @Param("querymap") HashMap<String, String> querymap);
}
