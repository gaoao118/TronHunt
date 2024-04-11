package com.ruoyi.tron.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 波场靓号对象 tx_good_address
 */
public class TxGoodAddress extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 波场地址
     */
    @Excel(name = "波场地址")
    private String address;

    /**
     * 16进制地址
     */
    @Excel(name = "16进制地址")
    private String hexAddress;

    /**
     * 私钥
     */
    @Excel(name = "私钥")
    private String privateKey;

    /**
     * 连号长度
     */
    @Excel(name = "连号长度")
    private Integer length;

    /**
     * 删除标识
     */
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setHexAddress(String hexAddress) {
        this.hexAddress = hexAddress;
    }

    public String getHexAddress() {
        return hexAddress;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return length;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("address", getAddress())
                .append("hexAddress", getHexAddress())
                .append("privateKey", getPrivateKey())
                .append("length", getLength())
                .append("createTime", getCreateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
