package com.base.modules.jeebms.service;

import com.base.modules.jeebms.entity.BmsBillI;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: bms_bill_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface IBmsBillIService extends IService<BmsBillI> {
    public List<BmsBillI> selectByMainId(String mainId);
}
