package ru.kai.assistantschedule.schedule;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.core.MainCommand;
import ru.kai.assistantschedule.core.cache.LessonType;
import ru.kai.assistantschedule.core.cache.Time;
import ru.kai.assistantschedule.core.calendar.Class;
import ru.kai.assistantschedule.core.model.schedule.ScheduleContentProvider;
import ru.kai.assistantschedule.core.model.schedule.ScheduleLabelProvider;
import ru.kai.assistantschedule.ui.forms.AbstractScheduleTable;

public class ScheduleTableProcessing extends AbstractScheduleTable {

    protected static final Logger LOG = LoggerFactory
	    .getLogger(ScheduleTableProcessing.class);

    public ScheduleTableProcessing(Composite parent) {
		super(parent);
		MainCommand.setScheduleTableProcessing(this);
    }

    @Override
    protected void listeners() {
	// TODO Auto-generated method stub

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
