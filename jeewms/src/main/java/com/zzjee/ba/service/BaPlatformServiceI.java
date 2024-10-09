package com.zzjee.ba.service;
import com.zzjee.ba.entity.BaPlatformEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BaPlatformServiceI extends CommonService{
	
 	public void delete(BaPlatformEntity entity) throws Exception;
 	
 	public Serializable save(BaPlatformEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BaPlatformEntity entity) throws Exception;
 	
}
