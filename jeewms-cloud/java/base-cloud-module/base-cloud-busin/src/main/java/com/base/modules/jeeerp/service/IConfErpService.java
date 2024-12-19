package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.ConfErpItem;
import com.base.modules.jeeerp.entity.ConfErp;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: conf_erp
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IConfErpService extends IService<ConfErp> {

	/**
	 * 添加一对多
	 *
	 */
	public void saveMain(ConfErp confErp,List<ConfErpItem> confErpItemList) ;

	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(ConfErp confErp,List<ConfErpItem> confErpItemList);

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

}
