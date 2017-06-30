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
		if (StringUtil.isOrNotEmpty(mobile) && StringUtil.isOrNotEmpty(code))
			flag = true;
		return flag;
	}
	
	/**
	 * 跨城车辆参数验证
	 */
	public static boolean crossCity(HttpServletRequest request){
		boolean flag = false;
		request.getParameter("");
		return flag;
	}
}
