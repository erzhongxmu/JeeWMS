package com.zzjee.wm.service;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface WmOmNoticeHServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(WmOmNoticeHEntity wmOmNoticeH,
	        List<WmOmNoticeIEntity> wmOmNoticeIList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WmOmNoticeHEntity wmOmNoticeH,
	        List<WmOmNoticeIEntity> wmOmNoticeIList);
	public void delMain (WmOmNoticeHEntity wmOmNoticeH);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(WmOmNoticeHEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(WmOmNoticeHEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(WmOmNoticeHEntity t);
}
