package ru.kai.assistantschedule.setting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.core.calendar.Activity;

/**
 * User: Роман
 * Date: 23.03.12
 * Time: 15:33
 */
@Deprecated
public class ActivityTreeContentProvider implements ITreeContentProvider {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ActivityTreeContentProvider.class);

//    private Activity upload;
//    private Activity download;
//    private Activity lectureRoom;

    public ActivityTreeContentProvider() {

        // Корневые элементы
//        upload = new Activity();
//        upload.setName("Загрузка");
//        List<String> documents = new ArrayList<String>();
//        documents.add("Расписание");
//        documents.add("Нагрузка");
//        upload.setDocuments(documents);
//        
//        download = new Activity();
//        download.setName("Выгрузка");
//        documents = new ArrayList<String>();
//        documents.add("Расписание");
//        download.setDocuments(documents);
//        
//        lectureRoom = new Activity();
//        lectureRoom.setName("Аудитории");
//        documents = new ArrayList<String>();
//        documents.add("Автозаполнение");
//        lectureRoom.setDocuments(documents);
                
    }

    @Override
    public Object[] getElements(Object o) {
        return new Object[]{"Загрузка", "Выгрузка", "Аудитории"};
    }

    @Override
    public Object[] getChildren(Object o) {
        
        // Return the files and subdirectories in this directory
        if(String.valueOf(o).equals("Загрузка")) {
        	return new String[]{"Расписание", "Нагрузка"};
        }
        if(String.valueOf(o).equals("Выгрузка")) {
        	return new String[]{"Расписание"};
        }
        if(String.valueOf(o).equals("Аудитории")) {
        	return new String[]{"Автозаполнение"};
        }
    	
    	return null;
    }

    @Override
    public Object getParent(Object o) {
        return null;
    }

    @Override
    public boolean hasChildren(Object o) {
        // Get the children
        Object[] obj = getChildren(o);

        // Return whether the parent has children
        return obj == null ? false : obj.length > 0;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object o, Object o1) {
        LOGGER.debug("Input changed " + viewer.getInput());
    }

}
