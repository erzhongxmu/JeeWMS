package com.zzjee.ba.service;

import com.zzjee.ba.entity.BaPlatformEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaPlatformServiceI extends CommonService {
    /**
     * 删除
     *
     * @param entity
     * @throws Exception
     */
    public void delete(BaPlatformEntity entity) throws Exception;

    public Serializable save(BaPlatformEntity entity) throws Exception;

    public void saveOrUpdate(BaPlatformEntity entity) throws Exception;

}
