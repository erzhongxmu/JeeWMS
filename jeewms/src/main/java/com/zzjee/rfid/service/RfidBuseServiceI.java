package com.zzjee.rfid.service;
import com.zzjee.rfid.entity.RfidBuseEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RfidBuseServiceI extends CommonService{
	
 	public void delete(RfidBuseEntity entity) throws Exception;
 	
 	public Serializable save(RfidBuseEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RfidBuseEntity entity) throws Exception;
 	
}
