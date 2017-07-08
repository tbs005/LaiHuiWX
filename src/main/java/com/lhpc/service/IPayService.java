package com.lhpc.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface IPayService {

	ResponseEntity<String> pay(HttpServletRequest request);

	
}
