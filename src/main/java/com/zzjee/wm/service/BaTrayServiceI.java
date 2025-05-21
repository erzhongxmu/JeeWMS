package com.zzjee.wm.service;

import com.zzjee.wm.entity.BaTrayEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface BaTrayServiceI extends CommonService {

    public void delete(BaTrayEntity entity) throws Exception;

    public Serializable save(BaTrayEntity entity) throws Exception;

    public void saveOrUpdate(BaTrayEntity entity) throws Exception;

}
