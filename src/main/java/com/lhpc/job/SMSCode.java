package com.lhpc.job;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lhpc.dao.VerificationCodeMapper;
import com.lhpc.util.DateUtil;

@Component("taskJob")
public class SMSCode {
	@Autowired
	private VerificationCodeMapper verificationCodeMapper;

	// @Scheduled(cron = "1/1 * * * * ?")
	@Scheduled(cron = "0 0 1 * * ?")
	public void delectSMSCode() {
		Date data = DateUtil.date4OneDay();
		verificationCodeMapper.deleteByTime(data);
		System.out
				.println("我在" + DateUtil.date2String(new Date()) + "执行了定时任务!");
	}

}
