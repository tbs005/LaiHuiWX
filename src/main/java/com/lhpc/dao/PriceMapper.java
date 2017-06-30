package com.lhpc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lhpc.model.Price;

public interface PriceMapper {
    int deleteByPrimaryKey(Integer priceId);

    int insert(Price record);

    int insertSelective(Price record);

    Price selectByPrimaryKey(Integer priceId);
    List<Price> select(@Param("startCode") int startCode,@Param("endCode") int endCode);

    int updateByPrimaryKeySelective(Price record);

    int updateByPrimaryKey(Price record);
}