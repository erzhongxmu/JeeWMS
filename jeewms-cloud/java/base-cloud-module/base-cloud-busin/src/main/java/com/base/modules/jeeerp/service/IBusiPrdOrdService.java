package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.base.modules.jeeerp.entity.BusiOrdCraft;
import com.base.modules.jeeerp.entity.BusiPrdOrd;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.jeeerp.vo.BusiPrdOrdPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: busi_prd_ord
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IBusiPrdOrdService extends IService<BusiPrdOrd> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(BusiPrdOrd busiPrdOrd,List<BusiPrdOrdItem> busiPrdOrdItemList,List<BusiOrdCraft> busiOrdCraftList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(BusiPrdOrd busiPrdOrd,List<BusiPrdOrdItem> busiPrdOrdItemList,List<BusiOrdCraft> busiOrdCraftList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


	/**
	 * 包装订单、质检订单新增
	 */
	public void  BusiPrdOrdPageAdd(BusiPrdOrdPage busiPrdOrdPage);

}
