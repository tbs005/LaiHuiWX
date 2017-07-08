package com.lhpc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.lhpc.service.IPayService;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.Utils;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class PayServiceImpl implements IPayService {

	@Override
	public ResponseEntity<String> pay(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String orderNum = request.getParameter("orderNum");
		String openID = request.getParameter("openID");
		double price = Double.parseDouble(request.getParameter("price"));
		String body = "拼车费用";
		String description = "拼车费用";

		String now_ip = Utils.getIP(request);
		String nonce_str = Utils.getCharAndNum(32);
		String total_fee = (int) price * 100 + "";
		String prepay_id = null;
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("appid", ConfigUtil.WX_APP_ID);
		paraMap.put("attach", description);
		paraMap.put("body", body);
		paraMap.put("mch_id", ConfigUtil.WX_WEB_MCH_ID);
		paraMap.put("nonce_str", nonce_str);
		paraMap.put("openid", openID);
		paraMap.put("out_trade_no", orderNum);
		paraMap.put("spbill_create_ip", now_ip);
		paraMap.put("total_fee", total_fee);
		paraMap.put("trade_type", "JSAPI");
		paraMap.put("notify_url", ConfigUtil.WX_PAY_WEB_NOTIFY_URL);
		List<String> keys = new ArrayList<>(paraMap.keySet());
		Collections.sort(keys);
		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String value = paraMap.get(keys.get(i));
			authInfo.append(keys.get(i) + "=" + value + "&");
		}
		authInfo.append(keys.get(keys.size() - 1) + "="
				+ paraMap.get(keys.get(keys.size() - 1)));
		String stringA = authInfo.toString() + "&key="
				+ ConfigUtil.WX_WEB_MCH_SECRET_KEY;
		System.out.println(stringA);
		String sign = Utils.encode("MD5", stringA).toUpperCase();
		String trade_type = "JSAPI";
		// 封装xml
		String paras = "<xml>\n" + "<appid>" + ConfigUtil.WX_APP_ID
				+ "</appid>\n" + "<mch_id>" + ConfigUtil.WX_WEB_MCH_ID
				+ "</mch_id>\n" + "<nonce_str>" + nonce_str + "</nonce_str>\n"
				+ "<sign>" + sign + "</sign>\n" + "<body><![CDATA[" + body
				+ "]]></body>\n" + "<attach>" + description + "</attach>\n"
				+ "<out_trade_no>" + orderNum + "</out_trade_no>\n"
				+ "<total_fee>" + total_fee + "</total_fee>\n"
				+ "<spbill_create_ip>" + now_ip + "</spbill_create_ip>\n"
				+ "<notify_url>" + ConfigUtil.WX_PAY_WEB_NOTIFY_URL
				+ "</notify_url>\n" + "<trade_type>" + trade_type
				+ "</trade_type>\n" + "<openid>" + openID + "</openid>\n"
				+ "</xml>";
		try {
			String content = senPost(paras);
			if (content != null) {
				prepay_id = Utils.readStringXml(content);
			}
			if (prepay_id != null) {
				String current_noncestr = Utils.getCharAndNum(32);
				String current_sign = null;
				long current_timestamp = System.currentTimeMillis() / 1000;
				JSONObject signn = new JSONObject();
				signn.put("appid", ConfigUtil.WX_APP_ID);
				signn.put("partnerid", ConfigUtil.WX_WEB_MCH_ID);
				signn.put("prepayid", prepay_id);
				signn.put("package", "Sign=WXPay");
				signn.put("noncestr", current_noncestr);
				signn.put("timestamp", current_timestamp);
				// 加密算法
				String nowStringA = "appid=" + ConfigUtil.WX_APP_ID
						+ "&noncestr=" + current_noncestr
						+ "&package=Sign=WXPay&partnerid="
						+ ConfigUtil.WX_WEB_MCH_ID + "&prepayid=" + prepay_id
						+ "&timestamp=" + current_timestamp + "&key="
						+ ConfigUtil.WX_SECRET_KEY;
				current_sign = Utils.encode("MD5", nowStringA).toUpperCase();
				signn.put("sign", current_sign);

				SortedMap<String, String> finalpackage = new TreeMap<String, String>();
				String timeStamp = String
						.valueOf(System.currentTimeMillis() / 1000);
				String packages = "prepay_id=" + prepay_id;
				;// 订单详情扩展字符串
				finalpackage.put("appId", ConfigUtil.WX_APP_ID);// 公众号appid
				finalpackage.put("timeStamp", timeStamp);
				finalpackage.put("nonceStr", current_noncestr); // 随机数
				finalpackage.put("package", packages);
				finalpackage.put("signType", "MD5");// 签名方式
				StringBuilder finals = new StringBuilder();
				List<String> likes = new ArrayList<>(finalpackage.keySet());
				Collections.sort(likes);
				for (int i = 0; i < likes.size() - 1; i++) {
					String value = finalpackage.get(likes.get(i));
					finals.append(likes.get(i) + "=" + value + "&");
				}
				finals.append(likes.get(likes.size() - 1) + "="
						+ finalpackage.get(likes.get(likes.size() - 1)));
				String stringB = finals.toString() + "&key="
						+ ConfigUtil.WX_WEB_MCH_SECRET_KEY;
				System.out.println(stringB);
				String finalsign = Utils.encode("MD5", stringB).toUpperCase();
				map.put("appId", ConfigUtil.WX_APP_ID);
				map.put("timeStamp", current_timestamp);
				map.put("nonceStr", current_noncestr);
				map.put("packages", packages);
				map.put("paySign", finalsign);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "操作成功!", map);

	}

	public static String senPost(String paras) throws IOException {
		boolean is_success = true;
		int i = 0;
		String result = "";
		while (is_success) {

			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

			@SuppressWarnings({ "resource" })
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity postingString = new StringEntity(paras, "UTF-8");// xml传递
			post.setEntity(postingString);
			post.setHeader("Content-type", "text/html; charset=UTF-8");
			HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity());

			if (result == null || result.isEmpty()) {
				i++;
			} else {
				break;
			}
			if (i > 2) {
				break;
			}
		}

		return result;
	}
}
