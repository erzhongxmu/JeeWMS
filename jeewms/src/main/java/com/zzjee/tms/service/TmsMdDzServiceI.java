package com.zzjee.tms.service;
import com.zzjee.tms.entity.TmsMdDzEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TmsMdDzServiceI extends CommonService {
	
 	public void delete(TmsMdDzEntity entity) throws Exception;
 	
 	public Serializable save(TmsMdDzEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TmsMdDzEntity entity) throws Exception;
 	
}
