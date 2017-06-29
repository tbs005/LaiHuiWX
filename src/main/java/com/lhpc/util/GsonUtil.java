package com.lhpc.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class GsonUtil {
	
	private GsonUtil(){
		
	}
	
	public static String getJson(int code,String message){
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("message", message);
		return gson.toJson(map);
	}
	
	public static String getJson(int code,String message,Object obj){
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("message", message);
		map.put("data", obj);
		return gson.toJson(map);
	}
	
}
