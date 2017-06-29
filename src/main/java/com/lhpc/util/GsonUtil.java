package com.lhpc.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

public class GsonUtil {

	private GsonUtil() {

	}

	public static ResponseEntity<String> getJson(int code, String message) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message);
		return new ResponseEntity<String>(gson.toJson(map), responseHeaders(),
				HttpStatus.OK);
	}

	public static ResponseEntity<String> getJson(int code, String message,
			Object obj) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message);
		map.put("data", obj);
		return new ResponseEntity<String>(gson.toJson(map), responseHeaders(),
				HttpStatus.OK);
	}

	/**
	 * 响应头
	 * 
	 * @return
	 */
	public static HttpHeaders responseHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
		return responseHeaders;
	}
}
