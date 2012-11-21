/**
 * 
 */
package ru.kai.assistantschedule.status.open;

import org.eclipse.swt.graphics.Font;

import ru.kai.assistantschedule.status.StatusView;


/**
 * Singleton - для работы со строкой состояния
 * @author Роман
 *
 */
public class StatusImpl implements IStatus {
    
    private static IStatus instance;
    
    public StatusImpl() {}
    
    public static IStatus getInstance() {
        if(instance == null) {
            instance = new StatusImpl();
        }
        return instance;
    }
    /* (non-Javadoc)
     * @see ru.kai.assistantschedule.status.open.IStatus#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
        StatusView.t2.setText(text);
    }

    @Override
    public void setFont(Font font) {
        StatusView.t2.setFont(font);
    }

    @Override
    public void append(String string) {
        StatusView.t2.append(string);
    }

}
