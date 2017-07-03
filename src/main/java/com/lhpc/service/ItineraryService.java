package com.lhpc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lhpc.model.Stroke;


public interface ItineraryService {
	
	public boolean insertSelective (HttpServletRequest request,int userId);

	public List<Stroke> selectStroke(int userId, int IsEnable);

	public int  updateStroke(Stroke stroke);
	
	List<Stroke> selectCrossCityList(Stroke stroke);
	
	int selectCrossCityCount(Stroke stroke);

}
