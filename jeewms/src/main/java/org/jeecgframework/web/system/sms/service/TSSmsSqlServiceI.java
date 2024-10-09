package org.jeecgframework.web.system.sms.service;
import org.jeecgframework.web.system.sms.entity.TSSmsSqlEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.Map;

public interface TSSmsSqlServiceI extends CommonService{
	
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
 	public boolean doAddSql(TSSmsSqlEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSSmsSqlEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSSmsSqlEntity t);
 	/**
 	 * 
 	  * getMap(这里用一句话描述这个方法的作用)
 	  *
 	  * @Title: getMap
 	  * @Description: TODO
 	  * @param @param sql
 	  * @param @param map
 	  * @param @return    设定文件
 	  * @return Map<String,Object>    返回类型
 	  * @throws
 	 */
 	public Map<String, Object> getMap(String sql,Map<String, Object> map);
}
