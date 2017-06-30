package com.lhpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhpc.dao.PriceMapper;
import com.lhpc.model.Price;
import com.lhpc.service.IPriceService;

@Service
public class PriceServiceImpl implements IPriceService {

	@Autowired
	private PriceMapper priceMapper;
	@Override
	public List<Price> select4startCode(String startCode, String endCode) {
		
		return priceMapper.select(Integer.parseInt(startCode),Integer.parseInt(endCode));
	}

}
