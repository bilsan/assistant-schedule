package ru.kai.assistantschedule.setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kai.assistantschedule.core.ExcelWorker;
import ru.kai.assistantschedule.core.GlobalStorage;
import ru.kai.assistantschedule.status.open.IStatus;
import ru.kai.assistantschedule.status.open.StatusImpl;

/**
 * User: Роман
 * Date: 31.10.12
 * Time: 14:23
 */
public class SettingsAndConnectionForm {
    
    protected static final Logger LOG = LoggerFactory.getLogger(SettingsAndConnectionForm.class);
    
    private CTabFolder folder;
    private CTabItem tabItem;
    private Composite _mainComposite, _composite;
    private Button loadSchedulleBtn, clearSchedulleBtn, loadLoadBtn, clearLoadBtn, AutoFillSettingsBtn;
    private Text schedullePathText, loadPathText, descForAutoFillSettingsBtn;

    private IStatus status = StatusImpl.getInstance();
    
    public SettingsAndConnectionForm(Composite parent) {
        _mainComposite = new Composite(parent, SWT.NONE);

        folder = new CTabFolder(_mainComposite, SWT.NONE);
		folder.setSimple(false);
		folder.setBorderVisible(true);
		folder.setSingle(false);
		folder.setUnselectedImageVisible(true);

        tabItem = new CTabItem(folder, SWT.NONE);
		tabItem.setText("Настройка/подключение");

        _composite = new Composite(folder, SWT.NONE);
        tabItem.setControl(_composite);
        folder.setSelection(tabItem);

        /**
         * Кнопка загрузки расписания
         */
        loadSchedulleBtn = new Button(_composite, SWT.PUSH);
        loadSchedulleBtn.setText("Загрузить расписание");

        /**
         * Путь до рассписания
         */
        schedullePathText = new Text(_composite, SWT.READ_ONLY | SWT.LEFT | SWT.BORDER);

        /**
         * Кнопка очистки расписания
         */
        clearSchedulleBtn = new Button(_composite, SWT.PUSH);
        clearSchedulleBtn.setText("X");


        /**
         * Кнопка загрузки нагрузки
         */
        loadLoadBtn = new Button(_composite, SWT.PUSH);
        loadLoadBtn.setText("Загрузить нагрузку");

        /**
         * Путь до нагрузки
         */
        loadPathText = new Text(_composite, SWT.READ_ONLY | SWT.LEFT | SWT.BORDER);

        /**
         * Кнопка очистки нагрузки
         */
        clearLoadBtn = new Button(_composite, SWT.PUSH);
        clearLoadBtn.setText("X");


        /**
         * Описание следующей кнопки для пользователя
         */
        descForAutoFillSettingsBtn = new Text(_composite, SWT.READ_ONLY | SWT.RIGHT);
        descForAutoFillSettingsBtn.setText("Настройки Автозаполнения аудиторий где указано каф ПМИ");

        /**
         * Кнопка настройки аудиторий для автозаполнения расписания, где будет
         * написано КАФ или ВЦ ПМИ
         */
        AutoFillSettingsBtn = new Button(_composite, SWT.PUSH);
        AutoFillSettingsBtn.setText("Открыть");


        layout();
        listeners();
    }

    private void layout() {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        _mainComposite.setLayout(new GridLayout());
        _mainComposite.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        folder.setLayoutData(layoutData);

        GridLayout compositeGL = new GridLayout();
        compositeGL.numColumns = 4;
        compositeGL.marginHeight = compositeGL.marginWidth = 5;
        compositeGL.verticalSpacing = compositeGL.horizontalSpacing = 5;
        _composite.setLayout(compositeGL);

        GridData loadSchBtnGD = new GridData(GridData.FILL_BOTH);
        loadSchBtnGD.grabExcessHorizontalSpace = false;
        loadSchBtnGD.grabExcessVerticalSpace = false;
        loadSchBtnGD.widthHint = 150;
        loadSchedulleBtn.setLayoutData(loadSchBtnGD);

        GridData schedullePathTextGD = new GridData(GridData.FILL_HORIZONTAL);
        schedullePathTextGD.horizontalSpan = 2;
        schedullePathText.setLayoutData(schedullePathTextGD);

        GridData clearSchBtnGD = new GridData();
        clearSchBtnGD.grabExcessHorizontalSpace = false;
        clearSchBtnGD.grabExcessVerticalSpace = false;
        clearSchedulleBtn.setLayoutData(clearSchBtnGD);

        GridData loadLoadBtnGD = new GridData(GridData.FILL_BOTH);
        loadLoadBtnGD.grabExcessHorizontalSpace = false;
        loadLoadBtnGD.grabExcessVerticalSpace = false;
        loadLoadBtnGD.widthHint = 150;
        loadLoadBtn.setLayoutData(loadLoadBtnGD);

        GridData loadPathTextGD = new GridData(GridData.FILL_HORIZONTAL);
        loadPathTextGD.horizontalSpan = 2;
        loadPathText.setLayoutData(loadPathTextGD);

        GridData clearLoadBtnGD = new GridData(GridData.FILL_BOTH);
        clearLoadBtnGD.grabExcessHorizontalSpace = false;
        clearLoadBtnGD.grabExcessVerticalSpace = false;
        clearLoadBtn.setLayoutData(clearLoadBtnGD);

        GridData descForAutoFillSettingsBtnGD = new GridData();
        descForAutoFillSettingsBtnGD.horizontalSpan = 2;
        descForAutoFillSettingsBtn.setLayoutData(descForAutoFillSettingsBtnGD);

        GridData AutoFillSettingsBtnGD = new GridData(GridData.FILL_HORIZONTAL);
        AutoFillSettingsBtnGD.grabExcessHorizontalSpace = false;
        AutoFillSettingsBtnGD.grabExcessVerticalSpace = false;
        AutoFillSettingsBtnGD.minimumWidth = 150;
        AutoFillSettingsBtnGD.horizontalSpan = 2;
        AutoFillSettingsBtn.setLayoutData(AutoFillSettingsBtnGD);
    }
    
    /**
     * Только копируют в schedullePathText
     * 
     * @author Дамир
     */
    class OpenSchedule implements SelectionListener {
            public void widgetSelected(SelectionEvent event) {
                LOG.debug("Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, путь по умолчанию и фильтры. " +
                		"Получаем String переменную результата и выводим её.");
                LOG.info("Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, путь по умолчанию и фильтры. " +
                        "Получаем String переменную результата и выводим её.");
                    
                    try {
                        /**
                         * Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, путь
                         * по умолчанию и фильтры. Получаем String переменную результата
                         * и выводим её.
                         */
                        FileDialog fd = new FileDialog(_mainComposite.getShell(), SWT.OPEN);
                        fd.setText("Открыть расписание");
                        fd.setFilterPath("C:/");
                        String[] filterExt = {"*.xls"};
                        fd.setFilterExtensions(filterExt);
                        if ((GlobalStorage.selectedSchedule = fd.open()) != null) {
                                schedullePathText.setText(GlobalStorage.selectedSchedule);
                                GlobalStorage.put("selectedSchedule", GlobalStorage.selectedSchedule);
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

            public void widgetDefaultSelected(SelectionEvent event) {
            }
    }

    /**
     * Только копирует строку в loadPathText
     * 
     * @author Дамир
     */
    class OpenLoadOfProffs implements SelectionListener {
            public void widgetSelected(SelectionEvent event) {
                LOG.debug("OpenLoadOfProffs");
                LOG.info("OpenLoadOfProffs");
                try {
                    /**
                     * Создаем Диалог На ОТКРЫТИЕ(!) файла, прописываем title, путь
                     * по умолчанию и фильтры. Получаем String переменную результата
                     * и выводим её.
                     */
                    FileDialog fd = new FileDialog(_mainComposite.getShell(), SWT.OPEN);
                    fd.setText("Открыть нагрузку");
                    fd.setFilterPath("C:/");
                    String[] filterExt = {"*.xls"};
                    fd.setFilterExtensions(filterExt);
                    if ((GlobalStorage.selectedProffsLoad = fd.open()) != null) {
                            loadPathText.setText(GlobalStorage.selectedProffsLoad);
                            GlobalStorage.put("selectedProffsLoad", GlobalStorage.selectedProffsLoad);
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

            public void widgetDefaultSelected(SelectionEvent event) {
            }
    }

    private void listeners() {
        loadSchedulleBtn.addSelectionListener(new OpenSchedule());
		loadLoadBtn.addSelectionListener(new OpenLoadOfProffs());
		clearSchedulleBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				schedullePathText.setText(GlobalStorage.selectedSchedule = "");
				GlobalStorage.put("selectedSchedule", "");
				GlobalStorage.matrix = null;
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
