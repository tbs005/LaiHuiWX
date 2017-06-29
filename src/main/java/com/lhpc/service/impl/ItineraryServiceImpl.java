package com.lhpc.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhpc.dao.StrokeMapper;
import com.lhpc.model.Stroke;
import com.lhpc.service.ItineraryService;
@Service
public class ItineraryServiceImpl implements ItineraryService {

	@Autowired
	private StrokeMapper strokeMapper;
	/**
	 * 车主发布行程
	 */
	public boolean insertSelective (HttpServletRequest request){
		Stroke stroke = new Stroke();
		String startCity = request.getParameter("startCity");
		String startCityCode = request.getParameter("startCityCode");
		String startAddress = request.getParameter("startAddress");
		String endCity = request.getParameter("endCity");
		String endCityCode = request.getParameter("endCityCode");
		String endAddress = request.getParameter("endAddress");
		String price = request.getParameter("price");
		String startTime = request.getParameter("startTime");
		String carType = request.getParameter("carType");
		String seats = request.getParameter("seats");
		String strokeRoute = request.getParameter("strokeRoute");
		String remark = request.getParameter("remark");
		stroke.setStartCity(startCity);
		stroke.setStartCityCode(Integer.parseInt(startCityCode));
		stroke.setStartAddress(startAddress);
		stroke.setEndCity(endCity);
		stroke.setEndCityCode(Integer.parseInt(endCityCode));
		stroke.setEndAddress(endAddress);
		stroke.setCarType(carType);
		stroke.setSeats(Integer.parseInt(seats));
		stroke.setStrokeRoute(strokeRoute);
		stroke.setPrice(Double.parseDouble(price));
		stroke.setRemark(remark);
		stroke.setCreateTime(new Date());
		stroke.setUpdateTime(new Date());
		stroke.setIsEnable(1);
		stroke.setUserId(1);
		stroke.setStartTime(new Date());
		boolean flag = false;
		if (strokeMapper.insertSelective(stroke)>0) {
			flag = true;
		}
		return flag;
	}

	public List<Stroke> selectStroke(int userId, int IsEnable) {
		
		return strokeMapper.selectByUserIdAndIsEnable(userId,IsEnable);
		
	}
	
}
