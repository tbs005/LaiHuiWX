package com.lhpc.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Utils {
	private Utils() {
	}

	/**
	 * 获取请求ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 得到随机数字和字符
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharAndNum(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 加密算法
	 * 
	 * @param algorithm
	 * @param str
	 * @return
	 */
	public static String encode(String algorithm, String str) {

		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes("utf-8"));
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * 解析xml
	 * 
	 * @param xml
	 * @return
	 */
	public static String readStringXml(String xml) {

		Document doc;
		String result = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点

			Iterator<?> return_code = rootElt.elementIterator("return_code"); // 获取根节点下的子节点return_code
			// 获取根节点下的子节点return_code
			String is_success = null;

			// 遍历head节点
			while (return_code.hasNext()) {
				Element recordEle = (Element) return_code.next();
				is_success = recordEle.getText(); // 拿到return_code返回值
				// System.out.println("return_code:" + is_success);
			}
			if (is_success != null && is_success.equals("SUCCESS")) {
				Iterator<?> prepay_id = rootElt.elementIterator("prepay_id");
				while (prepay_id.hasNext()) {

					Element recordEle = (Element) prepay_id.next();
					result = recordEle.getText(); // 拿到prepay_id返回值
					// System.out.println("prepay_id:" + result);

				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
