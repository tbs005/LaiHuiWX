package com.lhpc.model;

import java.util.Date;

public class Stroke {
    private Integer strokeId;

    private Integer userId;

    private String startCity;

    private Integer startCityCode;

    private String startAddress;

    private String endCity;

    private Integer endCityCode;

    private String endAddress;

    private Double price;

    private Date startTime;

    private String carType;

    private Integer seats;

    private String strokeRoute;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer isEnable;

    public Integer getStrokeId() {
        return strokeId;
    }

    public void setStrokeId(Integer strokeId) {
        this.strokeId = strokeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity == null ? null : startCity.trim();
    }

    public Integer getStartCityCode() {
        return startCityCode;
    }

    public void setStartCityCode(Integer startCityCode) {
        this.startCityCode = startCityCode;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress == null ? null : startAddress.trim();
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity == null ? null : endCity.trim();
    }

    public Integer getEndCityCode() {
        return endCityCode;
    }

    public void setEndCityCode(Integer endCityCode) {
        this.endCityCode = endCityCode;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress == null ? null : endAddress.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getStrokeRoute() {
        return strokeRoute;
    }

    public void setStrokeRoute(String strokeRoute) {
        this.strokeRoute = strokeRoute == null ? null : strokeRoute.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}