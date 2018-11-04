package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmToDownGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmToDownGoodsServiceI extends CommonService{
	
 	public void delete(WmToDownGoodsEntity entity) throws Exception;
 	
 	public Serializable save(WmToDownGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmToDownGoodsEntity entity) throws Exception;
 	
}
