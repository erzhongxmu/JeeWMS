package com.zzjee.mvyj.service;
import com.zzjee.mvyj.entity.MvStockYjEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MvStockYjServiceI extends CommonService{
	
 	public void delete(MvStockYjEntity entity) throws Exception;
 	
 	public Serializable save(MvStockYjEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MvStockYjEntity entity) throws Exception;
 	
}
