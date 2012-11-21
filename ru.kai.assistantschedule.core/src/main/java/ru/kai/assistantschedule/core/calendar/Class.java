package ru.kai.assistantschedule.core.calendar;

public class Class {
	public Class(Time t, String au, String disc, FormOfClass FoC, String groups, String prep, String kaf){
		this.time = t;
		this.classRoom = au;
		this.discipline = disc;
		this.formOfClass = FoC;
		this.groups = groups;
		this.prepodavatel = prep;
		this.kafedra = kaf;
	}
	
	public Time time;
	public FormOfClass formOfClass;
	public String discipline, groups, prepodavatel, classRoom, kafedra;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classRoom == null) ? 0 : classRoom.hashCode());
		result = prime * result + ((discipline == null) ? 0 : discipline.hashCode());
		result = prime * result + ((formOfClass == null) ? 0 : formOfClass.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((kafedra == null) ? 0 : kafedra.hashCode());
		result = prime * result + ((prepodavatel == null) ? 0 : prepodavatel.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Class)) {
			return false;
		}
		Class other = (Class) obj;
		if (classRoom == null) {
			if (other.classRoom != null) {
				return false;
			}
		} else if (!classRoom.equals(other.classRoom)) {
			return false;
		}
		if (discipline == null) {
			if (other.discipline != null) {
				return false;
			}
		} else if (!discipline.equals(other.discipline)) {
			return false;
		}
		if (formOfClass != other.formOfClass) {
			return false;
		}
		if (groups == null) {
			if (other.groups != null) {
				return false;
			}
		} else if (!groups.equals(other.groups)) {
			return false;
		}
		if (kafedra == null) {
			if (other.kafedra != null) {
				return false;
			}
		} else if (!kafedra.equals(other.kafedra)) {
			return false;
		}
		if (prepodavatel == null) {
			if (other.prepodavatel != null) {
				return false;
			}
		} else if (!prepodavatel.equals(other.prepodavatel)) {
			return false;
		}
		if (time != other.time) {
			return false;
		}
		return true;
	}
	
}