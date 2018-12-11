package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dstz.base.api.model.Tree;
import com.dstz.base.api.query.QueryOP;

import cn.hutool.core.date.DateUtil;

public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 可以用于判断 Map,Collection,String,Array,Long是否为空
     *
     * @param o java.lang.Object.
     * @return boolean.
     */
    @SuppressWarnings("unused")
    public static boolean isEmpty(Object o) {
        if (o == null)
            return true;
        if (o instanceof String) {
            if (((String) o).trim().length() == 0)
                return true;
        } else if (o instanceof Collection) {
            if (((Collection<?>) o).size() == 0)
                return true;
        } else if (o.getClass().isArray()) {
            if (((Object[]) o).length == 0)
                return true;
        } else if (o instanceof Map) {
            if (((Map<?, ?>) o).size() == 0)
                return true;
        }
        return false;

    }
    
    
    

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 判断对象是否为数字
     *
     * @param o
     * @return
     */
    public static boolean isNumber(Object o) {
        if (o == null)
            return false;
        if (o instanceof Number)
            return true;
        if (o instanceof String) {
            try {
                Double.parseDouble((String) o);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * <pre>
     * 根据字段类型，条件，把字符串的valStr转成真正的val
     * </pre>
     *
     * @param columnType 字段类型
     * @param queryOP    条件
     * @param valStr     字符串值
     * @return
     */
    public static Object getValue(String columnType, QueryOP queryOP, String valStr) {
        Object value = null;
        if ("varchar".equals(columnType)) {
            value = valStr;
        } else if ("number".equals(columnType)) {
            value = Double.parseDouble(valStr);
        } else if ("date".equals(columnType)) {
            try {
                value = DateUtil.parse(valStr);
            } catch (Exception e) {

            }
        }
        return value;
    }
    
    /**
     * 将字符串数据按照指定的类型进行转换。
     *
     * @param typeName 实际的数据类型
     * @param valStr   字符串值。
     * @return Object
     */
    public static Object getValue(String typeName, String valStr) {
        Object o = null;
        if (typeName.equals("int")) {
            o = Integer.parseInt(valStr);
        } else if (typeName.equals("short")) {
            o = Short.parseShort(valStr);
        } else if (typeName.equals("long")) {
            o = Long.parseLong(valStr);
        } else if (typeName.equals("float")) {
            o = Float.parseFloat(valStr);
        } else if (typeName.equals("double")) {
            o = Double.parseDouble(valStr);
        } else if (typeName.equals("boolean")) {
            o = Boolean.parseBoolean(valStr);
        } else if (typeName.equals("java.lang.String")) {
            o = valStr;
        } else {
            o = valStr;
        }
        return o;
    }
  
    /**
     * @描述 list数据转Tree，大多使用在前台json中。
     * @说明 实现接口 Tree即可
     * @扩展 可通过反射获取id, pid，目前只提供Tree接口排序的实现
     * @author jeff
     */
    public static <T> List<T> listToTree(List<T> list) {
        Map<String, Tree> tempMap = new LinkedHashMap<String, Tree>();
        if (BeanUtils.isEmpty(list)) return Collections.emptyList();
        if (!(list.get(0) instanceof Tree)) {
            throw new RuntimeException("树形转换出现异常。数据必须实现Tree接口！");
        }

        List<T> returnList = new ArrayList<T>();
        for (Tree tree : (List<Tree>) list) {
            tempMap.put(tree.getId(), tree);
        }

        for (Tree obj : (List<Tree>) list) {
            String parentId = obj.getParentId();
            if (tempMap.containsKey(parentId) && !obj.getId().equals(parentId)) {
                if (tempMap.get(parentId).getChildren() == null) {
                    tempMap.get(parentId).setChildren(new ArrayList());
                }
                tempMap.get(parentId).getChildren().add(obj);
            } else {
                returnList.add((T) obj);
            }
        }

        return returnList;
    }

}
