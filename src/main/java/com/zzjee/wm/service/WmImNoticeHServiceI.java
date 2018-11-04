package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmImNoticeHEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface WmImNoticeHServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(WmImNoticeHEntity wmImNoticeH,
	        List<WmImNoticeIEntity> wmImNoticeIList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WmImNoticeHEntity wmImNoticeH,
	        List<WmImNoticeIEntity> wmImNoticeIList);
	public void delMain (WmImNoticeHEntity wmImNoticeH);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(WmImNoticeHEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(WmImNoticeHEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(WmImNoticeHEntity t);
}
