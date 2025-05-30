package org.jeecgframework.web.system.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.DynamicDataSourceEntity;


/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface DynamicDataSourceServiceI extends CommonService {

    public List<DynamicDataSourceEntity> initDynamicDataSource();

    public void refleshCache();


    public DynamicDataSourceEntity getDynamicDataSourceEntityForDbKey(String dbKey);


}
