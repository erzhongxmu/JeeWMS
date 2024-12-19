package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.WmSttInGoods;

/**
 * @Description: wm_stt_in_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
public interface WmSttInGoodsMapper extends BaseMapper<WmSttInGoods> {

    /**
     * 盘点差异过账
     *
     * @param page
     * @param wmSttInGoods
     * @return
     */
    default IPage<WmSttInGoods> differentPageList(Page<WmSttInGoods> page, @Param("wmSttInGoods") WmSttInGoods wmSttInGoods) {
        return null;
    }
}
