package com.lhpc.service;

import org.springframework.http.ResponseEntity;

import com.lhpc.model.Booked;

public interface IPassengerService {

	 ResponseEntity<String>  scheduled(Booked booked);

	ResponseEntity<String> unsubscribeTravel(int bookedId);

}
