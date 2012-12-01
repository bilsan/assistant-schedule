package ru.kai.assistantschedule.setting;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import ru.kai.assistantschedule.core.calendar.Activity;

/**
 * Отображает дерево действий
 * <p/>
 * User: Роман
 * Date: 23.03.12
 * Time: 15:35
 */
@Deprecated
public class ActivityTreeLabelProvider implements ILabelProvider {
    @Override
    public Image getImage(Object o) {
        return null;
    }

    @Override
    public String getText(Object o) {
        return (o instanceof Activity) ? ((Activity) o).getName() : String.valueOf(o);
    }

    @Override
    public void addListener(ILabelProviderListener iLabelProviderListener) {
        //stub
    }

    @Override
    public void dispose() {
        //stub
    }

    @Override
    public boolean isLabelProperty(Object o, String s) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener iLabelProviderListener) {
        //stub
    }
}
