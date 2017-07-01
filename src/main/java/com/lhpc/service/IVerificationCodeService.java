package com.lhpc.service;

import com.lhpc.model.VerificationCode;

public interface IVerificationCodeService {
	
	int deleteByMobile(String mobile);

    int insert(VerificationCode verificationCode);

    VerificationCode selectByMobile(String mobile);

}
