package com.base.modules.jeeerp.mapper;

import java.util.List;
import com.base.modules.jeeerp.entity.BusiOmItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: busi_om_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface BusiOmItemMapper extends BaseMapper<BusiOmItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<BusiOmItem> selectByMainId(@Param("mainId") String mainId);
}
