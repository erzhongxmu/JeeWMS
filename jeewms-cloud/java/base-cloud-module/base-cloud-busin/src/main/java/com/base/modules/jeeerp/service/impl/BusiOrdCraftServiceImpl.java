package com.base.modules.jeeerp.service.impl;

import com.base.modules.jeeerp.entity.BusiOrdCraft;
import com.base.modules.jeeerp.mapper.BusiOrdCraftMapper;
import com.base.modules.jeeerp.service.IBusiOrdCraftService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: busi_ord_craft
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class BusiOrdCraftServiceImpl extends ServiceImpl<BusiOrdCraftMapper, BusiOrdCraft> implements IBusiOrdCraftService {

	@Autowired
	private BusiOrdCraftMapper busiOrdCraftMapper;

	@Override
	public List<BusiOrdCraft> selectByMainId(String mainId) {
		return busiOrdCraftMapper.selectByMainId(mainId);
	}
}
