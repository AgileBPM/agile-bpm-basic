package com.dstz.sys.util;

import com.dstz.base.core.util.AppUtil;
import com.dstz.sys.api.service.PropertyService;

/**
 * 系统参数工具类。
 *
 * @author ray
 */
public class SysPropertyUtil {

    /**
     * 获取字符串参数值。
     *
     * @param alias
     * @return
     */
    public static String getByAlias(String alias) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        return service.getByAlias(alias);

    }

    /**
     * 获取字符串参数值，带默认值。
     *
     * @param alias
     * @param defaultValue
     * @return
     */
    public static String getByAlias(String alias, String defaultValue) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        if (service == null) return defaultValue;
        return service.getByAlias(alias, defaultValue);

    }

    /**
     * 根据参数别名获取整形参数。
     *
     * @param alias
     * @return
     */
    public static Integer getIntByAlias(String alias) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        return service.getIntByAlias(alias);

    }

    /**
     * 根据参数别名获取整形参数，带默认值。
     *
     * @param alias
     * @param defaulValue
     * @return
     */
    public static Integer getIntByAlias(String alias, Integer defaulValue) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        return service.getIntByAlias(alias, defaulValue);
    }

    /**
     * 根据别名获取长整型参数。
     *
     * @param alias
     * @return
     */
    public static Long getLongByAlias(String alias) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        return service.getLongByAlias(alias);

    }

    /**
     * 根据别名获取布尔型参数值。
     *
     * @param alias
     * @return
     */
    public static boolean getBooleanByAlias(String alias) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        return service.getBooleanByAlias(alias);

    }

    /**
     * 根据别名获取布尔型参数值,带默认值。
     *
     * @param alias
     * @param defaulValue
     * @return
     */
    public static boolean getBooleanByAlias(String alias, boolean defaulValue) {
        PropertyService service = AppUtil.getBean(PropertyService.class);
        return service.getBooleanByAlias(alias, defaulValue);
    }

}