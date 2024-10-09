package com.zzjee.ba.service;
import com.zzjee.ba.entity.BaStoreEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BaStoreServiceI extends CommonService{
	
 	public void delete(BaStoreEntity entity) throws Exception;
 	
 	public Serializable save(BaStoreEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BaStoreEntity entity) throws Exception;
 	
}
