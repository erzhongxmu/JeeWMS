package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.api.vo.Result;
import com.base.common.base.dto.WMSResult;
import com.base.modules.jeewms.entity.WmOmQmI;
import com.base.modules.jeewms.entity.WvStockStt;
import com.base.modules.jeewms.entity.WvStockSttQuery;
import com.base.modules.jeewms.vo.EditBatchWmQmIVo;
import com.base.modules.jeewms.vo.InsertAssignToBatchVo;
import com.base.modules.jeewms.vo.WavebatchVo;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.system.entity.SysUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 下架任务
 * @Author: base-boot
 * @Date: 2021-05-20
 * @Version: V1.0
 */
public interface IWmOmQmIService extends IService<WmOmQmI> {

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

    /**
     * @param ids
     * @return
     * @Describe 下架功能
     * @Author zly
     * @Create Date 2021/5/20
     */
    public Result<?> dotowavedown(List<String> ids,String query09, String binSta, List<WvStockSttQuery> wvStockStt);

    /**
     * 下架后通知双维
     * @param ids
     * @param binSta
     * @return
     */
    public Result<?> dotowavedown1(List<String> ids,String query09,Integer tenantId, String binSta);



    /**
     * @param list
     * @return
     * @Describe 批量更改
     * @Author zly
     * @Create Date 2021/5/20
     */
    public Result<?> editBatch(List<EditBatchWmQmIVo> list);

    /**
     * @param ids
     * @return
     * @Describe 批量确认任务
     * @Author zly
     * @Create Date 2021/5/24
     */
    public Result<?> doassignbatch(List<String> ids);

    /**
     * @param vo
     * @return
     * @Describe 波次生成和波次生成到指定容器
     * @Author zly
     * @Create Date 2021/5/24
     */
    public Result<?> wavebatch(WavebatchVo vo);

    /**
     * @param vo
     * @return
     * @Describe 批量设置接收人
     * @Author zly
     * @Create Date 2021/5/24
     */
    public Result<?> insertAssignToBatch(InsertAssignToBatchVo vo);

    Result<?> pdaDotowavedown(List<WmOmQmI> wmOmQmIList, String wm_h);
    Result<?> pdaDotowavedown1(List<WmOmQmI> wmOmQmIList, String wm_h);

    /**
     * 下拉接收人
     * @param idList
     * @return
     */
    List<SysUser> getJobPersonList(List<String> idList);

    boolean checkOmQyt(String orderNo);


    List<String> getOrderNoByWave(String waveId);

    List<String> getGoodsDetailByDelivery(String deliveryId);

    public List<String> queryOmNoticeIdList();
}
