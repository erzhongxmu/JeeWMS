package com.base.modules.jeeerp.service.impl;

import com.base.modules.jeeerp.entity.ConfErpItem;
import com.base.modules.jeeerp.mapper.ConfErpItemMapper;
import com.base.modules.jeeerp.service.IConfErpItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: conf_erp_item
 * @Author: base-boot
 * @Date:   2022-11-19
 * @Version: V1.0
 */
@Service
public class ConfErpItemServiceImpl extends ServiceImpl<ConfErpItemMapper, ConfErpItem> implements IConfErpItemService {

	@Autowired
	private ConfErpItemMapper confErpItemMapper;

	@Override
	public List<ConfErpItem> selectByMainId(String mainId) {
		return confErpItemMapper.selectByMainId(mainId);
	}
}
