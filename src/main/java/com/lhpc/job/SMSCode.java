package com.lhpc.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lhpc.dao.AccessTokenMapper;
import com.lhpc.dao.VerificationCodeMapper;
import com.lhpc.model.AccessToken;
import com.lhpc.util.DateUtil;

@Component("taskJob")
public class SMSCode {
	@Autowired
	private VerificationCodeMapper verificationCodeMapper;
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;

	// @Scheduled(cron = "1/1 * * * * ?")
	@Scheduled(cron = "0 0 1 * * ?")
	public void delectSMSCode() {
		Date data = DateUtil.date4OneDay();
		verificationCodeMapper.deleteByTime(data);
		AccessToken accessToken = new AccessToken();
		accessToken.setCreateTime(data);
		accessTokenMapper.delete(accessToken);
	}

}
