package com.lhpc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private DateUtil(){
		
	}
	/**
	 * date转String
	 * @param date 要转换的时间
	 * @return 转换之后的时间
	 */
	public static String date2String (Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	} 
}
