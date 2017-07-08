package com.lhpc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lhpc.dao.OrderMapper;
import com.lhpc.model.Order;
import com.lhpc.service.IOrderService;

public class OrderServiceImpl implements IOrderService {
	
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public int insert(Order record) {
		record.setCreateTime(new Date());
		return orderMapper.insert(record);
	}

	@Override
	public Order selectByBookedId(Integer bookedId) {
		return orderMapper.selectByBookedId(bookedId);
	}

}
