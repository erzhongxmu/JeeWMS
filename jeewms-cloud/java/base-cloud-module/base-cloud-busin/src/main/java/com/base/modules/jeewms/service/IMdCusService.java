package com.base.modules.jeewms.service;

import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdCus;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 客户
 * @Author: base-boot
 * @Date:   2021-05-17
 * @Version: V1.0
 */
public interface IMdCusService extends IService<MdCus> {

    public Result<?> findByKeHuBianMa(String keHuBianMa);

}
