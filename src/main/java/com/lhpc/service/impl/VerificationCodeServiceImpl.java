package com.lhpc.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhpc.dao.VerificationCodeMapper;
import com.lhpc.model.VerificationCode;
import com.lhpc.service.IVerificationCodeService;

@Service
@Transactional
public class VerificationCodeServiceImpl implements IVerificationCodeService {

	@Autowired
	private VerificationCodeMapper verificationCodeDao;

	@Override
	public int deleteByMobile(VerificationCode verificationCode) {
		return verificationCodeDao.deleteByMobile(verificationCode);
	}

	@Override
	public int insert(VerificationCode verificationCode) {
		verificationCode.setCreateTime(new Date());
		return verificationCodeDao.insert(verificationCode);
	}

	@Override
	public VerificationCode selectByMobile(VerificationCode verificationCode) {
		return verificationCodeDao.selectByMobile(verificationCode);
	} 
	

}
