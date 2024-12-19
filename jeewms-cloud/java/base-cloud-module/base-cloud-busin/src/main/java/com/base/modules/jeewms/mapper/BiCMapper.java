package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.modules.jeewms.entity.WmImNoticeI;

import java.util.List;
import java.util.Map;

public interface BiCMapper extends BaseMapper<WmImNoticeI> {

    Map<String,Object> getNum1();

    Map<String,Object> getNum2();

    Map<String,Object> getNum3();

    Map<String,Object> getNum4();

    Map<String,Object> getNum5();

    List<Map<String,Object>> getDayCountmonth();

    List<Map<String,Object>> getStudentCountmonth();

    List<Map<String,Object>> getStudentCount();
}
