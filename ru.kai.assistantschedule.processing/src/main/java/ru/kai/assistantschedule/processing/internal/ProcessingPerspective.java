package ru.kai.assistantschedule.processing.internal;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ProcessingPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
//		layout.setFixed(true);
		layout.createPlaceholderFolder("bottomFolder", IPageLayout.BOTTOM, 0.8f, layout.getEditorArea());

		layout.createPlaceholderFolder("rightFolder", IPageLayout.RIGHT, 0.15f, layout.getEditorArea());

		layout.createPlaceholderFolder("leftFolder", IPageLayout.LEFT, 0.85f, layout.getEditorArea());
        }

}
