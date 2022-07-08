package com.dstz.org.api.context;

import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;

import java.util.Locale;

/**
 * 获取上下文对象数据。
 */
public interface ICurrentContext {

    /**
     * 当前岗位
     */
    public static final String CURRENT_ORG = "current_org";

    /**
     * 获取当当前登录用户
     *
     * @return User
     */
    IUser getCurrentUser();

    /**
     * 获取当前执行人
     *
     * @return String
     */
    String getCurrentUserId();

    /**
     * 获取当前组织
     */
    IGroup getCurrentGroup();

    /**
     * 清理当前用户
     */
    void clearCurrentUser();

    /**
     * 设置当前用户。
     *
     * @param user
     */
    void setCurrentUser(IUser user);

    /**
     * 根据用户帐号设置上下文用户。
     *
     * @param account 帐号。
     */
    void setCurrentUserByAccount(String account);

    /**
     * 获取当前组织。
     */
    void cacheCurrentGroup(IGroup group);

    /**
     * 获取当前Locale。
     *
     * @return Locale
     */
    Locale getLocale();

    /**
     * 设置上下文local。
     *
     * @param local
     */
    void setLocale(Locale local);

    /**
     * 清除上下文local。
     */
    void clearLocale();

}
