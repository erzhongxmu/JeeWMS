package com.base.modules.bi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.modules.bi.entity.zdOnlCgreportParam;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: onl_cgreport_param
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface zdOnlCgreportParamMapper extends BaseMapper<zdOnlCgreportParam> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<zdOnlCgreportParam> selectByMainId(@Param("mainId") String mainId);
}
