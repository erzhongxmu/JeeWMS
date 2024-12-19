package com.base.modules.jeeerp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.modules.jeeerp.entity.BusiPaymentReceived;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.jeeerp.entity.BusiPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeeerp.entity.BusiPrdOrd;
import com.base.modules.jeeerp.vo.BusiPoPage;
import com.base.modules.jeeerp.vo.BusiPrdOrdPage;
import com.base.modules.jeewms.entity.WmImNoticeI;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: busi_po
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
public interface IBusiPoService extends IService<BusiPo> {

	/**
	 * 添加一对多
	 */
	public void saveMain(BusiPo busiPo, List<BusiPoItem> busiPoItemList);

	/**
	 * 修改一对多
	 */
	public void updateMain(BusiPo busiPo, List<BusiPoItem> busiPoItemList);

	/**
	 * 删除一对多
	 */
	public void delMain(String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);


	/**
	 * 查询采购含税表单数据
	 */
	public List<BusiPoPage> CGHS(List<BusiPo> busiPoList);

	/**
	 * 付款单
	 */
	public List<BusiPoPage> FKD(List<BusiPo> busiPoList);

	/**
	 * 形式发票
	 */
	public List<BusiPoPage> XSFP(List<BusiPaymentReceived> busiPaymentReceivedList);

	/**
	 * 形式发票汇总
	 */
	public List<BusiPoPage> XSFPHZ(List<BusiPaymentReceived> busiPaymentReceivedList);

	/**
	 * 采购质检单
	 */
	public List<WmImNoticeI> CGZJ(String orderids);

	/**
	 * 质检订单
	 */
	public List<WmImNoticeI> ZJDD(String orderids);
}
