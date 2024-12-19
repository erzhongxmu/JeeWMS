package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.modules.jeewms.entity.WmInQmI;

import java.util.List;
import java.util.Map;

/**
 * @Description: 进货管理——批量收货
 * @Author: base-boot
 * @Date:   2021-05-20
 * @Version: V1.0
 */
public interface WmInQmIMapper extends BaseMapper<WmInQmI> {

    /**
     * 查询行项目
     * @param noticeId
     * @return
     */
    List<Map<String, Object>> getImQmiMap(String noticeId);
}
