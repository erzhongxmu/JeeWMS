package com.base.modules.jeebms.service;

import com.base.modules.jeebms.entity.WmCusCostI;
import com.base.modules.jeebms.entity.WmCusCostH;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 仓储合同
 * @Author: base-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
public interface IWmCusCostHService extends IService<WmCusCostH> {

	/**
	 * 删除一对多
	 */
	public void delMain (String id);

	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


}
