package com.lhpc.model;

import java.util.Date;

public class Order {
    private Integer orderId;

    private Integer bookedId;

    private String orderNum;

    private Date createTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookedId() {
        return bookedId;
    }

    public void setBookedId(Integer bookedId) {
        this.bookedId = bookedId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}