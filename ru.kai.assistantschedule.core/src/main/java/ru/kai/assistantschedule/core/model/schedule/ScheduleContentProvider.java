package ru.kai.assistantschedule.core.model.schedule;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ru.kai.assistantschedule.core.cache.ScheduleEntry;
import ru.kai.assistantschedule.core.calendar.Class;

public class ScheduleContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return ((List<ScheduleEntry>) inputElement).toArray();
	}

}
