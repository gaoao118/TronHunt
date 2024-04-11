package com.ruoyi.common.enums;

/**
 * 分布式锁枚举
 */
public enum LockEnum {
    TRON_SET_API_KEY_LOCK("tron.lock.apiKeyList", "插入请求key集合加锁"),

    ;
    private final String code;
    private final String info;

    LockEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
