/**
 * 描述：TODO
 * 包名：com.dstz.platform.customquery.service
 * 文件名：CustomQueryService.java
 */
package com.dstz.form.api.service;

import java.util.List;

public interface CustomQueryService {

    public List<?> getAllDataByAlias(String alias);

    public List<?> getAll();
}
