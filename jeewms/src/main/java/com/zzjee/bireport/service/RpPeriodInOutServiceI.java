package com.zzjee.bireport.service;
import com.zzjee.bireport.entity.RpPeriodInOutEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpPeriodInOutServiceI extends CommonService{
	
 	public void delete(RpPeriodInOutEntity entity) throws Exception;
 	
 	public Serializable save(RpPeriodInOutEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RpPeriodInOutEntity entity) throws Exception;
 	
}
