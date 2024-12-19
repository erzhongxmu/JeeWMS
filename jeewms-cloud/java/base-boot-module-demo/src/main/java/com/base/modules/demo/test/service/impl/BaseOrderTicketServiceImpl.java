package com.base.modules.demo.test.service.impl;

import java.util.List;

import com.base.modules.demo.test.entity.BaseOrderTicket;
import com.base.modules.demo.test.mapper.BaseOrderTicketMapper;
import com.base.modules.demo.test.service.IBaseOrderTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单机票
 * @Author: base-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
@Service
public class BaseOrderTicketServiceImpl extends ServiceImpl<BaseOrderTicketMapper, BaseOrderTicket> implements IBaseOrderTicketService {
	@Autowired
	private BaseOrderTicketMapper baseOrderTicketMapper;
	
	@Override
	public List<BaseOrderTicket> selectTicketsByMainId(String mainId) {
		return baseOrderTicketMapper.selectTicketsByMainId(mainId);
	}

}
