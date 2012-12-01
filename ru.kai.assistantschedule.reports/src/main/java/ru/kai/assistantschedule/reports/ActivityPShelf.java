package ru.kai.assistantschedule.reports;

import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.status.open.IStatus;
import ru.kai.assistantschedule.status.open.StatusImpl;

public class ActivityPShelf {

    protected static final Logger LOG = LoggerFactory.getLogger(ActivityPShelf.class);
    
    private PShelf _shelf;
    
    private Text schedullePathText;

    private Text loadPathText;

    // Получаем экземпляр консоли, для вывода в него вспомогательной информации
    private IStatus status = StatusImpl.getInstance();
    
    public ActivityPShelf(Composite parent) {
	parent.setLayout(new FillLayout());
	_shelf = new PShelf(parent, SWT.NONE);

	// Optionally, change the renderer
	// shelf.setRenderer(new RedmondShelfRenderer());

	PShelfItem professorsScheduleShelf = new PShelfItem(_shelf, SWT.NONE);
	professorsScheduleShelf.setText("Расписание преподователей");
	professorsScheduleShelf.getBody().setLayout(getGridLayout());

	Button uploadScheduleBtn = new Button(professorsScheduleShelf.getBody(), SWT.FLAT);
	uploadScheduleBtn.setText("Общее");
	uploadScheduleBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	uploadScheduleBtn.addSelectionListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
//		openShedule();
	    }
	    
	});
	

	Button uploadProfessorsLoadBtn = new Button(professorsScheduleShelf.getBody(), SWT.FLAT);
	uploadProfessorsLoadBtn.setText("Частное");
	uploadProfessorsLoadBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	uploadProfessorsLoadBtn.addSelectionListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
//		openProfessorsLoad();
	    }
	    
	});
    }
    
    private GridLayout getGridLayout() {
	GridLayout layout = new GridLayout();
	layout.marginLeft = 0;
	layout.marginRight = 0;
	layout.marginTop = 0;
	
	return layout;
    }

    public void setFocus() {
	_shelf.setFocus();
    }

    public void dispose() {
	_shelf.dispose();
    }

}
