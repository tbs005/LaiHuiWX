package com.lhpc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lhpc.model.Stroke;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.DateUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.StringUtil;

/**
 * 车单模块
 * @author pangzhenpeng
 *
 */
@Controller
@RequestMapping(value = "/wx")
public class ItineraryController {

	@Autowired
	private ItineraryService itineraryService;
	
	/**
	 * 发布车单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/release/itinerary", method = RequestMethod.POST)
	public ResponseEntity<String> releaseItinerary(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (itineraryService.selectStroke(1,1).size()>0) {
			return GsonUtil.getJson(ResponseCodeUtil.UNFINISHED_TRIP, "你有未完成行程!");
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
		map.put("stroke", stroke);
		map.put("startTime", DateUtil.date2String(stroke.getStartTime()));
		map.put("createTime", DateUtil.date2String(stroke.getCreateTime()));
		map.put("updateTime", DateUtil.date2String(stroke.getUpdateTime()));
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "行程发布成功!", map);
		

	}
	
	/**
	 * 查询未完成行程
	 */
	@ResponseBody
	@RequestMapping(value="/unfinished/itinerary",method=RequestMethod.POST)
	public ResponseEntity<String> unfinishedItinerary(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Stroke> strokeList = new ArrayList<Stroke>();
		try {
			strokeList = itineraryService.selectStroke(1,1);
		} catch (Exception e) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		if (strokeList.size()<0) {
			return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据!");
		}
		map.put("stroke", strokeList.get(0));
		map.put("startTime", DateUtil.date2String(strokeList.get(0).getStartTime()));
		map.put("createTime", DateUtil.date2String(strokeList.get(0).getCreateTime()));
		map.put("updateTime", DateUtil.date2String(strokeList.get(0).getUpdateTime()));
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "未完成行程查询成功!",map); 
	}

	/**
	 * 结束未完成行程
	 */
	@ResponseBody
	@RequestMapping(value="/finished/itinerary",method=RequestMethod.POST)
	public ResponseEntity<String> finishedItinerary(HttpServletRequest request){
		Stroke stroke = new Stroke();
		String strokeId = request.getParameter("strokeId");
		if (!StringUtil.isOrNotEmpty(strokeId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整");
		}
		stroke.setStrokeId(Integer.parseInt(strokeId));
		stroke.setIsEnable(0);
		int count = itineraryService.updateStroke(stroke);
		if (count==0) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "行程结束成功!");
		
	}
}
