package com.zzjee.md.service;
import com.zzjee.md.entity.MdCusEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MdCusServiceI extends CommonService{
	
 	public void delete(MdCusEntity entity) throws Exception;
 	
 	public Serializable save(MdCusEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MdCusEntity entity) throws Exception;
 	
}
