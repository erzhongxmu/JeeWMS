package com.base.modules.bi.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: onl_cgreport_item
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface zdOnlCgreportItemMapper extends BaseMapper<zdOnlCgreportItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<zdOnlCgreportItem> selectByMainId(@Param("mainId") String mainId);
}
