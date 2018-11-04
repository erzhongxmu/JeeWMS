package com.zzjee.wv.service.impl;
import com.zzjee.wv.service.MvStockCusServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.wv.entity.MvStockCusEntity;
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

@Service("mvStockCusService")
@Transactional
public class MvStockCusServiceImpl extends CommonServiceImpl implements MvStockCusServiceI {

	
 	public void delete(MvStockCusEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(MvStockCusEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(MvStockCusEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(MvStockCusEntity t) throws Exception{
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
	private void doUpdateBus(MvStockCusEntity t) throws Exception{
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
	private void doDelBus(MvStockCusEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(MvStockCusEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("kuctype", t.getKuctype());
		map.put("goods_qua", t.getGoodsQua());
		map.put("goods_unit", t.getGoodsUnit());
		map.put("base_goodscount", t.getBaseGoodscount());
		map.put("base_unit", t.getBaseUnit());
		map.put("ku_wei_bian_ma", t.getKuWeiBianMa());
		map.put("bin_id", t.getBinId());
		map.put("cus_code", t.getCusCode());
		map.put("zhong_wen_qch", t.getZhongWenQch());
		map.put("goods_id", t.getGoodsId());
		map.put("shp_ming_cheng", t.getShpMingCheng());
		map.put("shl_dan_wei", t.getShlDanWei());
		map.put("goods_pro_data", t.getGoodsProData());
		map.put("bzhi_qi", t.getBzhiQi());
		map.put("qu_huo_ci_xu", t.getQuHuoCiXu());
		map.put("shang_jia_ci_xu", t.getShangJiaCiXu());
		map.put("dqr", t.getDqr());
		map.put("hiti", t.getHiti());
		map.put("ku_wei_lei_xing", t.getKuWeiLeiXing());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,MvStockCusEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{kuctype}",String.valueOf(t.getKuctype()));
 		sql  = sql.replace("#{goods_qua}",String.valueOf(t.getGoodsQua()));
 		sql  = sql.replace("#{goods_unit}",String.valueOf(t.getGoodsUnit()));
 		sql  = sql.replace("#{base_goodscount}",String.valueOf(t.getBaseGoodscount()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{ku_wei_bian_ma}",String.valueOf(t.getKuWeiBianMa()));
 		sql  = sql.replace("#{bin_id}",String.valueOf(t.getBinId()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{zhong_wen_qch}",String.valueOf(t.getZhongWenQch()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{shp_ming_cheng}",String.valueOf(t.getShpMingCheng()));
 		sql  = sql.replace("#{shl_dan_wei}",String.valueOf(t.getShlDanWei()));
 		sql  = sql.replace("#{goods_pro_data}",String.valueOf(t.getGoodsProData()));
 		sql  = sql.replace("#{bzhi_qi}",String.valueOf(t.getBzhiQi()));
 		sql  = sql.replace("#{qu_huo_ci_xu}",String.valueOf(t.getQuHuoCiXu()));
 		sql  = sql.replace("#{shang_jia_ci_xu}",String.valueOf(t.getShangJiaCiXu()));
 		sql  = sql.replace("#{dqr}",String.valueOf(t.getDqr()));
 		sql  = sql.replace("#{hiti}",String.valueOf(t.getHiti()));
 		sql  = sql.replace("#{ku_wei_lei_xing}",String.valueOf(t.getKuWeiLeiXing()));
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
					javaInter.execute("mv_stock_cus",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}