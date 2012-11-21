package ru.kai.assistantschedule.processing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import ru.kai.assistantschedule.core.ExcelWorker;
import ru.kai.assistantschedule.core.GlobalStorage;
import ru.kai.assistantschedule.core.calendar.SemestrBuilder;
import ru.kai.assistantschedule.core.exceptions.SheduleIsNotOpenedException;
import ru.kai.assistantschedule.status.open.IStatus;
import ru.kai.assistantschedule.status.open.StatusImpl;

import java.util.GregorianCalendar;

/**
 * User: Роман
 * Date: 31.10.12
 * Time: 14:24
 */
public class ProcessingForm {

    private CTabFolder folder;
    private CTabItem tabItem;
    private Composite _mainComposite, _composite;
    Button schedulleChBTN, searchingInLoadBTN, radioB1, radioB2, setStartOfSemestrBtn, setEndOfSemestrBtn, processSchedule;
    Text startOfSemestrLabel, endOfSemestrLabel;
    Group groupSchCheking1, groupSchCheking2, groupClassRoomsChek;
    Combo matchingPercentageC;
    
    private IStatus status = StatusImpl.getInstance();

    public ProcessingForm(Composite parent) {
        _mainComposite = new Composite(parent, SWT.NONE);

        folder = new CTabFolder(_mainComposite, SWT.NONE);
        folder.setSimple(false);
        folder.setBorderVisible(true);
        folder.setSingle(false);
        folder.setUnselectedImageVisible(true);

        tabItem = new CTabItem(folder, SWT.NONE);
        tabItem.setText("Обработки");

        _composite = new Composite(folder, SWT.NONE);
        tabItem.setControl(_composite);
        folder.setSelection(tabItem);

        /**
         * Группа проверка расписания №1
         */
        groupSchCheking1 = new Group(_composite, SWT.NONE);
        groupSchCheking1.setText(" Проверка расписания ");

        /**
         * Наполнение: 1 кнопка
         */
        schedulleChBTN = new Button(groupSchCheking1, SWT.PUSH);
        schedulleChBTN.setText("Проверка №1");
        /**
         * Группа нахождения в нагрузке
         */
        groupSchCheking2 = new Group(_composite, SWT.NONE);
        groupSchCheking2.setText(" Поиск в нагрузке ");

        /**
         * Наполнение: 1 кнопка, 1 комбо со значениями 50% 75% и 100% и радио
         * кнопки: осень весна
         */
        searchingInLoadBTN = new Button(groupSchCheking2, SWT.PUSH);
        searchingInLoadBTN.setText("Проверка №2");

        radioB1 = new Button(groupSchCheking2, SWT.RADIO);
        radioB1.setText("Осень");
        radioB1.setSelection(true);

        radioB2 = new Button(groupSchCheking2, SWT.RADIO);
        radioB2.setText("Весна");
        radioB2.setSelection(false);

        String matchingPercents[] = {"50", "75", "100"};

        matchingPercentageC = new Combo(groupSchCheking2, SWT.DROP_DOWN | SWT.READ_ONLY);
        matchingPercentageC.setItems(matchingPercents);
        matchingPercentageC.select(2);
        matchingPercentageC.pack();

        groupClassRoomsChek = new Group(_composite, SWT.NONE);
        groupClassRoomsChek.setText(" Формирование расписания на семестр: ");


        startOfSemestrLabel = new Text(groupClassRoomsChek, SWT.READ_ONLY | SWT.CENTER | SWT.BORDER);


        setStartOfSemestrBtn = new Button(groupClassRoomsChek, SWT.PUSH);
        setStartOfSemestrBtn.setText("Дата начала");


        endOfSemestrLabel = new Text(groupClassRoomsChek, SWT.READ_ONLY | SWT.CENTER | SWT.BORDER);


        setEndOfSemestrBtn = new Button(groupClassRoomsChek, SWT.PUSH);
        setEndOfSemestrBtn.setText("Дата окончания");


        processSchedule = new Button(groupClassRoomsChek, SWT.PUSH);
        processSchedule.setText("Сформировать!");
        processSchedule.setEnabled(false);


        layout();
        listeners();
    }

    private void layout() {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        _mainComposite.setLayout(new GridLayout());
        _mainComposite.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        folder.setLayoutData(layoutData);

        GridLayout composite2GL = new GridLayout();
        composite2GL.numColumns = 4;
        composite2GL.makeColumnsEqualWidth = true;
        composite2GL.marginHeight = composite2GL.marginWidth = 5;
        composite2GL.verticalSpacing = composite2GL.horizontalSpacing = 5;
        _composite.setLayout(composite2GL);

        GridData groupSchCheking1GD = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        groupSchCheking1.setLayoutData(groupSchCheking1GD);
        FillLayout groupSchCheking1FL = new FillLayout(SWT.HORIZONTAL);
        groupSchCheking1FL.marginHeight = groupSchCheking1FL.marginWidth = 3;
        groupSchCheking1FL.spacing = 10;
        groupSchCheking1.setLayout(groupSchCheking1FL);

        GridData groupSchCheking2GD = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        groupSchCheking2GD.horizontalSpan = 3;
        groupSchCheking2.setLayoutData(groupSchCheking2GD);
        FillLayout groupSchCheking2FL = new FillLayout(SWT.HORIZONTAL);
        groupSchCheking2FL.marginHeight = groupSchCheking2FL.marginWidth = 3;
        groupSchCheking2FL.spacing = 10;
        groupSchCheking2.setLayout(groupSchCheking2FL);

        GridData groupClassRoomsChekGD = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        groupClassRoomsChekGD.horizontalSpan = 4;
        groupClassRoomsChek.setLayoutData(groupClassRoomsChekGD);
        groupClassRoomsChek.setLayout(new GridLayout(6, true));

        GridData startOfSemestrLabelGD = new GridData(GridData.FILL_HORIZONTAL);
        startOfSemestrLabel.setLayoutData(startOfSemestrLabelGD);

        setStartOfSemestrBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        GridData endOfSemestrLabelGD = new GridData(GridData.FILL_HORIZONTAL);
        endOfSemestrLabel.setLayoutData(endOfSemestrLabelGD);

        setEndOfSemestrBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        processSchedule.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    private void listeners() {
        /**
         * Назначение обработок кнопкам
         */
        schedulleChBTN.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent arg0) {
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
                status.setFont(new Font(_mainComposite.getDisplay(), new FontData("Courier", 10, SWT.BOLD)));
                status.append("Строка: Группа: Предмет:" + str + "Форма:" + "\n");
                status.setFont(new Font(_mainComposite.getDisplay(), new FontData("Courier", 10, SWT.NORMAL)));
                for (int i = 0; i < GlobalStorage.matrix.length; i++) {
                    status.append(i == 0 ? "" : "\n");
                    for (str = ""; (GlobalStorage.matrix[i][2].length() / 8 + str.length()) != (maxLength / 8 + 1); str += "\t") {
                    }
                    status.append(GlobalStorage.matrix[i][0] + "\t" + GlobalStorage.matrix[i][1] + "\t" + GlobalStorage.matrix[i][2] + str + GlobalStorage.matrix[i][3]);
                }
                status.append("\nНайдено записей: " + GlobalStorage.matrix.length);
            }
        });

        searchingInLoadBTN.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                int suc = 0;
                int season = 0; // autumn as default
                if (radioB1.getSelection() == true)
                    season = 0; // autumn
                else if (radioB2.getSelection() == true)
                    season = 1; // spring
                if (ExcelWorker.isLoadOpened()) {
                    int percent = 100;
                    if (matchingPercentageC.getSelectionIndex() == 0)
                        percent = 50;
                    else if (matchingPercentageC.getSelectionIndex() == 1)
                        percent = 75;
                    else if (matchingPercentageC.getSelectionIndex() == 2)
                        percent = 100;
                    GlobalStorage.matrix = ExcelWorker.openGeneralLoad(GlobalStorage.matrix, season, percent);
                    if (GlobalStorage.matrix == null)
                        return;
                    status.setText("");
                    int maxLength = 0;
                    for (int i = 0; i < GlobalStorage.matrix.length; i++)
                        if (GlobalStorage.matrix[i][2].length() > maxLength)
                            maxLength = GlobalStorage.matrix[i][2].length();

                    String str;
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
        });

        setStartOfSemestrBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                final Shell dialog = new Shell(setStartOfSemestrBtn.getDisplay(), SWT.DIALOG_TRIM);
                dialog.setText("Начало семестра:");
                dialog.setLayout(new GridLayout(2, false));
                final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
                Button ok = new Button(dialog, SWT.PUSH);
                ok.setText("OK");
                ok.setLayoutData(new GridData(GridData.FILL_BOTH));
                ok.addSelectionListener(new SelectionAdapter() {
                    @SuppressWarnings("deprecation")
                    public void widgetSelected(SelectionEvent e) {
                        GlobalStorage.beginingOfSemestr = new GregorianCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay()).getTime();
                        startOfSemestrLabel.setText(GlobalStorage.beginingOfSemestr.getDate() + "/" + (GlobalStorage.beginingOfSemestr.getMonth() + 1) + "/" + (1900 + GlobalStorage.beginingOfSemestr.getYear()));
                        if (GlobalStorage.beginingOfSemestr != null && GlobalStorage.endOfSemestr != null && (GlobalStorage.beginingOfSemestr.before(GlobalStorage.endOfSemestr) || GlobalStorage.beginingOfSemestr.equals(GlobalStorage.endOfSemestr))) {
                            processSchedule.setEnabled(true);
                        } else
                            processSchedule.setEnabled(false);
                        dialog.close();
                    }
                });
                dialog.setDefaultButton(ok);
                dialog.pack();
                dialog.open();
            }
        });

        setEndOfSemestrBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                final Shell dialog = new Shell(setEndOfSemestrBtn.getDisplay(), SWT.DIALOG_TRIM);
                dialog.setText("Конец семестра:");
                dialog.setLayout(new GridLayout(2, false));
                final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
                Button ok = new Button(dialog, SWT.PUSH);
                ok.setText("OK");
                ok.setLayoutData(new GridData(GridData.FILL_BOTH));
                ok.addSelectionListener(new SelectionAdapter() {
                    @SuppressWarnings("deprecation")
                    public void widgetSelected(SelectionEvent e) {
                        GlobalStorage.endOfSemestr = new GregorianCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay()).getTime();
                        endOfSemestrLabel.setText(GlobalStorage.endOfSemestr.getDate() + "/" + (GlobalStorage.endOfSemestr.getMonth() + 1) + "/" + (1900 + GlobalStorage.endOfSemestr.getYear()));
                        if (GlobalStorage.beginingOfSemestr != null && GlobalStorage.endOfSemestr != null && (GlobalStorage.beginingOfSemestr.before(GlobalStorage.endOfSemestr) || GlobalStorage.beginingOfSemestr.equals(GlobalStorage.endOfSemestr))) {
                            processSchedule.setEnabled(true);
                        } else
                            processSchedule.setEnabled(false);
                        dialog.close();
                    }
                });
                dialog.setDefaultButton(ok);
                dialog.pack();
                dialog.open();
            }
        });

        processSchedule.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent arg0) {
                SemestrBuilder SB = new SemestrBuilder(GlobalStorage.beginingOfSemestr, GlobalStorage.endOfSemestr);
                try {
                    ExcelWorker.GenerateSchedule(SB);
                } catch (SheduleIsNotOpenedException e) {
                    status.setText("Расписание не открыто! Обработка отменена...");
                }
            }
        });

    }

    public void setFocus() {
        _mainComposite.getParent().setFocus();
    }
    
    public void dispose() {
        _mainComposite.dispose();
    }
}
