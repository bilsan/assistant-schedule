package ru.kai.assistantschedule.setting;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class SettingView extends ViewPart {

    public static final String ID = "ru.kai.assistantschedule.setting.view";

    private SettingsAndConnectionForm form;

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
        form = new SettingsAndConnectionForm(parent);
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