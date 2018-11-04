package com.zzjee.md.service;
import com.zzjee.md.entity.MdGoodsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface MdGoodsServiceI extends CommonService{
	
 	public void delete(MdGoodsEntity entity) throws Exception;
 	
 	public Serializable save(MdGoodsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(MdGoodsEntity entity) throws Exception;
 	
}
