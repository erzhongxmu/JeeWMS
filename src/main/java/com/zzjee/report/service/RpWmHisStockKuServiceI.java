package com.zzjee.report.service;
import com.zzjee.report.entity.RpWmHisStockKuEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface RpWmHisStockKuServiceI extends CommonService{
    /**
     * 删除RpWmHisStockKuEntity实体对象
	 */
 	public void delete(RpWmHisStockKuEntity entity) throws Exception;
	/**
	 * 保存RpWmHisStockKuEntity实体对象
	 */
 	public Serializable save(RpWmHisStockKuEntity entity) throws Exception;
	/**
	 * 保存或更新RpWmHisStockKuEntity实体对象
	 */
 	public void saveOrUpdate(RpWmHisStockKuEntity entity) throws Exception;
 	
}
