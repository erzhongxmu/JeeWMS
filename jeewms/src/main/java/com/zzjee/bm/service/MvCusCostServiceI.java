package com.zzjee.bm.service;
import com.zzjee.bm.entity.MvCusCostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MvCusCostServiceI extends CommonService{
	
 	public void delete(MvCusCostEntity entity) throws Exception;
 	
 	public Serializable save(MvCusCostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MvCusCostEntity entity) throws Exception;
 	
}
