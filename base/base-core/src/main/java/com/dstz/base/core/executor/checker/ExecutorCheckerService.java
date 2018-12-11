package com.dstz.base.core.executor.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dstz.base.api.executor.checker.ExecutorChecker;
import com.dstz.base.core.util.AppUtil;

/**
 * 执行器校验器的服务类
 *
 * @author aschs
 */
public class ExecutorCheckerService {
    /**
     * 校验器map
     */
    private static Map<String, ExecutorChecker> checkerMap = new HashMap<>();

    /**
     * 私有化构建方法
     */
    private ExecutorCheckerService() {

    }

    /**
     * <pre>
     * 获取指定校验器
     * </pre>
     *
     * @param key
     * @return
     */
    public static ExecutorChecker getChecker(String key) {
        //初始化校验器
        if (checkerMap.isEmpty()) {
            Map<String, ExecutorChecker> map = AppUtil.getImplInstance(ExecutorChecker.class);
            for (Entry<String, ExecutorChecker> entry : map.entrySet()) {
                ExecutorChecker checker = entry.getValue();
                checkerMap.put(checker.getKey(), checker);
            }
        }
        if (checkerMap.get(key) == null) {
            throw new RuntimeException("找不到执行器校验器[" + key + "]");
        } else {
            return checkerMap.get(key);
        }
    }

    public static List<ExecutorChecker> getCheckers(String keys) {
        List<ExecutorChecker> checkers = new ArrayList<>();
        for (String key : keys.split(",")) {
            checkers.add(getChecker(key));
        }
        return checkers;
    }
}
