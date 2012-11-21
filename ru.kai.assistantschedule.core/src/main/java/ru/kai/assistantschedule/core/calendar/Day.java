package ru.kai.assistantschedule.core.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Day {
	private Calendar dateOfTheDay = new GregorianCalendar();
	public final String dateStr;
	public List<Class> classes = new ArrayList<Class>();
	public int DayOfWeek;
	
	public Day(Date date, int aDayOfWeek){
		dateOfTheDay.setTime(date);
		DayOfWeek = aDayOfWeek;
		String[] tmp = date.toString().split(" ");
		dateStr = tmp[0]+" "+tmp[2]+" "+" "+tmp[1]+" "+tmp[5]+" "+tmp[3];
	}

	public String getDate(){
		return dateOfTheDay.getTime().toString();
	}

	public void addClass(Time t, String audit, String disc, FormOfClass FoC, String prepod, String kaf, String groups ){
		classes.add(new Class(t, audit, disc, FoC, groups, prepod, kaf));
	}

}