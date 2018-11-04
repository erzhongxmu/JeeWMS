package com.zzjee.md.service.impl;
import com.zzjee.md.service.MdBinServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.zzjee.md.entity.MdBinEntity;

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

@Service("mdBinService")
@Transactional
public class MdBinServiceImpl extends CommonServiceImpl implements MdBinServiceI {

	
 	public void delete(MdBinEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(MdBinEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(MdBinEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(MdBinEntity t) throws Exception{
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
	private void doUpdateBus(MdBinEntity t) throws Exception{
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
	private void doDelBus(MdBinEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(MdBinEntity t){
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
		map.put("ku_wei_ming_cheng", t.getKuWeiMingCheng());
		map.put("ku_wei_bian_ma", t.getKuWeiBianMa());
		map.put("ku_wei_tiao_ma", t.getKuWeiTiaoMa());
		map.put("ku_wei_lei_xing", t.getKuWeiLeiXing());
		map.put("chp_shu_xing", t.getChpShuXing());
		map.put("ku_wei_shu_xing", t.getKuWeiShuXing());
		map.put("shang_jia_ci_xu", t.getShangJiaCiXu());
		map.put("qu_huo_ci_xu", t.getQuHuoCiXu());
		map.put("suo_shu_ke_hu", t.getSuoShuKeHu());
		map.put("ti_ji_dan_wei", t.getTiJiDanWei());
		map.put("zhong_liang_dan_wei", t.getZhongLiangDanWei());
		map.put("mian_ji_dan_wei", t.getMianJiDanWei());
		map.put("zui_da_ti_ji", t.getZuiDaTiJi());
		map.put("zui_da_zhong_liang", t.getZuiDaZhongLiang());
		map.put("zui_da_mian_ji", t.getZuiDaMianJi());
		map.put("zui_da_tuo_pan", t.getZuiDaTuoPan());
		map.put("chang", t.getChang());
		map.put("kuan", t.getKuan());
		map.put("gao", t.getGao());
		map.put("ting_yong", t.getTingYong());
		map.put("ming_xi", t.getMingXi());
		map.put("ming_xi1", t.getMingXi1());
		map.put("ming_xi2", t.getMingXi2());
		map.put("ming_xi3", t.getMingXi3());
		map.put("bin_store", t.getBinStore());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,MdBinEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{ku_wei_ming_cheng}",String.valueOf(t.getKuWeiMingCheng()));
 		sql  = sql.replace("#{ku_wei_bian_ma}",String.valueOf(t.getKuWeiBianMa()));
 		sql  = sql.replace("#{ku_wei_tiao_ma}",String.valueOf(t.getKuWeiTiaoMa()));
 		sql  = sql.replace("#{ku_wei_lei_xing}",String.valueOf(t.getKuWeiLeiXing()));
 		sql  = sql.replace("#{chp_shu_xing}",String.valueOf(t.getChpShuXing()));

 		sql  = sql.replace("#{ku_wei_shu_xing}",String.valueOf(t.getKuWeiShuXing()));
 		sql  = sql.replace("#{shang_jia_ci_xu}",String.valueOf(t.getShangJiaCiXu()));
 		sql  = sql.replace("#{qu_huo_ci_xu}",String.valueOf(t.getQuHuoCiXu()));
 		sql  = sql.replace("#{suo_shu_ke_hu}",String.valueOf(t.getSuoShuKeHu()));
 		sql  = sql.replace("#{ti_ji_dan_wei}",String.valueOf(t.getTiJiDanWei()));
 		sql  = sql.replace("#{zhong_liang_dan_wei}",String.valueOf(t.getZhongLiangDanWei()));
 		sql  = sql.replace("#{mian_ji_dan_wei}",String.valueOf(t.getMianJiDanWei()));
 		sql  = sql.replace("#{zui_da_ti_ji}",String.valueOf(t.getZuiDaTiJi()));
 		sql  = sql.replace("#{zui_da_zhong_liang}",String.valueOf(t.getZuiDaZhongLiang()));
 		sql  = sql.replace("#{zui_da_mian_ji}",String.valueOf(t.getZuiDaMianJi()));
 		sql  = sql.replace("#{zui_da_tuo_pan}",String.valueOf(t.getZuiDaTuoPan()));
 		sql  = sql.replace("#{chang}",String.valueOf(t.getChang()));
 		sql  = sql.replace("#{kuan}",String.valueOf(t.getKuan()));
 		sql  = sql.replace("#{gao}",String.valueOf(t.getGao()));
 		sql  = sql.replace("#{ting_yong}",String.valueOf(t.getTingYong()));
 		sql  = sql.replace("#{ming_xi}",String.valueOf(t.getMingXi()));
 		sql  = sql.replace("#{ming_xi1}",String.valueOf(t.getMingXi1()));
 		sql  = sql.replace("#{ming_xi2}",String.valueOf(t.getMingXi2()));
 		sql  = sql.replace("#{ming_xi3}",String.valueOf(t.getMingXi3()));
 		sql  = sql.replace("#{bin_store}",String.valueOf(t.getBinStore()));
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
					javaInter.execute("md_bin",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}