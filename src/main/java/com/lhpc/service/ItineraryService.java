package com.lhpc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lhpc.model.Stroke;


public interface ItineraryService {
	
	public boolean insertSelective (HttpServletRequest request);

	public List<Stroke> selectStroke(int userId, int IsEnable);

}
