package com.lhpc.dao;

import com.lhpc.model.Order;

public interface OrderMapper {

    int insert(Order record);

    Order selectByBookedId(Integer bookedId);
}