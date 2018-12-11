package com.dstz.sys;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author didi
 */
public abstract class BasicTest {

    private ApplicationContext applicationContext;

    /**
     * 得到spring上下文对象
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 子类可以重写，加载指定的spring配置文件
     *
     * @return
     */
    protected Set<String> loadConfigLocations() {
        Set<String> configLocations = new HashSet<>();
        configLocations.add("classpath:conf/app-test.xml");
        return configLocations;
    }

    /**
     * 初始化
     */
    @Before
    public void setup() {
        Set<String> configLocations = loadConfigLocations();
        this.applicationContext = new ClassPathXmlApplicationContext(configLocations.toArray(new String[configLocations.size()]));
    }
}
