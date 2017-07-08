package com.lhpc.util;

import java.util.UUID;

import com.lhpc.model.User;

/**
 * 生成订单编号
 * 
 * @author YangGuang
 *
 */
public class OrderNumUtil {

	private OrderNumUtil() {
	}

	public static String getOrderNum(User user) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String time = String.valueOf(System.currentTimeMillis());
		String orderNum = "" +  uuid + time + user.getUserId();
		return orderNum;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setUserId(1);
		System.out.println(getOrderNum(user));
	}
}
