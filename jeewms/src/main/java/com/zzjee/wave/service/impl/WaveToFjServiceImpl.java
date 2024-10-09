package com.zzjee.wave.service.impl;
import com.zzjee.wave.service.WaveToFjServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.wave.entity.WaveToFjEntity;
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

@Service("waveToFjService")
@Transactional
public class WaveToFjServiceImpl extends CommonServiceImpl implements WaveToFjServiceI {

	
 	@Override
    public void delete(WaveToFjEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	@Override
    public Serializable save(WaveToFjEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	@Override
    public void saveOrUpdate(WaveToFjEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WaveToFjEntity t) throws Exception{
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
	private void doUpdateBus(WaveToFjEntity t) throws Exception{
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
	private void doDelBus(WaveToFjEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(WaveToFjEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_by", t.getCreateBy());
		map.put("create_name", t.getCreateName());
		map.put("cus_code", t.getCusCode());
		map.put("cus_name", t.getCusName());
		map.put("om_notice_id", t.getOmNoticeId());
		map.put("iom_notice_item", t.getIomNoticeItem());
		map.put("goods_id", t.getGoodsId());
		map.put("goods_name", t.getGoodsName());
		map.put("bin_id", t.getBinId());
		map.put("tin_id", t.getTinId());
		map.put("pro_data", t.getProData());
		map.put("base_goodscount", t.getBaseGoodscount());
		map.put("base_unit", t.getBaseUnit());
		map.put("wave_id", t.getWaveId());
		map.put("first_rq", t.getFirstRq());
		map.put("second_rq", t.getSecondRq());
		map.put("by1", t.getBy1());
		map.put("by2", t.getBy2());
		map.put("by3", t.getBy3());
		map.put("by4", t.getBy4());
		map.put("by5", t.getBy5());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WaveToFjEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{cus_name}",String.valueOf(t.getCusName()));
 		sql  = sql.replace("#{om_notice_id}",String.valueOf(t.getOmNoticeId()));
 		sql  = sql.replace("#{iom_notice_item}",String.valueOf(t.getIomNoticeItem()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{goods_name}",String.valueOf(t.getGoodsName()));
 		sql  = sql.replace("#{bin_id}",String.valueOf(t.getBinId()));
 		sql  = sql.replace("#{tin_id}",String.valueOf(t.getTinId()));
 		sql  = sql.replace("#{pro_data}",String.valueOf(t.getProData()));
 		sql  = sql.replace("#{base_goodscount}",String.valueOf(t.getBaseGoodscount()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{wave_id}",String.valueOf(t.getWaveId()));
 		sql  = sql.replace("#{first_rq}",String.valueOf(t.getFirstRq()));
 		sql  = sql.replace("#{second_rq}",String.valueOf(t.getSecondRq()));
 		sql  = sql.replace("#{by1}",String.valueOf(t.getBy1()));
 		sql  = sql.replace("#{by2}",String.valueOf(t.getBy2()));
 		sql  = sql.replace("#{by3}",String.valueOf(t.getBy3()));
 		sql  = sql.replace("#{by4}",String.valueOf(t.getBy4()));
 		sql  = sql.replace("#{by5}",String.valueOf(t.getBy5()));
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
					javaInter.execute("wave_to_fj",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}