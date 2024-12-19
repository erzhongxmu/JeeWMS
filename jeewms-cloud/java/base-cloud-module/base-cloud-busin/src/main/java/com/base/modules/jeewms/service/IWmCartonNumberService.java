package com.base.modules.jeewms.service;

import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmCartonNumber;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: wm_carton_number
 * @Author: base-boot
 * @Date:   2022-07-06
 * @Version: V1.0
 */
public interface IWmCartonNumberService extends IService<WmCartonNumber> {


    public Result<?> add(WmCartonNumber wmCartonNumber);

}
