package com.zzjee.mvyj.service.impl;
import com.zzjee.mvyj.service.MvStockYjServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.mvyj.entity.MvStockYjEntity;
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

@Service("mvStockYjService")
@Transactional
public class MvStockYjServiceImpl extends CommonServiceImpl implements MvStockYjServiceI {

	
 	@Override
    public void delete(MvStockYjEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	@Override
    public Serializable save(MvStockYjEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	@Override
    public void saveOrUpdate(MvStockYjEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(MvStockYjEntity t) throws Exception{
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
	private void doUpdateBus(MvStockYjEntity t) throws Exception{
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
	private void doDelBus(MvStockYjEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}

	/**
	 * 根据给定的 MvStockYjEntity 对象构建一个包含其属性值的映射关系。
	 *
	 * @param t 要处理的 MvStockYjEntity 实体对象
	 * @return 返回一个包含实体属性名和对应值的 Map 对象
	 */
 	private Map<String,Object> populationMap(MvStockYjEntity t){
		//进行存储数据
		Map<String,Object> map = new HashMap<String,Object>(1024);
		//将t对象的属性值逐个储存
		map.put("id", t.getId());
		map.put("kuctype", t.getKuctype());
		map.put("base_goodscount", t.getBaseGoodscount());
		map.put("base_unit", t.getBaseUnit());
		map.put("ku_wei_bian_ma", t.getKuWeiBianMa());
		map.put("bin_id", t.getBinId());
		map.put("cus_code", t.getCusCode());
		map.put("zhong_wen_qch", t.getZhongWenQch());
		map.put("goods_id", t.getGoodsId());
		map.put("shp_ming_cheng", t.getShpMingCheng());
		map.put("goods_pro_data", t.getGoodsProData());
		map.put("bzhi_qi", t.getBzhiQi());
		map.put("dqr", t.getDqr());
		map.put("qu_huo_ci_xu", t.getQuHuoCiXu());
		map.put("shang_jia_ci_xu", t.getShangJiaCiXu());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,MvStockYjEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{kuctype}",String.valueOf(t.getKuctype()));
 		sql  = sql.replace("#{base_goodscount}",String.valueOf(t.getBaseGoodscount()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{ku_wei_bian_ma}",String.valueOf(t.getKuWeiBianMa()));
 		sql  = sql.replace("#{bin_id}",String.valueOf(t.getBinId()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{zhong_wen_qch}",String.valueOf(t.getZhongWenQch()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{shp_ming_cheng}",String.valueOf(t.getShpMingCheng()));
 		sql  = sql.replace("#{goods_pro_data}",String.valueOf(t.getGoodsProData()));
 		sql  = sql.replace("#{bzhi_qi}",String.valueOf(t.getBzhiQi()));
 		sql  = sql.replace("#{dqr}",String.valueOf(t.getDqr()));
 		sql  = sql.replace("#{qu_huo_ci_xu}",String.valueOf(t.getQuHuoCiXu()));
 		sql  = sql.replace("#{shang_jia_ci_xu}",String.valueOf(t.getShangJiaCiXu()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 执行Java扩展功能，根据类型实例化对象并调用相应方法。
	 *
	 * @param cgJavaType  Java扩展类型，可能的值为"class"或"spring"
	 * @param cgJavaValue 要实例化的类的全名或Spring Bean的名称
	 * @param data       传递给执行方法的数据映射
	 * @throws Exception 当执行Java增强出现异常时抛出
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				// 通过MyClassLoader根据类的全名cgJavaValue实例化一个对象
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					//调用javaInter的execute方法，传入参数"mv_stock_yj"和data
					javaInter.execute("mv_stock_yj",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}