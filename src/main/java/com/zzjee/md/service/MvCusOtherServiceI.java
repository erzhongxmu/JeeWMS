package com.zzjee.md.service;
import com.zzjee.md.entity.MvCusOtherEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MvCusOtherServiceI extends CommonService{
	
 	public void delete(MvCusOtherEntity entity) throws Exception;
 	
 	public Serializable save(MvCusOtherEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MvCusOtherEntity entity) throws Exception;
 	
}
