package org.jeecgframework.core.common.hibernate.dialect;

import org.springframework.beans.factory.FactoryBean;

/**
 * Demo class
 *
 * @author admin
 * @date 2016/10/31
 */
public class DialectFactoryBean implements FactoryBean<Dialect> {
    public static final String ORACLE = "oracle";
    public static final String MYSQL = "mysql";
    public static final String SQLSERVER = "sqlserver";
    public static final String DB2 = "db2";
    public static final String POSTGRES = "postgres";
    private Dialect dialect;
    private String dbType = "mysql";

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public Dialect getObject() throws Exception {
        if ("oracle".equals(this.dbType)) {
            this.dialect = new OracleDialect();
        } else if ("sqlserver".equals(this.dbType)) {
            this.dialect = new SQLServer2005Dialect();
        } else if ("db2".equals(this.dbType)) {
            this.dialect = new DB2Dialect();
        } else if ("mysql".equals(this.dbType)) {
            this.dialect = new MySQLDialect();
        } else if ("postgres".equals(this.dbType)) {
            this.dialect = new PostgreSQLDialect();
        } else {
            throw new Exception("没有设置合适的数据库类型");
        }
        return this.dialect;
    }

    @Override
    public Class<?> getObjectType() {
        return Dialect.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}