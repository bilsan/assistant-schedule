package ru.kai.assistantschedule.core.calendar;

public class Class {
	
	
	public Class(Time time, String lectureRoom, String discipline, 
			LessonType lessonType, String group, String professor, String department){
		this.time = time;
		this.lectureRoom = lectureRoom;
		this.discipline = discipline;
		this.lessonType = lessonType;
		this.group = group;
		this.professor = professor;
		this.department = department;
	}
	
	public Time time;
	public LessonType lessonType;
	public String discipline, group, professor, lectureRoom, department;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lectureRoom == null) ? 0 : lectureRoom.hashCode());
		result = prime * result + ((discipline == null) ? 0 : discipline.hashCode());
		result = prime * result + ((lessonType == null) ? 0 : lessonType.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((professor == null) ? 0 : professor.hashCode());
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
		if (lectureRoom == null) {
			if (other.lectureRoom != null) {
				return false;
			}
		} else if (!lectureRoom.equals(other.lectureRoom)) {
			return false;
		}
		if (discipline == null) {
			if (other.discipline != null) {
				return false;
			}
		} else if (!discipline.equals(other.discipline)) {
			return false;
		}
		if (lessonType != other.lessonType) {
			return false;
		}
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (department == null) {
			if (other.department != null) {
				return false;
			}
		} else if (!department.equals(other.department)) {
			return false;
		}
		if (professor == null) {
			if (other.professor != null) {
				return false;
			}
		} else if (!professor.equals(other.professor)) {
			return false;
		}
		if (time != other.time) {
			return false;
		}
		return true;
	}
	
}