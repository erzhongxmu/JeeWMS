package com.zzjee.md.service;
import com.zzjee.md.entity.MdSupEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MdSupServiceI extends CommonService{
	
 	public void delete(MdSupEntity entity) throws Exception;
 	
 	public Serializable save(MdSupEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MdSupEntity entity) throws Exception;
 	
}
