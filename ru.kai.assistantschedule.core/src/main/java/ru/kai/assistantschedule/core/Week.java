package ru.kai.assistantschedule.core;

import java.util.HashMap;

public class Week {
    /**
     * Задает четность недели
     * @param isEven
     */
	public Week(boolean isEven){
		even = isEven;
	}
	
	private boolean even = false;
	private HashMap<String, String> The7ThBuilding = new HashMap<String, String>(65);
	
		
	public void setParity(boolean parity) {this.even = parity;}
	public boolean isParity() {return even;}

	public void addLesson(String key, String groupName){The7ThBuilding.put(key, groupName);}
	
	public void delLesson(String key){The7ThBuilding.remove(key);}
	
	public boolean containsKey(String key){return The7ThBuilding.containsKey(key);}
	
	public String getValue(String key){return The7ThBuilding.get(key);}
}
