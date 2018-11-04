package com.zzjee.wm.service;
import com.zzjee.wm.entity.WvStockEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WvStockServiceI extends CommonService{
	
 	public void delete(WvStockEntity entity) throws Exception;
 	
 	public Serializable save(WvStockEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WvStockEntity entity) throws Exception;
 	
}
