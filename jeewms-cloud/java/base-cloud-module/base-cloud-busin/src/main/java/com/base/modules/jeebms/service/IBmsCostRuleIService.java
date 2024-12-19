package com.base.modules.jeebms.service;

import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: bms_cost_rule_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface IBmsCostRuleIService extends IService<BmsCostRuleI> {

	public List<BmsCostRuleI> selectByMainId(String mainId);
}
