package com.lhpc.util;

public class ResponseCodeUtil {
	
	private ResponseCodeUtil(){
		
	}
	public static final int SUCCESS = 2000;//响应成功
	public static final int UNFINISHED_TRIP = 3001;//存在未完成行程
	public static final int PARAMETER_MISS = 4001;//参数不完整
	public static final int NO_USER = 4002;//用户不存在
	public static final int NO_DATA = 4003;//暂无数据
	public static final int PHONE_ERROR = 4004;//手机号格式错误
	public static final int NO_LOGIN = 4005;//用户未登录
	public static final int LOGIN_ERROR = 4006;//登录失败
	public static final int CODE_ERROR = 4007;//验证码不正确
	public static final int SYSTEM_ERROR = 5005;//系统错误
	

	
	
}
