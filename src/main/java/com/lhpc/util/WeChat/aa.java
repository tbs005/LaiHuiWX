package com.lhpc.util.WeChat;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.apache.log4j.spi.LoggerFactory;

public class aa {
    
  

    /**
     * 发送模板消息
     * appId 公众账号的唯一标识
     * appSecret 公众账号的密钥
     * openId 用户标识
     */
    public void send_template_message(String appId, String appSecret, String openId) {
        //AccessToken token = WeixinUtil.getAccessToken(appId, appSecret);
//        //String access_token = token.getToken();
//    	String access_token = "";
//        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
//        WxTemplate temp = new WxTemplate();
//        temp.setUrl("http://weixin.qq.com/download");
//        temp.setTouser(openId);
//        temp.setTopcolor("#000000");
////        temp.setTemplate_id("ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY");
//        temp.setTemplate_id("LBBm6qscHUcz-0Gh7PlBonsLKtJBHjCLIirnaiBZ4xQ");
//        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
//        TemplateData first = new TemplateData();
//        first.setColor("#000000");  
//        first.setValue("这里填写您要发送的模板信息");  
//        m.put("first", first);  
//        TemplateData name = new TemplateData();  
//        name.setColor("#000000");  
//        name.setValue("另一行内人");  
//        m.put("name", name);
//        TemplateData wuliu = new TemplateData();  
//        wuliu.setColor("#000000");  
//        wuliu.setValue("N行");  
//        m.put("wuliu", wuliu);
//        TemplateData orderNo = new TemplateData();  
//        orderNo.setColor("#000000");  
//        orderNo.setValue("**666666");  
//        m.put("orderNo", orderNo);
//        TemplateData receiveAddr = new TemplateData();  
//        receiveAddr.setColor("#000000");  
//        receiveAddr.setValue("*测试模板");  
//        m.put("receiveAddr", receiveAddr);
//        TemplateData remark = new TemplateData();  
//        remark.setColor("#000000");  
//        remark.setValue("***备注说明***");  
//        m.put("Remark", remark);
//        temp.setData(m);
//        String jsonString = JSONObject.fromObject(temp).toString();
//        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
//        System.out.println(jsonObject);
//        int result = 0;
//        if (null != jsonObject) {  
//             if (0 != jsonObject.getInt("errcode")) {  
//                 result = jsonObject.getInt("errcode");
//                 //log.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
//             }  
//         }
//        //log.info("模板消息发送结果："+result);
    }
}
