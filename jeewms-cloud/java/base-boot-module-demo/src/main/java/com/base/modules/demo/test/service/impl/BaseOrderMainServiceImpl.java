package com.base.modules.demo.test.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.base.modules.demo.test.entity.BaseOrderCustomer;
import com.base.modules.demo.test.entity.BaseOrderMain;
import com.base.modules.demo.test.entity.BaseOrderTicket;
import com.base.modules.demo.test.mapper.BaseOrderCustomerMapper;
import com.base.modules.demo.test.mapper.BaseOrderMainMapper;
import com.base.modules.demo.test.mapper.BaseOrderTicketMapper;
import com.base.modules.demo.test.service.IBaseOrderMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单
 * @Author: base-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
@Service
public class BaseOrderMainServiceImpl extends ServiceImpl<BaseOrderMainMapper, BaseOrderMain> implements IBaseOrderMainService {

    @Autowired
    private BaseOrderMainMapper baseOrderMainMapper;
    @Autowired
    private BaseOrderCustomerMapper baseOrderCustomerMapper;
    @Autowired
    private BaseOrderTicketMapper baseOrderTicketMapper;

    @Override
    @Transactional
    public void saveMain(BaseOrderMain baseOrderMain, List<BaseOrderCustomer> baseOrderCustomerList, List<BaseOrderTicket> baseOrderTicketList) {
        baseOrderMainMapper.insert(baseOrderMain);
        if (baseOrderCustomerList != null) {
            for (BaseOrderCustomer entity : baseOrderCustomerList) {
                entity.setOrderId(baseOrderMain.getId());
                baseOrderCustomerMapper.insert(entity);
            }
        }
        if (baseOrderTicketList != null) {
            for (BaseOrderTicket entity : baseOrderTicketList) {
                entity.setOrderId(baseOrderMain.getId());
                baseOrderTicketMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(BaseOrderMain baseOrderMain, List<BaseOrderCustomer> baseOrderCustomerList, List<BaseOrderTicket> baseOrderTicketList) {
        baseOrderMainMapper.updateById(baseOrderMain);

        //1.先删除子表数据
        baseOrderTicketMapper.deleteTicketsByMainId(baseOrderMain.getId());
        baseOrderCustomerMapper.deleteCustomersByMainId(baseOrderMain.getId());

        //2.子表数据重新插入
        if (baseOrderCustomerList != null) {
            for (BaseOrderCustomer entity : baseOrderCustomerList) {
                entity.setOrderId(baseOrderMain.getId());
                baseOrderCustomerMapper.insert(entity);
            }
        }
        if (baseOrderTicketList != null) {
            for (BaseOrderTicket entity : baseOrderTicketList) {
                entity.setOrderId(baseOrderMain.getId());
                baseOrderTicketMapper.insert(entity);
            }
        }
    }

    /**
     * 一对多维护逻辑改造  LOWCOD-315
     * @param baseOrderMain
     * @param baseOrderCustomerList
     * @param baseOrderTicketList
     */
    @Override
    @Transactional
    public void updateCopyMain(BaseOrderMain baseOrderMain, List<BaseOrderCustomer> baseOrderCustomerList, List<BaseOrderTicket> baseOrderTicketList) {
        baseOrderMainMapper.updateById(baseOrderMain);

        // 循环前台传过来的数据
        for (BaseOrderTicket ticket: baseOrderTicketList){
            // 先查询子表数据库
            BaseOrderTicket orderTicket = baseOrderTicketMapper.selectById(ticket.getId());
            if(orderTicket == null){
                // 当传过来的id数据库不存在时，说明数据库没有，走新增逻辑
                ticket.setOrderId(baseOrderMain.getId());
                baseOrderTicketMapper.insert(ticket);
                break;
            }
            if(orderTicket.getId().equals(ticket.getId())){
                // 传过来的id和数据库id一至时，说明数据库存在该数据，走更新逻辑
                baseOrderTicketMapper.updateById(ticket);
            }
        }
        for (BaseOrderCustomer customer: baseOrderCustomerList){
            // 先查询子表数据库
            BaseOrderCustomer customers = baseOrderCustomerMapper.selectById(customer.getId());
            if(customers == null){
                // 当传过来的id数据库不存在时，说明数据库没有，走新增逻辑
                customer.setOrderId(baseOrderMain.getId());
                baseOrderCustomerMapper.insert(customer);
                break;
            }
            if(customers.getId().equals(customer.getId())){
                //TODO 传过来的id和数据库id一至时，说明数据库存在该数据，走更新逻辑
                baseOrderCustomerMapper.updateById(customer);
            }
        }
        // 当跟新和删除之后取差集， 当传过来的id不存在，而数据库存在时，说明已删除，走删除逻辑
        List<BaseOrderTicket> baseOrderTickets = baseOrderTicketMapper.selectTicketsByMainId(baseOrderMain.getId());
        List<BaseOrderTicket> collect = baseOrderTickets.stream()
                .filter(item -> !baseOrderTicketList.stream()
                .map(e -> e.getId())
                .collect(Collectors.toList())
                .contains(item.getId()))
                .collect(Collectors.toList());
        // for循环删除id
        for (BaseOrderTicket ticket:collect){
            baseOrderTicketMapper.deleteById(ticket.getId());
        }

        List<BaseOrderCustomer> baseOrderCustomers = baseOrderCustomerMapper.selectCustomersByMainId(baseOrderMain.getId());
        List<BaseOrderCustomer> customersCollect = baseOrderCustomers.stream()
                .filter(item -> !baseOrderCustomerList.stream()
                        .map(e -> e.getId())
                        .collect(Collectors.toList())
                        .contains(item.getId()))
                .collect(Collectors.toList());
        //TODO for循环删除id
        for (BaseOrderCustomer c:customersCollect){
            baseOrderCustomerMapper.deleteById(c.getId());
        }
    }
	@Override
	@Transactional
	public void delMain(String id) {
		baseOrderMainMapper.deleteById(id);
		baseOrderTicketMapper.deleteTicketsByMainId(id);
		baseOrderCustomerMapper.deleteCustomersByMainId(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			baseOrderMainMapper.deleteById(id);
			baseOrderTicketMapper.deleteTicketsByMainId(id.toString());
			baseOrderCustomerMapper.deleteCustomersByMainId(id.toString());
		}
	}

}
