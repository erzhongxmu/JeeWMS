package com.zzjee.wm.service;
import com.zzjee.tms.entity.TmsYwDingdanEntity;
import com.zzjee.wm.entity.WmOmNoticeHEntity;
import com.zzjee.wm.entity.WmOmNoticeIEntity;

import java.util.List;

import com.zzjee.wm.entity.WmTmsNoticeHEntity;
import com.zzjee.wm.entity.WmTmsNoticeIEntity;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface WmOmNoticeHServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public  void addMain(WmOmNoticeHEntity wmOmNoticeH,
	        List<WmOmNoticeIEntity> wmOmNoticeIList) ;
	public void addMaintms(WmTmsNoticeHEntity wmOmNoticeH,
						List<WmTmsNoticeIEntity> wmOmNoticeIList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WmOmNoticeHEntity wmOmNoticeH,
	        List<WmOmNoticeIEntity> wmOmNoticeIList,List<TmsYwDingdanEntity> wmOmtmsIList);
	public void delMain (WmOmNoticeHEntity wmOmNoticeH);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(WmOmNoticeHEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(WmOmNoticeHEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(WmOmNoticeHEntity t);
}
