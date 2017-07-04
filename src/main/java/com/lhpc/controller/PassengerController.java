package com.lhpc.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.Booked;
import com.lhpc.service.IPassengerService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 乘客操作模块
 * @author pangzhenpeng
 *
 */
@Controller
@RequestMapping(value="/wx")
public class PassengerController {

	@Autowired
	private IPassengerService passengerService;
	
	@ResponseBody
	@RequestMapping(value="/scheduled/travel",method =RequestMethod.POST)
	public ResponseEntity<String> scheduled(Booked booked,HttpServletRequest request){
		if (!ParamVerificationUtil.scheduledTravel(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
		return passengerService.scheduled(booked);
	}
}
