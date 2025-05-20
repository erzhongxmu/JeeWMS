package com.zzjee.ba.service;
import com.zzjee.ba.entity.BaCostConfEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaCostConfServiceI extends CommonService{
	/**
	 * 删除
	 * @param entity
	 * @throws Exception
	 */
 	public void delete(BaCostConfEntity entity) throws Exception;
 	
 	public Serializable save(BaCostConfEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BaCostConfEntity entity) throws Exception;
 	
}
