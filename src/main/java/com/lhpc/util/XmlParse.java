package com.lhpc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by zhu on 2016/12/30.
 */
public class XmlParse {
	public static void main(String args[]) throws DocumentException {
		String text = "<xml>\n"
				+ "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n"
				+ "  <attach><![CDATA[支付测试]]></attach>\n"
				+ "  <bank_type><![CDATA[CFT]]></bank_type>\n"
				+ "  <fee_type><![CDATA[CNY]]></fee_type>\n"
				+ "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n"
				+ "  <mch_id><![CDATA[10000100]]></mch_id>\n"
				+ "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n"
				+ "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n"
				+ "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n"
				+ "  <result_code><![CDATA[SUCCESS]]></result_code>\n"
				+ "  <return_code><![CDATA[SUCCESS]]></return_code>\n"
				+ "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n"
				+ "  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n"
				+ "  <time_end><![CDATA[20140903131540]]></time_end>\n"
				+ "  <total_fee>1</total_fee>\n"
				+ "  <trade_type><![CDATA[JSAPI]]></trade_type>\n"
				+ "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n"
				+ "</xml> ";

		long begin = System.currentTimeMillis();
		readStringXmlOut(text);
		long after = System.currentTimeMillis();
		System.out.println("DOM用时" + (after - begin) + "毫秒");
		
	}


	 /**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
    public static Map<String, String> readStringXmlOut(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            @SuppressWarnings("unchecked")
            List<Element> list = rootElt.elements();// 获取根节点下所有节点
            for (Element element : list) { // 遍历节点
                map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
