package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmHisStockKuEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmHisStockKuServiceI extends CommonService{
	
 	public void delete(RpWmHisStockKuEntity entity) throws Exception;
 	
 	public Serializable save(RpWmHisStockKuEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RpWmHisStockKuEntity entity) throws Exception;
 	
}
