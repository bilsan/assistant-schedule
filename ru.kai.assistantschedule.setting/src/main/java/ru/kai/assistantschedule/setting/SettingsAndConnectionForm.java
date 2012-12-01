package ru.kai.assistantschedule.setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
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
@Deprecated
public class SettingsAndConnectionForm {
    
    protected static final Logger LOG = LoggerFactory.getLogger(SettingsAndConnectionForm.class);
    
    /**
     * Главный композит
     */
    private Composite _mainComposite;

    private Composite _leftComposite;

    private Composite _rightComposite;

    /**
     * Левая передвижная перегородка
     */
    private Sash _sash;
    
    private CTabFolder folder;
    private CTabItem tabItem;
    private Composite _composite;
    private Button loadSchedulleBtn, clearSchedulleBtn, loadLoadBtn, clearLoadBtn, AutoFillSettingsBtn;
    private Text schedullePathText, loadPathText, descForAutoFillSettingsBtn;

    //Получаем экземпляр консоли, для вывода в него вспомогательной информации
    private IStatus status = StatusImpl.getInstance();
    
    public SettingsAndConnectionForm(Composite parent) {
    	parent.setLayout(new FormLayout());
    	Color color = new Color(Display.getCurrent(), 200, 0, 200);
    	parent.setBackground(color);
    	
        _mainComposite = new Composite(parent, SWT.NONE);
        _mainComposite.setBackground(color);
        _leftComposite = new Composite(_mainComposite, SWT.NONE);
        _sash = new Sash(_mainComposite, SWT.VERTICAL);
        _rightComposite = new Composite(_mainComposite, SWT.NONE);

        color = new Color(Display.getCurrent(), 0, 120, 100);
        _rightComposite.setBackground(color);
        
        ActivityTree downloadTree = new ActivityTree(_leftComposite);
        
//        folder = new CTabFolder(_mainComposite, SWT.NONE);
//		folder.setSimple(false);
//		folder.setBorderVisible(true);
//		folder.setSingle(false);
//		folder.setUnselectedImageVisible(true);
//
//        tabItem = new CTabItem(folder, SWT.NONE);
//		tabItem.setText("Настройка/подключение");
//
//        _composite = new Composite(folder, SWT.NONE);
//        tabItem.setControl(_composite);
//        folder.setSelection(tabItem);

        /**
         * Кнопка загрузки расписания
         */
//        loadSchedulleBtn = new Button(_composite, SWT.PUSH);
//        loadSchedulleBtn.setText("Загрузить расписание");

        /**
         * Путь до рассписания
         */
//        schedullePathText = new Text(_composite, SWT.READ_ONLY | SWT.LEFT | SWT.BORDER);

        /**
         * Кнопка очистки расписания
         */
//        clearSchedulleBtn = new Button(_composite, SWT.PUSH);
//        clearSchedulleBtn.setText("X");


        /**
         * Кнопка загрузки нагрузки
         */
//        loadLoadBtn = new Button(_composite, SWT.PUSH);
//        loadLoadBtn.setText("Загрузить нагрузку");

        /**
         * Путь до нагрузки
         */
//        loadPathText = new Text(_composite, SWT.READ_ONLY | SWT.LEFT | SWT.BORDER);

        /**
         * Кнопка очистки нагрузки
         */
//        clearLoadBtn = new Button(_composite, SWT.PUSH);
//        clearLoadBtn.setText("X");


        /**
         * Описание следующей кнопки для пользователя
         */
//        descForAutoFillSettingsBtn = new Text(_composite, SWT.READ_ONLY | SWT.RIGHT);
//        descForAutoFillSettingsBtn.setText("Настройки Автозаполнения аудиторий где указано каф ПМИ");

        /**
         * Кнопка настройки аудиторий для автозаполнения расписания, где будет
         * написано КАФ или ВЦ ПМИ
         */
//        AutoFillSettingsBtn = new Button(_composite, SWT.PUSH);
//        AutoFillSettingsBtn.setText("Открыть");


        layout();
        listeners();
    }

    private void layout() {
    	FormData formData;

        formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.bottom = new FormAttachment(100, 0);
        _mainComposite.setLayout(new FormLayout());
        _mainComposite.setLayoutData(formData);

        //Передвижная перегородка
        formData = new FormData();
        formData.left = new FormAttachment(0, 250);
        formData.top = new FormAttachment(0, 0);
        //formData.right = new FormAttachment(100, 0);
        formData.bottom = new FormAttachment(100, 0);
        _sash.setLayoutData(formData);

        //Атрибуты
        formData = new FormData();
        formData.left = new FormAttachment(0, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(_sash, 0);
        formData.bottom = new FormAttachment(100, 0);
        _leftComposite.setLayout(new FormLayout());
        _leftComposite.setLayoutData(formData);

        //Действия с атрибутами
        formData = new FormData();
        formData.left = new FormAttachment(_sash, 0);
        formData.top = new FormAttachment(0, 0);
        formData.right = new FormAttachment(100, 0);
        formData.bottom = new FormAttachment(100, 0);
        _rightComposite.setLayout(new FormLayout());
        _rightComposite.setLayoutData(formData);

    	/*
    	
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
        */
    }
    
    

    

    private void listeners() {
//        loadSchedulleBtn.addSelectionListener(new OpenSchedule());
//		loadLoadBtn.addSelectionListener(new OpenLoadOfProffs());
//		clearSchedulleBtn.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent arg0) {
//				schedullePathText.setText(GlobalStorage.selectedSchedule = "");
//				GlobalStorage.put("selectedSchedule", "");
//				GlobalStorage.matrix = null;
//			}
//		});
		
		_sash.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
                if (event.detail != SWT.DRAG) {
                    ((FormData) _sash.getLayoutData()).left = new FormAttachment(0, event.x);
                    _sash.getParent().layout();
                }
            }
        }
        );
    }

    public void setFocus() {
        _mainComposite.getParent().setFocus();
    }
    
    public void dispose() {
        _mainComposite.dispose();
    }

}
