package ru.kai.assistantschedule.reports.professorsSchedule;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class IndividualView extends ViewPart {

    public static final String ID = "ru.kai.assistantschedule.reports.professorsSchedule.individualView";

//    private ProfessorLoadTable table;

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
//	table = new ProfessorLoadTable(parent);
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
//        table.setFocus();
    }

    @Override
    public void dispose() {
//        table.dispose();
        super.dispose();
    }
    
}