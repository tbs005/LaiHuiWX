package com.lhpc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	 * 添加
	 */
	@Override
	public boolean insertSelective(HttpServletRequest request) {
		Date time = new Date();
		Stroke stroke = new Stroke();
		String startCity = request.getParameter("startCity");
		String startLongitude = request.getParameter("startLongitude");
		String startLatitude = request.getParameter("startLatitude");
		String endLongitude = request.getParameter("endLongitude");
		String endLatitude = request.getParameter("endLatitude");
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
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			time = sf.parse(startTime);
		} catch (ParseException e) {

		}
		stroke.setStartCity(startCity);
		stroke.setStartLongitude(startLongitude);
		stroke.setStartLatitude(startLatitude);
		stroke.setEndLongitude(endLongitude);
		stroke.setEndLatitude(endLatitude);
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
		stroke.setStartTime(time);
		boolean flag = false;
		if (strokeMapper.insertSelective(stroke) > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据用户ID和是否可用查询行程
	 */
	@Override
	public List<Stroke> selectStroke(int userId, int isEnable) {

		return strokeMapper.selectByUserIdAndIsEnable(userId, isEnable);

	}

	/**
	 * 根据行程ID修改行程
	 */
	@Override
	public int updateStroke(Stroke stroke) {

		return strokeMapper.updateByPrimaryKeySelective(stroke);
	}

	@Override
	public List<Stroke> selectCrossCityList(Stroke stroke) {
		return strokeMapper.selectCrossCityList(stroke);
	}

	@Override
	public int selectCrossCityCount(Stroke stroke) {
		return strokeMapper.selectCrossCityCount(stroke);
	}

}
