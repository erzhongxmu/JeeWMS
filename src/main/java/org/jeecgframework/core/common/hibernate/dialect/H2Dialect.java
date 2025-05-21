package org.jeecgframework.core.common.hibernate.dialect;


/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class H2Dialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return new StringBuffer(sql.length() + 40).append(sql).append(" limit " + limitPlaceholder).toString();
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }
}