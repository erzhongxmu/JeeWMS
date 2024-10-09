<#if packageStyle == "service">
package ${bussiPackage}.${entityPackage}.service.impl;
import ${bussiPackage}.${entityPackage}.service.${entityName}ServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import ${bussiPackage}.${entityPackage}.entity.${entityName}Entity;
<#else>
package ${bussiPackage}.service.impl.${entityPackage};
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
</#if>
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("${entityName?uncap_first}Service")
@Transactional
public class ${entityName}ServiceImpl extends CommonServiceImpl implements ${entityName}ServiceI {

	
 	public void delete(${entityName}Entity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(${entityName}Entity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(${entityName}Entity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
	<#list buttons as btn>
	<#if btn.optType=='action'>
 	/**
	 * 自定义按钮-[${btn.buttonName}]业务处理
	 * @param id
	 * @return
	 */
	 public void do${btn.buttonCode?cap_first}Bus(${entityName}Entity t) throws Exception{
	 	//-----------------sql增强 start----------------------------
	 	<#list buttonSqlMap[btn.buttonCode] as sql>
	 	//sql增强第${sql_index+1}条
	 	String sqlEnhance_${sql_index+1} ="${sql}";
	 	this.executeSql(replaceVal(sqlEnhance_${sql_index+1},t));
	 	</#list>
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	<#if buttonJavaMap??&&buttonJavaMap[btn.buttonCode]?? >
	 		Map<String,Object> data = populationMap(t);
	 		executeJavaExtend("${buttonJavaMap[btn.buttonCode].cgJavaType}","${buttonJavaMap[btn.buttonCode].cgJavaValue}",data);
	 	</#if>
	 	//-----------------java增强 end-----------------------------
	 }
 	</#if>
 	</#list> 
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(${entityName}Entity t) throws Exception{
		//-----------------sql增强 start----------------------------
 		<#list buttonSqlMap['add'] as sql>
	 	//sql增强第${sql_index+1}条
	 	String sqlEnhance_${sql_index+1} ="${sql}";
	 	this.executeSql(replaceVal(sqlEnhance_${sql_index+1},t));
	 	</#list>
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	<#if buttonJavaMap??&&buttonJavaMap['add']?? >
	 		Map<String,Object> data = populationMap(t);
	 		executeJavaExtend("${buttonJavaMap['add'].cgJavaType}","${buttonJavaMap['add'].cgJavaValue}",data);
	 	</#if>
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(${entityName}Entity t) throws Exception{
		//-----------------sql增强 start----------------------------
 		<#list buttonSqlMap['update'] as sql>
	 	//sql增强第${sql_index+1}条
	 	String sqlEnhance_${sql_index+1} ="${sql}";
	 	this.executeSql(replaceVal(sqlEnhance_${sql_index+1},t));
	 	</#list>
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	<#if buttonJavaMap??&&buttonJavaMap['update']?? >
	 		Map<String,Object> data = populationMap(t);
	 		executeJavaExtend("${buttonJavaMap['update'].cgJavaType}","${buttonJavaMap['update'].cgJavaValue}",data);
	 	</#if>
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(${entityName}Entity t) throws Exception{
	    //-----------------sql增强 start----------------------------
 		<#list buttonSqlMap['delete'] as sql>
	 	//sql增强第${sql_index+1}条
	 	String sqlEnhance_${sql_index+1} ="${sql}";
	 	this.executeSql(replaceVal(sqlEnhance_${sql_index+1},t));
	 	</#list>
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	<#if buttonJavaMap??&&buttonJavaMap['delete']?? >
	 		Map<String,Object> data = populationMap(t);
	 		executeJavaExtend("${buttonJavaMap['delete'].cgJavaType}","${buttonJavaMap['delete'].cgJavaValue}",data);
	 	</#if>
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(${entityName}Entity t){
		Map<String,Object> map = new HashMap<String,Object>();
		<#list columns as po>
		map.put("${fieldMeta[po.fieldName]?lower_case}", t.get${po.fieldName?cap_first}());
 		</#list>
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,${entityName}Entity t){
 		<#list columns as po>
 		sql  = sql.replace("${'#'}{${fieldMeta[po.fieldName]?lower_case}}",String.valueOf(t.get${po.fieldName?cap_first}()));
 		</#list>
 		sql  = sql.replace("${'#'}{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("${tableName}",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}