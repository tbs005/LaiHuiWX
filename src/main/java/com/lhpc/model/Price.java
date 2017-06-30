package com.lhpc.model;

import java.util.Date;

public class Price {
    private Integer priceId;

    private Integer startCode;

    private Integer endCode;

    private String startName;

    private String endName;

    private Double price;

    private Date createTime;

    private Date updateTime;

    private Boolean isEnable;

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getStartCode() {
        return startCode;
    }

    public void setStartCode(Integer startCode) {
        this.startCode = startCode;
    }

    public Integer getEndCode() {
        return endCode;
    }

    public void setEndCode(Integer endCode) {
        this.endCode = endCode;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName == null ? null : startName.trim();
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName == null ? null : endName.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }
}