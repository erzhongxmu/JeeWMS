package com.zzjee.oms.service;
import com.zzjee.oms.entity.OmsOederDetailEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface OmsOederDetailServiceI extends CommonService{
	
 	public void delete(OmsOederDetailEntity entity) throws Exception;
 	
 	public Serializable save(OmsOederDetailEntity entity) throws Exception;
 	
 	public void saveOrUpdate(OmsOederDetailEntity entity) throws Exception;
 	
}
