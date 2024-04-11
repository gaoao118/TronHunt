package com.ruoyi.tron.domain.dto;

import java.io.Serializable;

public class ApiKeyDto implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 请求密钥
     */
    private String apiKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 可用次数
     */
    private Long usable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Long getUsable() {
        return usable;
    }

    public void setUsable(Long usable) {
        this.usable = usable;
    }
}
