package com.lhpc.dao;

import com.lhpc.model.VerificationCode;

public interface VerificationCodeMapper {
    int deleteByMobile(String mobile);

    int insert(VerificationCode verificationCode);

    VerificationCode selectByMobile(String mobile);
}