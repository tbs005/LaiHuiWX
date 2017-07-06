package com.lhpc.util.WeChat;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lhpc.util.ConfigUtil;
import com.lhpc.util.HttpRequest;

public class Send {

	public static String getAccessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String param = "grant_type=client_credential&appid="
				+ ConfigUtil.WX_APP_ID + "&secret=" + ConfigUtil.WX_SECRET_KEY;
		String objectString = HttpRequest.sendGet(url, param);
		JSONObject jsonObject = JSONObject.fromObject(objectString);
		String accessToken = jsonObject.getString("access_token");
		System.out.println(accessToken);
		return accessToken;

	}

	/**
	 * 发送模板消息 appId 公众账号的唯一标识 appSecret 公众账号的密钥 openId 用户标识
	 */
	public static void send_template_message(String appId, String appSecret,
			String openId) {
		// AccessToken token = getAccessToken();
		// String access_token = token.getToken();
		String access_token = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ access_token;
		WxTemplate temp = new WxTemplate();
		temp.setUrl("http://weixin.qq.com/download");
		temp.setTouser(openId);
		temp.setTopcolor("#000000");
		temp.setTemplate_id("7dN2fUAKlDU8oDHRStNoMEqWKCOgScnuS13SYw2JTCQ");
		//temp.setTemplate_id("LBBm6qscHUcz-0Gh7PlBonsLKtJBHjCLIirnaiBZ4xQ");
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor("#000000");
		first.setValue("这里填写您要发送的模板信息");
		m.put("first", first);
		TemplateData keyword1 = new TemplateData();
		keyword1.setColor("#000000");
		keyword1.setValue("另一行内人");
		m.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setColor("#000000");
		keyword2.setValue("N行");
		m.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setColor("#000000");
		keyword3.setValue("**666666");
		m.put("keyword3", keyword3);
		TemplateData keyword4 = new TemplateData();
		keyword4.setColor("#000000");
		keyword4.setValue("<a href='https://www.baidu.com'>点击我</a>");
		m.put("keyword4", keyword4);
		TemplateData remark = new TemplateData();
		remark.setColor("#000000");
		remark.setValue("***备注说明***");
		m.put("Remark", remark);
		temp.setData(m);
		String jsonString = JSONObject.fromObject(temp).toString();
		// JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST",
		// jsonString);
		String jsonObject = HttpRequest.sendPost(url, jsonString);
		System.out.println(jsonObject);
		
		// if (null != jsonObject) {
		// if (0 != jsonObject.getInt("errcode")) {
		// result = jsonObject.getInt("errcode");
		// //log.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
		// jsonObject.getString("errmsg"));
		// }
		// }
		// log.info("模板消息发送结果："+result);
	}

	public static void main(String[] args) {
		send_template_message(ConfigUtil.WX_APP_ID, ConfigUtil.WX_SECRET_KEY,
				"oTnCRwNr4VA0euswLE7uEGjpCqUw");
	}
}
