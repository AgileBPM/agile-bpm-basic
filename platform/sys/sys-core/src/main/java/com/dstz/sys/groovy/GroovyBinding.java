package com.dstz.sys.groovy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Binding;

/**
 * Groovy 绑定
 * 
 * @author ray
 */
public class GroovyBinding extends Binding {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private Map<?, ?> variables;
	private static ThreadLocal<Map<String, Object>> localVars = new ThreadLocal<>();

	private static Map<String, Object> propertyMap = new HashMap<>();

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

	@Override
	public Object getVariable(String name) {
		Map<String, Object> map = localVars.get();
		Object result = null;
		if (map != null) {
			result = map.get(name);
		}
		if (result == null) {
			result = propertyMap.get(name);
		}

		if (result == null) {
			logger.warn("执行Groovy 语句时,Context 缺少 Variable ：{}", name);
		}

		return result;
	}

	@Override
	public void setVariable(String name, Object value) {
		if (localVars.get() == null) {
			Map<String, Object> vars = new LinkedHashMap<>();
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

	@Override
	public Object getProperty(String property) {
		return propertyMap.get(property);
	}

	@Override
	public void setProperty(String property, Object newValue) {
		propertyMap.put(property, newValue);
	}
}
