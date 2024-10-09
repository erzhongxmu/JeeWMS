package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmNoticeConfEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmNoticeConfServiceI extends CommonService{
	
 	public void delete(WmNoticeConfEntity entity) throws Exception;
 	
 	public Serializable save(WmNoticeConfEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmNoticeConfEntity entity) throws Exception;
 	
}
