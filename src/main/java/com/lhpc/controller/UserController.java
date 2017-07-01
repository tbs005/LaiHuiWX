package com.lhpc.controller;

import java.util.Date;

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
import com.lhpc.service.IUserService;
import com.lhpc.service.IVerificationCodeService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.RegExpValidatorUtils;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 用户登录
 * 
 * @author YangGuang
 *
 */

@Controller
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private IUserService userService;
	@Autowired
	private IVerificationCodeService verificationCodeService;

	/**
	 * 用户注册与登录
	 */
	@ResponseBody
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<String> sendPhoneCode(HttpServletRequest request,
			User user) {
		try {
			//验证参数是否完整
			if (ParamVerificationUtil.userLogin(request)) {
				String mobile = request.getParameter("mobile");
				//验证手机号格式
				boolean isTelephone = RegExpValidatorUtils.IsHandset(mobile);
				if (isTelephone) {
					String code = request.getParameter("code");
					//验证码是否正确
					if (Integer.parseInt(code) == verificationCodeService
							.selectCodeByMobile(mobile).getCode()) {
						//如果是司机,则需要这两个参数
						if (request.getParameter("userType").equals("1")) {
							if (ParamVerificationUtil.driverLogin(request)) {
								user.setCarType(request.getParameter("carType"));
								user.setCarLicense(request.getParameter("carLicense"));
							}else {
								return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
										"参数不完整");
							}
						}
						user.setUserMobile(mobile);
						user.setOpenId(request.getParameter("openID"));
						user.setUserName(request.getParameter("userName"));
						user.setCreateTime(new Date());
						user.setLoginTime(new Date());
						//把用户信息插入数据库
						if (userService.insert(user) == 1)
							return GsonUtil.getJson(ResponseCodeUtil.SUCCESS,
									"注册成功");
						else
							return GsonUtil.getJson(
									ResponseCodeUtil.LOGIN_ERROR, "注册失败");
					} else {
						return GsonUtil.getJson(ResponseCodeUtil.CODE_ERROR,
								"验证码错误!");
					}
				} else {
					return GsonUtil.getJson(ResponseCodeUtil.PHONE_ERROR,
							"请输入正确的手机号");
				}
			} else {
				return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
						"参数不完整");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
					"服务器繁忙,请稍后重试!");
		}

	}

	/**
	 * 未注册
	 */
	@ResponseBody
	@RequestMapping(value = "/user/noLogin")
	public ResponseEntity<String> noLogin() {
		return GsonUtil.getJson(ResponseCodeUtil.NO_LOGIN, "您未注册,请先注册!");
	}
}
