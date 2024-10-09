package com.zzjee.tmsv.service;
import com.zzjee.tmsv.entity.VYsddEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface VYsddServiceI extends CommonService {
	
 	public void delete(VYsddEntity entity) throws Exception;
 	
 	public Serializable save(VYsddEntity entity) throws Exception;
 	
 	public void saveOrUpdate(VYsddEntity entity) throws Exception;
 	
}
