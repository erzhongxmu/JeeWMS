package org.jeecgframework.core.common.hibernate.dialect;


public class HSQLDialect extends Dialect
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
    boolean hasOffset = offset > 0;
    return new StringBuffer(sql.length() + 10).append(sql).insert(sql.toLowerCase().indexOf("select") + 6, " top " + limitPlaceholder).toString();
  }
}