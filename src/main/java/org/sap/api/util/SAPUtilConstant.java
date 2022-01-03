package org.sap.api.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
public class SAPUtilConstant {
	
	public enum JOB_SCHEDULER_TIME{
	    	D,W,M,Y,H
	}
	
	public static Date startDate(int jobDay,String jobType) {
    	Date startDate=null;
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
    	if(JOB_SCHEDULER_TIME.D.toString().equalsIgnoreCase(jobType)){
    		calendar.add(Calendar.DATE,-jobDay);
    	}else if(JOB_SCHEDULER_TIME.M.toString().equalsIgnoreCase(jobType)) {
    		calendar.add(Calendar.MONTH,-jobDay);
    	}else if(JOB_SCHEDULER_TIME.Y.toString().equalsIgnoreCase(jobType)) {
    		calendar.add(Calendar.YEAR,-jobDay);
    	}else if(JOB_SCHEDULER_TIME.W.toString().equalsIgnoreCase(jobType)) {
    		calendar.add(Calendar.WEEK_OF_MONTH,-jobDay);
    	}else if(JOB_SCHEDULER_TIME.H.toString().equalsIgnoreCase(jobType)) {
    		calendar.add(Calendar.HOUR,-jobDay);
    	}
    	startDate=calendar.getTime();
    	return startDate;
    }
	
	public static boolean isEmpty(Object obj) {
		return (obj==null)?true:false;
	}
	
	public static LocalDate convertDateToLocalDate(Date date) {
		if(date!=null) {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}else {
			return null;
		}
	}
	
	public static LocalTime convertDateToLocalTime(Date date) {
		if(date!=null) {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		}else {
			return null;
		}
	}
}