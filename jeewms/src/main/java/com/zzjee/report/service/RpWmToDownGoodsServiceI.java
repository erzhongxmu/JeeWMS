package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmToDownGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmToDownGoodsServiceI extends CommonService{
	
 	public void delete(RpWmToDownGoodsEntity entity) throws Exception;
 	
 	public Serializable save(RpWmToDownGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RpWmToDownGoodsEntity entity) throws Exception;
 	
}
