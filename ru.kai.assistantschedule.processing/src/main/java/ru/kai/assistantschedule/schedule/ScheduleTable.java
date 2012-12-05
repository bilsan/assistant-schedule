package ru.kai.assistantschedule.schedule;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.core.cache.LessonType;
import ru.kai.assistantschedule.core.cache.Time;
import ru.kai.assistantschedule.core.calendar.Class;
import ru.kai.assistantschedule.core.model.schedule.ScheduleContentProvider;
import ru.kai.assistantschedule.core.model.schedule.ScheduleLabelProvider;
import ru.kai.assistantschedule.ui.forms.AbstractScheduleTable;

public class ScheduleTable extends AbstractScheduleTable {

    protected static final Logger LOG = LoggerFactory
	    .getLogger(ScheduleTable.class);

    public ScheduleTable(Composite parent) {
	super(parent);
    }

    @Override
    protected void listeners() {
	// TODO Auto-generated method stub

    }

    @Override
    protected Class[] getInput() {
	Class[] elements = new Class[10];
	for (int i = 0; i < 10; i++) {
	    elements[i] = new Class(Time.at08_00, "lectureRoom_" + i,
		    "discipline_" + i, LessonType.LEC, "group_" + i,
		    "professor_" + i, "department_" + i);
	}

	return elements;
    }

    @Override
    protected IBaseLabelProvider getLabelProvider() {
	return new ScheduleLabelProvider();
    }

    @Override
    protected IContentProvider getContentProvider() {
	return new ScheduleContentProvider();
    }

}
