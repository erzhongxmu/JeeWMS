package com.base.modules.jeewms.mapper;

import org.apache.ibatis.annotations.Param;
import com.base.modules.jeewms.entity.BaCity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 地区信息
 * @Author: base-boot
 * @Date:   2021-05-19
 * @Version: V1.0
 */
public interface BaCityMapper extends BaseMapper<BaCity> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

}
