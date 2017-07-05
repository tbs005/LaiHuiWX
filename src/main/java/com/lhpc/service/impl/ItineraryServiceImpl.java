package com.lhpc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lhpc.dao.StrokeMapper;
import com.lhpc.model.Stroke;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.DateUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;

@Service
public class ItineraryServiceImpl implements ItineraryService {

	@Autowired
	private StrokeMapper strokeMapper;

	/**
	 * 添加
	 */
	@Override
	public boolean insertSelective(HttpServletRequest request, int userId) {
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
		String carType = request.getParameter("carType");
		String startTime = request.getParameter("startTime");
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
		stroke.setIsEnable(1);
		stroke.setUserId(userId);
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

	@Override
	public ResponseEntity<String> selectSearchStrokeList(Stroke stroke,
			HttpServletRequest request) {
		if (ParamVerificationUtil.selectSearchStrokeList(request)) {
			stroke.setPage(stroke.getPage()*stroke.getSize());
			List<Stroke> list = strokeMapper.selectSearchStrokeList(stroke);
			if (list.size() > 0){
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				for (Stroke stroke1 : list) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("userName", stroke1.getUserName());
					resultMap.put("carType", stroke1.getCarType());
					resultMap.put("startTime", DateUtil.dateString(stroke1.getStartTime()));
					resultMap.put("count", strokeMapper.selectCount(stroke1));
					resultMap.put("startCity", stroke1.getStartCity());
					resultMap.put("endCity", stroke1.getEndCity());
					resultMap.put("startAddress", stroke1.getStartAddress());
					resultMap.put("endAddress", stroke1.getEndAddress());
					resultMap.put("strokeRoute", stroke1.getStrokeRoute());
					resultMap.put("remark", stroke1.getRemark());
					resultMap.put("price", stroke1.getPrice());
					resultMap.put("seats", stroke1.getSeats());
					resultMap.put("strokeId", stroke1.getStrokeId());
					resultList.add(resultMap);
				}
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功", resultList);
			}
			else{
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据");
			}
		} else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整");
		}
	}
}
