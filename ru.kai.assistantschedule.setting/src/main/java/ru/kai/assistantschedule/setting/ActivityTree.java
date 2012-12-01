/**
 * 
 */
package ru.kai.assistantschedule.setting;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.core.ExcelWorker;
import ru.kai.assistantschedule.core.GlobalStorage;
import ru.kai.assistantschedule.status.open.IStatus;
import ru.kai.assistantschedule.status.open.StatusImpl;

/**
 * @author Роман
 * 
 */
@Deprecated
public class ActivityTree {

    protected static final Logger LOG = LoggerFactory
	    .getLogger(ActivityTree.class);

    /**
     * Просмоторщик дерева атрибутов
     */
    private TreeViewer _activityTreeViewer;

    private Text schedullePathText;

    private Text loadPathText;

    // Получаем экземпляр консоли, для вывода в него вспомогательной информации
    private IStatus status = StatusImpl.getInstance();

    public ActivityTree(Composite composite) {
	_activityTreeViewer = new TreeViewer(composite);
	_activityTreeViewer
		.setContentProvider(new ActivityTreeContentProvider());
	_activityTreeViewer.setLabelProvider(new ActivityTreeLabelProvider());
	// _attributeTreeViewer.setSorter(new AttributeSorter(true));

	_activityTreeViewer.setInput(new Object()); // pass a non-null that will
						    // be ignored
	_activityTreeViewer.expandToLevel(_activityTreeViewer.getTree()
		.getItems()[0].getData(), 1);

	layouts();
	listeners();
    }

    private void layouts() {
	FormData formData;

	formData = new FormData();
	formData.left = new FormAttachment(0, 0);
	formData.top = new FormAttachment(0, 0);
	formData.right = new FormAttachment(100, 0);
	formData.bottom = new FormAttachment(100, 0);
	_activityTreeViewer.getTree().setLayoutData(formData);
    }

    private void listeners() {
	SelectionAdapter selectActivityListener = new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		LOG.debug("Selection ActivityTree {}", e);
		if (0 >= _activityTreeViewer.getTree().getSelection().length) {
		    return;
		}
		TreeItem item = _activityTreeViewer.getTree().getSelection()[0];
		TreeItem parent = item.getParentItem();
		String parentActivity = (parent != null) ? String
			.valueOf(parent.getData()) : "";
		String activity = String.valueOf(_activityTreeViewer.getTree()
			.getSelection()[0].getData());
		if (parentActivity.equals("Загрузка")
			&& activity.equals("Расписание")) {
		    openShedule();
		}

		if (parentActivity.equals("Загрузка")
			&& activity.equals("Нагрузка")) {
		    openProfessorsLoad();
		}
	    }

	};

	_activityTreeViewer.getTree().addSelectionListener(
		selectActivityListener);
    }

    /**
     * Только копируют в schedullePathText
     * 
     * @author Дамир
     */
    private void openShedule() {
	LOG.debug("Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, "
		+ "путь по умолчанию и фильтры. "
		+ "Получаем String переменную результата и выводим её.");
	LOG.info("Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, "
		+ "путь по умолчанию и фильтры. "
		+ "Получаем String переменную результата и выводим её.");

	try {
	    /**
	     * Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, путь по
	     * умолчанию и фильтры. Получаем String переменную результата и
	     * выводим её.
	     */
	    FileDialog fd = new FileDialog(_activityTreeViewer.getTree()
		    .getShell(), SWT.OPEN);
	    fd.setText("Открыть расписание");
	    fd.setFilterPath("C:/");
	    String[] filterExt = { "*.xls" };
	    fd.setFilterExtensions(filterExt);
	    if ((GlobalStorage.selectedSchedule = fd.open()) != null) {
		schedullePathText.setText(GlobalStorage.selectedSchedule);
		GlobalStorage.put("selectedSchedule",
			GlobalStorage.selectedSchedule);
		try {
		    ExcelWorker.openSchedule(GlobalStorage.selectedSchedule);
		} catch (Exception e) {
		    status.setText(e.getLocalizedMessage());
		}
		status.setText("Расписание открыто");
	    }
	} catch (Exception exception) {
	    LOG.error(exception.getMessage());
	}
    }

    /**
     * Только копирует строку в loadPathText
     * 
     * @author Дамир
     */
    private void openProfessorsLoad() {
	LOG.debug("OpenLoadOfProffs");
	LOG.info("OpenLoadOfProffs");
	try {
	    /**
	     * Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, путь по
	     * умолчанию и фильтры. Получаем String переменную результата и
	     * выводим её.
	     */
	    FileDialog fd = new FileDialog(_activityTreeViewer.getTree()
		    .getShell(), SWT.OPEN);
	    fd.setText("Открыть нагрузку");
	    fd.setFilterPath("C:/");
	    String[] filterExt = { "*.xls" };
	    fd.setFilterExtensions(filterExt);
	    if ((GlobalStorage.selectedProffsLoad = fd.open()) != null) {
		loadPathText.setText(GlobalStorage.selectedProffsLoad);
		GlobalStorage.put("selectedProffsLoad",
			GlobalStorage.selectedProffsLoad);
		try {
		    ExcelWorker.openLoad(GlobalStorage.selectedProffsLoad);
		} catch (Exception e) {
		    status.setText(e.getLocalizedMessage());
		}
		status.setText("Нагрузка открыта");
	    }
	} catch (Exception exception) {
	    LOG.error(exception.getMessage());
	}
    }

    public void setFocus() {
	// TODO Auto-generated method stub
	_activityTreeViewer.getTree().setFocus();
    }

    public void dispose() {
	// TODO Auto-generated method stub
	_activityTreeViewer.getTree().dispose();
    }

}
