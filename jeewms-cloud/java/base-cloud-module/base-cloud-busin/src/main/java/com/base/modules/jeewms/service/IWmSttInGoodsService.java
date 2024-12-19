package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmSttInGoods;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: wm_stt_in_goods
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
public interface IWmSttInGoodsService extends IService<WmSttInGoods> {

    /**
     * 盘点差异过账
     * @param pageNo
     * @param pageSize
     * @param wvStockStt
     * @return
     */
    IPage<WmSttInGoods> differentPageList(Integer pageNo, Integer pageSize, WmSttInGoods wmSttInGoods);

    /**
     * 差异过账: 生成上架、下架任务
     * @param wmSttInGoods
     */
    void doDifferent(WmSttInGoods wmSttInGoods);

    public Result<?> inventory(WmSttInGoods wmSttInGoods);

}
