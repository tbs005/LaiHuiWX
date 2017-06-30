package com.lhpc.service;

import java.util.List;

import com.lhpc.model.Price;

public interface IPriceService {

	List<Price> select4startCode(String startCode, String endCode);

}
