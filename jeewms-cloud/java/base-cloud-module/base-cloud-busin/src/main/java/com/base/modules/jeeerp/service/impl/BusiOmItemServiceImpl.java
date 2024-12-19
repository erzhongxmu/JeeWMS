package com.base.modules.jeeerp.service.impl;

import com.base.modules.jeeerp.entity.BusiOmItem;
import com.base.modules.jeeerp.mapper.BusiOmItemMapper;
import com.base.modules.jeeerp.service.IBusiOmItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: busi_om_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class BusiOmItemServiceImpl extends ServiceImpl<BusiOmItemMapper, BusiOmItem> implements IBusiOmItemService {

	@Autowired
	private BusiOmItemMapper busiOmItemMapper;

	@Override
	public List<BusiOmItem> selectByMainId(String mainId) {
		return busiOmItemMapper.selectByMainId(mainId);
	}
}
