package com.zzjee.md.service.impl;

import com.zzjee.md.entity.TMdBomHeadEntity;
import com.zzjee.md.entity.TMdBomItemEntity;
import com.zzjee.md.service.TMdBomHeadServiceI;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service("tMdBomHeadService")
@Transactional
public class TMdBomHeadServiceImpl extends CommonServiceImpl implements TMdBomHeadServiceI {
	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TMdBomHeadEntity)entity);
 	}
	
	@Override
    public void addMain(TMdBomHeadEntity tMdBomHead,
                        List<TMdBomItemEntity> tMdBomItemList){
			//保存主信息
			this.save(tMdBomHead);
		
			/**保存-BOM项目*/
			for(TMdBomItemEntity tMdBomItem:tMdBomItemList){
				//外键设置
				tMdBomItem.setBomid(tMdBomHead.getId());
				this.save(tMdBomItem);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tMdBomHead);
	}

	
	@Override
    public void updateMain(TMdBomHeadEntity tMdBomHead,
                           List<TMdBomItemEntity> tMdBomItemList) {
		//保存主表信息
		this.saveOrUpdate(tMdBomHead);
		//===================================================================================
		//获取参数
		Object id0 = tMdBomHead.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-BOM项目
	    String hql0 = "from TMdBomItemEntity where 1 = 1 AND bOMID = ? ";
	    List<TMdBomItemEntity> tMdBomItemOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-BOM项目
		if(tMdBomItemList!=null&&tMdBomItemList.size()>0){
		for(TMdBomItemEntity oldE:tMdBomItemOldList){
			boolean isUpdate = false;
				for(TMdBomItemEntity sendE:tMdBomItemList){
					//需要更新的明细数据-BOM项目
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-BOM项目
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-BOM项目
			for(TMdBomItemEntity tMdBomItem:tMdBomItemList){
				if(oConvertUtils.isEmpty(tMdBomItem.getId())){
					//外键设置
					tMdBomItem.setBomid(tMdBomHead.getId());
					this.save(tMdBomItem);
				}
			}
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tMdBomHead);
	}

	
	@Override
    public void delMain(TMdBomHeadEntity tMdBomHead) {
		//删除主表信息
		this.delete(tMdBomHead);
		//===================================================================================
		//获取参数
		Object id0 = tMdBomHead.getId();
		//===================================================================================
		//删除-BOM项目
	    String hql0 = "from TMdBomItemEntity where 1 = 1 AND bOMID = ? ";
	    List<TMdBomItemEntity> tMdBomItemOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tMdBomItemOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	@Override
    public boolean doAddSql(TMdBomHeadEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TMdBomHeadEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	@Override
    public boolean doDelSql(TMdBomHeadEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TMdBomHeadEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{bpm_status}",String.valueOf(t.getBpmStatus()));
 		sql  = sql.replace("#{matcode}",String.valueOf(t.getMatcode()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{text}",String.valueOf(t.getText()));
 		sql  = sql.replace("#{matname}",String.valueOf(t.getMatname()));
 		sql  = sql.replace("#{qty}",String.valueOf(t.getQty()));
 		sql  = sql.replace("#{units}",String.valueOf(t.getUnits()));
 		sql  = sql.replace("#{prccode}",String.valueOf(t.getPrccode()));
 		sql  = sql.replace("#{prcname}",String.valueOf(t.getPrcname()));
 		sql  = sql.replace("#{startdate}",String.valueOf(t.getStartdate()));
 		sql  = sql.replace("#{enddate}",String.valueOf(t.getEnddate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}