package com.base.modules.jeewms.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.entity.WmSttInGoods;
import com.base.modules.jeewms.entity.WvStock;
import com.base.modules.jeewms.entity.WvStockStt;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
 * @Description: 盘点视图
 * @Author: base-boot
 * @Date:   2021-05-26
 * @Version: V1.0
 */
public interface IWvStockSttService extends IService<WvStockStt> {

    /**
     * 生成盘点单
     * @param id
     */
    void generate(List<String> id,String type);
    void generateSw(List<String> id,String type);

    /**
     * pda查询库存
     * @param page
     * @param wvStock
     * @return
     */
    IPage<WmSttInGoods> pdaStockList(Page<WvStock> page, WvStock wvStock);

    /**
     * 综合盘点
     * @param queryWrapper
     * @param page
     * @return
     */
    IPage<WvStockStt> getZhList(WvStockStt wvStockStt, QueryWrapper<WvStockStt> queryWrapper, Page<WvStockStt> page, HttpServletRequest req);

    WvStock getKwByTinId(String tinId);

    List<WvStock> getTinIdListByBinId(String binId);

    /**
     * 查询库存
     * @param kwCode
     * @return
     */
    List<WvStock> getStockByKwAndGoodsId(String kwCode);
}
