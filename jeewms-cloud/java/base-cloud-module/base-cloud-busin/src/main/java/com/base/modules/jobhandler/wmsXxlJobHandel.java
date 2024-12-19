package com.base.modules.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
@Slf4j
public class wmsXxlJobHandel {
    @Autowired
    private com.base.modules.jeewms.service.impl.SmsSendImpl SmsSendImpl;
    @Autowired
    private com.base.modules.jeewms.service.impl.CostTask costTask;
    @XxlJob("wmsothertask")
    public void wmsothertaskJobHandler() throws Exception {
        XxlJobHelper.log("wmsothertask.");
        System.out.println("wmsothertask");
        SmsSendImpl.runothertask();
        // default success
    }
//    @XxlJob("wmsdowntask")
    public void wmsdowntaskJobHandler() throws Exception {
        XxlJobHelper.log("wmsdowntask.");
        System.out.println("wmsdowntask");
        SmsSendImpl.rundowntask("");
        // default success
    }
    @XxlJob("wmscosttask")
    public void wmscosttaskJobHandler() throws Exception {
        XxlJobHelper.log("wmscosttask.");
        System.out.println("wmscosttask");
        costTask.costtask();
        // default success
    }
}
