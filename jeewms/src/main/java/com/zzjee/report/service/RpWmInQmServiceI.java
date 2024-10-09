package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmInQmEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmInQmServiceI extends CommonService{
	
 	public void delete(RpWmInQmEntity entity) throws Exception;
 	
 	public Serializable save(RpWmInQmEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RpWmInQmEntity entity) throws Exception;
 	
}
