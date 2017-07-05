package com.lhpc.dao;

import com.lhpc.model.AccessToken;

public interface AccessTokenMapper {

    int insert(AccessToken record);

    AccessToken selectToken();
    
    int delete(AccessToken accessToken);

}