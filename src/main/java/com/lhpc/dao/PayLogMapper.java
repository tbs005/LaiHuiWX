package com.lhpc.dao;

import java.util.List;

import com.lhpc.model.PayLog;

public interface PayLogMapper {
    int deleteByPrimaryKey(Integer payId);

    int insert(PayLog record);

    int insertSelective(PayLog record);

    PayLog selectByPrimaryKey(Integer payId);

    int updateByPrimaryKeySelective(PayLog record);

    int updateByPrimaryKey(PayLog record);

	List<PayLog> selectByOutTradeNo(String outTradeNo);
}