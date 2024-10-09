package org.jeecgframework.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author  admin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JeecgEntityTitle {
	  String name();
}
