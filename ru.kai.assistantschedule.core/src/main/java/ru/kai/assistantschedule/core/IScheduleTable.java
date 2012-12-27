package ru.kai.assistantschedule.core;

import java.util.List;

import ru.kai.assistantschedule.core.cache.ScheduleEntry;

public interface IScheduleTable {
	public void setInput(List<ScheduleEntry> elements);
}
