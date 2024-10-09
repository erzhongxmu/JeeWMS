package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmSttInGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmSttInGoodsServiceI extends CommonService{
	
 	public void delete(WmSttInGoodsEntity entity) throws Exception;
 	
 	public Serializable save(WmSttInGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmSttInGoodsEntity entity) throws Exception;
 	
}
