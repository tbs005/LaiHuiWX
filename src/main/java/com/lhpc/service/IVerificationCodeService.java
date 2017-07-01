package com.lhpc.service;

import com.lhpc.model.VerificationCode;

public interface IVerificationCodeService {
	
	int deleteByMobile(VerificationCode verificationCode);

    int insert(VerificationCode verificationCode);

    VerificationCode selectByMobile(VerificationCode verificationCode);

}
