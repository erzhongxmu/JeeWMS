package com.base.modules.jeebms.job;

import com.base.common.util.DateUtils;

import com.base.modules.jeebms.controller.BmsApiController;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 示例带参定时任务
 *
 * @Author base-boot
 */
@Slf4j
@Component
public class CostTaskJob implements Job {

	@Autowired
	private BmsApiController bmsApiController;
	/**
	 * 若参数变量名修改 QuartzJobController中也需对应修改
	 */
	private String parameter;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		String[] split = this.parameter.split(";");
		for (String s : split) {
			bmsApiController.bmsrun(s,null);
		}
		log.info(" Job Execution key："+jobExecutionContext.getJobDetail().getKey());
		log.info( String.format("welcome %s! Base-Boot 带参数定时任务 SampleParamJob !   时间:" + DateUtils.now(), this.parameter));
	}
}
