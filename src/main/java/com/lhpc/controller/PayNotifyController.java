package com.lhpc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lhpc.service.IPayNotifyService;

@Controller
//@RequestMapping(value = "/wx")
public class PayNotifyController {

	@Autowired
	private IPayNotifyService PayNotifyService;
	
	// 来回微信支付回调地址
	@RequestMapping(value = "/wx_pays/notify", method = RequestMethod.POST)
	public ResponseEntity<String> wx_pays(HttpServletRequest request,
			HttpServletResponse response) {
		return PayNotifyService.notify(request, response);

	}
}
