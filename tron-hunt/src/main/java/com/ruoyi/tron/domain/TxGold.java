package com.ruoyi.tron.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 目标账号对象 tx_gold
 */
public class TxGold extends BaseEntity {
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
     * 私钥
     */
    @Excel(name = "私钥")
    private String privateKey;

    /**
     * 状态(1未处理，2已处理)
     */
    @Excel(name = "状态(1未处理，2已处理)")
    private Integer status;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * TRX余额
     */
    private BigDecimal trx;

    /**
     * TRX质押
     */
    private BigDecimal frozenTrx;

    /**
     * usdt余额
     */
    private BigDecimal usdt;

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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getTrx() {
        return trx;
    }

    public void setTrx(BigDecimal trx) {
        this.trx = trx;
    }

    public BigDecimal getFrozenTrx() {
        return frozenTrx;
    }

    public void setFrozenTrx(BigDecimal frozenTrx) {
        this.frozenTrx = frozenTrx;
    }

    public BigDecimal getUsdt() {
        return usdt;
    }

    public void setUsdt(BigDecimal usdt) {
        this.usdt = usdt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("address", getAddress())
                .append("privateKey", getPrivateKey())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
