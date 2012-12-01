package ru.kai.assistantschedule.ui.forms;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerEditor;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import ru.kai.assistantschedule.core.calendar.Class;

public abstract class AbstractScheduleTable {

    private GridTableViewer v;

    private Composite composite;

    public AbstractScheduleTable(Composite parent) {
	parent.setLayout(new FillLayout());
	composite = new Composite(parent, SWT.NONE);
	composite.setLayout(new FillLayout());
	v = new GridTableViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	v.setLabelProvider(getLabelProvider());
	v.setContentProvider(getContentProvider());
	v.getGrid().setCellSelectionEnabled(true);
			
	v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getGrid()), new TextCellEditor(v.getGrid()) });
	v.setCellModifier(new ICellModifier() {

		public boolean canModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			return "Column " + property + " => " + element.toString();
		}

		public void modify(Object element, String property, Object value) {
			
		}
		
	});
	
	v.setColumnProperties(new String[] {"1","2"});
//	v.setColumnProperties(new String[] {"Группа","Время","Дисциплина","Вид занятий","Преподователь","Кафедра"});
	
	ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(v) {
		protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
			return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
					|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR);
		}
	};
	
	GridViewerEditor.create(v, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
			| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
			| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
	
	GridColumn column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Группа");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("День");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Время");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Дата");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Дисциплина");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Вид занятий");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Аудитория");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Здание");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Должность");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Преподователь");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Кафедра");
	
	v.setInput(getInput());
	v.getGrid().setLinesVisible(true);
	v.getGrid().setHeaderVisible(true);
	v.getGrid().setRowHeaderVisible(true);
	
	listeners();
    }
    
    protected abstract void listeners();
    
    protected abstract Class[] getInput();

    protected abstract IBaseLabelProvider getLabelProvider();

    protected abstract IContentProvider getContentProvider();
    
    public void setFocus() {
	composite.setFocus();
    }

    public void dispose() {
	composite.dispose();
    }
    
}
