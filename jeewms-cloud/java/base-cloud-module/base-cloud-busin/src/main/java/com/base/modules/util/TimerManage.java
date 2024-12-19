package com.base.modules.util;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Component
public class TimerManage {

    public TimerManage(){
        Timer timer = new Timer();
        MqttTask Mqtttask = new MqttTask();
        timer.schedule(Mqtttask,new Date(), TimeUnit.HOURS.toMillis(3));
    }
}
