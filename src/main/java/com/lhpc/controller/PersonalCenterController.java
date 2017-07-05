package com.lhpc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.User;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.HttpRequest;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.StringUtil;

/**
 * 个人中心controller
 * @author YangGuang
 *
 */
@Controller
@RequestMapping(value="/wx")
public class PersonalCenterController {
	
	private static Logger log = Logger.getLogger(PersonalCenterController.class);
	@Autowired
	private HttpSession session;
	
	
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
	
	@ResponseBody
	@RequestMapping(value="personal/info",method=RequestMethod.POST)
	public ResponseEntity<String> info(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("CURRENT_USER");
		map.put("userName", user.getUserName());
		map.put("userMobile", user.getUserMobile());
		map.put("wallet", user.getWallet());
		map.put("carType", StringUtil.checkNull(user.getCarType()));
		map.put("CarLicense", StringUtil.checkNull(user.getCarLicense()));
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "个人信息获取成功!", map);
		
		
	}
}
