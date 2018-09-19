package com.tcs.ifact.handler;

import java.time.DayOfWeek;
import java.time.YearMonth;

public class IFactHelper {

	public static boolean isNumeric(String str) {
		boolean result = false;
		try {
			Integer.parseInt(str);
			result = true;
		}catch(Exception e) {
			result = false;
		}
		return result;
	}

	public static String constructMonthMax(String workLocation, String year, String month) {
		int yearNum = Integer.parseInt(year);
		int mothNum = Integer.parseInt(month);
		YearMonth ymObj= YearMonth.of(yearNum, mothNum);
		int daysInMonth = ymObj.lengthOfMonth();
		int weekends = 0;
		for(int day=1;day<=daysInMonth;day++) {
			if(ymObj.atDay(day).getDayOfWeek().equals(DayOfWeek.SATURDAY)||
					ymObj.atDay(day).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				weekends++;
			}
		}
		int maxDays = (daysInMonth - weekends)*8;
		return Integer.toString(maxDays);
	}

}
