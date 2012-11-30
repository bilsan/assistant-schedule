package ru.kai.assistantschedule.core.calendar;

import java.util.List;

/**
 * Действия совершаемые над документами
 * (например: загрузка/выгрузка excel-файла)
 * @author Роман
 *
 */
public class Activity {
	
	private String name;
	
	private List<String> documents;

	public Activity() {
		super();
	}

	public Activity(String name, List<String> documents) {
		super();
		this.name = name;
		this.documents = documents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

}
