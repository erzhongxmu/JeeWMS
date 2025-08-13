package com.zzjee.wv.service;
import com.zzjee.wv.entity.MvStockCusEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface MvStockCusServiceI extends CommonService{

 	public void delete(MvStockCusEntity entity) throws Exception;

 	public Serializable save(MvStockCusEntity entity) throws Exception;

 	public void saveOrUpdate(MvStockCusEntity entity) throws Exception;

}
