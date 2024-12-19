package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmToDownGoods;
import com.base.modules.jeewms.vo.BatchLoadingReviewVo;
import com.base.modules.jeewms.vo.EditBatchWmToDownGoodsVo;
import com.base.modules.jeewms.vo.OrderPickingVo;

import java.util.List;

/**
 * @Description: 下架明细
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
public interface IWmToDownGoodsService extends IService<WmToDownGoods> {

    public List<WmToDownGoods> selectByMainId(String mainId);

    /**
     * @param list
     * @return
     * @Describe 批量更改
     * @Author zly
     * @Create Date 2021/5/21
     */
    public Result<?> editBatch(List<EditBatchWmToDownGoodsVo> list);

    /**
     * @param ids
     * @return
     * @Describe 下架明细-装车复核-批量复核
     * @Author zly
     * @Create Date 2021/5/21
     */
    public Result<?> dofubatch(List<String> ids,Integer tenantId);
    /**
     * @param vo
     * @return
     * @Describe 下架明细-装车复核-批量复核
     * @Author zly
     * @Create Date 2021/5/21
     */
    public Result<?> batchLoadingReview(List<BatchLoadingReviewVo> vo);

    /**
     * @return
     * @Describe 按单拣货保存
     * @Author zly
     * @Create Date 2021/6/11
     */
    public Result<?> orderPickingEdit(OrderPickingVo orderPickingVo);


    public List<String> getWmOmIList(String userName);

    public Result<?> queryWmOmNoticeIByMainNo(String id);

    public Result<?> scanCodeReview(String id, String barCode);

    public Result<?> finishReview(String id, String packingNo, String childWaybillCount);

    public Result<?> afreshReview(String id);


}
