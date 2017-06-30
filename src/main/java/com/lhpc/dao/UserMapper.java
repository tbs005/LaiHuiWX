package com.lhpc.dao;

import com.lhpc.model.User;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(Integer userId);
    
    User selectByOpenID(String openID);
}