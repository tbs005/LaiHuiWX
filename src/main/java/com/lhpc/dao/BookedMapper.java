package com.lhpc.dao;

import com.lhpc.model.Booked;

public interface BookedMapper {
    int deleteByPrimaryKey(Integer bookedId);

    int insert(Booked record);

    int insertSelective(Booked record);

    Booked selectByPrimaryKey(Integer bookedId);

    int updateByPrimaryKeySelective(Booked record);

    int updateByPrimaryKey(Booked record);
}