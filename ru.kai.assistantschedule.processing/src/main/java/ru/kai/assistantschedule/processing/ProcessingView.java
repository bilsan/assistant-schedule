package ru.kai.assistantschedule.processing;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ProcessingView extends ViewPart {

    public static final String ID = "ru.kai.assistantschedule.processing.view";

    private ProcessingForm form;

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
        form = new ProcessingForm(parent);
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        form.setFocus();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        form.dispose();
        super.dispose();
    }

}