package com.lhpc.service;

import com.lhpc.model.User;

public interface IUserService {
	
	int insert(User record);

    User selectByPrimaryKey(Integer userId);
    
    User selectByOpenID(String openID);

}
