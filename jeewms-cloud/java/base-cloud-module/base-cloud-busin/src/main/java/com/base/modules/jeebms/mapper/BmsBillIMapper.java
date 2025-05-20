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
    /**
     * 批量删除
     * @param mainId
     * @return
     */
    public boolean deleteByMainId(@Param("mainId") String mainId);

    /**
     * 查询
     * @param mainId
     * @return
     */
    public List<BmsBillI> selectByMainId(@Param("mainId") String mainId);
}
