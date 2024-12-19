package com.base.modules.jeebms.service.impl;

import com.base.modules.jeebms.entity.WmCusCostI;
import com.base.modules.jeebms.mapper.WmCusCostIMapper;
import com.base.modules.jeebms.service.IWmCusCostIService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 合同项目
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Service
public class WmCusCostIServiceImpl extends ServiceImpl<WmCusCostIMapper, WmCusCostI> implements IWmCusCostIService {

	@Autowired
	private WmCusCostIMapper wmCusCostIMapper;

	@Override
	public List<WmCusCostI> selectByMainId(String mainId) {
		return wmCusCostIMapper.selectByMainId(mainId);
	}
}
