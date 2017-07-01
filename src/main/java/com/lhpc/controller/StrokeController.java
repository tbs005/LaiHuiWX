package com.lhpc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.Stroke;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 行程Controller
 * @author YangGuang
 */
@Controller
public class StrokeController {
	
	private static Logger log = Logger.getLogger(StrokeController.class);
	
	@Autowired
	private ItineraryService itineraryService;
	
	/**
	 * 跨城车辆
	 */
	@ResponseBody
	@RequestMapping("cross/city")
	public ResponseEntity<String> crossCity(HttpServletRequest request,Stroke stroke) {
		try {
			//跨城车辆列表
			List<Stroke> crossCityList = itineraryService.selectCrossCityList(stroke);
			if (crossCityList.size()>0) {
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				for (Stroke s : crossCityList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("startCity", s.getStartCity());
					map.put("startCityCode", s.getStartCityCode());
					map.put("endCity", s.getEndCity());
					map.put("endCityCode", s.getEndCityCode());
					map.put("count", itineraryService.selectCrossCityCount(s));
					list.add(map);
				}
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功",list); 
			}else {
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据"); 
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!"); 
		}
	}
}
