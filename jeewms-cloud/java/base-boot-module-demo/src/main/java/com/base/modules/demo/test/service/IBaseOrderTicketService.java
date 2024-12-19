package com.base.modules.demo.test.service;

import java.util.List;

import com.base.modules.demo.test.entity.BaseOrderTicket;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单机票
 * @Author: base-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface IBaseOrderTicketService extends IService<BaseOrderTicket> {
	
	public List<BaseOrderTicket> selectTicketsByMainId(String mainId);
}
