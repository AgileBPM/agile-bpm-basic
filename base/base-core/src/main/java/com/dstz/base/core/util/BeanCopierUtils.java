package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

/**
 *  bean 属性copy
 *  
 */
public class BeanCopierUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(AppUtil.class);

    public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();  
      
 
    public static void copyProperties(Object source,Object target){  
        String beanKey = generateKey(source.getClass(),target.getClass());  
        BeanCopier copier = null;  
        if (!beanCopierMap.containsKey(beanKey)) {  
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);  
            beanCopierMap.put(beanKey, copier);  
        }else {  
            copier = beanCopierMap.get(beanKey);  
        }  
        copier.copy(source, target, null);  
    }  
    
    /**
     * 将一个类转换成另外一个类。属性copy
     * @param source
     * @param clazz
     * @return
     */
    public static <T> T transformBean(Object source,Class<T> clazz){  
    	if(source == null) return null;
    	T target;
		try {
			target = clazz.newInstance();
			
			String beanKey = generateKey(source.getClass(),clazz); 
			
			BeanCopier copier = null;  
	        if (!beanCopierMap.containsKey(beanKey)) {  
	            copier = BeanCopier.create(source.getClass(), target.getClass(), false);  
	            beanCopierMap.put(beanKey, copier);  
	        }else {  
	            copier = beanCopierMap.get(beanKey);  
	        }  
		        
		    copier.copy(source, target, null);
			return target;
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.warn("类转换出错",e);
		}
    	return null;
    }
    
    /**
     * 转换成list
     * @param sourceList
     * @param clazz
     * @return
     */
    public static <T> List<T> transformList(List sourceList,Class<T> clazz){  
    	 
    	List<T> list = new ArrayList<>();
    	
    	for (Object source : sourceList) {
			T target = transformBean(source, clazz);
			
    		if(target != null)list.add(target);
		}
    	
    //	if(sourceList instanceof Page) 对分页数据进行特殊处理 TODO
    	return list;
    }
    
    
    private static String generateKey(Class<?>class1,Class<?>class2){  
        return class1.toString() + class2.toString();  
    }  
  
}
