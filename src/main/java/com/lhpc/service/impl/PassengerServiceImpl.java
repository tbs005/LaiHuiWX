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
import org.springframework.transaction.annotation.Transactional;

import com.lhpc.dao.BookedMapper;
import com.lhpc.dao.StrokeMapper;
import com.lhpc.dao.UserMapper;
import com.lhpc.model.Booked;
import com.lhpc.model.Stroke;
import com.lhpc.model.User;
import com.lhpc.service.IPassengerService;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.SendSMSUtil;

@Service
@Transactional
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

		}
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("CURRENT_USER");
		booked.setUserId(user.getUserId());
		booked.setBookedTime(new Date());
		List<Booked> bookedList = bookedMapper.selectBystrokeId(user
				.getUserId());
		if (bookedList.size() > 0) {
			return GsonUtil.getJson(ResponseCodeUtil.BOOKING_REPEAT,
					"你已经预定过车单了,请先取消已预订车单在预约其他车单!");
		}
		stroke = strokeMapper.selectByPrimaryKey(booked.getStrokeId());
		if (stroke.getSeats() < booked.getBookedSeats()) {
			return GsonUtil.getJson(ResponseCodeUtil.SEAT_LACK,
					"该车单座位不足,请重新选择其它车单!");
		}
		int count = bookedMapper.insertSelective(booked);
		if (count == 0) {
			logger.error("预定行程插入数据失败 ------------------ ");
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
					"服务器繁忙,请稍后重试!");
		}

		User driver = new User();
		try {

			driver = userMapper.selectByPrimaryKey(stroke.getUserId());
		} catch (Exception e) {
			logger.error("查询数据失败 -------------------- ");
			logger.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
					"服务器繁忙,请稍后重试!");
		}
		map.put("driverMobile", driver.getUserMobile());
		return GsonUtil
				.getJson(ResponseCodeUtil.SUCCESS, "预订成功,请尽快与车主联系!", map);
	}

	@Override
	public ResponseEntity<String> unsubscribeTravel(int bookedId) {
		User user = (User) session.getAttribute("CURRENT_USER");
		Booked booked = bookedMapper.selectByPrimaryKey(bookedId);
		if (strokeMapper.update4Seats(booked.getStrokeId(),
				booked.getBookedSeats()) == 0) {
			logger.error("乘客退订行程更新车主行程座位数失败--------------------------");
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
					"服务器繁忙,请稍后重试!");
		}
		booked.setIsEnable(2);
		booked.setUnbookedTime(new Date());
		booked.setUnbookedId(user.getUserId());
		booked.setUnbookedFlag(0);
		if (bookedMapper.updateByPrimaryKeySelective(booked) == 0) {
			logger.error("乘客退订修改退订记录状态失败--------------------------");
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
					"服务器繁忙,请稍后重试!");
		}
		User driver = new User();
		try {
			driver = userMapper.selectByPrimaryKey(strokeMapper
					.selectByPrimaryKey(booked.getStrokeId()).getUserId());
			SendSMSUtil.sendSMS(driver.getUserMobile(),
					ConfigUtil.UNSUBSCRIBE_TRAVEL, "#name#="+driver.getUserName());
		} catch (Exception e) {
			logger.error("查询车主信息失败,推送失败!--------------------------");
		}

		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "退订成功!");
	}

	

}
