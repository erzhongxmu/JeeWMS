package com.zzjee.wm.service.impl;
import com.zzjee.md.entity.MdGoodsEntity;
import com.zzjee.md.entity.MvGoodsEntity;
import com.zzjee.wm.service.WmImNoticeHServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.zzjee.wm.entity.WmImNoticeHEntity;
import com.zzjee.wm.entity.WmImNoticeIEntity;
import com.zzjee.wm.entity.WmInQmIEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("wmImNoticeHService")
@Transactional
public class WmImNoticeHServiceImpl extends CommonServiceImpl implements WmImNoticeHServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((WmImNoticeHEntity)entity);
 	}
	
	public void addMain(WmImNoticeHEntity wmImNoticeH,
	        List<WmImNoticeIEntity> wmImNoticeIList){
			//保存主信息
			this.save(wmImNoticeH);
		
			/**保存-进货通知明细*/
			for(WmImNoticeIEntity wmImNoticeI:wmImNoticeIList){
				//外键设置
				MvGoodsEntity mvgoods = new MvGoodsEntity();
				mvgoods = this.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", wmImNoticeI.getGoodsCode()) ;		
				if(mvgoods!=null){
					wmImNoticeI.setGoodsName(mvgoods.getGoodsName());
					wmImNoticeI.setBarCode(mvgoods.getShpTiaoMa());

					try {
						try {
							wmImNoticeI.setGoodsFvol(String.valueOf(Double.parseDouble(mvgoods.getTiJiCm())*Long.parseLong(wmImNoticeI.getGoodsCount())));

						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							if(!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())){
								wmImNoticeI.setBaseGoodscount(String.valueOf(Long.parseLong(mvgoods.getChlShl())*Long.parseLong(wmImNoticeI.getGoodsCount())));
							}else{
								wmImNoticeI.setBaseGoodscount(wmImNoticeI.getGoodsCount());
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						wmImNoticeI.setGoodsUnit(mvgoods.getShlDanWei());
						wmImNoticeI.setBaseUnit(mvgoods.getBaseunit());

//						wmImNoticeI.setGoodsWeight(String.valueOf(Long.parseLong(mdgoods.getZhlKg())*Long.parseLong(wmImNoticeI.getGoodsCount())));
//						wmImNoticeI.setGoodsUnit(mdgoods.getShlDanWei());

					} catch (Exception e) {
						// TODO: handle exception
					}
					
					try {
//						wmImNoticeI.setGoodsFvol(String.valueOf(Long.parseLong(mdgoods.getTiJiCm())*Long.parseLong(wmImNoticeI.getGoodsCount())));
						wmImNoticeI.setGoodsWeight(String.valueOf(Double.parseDouble(mvgoods.getZhlKg())*Long.parseLong(wmImNoticeI.getGoodsCount())));
//						wmImNoticeI.setGoodsUnit(mdgoods.getShlDanWei());

					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				if("04".equals(wmImNoticeH.getOrderTypeCode())){//越库任务
					wmImNoticeI.setGoodsQmCount("0");
					wmImNoticeI.setBinPre("Y");
					wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
					this.save(wmImNoticeI);
					WmInQmIEntity wmInQmI = new WmInQmIEntity();
					wmInQmI.setBinId(wmImNoticeH.getNoticeId());//仓位
					wmInQmI.setImNoticeId(wmImNoticeH.getNoticeId());//通知单号
					wmInQmI.setImNoticeItem(wmImNoticeI.getId());
					wmInQmI.setBinSta("Y");
					wmInQmI.setCusCode(wmImNoticeH.getCusCode());;
					wmInQmI.setTinId(wmImNoticeH.getNoticeId());
					wmInQmI.setTinTj(wmImNoticeI.getGoodsFvol());
					wmInQmI.setTinZhl(wmImNoticeI.getGoodsWeight());
					wmInQmI.setGoodsId(wmImNoticeI.getGoodsCode());
					wmInQmI.setGoodsUnit(wmImNoticeI.getGoodsUnit());
					wmInQmI.setQmOkQuat(wmImNoticeI.getGoodsCount());
					wmInQmI.setImQuat(wmImNoticeI.getGoodsCount());
					wmInQmI.setBaseQmcount(wmImNoticeI.getBaseGoodscount());
					wmInQmI.setBaseUnit(wmImNoticeI.getBaseUnit());
					wmInQmI.setProData(DateUtils.date2Str(DateUtils.date_sdf));
					wmInQmI.setGoodsBatch(wmInQmI.getProData());
					wmInQmI.setBaseGoodscount(wmImNoticeI.getBaseGoodscount());
					wmInQmI.setImCusCode(wmImNoticeH.getImCusCode());
					this.save(wmInQmI);

				}else{
					wmImNoticeI.setBinPre("N");
					wmImNoticeI.setGoodsQmCount("0");
					TSUser user = ResourceUtil.getSessionUserName();
					String roles = "";
					if (user != null) {
						List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
						for (TSRoleUser ru : rUsers) {
							TSRole role = ru.getTSRole();
							roles += role.getRoleCode() + ",";
						}
						if (roles.length() > 0) {
							roles = roles.substring(0, roles.length() - 1);
						}
						if(roles.equals("CUS")){
							wmImNoticeI.setBinPre("I");
						}
					}
					
					wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
					wmImNoticeI.setImBeizhu(wmImNoticeH.getImBeizhu());
					wmImNoticeI.setImCusCode(wmImNoticeH.getImCusCode());
					this.save(wmImNoticeI);
				}

	
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(wmImNoticeH);
	}

	
	public void updateMain(WmImNoticeHEntity wmImNoticeH,
	        List<WmImNoticeIEntity> wmImNoticeIList) {
		//保存主表信息
		this.saveOrUpdate(wmImNoticeH);
		//===================================================================================
		//获取参数
		Object id0 = wmImNoticeH.getNoticeId();
		//===================================================================================
		//1.查询出数据库的明细数据-进货通知明细
	    String hql0 = "from WmImNoticeIEntity where 1 = 1 AND imNoticeId = ? ";
	    List<WmImNoticeIEntity> wmImNoticeIOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-进货通知明细
		if(wmImNoticeIList!=null&&wmImNoticeIList.size()>0){
		for(WmImNoticeIEntity oldE:wmImNoticeIOldList){
			boolean isUpdate = false;
				for(WmImNoticeIEntity sendE:wmImNoticeIList){
					//需要更新的明细数据-进货通知明细
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-进货通知明细
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-进货通知明细
			for(WmImNoticeIEntity wmImNoticeI:wmImNoticeIList){
				if(oConvertUtils.isEmpty(wmImNoticeI.getId())){
					//外键设置
					MvGoodsEntity mvgoods = new MvGoodsEntity();
					mvgoods = this.findUniqueByProperty(MvGoodsEntity.class, "goodsCode", wmImNoticeI.getGoodsCode()) ;				
					if(mvgoods!=null){
						try {
							wmImNoticeI.setGoodsUnit(mvgoods.getShlDanWei());
							wmImNoticeI.setBaseUnit(mvgoods.getBaseunit());
							try {
								if(!mvgoods.getBaseunit().equals(mvgoods.getShlDanWei())){
									wmImNoticeI.setBaseGoodscount(String.valueOf(Long.parseLong(mvgoods.getChlShl())*Long.parseLong(wmImNoticeI.getGoodsCount())));
								}else{
									wmImNoticeI.setBaseGoodscount(wmImNoticeI.getGoodsCount());
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

//							wmImNoticeI.setGoodsFvol(String.valueOf(Long.parseLong(mdgoods.getTiJiCm())*Long.parseLong(wmImNoticeI.getGoodsCount())));
							try {
								wmImNoticeI.setGoodsWeight(String.valueOf(Double.parseDouble(mvgoods.getZhlKg())*Long.parseLong(wmImNoticeI.getGoodsCount())));

							} catch (Exception e) {
								// TODO: handle exception
							}
//							wmImNoticeI.setGoodsUnit(mdgoods.getShlDanWei());
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							wmImNoticeI.setGoodsFvol(String.valueOf(Double.parseDouble(mvgoods.getTiJiCm())*Long.parseLong(wmImNoticeI.getGoodsCount())));
//							wmImNoticeI.setGoodsWeight(String.valueOf(Long.parseLong(mdgoods.getZhlKg())*Long.parseLong(wmImNoticeI.getGoodsCount())));
//							wmImNoticeI.setGoodsUnit(mdgoods.getShlDanWei());
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					wmImNoticeI.setGoodsQmCount("0");
					wmImNoticeI.setImNoticeId(wmImNoticeH.getNoticeId());
					this.save(wmImNoticeI);
				}
			}
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(wmImNoticeH);
	}

	
	public void delMain(WmImNoticeHEntity wmImNoticeH) {
		//删除主表信息
		this.delete(wmImNoticeH);
		//===================================================================================
		//获取参数
		Object id0 = wmImNoticeH.getNoticeId();
		//===================================================================================
		//删除-进货通知明细
	    String hql0 = "from WmImNoticeIEntity where 1 = 1 AND imNoticeId = ? ";
	    List<WmImNoticeIEntity> wmImNoticeIOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(wmImNoticeIOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	public boolean doAddSql(WmImNoticeHEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	public boolean doUpdateSql(WmImNoticeHEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	public boolean doDelSql(WmImNoticeHEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,WmImNoticeHEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{cus_code}",String.valueOf(t.getCusCode()));
 		sql  = sql.replace("#{im_data}",String.valueOf(t.getImData()));
 		sql  = sql.replace("#{im_cus_code}",String.valueOf(t.getImCusCode()));
 		sql  = sql.replace("#{im_car_no}",String.valueOf(t.getImCarNo()));
 		sql  = sql.replace("#{im_car_dri}",String.valueOf(t.getImCarDri()));
 		sql  = sql.replace("#{im_car_mobile}",String.valueOf(t.getImCarMobile()));
 		sql  = sql.replace("#{order_type_code}",String.valueOf(t.getOrderTypeCode()));
 		sql  = sql.replace("#{platform_code}",String.valueOf(t.getPlatformCode()));
 		sql  = sql.replace("#{im_beizhu}",String.valueOf(t.getImBeizhu()));
 		sql  = sql.replace("#{im_sta}",String.valueOf(t.getImSta()));
 		sql  = sql.replace("#{notice_id}",String.valueOf(t.getNoticeId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}