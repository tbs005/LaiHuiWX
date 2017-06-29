package com.lhpc.model;

import java.util.Date;

public class Booked {
    private Integer bookedId;

    private Integer strokeId;

    private Integer userId;

    private Date bookedTime;

    private Integer bookedSeats;

    private String upAddress;

    private String downAddress;

    private String message;

    private Date unbookedTime;

    private String unbookedReason;

    private Integer unbookedFlag;

    private Integer unbookedId;

    private Integer isEnable;

    public Integer getBookedId() {
        return bookedId;
    }

    public void setBookedId(Integer bookedId) {
        this.bookedId = bookedId;
    }

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

    public Date getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(Date bookedTime) {
        this.bookedTime = bookedTime;
    }

    public Integer getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(Integer bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public String getUpAddress() {
        return upAddress;
    }

    public void setUpAddress(String upAddress) {
        this.upAddress = upAddress == null ? null : upAddress.trim();
    }

    public String getDownAddress() {
        return downAddress;
    }

    public void setDownAddress(String downAddress) {
        this.downAddress = downAddress == null ? null : downAddress.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getUnbookedTime() {
        return unbookedTime;
    }

    public void setUnbookedTime(Date unbookedTime) {
        this.unbookedTime = unbookedTime;
    }

    public String getUnbookedReason() {
        return unbookedReason;
    }

    public void setUnbookedReason(String unbookedReason) {
        this.unbookedReason = unbookedReason == null ? null : unbookedReason.trim();
    }

    public Integer getUnbookedFlag() {
        return unbookedFlag;
    }

    public void setUnbookedFlag(Integer unbookedFlag) {
        this.unbookedFlag = unbookedFlag;
    }

    public Integer getUnbookedId() {
        return unbookedId;
    }

    public void setUnbookedId(Integer unbookedId) {
        this.unbookedId = unbookedId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}