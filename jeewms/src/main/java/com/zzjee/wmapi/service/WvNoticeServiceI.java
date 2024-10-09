package com.zzjee.wmapi.service;
import com.zzjee.wmapi.entity.WvNoticeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WvNoticeServiceI extends CommonService{
	
 	public void delete(WvNoticeEntity entity) throws Exception;
 	
 	public Serializable save(WvNoticeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WvNoticeEntity entity) throws Exception;
 	
}
