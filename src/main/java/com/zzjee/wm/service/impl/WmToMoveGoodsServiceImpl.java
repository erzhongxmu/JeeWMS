package com.zzjee.wm.service.impl;
import com.zzjee.wm.service.WmToMoveGoodsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.wm.entity.WmToMoveGoodsEntity;
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

@Service("wmToMoveGoodsService")
@Transactional
public class WmToMoveGoodsServiceImpl extends CommonServiceImpl implements WmToMoveGoodsServiceI {

	
 	public void delete(WmToMoveGoodsEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WmToMoveGoodsEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WmToMoveGoodsEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WmToMoveGoodsEntity t) throws Exception{
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
	private void doUpdateBus(WmToMoveGoodsEntity t) throws Exception{
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
	private void doDelBus(WmToMoveGoodsEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(WmToMoveGoodsEntity t){
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
		map.put("order_type_code", t.getOrderTypeCode());
		map.put("order_id", t.getOrderId());
		map.put("order_id_i", t.getOrderIdI());
		map.put("goods_id", t.getGoodsId());
		map.put("goods_name", t.getGoodsName());
		map.put("goods_qua", t.getGoodsQua());
		map.put("goods_pro_data", t.getGoodsProData());
		map.put("goods_unit", t.getGoodsUnit());
		map.put("cus_code", t.getCusCode());
		map.put("cus_name", t.getCusName());
		map.put("tin_from", t.getTinFrom());
		map.put("tin_id", t.getTinId());
		map.put("bin_from", t.getBinFrom());
		map.put("bin_to", t.getBinTo());
		map.put("move_sta", t.getMoveSta());
		map.put("to_cus_code", t.getToCusCode());
		map.put("to_cus_name", t.getToCusName());
		map.put("base_unit", t.getBaseUnit());
		map.put("base_goodscount", t.getBaseGoodscount());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WmToMoveGoodsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{order_type_code}",String.valueOf(t.getOrderTypeCode()));
 		sql  = sql.replace("#{order_id}",String.valueOf(t.getOrderId()));
 		sql  = sql.replace("#{order_id_i}",String.valueOf(t.getOrderIdI()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{goods_name}",String.valueOf(t.getGoodsName()));
 		sql  = sql.replace("#{goods_qua}",String.valueOf(t.getGoodsQua()));
 		sql  = sql.replace("#{goods_pro_data}",String.valueOf(t.getGoodsProData()));
 		sql  = sql.replace("#{goods_unit}",String.valueOf(t.getGoodsUnit()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{cus_name}",String.valueOf(t.getCusName()));
 		sql  = sql.replace("#{tin_from}",String.valueOf(t.getTinFrom()));
 		sql  = sql.replace("#{tin_id}",String.valueOf(t.getTinId()));
 		sql  = sql.replace("#{bin_from}",String.valueOf(t.getBinFrom()));
 		sql  = sql.replace("#{bin_to}",String.valueOf(t.getBinTo()));
 		sql  = sql.replace("#{move_sta}",String.valueOf(t.getMoveSta()));
 		sql  = sql.replace("#{to_cus_code}",String.valueOf(t.getToCusCode()));
 		sql  = sql.replace("#{to_cus_name}",String.valueOf(t.getToCusName()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{base_goodscount}",String.valueOf(t.getBaseGoodscount()));
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
					javaInter.execute("wm_to_move_goods",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}