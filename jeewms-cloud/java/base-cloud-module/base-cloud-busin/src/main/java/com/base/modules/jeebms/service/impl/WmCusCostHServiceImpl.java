package com.base.modules.jeebms.service.impl;

import com.base.modules.jeebms.entity.WmCusCostH;
import com.base.modules.jeebms.entity.WmCusCostI;
import com.base.modules.jeebms.mapper.WmCusCostIMapper;
import com.base.modules.jeebms.mapper.WmCusCostHMapper;
import com.base.modules.jeebms.service.IWmCusCostHService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 仓储合同
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Service
public class WmCusCostHServiceImpl extends ServiceImpl<WmCusCostHMapper, WmCusCostH> implements IWmCusCostHService {

	@Autowired
	private WmCusCostHMapper wmCusCostHMapper;
	@Autowired
	private WmCusCostIMapper wmCusCostIMapper;

	@Override
	@Transactional
	public void delMain(String id) {
		wmCusCostIMapper.deleteByMainId(id);
		wmCusCostHMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wmCusCostIMapper.deleteByMainId(id.toString());
			wmCusCostHMapper.deleteById(id);
		}
	}

}
