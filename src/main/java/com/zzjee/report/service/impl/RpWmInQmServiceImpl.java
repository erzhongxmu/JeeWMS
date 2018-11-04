package com.zzjee.report.service.impl;
import com.zzjee.report.service.RpWmInQmServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.report.entity.RpWmInQmEntity;
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

@Service("rpWmInQmService")
@Transactional
public class RpWmInQmServiceImpl extends CommonServiceImpl implements RpWmInQmServiceI {

	
 	public void delete(RpWmInQmEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(RpWmInQmEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(RpWmInQmEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(RpWmInQmEntity t) throws Exception{
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
	private void doUpdateBus(RpWmInQmEntity t) throws Exception{
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
	private void doDelBus(RpWmInQmEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(RpWmInQmEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_date", t.getCreateDate());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("im_notice_id", t.getImNoticeId());
		map.put("item_text", t.getItemText());
		map.put("rec_deg", t.getRecDeg());
		map.put("ku_wei_bian_ma", t.getKuWeiBianMa());
		map.put("bin_id", t.getBinId());
		map.put("cus_code", t.getCusCode());
		map.put("zhong_wen_qch", t.getZhongWenQch());
		map.put("goods_code", t.getGoodsCode());
		map.put("goods_id", t.getGoodsId());
		map.put("goods_qua", t.getGoodsQua());
		map.put("shp_ming_cheng", t.getShpMingCheng());
		map.put("goods_pro_data", t.getGoodsProData());
		map.put("bzhi_qi", t.getBzhiQi());
		map.put("goods_unit", t.getGoodsUnit());
		map.put("base_unit", t.getBaseUnit());
		map.put("base_goodscount", t.getBaseGoodscount());
		map.put("zhl_kg", t.getZhlKg());
		map.put("sumzhl", t.getSumzhl());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,RpWmInQmEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{im_notice_id}",String.valueOf(t.getImNoticeId()));
 		sql  = sql.replace("#{item_text}",String.valueOf(t.getItemText()));
 		sql  = sql.replace("#{rec_deg}",String.valueOf(t.getRecDeg()));
 		sql  = sql.replace("#{ku_wei_bian_ma}",String.valueOf(t.getKuWeiBianMa()));
 		sql  = sql.replace("#{bin_id}",String.valueOf(t.getBinId()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{zhong_wen_qch}",String.valueOf(t.getZhongWenQch()));
 		sql  = sql.replace("#{goods_code}",String.valueOf(t.getGoodsCode()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{goods_qua}",String.valueOf(t.getGoodsQua()));
 		sql  = sql.replace("#{shp_ming_cheng}",String.valueOf(t.getShpMingCheng()));
 		sql  = sql.replace("#{goods_pro_data}",String.valueOf(t.getGoodsProData()));
 		sql  = sql.replace("#{bzhi_qi}",String.valueOf(t.getBzhiQi()));
 		sql  = sql.replace("#{goods_unit}",String.valueOf(t.getGoodsUnit()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{base_goodscount}",String.valueOf(t.getBaseGoodscount()));
 		sql  = sql.replace("#{zhl_kg}",String.valueOf(t.getZhlKg()));
 		sql  = sql.replace("#{sumzhl}",String.valueOf(t.getSumzhl()));
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
					javaInter.execute("rp_wm_in_qm",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}