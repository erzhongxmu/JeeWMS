package com.zzjee.uniapp.service;
import com.zzjee.uniapp.entity.WmsAppFunctionEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WmsAppFunctionServiceI extends CommonService{

 	public void delete(WmsAppFunctionEntity entity) throws Exception;

 	public Serializable save(WmsAppFunctionEntity entity) throws Exception;

 	public void saveOrUpdate(WmsAppFunctionEntity entity) throws Exception;

}
