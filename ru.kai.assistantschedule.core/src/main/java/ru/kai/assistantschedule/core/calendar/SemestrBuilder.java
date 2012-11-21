package ru.kai.assistantschedule.core.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SemestrBuilder {
	public List<Week> semestr = new ArrayList<Week>();
	private Calendar GC = GregorianCalendar.getInstance();
	
	public SemestrBuilder(Date begin, Date end){
		GC.setTime(begin);
		while(GC.getTime().before(end) || GC.getTime().equals(end)){
			semestr.add(new Week());
			switch (GC.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.MONDAY: semestr.get(semestr.size()-1).days.add(new Day(GC.getTime(), Calendar.MONDAY)); GC.add(Calendar.DATE, 1); if(GC.getTime().after(end))break;
				case Calendar.TUESDAY: semestr.get(semestr.size()-1).days.add(new Day(GC.getTime(), Calendar.TUESDAY)); GC.add(Calendar.DATE, 1); if(GC.getTime().after(end))break;
				case Calendar.WEDNESDAY: semestr.get(semestr.size()-1).days.add(new Day(GC.getTime(), Calendar.WEDNESDAY)); GC.add(Calendar.DATE, 1); if(GC.getTime().after(end))break;
				case Calendar.THURSDAY: semestr.get(semestr.size()-1).days.add(new Day(GC.getTime(), Calendar.THURSDAY)); GC.add(Calendar.DATE, 1); if(GC.getTime().after(end))break;
				case Calendar.FRIDAY: semestr.get(semestr.size()-1).days.add(new Day(GC.getTime(), Calendar.FRIDAY)); GC.add(Calendar.DATE, 1); if(GC.getTime().after(end))break;
				case Calendar.SATURDAY: semestr.get(semestr.size()-1).days.add(new Day(GC.getTime(), Calendar.SATURDAY)); GC.add(Calendar.DATE, 1); if(GC.getTime().after(end))break;
				default: GC.add(Calendar.DATE, 1);
			}
		}
		GC.setTime(begin);
	}

	public static void addLesson(){} 

}
