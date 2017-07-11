package com.lhpc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.Booked;
import com.lhpc.service.IPassengerService;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.StringUtil;

/**
 * 乘客操作模块
 * 
 * @author pangzhenpeng
 *
 */
@Controller
@RequestMapping(value = "/wx")
public class PassengerController {

	@Autowired
	private IPassengerService passengerService;

	@Autowired
	private ItineraryService itineraryService;

	/**
	 * 乘客预定行程
	 * 
	 * @param booked
	 *            接收参数对象
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scheduled/travel", method = RequestMethod.POST)
	public ResponseEntity<String> scheduled(Booked booked,
			HttpServletRequest request) {
		if (!ParamVerificationUtil.scheduledTravel(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
		return passengerService.scheduled(booked);
	}

	/**
	 * 乘客退订
	 */
	@ResponseBody
	@RequestMapping(value = "/unsubscribe/travel")
	public ResponseEntity<String> unsubscribe(HttpServletRequest request,
			String bookedId) {
		if (!ParamVerificationUtil.unsubscribeTravel(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
		return passengerService.unsubscribeTravel(Integer.parseInt(bookedId));
	}
	
	/**
	 * 乘客同意或拒绝车主页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "passenger/isAgree", method = RequestMethod.POST)
	public ResponseEntity<String> passengerIsAgree(
			HttpServletRequest request) {
		String openID = request.getParameter("openID");
		String strokeId = request.getParameter("strokeId");
		String bookedId = request.getParameter("bookedId");
		if (!StringUtil.isOrNotEmpty(openID)
				|| !StringUtil.isOrNotEmpty(strokeId)
				|| !StringUtil.isOrNotEmpty(bookedId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
		Map<String, Object> resultMap = itineraryService
				.passengerIsAgree(strokeId,bookedId);
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功", resultMap);
	}
}
