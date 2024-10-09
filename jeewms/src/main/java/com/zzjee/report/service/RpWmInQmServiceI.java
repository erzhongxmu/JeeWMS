package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmInQmEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmInQmServiceI extends CommonService{
	/**
	 * 删除RpWmInQmEntity实体对象
	 */
 	public void delete(RpWmInQmEntity entity) throws Exception;
	/**
	 * 保存RpWmInQmEntity实体对象
	 */
 	public Serializable save(RpWmInQmEntity entity) throws Exception;
	/**
	 * 保存或更新RpWmInQmEntity实体对象
	 */
 	public void saveOrUpdate(RpWmInQmEntity entity) throws Exception;
 	
}
