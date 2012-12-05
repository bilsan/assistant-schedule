package ru.kai.assistantschedule.core.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.kai.assistantschedule.core.cache.LessonType;
import ru.kai.assistantschedule.core.cache.ScheduleEntry;
import ru.kai.assistantschedule.core.cache.Time;

public class Day {
	private Calendar dateOfTheDay = new GregorianCalendar();
	public final String dateStr;
	public List<ScheduleEntry> classes = new ArrayList<ScheduleEntry>();
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

	//@ FIXME: Пока закоментил. Нужно переосмыслить и сделать=)
	public void addClass(Time time, String audit, String disc, LessonType lesType, String prepod, String kaf, String groups ){
		//classes.add(new ScheduleEntry(groups, null, time, null, disc, lesType, audit, null, null, prepod, kaf ));
		//classes.add(new Class(audit, prepod, kaf));
	}
}
