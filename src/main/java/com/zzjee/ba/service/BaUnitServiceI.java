package com.zzjee.ba.service;

import com.zzjee.ba.entity.BaUnitEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaUnitServiceI extends CommonService {

    public void delete(BaUnitEntity entity) throws Exception;

    public Serializable save(BaUnitEntity entity) throws Exception;

    public void saveOrUpdate(BaUnitEntity entity) throws Exception;

}
