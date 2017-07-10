package com.lhpc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lhpc.model.Booked;

public interface BookedMapper {
    int deleteByPrimaryKey(Integer bookedId);

    int insert(Booked record);

    int insertSelective(Booked record);

    Booked selectByPrimaryKey(Integer bookedId);
    
    List<Booked> selectByUserId(@Param("userId")Integer userId,@Param("page")Integer page,@Param("size")Integer size);

    int updateByPrimaryKeySelective(Booked record);

    int updateByPrimaryKey(Booked record);

	List<Booked> selectBystrokeId(@Param("userId")Integer userId);
	
	List<Booked> selectStrokeBystrokeId(@Param("strokeId")Integer strokeId,@Param("isEnable") Integer isEnable);
	
	List<Booked> selectBookedByStrokeId(@Param("strokeId")Integer strokeId);

	Booked selectByOutTradeNo(String out_trade_no);
}