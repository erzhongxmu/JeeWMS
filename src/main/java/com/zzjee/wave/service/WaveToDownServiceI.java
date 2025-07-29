package com.zzjee.wave.service;
import com.zzjee.wave.entity.WaveToDownEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WaveToDownServiceI extends CommonService{

 	public void delete(WaveToDownEntity entity) throws Exception;

 	public Serializable save(WaveToDownEntity entity) throws Exception;

 	public void saveOrUpdate(WaveToDownEntity entity) throws Exception;

}
