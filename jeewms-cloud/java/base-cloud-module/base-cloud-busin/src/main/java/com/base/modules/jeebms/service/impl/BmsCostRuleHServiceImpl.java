package com.base.modules.jeebms.service.impl;

import com.base.modules.jeebms.entity.BmsCostRuleH;
import com.base.modules.jeebms.entity.BmsCostRuleI;
import com.base.modules.jeebms.mapper.BmsCostRuleIMapper;
import com.base.modules.jeebms.mapper.BmsCostRuleHMapper;
import com.base.modules.jeebms.service.IBmsCostRuleHService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: bms_cost_rule_h
 * @Author: base-boot
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Service
public class BmsCostRuleHServiceImpl extends ServiceImpl<BmsCostRuleHMapper, BmsCostRuleH> implements IBmsCostRuleHService {

	@Autowired
	private BmsCostRuleHMapper bmsCostRuleHMapper;
	@Autowired
	private BmsCostRuleIMapper bmsCostRuleIMapper;

	@Override
	@Transactional
	public void saveMain(BmsCostRuleH bmsCostRuleH, List<BmsCostRuleI> bmsCostRuleIList) {
		bmsCostRuleHMapper.insert(bmsCostRuleH);
		if(bmsCostRuleIList!=null && bmsCostRuleIList.size()>0) {
			for(BmsCostRuleI entity:bmsCostRuleIList) {
				//外键设置
				entity.setContNo(bmsCostRuleH.getContNo());
				bmsCostRuleIMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(BmsCostRuleH bmsCostRuleH,List<BmsCostRuleI> bmsCostRuleIList) {
		bmsCostRuleHMapper.updateById(bmsCostRuleH);

		//1.先删除子表数据
		bmsCostRuleIMapper.deleteByMainId(bmsCostRuleH.getId());

		//2.子表数据重新插入
		if(bmsCostRuleIList!=null && bmsCostRuleIList.size()>0) {
			for(BmsCostRuleI entity:bmsCostRuleIList) {
				//外键设置
				entity.setContNo(bmsCostRuleH.getContNo());
				bmsCostRuleIMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		bmsCostRuleIMapper.deleteByMainId(id);
		bmsCostRuleHMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			bmsCostRuleIMapper.deleteByMainId(id.toString());
			bmsCostRuleHMapper.deleteById(id);
		}
	}

}
