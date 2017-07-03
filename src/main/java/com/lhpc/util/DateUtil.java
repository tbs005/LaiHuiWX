package com.lhpc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	 /** 
     * 获取时间的前一天时间 
     *  
     * @param cl 
     */ 
	
	public static Date date4OneDay(){
		Calendar   c   =   Calendar.getInstance();   
		c.add(Calendar.DAY_OF_MONTH, -1);  
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		//String mDateTime=formatter.format(c.getTime());  
		return c.getTime();
		
	}
	public static void main(String []args){
		System.out.println(date4OneDay());
	}
}
