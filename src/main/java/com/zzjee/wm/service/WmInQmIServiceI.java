package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmInQmIEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmInQmIServiceI extends CommonService{
	
 	public void delete(WmInQmIEntity entity) throws Exception;
 	
 	public Serializable save(WmInQmIEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmInQmIEntity entity) throws Exception;
 	
}
