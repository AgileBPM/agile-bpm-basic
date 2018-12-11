package com.dstz.sys.api.groovy;

import java.util.List;
import java.util.Map;

public interface IGroovyScriptEngine {
    /**
     * 执行groovy代码无返回。
     *
     * @param script
     */
    public void execute(String script);

    /**
     * 执行groovy代码无返回。
     *
     * @param script 脚本
     * @param vars   变量
     */
    public void execute(String script, Map<String, Object> vars);

    /**
     * 执行groovy代码返回布尔型。
     *
     * @param script
     * @return
     * @throws Exception
     */
    public boolean executeBoolean(String script, Map<String, Object> vars);

    /**
     * 执行脚本返回字符串数据。
     *
     * @param script
     * @return 字符串数据
     */
    public String executeString(String script, Map<String, Object> vars);

    /**
     * 执行脚本返回整形数据。
     *
     * @param script
     * @return 整形数据
     */
    public int executeInt(String script, Map<String, Object> vars);

    /**
     * 执行脚本返回浮点型数据。
     *
     * @param script
     * @return 浮点型数据
     */
    public float executeFloat(String script, Map<String, Object> vars);

    /**
     * 执行脚本返回对象数据。
     *
     * @param script
     * @return 对象数据
     */
    public Object executeObject(String script, Map<String, Object> vars);

}