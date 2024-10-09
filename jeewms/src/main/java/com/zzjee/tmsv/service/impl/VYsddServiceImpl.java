package com.zzjee.tmsv.service.impl;

import com.zzjee.tmsv.entity.VYsddEntity;
import com.zzjee.tmsv.service.VYsddServiceI;
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

@Service("vYsddService")
@Transactional
public class VYsddServiceImpl extends CommonServiceImpl implements VYsddServiceI {

	
 	@Override
    public void delete(VYsddEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	@Override
    public Serializable save(VYsddEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	@Override
    public void saveOrUpdate(VYsddEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(VYsddEntity t) throws Exception{
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
	private void doUpdateBus(VYsddEntity t) throws Exception{
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
	private void doDelBus(VYsddEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(VYsddEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_date", t.getCreateDate());
		map.put("fahuoren", t.getFahuoren());
		map.put("huowu", t.getHuowu());
		map.put("hwshjs", t.getHwshjs());
		map.put("zhongl", t.getZhongl());
		map.put("tiji", t.getTiji());
		map.put("shrdh", t.getShrdh());
		map.put("shouhuoren", t.getShouhuoren());
		map.put("hwshfs", t.getHwshfs());
		map.put("shrsj", t.getShrsj());
		map.put("daishouk", t.getDaishouk());
		map.put("hwyf", t.getHwyf());
		map.put("hwzfy", t.getHwzfy());
		map.put("hwxhf", t.getHwxhf());
		map.put("chehao", t.getChehao());
		map.put("zhuangtai", t.getZhuangtai());
		map.put("ywhdbz", t.getYwhdbz());
		map.put("sdsj", t.getSdsj());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,VYsddEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{fahuoren}",String.valueOf(t.getFahuoren()));
 		sql  = sql.replace("#{huowu}",String.valueOf(t.getHuowu()));
 		sql  = sql.replace("#{hwshjs}",String.valueOf(t.getHwshjs()));
 		sql  = sql.replace("#{zhongl}",String.valueOf(t.getZhongl()));
 		sql  = sql.replace("#{tiji}",String.valueOf(t.getTiji()));
 		sql  = sql.replace("#{shrdh}",String.valueOf(t.getShrdh()));
 		sql  = sql.replace("#{shouhuoren}",String.valueOf(t.getShouhuoren()));
 		sql  = sql.replace("#{hwshfs}",String.valueOf(t.getHwshfs()));
 		sql  = sql.replace("#{shrsj}",String.valueOf(t.getShrsj()));
 		sql  = sql.replace("#{daishouk}",String.valueOf(t.getDaishouk()));
 		sql  = sql.replace("#{hwyf}",String.valueOf(t.getHwyf()));
 		sql  = sql.replace("#{hwzfy}",String.valueOf(t.getHwzfy()));
 		sql  = sql.replace("#{hwxhf}",String.valueOf(t.getHwxhf()));
 		sql  = sql.replace("#{chehao}",String.valueOf(t.getChehao()));
 		sql  = sql.replace("#{zhuangtai}",String.valueOf(t.getZhuangtai()));
 		sql  = sql.replace("#{ywhdbz}",String.valueOf(t.getYwhdbz()));
 		sql  = sql.replace("#{sdsj}",String.valueOf(t.getSdsj()));
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
					javaInter.execute("v_ysdd",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}