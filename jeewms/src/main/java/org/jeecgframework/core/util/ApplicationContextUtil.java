package org.jeecgframework.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author  admin
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
    public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}
}
