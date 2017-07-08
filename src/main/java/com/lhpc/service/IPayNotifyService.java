package com.lhpc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface IPayNotifyService {

	ResponseEntity<String> notify(HttpServletRequest request,
			HttpServletResponse response);

}
