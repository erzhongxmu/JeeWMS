package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: busi_prd_ord_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IBusiPrdOrdItemService extends IService<BusiPrdOrdItem> {

	public List<BusiPrdOrdItem> selectByMainId(String mainId);
}
