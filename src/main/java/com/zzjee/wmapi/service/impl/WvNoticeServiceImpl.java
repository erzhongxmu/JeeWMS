package com.zzjee.wmapi.service.impl;
import com.zzjee.wmapi.service.WvNoticeServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.wmapi.entity.WvNoticeEntity;
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

@Service("wvNoticeService")
@Transactional
public class WvNoticeServiceImpl extends CommonServiceImpl implements WvNoticeServiceI {

	
 	public void delete(WvNoticeEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WvNoticeEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WvNoticeEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WvNoticeEntity t) throws Exception{
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
	private void doUpdateBus(WvNoticeEntity t) throws Exception{
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
	private void doDelBus(WvNoticeEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(WvNoticeEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("notice_id", t.getNoticeId());
		map.put("cus_code", t.getCusCode());
		map.put("zhong_wen_qch", t.getZhongWenQch());
		map.put("im_sta", t.getImSta());
		map.put("goods_code", t.getGoodsCode());
		map.put("shp_ming_cheng", t.getShpMingCheng());
		map.put("shp_tiao_ma", t.getShpTiaoMa());
		map.put("cf_wen_ceng", t.getCfWenCeng());
		map.put("mp_dan_ceng", t.getMpDanCeng());
		map.put("mp_ceng_gao", t.getMpCengGao());
		map.put("ti_ji_cm", t.getTiJiCm());
		map.put("goods_count", t.getGoodsCount());
		map.put("goods_qm_count", t.getGoodsQmCount());
		map.put("gr_count", t.getGrCount());
		map.put("shl_dan_wei", t.getShlDanWei());
		map.put("goods_fvol", t.getGoodsFvol());
		map.put("goods_weight", t.getGoodsWeight());
		map.put("lastgrdate", t.getLastgrdate());
		map.put("preprodate", t.getPreprodate());
		map.put("rec_deg", t.getRecDeg());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WvNoticeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{notice_id}",String.valueOf(t.getNoticeId()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{zhong_wen_qch}",String.valueOf(t.getZhongWenQch()));
 		sql  = sql.replace("#{im_sta}",String.valueOf(t.getImSta()));
 		sql  = sql.replace("#{goods_code}",String.valueOf(t.getGoodsCode()));
 		sql  = sql.replace("#{shp_ming_cheng}",String.valueOf(t.getShpMingCheng()));
 		sql  = sql.replace("#{shp_tiao_ma}",String.valueOf(t.getShpTiaoMa()));
 		sql  = sql.replace("#{cf_wen_ceng}",String.valueOf(t.getCfWenCeng()));
 		sql  = sql.replace("#{mp_dan_ceng}",String.valueOf(t.getMpDanCeng()));
 		sql  = sql.replace("#{mp_ceng_gao}",String.valueOf(t.getMpCengGao()));
 		sql  = sql.replace("#{ti_ji_cm}",String.valueOf(t.getTiJiCm()));
 		sql  = sql.replace("#{goods_count}",String.valueOf(t.getGoodsCount()));
 		sql  = sql.replace("#{goods_qm_count}",String.valueOf(t.getGoodsQmCount()));
 		sql  = sql.replace("#{gr_count}",String.valueOf(t.getGrCount()));
 		sql  = sql.replace("#{shl_dan_wei}",String.valueOf(t.getShlDanWei()));
 		sql  = sql.replace("#{goods_fvol}",String.valueOf(t.getGoodsFvol()));
 		sql  = sql.replace("#{goods_weight}",String.valueOf(t.getGoodsWeight()));
 		sql  = sql.replace("#{lastgrdate}",String.valueOf(t.getLastgrdate()));
 		sql  = sql.replace("#{preprodate}",String.valueOf(t.getPreprodate()));
 		sql  = sql.replace("#{rec_deg}",String.valueOf(t.getRecDeg()));
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
					javaInter.execute("wv_notice",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}