package com.lhpc.model;

import java.util.Date;

public class Flow {
    private Integer flowId;

    private Integer userId;

    private Integer bookedId;

    private Integer flowType;

    private Double flowAmount;

    private Date flowTime;

    private String flowContent;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookedId() {
        return bookedId;
    }

    public void setBookedId(Integer bookedId) {
        this.bookedId = bookedId;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public Double getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(Double flowAmount) {
        this.flowAmount = flowAmount;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public String getFlowContent() {
        return flowContent;
    }

    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent == null ? null : flowContent.trim();
    }
}