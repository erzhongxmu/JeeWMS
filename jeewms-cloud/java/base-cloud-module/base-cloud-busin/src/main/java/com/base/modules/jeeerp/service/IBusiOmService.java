package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.BusiOmItem;
import com.base.modules.jeeerp.entity.BusiOm;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: busi_om
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IBusiOmService extends IService<BusiOm> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(BusiOm busiOm,List<BusiOmItem> busiOmItemList,int index) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(BusiOm busiOm,List<BusiOmItem> busiOmItemList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
