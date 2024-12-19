package com.base.modules.message.service.impl;

import com.base.common.system.base.service.impl.BaseServiceImpl;
import com.base.modules.message.entity.SysMessage;
import com.base.modules.message.mapper.SysMessageMapper;
import com.base.modules.message.service.ISysMessageService;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: base-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

}
