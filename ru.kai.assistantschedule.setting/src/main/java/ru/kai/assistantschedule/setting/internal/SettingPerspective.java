package ru.kai.assistantschedule.setting.internal;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class SettingPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
//		layout.setFixed(true);
		layout.createPlaceholderFolder("mainFolder", IPageLayout.TOP, 0.8f, layout.getEditorArea());
		layout.createPlaceholderFolder("bottomFolder", IPageLayout.TOP, 0.2f, layout.getEditorArea());
	}
	
}
