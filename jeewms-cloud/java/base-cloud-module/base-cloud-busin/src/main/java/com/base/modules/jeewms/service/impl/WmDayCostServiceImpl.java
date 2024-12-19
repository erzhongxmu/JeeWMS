package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.WmDayCost;
import com.base.modules.jeewms.mapper.WmDayCostMapper;
import com.base.modules.jeewms.service.IWmDayCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 费用
 * @Author: base-boot
 * @Date: 2021-05-24
 * @Version: V1.0
 */
@Service
public class WmDayCostServiceImpl extends ServiceImpl<WmDayCostMapper, WmDayCost> implements IWmDayCostService {

    @Autowired
    private WmDayCostMapper wmDayCostMapper;

    /**
     * @param ids
     * @return
     * @Describe 收款取消收款状态修改
     * @Author zly
     * @Create Date 2021/5/24
     */
    @Override
    public Result<?> dopljq(List<String> ids, String costjs) {
        int eer = ids.size();
        for (String id : ids) {
            //获取数据
            WmDayCost en = wmDayCostMapper.selectById(id);
            en.setCostJs(costjs);
            int row = wmDayCostMapper.updateById(en);
            eer = eer - row;
        }
        if (eer == 0) {
            return Result.ok("任务确认成功");
        }
        return Result.ok("任务确认失败" + eer + "条");
    }
}
