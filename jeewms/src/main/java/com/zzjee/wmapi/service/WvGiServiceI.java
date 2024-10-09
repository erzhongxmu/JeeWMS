package com.zzjee.wmapi.service;
import com.zzjee.wmapi.entity.WvGiEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WvGiServiceI extends CommonService{
	
 	public void delete(WvGiEntity entity) throws Exception;
 	
 	public Serializable save(WvGiEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WvGiEntity entity) throws Exception;
 	
}
