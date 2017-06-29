package com.lhpc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.Stroke;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;

@Controller
@RequestMapping(value = "/wx")
public class ItineraryController {

	@Autowired
	private ItineraryService itineraryService;
	
	@ResponseBody
	@RequestMapping(value = "/release/itinerary", method = RequestMethod.POST)
	public ResponseEntity<String> releaseItinerary(HttpServletRequest request) {
		if (itineraryService.selectStroke(1,1).size()>0) {
			return GsonUtil.getJson(ResponseCodeUtil.UNFINISHED_TRIP, "你有一个未完成行程!");
		}
		Stroke stroke = new Stroke();
		if (!ParamVerificationUtil.releaseItinerary(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整");
		}
		
		boolean success = itineraryService.insertSelective(request);
		if (!success) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		stroke = itineraryService.selectStroke(1,1).get(0);
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "行程发布成功", stroke);
		

	}

}
