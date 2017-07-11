package com.lhpc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhpc.dao.BookedMapper;
import com.lhpc.model.AccessToken;
import com.lhpc.model.Booked;
import com.lhpc.model.PushStatus;
import com.lhpc.model.Stroke;
import com.lhpc.model.User;
import com.lhpc.service.IAccessTokenService;
import com.lhpc.service.IPassengerService;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.WeChat.PushInfoUtil;
import com.lhpc.util.WeChat.Send;
import com.lhpc.util.WeChat.WxTemplate;

/**
 * 微信推送Controller
 * @author YangGuang
 *
 */
@Controller
@RequestMapping("/wx")
public class PushController {
	
	@Autowired
	private IAccessTokenService accessTokenService;
	
	@Autowired
	private ItineraryService itineraryService;
	@Autowired
	private IPassengerService passengerService;
	@Autowired
	private BookedMapper bookedMapper;
	@Autowired
	private HttpSession session;
	
	/**
	 * 发布行程成功的推送
	 */
	@ResponseBody
	@RequestMapping(value = "push/publish", method = RequestMethod.POST)
	public ResponseEntity<String> pushPublish(String openID,String strokeId) {
		User user = (User)session.getAttribute("CURRENT_USER");
		if (ParamVerificationUtil.pushPublish(openID, strokeId)) {
			//获取access_token
			AccessToken accessToken = accessTokenService.selectToken();
			//获取行程信息
			Stroke stroke = itineraryService.selectByPrimaryKey(Integer.parseInt(strokeId));
			//设置推送内容
			WxTemplate temp = PushInfoUtil.pushPublish(stroke,user,openID,strokeId);
			//推送
			String jsonString = Send.sendTemplateMessage(accessToken.getAccessToken(), temp);
			PushStatus pushStatus = GsonUtil.parseJsonWithGson(jsonString, PushStatus.class);
			if (pushStatus.getErrcode() == 0) 
			return GsonUtil.getJson(ResponseCodeUtil.SUCCESS,
					"请求成功!",pushStatus);
			else
				return GsonUtil.getJson(ResponseCodeUtil.PUSH_ERROR,
						"推送失败!",pushStatus);
		}else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
	}
	
	/**
	 * 预约行程推送
	 * @param openID
	 * @param bookedId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "push/scheduled", method = RequestMethod.POST)
	public ResponseEntity<String> pushScheduled(String openID,String bookedId) {
		User user = (User)session.getAttribute("CURRENT_USER");
		if (ParamVerificationUtil.pushScheduled(openID, bookedId)) {
			//获取access_token
			AccessToken accessToken = accessTokenService.selectToken();
			//获取预约信息
			Booked booked = passengerService.selectByPrimaryKey(Integer.parseInt(bookedId));
			//设置推送内容
			WxTemplate temp = PushInfoUtil.pushScheduled(booked,user,openID);
			//推送
			String jsonString = Send.sendTemplateMessage(accessToken.getAccessToken(), temp);
			PushStatus pushStatus = GsonUtil.parseJsonWithGson(jsonString, PushStatus.class);
			if (pushStatus.getErrcode() == 0) 
			return GsonUtil.getJson(ResponseCodeUtil.SUCCESS,
					"请求成功!",pushStatus);
			else
				return GsonUtil.getJson(ResponseCodeUtil.PUSH_ERROR,
						"推送失败!",pushStatus);
		}else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
	}

	/**
	 * 车主同意乘客预约推送
	 * @param openID
	 * @param strokeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "push/aggress", method = RequestMethod.POST)
	public ResponseEntity<String> pushAggress(String openID,String strokeId,String bookedId) {
		User user = (User)session.getAttribute("CURRENT_USER");
		if (ParamVerificationUtil.pushAggress(openID, strokeId,bookedId)) {
			//获取access_token
			AccessToken accessToken = accessTokenService.selectToken();
			//获取行程信息
			Stroke stroke = itineraryService.selectByPrimaryKey(Integer.parseInt(strokeId));
			//获取预约信息
			Booked booked = passengerService.selectByPrimaryKey(Integer.parseInt(bookedId));
			//设置推送内容
			WxTemplate temp = PushInfoUtil.pushAggress(stroke,user,openID,booked);
			//推送
			String jsonString = Send.sendTemplateMessage(accessToken.getAccessToken(), temp);
			PushStatus pushStatus = GsonUtil.parseJsonWithGson(jsonString, PushStatus.class);
			if (pushStatus.getErrcode() == 0) 
			return GsonUtil.getJson(ResponseCodeUtil.SUCCESS,
					"请求成功!",pushStatus);
			else
				return GsonUtil.getJson(ResponseCodeUtil.PUSH_ERROR,
						"推送失败!",pushStatus);
		}else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS,
					"参数不完整!");
		}
	}
}
