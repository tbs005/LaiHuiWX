package com.lhpc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhpc.dao.BookedMapper;
import com.lhpc.dao.PayLogMapper;
import com.lhpc.dao.StrokeMapper;
import com.lhpc.dao.UserMapper;
import com.lhpc.model.Booked;
import com.lhpc.model.PayLog;
import com.lhpc.service.IPayNotifyService;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.SendSMSUtil;
import com.lhpc.util.Utils;
import com.lhpc.util.XmlParse;

@Service
@Transactional
public class PayNotifyServiceImpl implements IPayNotifyService {

	private Logger logger = Logger.getLogger(IPayNotifyService.class);
	@Autowired
	private PayLogMapper payLogMapper;
	@Autowired
	private BookedMapper bookedMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private StrokeMapper strokeMapper;

	@Override
	public ResponseEntity<String> notify(HttpServletRequest request,
			HttpServletResponse response) {

		String response_content = "<xml> \n" + "\n"
				+ "  <return_code><![CDATA[SUCCESS]]></return_code>\n"
				+ "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml> \n";

		// 获取回执参数
		System.out.println("-----开始处理微信通知------");
		InputStream is;
		String return_xml = null;
		try {
			is = request.getInputStream();
			return_xml = IOUtils.toString(is, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("微信异步通知内容："+return_xml);
		boolean is_success = true;
		Document doc;
		Map<String, String> parameterMap = new HashMap<>();
		try {
			doc = DocumentHelper.parseText(return_xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<?> return_code = rootElt.elementIterator("return_code"); // 获取根节点下的子节点return_code
			while (return_code.hasNext()) {
				Element recordEle = (Element) return_code.next();
				String code = recordEle.getText(); // 拿到return_code返回值
				if (code != null && code.equals("SUCCESS")) {
					is_success = true;
				}
				System.out.println("code:" + code);
			}
			if (is_success) {
				// System.out.println("得到的xml:"+return_xml);
				parameterMap = XmlParse.readStringXmlOut(return_xml);
			} else {
				// 直接停止执行
				PrintWriter out = null;
				try {
					response.reset();
					out = response.getWriter();
					out.write(response_content);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					out.close();
				}
				return GsonUtil.getJson(ResponseCodeUtil.PAY_NOTIFY_ERROR,
						"支付失败!");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sign = parameterMap.get("sign");
		String result_code = parameterMap.get("result_code");
		String out_trade_no = parameterMap.get("out_trade_no");
		// System.out.println("map大小："+parameterMap.size()+"sign="+sign+"result_code="+result_code+"out_trade_no="+out_trade_no);

		List<String> keys = new ArrayList<>(parameterMap.keySet());
		keys.remove("sign");
		String result_parameter = "";
		Collections.sort(keys);
		for (String str : keys) {
			result_parameter = result_parameter + str + "="
					+ parameterMap.get(str) + "&";
		}
		result_parameter = result_parameter + "key="
				+ ConfigUtil.WX_LAIHUI_APP_SECRET_KEY;

		// System.out.println("待签名字符串为："+result_parameter);
		String current_sign = Utils.encode("MD5", result_parameter)
				.toUpperCase();
		if (current_sign.equals(sign)) {
			is_success = true;
		} else {
			is_success = false;
		}
		System.out.println("微信支付签名校验：" + is_success);

		if (is_success) {
			if (result_code.equals("SUCCESS")) {
				
				Booked booked = bookedMapper.selectByOutTradeNo(out_trade_no);
				int driverId = strokeMapper.selectByPrimaryKey(booked.getStrokeId()).getUserId();
				int passengerId = booked.getUserId();
				if (driverId>0&&passengerId>0){ 
					String driverMobile = userMapper.selectByPrimaryKey(driverId).getUserMobile();
					String passengerName = userMapper.selectByPrimaryKey(passengerId).getUserName();
					double price = strokeMapper.selectByPrimaryKey(booked.getStrokeId()).getPrice();
					SendSMSUtil.sendSMS(driverMobile, ConfigUtil.PAY_SUCCESS, "#name#="+passengerName+"&#price#="+price);
					booked.setIsEnable(2);
					int count = bookedMapper.updateByPrimaryKeySelective(booked);
					if (count == 0) {
						logger.error("订单号为"+out_trade_no+"的用户支付成功,但是结束预约单失败");
					}
				}
				else 
					logger.error("订单号为"+out_trade_no+"的用户推送信息推送失败,没有查询到相关信息");
				
				// System.out.println("查询是否已经收到异步通知！");
				List<PayLog> alipayNotifyList = payLogMapper
						.selectByOutTradeNo(out_trade_no);
				if (alipayNotifyList.size() > 0) {
					PrintWriter out = null;
					try {
						response.reset();
						out = response.getWriter();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out.write(response_content);
					return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "");
				}
				System.out.println("开始创建支付log！");
				// Todo:创建微信支付记录
				PayLog wxPay = new PayLog();
				double price = 0;

				try {
					price = Integer.parseInt(parameterMap.get("total_fee")) / 100d;
				} catch (NumberFormatException e) {
					price = 0;

					e.printStackTrace();
				}
				wxPay.setPrice(price);
				wxPay.setTradeNo(parameterMap.get("transaction_id"));
				wxPay.setOutTradeNo(parameterMap.get("out_trade_no"));
				wxPay.setTradeStatus(parameterMap.get("result_code"));
				wxPay.setBuyerId(parameterMap.get("openid"));
				wxPay.setSellerId(parameterMap.get("mch_id"));

				int count = payLogMapper.insertSelective(wxPay);
				if (count == 0) {
					logger.error("订单号为" + parameterMap.get("out_trade_no")
							+ "订单支付记录创建失败!订单价格为 " + price);
				}
				System.out.println("微信支付log创建成功！");

				PrintWriter out = null;
				try {
					response.reset();
					out = response.getWriter();
					out.write(response_content);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					out.close();
				}
			}
		}else {
			logger.error("回调中微信支付log创建失败!");
		}

		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "");

	}

}
