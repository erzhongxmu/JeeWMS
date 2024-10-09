package com.zzjee.wm.service;
import com.zzjee.wm.entity.WvStockSttBinEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WvStockSttBinServiceI extends CommonService{
	
 	public void delete(WvStockSttBinEntity entity) throws Exception;
 	
 	public Serializable save(WvStockSttBinEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WvStockSttBinEntity entity) throws Exception;
 	
}
