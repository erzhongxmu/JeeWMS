package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmPrintModelEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmPrintModelServiceI extends CommonService{
	
 	public void delete(WmPrintModelEntity entity) throws Exception;
 	
 	public Serializable save(WmPrintModelEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmPrintModelEntity entity) throws Exception;
 	
}
