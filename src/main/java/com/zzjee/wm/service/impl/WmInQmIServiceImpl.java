package com.zzjee.wm.service.impl;
import com.zzjee.wm.service.WmInQmIServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.wm.entity.WmInQmIEntity;
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

@Service("wmInQmIService")
@Transactional
public class WmInQmIServiceImpl extends CommonServiceImpl implements WmInQmIServiceI {

	
 	public void delete(WmInQmIEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WmInQmIEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WmInQmIEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WmInQmIEntity t) throws Exception{
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
	private void doUpdateBus(WmInQmIEntity t) throws Exception{
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
	private void doDelBus(WmInQmIEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(WmInQmIEntity t){
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
		map.put("im_notice_id", t.getImNoticeId());
		map.put("im_notice_item", t.getImNoticeItem());
		map.put("goods_id", t.getGoodsId());
		map.put("im_quat", t.getImQuat());
		map.put("qm_ok_quat", t.getQmOkQuat());
		map.put("item_text", t.getItemText());
		map.put("pro_data", t.getProData());
		map.put("tin_id", t.getTinId());
		map.put("goods_unit", t.getGoodsUnit());
		map.put("goods_batch", t.getGoodsBatch());
		map.put("bin_id", t.getBinId());
		map.put("tin_tj", t.getTinTj());
		map.put("tin_zhl", t.getTinZhl());
		map.put("bin_sta", t.getBinSta());
		map.put("cus_code", t.getCusCode());
		map.put("rec_deg", t.getRecDeg());
		map.put("base_unit", t.getBaseUnit());
		map.put("base_goodscount", t.getBaseGoodscount());
		map.put("base_qmcount", t.getBaseQmcount());
		map.put("cus_name", t.getCusName());
		map.put("goods_name", t.getGoodsName());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WmInQmIEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{im_notice_id}",String.valueOf(t.getImNoticeId()));
 		sql  = sql.replace("#{im_notice_item}",String.valueOf(t.getImNoticeItem()));
 		sql  = sql.replace("#{goods_id}",String.valueOf(t.getGoodsId()));
 		sql  = sql.replace("#{im_quat}",String.valueOf(t.getImQuat()));
 		sql  = sql.replace("#{qm_ok_quat}",String.valueOf(t.getQmOkQuat()));
 		sql  = sql.replace("#{item_text}",String.valueOf(t.getItemText()));
 		sql  = sql.replace("#{pro_data}",String.valueOf(t.getProData()));
 		sql  = sql.replace("#{tin_id}",String.valueOf(t.getTinId()));
 		sql  = sql.replace("#{goods_unit}",String.valueOf(t.getGoodsUnit()));
 		sql  = sql.replace("#{goods_batch}",String.valueOf(t.getGoodsBatch()));
 		sql  = sql.replace("#{bin_id}",String.valueOf(t.getBinId()));
 		sql  = sql.replace("#{tin_tj}",String.valueOf(t.getTinTj()));
 		sql  = sql.replace("#{tin_zhl}",String.valueOf(t.getTinZhl()));
 		sql  = sql.replace("#{bin_sta}",String.valueOf(t.getBinSta()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{rec_deg}",String.valueOf(t.getRecDeg()));
 		sql  = sql.replace("#{base_unit}",String.valueOf(t.getBaseUnit()));
 		sql  = sql.replace("#{base_goodscount}",String.valueOf(t.getBaseGoodscount()));
 		sql  = sql.replace("#{base_qmcount}",String.valueOf(t.getBaseQmcount()));
 		sql  = sql.replace("#{cus_name}",String.valueOf(t.getCusName()));
 		sql  = sql.replace("#{goods_name}",String.valueOf(t.getGoodsName()));
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
					javaInter.execute("wm_in_qm_i",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}