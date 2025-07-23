package com.zzjee.sys.service;
import com.zzjee.sys.entity.SysParaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface SysParaServiceI extends CommonService{

 	public void delete(SysParaEntity entity) throws Exception;

 	public Serializable save(SysParaEntity entity) throws Exception;

 	public void saveOrUpdate(SysParaEntity entity) throws Exception;

}
