package com.lhpc.dao;

import java.util.List;
import java.util.Map;

import com.lhpc.model.User;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(Integer userId);
    
    User selectByOpenID(String openID);

	List<String> getMobileByList(Map<String, Object> userIdsParam);
}