package com.lhpc.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数验证类
 * 
 * @author pangzhenpeng
 *
 */
public class ParamVerificationUtil {
	private ParamVerificationUtil() {

	}

	/**
	 * 发布行程参数验证
	 */
	public static boolean releaseItinerary(HttpServletRequest request) {
		boolean flag = false;
		String startCity = request.getParameter("startCity");
		String startLongitude = request.getParameter("startLongitude");
		String startLatitude = request.getParameter("startLatitude");
		String endLongitude = request.getParameter("endLongitude");
		String endLatitude = request.getParameter("endLatitude");
		String startCityCode = request.getParameter("startCityCode");
		String startAddress = request.getParameter("startAddress");
		String endCity = request.getParameter("endCity");
		String endCityCode = request.getParameter("endCityCode");
		String endAddress = request.getParameter("endAddress");
		String price = request.getParameter("price");
		String startTime = request.getParameter("startTime");
		String carType = request.getParameter("carType");
		String seats = request.getParameter("seats");
		String strokeRoute = request.getParameter("strokeRoute");
		if (StringUtil.isOrNotEmpty(startCity)
				&& StringUtil.isOrNotEmpty(startLongitude)
				&& StringUtil.isOrNotEmpty(startLatitude)
				&& StringUtil.isOrNotEmpty(endLongitude)
				&& StringUtil.isOrNotEmpty(endLatitude)
				&& StringUtil.isOrNotEmpty(startAddress)
				&& StringUtil.isOrNotEmpty(startCityCode)
				&& StringUtil.isOrNotEmpty(endCity)
				&& StringUtil.isOrNotEmpty(endCityCode)
				&& StringUtil.isOrNotEmpty(endAddress)
				&& StringUtil.isOrNotEmpty(price)
				&& StringUtil.isOrNotEmpty(startTime)
				&& StringUtil.isOrNotEmpty(carType)
				&& StringUtil.isOrNotEmpty(seats)
				&& StringUtil.isOrNotEmpty(strokeRoute)) {
			flag = true;

		}
		return flag;
	}

	/**
	 * 用户登录参数验证
	 */
	public static boolean userLogin(HttpServletRequest request) {
		boolean flag = false;
		String mobile = request.getParameter("mobile");
		String code = request.getParameter("code");
		String userName = request.getParameter("userName");
		String openID = request.getParameter("openID");
		String userType = request.getParameter("userType");
		if (StringUtil.isOrNotEmpty(mobile) && StringUtil.isOrNotEmpty(code)
				&& StringUtil.isOrNotEmpty(userName)
				&& StringUtil.isOrNotEmpty(openID)
				&& StringUtil.isOrNotEmpty(userType))
			flag = true;
		return flag;
	}

	/**
	 * 司机登录参数验证
	 */
	public static boolean driverLogin(HttpServletRequest request) {
		boolean flag = false;
		String carType = request.getParameter("carType");
		String carLicense = request.getParameter("carLicense");
		if (StringUtil.isOrNotEmpty(carType)
				&& StringUtil.isOrNotEmpty(carLicense))
			flag = true;
		return flag;
	}

	/**
	 * 发送短信验证码参数验证
	 */
	public static boolean sendSmsCode(HttpServletRequest request) {
		boolean flag = false;
		String mobile = request.getParameter("mobile");
		if (StringUtil.isOrNotEmpty(mobile) && Regex.isPhoneLegal(mobile))
			flag = true;
		return flag;
	}

	/**
	 * 乘客查询车主行程列表
	 */
	public static boolean selectSearchStrokeList(HttpServletRequest request) {
		boolean flag = false;
		String startCityCode = request.getParameter("startCityCode");
		String endCityCode = request.getParameter("endCityCode");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		if (StringUtil.isOrNotEmpty(startCityCode)
				&& StringUtil.isOrNotEmpty(endCityCode)
				&& StringUtil.isOrNotEmpty(page)
				&& StringUtil.isOrNotEmpty(size))
			flag = true;
		return flag;
	}

	/**
	 * 乘客预定行程参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean scheduledTravel(HttpServletRequest request) {
		boolean flag = false;

		String strokeId = request.getParameter("strokeId");// 车主行程ID
		String bookedSeats = request.getParameter("bookedSeats");// 预定座位
		String upAddress = request.getParameter("upAddress");// 上车地址
		String downAddress = request.getParameter("downAddress");// 下车地址
		if (StringUtil.isOrNotEmpty(strokeId)
				&& StringUtil.isOrNotEmpty(bookedSeats)
				&& StringUtil.isOrNotEmpty(upAddress)
				&& StringUtil.isOrNotEmpty(downAddress)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 我的行程参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean personalItinerary(HttpServletRequest request) {
		boolean flag = false;

		String openID = request.getParameter("openID");
		String mark = request.getParameter("mark");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		if (StringUtil.isOrNotEmpty(openID) && StringUtil.isOrNotEmpty(mark)
				&& StringUtil.isOrNotEmpty(page)
				&& StringUtil.isOrNotEmpty(size)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 车主编辑路线参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean personalItineraryEdit(HttpServletRequest request) {
		boolean flag = false;
		String seats = request.getParameter("seats");
		String startTime = request.getParameter("startTime");
		String remark = request.getParameter("remark");
		String strokeId = request.getParameter("strokeId");
		if (StringUtil.isOrNotEmpty(seats)
				&& StringUtil.isOrNotEmpty(startTime)
				&& StringUtil.isOrNotEmpty(remark)
				&& StringUtil.isOrNotEmpty(strokeId))
			flag = true;
		return flag;
	}

	/**
	 * 获取车主行程参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean personalItineraryDetail(String strokeId, String openID) {
		boolean flag = false;
		if (StringUtil.isOrNotEmpty(openID)
				&& StringUtil.isOrNotEmpty(strokeId))
			flag = true;
		return flag;
	}

	/**
	 * 乘客退订接口参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean unsubscribeTravel(HttpServletRequest request) {
		boolean flag = false;
		String bookedId = request.getParameter("bookedId");
		if (StringUtil.isOrNotEmpty(bookedId)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 车主同意乘客的预定
	 * 
	 * @param request
	 * @return
	 */
	public static boolean agreedBook(HttpServletRequest request) {
		boolean flag = false;
		String bookedId = request.getParameter("bookedId");
		String strokeId = request.getParameter("strokeId");
		if (StringUtil.isOrNotEmpty(bookedId)
				&& StringUtil.isOrNotEmpty(strokeId)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 发布成功推送参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean pushPublish(String openID, String strokeId) {
		boolean flag = false;
		if (StringUtil.isOrNotEmpty(openID)
				&& StringUtil.isOrNotEmpty(strokeId)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 微信支付参数验证
	 * 
	 * @param request
	 * @return
	 */
	public static boolean pay(HttpServletRequest request) {
		boolean flag = false;
		String openID = request.getParameter("openID");
		String orderNum = request.getParameter("orderNum");
		String price = request.getParameter("price");
		if (StringUtil.isOrNotEmpty(openID)
				&& StringUtil.isOrNotEmpty(orderNum)
				&& StringUtil.isOrNotEmpty(price)
				&& Double.parseDouble(price) > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 预约行程推送参数验证
	 * 
	 * @param openID
	 * @param bookedId
	 * @return
	 */
	public static boolean pushScheduled(String openID, String bookedId) {
		boolean flag = false;
		if (StringUtil.isOrNotEmpty(openID)
				&& StringUtil.isOrNotEmpty(bookedId)) {
			flag = true;
		}
		return flag;
	}

	public static boolean pushAggress(String openID, String strokeId,
			String bookedId) {
		boolean flag = false;
		if (StringUtil.isOrNotEmpty(openID)
				&& StringUtil.isOrNotEmpty(bookedId)
				&& StringUtil.isOrNotEmpty(bookedId)) {
			flag = true;
		}
		return flag;
	}

	public static boolean getPrice(HttpServletRequest request) {
		boolean flag = false;
		String startCode = request.getParameter("startCode");
		String endCode = request.getParameter("endCode");
		String bookingSeats = request.getParameter("booking_seats");
		String originLocation = request.getParameter("origin_location");
		String destinationLocation = request
				.getParameter("destination_location");
		if (StringUtil.isOrNotEmpty(startCode)
				&& StringUtil.isOrNotEmpty(endCode)
				&& StringUtil.isOrNotEmpty(bookingSeats)
				&& StringUtil.isOrNotEmpty(originLocation)
				&& StringUtil.isOrNotEmpty(destinationLocation)
				&& StringUtil.isOrNotEmpty(bookingSeats)) {
			flag = true;
		}
		return flag;
	}
}
