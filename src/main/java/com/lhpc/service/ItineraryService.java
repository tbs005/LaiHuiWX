package com.lhpc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.lhpc.model.Stroke;
import com.lhpc.model.User;


public interface ItineraryService {
	
	public boolean insertSelective (HttpServletRequest request,int userId);

	public List<Stroke> selectStroke(int userId, int IsEnable);

	public int  updateStroke(Stroke stroke);
	
	List<Stroke> selectCrossCityList(Stroke stroke);
	
	int selectCrossCityCount(Stroke stroke);
	
	ResponseEntity<String> selectSearchStrokeList(Stroke stroke,HttpServletRequest request);

	List<Map<String, Object>> selectPersonalItineraryList(Stroke stroke, User user);

	public Map<String, Object> getDriverItineraryInfo(String strokeId);
	
	public Map<String, Object> getPassengerItineraryInfo(String strokeId,String bookedId);
	
	public Map<String, Object> passengerIsAgree(String strokeId,String bookedId);
	
	public Map<String, Object> getPassengerItineraryDetail(String strokeId);

	ResponseEntity<String> personalItineraryEdit(HttpServletRequest request);
	
	public int closeItinerary(String bookedId);
	
	Stroke selectByPrimaryKey(Integer strokeId);
	
	int selectCount(Stroke stroke);

}
