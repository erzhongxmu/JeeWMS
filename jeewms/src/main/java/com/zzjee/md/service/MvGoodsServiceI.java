package com.zzjee.md.service;
import com.zzjee.md.entity.MvGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MvGoodsServiceI extends CommonService{
	
 	public void delete(MvGoodsEntity entity) throws Exception;
 	
 	public Serializable save(MvGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MvGoodsEntity entity) throws Exception;
 	
}
