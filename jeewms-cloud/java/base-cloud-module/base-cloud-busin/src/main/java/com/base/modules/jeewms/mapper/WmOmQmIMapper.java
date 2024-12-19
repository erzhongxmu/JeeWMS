package com.base.modules.jeewms.mapper;

import java.util.List;
import java.util.Map;

import com.base.common.base.dto.WmsOrderItemCallbackDTO;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.WmOmQmI;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 下架任务
 * @Author: base-boot
 * @Date:   2021-05-20
 * @Version: V1.0
 */
public interface WmOmQmIMapper extends BaseMapper<WmOmQmI> {

    int checkOmQyt(String orderNo);

    List<WmsOrderItemCallbackDTO> getBatchByOrderId(@Param("orderId") String orderId);

    List<String> getOrderNoByWave(@Param("waveId") String waveId);

    List<String> getGoodsDetailByDelivery(@Param("orderSn") String orderSn);


    List<String> queryOmNoticeIdList();
}
