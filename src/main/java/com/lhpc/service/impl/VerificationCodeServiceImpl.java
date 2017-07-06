package com.lhpc.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lhpc.controller.SmsVerificationController;
import com.lhpc.dao.VerificationCodeMapper;
import com.lhpc.model.VerificationCode;
import com.lhpc.service.IVerificationCodeService;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ResponseCodeUtil;
import com.lhpc.util.SendSMSUtil;

@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {
	private static Logger log = Logger
			.getLogger(SmsVerificationController.class);
	@Autowired
	private VerificationCodeMapper verificationCodeMapper;

	/**
	 * 查询验证码次数(一天)
	 */
	@Override
	public int selectSMS(String mobile) {
		int count = 0;
		try {
			count = verificationCodeMapper.selectByMobile(mobile);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return count;
	}

	@Override
	public ResponseEntity<String> sendSMS(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		String rand = SendSMSUtil.randomNum();
		System.out.println(rand);
		String code = "#code#=" + rand;
		int count = selectSMS(mobile);
		System.out.println(count);
		if (count >= 5) {
			return GsonUtil.getJson(ResponseCodeUtil.CODE_BEYOND,
					"发送验证码过于频繁，请稍后重试！");
		}

		boolean success = SendSMSUtil.sendSMS(mobile, 29230, code);
		if (!success) {
			return GsonUtil.getJson(ResponseCodeUtil.SMS_SEND_FAILED,
					"验证码发送失败，请校验您输入的手机号是否正确！");
		}
		int execteNum = insertSMS(rand, mobile);
		if (execteNum == 0) {
			return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器错误!");
		}
		return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "验证码发送成功!");
	}

	/**
	 * 插入数据
	 */
	@Override
	public int insertSMS(String code, String mobile) {
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setCode(code);
		verificationCode.setMobile(mobile);
		verificationCode.setCreateTime(new Date());
		return verificationCodeMapper.insertSelective(verificationCode);
	}

	@Override
	public VerificationCode selectCodeByMobile(String mobile) {
		try {
			VerificationCode verificationCode =	verificationCodeMapper.selectCodeByMobile(mobile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return verificationCodeMapper.selectCodeByMobile(mobile);
	}

}
