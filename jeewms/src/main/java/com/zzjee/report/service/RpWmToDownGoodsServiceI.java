package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmToDownGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmToDownGoodsServiceI extends CommonService{
	/**
	 * 删除RpWmToDownGoodsEntity实体对象
	 */
 	public void delete(RpWmToDownGoodsEntity entity) throws Exception;
	/**
	 * 保存RpWmToDownGoodsEntity实体对象
	 */
 	public Serializable save(RpWmToDownGoodsEntity entity) throws Exception;
	/**
	 * 保存或更新RpWmToDownGoodsEntity实体对象
	 */
 	public void saveOrUpdate(RpWmToDownGoodsEntity entity) throws Exception;
 	
}
