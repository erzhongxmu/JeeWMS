package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmPlatIoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmPlatIoServiceI extends CommonService{
	
 	public void delete(WmPlatIoEntity entity) throws Exception;
 	
 	public Serializable save(WmPlatIoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmPlatIoEntity entity) throws Exception;
 	
}
