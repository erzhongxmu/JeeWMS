package com.base.modules.jeewms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.WmOmNoticeH;

import java.util.List;
import java.util.Map;

/**
 * @Description: 出货通知抬头
 * @Author: base-boot
 * @Date:   2021-05-18
 * @Version: V1.0
 */
public interface WmOmNoticeHMapper extends BaseMapper<WmOmNoticeH> {

    public List<Map<String, Object>> findById(@Param("id") String id);

    public List<Map<String, Object>> findByOmNoticeId(@Param("id") String id);

    public WmOmNoticeH selectByMainId(@Param("mainId") String mainId);

    int selectNoticeIdCount();

    WmOmNoticeH getByDeliveryId(@Param("deliveryId") String deliveryId);

    public WmOmNoticeH queryWmOmNoticeIByMainNo(@Param("id") String id);
}
