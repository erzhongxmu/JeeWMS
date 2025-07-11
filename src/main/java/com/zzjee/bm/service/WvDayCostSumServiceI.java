package com.zzjee.bm.service;
import com.zzjee.bm.entity.WvDayCostSumEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WvDayCostSumServiceI extends CommonService{

 	public void delete(WvDayCostSumEntity entity) throws Exception;

 	public Serializable save(WvDayCostSumEntity entity) throws Exception;

 	public void saveOrUpdate(WvDayCostSumEntity entity) throws Exception;

}
