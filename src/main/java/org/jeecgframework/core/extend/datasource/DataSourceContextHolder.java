package org.jeecgframework.core.extend.datasource;

/**
 * 类名：DataSourceContextHolder.java
 * 功能：获得和设置上下文环境的类，主要负责改变上下文数据源的名称
 */

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DataSourceContextHolder {

    private static final ThreadLocal CONTEXT_HOLDER = new ThreadLocal();

    public static void setDataSourceType(DataSourceType dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    public static DataSourceType getDataSourceType() {
        return (DataSourceType) CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

}
