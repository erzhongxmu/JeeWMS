package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.BusiOrdCraft;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: busi_ord_craft
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IBusiOrdCraftService extends IService<BusiOrdCraft> {

	public List<BusiOrdCraft> selectByMainId(String mainId);
}
