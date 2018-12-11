package com.dstz.base.core.util;

import org.springframework.core.env.Environment;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.spring.CustPropertyPlaceholderConfigurer;
import com.dstz.base.core.spring.IProperty;

/**
 * 属性工具类。
 */
public class PropertyUtil {

    //private static Environment env = null;
	private static IProperty  propertyHolder = null;
    // spring boot 略 不同处理
	private static Environment environment = null;
    
    /**
     * 根据键值获取属性文件中配置的值。
     *
     * @param property     属性键值
     * @param defaultValue 默认值
     * @return String
     */
    public static String getProperty(String property, String defaultValue) {
    	if(propertyHolder == null){
    		getEnvironment();
    	}

        String v = propertyHolder.getValue(property);
        if (StringUtil.isEmpty(v)) {
            return defaultValue;
        }

        return v;
    }


    private static synchronized void getEnvironment() {
    	propertyHolder = (CustPropertyPlaceholderConfigurer) AppUtil.getBean("custPlaceHolder");
        
        if(propertyHolder == null) {
        	environment = AppUtil.getBean(Environment.class); 
        	if(environment == null) {
        		throw new BusinessException("Environment cannot be found");
        	}
        	
        	propertyHolder = new IProperty() {
				@Override
				public String getValue(String key) {
					return environment.getProperty(key);
				}
			};
        }
    }


    /**
     * 获取系统属性值。
     *
     * @param property 属性键值。
     * @return String
     */
    public static String getProperty(String property) {
        return getProperty(property, "");
    }

    /**
     * 获取整形值。
     *
     * @param property
     * @return
     */
    public static Integer getIntProperty(String property) {
        String str = getProperty(property, "");
        if (StringUtil.isEmpty(str)) {
            return 0;
        }
        return Integer.valueOf(str);
    }

    /**
     * 获取整形值，可以指定默认值。
     *
     * @param property
     * @param defaultValue
     * @return
     */
    public static Integer getIntProperty(String property, Integer defaultValue) {
        String str = getProperty(property, "");
        if (StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        return Integer.valueOf(str);
    }


    /**
     * 获取布尔值。如果配置值为 true，则返回true。这里不区分大小写，可以配置成True等。
     *
     * @param property
     * @return
     */
    public static boolean getBoolProperty(String property) {
        String str = getProperty(property, "");
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return str.equalsIgnoreCase("true");
    }

    /**
     * 获取当前数据库的类型。
     *
     * @return
     */
    public static String getJdbcType() {
        String str = getProperty("jdbc.dbType");
        if(StringUtil.isEmpty(str)) {
        	str = getProperty("spring.datasource.dbType");
        }
        
        return str;
    }
    
    /**
     * <pre>
     * 获取表单备份地址
     * </pre>
     * @return
     */
    public static String getFormDefBackupPath() {
    	return getProperty("formDefBackupPath");
    }

}
