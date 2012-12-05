package ru.kai.assistantschedule.core.cache;

public class ScheduleEntry {

	/****************************/
	/******* ПОЛЯ КЛАССА ********/ 
	/****************************/

	/**Номер записи*/
	public int id;

	/** Номер группы */
	public String groupName;

	/** День занятия */
	public DaysOfWeek day;

	/** Время занятия */
	public Time time;

	/** Дата занятия */
	public String date;

	/** Дисциплина */
	public String discipline;

	/** Вид занятия */
	public LessonType lessonType;

	/** Аудитория */
	public String classRoom;

	/** Здание */
	public String building;

	/** Должность */
	public String doljnost;

	/** Преподаватель */
	public String prepodavatel;

	/** Кафедра */
	public String kafedra;

	/****************************/
	/**** КОНЕЦ ПОЛЕЙ КЛАССА ****/ 
	/****************************/

	/**Конструктор полный*/
	public ScheduleEntry(String groupName, DaysOfWeek day, Time time, String date, String discipline, LessonType lessonType, String classRoom, String building, String doljnost, String prepodavatel, String kafedra){
		this.groupName = groupName;
		this.day = day;
		this.time = time;
		this.date = date;
		this.discipline = discipline;
		this.lessonType = lessonType;
		this.classRoom = classRoom;
		this.building = building;
		this.doljnost = doljnost;
		this.prepodavatel = prepodavatel;
		this.kafedra = kafedra;
	}

	/**
	 * hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((building == null) ? 0 : building.hashCode());
		result = prime * result + ((classRoom == null) ? 0 : classRoom.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((discipline == null) ? 0 : discipline.hashCode());
		result = prime * result + ((doljnost == null) ? 0 : doljnost.hashCode());
		result = prime * result + ((lessonType == null) ? 0 : lessonType.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((kafedra == null) ? 0 : kafedra.hashCode());
		result = prime * result + ((prepodavatel == null) ? 0 : prepodavatel.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	/**
	 * equals()
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ScheduleEntry)) {
			return false;
		}
		ScheduleEntry other = (ScheduleEntry) obj;
		if (building == null) {
			if (other.building != null) {
				return false;
			}
		} else if (!building.equals(other.building)) {
			return false;
		}
		if (classRoom == null) {
			if (other.classRoom != null) {
				return false;
			}
		} else if (!classRoom.equals(other.classRoom)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (day != other.day) {
			return false;
		}
		if (discipline == null) {
			if (other.discipline != null) {
				return false;
			}
		} else if (!discipline.equals(other.discipline)) {
			return false;
		}
		if (doljnost == null) {
			if (other.doljnost != null) {
				return false;
			}
		} else if (!doljnost.equals(other.doljnost)) {
			return false;
		}
		if (lessonType != other.lessonType) {
			return false;
		}
		if (groupName == null) {
			if (other.groupName != null) {
				return false;
			}
		} else if (!groupName.equals(other.groupName)) {
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
