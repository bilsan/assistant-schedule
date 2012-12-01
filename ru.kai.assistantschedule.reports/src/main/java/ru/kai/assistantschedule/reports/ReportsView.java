package ru.kai.assistantschedule.reports;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ReportsView extends ViewPart {
    public static final String ID = "ru.kai.assistantschedule.reports.view";

    private ActivityPShelf shelf;

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent) {
	shelf = new ActivityPShelf(parent);
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
	shelf.setFocus();
    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub
	shelf.dispose();
	super.dispose();
    }
}