package com.lhpc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhpc.dao.AccessTokenMapper;
import com.lhpc.model.AccessToken;
import com.lhpc.service.IAccessTokenService;

@Service
public class AccessTokenServiceImpl implements IAccessTokenService {

	@Autowired
	private AccessTokenMapper accessTokenDao;
	
	@Override
	public int insert(AccessToken record) {
		record.setCreateTime(new Date());
		return accessTokenDao.insert(record);
	}

	@Override
	public AccessToken selectToken() {
		return accessTokenDao.selectToken();
	}

}
