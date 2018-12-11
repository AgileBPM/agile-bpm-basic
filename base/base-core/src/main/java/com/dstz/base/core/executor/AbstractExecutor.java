package com.dstz.base.core.executor;

import java.util.List;

import com.dstz.base.api.executor.Executor;
import com.dstz.base.api.executor.ExecutorType;
import com.dstz.base.api.executor.checker.ExecutorChecker;
import com.dstz.base.core.executor.checker.ExecutorCheckerService;
import com.dstz.base.core.executor.checker.FreeExecutorChecker;

/**
 * 执行器的抽象类
 *
 * @param <T>
 * @author aschs
 */
public abstract class AbstractExecutor<T> implements Executor<T> {
    /**
     * <pre>
     * 执行器的key
     * </pre>
     *
     * @return
     */
    @Override
    public String getKey() {
        return this.getClass().getSimpleName();
    }

    /**
     * <pre>
     * 执行器的名称
     * </pre>
     *
     * @return
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int compareTo(Executor<T> executor) {
        return Integer.valueOf(this.getSn()).compareTo(Integer.valueOf(executor.getSn()));
    }
    
    @Override
	public String type() {
    	//默认是必要执行器
		return ExecutorType.NECESSARY.getKey();
	}

	@Override
	public String getCheckerKey() {
		//默认免费
		return FreeExecutorChecker.class.getSimpleName();
	}
    
    /**
     * <pre>
     * 运行这个执行器
     * </pre>
     *
     * @param param
     */
    @Override
    public void execute(T param) {
        //1 先校验权限
        List<ExecutorChecker> checkers = ExecutorCheckerService.getCheckers(this.getCheckerKey());
        for (ExecutorChecker checker : checkers) {
            if (!checker.check(getKey())) {//一个不满足就结束
                return;
            }
        }
        this.run(param);//2 运行执行器
    }

    /**
     * <pre>
     * 子类中运行这个执行器
     * </pre>
     *
     * @param param 运行的参数
     */
    protected abstract void run(T param);
}
