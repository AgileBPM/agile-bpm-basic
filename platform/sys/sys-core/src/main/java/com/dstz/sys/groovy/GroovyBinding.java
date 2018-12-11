package com.dstz.sys.groovy;

import groovy.lang.Binding;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Groovy 绑定
 * @author ray
 */
public class GroovyBinding extends Binding {
    private Log logger = LogFactory.getLog(GroovyScriptEngine.class);

    @SuppressWarnings("unused")
    private Map<?, ?> variables;
    private static ThreadLocal<Map<String, Object>> localVars = new ThreadLocal<Map<String, Object>>();

    private static Map<String, Object> propertyMap = new HashMap<String, Object>();

    public GroovyBinding() {
    }

    public void setThreadVariables(Map<String, Object> variables) {
    	localVars.remove();
        localVars.set(variables);
    }

    public GroovyBinding(String[] args) {
        this();
        setVariable("args", args);
    }

    public Object getVariable(String name) {
        Map<String, Object> map = localVars.get();
        Object result = null;
        if (map != null && map.containsKey(name)) {
            result = map.get(name);
        } else {
            result = propertyMap.get(name);
        }
        
        if(result == null) {
        	logger.warn("执行Groovy 语句时,Context 缺少 Variable ："+name);
        }

        return result;
    }

    public void setVariable(String name, Object value) {
        if (localVars.get() == null) {
            Map<String, Object> vars = new LinkedHashMap<String, Object>();
            vars.put(name, value);
            localVars.set(vars);
        } else {
            localVars.get().put(name, value);
        }
    }

    @SuppressWarnings("rawtypes")
    public Map<?, ?> getVariables() {
        if (localVars.get() == null) {
            return new LinkedHashMap();
        }
        return localVars.get();
    }

    public void clearVariables() {
        localVars.remove();
    }

    public Object getProperty(String property) {
        return propertyMap.get(property);
    }

    public void setProperty(String property, Object newValue) {
        propertyMap.put(property, newValue);
    }
}
