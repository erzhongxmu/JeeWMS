package com.base.modules.jeebms.service;

import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.base.modules.jeebms.entity.BmsCostRuleH;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: bms_cost_rule_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
public interface IBmsCostRuleHService extends IService<BmsCostRuleH> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(BmsCostRuleH bmsCostRuleH,List<BmsCostRuleI> bmsCostRuleIList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(BmsCostRuleH bmsCostRuleH,List<BmsCostRuleI> bmsCostRuleIList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
