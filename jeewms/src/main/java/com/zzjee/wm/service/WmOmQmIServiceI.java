package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmOmQmIEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmOmQmIServiceI extends CommonService{
	
 	public void delete(WmOmQmIEntity entity) throws Exception;
 	
 	public Serializable save(WmOmQmIEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmOmQmIEntity entity) throws Exception;
 	
}
