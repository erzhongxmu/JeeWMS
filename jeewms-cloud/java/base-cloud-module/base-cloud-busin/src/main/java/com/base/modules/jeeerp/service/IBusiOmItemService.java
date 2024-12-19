package com.base.modules.jeeerp.service;

import com.base.modules.jeeerp.entity.BusiOmItem;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: busi_om_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
public interface IBusiOmItemService extends IService<BusiOmItem> {

	public List<BusiOmItem> selectByMainId(String mainId);
}
