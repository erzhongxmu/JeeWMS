package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.controller.dto.StockMoveDto;
import com.base.modules.jeewms.entity.WmToMoveGoods;
import com.base.modules.jeewms.entity.WvStock;
import com.base.modules.jeewms.entity.WvStockStt;
import com.base.modules.jeewms.vo.EditBatchWmToMoveGoodsVo;
import com.base.modules.jeewms.vo.addWmToMoveVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 库存转移
 * @Author: base-boot
 * @Date: 2021-05-26
 * @Version: V1.0
 */
public interface IWmToMoveGoodsService extends IService<WmToMoveGoods> {
    /**
     * @param list
     * @return
     * @Describe 批量修改转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    public Result<?> editBatch(@RequestBody List<EditBatchWmToMoveGoodsVo> list);

    /**
     * @param wvStock
     * @param pageNo
     * @param pageSize
     * @param wvStock
     * @return
     * @Describe 生成托盘转移-分页列表查询
     * @Author zly
     * @Create Date 2021/5/26
     */
    public Result<?> findPageLists(WvStockStt wvStock, Integer pageNo, Integer pageSize, HttpServletRequest req);
    public Result<?> findPageList(WvStockStt wvStock, Integer pageNo, Integer pageSize, HttpServletRequest req);

    /**
     * @param vo
     * @return
     * @Describe 添加托盘转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    public Result<?> addWmToMove(addWmToMoveVo vo);

    /**
     * @param ids
     * @return
     * @Describe 添加储位转移
     * @Author zly
     * @Create Date 2021/5/26
     */
    public Result<?> addWmToMoveGoods(List<String> ids);

    void pdaStockMove(StockMoveDto vo);
}
