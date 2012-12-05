package ru.kai.assistantschedule.processing;

import java.util.GregorianCalendar;

import org.apache.log4j.varia.FallbackErrorHandler;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.core.ExcelWorker;
import ru.kai.assistantschedule.core.GlobalStorage;
import ru.kai.assistantschedule.core.calendar.SemestrBuilder;
import ru.kai.assistantschedule.core.exceptions.ScheduleIsNotOpenedException;
import ru.kai.assistantschedule.status.open.IStatus;
import ru.kai.assistantschedule.status.open.StatusImpl;

public class ActivityPShelf {

    protected static final Logger LOG = LoggerFactory
	    .getLogger(ActivityPShelf.class);

    private PShelf _shelf;

    private Text schedullePathText;

    private Text loadPathText;

    // Получаем экземпляр консоли, для вывода в него вспомогательной информации
    private IStatus status = StatusImpl.getInstance();

    private Button _findProfessorsBtn;
    
    private Button _fallBtn;

    private Button _springBtn;
    
    private Combo _matchesPercentagesCombo;
    
    private Button _generateScheduleBtn;
    
    private final CDateTime _fromCdt;
    
    private final CDateTime _toCdt;
    
    public ActivityPShelf(Composite parent) {
	parent.setLayout(new FillLayout());
	_shelf = new PShelf(parent, SWT.NONE);

	// Optionally, change the renderer
	// shelf.setRenderer(new RedmondShelfRenderer());

	PShelfItem professorsShelf = new PShelfItem(_shelf, SWT.NONE);
	professorsShelf.setText("Преподователи");
	professorsShelf.getBody().setLayout(getGridLayout());

	// Create the first Group
	Group semesterGroup = new Group(professorsShelf.getBody(),
		SWT.SHADOW_IN);
	semesterGroup.setText("Семестр");
	semesterGroup.setLayout(new RowLayout(SWT.VERTICAL));
	semesterGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	_fallBtn = new Button(semesterGroup, SWT.RADIO);
	_fallBtn.setText("Осень");
	_fallBtn.setSelection(true);
	
	_springBtn = new Button(semesterGroup, SWT.RADIO);
	_springBtn.setText("Весна");

	String matchesPercentages[] = { "50", "75", "100" };
	Group matchesPercentagesGroup = new Group(professorsShelf.getBody(),
		SWT.SHADOW_IN);
	matchesPercentagesGroup.setText("Процент совпадения");
	matchesPercentagesGroup.setLayout(new GridLayout());
	matchesPercentagesGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	_matchesPercentagesCombo = new Combo(matchesPercentagesGroup,
		SWT.DROP_DOWN | SWT.READ_ONLY);
	_matchesPercentagesCombo.setItems(matchesPercentages);
	_matchesPercentagesCombo.select(2);
	_matchesPercentagesCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	
	_findProfessorsBtn = new Button(professorsShelf.getBody(),
		SWT.FLAT);
	_findProfessorsBtn.setText("Поиск преподователей");
	_findProfessorsBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	_findProfessorsBtn.addSelectionListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
//		openShedule();
	    }

	});

	//==============================================================

	PShelfItem scheduleSheld = new PShelfItem(_shelf, SWT.NONE);
	scheduleSheld.setText("Расписание");
	scheduleSheld.getBody().setLayout(getGridLayout());

	Group fromDateGroup = new Group(scheduleSheld.getBody(),
		SWT.SHADOW_IN);
	fromDateGroup.setText("Дата начала");
	fromDateGroup.setLayout(new GridLayout());
	fromDateGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	_fromCdt = new CDateTime(fromDateGroup, CDT.BORDER | CDT.DROP_DOWN);
	_fromCdt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	Group toDateGroup = new Group(scheduleSheld.getBody(),
		SWT.SHADOW_IN);
	toDateGroup.setText("Дата окончания");
	toDateGroup.setLayout(new GridLayout());
	toDateGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	_toCdt = new CDateTime(toDateGroup, CDT.BORDER | CDT.DROP_DOWN);
	_toCdt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	
	_generateScheduleBtn = new Button(scheduleSheld.getBody(), SWT.FLAT);
	_generateScheduleBtn.setText("Сформировать расписание");
	_generateScheduleBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        _generateScheduleBtn.setEnabled(false);
	
        listeners();
    }
    
    private void listeners() {
        /**
         * Назначение обработок кнопкам
         */
	_findProfessorsBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent arg0) {
        	fullCheck(); 
            }
        });
	
	_fromCdt.addSelectionListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		GlobalStorage.beginingOfSemestr = _fromCdt.getSelection();
                checkToEnableGenerateScheduleBtn();
	    }
	    
	});

	_toCdt.addSelectionListener(new SelectionAdapter() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		GlobalStorage.endOfSemestr = _toCdt.getSelection();
		checkToEnableGenerateScheduleBtn();
	    }
	    
	});

        _generateScheduleBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent arg0) {
                SemestrBuilder SB = new SemestrBuilder(GlobalStorage.beginingOfSemestr, GlobalStorage.endOfSemestr);
                //@ FIXME: Функцию нужно переписать. Поэтому закомментил 
                //try {
                //    ExcelWorker.GenerateSchedule(SB);
                //} catch (ScheduleIsNotOpenedException e) {
                //    status.setText("Расписание не открыто! Обработка отменена...");
                //}
            }
        });

    }
    
    private void checkToEnableGenerateScheduleBtn() {
	if (GlobalStorage.beginingOfSemestr != null 
        	&& GlobalStorage.endOfSemestr != null 
        	&& (GlobalStorage.beginingOfSemestr.before(GlobalStorage.endOfSemestr) 
        		|| GlobalStorage.beginingOfSemestr.equals(GlobalStorage.endOfSemestr))) {
            _generateScheduleBtn.setEnabled(true);
        } else {
            _generateScheduleBtn.setEnabled(false);            
        }
    }
    
    private void fullCheck() {
	//====================== 1 - я проверка ==========================
	status.setText("");
        if (!ExcelWorker.isScheduleOpened())
            return;
        GlobalStorage.matrix = ExcelWorker.searchEmptyCellsOfPMI();
        int maxLength = 0;
        for (int i = 0; i < GlobalStorage.matrix.length; i++)
            if (GlobalStorage.matrix[i][2].length() > maxLength)
                maxLength = GlobalStorage.matrix[i][2].length();
        String str;
        for (str = ""; str.length() != (maxLength / 8); str += "\t") {
        }
        status.setFont(new Font(_shelf.getDisplay(), new FontData("Courier", 10, SWT.BOLD)));
        status.append("Строка: Группа: Предмет:" + str + "Форма:" + "\n");
        status.setFont(new Font(_shelf.getDisplay(), new FontData("Courier", 10, SWT.NORMAL)));
        for (int i = 0; i < GlobalStorage.matrix.length; i++) {
            status.append(i == 0 ? "" : "\n");
            for (str = ""; (GlobalStorage.matrix[i][2].length() / 8 + str.length()) != (maxLength / 8 + 1); str += "\t") {
            }
            status.append(GlobalStorage.matrix[i][0] + "\t" + GlobalStorage.matrix[i][1] + "\t" + GlobalStorage.matrix[i][2] + str + GlobalStorage.matrix[i][3]);
        }
        status.append("\nНайдено записей: " + GlobalStorage.matrix.length);
        
        //====================== 2 - я проверка ==========================
        
        int suc = 0;
        int season = 0; // autumn as default
        if (_fallBtn.getSelection() == true)
            season = 0; // autumn
        else if (_springBtn.getSelection() == true)
            season = 1; // spring
        if (ExcelWorker.isLoadOpened()) {
            int percent = 100;
            if (_matchesPercentagesCombo.getSelectionIndex() == 0)
                percent = 50;
            else if (_matchesPercentagesCombo.getSelectionIndex() == 1)
                percent = 75;
            else if (_matchesPercentagesCombo.getSelectionIndex() == 2)
                percent = 100;
            GlobalStorage.matrix = ExcelWorker.openGeneralLoad(GlobalStorage.matrix, season, percent);
            if (GlobalStorage.matrix == null)
                return;
            status.setText("");
            maxLength = 0;
            for (int i = 0; i < GlobalStorage.matrix.length; i++)
                if (GlobalStorage.matrix[i][2].length() > maxLength)
                    maxLength = GlobalStorage.matrix[i][2].length();

            for (str = ""; str.length() != (maxLength / 8); str += "\t") {
            }
            status.append("Строка: Группа: Предмет:" + str + "Форма:  Найдено:" + "\n");
            for (int i = 0; i < GlobalStorage.matrix.length; i++) {
                if (GlobalStorage.matrix[i][4] != null)
                    suc++;
                status.append(i == 0 ? "" : "\n");
                for (str = ""; (GlobalStorage.matrix[i][2].length() / 8 + str.length()) != (maxLength / 8 + 1); str += "\t") {
                }
                status.append(GlobalStorage.matrix[i][0] + "\t" + GlobalStorage.matrix[i][1] + "\t" + GlobalStorage.matrix[i][2] + str + GlobalStorage.matrix[i][3] + "\t" + GlobalStorage.matrix[i][4]);
            }
            status.append("\nНайдено записей: " + GlobalStorage.matrix.length + "\t\tУспешно найдено: " + suc);
        }
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
