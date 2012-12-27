package ru.kai.assistantschedule.core.model.schedule;

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

import ru.kai.assistantschedule.core.cache.ScheduleEntry;
import ru.kai.assistantschedule.core.calendar.Class;

public class ScheduleLabelProvider extends LabelProvider implements
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
		ScheduleEntry classRow = (ScheduleEntry) element;
		
		switch (columnIndex) {
			case 0:
				return classRow.groupName;
			case 1:
				switch (classRow.day) {
					case mon: return "пн";
					case tue: return "вт";
					case wed: return "ср";
					case thu: return "чт";
					case fri: return "пт";
					case sat: return "сб";
					default: return "";
				}
			case 2:
				switch (classRow.time) {
					case at08_00: return "8:00";
					case at09_40: return "9:40";
					case at11_30: return "11:30";
					case at13_10: return "13:10";
					case at15_00: return "15:00";
					case at16_40: return "16:40";
					case at18_15: return "18:15";
					case at19_45: return "19:45";
					default: return "";
				}
			case 3:
				return classRow.date;
			case 4:
				return classRow.discipline;
			case 5:
				switch (classRow.lessonType) {
					case LEC: return "лек";
					case PRAC: return "пр";
					case LABS: return "л.р.";
					case IZ: return "и.з.";
					case OTHER: return "";
					default: return "";
				}
			case 6:
				return classRow.classRoom;
			case 7:
				return classRow.building;
			case 8:
				return classRow.doljnost;
			case 9:
				return classRow.prepodavatel;
			case 10:
				return classRow.kafedra;
			default:
				return "";
		}
	}

}
