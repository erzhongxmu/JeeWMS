package com.base.modules.jeeerp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.entity.BusiPoItem;
import com.base.modules.jeeerp.mapper.BusiPoItemMapper;
import com.base.modules.jeeerp.service.IBusiPoItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: busi_po_item
 * @Author: base-boot
 * @Date:   2022-11-23
 * @Version: V1.0
 */
@Service
public class BusiPoItemServiceImpl extends ServiceImpl<BusiPoItemMapper, BusiPoItem> implements IBusiPoItemService {

	@Autowired
	private BusiPoItemMapper busiPoItemMapper;

	@Override
	public List<BusiPoItem> selectByMainId(String mainId) {
		return busiPoItemMapper.selectByMainId(mainId);
	}

}
