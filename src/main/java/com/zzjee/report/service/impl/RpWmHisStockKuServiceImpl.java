package com.zzjee.report.service.impl;
import com.zzjee.report.service.RpWmHisStockKuServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.report.entity.RpWmHisStockKuEntity;
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

@Service("rpWmHisStockKuService")
@Transactional
public class RpWmHisStockKuServiceImpl extends CommonServiceImpl implements RpWmHisStockKuServiceI {

	
 	public void delete(RpWmHisStockKuEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(RpWmHisStockKuEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(RpWmHisStockKuEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(RpWmHisStockKuEntity t) throws Exception{
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
	private void doUpdateBus(RpWmHisStockKuEntity t) throws Exception{
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
	private void doDelBus(RpWmHisStockKuEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(RpWmHisStockKuEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("his_date", t.getHisDate());
		map.put("cus_code", t.getCusCode());
		map.put("zhong_wen_qch", t.getZhongWenQch());
		map.put("ku_wei_bian_ma", t.getKuWeiBianMa());
		map.put("bin_id", t.getBinId());
		map.put("goods_id", t.getGoodsId());
		map.put("shp_ming_cheng", t.getShpMingCheng());
		map.put("count", t.getCount());
		map.put("base_unit", t.getBaseUnit());
		map.put("zhl_kg", t.getZhlKg());
		map.put("chl_shl", t.getChlShl());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,RpWmHisStockKuEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{his_date}",String.valueOf(t.getHisDate()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{zhong_wen_qch}",String.valueOf(t.getZhongWenQch()));
 		sql  = sql.replace("#{ku_wei_bian_ma}",String.valueOf(t.getKuWeiBianMa()));
 		sql  = sql.replace("#{bin_id}",String.valueOf(t.getBinId()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{shp_ming_cheng}",String.valueOf(t.getShpMingCheng()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{zhl_kg}",String.valueOf(t.getZhlKg()));
 		sql  = sql.replace("#{chl_shl}",String.valueOf(t.getChlShl()));
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
					javaInter.execute("rp_wm_his_stock_ku",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}