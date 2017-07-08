package com.lhpc.controller;

import java.util.HashMap;
import java.util.List;
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

import com.lhpc.model.Stroke;
import com.lhpc.model.User;
import com.lhpc.service.IPersonalService;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.StringUtil;

/**
 * 个人中心controller
 * 
 * @author YangGuang
 *
 */
@Controller
@RequestMapping(value = "/wx")
public class PersonalCenterController {

	private static Logger log = Logger
			.getLogger(PersonalCenterController.class);

	@Autowired
	private ItineraryService itineraryService;
	@Autowired
	private IPersonalService personalService;

	@Autowired
	private HttpSession session;

	/**
	 * 个人中心我的行程
	 */
	@ResponseBody
	@RequestMapping(value = "personal/itinerary")
	public ResponseEntity<String> personalItinerary(HttpServletRequest request,
			Stroke stroke) {
		try {
			if (ParamVerificationUtil.personalItinerary(request)) {
				User user = (User) session.getAttribute("CURRENT_USER");
				List<Map<String, Object>> resultList = itineraryService
						.selectPersonalItineraryList(stroke, user);
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功",
						resultList);
			} else {
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil
					.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!");
		}

	}

	/**
	 * 个人中心信息
	 */
	@ResponseBody
	@RequestMapping(value = "personal/info", method = RequestMethod.POST)
	public ResponseEntity<String> info(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("CURRENT_USER");
		map.put("userName", user.getUserName());
		map.put("userMobile", user.getUserMobile());
		map.put("wallet", user.getWallet());
		map.put("carType", StringUtil.checkNull(user.getCarType()));
		map.put("CarLicense", StringUtil.checkNull(user.getCarLicense()));
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "个人信息获取成功!", map);
	}

	/**
	 * 车主行程详情
	 * 
	 * @param request
	 * @param strokeId
	 *            行程ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "personal/driverItineraryInfo", method = RequestMethod.POST)
	public ResponseEntity<String> driverItineraryInfo(HttpServletRequest request) {
		String openID = request.getParameter("openID");
		String strokeId = request.getParameter("strokeId");
		if (!StringUtil.isOrNotEmpty(openID)
				|| !StringUtil.isOrNotEmpty(strokeId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
		Map<String, Object> resultMap = itineraryService
				.getDriverItineraryInfo(strokeId);
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功", resultMap);
	}

	/**
	 * 乘客行程详情
	 * 
	 * @param request
	 * @param strokeId
	 *            行程ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "personal/passengerItineraryInfo", method = RequestMethod.POST)
	public ResponseEntity<String> passengerItineraryInfo(
			HttpServletRequest request) {
		String openID = request.getParameter("openID");
		String strokeId = request.getParameter("strokeId");
		if (!StringUtil.isOrNotEmpty(openID)
				|| !StringUtil.isOrNotEmpty(strokeId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
		Map<String, Object> resultMap = itineraryService
				.getPassengerItineraryInfo(strokeId);
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功", resultMap);
	}

	/**
	 * 获取车主行程
	 */
	@ResponseBody
	@RequestMapping(value = "personal/itineraryDetail", method = RequestMethod.POST)
	public ResponseEntity<String> personalItineraryDetail(String strokeId,
			String openID) {
		if (ParamVerificationUtil.personalItineraryDetail(strokeId, openID)) {
			Map<String, Object> resultMap = itineraryService
					.getPassengerItineraryDetail(strokeId);
			if (resultMap.size() > 0)
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功!",
						resultMap);
			else
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据!");
		} else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
	}

	/**
	 * 车主编辑行程
	 */
	@ResponseBody
	@RequestMapping(value = "personal/itineraryEdit", method = RequestMethod.POST)
	public ResponseEntity<String> personalItineraryEdit(
			HttpServletRequest request) {
		if (ParamVerificationUtil.personalItineraryEdit(request)) {
			return itineraryService.personalItineraryEdit(request);
		} else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
	}

	/**
	 * 车主结束行程（多个乘客挨个结束）
	 * 
	 * @param request
	 * @param bookedId
	 *            ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "personal/closeItinerary", method = RequestMethod.POST)
	public ResponseEntity<String> closeItinerary(HttpServletRequest request) {
		String openID = request.getParameter("openID");
		String bookedId = request.getParameter("bookedId");
		if (!StringUtil.isOrNotEmpty(openID)
				|| !StringUtil.isOrNotEmpty(bookedId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
		itineraryService.closeItinerary(bookedId);
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "操作成功");
	}

	/**
	 * 车主同意乘客预定
	 */
	@ResponseBody
	@RequestMapping(value="/agreed/book",method=RequestMethod.POST)
	public ResponseEntity<String> agreedBook(HttpServletRequest request,String bookedId,String strokeId){
		if (!ParamVerificationUtil.agreedBook(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
		return personalService.agreedBook( bookedId,strokeId);
	}

	/**
	 * 车主拒绝乘客预定
	 */
	@ResponseBody
	@RequestMapping(value="/denial/book",method=RequestMethod.POST)
	public ResponseEntity<String> denialBook(HttpServletRequest request,String bookedId,String strokeId){
		String openID = request.getParameter("openID");
		if (!StringUtil.isOrNotEmpty(openID) || !StringUtil.isOrNotEmpty(bookedId)
				|| !StringUtil.isOrNotEmpty(strokeId)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,"参数不完整!");
		}
		return personalService.denialBook(bookedId,strokeId);
	}
	
	/**
	 * 提现
	 */
	@ResponseBody
	@RequestMapping(value="/extract/cash",method=RequestMethod.POST)
	public ResponseEntity<String> extractCash(String openID,String money){
		if (!StringUtil.isOrNotEmpty(openID) || !StringUtil.isOrNotEmpty(money)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,"参数不完整!");
		}
		return personalService.extractCash(money,openID);
	}
}
