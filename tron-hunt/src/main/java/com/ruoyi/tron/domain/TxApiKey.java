package com.ruoyi.tron.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * 请求key对象 tx_api_key
 */
public class TxApiKey extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 请求密钥
     */
    @Excel(name = "请求密钥")
    private String apiKey;

    /**
     * 私钥
     */
    @Excel(name = "私钥")
    private String privateKey;

    /**
     * 状态(1可用，2不可用)
     */
    @Excel(name = "状态(1可用，2不可用)")
    private Integer status;

    /**
     * 可用次数
     */
    @Excel(name = "可用次数")
    private Long usable;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setUsable(Long usable) {
        this.usable = usable;
    }

    public Long getUsable() {
        return usable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("apiKey", getApiKey())
                .append("privateKey", getPrivateKey())
                .append("status", getStatus())
                .append("usable", getUsable())
                .append("createTime", getCreateTime())
                .append("remark", getRemark())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TxApiKey txApiKey = (TxApiKey) o;
        return Objects.equals(id, txApiKey.id) && Objects.equals(apiKey, txApiKey.apiKey) && Objects.equals(status, txApiKey.status) && Objects.equals(usable, txApiKey.usable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiKey, status, usable);
    }
}
