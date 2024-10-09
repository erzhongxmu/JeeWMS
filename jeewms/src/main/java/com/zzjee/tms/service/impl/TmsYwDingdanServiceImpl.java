package com.zzjee.tms.service.impl;

import com.zzjee.tms.entity.TmsYwDingdanEntity;
import com.zzjee.tms.service.TmsYwDingdanServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("tmsYwDingdanService")
@Transactional
public class TmsYwDingdanServiceImpl extends CommonServiceImpl implements TmsYwDingdanServiceI {

	
 	@Override
    public void delete(TmsYwDingdanEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	@Override
    public Serializable save(TmsYwDingdanEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	@Override
    public void saveOrUpdate(TmsYwDingdanEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TmsYwDingdanEntity t) throws Exception{
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
	private void doUpdateBus(TmsYwDingdanEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param t
	 * @return
	 */
	private void doDelBus(TmsYwDingdanEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TmsYwDingdanEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
		map.put("sys_org_code", t.getSysOrgCode());
		map.put("sys_company_code", t.getSysCompanyCode());
		map.put("fadh", t.getFadh());
		map.put("username", t.getUsername());
		map.put("fahuoren", t.getFahuoren());
		map.put("fhrdh", t.getFhrdh());
		map.put("fhrdz", t.getFhrdz());
		map.put("shouhuoren", t.getShouhuoren());
		map.put("shrdh", t.getShrdh());
		map.put("chehao", t.getChehao());
		map.put("huowu", t.getHuowu());
		map.put("chang", t.getChang());
		map.put("kuan", t.getKuan());
		map.put("gao", t.getGao());
		map.put("tiji", t.getTiji());
		map.put("zhongl", t.getZhongl());
		map.put("daishouk", t.getDaishouk());
		map.put("dengtongzhi", t.getDengtongzhi());
		map.put("jiage", t.getJiage());
		map.put("xiadanfj", t.getXiadanfj());
		map.put("huidanfj", t.getHuidanfj());
		map.put("zhuangtai", t.getZhuangtai());
		map.put("xdrmz", t.getXdrmz());
		map.put("siji", t.getSiji());
		map.put("sdsj", t.getSdsj());
		map.put("yjsdsj", t.getYjsdsj());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TmsYwDingdanEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{fadh}",String.valueOf(t.getFadh()));
 		sql  = sql.replace("#{username}",String.valueOf(t.getUsername()));
 		sql  = sql.replace("#{fahuoren}",String.valueOf(t.getFahuoren()));
 		sql  = sql.replace("#{fhrdh}",String.valueOf(t.getFhrdh()));
 		sql  = sql.replace("#{fhrdz}",String.valueOf(t.getFhrdz()));
 		sql  = sql.replace("#{shouhuoren}",String.valueOf(t.getShouhuoren()));
 		sql  = sql.replace("#{shrdh}",String.valueOf(t.getShrdh()));
 		sql  = sql.replace("#{chehao}",String.valueOf(t.getChehao()));
 		sql  = sql.replace("#{huowu}",String.valueOf(t.getHuowu()));
 		sql  = sql.replace("#{chang}",String.valueOf(t.getChang()));
 		sql  = sql.replace("#{kuan}",String.valueOf(t.getKuan()));
 		sql  = sql.replace("#{gao}",String.valueOf(t.getGao()));
 		sql  = sql.replace("#{tiji}",String.valueOf(t.getTiji()));
 		sql  = sql.replace("#{zhongl}",String.valueOf(t.getZhongl()));
 		sql  = sql.replace("#{daishouk}",String.valueOf(t.getDaishouk()));
 		sql  = sql.replace("#{dengtongzhi}",String.valueOf(t.getDengtongzhi()));
 		sql  = sql.replace("#{jiage}",String.valueOf(t.getJiage()));
 		sql  = sql.replace("#{xiadanfj}",String.valueOf(t.getXiadanfj()));
 		sql  = sql.replace("#{huidanfj}",String.valueOf(t.getHuidanfj()));
 		sql  = sql.replace("#{zhuangtai}",String.valueOf(t.getZhuangtai()));
 		sql  = sql.replace("#{xdrmz}",String.valueOf(t.getXdrmz()));
 		sql  = sql.replace("#{siji}",String.valueOf(t.getSiji()));
 		sql  = sql.replace("#{sdsj}",String.valueOf(t.getSdsj()));
 		sql  = sql.replace("#{yjsdsj}",String.valueOf(t.getYjsdsj()));
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
					javaInter.execute("tms_yw_dingdan",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}