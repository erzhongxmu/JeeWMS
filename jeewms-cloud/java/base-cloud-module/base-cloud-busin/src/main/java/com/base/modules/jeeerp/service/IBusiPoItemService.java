package com.base.modules.jeeerp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: busi_po_item
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
public interface IBusiPoItemService extends IService<BusiPoItem> {

	public List<BusiPoItem> selectByMainId(String mainId);
}
