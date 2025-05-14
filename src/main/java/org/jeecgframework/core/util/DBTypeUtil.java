package org.jeecgframework.core.util;



import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @author  admin
 *
 */
public class DBTypeUtil {
	private static Logger log = Logger.getLogger(DBTypeUtil.class);
	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDBType(){
		String retStr="";
		ApplicationContext ctx = ApplicationContextUtil.getContext();
		if (ctx==null) {
			 return retStr;//如果ctx为空，则服务器异常了
		}else{
			org.springframework.orm.hibernate4.LocalSessionFactoryBean sf = (org.springframework.orm.hibernate4.LocalSessionFactoryBean)ctx.getBean("&sessionFactory");
			String dbdialect = sf.getHibernateProperties().getProperty("hibernate.dialect");
			log.debug(dbdialect);
			if ("org.hibernate.dialect.MySQLDialect".equals(dbdialect)) {
				retStr="mysql";
			}else if (dbdialect.contains("Oracle")) {//oracle有多个版本的方言
				retStr = "oracle";
			}else if ("org.hibernate.dialect.SQLServerDialect".equals(dbdialect)) {
				retStr = "sqlserver";
			}else if ("org.hibernate.dialect.PostgreSQLDialect".equals(dbdialect)) {
				retStr = "postgres";
			}
			else if ("org.jeecgframework.core.common.hibernate.dialect.MySQLServer2008Dialect".equals(dbdialect)) {
				retStr = "sqlserver";
			}
			return retStr;
		}
	}
}
