package com.lhpc.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**
 *短信API服务调用示例代码 － 聚合数据
 *在线接口文档：http://www.juhe.cn/docs/54
 **/

public class SendSMSUtil {
   

    

    //1.屏蔽词检查测
    public static void getRequest1(){
        String result =null;
        String url ="http://v.juhe.cn/sms/black";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
        params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
        params.put("key",ConfigUtil.APPKEY);//应用ConfigUtil.APPKEY(应用详细页查询)

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getIntValue("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2.发送短信
    public static void getRequest2(String mobile,int tpl_id,String tpl_value){
        if(tpl_id==0)
        {
            tpl_id=8193;
        }
        String result =null;
        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
        params.put("mobile",mobile);//接收短信的手机号码
        params.put("tpl_id",tpl_id);//短信模板ID，请参考个人中心短信模板设置 8193
        params.put("tpl_value",tpl_value);//变量名和变量值对。如果你的变量名或者变量值中带有#&amp;=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，&lt;a href=&quot;http://www.juhe.cn/news/index/id/50&quot; target=&quot;_blank&quot;&gt;详细说明&gt;&lt;/a&gt;
        params.put("key",ConfigUtil.APPKEY);//应用ConfigUtil.APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json

        int maxTry=3;
        int num=0;
        while (num<maxTry) {
            try {
                boolean flag = false;
                result = net(url, params, "GET");
                JSONObject object = JSONObject.parseObject(result);
                System.out.println("object信息：" + object);
                if (object.getIntValue("error_code") == 0) {
                    JSONObject jsonObject = object.getJSONObject("result");
                    int fee = jsonObject.getIntValue("fee");
                    int count = jsonObject.getIntValue("count");
                    String sid = jsonObject.getString("sid");
                    System.out.println(object.get("result"));
                    flag = true;
                } else {
                    System.out.println(object.get("error_code") + ":" + object.get("reason"));
                    num++;
                }
                if(flag==true){
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String net(String strUrl, Map<String, Object> params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent",ConfigUtil.USERAGENT);
            conn.setUseCaches(false);
            conn.setConnectTimeout(ConfigUtil.DEF_CONN_TIMEOUT);
            conn.setReadTimeout(ConfigUtil.DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
//                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
//                    out.writeBytes(urlencode(params));
//                }
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(urlencode(params));
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, ConfigUtil.DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, ?> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ?> i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public static String randomNum(){
        String randomNum="";
        int i=0;
        int [] all=new int[4];
        while (i<4){
            int t=(int)(Math.random()*10);
            all[i]=t;
            i++;
        }
        for(int k=0;k<4;k++){
            randomNum=randomNum+all[k];
        }
        return randomNum;
    }
    

  
  
    public static String sendSMStoServer(String mobile,int tpl_id,String tpl_value){
        if(tpl_id==0)
        {
            tpl_id=29230;
        }
        String result =null;
        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
        params.put("mobile",mobile);//接收短信的手机号码
        params.put("tpl_id",tpl_id);//短信模板ID，请参考个人中心短信模板设置 8193
        params.put("tpl_value",tpl_value);//变量名和变量值对。如果你的变量名或者变量值中带有#&amp;=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，&lt;a href=&quot;http://www.juhe.cn/news/index/id/50&quot; target=&quot;_blank&quot;&gt;详细说明&gt;&lt;/a&gt;
        params.put("key",ConfigUtil.APPKEY);//应用ConfigUtil.APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json

        int maxTry=3;
        int num=0;
        while (num<maxTry) {
            try {
                boolean flag = false;
                result = net(url, params, "GET");
                JSONObject object = JSONObject.parseObject(result);
                if(object!=null)
                {
                    flag=true;
                }
                if(flag==true){
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
   
    public static boolean sendSMS(String mobile,int tpl_id,String tpl_value)
    {
        boolean isSuccess=false;
        String result=sendSMStoServer(mobile, tpl_id, tpl_value);
        int sms_error_code=-1;
        String sms_reason="";
        String sms_sid="";
        if(result!=null) {
            JSONObject object = JSONObject.parseObject(result);
            System.out.println("object信息：" + object);
            if (object.getIntValue("error_code") == 0) {
                JSONObject jsonObject = object.getJSONObject("result");
                sms_sid= jsonObject.getString("sid");
                sms_reason=object.getString("reason");
                sms_error_code=0;
                isSuccess=true;
            } else {
                sms_reason=object.getString("reason");
                sms_error_code=object.getIntValue("error_code");
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
                isSuccess=false;
            }
        }
        //把短信发送信息存入数据库
        return isSuccess;
    }
    
    public static void main(String [] args){
        //String rand=server("13838741275");
        String rand=randomNum();
        String code="#code#="+rand;
        sendSMS("15738961936",29230,code);
    }

	

	
}