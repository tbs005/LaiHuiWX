package com.lhpc.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lhpc.model.Price;
import com.lhpc.model.Stroke;
import com.lhpc.service.IPriceService;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.DateUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.StringUtil;

/**
 * 车单模块
 * 
 * @author pangzhenpeng
 *
 */
@Controller
@RequestMapping(value = "/wx")
public class ItineraryController {

	@Autowired
	private ItineraryService itineraryService;
	@Autowired
	private IPriceService priceService;

	/**
	 * 查询前一个行程
	 */
	@ResponseBody
	@RequestMapping(value = "/select/previous/itinerary", method = RequestMethod.POST)
	public ResponseEntity<String> select(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Stroke> strokeList = new ArrayList<Stroke>();
		try {
			strokeList = itineraryService.selectStroke(1, 0);
		} catch (Exception e) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		if (strokeList.size() < 0) {
			return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据!");
		}
		map.put("stroke", strokeList.get(0));
		map.put("startTime",
				DateUtil.date2String(strokeList.get(0).getStartTime()));
		map.put("createTime",
				DateUtil.date2String(strokeList.get(0).getCreateTime()));
		map.put("updateTime",
				DateUtil.date2String(strokeList.get(0).getUpdateTime()));
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "行程查询成功!", map);
	}

	/**
	 * 发布车单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/release/itinerary", method = RequestMethod.POST)
	public ResponseEntity<String> releaseItinerary(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (itineraryService.selectStroke(1, 1).size() > 0) {
			return GsonUtil.getJson(ResponseCodeUtil.UNFINISHED_TRIP,
					"你有未完成行程!");
		}
		Stroke stroke = new Stroke();
		if (!ParamVerificationUtil.releaseItinerary(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整");
		}

		boolean success = itineraryService.insertSelective(request);
		if (!success) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		stroke = itineraryService.selectStroke(1, 1).get(0);
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
	@RequestMapping(value = "/unfinished/itinerary", method = RequestMethod.POST)
	public ResponseEntity<String> unfinishedItinerary(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Stroke> strokeList = new ArrayList<Stroke>();
		try {
			strokeList = itineraryService.selectStroke(1, 1);
		} catch (Exception e) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		if (strokeList.size() < 0) {
			return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据!");
		}
		map.put("stroke", strokeList.get(0));
		map.put("startTime",
				DateUtil.date2String(strokeList.get(0).getStartTime()));
		map.put("createTime",
				DateUtil.date2String(strokeList.get(0).getCreateTime()));
		map.put("updateTime",
				DateUtil.date2String(strokeList.get(0).getUpdateTime()));
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "未完成行程查询成功!", map);
	}

	/**
	 * 结束未完成行程
	 */
	@ResponseBody
	@RequestMapping(value = "/finished/itinerary", method = RequestMethod.POST)
	public ResponseEntity<String> finishedItinerary(HttpServletRequest request) {
		Stroke stroke = new Stroke();
		String strokeId = request.getParameter("strokeId");
		if (!StringUtil.isOrNotEmpty(strokeId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整");
		}
		stroke.setStrokeId(Integer.parseInt(strokeId));
		stroke.setIsEnable(0);
		int count = itineraryService.updateStroke(stroke);
		if (count == 0) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "系统错误!");
		}
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "行程结束成功!");

	}

	/**
	 * 行程价格接口
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/distance/prices", produces = "application/json; charset=utf-8")
	public ResponseEntity<?> getPriceToWeb(HttpServletRequest request) {
		JSONObject resultObject = new JSONObject();
		String startCode = request.getParameter("startCode");
		String endCode = request.getParameter("endCode");
		List<Price> pricesList = priceService.select4startCode(startCode,
				endCode);
		if (pricesList.size() > 0) {
			double price = pricesList.get(0).getPrice();
			resultObject.put("price", price);
			return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "价格获取成功",
					resultObject);
		}
		String origin_location = request.getParameter("origin_location");
		String destination_location = request
				.getParameter("destination_location");
		String booking_seats = request.getParameter("booking_seats");
		int person = 1;
		if (booking_seats != null && !booking_seats.isEmpty()) {
			try {
				person = Integer.parseInt(booking_seats);
			} catch (NumberFormatException e) {
				person = 1;
				e.printStackTrace();
			}
		}
		String result = "";

		URL file_url = null;
		try {
			String json_url = "http://restapi.amap.com/v3/distance?key=5f128c6b72fb65b81348ca1477f3c3ce&origins="
					+ origin_location
					+ "&destination="
					+ destination_location
					+ "&type=1";
			file_url = new URL(json_url);
			InputStream content = (InputStream) file_url.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					content, "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result = result + line;
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
		JSONObject dataObject = JSONObject.parseObject(result);
		JSONArray dataArray = dataObject.getJSONArray("results");
		if (dataArray.size() > 0) {
			JSONObject nowObject = dataArray.getJSONObject(0);
			int distance = nowObject.getIntValue("distance");
			int duration = nowObject.getIntValue("duration");

			double price = distance * 3.3 / 10000f;

			double last_price = price * person;
			DecimalFormat df = new DecimalFormat("######0.00");
			double average = price * 1000f / distance;
			resultObject.put("price",
					new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString());
			resultObject.put("total_price", new BigDecimal(last_price)
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			resultObject.put("cost_time", duration / 60 + "分钟");
			resultObject.put("distance", distance / 1000);
			resultObject.put("average", new BigDecimal(df.format(average))
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}

		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "价格计算成功",
				resultObject);
	}

}
