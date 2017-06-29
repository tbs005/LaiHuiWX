package com.lhpc.dao;

import com.lhpc.model.UserLogin;

public interface UserLoginMapper {
    int deleteByPrimaryKey(Integer loginId);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Integer loginId);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
}