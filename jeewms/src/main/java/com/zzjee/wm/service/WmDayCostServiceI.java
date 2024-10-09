package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmDayCostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmDayCostServiceI extends CommonService{
	
 	public void delete(WmDayCostEntity entity) throws Exception;
 	
 	public Serializable save(WmDayCostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmDayCostEntity entity) throws Exception;
 	
}
