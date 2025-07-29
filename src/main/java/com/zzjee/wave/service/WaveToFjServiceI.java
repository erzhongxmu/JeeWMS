package com.zzjee.wave.service;
import com.zzjee.wave.entity.WaveToFjEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WaveToFjServiceI extends CommonService{

 	public void delete(WaveToFjEntity entity) throws Exception;

 	public Serializable save(WaveToFjEntity entity) throws Exception;

 	public void saveOrUpdate(WaveToFjEntity entity) throws Exception;

}
