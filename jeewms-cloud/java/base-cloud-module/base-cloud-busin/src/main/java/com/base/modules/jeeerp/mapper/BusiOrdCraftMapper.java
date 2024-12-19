package com.base.modules.jeeerp.mapper;

import java.util.List;
import com.base.modules.jeeerp.entity.BusiOrdCraft;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: busi_ord_craft
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface BusiOrdCraftMapper extends BaseMapper<BusiOrdCraft> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<BusiOrdCraft> selectByMainId(@Param("mainId") String mainId);
}
