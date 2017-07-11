package com.lhpc.util.WeChat;

import java.util.HashMap;
import java.util.Map;

import com.lhpc.model.Booked;
import com.lhpc.model.Stroke;
import com.lhpc.model.User;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.DateUtil;

/**
 * 推送消息拼接工具类
 * 
 * @author YangGuang
 *
 */
public class PushInfoUtil {

	private PushInfoUtil() {
	}

	/**
	 * 发布成功
	 * @param stroke
	 * @return
	 */
	public static WxTemplate pushPublish(Stroke stroke,User user,String openID){
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor(ConfigUtil.GREEN);
		first.setValue(user.getUserName()+"，您的出行信息已发布成功。");
		m.put("first", first);
		TemplateData keyword1 = new TemplateData();
		keyword1.setColor(ConfigUtil.BLUE);
		keyword1.setValue(stroke.getStartCity());
		m.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setColor(ConfigUtil.BLUE);
		keyword2.setValue(stroke.getEndCity());
		m.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setColor(ConfigUtil.BLUE);
		keyword3.setValue(user.getUserMobile());
		m.put("keyword3", keyword3);
		TemplateData keyword4 = new TemplateData();
		keyword4.setColor(ConfigUtil.BLUE);
		keyword4.setValue(DateUtil.dateWxString(stroke.getStartTime()));
		m.put("keyword4", keyword4);
		WxTemplate temp = new WxTemplate();
		temp.setUrl("https://www.baidu.com");
		temp.setTouser(openID);
		temp.setTopcolor(ConfigUtil.BLACK);
		temp.setTemplate_id(ConfigUtil.PUSH_PUBLISH);
		temp.setData(m);
		return temp;
	}
	
	/**
	 * 预约推送
	 * @param stroke
	 * @return
	 */
	public static WxTemplate pushScheduled(Booked booked,User user,String openID){
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor(ConfigUtil.GREEN);
		first.setValue("有新的拼友预定了您的座位.");
		m.put("first", first);
		TemplateData keyword1 = new TemplateData();
		keyword1.setColor(ConfigUtil.BLUE);
		keyword1.setValue(user.getUserName());
		m.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setColor(ConfigUtil.BLUE);
		keyword2.setValue(booked.getBookedSeats()+"");
		m.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setColor(ConfigUtil.BLUE);
		keyword3.setValue(user.getUserMobile());
		m.put("keyword3", keyword3);
		TemplateData keyword4 = new TemplateData();
		keyword4.setColor(ConfigUtil.BLUE);
		keyword4.setValue(booked.getUpAddress());
		m.put("keyword4", keyword4);
		TemplateData keyword5 = new TemplateData();
		keyword5.setColor(ConfigUtil.BLUE);
		keyword5.setValue(booked.getDownAddress());
		m.put("keyword5", keyword5);
		TemplateData remark = new TemplateData();
		remark.setColor(ConfigUtil.BLUE);
		remark.setValue("点击【详情】查看拼友电话、上车点。");
		m.put("remark", remark);
		WxTemplate temp = new WxTemplate();
		temp.setUrl("https://www.baidu.com");
		temp.setTouser(openID);
		temp.setTopcolor(ConfigUtil.BLACK);
		temp.setTemplate_id(ConfigUtil.PUSH_SCHEDULED);
		temp.setData(m);
		return temp;
	}

	public static WxTemplate pushAggress(Stroke stroke, User user, String openID,Booked booked) {
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor(ConfigUtil.GREEN);
		first.setValue("您已成功预定了"+booked.getBookedSeats()+"个座位。");
		m.put("first", first);
		TemplateData keyword1 = new TemplateData();
		keyword1.setColor(ConfigUtil.BLUE);
		keyword1.setValue(stroke.getStartCity()+"-"+stroke.getEndAddress());
		m.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setColor(ConfigUtil.BLUE);
		keyword2.setValue(user.getUserName());
		m.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setColor(ConfigUtil.BLUE);
		keyword3.setValue(user.getUserMobile());
		m.put("keyword3", keyword3);
		TemplateData keyword4 = new TemplateData();
		keyword4.setColor(ConfigUtil.BLUE);
		keyword4.setValue(user.getCarType());
		m.put("keyword4", keyword4);
		TemplateData keyword5 = new TemplateData();
		keyword5.setColor(ConfigUtil.BLUE);
		keyword5.setValue(DateUtil.dateString(stroke.getStartTime()));
		m.put("keyword5", keyword5);
		WxTemplate temp = new WxTemplate();
		temp.setUrl("https://www.baidu.com");
		temp.setTouser(openID);
		temp.setTopcolor(ConfigUtil.BLACK);
		temp.setTemplate_id(ConfigUtil.PUSH_AGGRESS_SCHEDULED);
		temp.setData(m);
		return temp;
	}
}
