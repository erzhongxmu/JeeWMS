package org.jeecgframework.core.common.service.impl;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	public ICommonDao commonDao = null;

	/**
	 * 获取所有数据库表
	 * 
	 * @return
	 */
	@Override
    public List<DBTable> getAllDbTableName() {
		return commonDao.getAllDbTableName();
	}

	@Override
    public Integer getAllDbTableSize() {
		return commonDao.getAllDbTableSize();
	}

	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	
	@Override
    public <T> Serializable save(T entity) {
		return commonDao.save(entity);
	}

	
	@Override
    public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);

	}

	
	@Override
    public <T> void delete(T entity) {
		commonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	@Override
    public <T> void deleteAllEntitie(Collection<T> entities) {
		commonDao.deleteAllEntitie(entities);
	}

	/**
	 * 根据实体名获取对象
	 */
	@Override
    public <T> T get(Class<T> class1, Serializable id) {
		return commonDao.get(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	@Override
    public <T> List<T> getList(Class clas) {
		return commonDao.loadAll(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
	@Override
    public <T> T getEntity(Class entityName, Serializable id) {
		return commonDao.getEntity(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
    public <T> T findUniqueByProperty(Class<T> entityClass,
                                      String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	@Override
    public <T> List<T> findByProperty(Class<T> entityClass,
                                      String propertyName, Object value) {

		return commonDao.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	@Override
    public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.loadAll(entityClass);
	}

	@Override
    public <T> T singleResult(String hql) {
		return commonDao.singleResult(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 * @param id
	 */
	@Override
    public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	@Override
    public <T> void updateEntitie(T pojo) {
		commonDao.updateEntitie(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param hql
	 * @return
	 */
	@Override
    public <T> List<T> findByQueryString(String hql) {
		return commonDao.findByQueryString(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @param sql
	 * @return
	 */
	@Override
    public int updateBySqlString(String sql) {
		return commonDao.updateBySqlString(sql);
	}

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@Override
    public <T> List<T> findListbySql(String query) {
		return commonDao.findListbySql(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	@Override
    public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
                                             String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertyisOrder(entityClass, propertyName,
				value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
    public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getPageList(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
    public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
                                              final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
    public DataGridReturn getDataGridReturn(final CriteriaQuery cq,
                                            final boolean isOffset) {
		return commonDao.getDataGridReturn(cq, isOffset);
	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param hqlQuery
	 * @param needParameter
	 * @return
	 */
	@Override
    public PageList getPageList(final HqlQuery hqlQuery,
                                final boolean needParameter) {
		return commonDao.getPageList(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param hqlQuery
	 * @param isToEntity
	 * @return
	 */
	@Override
    public PageList getPageListBySql(final HqlQuery hqlQuery,
                                     final boolean isToEntity) {
		return commonDao.getPageListBySql(hqlQuery, isToEntity);
	}

	@Override
    public Session getSession()

	{
		return commonDao.getSession();
	}

	@Override
    public List findByExample(final String entityName,
                              final Object exampleEntity) {
		return commonDao.findByExample(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	@Override
    public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,
                                              Boolean ispage) {
		return commonDao.getListByCriteriaQuery(cq, ispage);
	}

	/**
	 * 文件上传
	 * 
	 * @param uploadFile
	 */
	@Override
    public <T> T uploadFile(UploadFile uploadFile) {
		return commonDao.uploadFile(uploadFile);
	}

	@Override
    public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return commonDao.viewOrDownloadFile(uploadFile);
	}

	/**
	 * 生成XML文件
	 * 
	 * @param importFile
	 *            XML全路径
	 * @return
	 */
	@Override
    public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	@Override
    public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	@Override
    public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return commonDao.comTree(all, comboTree);
	}

	@Override
    public List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive) {
        return commonDao.ComboTree(all, comboTreeModel, in, recursive);
	}

	/**
	 * 构建树形数据表
	 */
	@Override
    public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel) {
		return commonDao.treegrid(all, treeGridModel);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
    public <T> List<T> getAutoList(Autocomplete autocomplete) {
		StringBuffer sb = new StringBuffer("");
		for (String searchField : autocomplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"+ autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 "+ sb.toString();
		return commonDao.getSession().createQuery(hql).setFirstResult(autocomplete.getCurPage() - 1).setMaxResults(autocomplete.getMaxRows()).list();
	}

	
	@Override
    public Integer executeSql(String sql, List<Object> param) {
		return commonDao.executeSql(sql, param);
	}

	
	@Override
    public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}

	
	@Override
    public Integer executeSql(String sql, Map<String, Object> param) {
		return commonDao.executeSql(sql, param);
	}
	
	@Override
    public Object executeSqlReturnKey(String sql, Map<String, Object> param) {
		return commonDao.executeSqlReturnKey(sql, param);
	}
	
	@Override
    public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return commonDao.findForJdbc(sql, page, rows);
	}

	
	@Override
    public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findForJdbc(sql, objs);
	}

	
	@Override
    public List<Map<String, Object>> findForJdbcParam(String sql, int page,
                                                      int rows, Object... objs) {
		return commonDao.findForJdbcParam(sql, page, rows, objs);
	}

	
	@Override
    public <T> List<T> findObjForJdbc(String sql, int page, int rows,
                                      Class<T> clazz) {
		return commonDao.findObjForJdbc(sql, page, rows, clazz);
	}

	
	@Override
    public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.findOneForJdbc(sql, objs);
	}

	
	@Override
    public Long getCountForJdbc(String sql) {
		return commonDao.getCountForJdbc(sql);
	}
	@Override
    public Long getCountForJdbcParam(String sql, Object[] objs) {
		return commonDao.getCountForJdbcParam(sql,objs);
	}

	
	@Override
    public <T> void batchSave(List<T> entitys) {
		this.commonDao.batchSave(entitys);
	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param hql
	 * @return
	 */
	@Override
    public <T> List<T> findHql(String hql, Object... param) {
		return this.commonDao.findHql(hql, param);
	}

	@Override
    public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
                                int maxResult) {
		return this.commonDao.pageList(dc, firstResult, maxResult);
	}

	@Override
    public <T> List<T> findByDetached(DetachedCriteria dc) {
		return this.commonDao.findByDetached(dc);
	}

	/**
	 * 调用存储过程
	 */
	@Override
    public <T> List<T> executeProcedure(String procedureSql, Object... params) {
		return this.commonDao.executeProcedure(procedureSql, params);
	}

}
