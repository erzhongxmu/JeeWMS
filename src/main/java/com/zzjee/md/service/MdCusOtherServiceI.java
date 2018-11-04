package com.zzjee.md.service;
import com.zzjee.md.entity.MdCusOtherEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MdCusOtherServiceI extends CommonService{
	
 	public void delete(MdCusOtherEntity entity) throws Exception;
 	
 	public Serializable save(MdCusOtherEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MdCusOtherEntity entity) throws Exception;
 	
}
