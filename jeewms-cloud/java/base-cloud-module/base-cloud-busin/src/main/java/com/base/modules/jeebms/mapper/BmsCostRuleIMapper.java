package com.base.modules.jeebms.mapper;

import java.util.List;
import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: bms_cost_rule_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface BmsCostRuleIMapper extends BaseMapper<BmsCostRuleI> {

	public boolean deleteByMainId(@Param("mainId") String mainId);

	public List<BmsCostRuleI> selectByMainId(@Param("mainId") String mainId);
}
