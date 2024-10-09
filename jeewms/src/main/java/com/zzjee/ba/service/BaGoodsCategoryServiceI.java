package com.zzjee.ba.service;
import com.zzjee.ba.entity.BaGoodsCategoryEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BaGoodsCategoryServiceI extends CommonService{
	/**
	 * 删除
	 * @param entity
	 * @throws Exception
	 */
 	public void delete(BaGoodsCategoryEntity entity) throws Exception;
 	
 	public Serializable save(BaGoodsCategoryEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BaGoodsCategoryEntity entity) throws Exception;
 	
}
