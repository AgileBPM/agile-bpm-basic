package com.dstz.base.core.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Map;
import java.util.Properties;

/**
 * 系统配置属性
 * TODO 支持加密、支持不同环境配置
 */
public class CustPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements IProperty, EnvironmentAware {

    private Environment environment;

    @Override
    protected void convertProperties(Properties props) {
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        Properties newProperties = new Properties();
        for (Map.Entry<Object, Object> propEntry : props.entrySet()) {
            if (!configurableEnvironment.containsProperty(String.valueOf(propEntry.getKey()))) {
                newProperties.put(propEntry.getKey(), propEntry.getValue());
            }
        }
        configurableEnvironment.getPropertySources().addLast(new PropertiesPropertySource("agilebpm", newProperties));
        super.convertProperties(props);
    }

    /**
     * 根据建获取属性中的值。
     */
    @Override
    public String getValue(String key) {
        return environment.getProperty(key);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
