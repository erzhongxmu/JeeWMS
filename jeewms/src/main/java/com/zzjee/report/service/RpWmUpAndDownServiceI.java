package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmUpAndDownEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmUpAndDownServiceI extends CommonService{
	/**
	 * 删除RpWmUpAndDownEntity实体对象
	 */
 	public void delete(RpWmUpAndDownEntity entity) throws Exception;
	/**
	 * 保存RpWmUpAndDownEntity实体对象
	 */
 	public Serializable save(RpWmUpAndDownEntity entity) throws Exception;
	/**
	 * 保存或更新RpWmUpAndDownEntity实体对象
	 */
 	public void saveOrUpdate(RpWmUpAndDownEntity entity) throws Exception;
 	
}
