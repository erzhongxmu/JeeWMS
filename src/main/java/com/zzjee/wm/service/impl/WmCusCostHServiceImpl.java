package com.zzjee.wm.service.impl;
import com.zzjee.wm.service.WmCusCostHServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.zzjee.wm.entity.WmCusCostHEntity;
import com.zzjee.wm.entity.WmCusCostIEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("wmCusCostHService")
@Transactional
public class WmCusCostHServiceImpl extends CommonServiceImpl implements WmCusCostHServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((WmCusCostHEntity)entity);
 	}
	
	public void addMain(WmCusCostHEntity wmCusCostH,
	        List<WmCusCostIEntity> wmCusCostIList){
			//保存主信息
			this.save(wmCusCostH);
		
			/**保存-费用项目*/
			for(WmCusCostIEntity wmCusCostI:wmCusCostIList){
				//外键设置
				wmCusCostI.setCusCostId(wmCusCostH.getId());
				this.save(wmCusCostI);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(wmCusCostH);
	}

	
	public void updateMain(WmCusCostHEntity wmCusCostH,
	        List<WmCusCostIEntity> wmCusCostIList) {
		//保存主表信息
		this.saveOrUpdate(wmCusCostH);
		//===================================================================================
		//获取参数
		Object id0 = wmCusCostH.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-费用项目
	    String hql0 = "from WmCusCostIEntity where 1 = 1 AND cUS_COST_ID = ? ";
	    List<WmCusCostIEntity> wmCusCostIOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-费用项目
		if(wmCusCostIList!=null&&wmCusCostIList.size()>0){
		for(WmCusCostIEntity oldE:wmCusCostIOldList){
			boolean isUpdate = false;
				for(WmCusCostIEntity sendE:wmCusCostIList){
					//需要更新的明细数据-费用项目
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-费用项目
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-费用项目
			for(WmCusCostIEntity wmCusCostI:wmCusCostIList){
				if(oConvertUtils.isEmpty(wmCusCostI.getId())){
					//外键设置
					wmCusCostI.setCusCostId(wmCusCostH.getId());
					this.save(wmCusCostI);
				}
			}
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(wmCusCostH);
	}

	
	public void delMain(WmCusCostHEntity wmCusCostH) {
		//删除主表信息
		this.delete(wmCusCostH);
		//===================================================================================
		//获取参数
		Object id0 = wmCusCostH.getId();
		//===================================================================================
		//删除-费用项目
	    String hql0 = "from WmCusCostIEntity where 1 = 1 AND cUS_COST_ID = ? ";
	    List<WmCusCostIEntity> wmCusCostIOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(wmCusCostIOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(WmCusCostHEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(WmCusCostHEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(WmCusCostHEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,WmCusCostHEntity t){
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
 		sql  = sql.replace("#{cus_hetongid}",String.valueOf(t.getCusHetongid()));
 		sql  = sql.replace("#{begin_date}",String.valueOf(t.getBeginDate()));
 		sql  = sql.replace("#{end_date}",String.valueOf(t.getEndDate()));
 		sql  = sql.replace("#{cus_beizhu}",String.valueOf(t.getCusBeizhu()));
 		sql  = sql.replace("#{fujian}",String.valueOf(t.getFujian()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}