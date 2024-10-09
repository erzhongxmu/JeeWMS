package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmToUpGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmToUpGoodsServiceI extends CommonService{
	/**
	 * 删除RpWmToUpGoodsEntity实体对象
	 */
 	public void delete(RpWmToUpGoodsEntity entity) throws Exception;
	/**
	 * 保存RpWmToUpGoodsEntity实体对象
	 */
 	public Serializable save(RpWmToUpGoodsEntity entity) throws Exception;
	/**
	 * 保存或更新RpWmToUpGoodsEntity实体对象
	 */
 	public void saveOrUpdate(RpWmToUpGoodsEntity entity) throws Exception;
 	
}
