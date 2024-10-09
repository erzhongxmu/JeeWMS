package com.zzjee.plc.service;
import com.zzjee.plc.entity.WmsPlcEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
public interface WmsPlcServiceI extends CommonService{
	/**
	 * 删除WmsPlcEntity对象
	 * @param entity 要删除的WmsPlcEntity对象
	 * @throws Exception 删除过程中的异常
	 */
 	public void delete(WmsPlcEntity entity) throws Exception;
	/**
	 * 保存WmsPlcEntity对象
	 * @param entity 要保存的WmsPlcEntity对象
	 * @return 保存后的对象的唯一标识符
	 * @throws Exception 保存过程中的异常
	 */
 	public Serializable save(WmsPlcEntity entity) throws Exception;
	/**
	 * 保存或更新WmsPlcEntity对象
	 * @param entity 要保存或更新的WmsPlcEntity对象
	 * @throws Exception 保存或更新过程中的异常
	 */
 	public void saveOrUpdate(WmsPlcEntity entity) throws Exception;
 	
}
