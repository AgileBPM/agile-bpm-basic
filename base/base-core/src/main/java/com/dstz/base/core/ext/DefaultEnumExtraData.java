package com.dstz.base.core.ext;

import com.dstz.base.core.model.EnumExtraData;

public class DefaultEnumExtraData implements EnumExtraData {

    private String name;

    private String key;

    private String desc;

    public static DefaultEnumExtraData newInstance(String name, String key, String desc) {
        DefaultEnumExtraData defaultEnumExtraData = new DefaultEnumExtraData();
        defaultEnumExtraData.name = name;
        defaultEnumExtraData.key = key;
        defaultEnumExtraData.desc = desc;
        return defaultEnumExtraData;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
