package com.base.modules.jeewms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeewms.controller.dto.HdDTO;
import com.base.modules.jeewms.controller.dto.SendCarDTO;
import com.base.modules.jeewms.entity.TmsYwDingdan;

import java.util.List;

/**
 * @Description: tms_yw_dingdan
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
public interface ITmsYwDingdanService extends IService<TmsYwDingdan> {

    /**
     * 批量派车
     * @param param
     */
    void batchSendCar(SendCarDTO param);

    /**
     * 批量回单
     * @param param
     */
    void batchHd(HdDTO param);

    /**
     * 批量取消回单
     * @param id
     */
    void batchCancelHd(List<String> id);
}
