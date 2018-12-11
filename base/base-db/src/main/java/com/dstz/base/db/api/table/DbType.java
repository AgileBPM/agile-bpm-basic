package com.dstz.base.db.api.table;

/**
 * <pre>
 * 数据库类型的枚举
 * </pre>
 *
 * @author aschs
 */
public enum DbType {
    /**
     * MYSQL
     */
    MYSQL("mysql", "mysql数据库", "com.mysql.jdbc.Driver", "jdbc:mysql://主机:3306/数据库名?useUnicode=true&characterEncoding=utf-8"),
    /**
     * ORACLE
     */
    ORACLE("oracle", "oracle数据库", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@主机:1521:数据库实例"),
    /**
     * SQLSERVER
     */
    SQLSERVER("sqlserver", "sqlserver数据库", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://主机:1433;databaseName=数据库名");
    private String key;
    private String desc;
    private String driverClassName;
    private String url;

    private DbType(String key, String desc, String driverClassName, String url) {
        this.desc = desc;
        this.key = key;
        this.driverClassName = driverClassName;
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public String getKey() {
        return key;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    /**
     * <pre>
     * 根据key来判断是否跟当前一致
     * </pre>
     *
     * @param key
     * @return
     */
    public boolean equalsWithKey(String key) {
        return this.key.equals(key);
    }
}
