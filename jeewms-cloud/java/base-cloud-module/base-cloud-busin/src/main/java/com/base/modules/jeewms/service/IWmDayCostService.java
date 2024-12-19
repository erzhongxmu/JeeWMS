package com.base.modules.jeewms.service;

import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmDayCost;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description: 费用
 * @Author: base-boot
 * @Date:   2021-05-24
 * @Version: V1.0
 */
public interface IWmDayCostService extends IService<WmDayCost> {

    /**
     * @param ids
     * @return
     * @Describe 收款取消收款状态修改
     * @Author zly
     * @Create Date 2021/5/24
     */
    public Result<?> dopljq(@RequestBody List<String> ids, String costjs);

}
