package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.cache.ICache;

/**
 * 获取上下文bean。
 *
 * <pre>
 * 在spring配置文件中做如下配置。
 * &lt;bean id="appUtil" class="com.dstz.base.core.util.AppUtil" />
 * </pre>
 */
@Component
public class AppUtil implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AppUtil.class);

    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext _context)
            throws BeansException {
        AppUtil.context = _context;

    }

    /**
     * 获取spring容器上下文。
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicaitonContext() {
        return context;
    }

    /**
     * 根据beanId获取配置在系统中的对象实例。
     *
     * @param beanId
     * @return Object
     * @throws
     * @since 1.0.0
     */
    public static Object getBean(String beanId) {
        try {
            return context.getBean(beanId);
        } catch (Exception ex) {
            LOGGER.debug("getBean:" + beanId + "," + ex.getMessage());
        }
        return null;
    }

    /**
     * 获取Spring容器的Bean
     *
     * @param beanClass
     * @return T
     * @throws
     * @since 1.0.0
     */
    public static <T> T getBean(Class<T> beanClass) {
        try {
            return context.getBean(beanClass);
        } catch (Exception ex) {
            LOGGER.debug("getBean:" + beanClass + "," + ex.getMessage());
        }
        return null;
    }

    /**
     * 根据指定的接口或基类获取实现类列表。
     *
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class> getImplClass(Class clazz) throws ClassNotFoundException {
        List<Class> list = new ArrayList<Class>();

        Map map = context.getBeansOfType(clazz);
        for (Object obj : map.values()) {
            String name = obj.getClass().getName();
            int pos = name.indexOf("$$");
            if (pos > 0) {
                name = name.substring(0, name.indexOf("$$"));
            }
            Class cls = Class.forName(name);

            list.add(cls);
        }
        return list;
    }


    /**
     * 获取接口的实现类实例。
     *
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> getImplInstance(Class<T> clazz){
        return context.getBeansOfType(clazz);
    }
    
    public static <T> List<T> getImplInstanceArray(Class<T> clazz){
    	List<T> list = new ArrayList<>();
    	
    	Map<String,T> map = context.getBeansOfType(clazz);
    	
    	for (T t : map.values()) {
    		list.add(t);
		}
    	return list;
    }

    /**
     * 获取系统中的ICache实现类。
     *
     * @return
     */
    public static ICache getCache() {
        return (ICache) getBean("iCache");
    }


    /**
     * 发布事件。
     *
     * @param event void
     */
    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            context.publishEvent(event);
        }
    }


    /**
     * 获取当前系统环境<br>
     * 目前仅支持单一环境配置。请勿配置多个参数 dev sit 之类。 环境配置的判断参考下面代码<br>
     * doGetActiveProfiles().contains(profile) || (doGetActiveProfiles().isEmpty() && doGetDefaultProfiles().contains(profile))
     *
     * @return
     */
    private static String currentProfiles = null;

    public static String getCtxEnvironment() {
        if (currentProfiles != null) {
            return currentProfiles;
        }

        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();

        if (BeanUtils.isNotEmpty(activeProfiles)) {
            currentProfiles = activeProfiles[0];
            return currentProfiles;
        }

        String[] defaultProfiles = environment.getDefaultProfiles();
        if (BeanUtils.isNotEmpty(defaultProfiles)) {
            currentProfiles = defaultProfiles[0];
            return defaultProfiles[0];
        }

        throw new BusinessException("查找不到正确的环境属性配置！", BaseStatusCode.SYSTEM_ERROR);
    }


}
