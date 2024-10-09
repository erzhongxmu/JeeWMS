package com.zzjee.ba.service;
import com.zzjee.ba.entity.BaCostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BaCostServiceI extends CommonService{
	
 	public void delete(BaCostEntity entity) throws Exception;
 	
 	public Serializable save(BaCostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BaCostEntity entity) throws Exception;
 	
}
