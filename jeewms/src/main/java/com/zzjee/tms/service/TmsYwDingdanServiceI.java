package com.zzjee.tms.service;
import com.zzjee.tms.entity.TmsYwDingdanEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TmsYwDingdanServiceI extends CommonService {
	
 	public void delete(TmsYwDingdanEntity entity) throws Exception;
 	
 	public Serializable save(TmsYwDingdanEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TmsYwDingdanEntity entity) throws Exception;
 	
}
