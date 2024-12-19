package com.base.modules.jeeerp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.modules.jeeerp.entity.BusiPrdOrd;
import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: busi_prd_ord_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface BusiPrdOrdItemMapper extends BaseMapper<BusiPrdOrdItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<BusiPrdOrdItem> selectByMainId(@Param("mainId") String mainId);

}
