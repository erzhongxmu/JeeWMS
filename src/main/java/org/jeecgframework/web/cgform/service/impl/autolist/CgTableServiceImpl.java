package org.jeecgframework.web.cgform.service.impl.autolist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 *
 * @Title:CgTableServiceImpl
 * @description:动态表数据服务实现
 * @author 赵俊夫
 * @date Jul 5, 2013 9:34:51 PM
 * @version V1.0
 */
@Service("cgTableService")
@Transactional
public class CgTableServiceImpl extends CommonServiceImpl implements CgTableServiceI {
	@Autowired
	private CommonService commonService;

	@Autowired
	private DataBaseService dataBaseService;

	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private DataSource dataSource;

	@Override
    @SuppressWarnings("unchecked")

	public List<Map<String, Object>> querySingle(String table, String field, Map params,
			int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,field,params,sqlB);
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}

	@Override
    public List<Map<String, Object>> querySingle(String table, String field, Map params,
                                                 String sort, String order, int page, int rows) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,field,params,sqlB);
		// 对 sort 和 order 进行安全过滤
		sort = sanitizeSort(sort);
		order = sanitizeOrder(order);
		if (!StringUtil.isEmpty(sort) && !StringUtil.isEmpty(order)) {
			sqlB.append(" ORDER BY ").append(sort).append(" ").append(order);
		}
		List<Map<String, Object>> result = commonService.findForJdbcParam(sqlB
				.toString(), page, rows);
		return result;
	}

	private String sanitizeSort(String sort) {
		if (sort == null) {
			return null;
		}
		if (sort.matches("[a-zA-Z0-9_]+")) {
			return sort;
		}
		return null;
	}
	private String sanitizeOrder(String order) {
		if (order == null) {
			return null;
		}
		if ("ASC".equalsIgnoreCase(order) || "DESC".equalsIgnoreCase(order)) {
			return order.toUpperCase();
		}
		return null;
	}
	@Override
    @SuppressWarnings("unchecked")

	public boolean delete(String table, Object id) {
		try{
			CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByTableName(table);
			Map<String,Object> data  = dataBaseService.findOneForJdbc(table, id.toString());
			if(data!=null){
				//打印测试
			    Iterator it=data.entrySet().iterator();
			    while(it.hasNext()){
			    	Map.Entry entry=(Map.Entry)it.next();
			        Object ok=entry.getKey();
			        Object ov=entry.getValue()==null?"":entry.getValue();
			        //org.jeecgframework.core.util.LogUtil.info("name:"+ok.toString()+";value:"+ov.toString());
			    }
				data = CommUtils.mapConvert(data);
				dataBaseService.executeSqlExtend(head.getId(), "delete", data);

				dataBaseService.executeJavaExtend(head.getId(), "delete", data);

			}
			//step.1 删除表
			StringBuilder deleteSql = new StringBuilder();
			deleteSql.append("DELETE FROM "+table+" WHERE id = ?");
			if(!QueryParamUtil.sql_inj(id.toString())){
				commonService.executeSql(deleteSql.toString(), id);
			}
			//step.2 判断是否有明细表,进行连带删除
			String[] subTables = head.getSubTableStr()==null?new String[0]:head.getSubTableStr().split(",");
			for(String subTable:subTables){
				Map<String, CgFormFieldEntity>  fields = cgFormFieldService.getAllCgFormFieldByTableName(subTable);
				String subFkField = null;
				Iterator it = fields.keySet().iterator();
				for(;it.hasNext();){
					String fieldName  = (String) it.next();
					CgFormFieldEntity fieldc = fields.get(fieldName);
					if(StringUtil.isNotEmpty(fieldc.getMainTable())){
						if(table.equalsIgnoreCase(fieldc.getMainTable())){
							subFkField = fieldName;
						}
					}
				}
				if(StringUtil.isNotEmpty(subFkField)){
					String dsql = "delete from "+subTable+" "+"where "+subFkField+" = ? ";
					this.executeSql(dsql,id);
				}
			}
//--------longjb-start--20150526 ----for:add step.3 判断是否有附件字段,进行连带删除附件及附件表---------------
			List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
			if(uploadBeans!=null){
				for(CgUploadEntity b:uploadBeans){
					String path = ResourceUtil.getSysPath()+File.separator+b.getRealpath();//附件路径
					FileUtils.delete(path);
					cgFormFieldService.deleteEntityById(CgUploadEntity.class, b.getId());
				}
			}
//--------longjb-end--20150526 ----for:add step.3 判断是否有附件字段,进行连带删除附件及附件表---------------
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	private void dealQuerySql(String table, String field, Map params, StringBuilder sqlB) {
		sqlB.append(" SELECT ");
		String[] fields = field.split(",");
		for (int i = 0; i < fields.length; i++) {
			sqlB.append(fields[i]);
			if (i < fields.length - 1) {
				sqlB.append(",");
			}
		}
		sqlB.append(" FROM ").append(table);

		// 先构建基础的 SQL 语句部分（不含参数部分）
		String baseSql = sqlB.toString();
		if (params.size() >= 1) {
			sqlB.append(" WHERE 1=1 ");
			try {
				Connection connection = dataSource.getConnection();
				// 创建预编译语句对象，初始 SQL 为基础语句加上 WHERE 1=1
				PreparedStatement preparedStatement = connection.prepareStatement(baseSql + " WHERE 1=1 ");
				int paramIndex = 1;
				Iterator it = params.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String value = String.valueOf(params.get(key));
					if (!StringUtil.isEmpty(value) &&!"null".equalsIgnoreCase(value)) {
						// 构建带有参数占位符的条件语句，防止 SQL 注入
						sqlB.append(" AND ").append(key).append(" =? ");
						// 根据参数类型设置对应的值到预编译语句中，此处先简单按字符串处理，可按需扩展类型判断
						preparedStatement.setString(paramIndex, value);
						paramIndex++;
					}
				}
				// 重新获取完整且安全的 SQL 语句
				sqlB.setLength(0);
				sqlB.append(preparedStatement.toString().substring(preparedStatement.toString().indexOf("SELECT")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Object dataRuleSql = JeecgDataAutorUtils.loadDataSearchConditonSQLString();
		if (dataRuleSql!= null &&!"".equals(dataRuleSql)) {
			if (params.size() == 0) {
				sqlB.append(" WHERE 1=1 ");
			}
			try {
				Connection connection = dataSource.getConnection();
				// 根据已有 SQL 语句和 dataRuleSql 构建新的预编译语句基础部分
				String combinedSql = sqlB.toString();
				if (params.size() > 0) {
					combinedSql += " AND ";
				} else {
					combinedSql += " WHERE 1=1 AND ";
				}
				combinedSql += dataRuleSql.toString().replaceAll("([^\\s=]+)=(\\S+)", "$1 =?");
				PreparedStatement finalPreparedStatement = connection.prepareStatement(combinedSql);
				int paramIndex = params.size() > 0? params.size() + 1 : 1;
				// 解析 dataRuleSql 中的参数值并设置到预编译语句中
				String[] parts = dataRuleSql.toString().split(" ");
				for (int i = 0; i < parts.length; i += 3) {
					if (i + 2 < parts.length) {
						finalPreparedStatement.setString(paramIndex, parts[i + 2]);
						paramIndex++;
					}
				}
				sqlB.setLength(0);
				sqlB.append(finalPreparedStatement.toString().substring(finalPreparedStatement.toString().indexOf("SELECT")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
    @SuppressWarnings("unchecked")

	public Long getQuerySingleSize(String table, String field, Map params) {
		StringBuilder sqlB = new StringBuilder();
		dealQuerySql(table,"count(*) as query_size,",params,sqlB);
		List<Map<String, Object>> result = commonService.findForJdbc(sqlB.toString());
		if(result.size()>=1){
			return Long.parseLong(String.valueOf(result.get(0).get("query_size")));
		}else{
			return 0L;
		}
	}

	@Override
    public boolean deleteBatch(String table, String[] ids) {
		try{
			for(String id:ids){
				delete(table, id);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return true;
	}

	@Override
	public void treeFromResultHandle(String table, String parentIdFieldName,
			String parentIdFieldType, List<Map<String, Object>> result) {
		if(result != null && result.size() > 0) {
			String parentIds = "";
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> resultMap = result.get(i);
				if(parentIdFieldType.equalsIgnoreCase(CgAutoListConstant.TYPE_STRING)) {
					parentIds += ",'" + resultMap.get("id") + "'";
				}else {
					parentIds += "," + resultMap.get("id");
				}
			}
			parentIds = parentIds.substring(1);
			String subSQL = "select `" + parentIdFieldName + "`, count(*) ct from " + table + " a where a.`" + parentIdFieldName + "` in" + "(" + parentIds + ") group by a.`" + parentIdFieldName + "`";
			List<Map<String, Object>> subCountResult =  this.findForJdbc(subSQL);
			Map<String, Object> subCountMap = new HashMap<String, Object>(1024);
			for (Map<String, Object> map : subCountResult) {
				subCountMap.put(map.get(parentIdFieldName).toString(), map.get("ct"));
			}
			for(Map<String, Object> resultMap:result){
				String state = "closed";
				if(subCountMap.get(resultMap.get("id").toString()) == null) {
					state = "open";
				}
				resultMap.put("state", state);
			}
		}
	}

}
