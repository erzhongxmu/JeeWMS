package org.jeecgframework.web.system.sms.service;
import org.jeecgframework.web.system.sms.entity.TSSmsTemplateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TSSmsTemplateServiceI extends CommonService{
	
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
 	public boolean doAddSql(TSSmsTemplateEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSSmsTemplateEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSSmsTemplateEntity t);
}
