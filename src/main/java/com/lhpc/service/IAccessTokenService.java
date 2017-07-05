package com.lhpc.service;

import com.lhpc.model.AccessToken;

public interface IAccessTokenService {

	int insert(AccessToken record);

	AccessToken selectToken();
}
