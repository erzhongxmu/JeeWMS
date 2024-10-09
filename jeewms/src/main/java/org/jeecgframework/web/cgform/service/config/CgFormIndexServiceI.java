package org.jeecgframework.web.cgform.service.config;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormIndexEntity;

public interface CgFormIndexServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(CgFormIndexEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(CgFormIndexEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(CgFormIndexEntity t);
 	
 	/**
 	 * 更新索引
 	 * @param cgFormHead
 	 * @return 
 	 * 
 	 */
	public boolean updateIndexes(CgFormHeadEntity cgFormHead);
	
	/**
	 * 创建索引
	 * @param cgFormHead
	 */
	public void createIndexes(CgFormHeadEntity cgFormHead);
}
