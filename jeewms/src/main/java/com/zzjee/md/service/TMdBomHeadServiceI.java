package com.zzjee.md.service;
import com.zzjee.md.entity.TMdBomHeadEntity;
import com.zzjee.md.entity.TMdBomItemEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

public interface TMdBomHeadServiceI extends CommonService {
	
 	@Override
    public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TMdBomHeadEntity tMdBomHead,
                        List<TMdBomItemEntity> tMdBomItemList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TMdBomHeadEntity tMdBomHead,
                           List<TMdBomItemEntity> tMdBomItemList);
	public void delMain(TMdBomHeadEntity tMdBomHead);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TMdBomHeadEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TMdBomHeadEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TMdBomHeadEntity t);
}
