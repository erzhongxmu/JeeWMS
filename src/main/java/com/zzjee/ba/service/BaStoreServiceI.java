package com.zzjee.ba.service;

import com.zzjee.ba.entity.BaStoreEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaStoreServiceI extends CommonService {
    /**
     * 删除
     *
     * @param entity
     * @throws Exception
     */
    public void delete(BaStoreEntity entity) throws Exception;

    public Serializable save(BaStoreEntity entity) throws Exception;

    public void saveOrUpdate(BaStoreEntity entity) throws Exception;

}
