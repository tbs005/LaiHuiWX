package com.lhpc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lhpc.dao.BookedMapper;
import com.lhpc.dao.StrokeMapper;
import com.lhpc.dao.UserMapper;
import com.lhpc.model.Booked;
import com.lhpc.model.Stroke;
import com.lhpc.model.User;
import com.lhpc.service.IPassengerService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;

@Service
public class PassengerServiceImpl implements IPassengerService {

	private Logger logger = Logger.getLogger(PassengerServiceImpl.class);
	@Autowired
	private HttpSession session;
	@Autowired
	private BookedMapper bookedMapper;
	@Autowired
	private StrokeMapper strokeMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseEntity<String> scheduled(Booked booked) {
		Stroke stroke = new Stroke();
		
		try {
			strokeMapper.update4AccessCount(booked.getStrokeId());
		} catch (Exception e) {
			logger.error("访问次数修改异常 ----------------- ");
			logger.error(e.getMessage());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("CURRENT_USER");
		booked.setUserId(user.getUserId());
		booked.setBookedTime(new Date());
		List<Booked> bookedList = bookedMapper.selectBystrokeId(
				user.getUserId(), booked.getStrokeId(), 1);
		if (bookedList.size() > 0) {
			return GsonUtil.getJson(ResponseCodeUtil.BOOKING_REPEAT,
					"你已经预定过该车单了,请预定其它车单!");
		}
		stroke = strokeMapper.selectByPrimaryKey(booked.getStrokeId());
		if (stroke.getSeats() < booked.getBookedSeats()) {
			return GsonUtil.getJson(ResponseCodeUtil.SEAT_LACK,
					"该车单座位不足,请重新选择其它车单!");
		}
		int count = bookedMapper.insertSelective(booked);
		if (count == 0) {
			logger.error("预定行程插入数据失败 ------------------ ");
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}

		User driver = new User();
		try {

			driver = userMapper.selectByPrimaryKey(stroke.getUserId());
		} catch (Exception e) {
			logger.error("查询数据失败 -------------------- ");
			logger.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		map.put("driverMobile", driver.getUserMobile());
		return GsonUtil
				.getJson(ResponseCodeUtil.SUCCESS, "预订成功,请尽快与车主联系!", map);
	}

}
