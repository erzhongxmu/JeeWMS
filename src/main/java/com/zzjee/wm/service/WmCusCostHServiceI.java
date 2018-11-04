package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmCusCostHEntity;
import com.zzjee.wm.entity.WmCusCostIEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface WmCusCostHServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(WmCusCostHEntity wmCusCostH,
	        List<WmCusCostIEntity> wmCusCostIList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WmCusCostHEntity wmCusCostH,
	        List<WmCusCostIEntity> wmCusCostIList);
	public void delMain (WmCusCostHEntity wmCusCostH);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(WmCusCostHEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(WmCusCostHEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(WmCusCostHEntity t);
}
