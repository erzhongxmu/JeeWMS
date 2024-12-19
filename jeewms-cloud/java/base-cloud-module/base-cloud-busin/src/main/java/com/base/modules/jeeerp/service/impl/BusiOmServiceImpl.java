package com.base.modules.jeeerp.service.impl;

import com.base.modules.jeeerp.entity.BusiOm;
import com.base.modules.jeeerp.entity.BusiOmItem;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.jeeerp.mapper.BusiOmItemMapper;
import com.base.modules.jeeerp.mapper.BusiOmMapper;
import com.base.modules.jeeerp.service.IBusiOmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;
import java.util.Random;

/**
 * @Description: busi_om
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class BusiOmServiceImpl extends ServiceImpl<BusiOmMapper, BusiOm> implements IBusiOmService {

	@Autowired
	private BusiOmMapper busiOmMapper;
	@Autowired
	private BusiOmItemMapper busiOmItemMapper;

	@Override
	@Transactional
	public void saveMain(BusiOm busiOm, List<BusiOmItem> busiOmItemList,int index) {
		busiOmMapper.insert(busiOm);
		if(busiOmItemList!=null && busiOmItemList.size()>0) {
			for(BusiOmItem entity:busiOmItemList) {
				//外键设置
				entity.setLink02(busiOm.getQuery23());
				busiOmItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(BusiOm busiOm,List<BusiOmItem> busiOmItemList) {
		busiOmMapper.updateById(busiOm);

		//1.先删除子表数据
		busiOmItemMapper.deleteByMainId(busiOm.getQuery23());

		//2.子表数据重新插入
		if(busiOmItemList!=null && busiOmItemList.size()>0) {
			for(BusiOmItem entity:busiOmItemList) {
				//外键设置
				entity.setLink02(busiOm.getQuery23());
				busiOmItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		BusiOm busiom = busiOmMapper.selectById(id);
		busiOmItemMapper.deleteByMainId(busiom.getQuery23());
		busiOmMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			BusiOm busiom = busiOmMapper.selectById(id);
			busiOmItemMapper.deleteByMainId(busiom.getQuery23());
			busiOmMapper.deleteById(id);
		}
	}

}
