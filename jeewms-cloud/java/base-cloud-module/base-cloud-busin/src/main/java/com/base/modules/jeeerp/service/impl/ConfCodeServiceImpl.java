package com.base.modules.jeeerp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.modules.jeeerp.entity.ConfCode;
import com.base.modules.jeeerp.mapper.ConfCodeMapper;
import com.base.modules.jeeerp.service.IConfCodeService;
import org.springframework.stereotype.Service;

/**
 * @Description: conf_code
 * @Author: base-boot
 * @Date:   2022-04-06
 * @Version: V1.0
 */
@Service
public class ConfCodeServiceImpl extends ServiceImpl<ConfCodeMapper, ConfCode> implements IConfCodeService {
}
