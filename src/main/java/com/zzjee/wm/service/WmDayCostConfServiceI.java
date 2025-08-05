package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmDayCostConfEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WmDayCostConfServiceI extends CommonService{

 	public void delete(WmDayCostConfEntity entity) throws Exception;

 	public Serializable save(WmDayCostConfEntity entity) throws Exception;

 	public void saveOrUpdate(WmDayCostConfEntity entity) throws Exception;

}
