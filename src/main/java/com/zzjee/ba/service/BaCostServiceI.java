package com.zzjee.ba.service;
import com.zzjee.ba.entity.BaCostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaCostServiceI extends CommonService{
	/**
	 * 删除
	 * @param entity
	 * @throws Exception
	 */
 	public void delete(BaCostEntity entity) throws Exception;
 	
 	public Serializable save(BaCostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BaCostEntity entity) throws Exception;
 	
}
