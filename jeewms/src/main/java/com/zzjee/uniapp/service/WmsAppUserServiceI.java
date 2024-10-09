package com.zzjee.uniapp.service;
import com.zzjee.uniapp.entity.WmsAppUserEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmsAppUserServiceI extends CommonService{
	
 	public void delete(WmsAppUserEntity entity) throws Exception;
 	
 	public Serializable save(WmsAppUserEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmsAppUserEntity entity) throws Exception;
 	
}
