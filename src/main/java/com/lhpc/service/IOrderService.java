package com.lhpc.service;

import com.lhpc.model.Order;

public interface IOrderService {
	
	int insert(Order record);

    Order selectByBookedId(Integer bookedId);

}
