package org.jeecgframework.core.common.hibernate.dialect;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DerbyDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return false;
    }

    @Override
    public boolean supportsLimitOffset() {
        return false;
    }

    @Override
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        throw new UnsupportedOperationException("paged queries not supported");
    }
}