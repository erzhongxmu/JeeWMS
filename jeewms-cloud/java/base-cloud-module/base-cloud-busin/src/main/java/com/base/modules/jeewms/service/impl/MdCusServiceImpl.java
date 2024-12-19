package com.base.modules.jeewms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.common.api.vo.Result;
import com.base.modules.jeewms.entity.MdCus;
import com.base.modules.jeewms.mapper.MdCusMapper;
import com.base.modules.jeewms.service.IMdCusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 客户
 * @Author: base-boot
 * @Date: 2021-05-17
 * @Version: V1.0
 */
@Service
public class MdCusServiceImpl extends ServiceImpl<MdCusMapper, MdCus> implements IMdCusService {

    @Autowired
    private MdCusMapper mdCusMapper;

    @Override
    public Result<?> findByKeHuBianMa(String keHuBianMa) {
        QueryWrapper<MdCus> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MdCus::getKeHuBianMa,keHuBianMa);
        MdCus mdCus = mdCusMapper.selectOne(queryWrapper);
        if(ObjectUtil.isNotEmpty(mdCus)){
            return Result.ok(mdCus);
        }
        return Result.ok("查询失败");
    }
}
