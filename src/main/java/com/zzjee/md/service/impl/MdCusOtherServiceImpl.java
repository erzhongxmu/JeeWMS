package com.zzjee.md.service.impl;
import com.zzjee.md.service.MdCusOtherServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.md.entity.MdCusOtherEntity;
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

@Service("mdCusOtherService")
@Transactional
public class MdCusOtherServiceImpl extends CommonServiceImpl implements MdCusOtherServiceI {

	
 	public void delete(MdCusOtherEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(MdCusOtherEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(MdCusOtherEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(MdCusOtherEntity t) throws Exception{
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
	private void doUpdateBus(MdCusOtherEntity t) throws Exception{
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
	private void doDelBus(MdCusOtherEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(MdCusOtherEntity t){
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
		map.put("zhong_wen_qch", t.getZhongWenQch());
		map.put("zhu_ji_ma", t.getZhuJiMa());
		map.put("ke_hu_jian_cheng", t.getKeHuJianCheng());
		map.put("ke_hu_bian_ma", t.getKeHuBianMa());
		map.put("ke_hu_ying_wen", t.getKeHuYingWen());
		map.put("zeng_yong_qi", t.getZengYongQi());
		map.put("zeng_yong_qi_ye", t.getZengYongQiYe());
		map.put("ke_hu_zhuang_tai", t.getKeHuZhuangTai());
		map.put("xing_ye_fen_lei", t.getXingYeFenLei());
		map.put("ke_hu_deng_ji", t.getKeHuDengJi());
		map.put("suo_shu_xing_ye", t.getSuoShuXingYe());
		map.put("shou_qian_ri_qi", t.getShouQianRiQi());
		map.put("zhong_zhi_he_shi_jian", t.getZhongZhiHeShiJian());
		map.put("shen_qing_shi_jian", t.getShenQingShiJian());
		map.put("ke_hu_shu_xing", t.getKeHuShuXing());
		map.put("gui_shu_zu_zh", t.getGuiShuZuZh());
		map.put("gui_shu_sheng", t.getGuiShuSheng());
		map.put("gui_shu_shi_dai", t.getGuiShuShiDai());
		map.put("gui_shu", t.getGuiShu());
		map.put("di_zhi", t.getDiZhi());
		map.put("you_zheng_bian_ma", t.getYouZhengBianMa());
		map.put("zhu_lian_xi_ren", t.getZhuLianXiRen());
		map.put("dian_hua", t.getDianHua());
		map.put("shou_ji", t.getShouJi());
		map.put("chuan_zhen", t.getChuanZhen());
		map.put("emaildi_zhi", t.getEmaildiZhi());
		map.put("wang_ye_di_zhi", t.getWangYeDiZhi());
		map.put("fa_ren_dai_biao", t.getFaRenDaiBiao());
		map.put("fa_ren_shen_fen", t.getFaRenShenFen());
		map.put("zhu_ce_zi_jin", t.getZhuCeZiJin());
		map.put("bi_bie", t.getBiBie());
		map.put("ying_ye_zhi_zhao", t.getYingYeZhiZhao());
		map.put("shui_wu_deng", t.getShuiWuDeng());
		map.put("zu_zhi_ji_gou", t.getZuZhiJiGou());
		map.put("dao_lu_yun_shu", t.getDaoLuYunShu());
		map.put("zhu_ying_ye_wu", t.getZhuYingYeWu());
		map.put("he_yi_xiang", t.getHeYiXiang());
		map.put("pi_zhun_ji_guan", t.getPiZhunJiGuan());
		map.put("pi_zhun_wen_hao", t.getPiZhunWenHao());
		map.put("zhu_ce_ri_qi", t.getZhuCeRiQi());
		map.put("bei_zhu", t.getBeiZhu());
		map.put("zhu_lian_xi_ren1", t.getZhuLianXiRen1());
		map.put("dian_hua1", t.getDianHua1());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,MdCusOtherEntity t){
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
 		sql  = sql.replace("#{zhong_wen_qch}",String.valueOf(t.getZhongWenQch()));
 		sql  = sql.replace("#{zhu_ji_ma}",String.valueOf(t.getZhuJiMa()));
 		sql  = sql.replace("#{ke_hu_jian_cheng}",String.valueOf(t.getKeHuJianCheng()));
 		sql  = sql.replace("#{ke_hu_bian_ma}",String.valueOf(t.getKeHuBianMa()));
 		sql  = sql.replace("#{ke_hu_ying_wen}",String.valueOf(t.getKeHuYingWen()));
 		sql  = sql.replace("#{zeng_yong_qi}",String.valueOf(t.getZengYongQi()));
 		sql  = sql.replace("#{zeng_yong_qi_ye}",String.valueOf(t.getZengYongQiYe()));
 		sql  = sql.replace("#{ke_hu_zhuang_tai}",String.valueOf(t.getKeHuZhuangTai()));
 		sql  = sql.replace("#{xing_ye_fen_lei}",String.valueOf(t.getXingYeFenLei()));
 		sql  = sql.replace("#{ke_hu_deng_ji}",String.valueOf(t.getKeHuDengJi()));
 		sql  = sql.replace("#{suo_shu_xing_ye}",String.valueOf(t.getSuoShuXingYe()));
 		sql  = sql.replace("#{shou_qian_ri_qi}",String.valueOf(t.getShouQianRiQi()));
 		sql  = sql.replace("#{zhong_zhi_he_shi_jian}",String.valueOf(t.getZhongZhiHeShiJian()));
 		sql  = sql.replace("#{shen_qing_shi_jian}",String.valueOf(t.getShenQingShiJian()));
 		sql  = sql.replace("#{ke_hu_shu_xing}",String.valueOf(t.getKeHuShuXing()));
 		sql  = sql.replace("#{gui_shu_zu_zh}",String.valueOf(t.getGuiShuZuZh()));
 		sql  = sql.replace("#{gui_shu_sheng}",String.valueOf(t.getGuiShuSheng()));
 		sql  = sql.replace("#{gui_shu_shi_dai}",String.valueOf(t.getGuiShuShiDai()));
 		sql  = sql.replace("#{gui_shu}",String.valueOf(t.getGuiShu()));
 		sql  = sql.replace("#{di_zhi}",String.valueOf(t.getDiZhi()));
 		sql  = sql.replace("#{you_zheng_bian_ma}",String.valueOf(t.getYouZhengBianMa()));
 		sql  = sql.replace("#{zhu_lian_xi_ren}",String.valueOf(t.getZhuLianXiRen()));
 		sql  = sql.replace("#{dian_hua}",String.valueOf(t.getDianHua()));
 		sql  = sql.replace("#{shou_ji}",String.valueOf(t.getShouJi()));
 		sql  = sql.replace("#{chuan_zhen}",String.valueOf(t.getChuanZhen()));
 		sql  = sql.replace("#{emaildi_zhi}",String.valueOf(t.getEmaildiZhi()));
 		sql  = sql.replace("#{wang_ye_di_zhi}",String.valueOf(t.getWangYeDiZhi()));
 		sql  = sql.replace("#{fa_ren_dai_biao}",String.valueOf(t.getFaRenDaiBiao()));
 		sql  = sql.replace("#{fa_ren_shen_fen}",String.valueOf(t.getFaRenShenFen()));
 		sql  = sql.replace("#{zhu_ce_zi_jin}",String.valueOf(t.getZhuCeZiJin()));
 		sql  = sql.replace("#{bi_bie}",String.valueOf(t.getBiBie()));
 		sql  = sql.replace("#{ying_ye_zhi_zhao}",String.valueOf(t.getYingYeZhiZhao()));
 		sql  = sql.replace("#{shui_wu_deng}",String.valueOf(t.getShuiWuDeng()));
 		sql  = sql.replace("#{zu_zhi_ji_gou}",String.valueOf(t.getZuZhiJiGou()));
 		sql  = sql.replace("#{dao_lu_yun_shu}",String.valueOf(t.getDaoLuYunShu()));
 		sql  = sql.replace("#{zhu_ying_ye_wu}",String.valueOf(t.getZhuYingYeWu()));
 		sql  = sql.replace("#{he_yi_xiang}",String.valueOf(t.getHeYiXiang()));
 		sql  = sql.replace("#{pi_zhun_ji_guan}",String.valueOf(t.getPiZhunJiGuan()));
 		sql  = sql.replace("#{pi_zhun_wen_hao}",String.valueOf(t.getPiZhunWenHao()));
 		sql  = sql.replace("#{zhu_ce_ri_qi}",String.valueOf(t.getZhuCeRiQi()));
 		sql  = sql.replace("#{bei_zhu}",String.valueOf(t.getBeiZhu()));
 		sql  = sql.replace("#{zhu_lian_xi_ren1}",String.valueOf(t.getZhuLianXiRen1()));
 		sql  = sql.replace("#{dian_hua1}",String.valueOf(t.getDianHua1()));
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
					javaInter.execute("md_cus_other",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}