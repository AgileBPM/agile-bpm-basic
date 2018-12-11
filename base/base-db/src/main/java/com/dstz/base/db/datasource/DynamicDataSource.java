package com.dstz.base.db.datasource;

import com.dstz.base.api.exception.BusinessException;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 根据当前上下文取得数据源。
 * <p>
 * Spring配置， 可以配置多个数据源。
 *
 * <pre>
 * <bean id="dataSource" class="com.dstz.base.db.datasource.DynamicDataSource">
 * 	<property name="targetDataSources">
 * 		<map key-type="java.lang.String">
 * 			<entry key="1" value-ref="ds1" />
 * 			<entry key="2" value-ref="ds2" />
 * 		&lt;/map>
 * 	&lt;/property>
 * 	<property name="defaultTargetDataSource" ref="ds1" />
 * &lt;/bean>
 * </pre>
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 取得当前使用那个数据源。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDataSource();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        // 重点
        super.afterPropertiesSet();
    }

    public void setDefaultDbtype(String dbType) {
        DbContextHolder.setDataSource("dataSourceDefault", dbType);

    }


    private static Object getValue(Object instance, String fieldName) {
        try {
            Field field = AbstractRoutingDataSource.class.getDeclaredField(fieldName);
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 添加数据源。
     *
     * @param key
     * @param dataSource
     * @throws IllegalAccessException
     * @throws NoSuchFieldException   void
     */
    public void addDataSource(String key, Object dataSource) {
        try {
            Map<Object, Object> targetDataSources = (Map<Object, Object>) getValue(this, DataSourceUtil.TARGET_DATASOURCES);
            boolean rtn = isDataSourceExist(key);
            if (rtn) {
                throw new DataSourceException("datasource name :" + key + "is exists!");
            }
            targetDataSources.put(key, dataSource);
            setTargetDataSources(targetDataSources);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 指定数据源是否存在。
     *
     * @param key
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException   boolean
     */
    public boolean isDataSourceExist(String key) {
        try {
            Map<Object, Object> targetDataSources = (Map<Object, Object>) getValue(this, DataSourceUtil.TARGET_DATASOURCES);
            return targetDataSources.containsKey(key);
        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

    /**
     * 根据别名删除数据源。
     *
     * @param key
     * @throws IllegalAccessException
     * @throws NoSuchFieldException   void
     */
    public void removeDataSource(String key) {
        Map<Object, Object> targetDataSources = (Map<Object, Object>) getValue(this, DataSourceUtil.TARGET_DATASOURCES);

        if (key.equals(DataSourceUtil.GLOBAL_DATASOURCE) || key.equals(DataSourceUtil.DEFAULT_DATASOURCE)) {
            throw new DataSourceException("datasource name :" + key + " can't be removed!");
        }
        targetDataSources.remove(key);
        setTargetDataSources(targetDataSources);
    }

    /**
     * 返回当前有哪些数据源。
     *
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException   Map<String,DataSource>
     */
    public Map<String, DataSource> getDataSource() {
        Map<String, DataSource> targetDataSources = (Map<String, DataSource>) getValue(this, DataSourceUtil.TARGET_DATASOURCES);
        return targetDataSources;

    }
    
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
    	super.setDefaultTargetDataSource(defaultTargetDataSource);
    }
    
    @Override
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
    	super.setDataSourceLookup(dataSourceLookup);
    }

}
