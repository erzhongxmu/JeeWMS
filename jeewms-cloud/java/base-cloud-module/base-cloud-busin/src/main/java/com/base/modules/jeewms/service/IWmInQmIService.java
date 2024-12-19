package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.controller.dto.BatchUpdateProduceDateDTO;
import com.base.modules.jeewms.controller.dto.batchUpdateBinDTO;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.entity.WmInQmI;
import com.base.modules.jeewms.vo.AvlKwVo;

import java.util.List;

/**
 * @Description: 进货管理——批量收货
 * @Author: base-boot
 * @Date:   2021-05-20
 * @Version: V1.0
 */
public interface IWmInQmIService extends IService<WmInQmI> {

    /**
     * 添加进货管理
     * @param wmInQmI
     */
    void add(WmInQmI wmInQmI);

   void upToShelfForce(List<String> idList);

    /**
     * 上架成功
     * @param idList
     */
    void upToShelf(List<String> idList);

   /**
    * 双维
    * @param idList
    */
   void upToShelf1(List<String> idList);

    /**
     * 批量更新储位
     * @param param
     */
    void batchUpdateBin(batchUpdateBinDTO param);

    /**
     * 批量更新生产日期
     * @param param
     */
    void batchUpdateProduceDate(BatchUpdateProduceDateDTO param);

    /**
     * 退货上架列表查询
     * @param wmInQmI
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<WmInQmI> querySjPageList(WmInQmI wmInQmI, Integer pageNo, Integer pageSize);

    /**
     * 批量验收
     * @param wmImNoticeIList
     */
    void batchAdd(List<WmImNoticeI> wmImNoticeIList);

    /**
     * 上架
     * @param wmInQmI
     */
    void upToShelfOne(WmInQmI wmInQmI);

    void upToShelfOneforce(WmInQmI wmInQmI);

    IPage<AvlKwVo> getAvlBin(String id, Integer pageNo, Integer pageSize);

    /**
     * 获取推荐库位
     * @param goodsId
     * @return
     */
    String getRecommendBin(String goodsId);
}
