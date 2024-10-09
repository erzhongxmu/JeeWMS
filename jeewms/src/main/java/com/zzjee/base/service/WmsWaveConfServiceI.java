package com.zzjee.base.service;
import com.zzjee.base.entity.WmsWaveConfEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmsWaveConfServiceI extends CommonService{
	
 	public void delete(WmsWaveConfEntity entity) throws Exception;
 	
 	public Serializable save(WmsWaveConfEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmsWaveConfEntity entity) throws Exception;
 	
}
