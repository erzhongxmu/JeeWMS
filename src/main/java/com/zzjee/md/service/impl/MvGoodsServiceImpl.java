package com.zzjee.md.service.impl;
import com.zzjee.md.service.MvGoodsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.md.entity.MvGoodsEntity;
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

@Service("mvGoodsService")
@Transactional
public class MvGoodsServiceImpl extends CommonServiceImpl implements MvGoodsServiceI {

	
 	public void delete(MvGoodsEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(MvGoodsEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(MvGoodsEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(MvGoodsEntity t) throws Exception{
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
	private void doUpdateBus(MvGoodsEntity t) throws Exception{
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
	private void doDelBus(MvGoodsEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(MvGoodsEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cus_code", t.getCusCode());
		map.put("goods_code", t.getGoodsCode());
		map.put("goods_name", t.getGoodsName());
		map.put("shl_dan_wei", t.getShlDanWei());
		map.put("cf_wen_ceng", t.getCfWenCeng());
		map.put("mp_dan_ceng", t.getMpDanCeng());
		map.put("mp_ceng_gao", t.getMpCengGao());
		map.put("shp_tiao_ma", t.getShpTiaoMa());
		map.put("bzhi_qi", t.getBzhiQi());
		map.put("chl_shl", t.getChlShl());
		map.put("ti_ji_cm", t.getTiJiCm());
		map.put("zhl_kg", t.getZhlKg());
		map.put("baseunit", t.getBaseunit());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,MvGoodsEntity t){
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{goods_code}",String.valueOf(t.getGoodsCode()));
 		sql  = sql.replace("#{goods_name}",String.valueOf(t.getGoodsName()));
 		sql  = sql.replace("#{shl_dan_wei}",String.valueOf(t.getShlDanWei()));
 		sql  = sql.replace("#{cf_wen_ceng}",String.valueOf(t.getCfWenCeng()));
 		sql  = sql.replace("#{mp_dan_ceng}",String.valueOf(t.getMpDanCeng()));
 		sql  = sql.replace("#{mp_ceng_gao}",String.valueOf(t.getMpCengGao()));
 		sql  = sql.replace("#{shp_tiao_ma}",String.valueOf(t.getShpTiaoMa()));
 		sql  = sql.replace("#{bzhi_qi}",String.valueOf(t.getBzhiQi()));
 		sql  = sql.replace("#{chl_shl}",String.valueOf(t.getChlShl()));
 		sql  = sql.replace("#{ti_ji_cm}",String.valueOf(t.getTiJiCm()));
 		sql  = sql.replace("#{zhl_kg}",String.valueOf(t.getZhlKg()));
 		sql  = sql.replace("#{baseunit}",String.valueOf(t.getBaseunit()));
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
					javaInter.execute("mv_goods",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}