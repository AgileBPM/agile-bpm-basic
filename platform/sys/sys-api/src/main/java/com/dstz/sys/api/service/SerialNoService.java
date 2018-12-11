package com.dstz.sys.api.service;

import java.text.ParseException;

/**
 * 流水号服务。
 * <pre>
 * </pre>
 */
public interface SerialNoService {

    /**
     * 根据别名获取下一个流水号。
     *
     * @param alias
     * @return String
     * @throws ParseException
     */
    String genNextNo(String alias);

    /**
     * 根据别名取得预览的流水号。
     *
     * @param alias
     * @return String
     */
    String getPreviewNo(String alias);

}
