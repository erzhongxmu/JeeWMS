package com.zzjee.ba.service;

import com.zzjee.ba.entity.BaGoodsTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaGoodsTypeServiceI extends CommonService {
    /**
     * 删除
     *
     * @param entity
     * @throws Exception
     */
    public void delete(BaGoodsTypeEntity entity) throws Exception;

    public Serializable save(BaGoodsTypeEntity entity) throws Exception;

    public void saveOrUpdate(BaGoodsTypeEntity entity) throws Exception;

}
