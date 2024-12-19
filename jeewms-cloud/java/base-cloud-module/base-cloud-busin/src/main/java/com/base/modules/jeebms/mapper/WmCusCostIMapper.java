package com.base.modules.jeebms.mapper;

import java.util.List;
import com.base.modules.jeebms.entity.WmCusCostI;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 合同项目
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
public interface WmCusCostIMapper extends BaseMapper<WmCusCostI> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<WmCusCostI> selectByMainId(@Param("mainId") String mainId);

}
