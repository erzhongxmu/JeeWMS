package com.base.modules.bi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.modules.bi.entity.zdOnlCgreportItem;
import com.base.modules.bi.mapper.zdOnlCgreportItemMapper;
import com.base.modules.bi.service.IzdOnlCgreportItemService;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: onl_cgreport_item
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
@Service
public class ZdOnlCgreportItemServiceImpl extends ServiceImpl<com.base.modules.bi.mapper.zdOnlCgreportItemMapper, zdOnlCgreportItem> implements IzdOnlCgreportItemService {
	
	@Autowired
	private zdOnlCgreportItemMapper zdOnlCgreportItemMapper;
	
	@Override
	public List<zdOnlCgreportItem> selectByMainId(String mainId) {
		return zdOnlCgreportItemMapper.selectByMainId(mainId);
	}

	@Override
	public List<Map<String, String>> getAutoListQueryInfo(String cgrheadId) {
		LambdaQueryWrapper<zdOnlCgreportItem> var2 = new LambdaQueryWrapper();
		var2.eq(zdOnlCgreportItem::getCgrheadId, cgrheadId);
		var2.eq(zdOnlCgreportItem::getIsSearch, 1);
		List var3 = this.list(var2);
		ArrayList var4 = new ArrayList();
		int var5 = 0;

		HashMap var8;
		for(Iterator var6 = var3.iterator(); var6.hasNext(); var4.add(var8)) {
			zdOnlCgreportItem var7 = (zdOnlCgreportItem)var6.next();
			var8 = new HashMap(1024);
			var8.put("label", var7.getFieldTxt());
			if (oConvertUtils.isNotEmpty(var7.getDictCode())) {
				var8.put("view", "list");
			} else {
				var8.put("view", var7.getFieldType().toLowerCase());
			}

			var8.put("mode", oConvertUtils.isEmpty(var7.getSearchMode()) ? "single" : var7.getSearchMode());
			var8.put("field", var7.getFieldName());
			++var5;
			if (var5 > 2) {
				var8.put("hidden", "1");
			}
		}

		return var4;
	}
}
