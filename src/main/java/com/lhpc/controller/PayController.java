package com.lhpc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.service.IPayService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;

@Controller
@RequestMapping(value="/wx")
public class PayController {

	@Autowired
	private IPayService payService;
	
	@ResponseBody
	@RequestMapping(value="wx_pay",method=RequestMethod.POST)
	public ResponseEntity<String> pay(HttpServletRequest request){
		if (!ParamVerificationUtil.pay(request)) {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整!");
		}
		return payService.pay(request);
	}
}
