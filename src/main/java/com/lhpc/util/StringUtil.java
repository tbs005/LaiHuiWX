package com.lhpc.util;

public class StringUtil {

	private StringUtil() {

	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param string
	 * @return true不为空,false为空
	 */
	public static boolean isOrNotEmpty(String string) {
		boolean flag = false;
		if (string != null && !string.equals(""))
			flag = true;
		return flag;
	}
	
	/**
	 * 验证字符串并根据情形返回数据
	 * @param input 传入的字符串
	 * @return
	 */
	 public static String checkNull(String input) {
	        String result = "";
	        if (input == null || input.trim().equals("") || input.trim().equals("null") || input.trim().equals("NULL")) {
	            result = "";
	        } else {
	            result = input;
	        }
	        return result;
	    }
	
}
