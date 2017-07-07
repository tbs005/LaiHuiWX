package com.lhpc.util.WeChat;

import net.sf.json.JSONObject;

import com.lhpc.util.ConfigUtil;
import com.lhpc.util.HttpRequest;

public class Send {

	/**
	 * 发送模板消息 appId 公众账号的唯一标识 appSecret 公众账号的密钥 openId 用户标识
	 */
	public static String sendTemplateMessage(String accessToken, WxTemplate temp) {
		String url = ConfigUtil.SEND_URL + accessToken;
		String jsonString = JSONObject.fromObject(temp).toString();
		String jsonObject = HttpRequest.sendPost(url, jsonString);
		System.out.println(jsonObject);
		return jsonObject;
	}
}
