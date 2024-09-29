package com.zzjee.mvyj.service;
import com.zzjee.mvyj.entity.MvStockYjEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
/**
 * MvStockYjServiceI 接口定义了对 MvStockYjEntity 实体类的增删改查操作，扩展了 CommonService 接口。
 * 方法中使用了 Exception 类型的异常，以应对操作中可能出现的问题。
 */
public interface MvStockYjServiceI extends CommonService{
	//根据实体类对象删除记录；
 	public void delete(MvStockYjEntity entity) throws Exception;
 	//保存实体类对象，并返回新记录的 id；
 	public Serializable save(MvStockYjEntity entity) throws Exception;
 	//如果实体类对象已存在，更新其记录；否则，保存一条新记录。
 	public void saveOrUpdate(MvStockYjEntity entity) throws Exception;
 	
}
