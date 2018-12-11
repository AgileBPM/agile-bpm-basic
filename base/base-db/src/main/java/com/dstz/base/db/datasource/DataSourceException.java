package com.dstz.base.db.datasource;

/**
 * 数据源异常。
 * <pre>
 * </pre>
 */
public class DataSourceException extends RuntimeException {

    /**
     * serialVersionUID
     *
     * @since 1.0.0
     */
    private static final long serialVersionUID = 3148019938789322656L;

    public DataSourceException(String msg) {
        super(msg);
    }

    public DataSourceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
