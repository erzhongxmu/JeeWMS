package com.base.modules.demo.test.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.base.modules.demo.test.entity.BaseOrderCustomer;
import com.base.modules.demo.test.entity.BaseOrderMain;
import com.base.modules.demo.test.entity.BaseOrderTicket;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单
 * @Author: base-boot
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface IBaseOrderMainService extends IService<BaseOrderMain> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(BaseOrderMain baseOrderMain, List<BaseOrderCustomer> baseOrderCustomerList, List<BaseOrderTicket> baseOrderTicketList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(BaseOrderMain baseOrderMain, List<BaseOrderCustomer> baseOrderCustomerList, List<BaseOrderTicket> baseOrderTicketList);
	
	/**
	 * 删除一对多
	 * @param jformOrderMain
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 * @param jformOrderMain
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

	public void updateCopyMain(BaseOrderMain baseOrderMain, List<BaseOrderCustomer> baseOrderCustomerList, List<BaseOrderTicket> baseOrderTicketList);
}
