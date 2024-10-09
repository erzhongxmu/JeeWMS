package com.zzjee.wmapi.service;
import com.zzjee.wmapi.entity.WvGiNoticeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WvGiNoticeServiceI extends CommonService{
	
 	public void delete(WvGiNoticeEntity entity) throws Exception;
 	
 	public Serializable save(WvGiNoticeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WvGiNoticeEntity entity) throws Exception;
 	
}
