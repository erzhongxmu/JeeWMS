package com.zzjee.md.service.impl;
import com.zzjee.md.service.MdGoodsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.md.entity.MdGoodsEntity;
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

@Service("mdGoodsService")
@Transactional
public class MdGoodsServiceImpl extends CommonServiceImpl implements MdGoodsServiceI {

	
 	public void delete(MdGoodsEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(MdGoodsEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(MdGoodsEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(MdGoodsEntity t) throws Exception{
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
	private void doUpdateBus(MdGoodsEntity t) throws Exception{
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
	private void doDelBus(MdGoodsEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(MdGoodsEntity t){
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
		map.put("suo_shu_ke_hu", t.getSuoShuKeHu());
		map.put("shp_ming_cheng", t.getShpMingCheng());
		map.put("shp_jian_cheng", t.getShpJianCheng());
		map.put("shp_bian_ma", t.getShpBianMa());
		map.put("shp_xing_hao", t.getShpXingHao());
		map.put("shp_gui_ge", t.getShpGuiGe());
		map.put("shp_yan_se", t.getShpYanSe());
		map.put("chp_shu_xing", t.getChpShuXing());
		map.put("cf_wen_ceng", t.getCfWenCeng());
		map.put("chl_kong_zhi", t.getChlKongZhi());
		map.put("mp_dan_ceng", t.getMpDanCeng());
		map.put("mp_ceng_gao", t.getMpCengGao());
		map.put("jf_shp_lei", t.getJfShpLei());
		map.put("shp_pin_pai", t.getShpPinPai());
		map.put("shp_tiao_ma", t.getShpTiaoMa());
		map.put("pp_tu_pian", t.getPpTuPian());
		map.put("bzhi_qi", t.getBzhiQi());
		map.put("shl_dan_wei", t.getShlDanWei());
		map.put("jsh_dan_wei", t.getJshDanWei());
		map.put("ti_ji_cm", t.getTiJiCm());
		map.put("zhl_kg", t.getZhlKg());
		map.put("zhl_kgm", t.getZhlKgm());
		map.put("chl_shl", t.getChlShl());
		map.put("jti_ji_bi", t.getJtiJiBi());
		map.put("jm_zhong_bi", t.getJmZhongBi());
		map.put("jj_zhong_bi", t.getJjZhongBi());
		map.put("chc_dan_wei", t.getChcDanWei());
		map.put("ch_dan_pin", t.getChDanPin());
		map.put("ku_dan_pin", t.getKuDanPin());
		map.put("gao_dan_pin", t.getGaoDanPin());
		map.put("ch_zh_xiang", t.getChZhXiang());
		map.put("ku_zh_xiang", t.getKuZhXiang());
		map.put("gao_zh_xiang", t.getGaoZhXiang());
		map.put("shp_miao_shu", t.getShpMiaoShu());
		map.put("zhuang_tai", t.getZhuangTai());
		map.put("jizhun_wendu", t.getJiZhunwendu());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,MdGoodsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{suo_shu_ke_hu}",String.valueOf(t.getSuoShuKeHu()));
 		sql  = sql.replace("#{shp_ming_cheng}",String.valueOf(t.getShpMingCheng()));
 		sql  = sql.replace("#{shp_jian_cheng}",String.valueOf(t.getShpJianCheng()));
 		sql  = sql.replace("#{shp_bian_ma}",String.valueOf(t.getShpBianMa()));
 		sql  = sql.replace("#{shp_xing_hao}",String.valueOf(t.getShpXingHao()));
 		sql  = sql.replace("#{shp_gui_ge}",String.valueOf(t.getShpGuiGe()));
 		sql  = sql.replace("#{shp_yan_se}",String.valueOf(t.getShpYanSe()));
 		sql  = sql.replace("#{chp_shu_xing}",String.valueOf(t.getChpShuXing()));
 		sql  = sql.replace("#{cf_wen_ceng}",String.valueOf(t.getCfWenCeng()));
 		sql  = sql.replace("#{chl_kong_zhi}",String.valueOf(t.getChlKongZhi()));
 		sql  = sql.replace("#{mp_dan_ceng}",String.valueOf(t.getMpDanCeng()));
 		sql  = sql.replace("#{mp_ceng_gao}",String.valueOf(t.getMpCengGao()));
 		sql  = sql.replace("#{jf_shp_lei}",String.valueOf(t.getJfShpLei()));
 		sql  = sql.replace("#{shp_pin_pai}",String.valueOf(t.getShpPinPai()));
 		sql  = sql.replace("#{shp_tiao_ma}",String.valueOf(t.getShpTiaoMa()));
 		sql  = sql.replace("#{pp_tu_pian}",String.valueOf(t.getPpTuPian()));
 		sql  = sql.replace("#{bzhi_qi}",String.valueOf(t.getBzhiQi()));
 		sql  = sql.replace("#{shl_dan_wei}",String.valueOf(t.getShlDanWei()));
 		sql  = sql.replace("#{jsh_dan_wei}",String.valueOf(t.getJshDanWei()));
 		sql  = sql.replace("#{ti_ji_cm}",String.valueOf(t.getTiJiCm()));
 		sql  = sql.replace("#{zhl_kg}",String.valueOf(t.getZhlKg()));
 		sql  = sql.replace("#{zhl_kgm}",String.valueOf(t.getZhlKgm()));
 		sql  = sql.replace("#{chl_shl}",String.valueOf(t.getChlShl()));
 		sql  = sql.replace("#{jti_ji_bi}",String.valueOf(t.getJtiJiBi()));
 		sql  = sql.replace("#{jm_zhong_bi}",String.valueOf(t.getJmZhongBi()));
 		sql  = sql.replace("#{jj_zhong_bi}",String.valueOf(t.getJjZhongBi()));
 		sql  = sql.replace("#{chc_dan_wei}",String.valueOf(t.getChcDanWei()));
 		sql  = sql.replace("#{ch_dan_pin}",String.valueOf(t.getChDanPin()));
 		sql  = sql.replace("#{ku_dan_pin}",String.valueOf(t.getKuDanPin()));
 		sql  = sql.replace("#{gao_dan_pin}",String.valueOf(t.getGaoDanPin()));
 		sql  = sql.replace("#{ch_zh_xiang}",String.valueOf(t.getChZhXiang()));
 		sql  = sql.replace("#{ku_zh_xiang}",String.valueOf(t.getKuZhXiang()));
 		sql  = sql.replace("#{gao_zh_xiang}",String.valueOf(t.getGaoZhXiang()));
 		sql  = sql.replace("#{shp_miao_shu}",String.valueOf(t.getShpMiaoShu()));
 		sql  = sql.replace("#{zhuang_tai}",String.valueOf(t.getZhuangTai()));
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
					javaInter.execute("md_goods",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}