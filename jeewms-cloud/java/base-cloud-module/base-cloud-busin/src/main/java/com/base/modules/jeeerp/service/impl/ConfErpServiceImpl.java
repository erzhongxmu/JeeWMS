package com.base.modules.jeeerp.service.impl;

import com.base.modules.jeeerp.entity.ConfErp;
import com.base.modules.jeeerp.entity.ConfErpItem;
import com.base.modules.jeeerp.mapper.ConfErpItemMapper;
import com.base.modules.jeeerp.mapper.ConfErpMapper;
import com.base.modules.jeeerp.service.IConfErpService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: conf_erp
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class ConfErpServiceImpl extends ServiceImpl<ConfErpMapper, ConfErp> implements IConfErpService {

	@Autowired
	private ConfErpMapper confErpMapper;
	@Autowired
	private ConfErpItemMapper confErpItemMapper;

	@Override
	@Transactional
	public void saveMain(ConfErp confErp, List<ConfErpItem> confErpItemList) {
		confErpMapper.insert(confErp);
		if(confErpItemList!=null && confErpItemList.size()>0) {
			for(ConfErpItem entity:confErpItemList) {
				//外键设置
				entity.setQuery04(confErp.getQuery04());
				confErpItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ConfErp confErp,List<ConfErpItem> confErpItemList) {
		confErpMapper.updateById(confErp);

		//1.先删除子表数据
		confErpItemMapper.deleteByMainId(confErp.getId());

		//2.子表数据重新插入
		if(confErpItemList!=null && confErpItemList.size()>0) {
			for(ConfErpItem entity:confErpItemList) {
				//外键设置
				entity.setQuery04(confErp.getQuery04());
				confErpItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		confErpItemMapper.deleteByMainId(id);
		confErpMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			confErpItemMapper.deleteByMainId(id.toString());
			confErpMapper.deleteById(id);
		}
	}

}
