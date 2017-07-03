package com.lhpc.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.lhpc.model.VerificationCode;

public interface VerificationCodeMapper {
	int deleteByPrimaryKey(Integer codeId);

	int insert(VerificationCode record);

	int insertSelective(VerificationCode record);

	VerificationCode selectByPrimaryKey(Integer codeId);

	int updateByPrimaryKeySelective(VerificationCode record);

	int updateByPrimaryKey(VerificationCode record);

	int selectByMobile(String mobile);

	VerificationCode selectCodeByMobile(String mobile);

	void deleteByTime(@Param("createTime") Date createTime);
}