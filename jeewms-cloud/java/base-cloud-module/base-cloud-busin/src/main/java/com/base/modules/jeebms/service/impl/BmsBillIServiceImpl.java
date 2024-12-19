package com.base.modules.jeebms.service.impl;

import com.base.modules.jeebms.entity.BmsBillI;
import com.base.modules.jeebms.mapper.BmsBillIMapper;
import com.base.modules.jeebms.service.IBmsBillIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: bms_bill_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Service
public class BmsBillIServiceImpl extends ServiceImpl<BmsBillIMapper, BmsBillI> implements IBmsBillIService {
    @Autowired
    private BmsBillIMapper bmsBillIMapper;
    @Override
    public List<BmsBillI> selectByMainId(String mainId) {
        return bmsBillIMapper.selectByMainId(mainId);
    }

}
