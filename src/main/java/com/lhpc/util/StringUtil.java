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
}
