package org.jeecgframework.core.common.hibernate.dialect;


public class MySQLDialect extends Dialect
{
  @Override
  public boolean supportsLimitOffset()
  {
    return true;
  }

  @Override
  public boolean supportsLimit()
  {
    return true;
  }

  @Override
  public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
  {
    if (offset > 0)
    {
      return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
    }

    return sql + " limit " + limitPlaceholder;
  }
}