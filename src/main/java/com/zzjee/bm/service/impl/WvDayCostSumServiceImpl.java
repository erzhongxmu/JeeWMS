package com.zzjee.bm.service.impl;
import com.zzjee.bm.service.WvDayCostSumServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.bm.entity.WvDayCostSumEntity;
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

@Service("wvDayCostSumService")
@Transactional
public class WvDayCostSumServiceImpl extends CommonServiceImpl implements WvDayCostSumServiceI {

	
 	public void delete(WvDayCostSumEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WvDayCostSumEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WvDayCostSumEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WvDayCostSumEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(WvDayCostSumEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(WvDayCostSumEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(WvDayCostSumEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("cost_data", t.getCostData());
		map.put("cus_code", t.getCusCode());
		map.put("cus_name", t.getCusName());
		map.put("cost_code", t.getCostCode());
		map.put("cost_name", t.getCostName());
		map.put("yuanj", t.getYuanj());
		map.put("bhsj", t.getBhsj());
		map.put("shuie", t.getShuie());
		map.put("hansj", t.getHansj());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WvDayCostSumEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{cost_data}",String.valueOf(t.getCostData()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{cus_name}",String.valueOf(t.getCusName()));
 		sql  = sql.replace("#{cost_code}",String.valueOf(t.getCostCode()));
 		sql  = sql.replace("#{cost_name}",String.valueOf(t.getCostName()));
 		sql  = sql.replace("#{yuanj}",String.valueOf(t.getYuanj()));
 		sql  = sql.replace("#{bhsj}",String.valueOf(t.getBhsj()));
 		sql  = sql.replace("#{shuie}",String.valueOf(t.getShuie()));
 		sql  = sql.replace("#{hansj}",String.valueOf(t.getHansj()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
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
					javaInter.execute("wv_day_cost_sum",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}