package com.lhpc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.AccessToken;
import com.lhpc.service.IAccessTokenService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 获取调用微信接口所需要的accessToken
 * @author YangGuang
 *
 */
@Controller
public class AccessTokenController {
	
	private static Logger log = Logger.getLogger(AccessTokenController.class);
	
	@Autowired
	private IAccessTokenService accessTokenService;
	
	@ResponseBody
	@RequestMapping(value="access/token")
	public ResponseEntity<String> accessToken() {
		try {
			AccessToken accessToken = accessTokenService.selectToken();
			if (accessToken == null) 
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据");
			else 
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功", accessToken.getAccessToken());
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!");
		}
		
	}

}
