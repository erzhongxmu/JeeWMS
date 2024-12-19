package com.base.modules.bi.service.impl;

import com.base.modules.bi.entity.zdOnlCgreportParam;
import com.base.modules.bi.mapper.zdOnlCgreportParamMapper;
import com.base.modules.bi.service.IzdOnlCgreportParamService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: onl_cgreport_param
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Service
public class ZdOnlCgreportParamServiceImpl extends ServiceImpl<com.base.modules.bi.mapper.zdOnlCgreportParamMapper, zdOnlCgreportParam> implements IzdOnlCgreportParamService {
	
	@Autowired
	private zdOnlCgreportParamMapper zdOnlCgreportParamMapper;
	
	@Override
	public List<zdOnlCgreportParam> selectByMainId(String mainId) {
		return zdOnlCgreportParamMapper.selectByMainId(mainId);
	}
}
