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

public abstract class AbstractProfessorsLoadTable {

    private GridTableViewer v;

    private Composite composite;

    public AbstractProfessorsLoadTable(Composite parent) {
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
	column.setText("NN");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Дисциплина");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Факультет");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Форма обучения");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Специальность");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Курс");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("чис. пот.");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("чис. гр.");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("чис. подгр.");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("кол-во студ.");
	
	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("кол-во нед.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("лекции");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("практ. зан.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("лаб. раб.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("конс.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("курс. раб.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("курс. пр.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("дом. зад.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("контр. раб.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("колл.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("зачёты");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("экзамен");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("итого за весенн.\\семестр без учёт\\дом. задан.,\\контр.и коллок..");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("итого за весенн.\\семестр c учёт.\\дом. задан., .\\контри коллок..");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("итого за год без\\учёт.дом. зад.,\\контр. и коллок.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("итого за год с\\учёт. дом. зад.,\\контр. и коллок..");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("лекции");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("практика");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("лаб.раб.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("конс.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("курс.раб.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("курс.пр.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("зачет");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("экзамен");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("итого");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Дом.з.");

	column = new GridColumn(v.getGrid(), SWT.NONE);
	column.setWidth(100);
	column.setText("Контр.раб.");
	
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
