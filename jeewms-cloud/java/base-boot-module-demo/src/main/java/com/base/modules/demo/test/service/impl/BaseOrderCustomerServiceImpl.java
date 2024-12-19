package com.base.modules.demo.test.service.impl;

import java.util.List;

import com.base.modules.demo.test.entity.BaseOrderCustomer;
import com.base.modules.demo.test.mapper.BaseOrderCustomerMapper;
import com.base.modules.demo.test.service.IBaseOrderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单客户
 * @Author: base-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
@Service
public class BaseOrderCustomerServiceImpl extends ServiceImpl<BaseOrderCustomerMapper, BaseOrderCustomer> implements IBaseOrderCustomerService {

	@Autowired
	private BaseOrderCustomerMapper baseOrderCustomerMapper;
	
	@Override
	public List<BaseOrderCustomer> selectCustomersByMainId(String mainId) {
		return baseOrderCustomerMapper.selectCustomersByMainId(mainId);
	}

}
