package org.jeecgframework.web.cgform.service.enhance;

import org.jeecgframework.web.cgform.entity.enhance.CgformEnhanceJsEntity;

import org.jeecgframework.core.common.service.CommonService;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public interface CgformEnhanceJsServiceI extends CommonService {

    /**
     * 根据cgJsType和formId查找数据
     *
     * @param cgJsType
     * @param formId
     * @return
     */
    public CgformEnhanceJsEntity getCgformEnhanceJsByTypeFormId(String cgJsType, String formId);
}
