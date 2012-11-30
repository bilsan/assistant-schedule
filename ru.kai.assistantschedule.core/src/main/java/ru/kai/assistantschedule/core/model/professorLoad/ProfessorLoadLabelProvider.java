package ru.kai.assistantschedule.core.model.professorLoad;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import ru.kai.assistantschedule.core.calendar.Class;

public class ProfessorLoadLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableFontProvider, ITableColorProvider {
	
	FontRegistry registry = new FontRegistry();

	@Override
	public Color getForeground(Object element, int columnIndex) {
//		if (((MyModel) element).counter % 2 == 1) {
//			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
//		}
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
//		if (((MyModel) element).counter % 2 == 0) {
//			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
//		}
		return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
//		if (((MyModel) element).counter % 2 == 0) {
//			return registry.getBold(Display.getCurrent().getSystemFont()
//					.getFontData()[0].getName());
//		}
		return null;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// Выводим данные в колонках
		Class classRow = (Class) element;
		
		switch (columnIndex) {
			case 0:
				return classRow.group;
			case 1:
				return classRow.time.toString();
			case 2:
				return classRow.discipline;
			case 3:
				return classRow.lessonType.toString();
			case 4:
				return classRow.lectureRoom;
			case 5:
				return classRow.professor;
			case 6:
				return classRow.department;
			default:
				return "";
		}
	}

}
