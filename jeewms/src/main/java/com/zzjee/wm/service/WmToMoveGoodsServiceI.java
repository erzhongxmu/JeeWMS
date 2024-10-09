package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmToMoveGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WmToMoveGoodsServiceI extends CommonService{
	
 	public void delete(WmToMoveGoodsEntity entity) throws Exception;
 	
 	public Serializable save(WmToMoveGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WmToMoveGoodsEntity entity) throws Exception;
 	
}
