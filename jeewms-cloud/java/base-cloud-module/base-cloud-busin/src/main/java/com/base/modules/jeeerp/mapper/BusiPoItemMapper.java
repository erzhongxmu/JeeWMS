package com.base.modules.jeeerp.mapper;

import java.util.List;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: busi_po_item
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
public interface BusiPoItemMapper extends BaseMapper<BusiPoItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public boolean deleteByMainIdItem(@Param("mainId") String mainId);

	public List<BusiPoItem> selectByMainId(@Param("mainId") String mainId);
}
