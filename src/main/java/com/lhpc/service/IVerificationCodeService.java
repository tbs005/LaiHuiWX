package com.lhpc.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.lhpc.model.VerificationCode;

public interface IVerificationCodeService {

	public  int selectSMS(String mobile);
	public ResponseEntity<String> sendSMS(HttpServletRequest request);
	int insertSMS(String code, String mobile);
	VerificationCode selectCodeByMobile(String mobile);

}
