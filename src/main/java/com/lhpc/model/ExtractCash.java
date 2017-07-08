package com.lhpc.model;

import java.util.Date;

public class ExtractCash {
    private Integer extractId;

    private Integer userId;

    private Double extractMoney;

    private Date createTime;

    private Integer extractStatus;

    public Integer getExtractId() {
        return extractId;
    }

    public void setExtractId(Integer extractId) {
        this.extractId = extractId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getExtractMoney() {
        return extractMoney;
    }

    public void setExtractMoney(Double extractMoney) {
        this.extractMoney = extractMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExtractStatus() {
        return extractStatus;
    }

    public void setExtractStatus(Integer extractStatus) {
        this.extractStatus = extractStatus;
    }
}