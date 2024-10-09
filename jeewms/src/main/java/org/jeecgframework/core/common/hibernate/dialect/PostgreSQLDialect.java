package org.jeecgframework.core.common.hibernate.dialect;


public class PostgreSQLDialect extends Dialect
{
  @Override
  public boolean supportsLimit()
  {
    return true;
  }

  @Override
  public boolean supportsLimitOffset()
  {
    return true;
  }

  @Override
  public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    return new StringBuffer(sql.length() + 20).append(sql).append(" limit " + limitPlaceholder).toString();
  }
}