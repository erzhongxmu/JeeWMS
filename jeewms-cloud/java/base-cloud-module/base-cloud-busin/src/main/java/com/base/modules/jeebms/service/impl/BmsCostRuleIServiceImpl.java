package com.base.modules.jeebms.service.impl;

import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.base.modules.jeebms.mapper.BmsCostRuleIMapper;
import com.base.modules.jeebms.service.IBmsCostRuleIService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: bms_cost_rule_i
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Service
public class BmsCostRuleIServiceImpl extends ServiceImpl<BmsCostRuleIMapper, BmsCostRuleI> implements IBmsCostRuleIService {

	@Autowired
	private BmsCostRuleIMapper bmsCostRuleIMapper;

	@Override
	public List<BmsCostRuleI> selectByMainId(String mainId) {
		return bmsCostRuleIMapper.selectByMainId(mainId);
	}
}
