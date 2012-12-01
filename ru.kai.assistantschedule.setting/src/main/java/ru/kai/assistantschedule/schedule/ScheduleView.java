package ru.kai.assistantschedule.schedule;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import ru.kai.assistantschedule.setting.SettingsAndConnectionForm;

public class ScheduleView extends ViewPart {

    public static final String ID = "ru.kai.assistantschedule.schedule.view";

    private ScheduleTable scheduleTable;

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
    	scheduleTable = new ScheduleTable(parent);
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
    	scheduleTable.setFocus();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        scheduleTable.dispose();
        super.dispose();
    }
    
}