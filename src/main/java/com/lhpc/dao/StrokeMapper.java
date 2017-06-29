package com.lhpc.dao;

import com.lhpc.model.Stroke;

public interface StrokeMapper {
    int deleteByPrimaryKey(Integer strokeId);

    int insert(Stroke record);

    int insertSelective(Stroke record);

    Stroke selectByPrimaryKey(Integer strokeId);

    int updateByPrimaryKeySelective(Stroke record);

    int updateByPrimaryKey(Stroke record);
}