package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmUpAndDownEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmUpAndDownServiceI extends CommonService{
	
 	public void delete(RpWmUpAndDownEntity entity) throws Exception;
 	
 	public Serializable save(RpWmUpAndDownEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RpWmUpAndDownEntity entity) throws Exception;
 	
}
