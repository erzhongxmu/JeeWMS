package com.base.modules.jeeerp.mapper;

import java.util.List;
import com.base.modules.jeeerp.entity.ConfErpItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: conf_erp_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface ConfErpItemMapper extends BaseMapper<ConfErpItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<ConfErpItem> selectByMainId(@Param("mainId") String mainId);
}
