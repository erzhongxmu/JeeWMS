package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmToUpGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmToUpGoodsServiceI extends CommonService{
	
 	public void delete(RpWmToUpGoodsEntity entity) throws Exception;
 	
 	public Serializable save(RpWmToUpGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RpWmToUpGoodsEntity entity) throws Exception;
 	
}
