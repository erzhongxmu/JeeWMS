package com.base.modules.bi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.modules.bi.entity.zdOnlCgreportItem;

import java.util.List;
import java.util.Map;

/**
 * @Description: onl_cgreport_item
 * @Author: wms-cloud
 * @Date:   2020-12-09
 * @Version: V1.0
 */
public interface IzdOnlCgreportItemService extends IService<zdOnlCgreportItem> {

	public List<zdOnlCgreportItem> selectByMainId(String mainId);

	List<Map<String, String>> getAutoListQueryInfo(String var1);
}
