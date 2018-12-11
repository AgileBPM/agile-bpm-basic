package com.dstz.base.core.executor;

import com.dstz.base.api.executor.ExecuteChain;

/**
 * 抽象的执行链
 * 这里只是作为一种子类会有多个实现的标记
 * 具体看例子吧-。-
 *
 * @param <T>
 * @author aschs
 */
public abstract class AbstractExecuteChain<T> extends AbstractExecutor<T> implements ExecuteChain<T> {

}
