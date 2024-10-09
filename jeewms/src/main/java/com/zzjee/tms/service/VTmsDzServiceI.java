package com.zzjee.tms.service;
import com.zzjee.tms.entity.VTmsDzEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface VTmsDzServiceI extends CommonService {
	
 	public void delete(VTmsDzEntity entity) throws Exception;
 	
 	public Serializable save(VTmsDzEntity entity) throws Exception;
 	
 	public void saveOrUpdate(VTmsDzEntity entity) throws Exception;
 	
}
