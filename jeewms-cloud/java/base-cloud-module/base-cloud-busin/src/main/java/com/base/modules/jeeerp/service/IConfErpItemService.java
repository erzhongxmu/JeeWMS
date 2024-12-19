package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.ConfErpItem;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: conf_erp_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IConfErpItemService extends IService<ConfErpItem> {

	public List<ConfErpItem> selectByMainId(String mainId);
}
