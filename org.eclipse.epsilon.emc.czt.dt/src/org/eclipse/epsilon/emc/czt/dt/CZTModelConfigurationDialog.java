package org.eclipse.epsilon.emc.czt.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.emc.czt.CZTModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CZTModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	protected String getModelName() {
		return "Z Model";
	}

	protected String getModelType() {
		return "ZModel";
	}
	
	protected Label fileTextLabel;
	protected Text fileText;
	protected Button browseModelFile;
	
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
	}
	
	protected Composite createFilesGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Files/URIs", 3);
		
		fileTextLabel = new Label(groupContent, SWT.NONE);
		fileTextLabel.setText("File: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "Z specifications in the workspace", "Select a Z specification"));
		
		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}
	
	protected void loadProperties(){
		super.loadProperties();
		if (properties == null) return;
		fileText.setText(properties.getProperty(CZTModel.PROPERTY_MODEL_FILE));
	}
	
	
	protected void storeProperties(){
		super.storeProperties();
		properties.put(CZTModel.PROPERTY_MODEL_FILE, fileText.getText());
	}

}
