package com.lhpc.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhpc.dao.UserMapper;
import com.lhpc.model.User;
import com.lhpc.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userDao;
	
	@Override
	public int insert(User record) {
		record.setCreateTime(new Date());
		return userDao.insert(record);
	}

	@Override
	public User selectByPrimaryKey(Integer userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	@Override
	public User selectByOpenID(String openID) {
		return userDao.selectByOpenID(openID);
	}

}
