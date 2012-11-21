package ru.kai.assistantschedule.app;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setShowProgressIndicator(true);
        configurer.setShowStatusLine(true);
        configurer.setShowCoolBar(true);
        configurer.setShowPerspectiveBar(false);
        configurer.setShowFastViewBars(false);
        configurer.setShowMenuBar(false);
        configurer.setInitialSize(new Point(1200, 700));
        configurer.setTitle("Помощник Расписания. Работает с таблицами Excel 97-2003."); //$NON-NLS-1$
    }

    @Override
    public void postWindowCreate() {
        Shell shell = getWindowConfigurer().getWindow().getShell();
        shell.setLocation(0, 0); // При открытии верхний правый угол окна расположен в точке экрана с коор-ми (0, 0)
        super.postWindowCreate();
    }
    
}
