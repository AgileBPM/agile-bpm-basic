package com.dstz.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.sys.api.service.SerialNoService;
import com.dstz.sys.core.manager.SerialNoManager;

/**
 *  流水号 接口实现
 */
@Service("serialNoService")
public class DefaultSerialNoService implements SerialNoService {

    @Resource
    SerialNoManager serialNoManager;

    @Override
    public String genNextNo(String alias) {
        return serialNoManager.nextId(alias);
    }

    @Override
    public String getPreviewNo(String alias) {
        return serialNoManager.getCurIdByAlias(alias);
    }
}
