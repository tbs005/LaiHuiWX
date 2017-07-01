package com.lhpc.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface IVerificationCodeService {

	public  int selectSMS(String mobile);
	public ResponseEntity<String> sendSMS(HttpServletRequest request);
	int insertSMS(int code, String mobile);


}
