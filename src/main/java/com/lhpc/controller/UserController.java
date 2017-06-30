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

	/**
	 * 用户登录
	 */
	@ResponseBody
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<String> sendPhoneCode(HttpServletRequest request,
			User user) {
		try {
			if (ParamVerificationUtil.userLogin(request)) {
				String mobile = request.getParameter("mobile");
				boolean isTelephone = RegExpValidatorUtils.IsTelephone(mobile);
				if (isTelephone) {
					String openID = request.getParameter("openID");
					String userName = request.getParameter("userName");
					String userCode = request.getParameter("userCode");
					String code = request.getParameter("code");
					if (userCode.equals(code)) {
						user.setUserMobile(mobile);
						user.setOpenId(openID);
						user.setUserName(userName);
						user.setCreateTime(new Date());
						user.setLoginTime(new Date());
						if (userService.insert(user) == 1)
							return GsonUtil.getJson(ResponseCodeUtil.SUCCESS,
									"登录成功");
						else
							return GsonUtil.getJson(
									ResponseCodeUtil.LOGIN_ERROR, "登录失败");
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
	 * 未登录
	 */
	@ResponseBody
	@RequestMapping(value = "/user/noLogin", method = RequestMethod.GET)
	public ResponseEntity<String> noLogin() {
		return GsonUtil.getJson(ResponseCodeUtil.NO_LOGIN, "您未登录,请先登录!");
	}
}
