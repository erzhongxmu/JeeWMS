package com.zzjee.uniapp.service;
import com.zzjee.uniapp.entity.WmsAppRoleEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WmsAppRoleServiceI extends CommonService{

 	public void delete(WmsAppRoleEntity entity) throws Exception;

 	public Serializable save(WmsAppRoleEntity entity) throws Exception;

 	public void saveOrUpdate(WmsAppRoleEntity entity) throws Exception;

}
