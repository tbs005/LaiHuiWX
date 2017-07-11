package com.lhpc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.HttpRequest;

@Controller
public class GetCityCodeController {

	@ResponseBody
	@RequestMapping(value = "getCity/info")
	public JSONObject getCityInfo(HttpServletRequest request) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
		String location = request.getParameter("location");
		String info = HttpRequest.sendGet(
				"https://apis.map.qq.com/ws/geocoder/v1/", "location="
						+ location + "&get_poi=1&key="
						+ ConfigUtil.TENCENT_APP_KEY);
		JSONObject jsonObject = JSONObject.parseObject(info);
		return jsonObject;

	}
}
