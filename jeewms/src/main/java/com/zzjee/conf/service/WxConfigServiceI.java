package com.zzjee.conf.service;
import com.zzjee.conf.entity.WxConfigEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WxConfigServiceI extends CommonService{
	
 	public void delete(WxConfigEntity entity) throws Exception;
 	
 	public Serializable save(WxConfigEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WxConfigEntity entity) throws Exception;
 	
}
