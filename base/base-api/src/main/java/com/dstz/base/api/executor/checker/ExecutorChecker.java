package com.dstz.base.api.executor.checker;

/**
 * 执行器的校验者
 *
 * @author aschs
 */
public interface ExecutorChecker {
    /**
     * <pre>
     * 校验器的key
     * 默认是类名
     * </pre>
     *
     * @return
     */
    String getKey();

    /**
     * <pre>
     * 校验器的名字
     * </pre>
     *
     * @return
     */
    String getName();

    /**
     * <pre>
     * 校验执行器
     * </pre>
     *
     * @param exectorKey 执行器key
     * @return
     */
    boolean check(String exectorKey);
}
