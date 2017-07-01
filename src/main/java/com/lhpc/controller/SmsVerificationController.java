package com.lhpc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.service.IVerificationCodeService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;

@Controller
public class SmsVerificationController {

	@Autowired
	private IVerificationCodeService verificationCodeService;

	/**
	 * 发送短信验证码
	 */
	@ResponseBody
	@RequestMapping(value = "send/sms/code", method = RequestMethod.POST)
	public ResponseEntity<String> send(HttpServletRequest request) {
		if (!ParamVerificationUtil.sendSmsCode(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}

		return verificationCodeService.sendSMS(request);

	}
}
