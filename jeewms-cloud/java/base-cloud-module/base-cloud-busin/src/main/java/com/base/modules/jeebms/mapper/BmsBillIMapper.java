package com.base.modules.jeebms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.base.modules.jeebms.entity.BmsBillI;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: bms_bill_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface BmsBillIMapper extends BaseMapper<BmsBillI> {
    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<BmsBillI> selectByMainId(@Param("mainId") String mainId);
}
