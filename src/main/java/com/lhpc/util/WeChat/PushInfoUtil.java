package com.lhpc.util.WeChat;

import java.util.HashMap;
import java.util.Map;

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
	public static WxTemplate pushPublish(Stroke stroke,User user,String openID,String storkeId){
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
}
