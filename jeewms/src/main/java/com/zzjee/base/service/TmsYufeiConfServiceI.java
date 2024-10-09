package com.zzjee.base.service;
import com.zzjee.base.entity.TmsYufeiConfEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TmsYufeiConfServiceI extends CommonService{
	
 	public void delete(TmsYufeiConfEntity entity) throws Exception;
 	
 	public Serializable save(TmsYufeiConfEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TmsYufeiConfEntity entity) throws Exception;
 	
}
