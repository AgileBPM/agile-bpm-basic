package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dstz.base.api.model.Tree;
import com.dstz.base.api.query.QueryOP;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;

public class BeanUtils {
	  
    /**
     * @描述 list数据转Tree，大多使用在前台json中。
     * @说明 实现接口 Tree即可
     * @扩展 可通过反射获取id, pid，目前只提供Tree接口排序的实现
     * @author jeff
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T extends Tree> List<T> listToTree(List<T> list) {
        Map<String, Tree> tempMap = new LinkedHashMap<String, Tree>();
        if (CollectionUtil.isEmpty(list)) return Collections.emptyList();
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


}
