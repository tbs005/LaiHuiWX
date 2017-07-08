package com.lhpc.service;

import org.springframework.http.ResponseEntity;

public interface IPersonalService {

	ResponseEntity<String> agreedBook(String bookedId, String strokeId);

	ResponseEntity<String> denialBook(String bookedId, String strokeId);
	
	ResponseEntity<String> extractCash(String money);

}
