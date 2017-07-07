package com.lhpc.service.impl;


import java.util.Date;

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
import com.lhpc.service.IPersonalService;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.SendSMSUtil;

@Service
@Transactional
public class PersonalServiceImpl implements IPersonalService {

	private Logger logger = Logger.getLogger(PersonalServiceImpl.class);
	@Autowired
	private BookedMapper bookedMapper;
	@Autowired
	private StrokeMapper strokeMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private HttpSession session;

	@Override
	public ResponseEntity<String> agreedBook(String bookedId, String strokeId) {

		Stroke stroke = strokeMapper.selectByPrimaryKey(Integer
				.parseInt(strokeId));
		Booked booked = bookedMapper.selectByPrimaryKey(Integer
				.parseInt(bookedId));
		if (stroke.getSeats() < booked.getBookedSeats()) {
			return GsonUtil.getJson(ResponseCodeUtil.SEAT_LACK,
					"座位数不足,请选择其他预定单!");
		}
		int seats = -booked.getBookedSeats();
		int count = strokeMapper
				.update4Seats(Integer.parseInt(strokeId), seats);
		if (count == 0) {
			logger.error("车主同意乘客预定修改座位数失败!----------------------");
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
					"服务器繁忙,请稍后重试!");
		}

		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "你已同意该预定!");
	}

	@Override
	public ResponseEntity<String> denialBook(String bookedId, String strokeId) {
		User user = (User) session.getAttribute("CURRENT_USER");
		Stroke stroke = strokeMapper.selectByPrimaryKey(Integer.parseInt(strokeId));
		Booked booked = bookedMapper.selectByPrimaryKey(Integer.parseInt(bookedId));
		int result = 0;
		if(booked!=null && stroke!=null){
			booked.setUnbookedTime(new Date());
			booked.setUnbookedFlag(1);
			booked.setUnbookedId(stroke.getUserId());
			booked.setIsEnable(0);
			result = bookedMapper.updateByPrimaryKeySelective(booked);
		}
		if(result>0){
			User passenger = userMapper.selectByPrimaryKey(booked.getUserId());
			
			SendSMSUtil.sendSMS(passenger.getUserMobile(), ConfigUtil.DENIAL_BOOK,"#name#="+user.getUserName());
			return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "拒绝成功!");
		}else{
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,"服务器繁忙,请稍后重试!");
		}
	}

}
