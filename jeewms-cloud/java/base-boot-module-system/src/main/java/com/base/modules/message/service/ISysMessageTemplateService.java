package com.base.modules.message.service;

import java.util.List;

import com.base.common.system.base.service.BaseService;
import com.base.modules.message.entity.SysMessageTemplate;

/**
 * @Description: 消息模板
 * @Author: base-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
public interface ISysMessageTemplateService extends BaseService<SysMessageTemplate> {
    List<SysMessageTemplate> selectByCode(String code);
}
