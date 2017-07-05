package com.lhpc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 个人中心controller
 * @author YangGuang
 *
 */
@Controller
@RequestMapping(value="/wx")
public class PersonalCenterController {
	
	private static Logger log = Logger.getLogger(PersonalCenterController.class);

	/**
	 * 个人中心我的行程
	 */
	@ResponseBody
	@RequestMapping(value="personal/itinerary")
	public ResponseEntity<String> personalItinerary() {
		try {
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功");
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!");
		}
		
	}
}
