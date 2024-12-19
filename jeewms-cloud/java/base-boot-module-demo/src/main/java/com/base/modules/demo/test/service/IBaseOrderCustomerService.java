package com.base.modules.demo.test.service;

import java.util.List;

import com.base.modules.demo.test.entity.BaseOrderCustomer;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单客户
 * @Author: base-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface IBaseOrderCustomerService extends IService<BaseOrderCustomer> {
	
	public List<BaseOrderCustomer> selectCustomersByMainId(String mainId);
}
