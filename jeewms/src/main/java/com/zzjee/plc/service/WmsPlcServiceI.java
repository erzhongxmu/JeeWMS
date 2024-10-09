package com.zzjee.plc.service;
import com.zzjee.plc.entity.WmsPlcEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmsPlcServiceI extends CommonService{
	
 	public void delete(WmsPlcEntity entity) throws Exception;
 	
 	public Serializable save(WmsPlcEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmsPlcEntity entity) throws Exception;
 	
}
