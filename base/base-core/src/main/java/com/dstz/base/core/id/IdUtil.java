package com.dstz.base.core.id;

/**
 * 获取ID 工具类 
 * 具体参考 idGenerator 实现类
 */
public class IdUtil {

    private static IdGenerator idGenerator;

    public void setIdGenerator(IdGenerator idGenerator_) {
        idGenerator = idGenerator_;
    }

    /**
     * 获取long型的ID.
     *
     * @return Long
     * @throws
     * @since 1.0.0
     */
    public static Long getUId() {
        return idGenerator.getUId();
    }

    /**
     * 获取字符型的ID
     *
     * @return
     */
    public static String getSuid() {
        return idGenerator.getSuid();
    }
}
