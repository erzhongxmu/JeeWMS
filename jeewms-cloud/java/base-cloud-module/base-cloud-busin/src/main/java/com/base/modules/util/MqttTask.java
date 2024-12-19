package com.base.modules.util;

import cn.hutool.core.collection.CollectionUtil;
import com.base.modules.jeeerp.entity.BusiPo;
import com.base.modules.jeeerp.mapper.BusiPoMapper;
import com.base.modules.jeeerp.service.IBusiPoService;
import com.base.modules.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备采集报表定时器
 */
@Slf4j
public class MqttTask extends TimerTask {
    @Autowired
    private IBusiPoService busiPoService =  SpringUtils.getBean(IBusiPoService.class);

    @Autowired
    private BusiPoMapper busiPoMapper =  SpringUtils.getBean(BusiPoMapper.class);


    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void run() {
        try {
//            startMqttReport();
        } catch (Exception e) {
            log.info("定时器执行错误:",e);
        }
        log.info("定时器执行时间:"+format.format(Calendar.getInstance().getTime()));
    }

}
