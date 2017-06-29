package com.lhpc.dao;

import com.lhpc.model.Flow;

public interface FlowMapper {
    int deleteByPrimaryKey(Integer flowId);

    int insert(Flow record);

    int insertSelective(Flow record);

    Flow selectByPrimaryKey(Integer flowId);

    int updateByPrimaryKeySelective(Flow record);

    int updateByPrimaryKey(Flow record);
}