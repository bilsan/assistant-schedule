package ru.kai.assistantschedule.core.exceptions;

public class ScheduleIsNotOpenedException extends Exception {
	private static final long serialVersionUID = 4898126402844285011L;

	public ScheduleIsNotOpenedException() {
		super();
	}

	public ScheduleIsNotOpenedException(String message, Throwable arg0) {
		super(message, arg0);
	}

	public ScheduleIsNotOpenedException(String message) {
		super(message);
	}

	public ScheduleIsNotOpenedException(Throwable arg0) {
		super(arg0);
	}
	
	

}
