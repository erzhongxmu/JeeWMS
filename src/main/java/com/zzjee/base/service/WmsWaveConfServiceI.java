package com.zzjee.base.service;
import com.zzjee.base.entity.WmsWaveConfEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface WmsWaveConfServiceI extends CommonService{
	/**
	 * 删除
	 * @param entity
	 * @throws Exception
	 */
 	public void delete(WmsWaveConfEntity entity) throws Exception;

	/**
	 * 保存
	 * @param entity
	 * @return
	 * @throws Exception
	 */
 	public Serializable save(WmsWaveConfEntity entity) throws Exception;

	/**
	 * 保存更新
	 * @param entity
	 * @throws Exception
	 */
 	public void saveOrUpdate(WmsWaveConfEntity entity) throws Exception;

}
