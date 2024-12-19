package com.base.modules.jeeerp.service.impl;

import com.base.modules.jeeerp.entity.BusiPrdOrdItem;
import com.base.modules.jeeerp.mapper.BusiPrdOrdItemMapper;
import com.base.modules.jeeerp.service.IBusiPrdOrdItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: busi_prd_ord_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class BusiPrdOrdItemServiceImpl extends ServiceImpl<BusiPrdOrdItemMapper, BusiPrdOrdItem> implements IBusiPrdOrdItemService {

	@Autowired
	private BusiPrdOrdItemMapper busiPrdOrdItemMapper;

	@Override
	public List<BusiPrdOrdItem> selectByMainId(String mainId) {
		return busiPrdOrdItemMapper.selectByMainId(mainId);
	}
}
