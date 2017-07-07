package com.lhpc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.model.OpenID;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.HttpRequest;
import com.lhpc.util.ResponseCodeUtil;

/**
 * 获取用户openIDcontroller
 * 
 * @author YangGuang
 *
 */
@Controller
public class GetOpenIDController {

	private static Logger log = Logger.getLogger(GetOpenIDController.class);

	@ResponseBody
	@RequestMapping(value = "get/openID")
	public ResponseEntity<String> getOpenID(HttpServletRequest request) {
		try {
			String code = request.getParameter("code");
			if (code == null || code.equals(""))
				return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
						"参数不完整!");
			String url = ConfigUtil.GET_OPENID;
			String param = "appid=" + ConfigUtil.WX_APP_ID + "&secret="
					+ ConfigUtil.WX_SECRET_KEY + "&code=" + code
					+ "&grant_type=authorization_code";
			String json = HttpRequest.sendGet(url, param);
			OpenID openID = GsonUtil.parseJsonWithGson(json, OpenID.class);
			if (openID.getErrcode() == 0) {
				return GsonUtil
						.getJson(ResponseCodeUtil.SUCCESS, "请求成功!",openID);
			}
			return GsonUtil
					.getJson(ResponseCodeUtil.CODE_REPEAT, "请求错误!",openID);
		} catch (Exception e) {
			log.error(e.getMessage());
			return GsonUtil
					.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!");
		}

	}

}
