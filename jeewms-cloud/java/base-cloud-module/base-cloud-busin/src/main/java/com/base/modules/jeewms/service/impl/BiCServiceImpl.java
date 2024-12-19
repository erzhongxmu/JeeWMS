package com.base.modules.jeewms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.jeewms.entity.WmImNoticeI;
import com.base.modules.jeewms.mapper.BiCMapper;
import com.base.modules.jeewms.service.IBiCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BiCServiceImpl extends ServiceImpl<BiCMapper, WmImNoticeI> implements IBiCService  {
    @Autowired
    private BiCMapper biCMapper;

    @Override
    public Map<String, Object> getNum1() {
        return biCMapper.getNum1();
    }

    @Override
    public Map<String, Object> getNum2() {
        return biCMapper.getNum2();
    }

    @Override
    public Map<String, Object> getNum3() {
        return biCMapper.getNum3();
    }

    @Override
    public Map<String, Object> getNum4() {
        return biCMapper.getNum4();
    }

    @Override
    public Map<String, Object> getNum5() {
        return biCMapper.getNum5();
    }

    @Override
    public List<Map<String, Object>> getDayCountmonth() {
        return biCMapper.getDayCountmonth();
    }

    @Override
    public List<Map<String, Object>> getStudentCountmonth() {
        return biCMapper.getStudentCountmonth();
    }

    @Override
    public List<Map<String, Object>> getStudentCount() {
        return biCMapper.getStudentCount();
    }
}
