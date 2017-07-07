package com.lhpc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lhpc.dao.BookedMapper;
import com.lhpc.dao.StrokeMapper;
import com.lhpc.dao.UserMapper;
import com.lhpc.model.Booked;
import com.lhpc.model.Stroke;
import com.lhpc.model.User;
import com.lhpc.service.ItineraryService;
import com.lhpc.util.DateUtil;
import com.lhpc.util.GsonUtil;
import com.lhpc.util.ParamVerificationUtil;
import com.lhpc.util.ResponseCodeUtil;

@Service
public class ItineraryServiceImpl implements ItineraryService {

	@Autowired
	private StrokeMapper strokeMapper;

	@Autowired
	private BookedMapper bookedMapper;

	@Autowired
	private UserMapper userMapper;

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

	/**
	 * 跨城车辆
	 */
	@Override
	public List<Stroke> selectCrossCityList(Stroke stroke) {
		return strokeMapper.selectCrossCityList(stroke);
	}

	/**
	 * 跨城车辆个数
	 */
	@Override
	public int selectCrossCityCount(Stroke stroke) {
		return strokeMapper.selectCrossCityCount(stroke);
	}

	/**
	 * 乘客搜索车主列表
	 */
	@Override
	public ResponseEntity<String> selectSearchStrokeList(Stroke stroke,
			HttpServletRequest request) {
		if (ParamVerificationUtil.selectSearchStrokeList(request)) {
			stroke.setPage(stroke.getPage() * stroke.getSize());
			List<Stroke> list = strokeMapper.selectSearchStrokeList(stroke);
			if (list.size() > 0) {
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				list.stream().forEach(stroke1 -> {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("userName", stroke1.getUserName());
					resultMap.put("carType", stroke1.getCarType());
					resultMap.put("startTime",
							DateUtil.dateString(stroke1.getStartTime()));
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
				});
				return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "请求成功",
						resultList);
			} else {
				return GsonUtil.getJson(ResponseCodeUtil.NO_DATA, "暂无数据");
			}
		} else {
			return GsonUtil.getJson(ResponseCodeUtil.PARAMETER_MISS, "参数不完整");
		}
	}

	/**
	 * 个人中心我的行程
	 */
	@Override
	public List<Map<String, Object>> selectPersonalItineraryList(Stroke stroke,
			User user) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 车主
		if (stroke.getMark() == 1) {
			stroke.setUserId(user.getUserId());
			stroke.setPage(stroke.getPage() * stroke.getSize());
			List<Stroke> list = strokeMapper
					.selectPersonalItineraryList(stroke);
			if (list.size() > 0) {
				Stream<Stroke> strokeStream = list.stream();
				strokeStream.forEach(stroke1 -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("strokeId", stroke1.getStrokeId());
					map.put("route",
							stroke1.getStartCity() + "--"
									+ stroke1.getEndCity());
					map.put("startTime",
							DateUtil.dateString(stroke1.getStartTime()));
					List<Booked> bookedList = bookedMapper
							.selectStrokeBystrokeId(stroke1.getStrokeId(), 1);
					int bookedSeats = 0;
					if (bookedList.size() > 0) {
						for (Booked booked : bookedList) {
							bookedSeats += booked.getBookedSeats();
						}
					}
					map.put("seats", bookedSeats + "人/" + stroke1.getSeats()
							+ "人");
					map.put("price", stroke1.getPrice() + "元/人");
					resultList.add(map);
				});
			}
		} else {
			// 乘客
			List<Booked> bookedList = bookedMapper.selectByUserId(
					user.getUserId(), stroke.getPage() * stroke.getSize(),
					stroke.getSize());
			if (bookedList.size() > 0) {
				Stream<Booked> bookedStream = bookedList.stream();
				bookedStream.forEach(booked -> {
					Stroke stroke1 = strokeMapper.selectByPrimaryKey(booked
							.getStrokeId());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("strokeId", stroke1);
					map.put("route",
							stroke1.getStartCity() + "--"
									+ stroke1.getEndCity());
					map.put("startTime",
							DateUtil.dateString(stroke1.getStartTime()));
					map.put("seats", booked.getBookedSeats() + "人");
					map.put("price", stroke1.getPrice() + "元/人");
					resultList.add(map);
				});
			}
		}
		return resultList;
	}

	/**
	 * 根据行程ID获取 车主 的行程详情
	 */
	@Override
	public Map<String, Object> getDriverItineraryInfo(String strokeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Stroke stroke = strokeMapper.selectByPrimaryKey(Integer
				.parseInt(strokeId));
		resultMap.put("accessCount", stroke.getAccessCount());
		resultMap.put("seats", stroke.getSeats());
		List<Booked> bookeds = bookedMapper.selectBookedByStrokeId(Integer
				.parseInt(strokeId));
		List<Map<String, Object>> resultBookeds = new ArrayList<Map<String, Object>>();
		for (Booked booked : bookeds) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bookedId", booked.getBookedId());
			map.put("userName", booked.getUserName());
			map.put("userMobile", booked.getUserMobile());
			map.put("upAddress", booked.getUpAddress());
			map.put("downAddress", booked.getDownAddress());
			map.put("bookedSeats", booked.getBookedSeats());
			resultBookeds.add(map);
		}
		resultMap.put("bookeds", resultBookeds);

		return resultMap;
	}

	/**
	 * 根据行程ID获取 乘客 的行程详情
	 */
	@Override
	public Map<String, Object> getPassengerItineraryInfo(String strokeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询车主行程详情
		Stroke stroke = strokeMapper.selectByPrimaryKey(Integer
				.parseInt(strokeId));
		resultMap.put("strokeId", stroke.getStrokeId());
		resultMap.put("userId", stroke.getUserId());
		User user = userMapper.selectByPrimaryKey(stroke.getUserId());
		resultMap.put("userName", user != null ? user.getUserName() : "");
		resultMap.put("carType", user != null ? user.getCarType() : "");
		resultMap.put("userMobile", user != null ? user.getUserMobile() : "");
		// 发车时间处理
		Date startTime = stroke.getStartTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int time = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		String startTimeStr = month + "月" + day + "日 " + time + ":" + min;

		resultMap.put("startTime", startTimeStr);
		resultMap.put("startAddress", stroke.getStartAddress());
		resultMap.put("endAddress", stroke.getEndAddress());
		resultMap.put("price", stroke.getPrice());
		resultMap.put("seats", stroke.getSeats());
		resultMap.put("strokeRoute", stroke.getStrokeRoute());
		resultMap.put("remark", stroke.getRemark());
		// 获取车主拼车数
		int pincheCount = strokeMapper.selectCount(stroke);
		resultMap.put("pincheCount", pincheCount);

		return resultMap;
	}

	/**
	 * 获取车主行程
	 */
	@Override
	public Map<String, Object> getPassengerItineraryDetail(String strokeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Stroke stroke = strokeMapper.selectByPrimaryKey(Integer
				.parseInt(strokeId));
		resultMap.put("startAddress", stroke.getStartAddress());
		resultMap.put("endAddress", stroke.getEndAddress());
		resultMap.put("route", stroke.getStrokeRoute());
		resultMap.put("seats", stroke.getSeats());
		resultMap.put("startTime", DateUtil.date2String(stroke.getStartTime()));
		resultMap.put("price", stroke.getPrice());
		resultMap.put("remark", stroke.getRemark());
		resultMap.put("strokeId", strokeId);
		return resultMap;
	}

	/**
	 * 编辑车主行程
	 */
	@Override
	public ResponseEntity<String> personalItineraryEdit(
			HttpServletRequest request) {
		try {
			int strokeId = Integer.parseInt(request.getParameter("strokeId"));
			int seats = Integer.parseInt(request.getParameter("seats"));
			Stroke strokeBean = strokeMapper.selectByPrimaryKey(strokeId);
			Stroke stroke = new Stroke();
			stroke.setStrokeId(strokeId);
			stroke.setSeats(seats);
			stroke.setUpdateTime(new Date());
			stroke.setRemark(request.getParameter("remark"));
			stroke.setStartTime(DateUtil.stringToDate(request
					.getParameter("startTime")));
			if (strokeBean.getSeats() >= seats) {
				stroke.setUpdateTime(new Date());
				if (strokeMapper.updateByPrimaryKey(stroke) > 0)
					// TODO 推送给乘客
					return GsonUtil.getJson(ResponseCodeUtil.SUCCESS, "修改成功!");
				else
					return GsonUtil.getJson(ResponseCodeUtil.SYSTEM_ERROR,
							"服务器繁忙,请重试!");
			} else {
				return GsonUtil.getJson(ResponseCodeUtil.SEAT_LACK,
						"座位数不足,不能修改!");
			}
		} catch (Exception e) {
			return GsonUtil
					.getJson(ResponseCodeUtil.SYSTEM_ERROR, "服务器繁忙,请重试!");
		}
	}

	/**
	 * 车主结束乘客行程
	 */
	@Override
	public int closeItinerary(String bookedId) {
		Booked booked = new Booked();
		booked.setBookedId(Integer.parseInt(bookedId));
		booked.setIsEnable(2);
		int value = bookedMapper.updateByPrimaryKeySelective(booked);
		// 如果乘客预约全部结束，车主行程单也关闭
		Booked booked2 = bookedMapper.selectByPrimaryKey(Integer
				.parseInt(bookedId));
		if (booked2 != null && booked2.getStrokeId() != null) {
			List<Booked> bookeds = bookedMapper.selectStrokeBystrokeId(
					booked2.getStrokeId(), 1);
			if (bookeds == null || bookeds.size() <= 0) {
				Stroke stroke = strokeMapper.selectByPrimaryKey(booked2
						.getStrokeId());
				if (stroke != null) {
					stroke.setIsEnable(2);
					int value2 = strokeMapper
							.updateByPrimaryKeySelective(stroke);
					System.out.println(value2);
				}
			}
		}
		return value;
	}

	@Override
	public Stroke selectByPrimaryKey(Integer strokeId) {
		return strokeMapper.selectByPrimaryKey(strokeId);
	}
}
