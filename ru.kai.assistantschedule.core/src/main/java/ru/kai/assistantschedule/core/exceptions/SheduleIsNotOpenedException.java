package ru.kai.assistantschedule.core.exceptions;

public class SheduleIsNotOpenedException extends Exception {
	private static final long serialVersionUID = 4898126402844285011L;

	public SheduleIsNotOpenedException() {
		super();
	}

	public SheduleIsNotOpenedException(String message, Throwable arg0) {
		super(message, arg0);
	}

	public SheduleIsNotOpenedException(String message) {
		super(message);
	}

	public SheduleIsNotOpenedException(Throwable arg0) {
		super(arg0);
	}
	
	

}
