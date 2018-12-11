package com.dstz.base.api.executor;

/**
 * 执行器
 *
 * @param <T> 运行执行器需要的参数
 * @author aschs
 */
public interface Executor<T> extends Comparable<Executor<T>> {

    /**
     * <pre>
     * 执行器的key
     * </pre>
     *
     * @return
     */
    String getKey();

    /**
     * <pre>
     * 执行器的名称
     * </pre>
     *
     * @return
     */
    String getName();

    /**
     * <pre>
     * 返回这个执行器的类型key
     * </pre>
     *
     * @return
     */
    String type();

    /**
     * <pre>
     * 返回校验器的别名
     * 多个以,分隔，eg：a,b,c,...
     * </pre>
     *
     * @return
     */
    String getCheckerKey();

    /**
     * <pre>
     * 序号
     * 用于某些执行器有先后顺序的
     * </pre>
     *
     * @return
     */
    int getSn();

    /**
     * <pre>
     * 运行这个执行器
     * 运行前要通过校验
     * </pre>
     *
     * @param param 运行的参数
     */
    void execute(T param);

}
