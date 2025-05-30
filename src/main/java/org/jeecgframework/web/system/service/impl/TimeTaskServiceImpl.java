package org.jeecgframework.web.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.jeecgframework.web.system.service.TimeTaskServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
@Service("timeTaskService")
@Transactional
public class TimeTaskServiceImpl extends CommonServiceImpl implements TimeTaskServiceI {

}
