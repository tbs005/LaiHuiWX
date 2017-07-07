package com.lhpc.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lhpc.dao.AccessTokenMapper;
import com.lhpc.model.AccessToken;
import com.lhpc.util.ConfigUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.HttpRequest;

/**
 * 每隔两个小时,获取accessToken,插入数据库
 * @author YangGuang
 */
@Component
public class GetAccessToken {

	@Autowired
	private AccessTokenMapper accessTokenMapper;
	
	@Scheduled(cron = "0 0 */2 * * ?")
	public void getAccessToken() {
		String url = ConfigUtil.GET_ACCESS_TOKEN;
		String param = "grant_type=client_credential&appid="+ConfigUtil.WX_APP_ID+"&secret="+ConfigUtil.WX_SECRET_KEY;
		String accessToken = HttpRequest.sendGet(url, param);
		AccessToken at = GsonUtil.parseJsonWithGson(accessToken, AccessToken.class);
		AccessToken bean = new AccessToken();
		bean.setAccessToken(at.getAccess_token());
		bean.setCreateTime(new Date());
		accessTokenMapper.insert(bean);
	}
}
