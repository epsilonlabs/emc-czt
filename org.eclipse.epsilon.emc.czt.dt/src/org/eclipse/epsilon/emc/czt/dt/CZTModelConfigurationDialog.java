package org.eclipse.epsilon.emc.czt.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class CZTModelConfigurationDialog extends AbstractModelConfigurationDialog {

	protected Button browseModelFile;
	protected Text modelFileText;
	protected Label modelFileLabel;

	public CZTModelConfigurationDialog() {
		super();
	}

	protected void setShellStyle(int newShellStyle) {
		super.setShellStyle(newShellStyle | SWT.RESIZE);
	}

	protected Control createDialogArea(Composite parent) {

		Composite superControl = (Composite) super.createDialogArea(parent);

		this.setTitle("Configure Z model");
		this.setMessage("Configure the details of the model");
		this.getShell().setText("Configure Z model");
		// this.getShell().setImage(EpsilonCommonsPlugin.createImage("icons/emf.gif"));

		Composite control = new Composite(superControl, SWT.FILL);
		control.setLayout(new GridLayout(1, true));
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		control.layout();
		control.pack();

		loadProperties();
		
		return control;
	}

	protected Composite createFilesGroup(Composite parent) {
		Group group = new Group(parent, SWT.FILL);

		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setText("Files");
		group.setLayout(new GridLayout(1, false));

		Composite groupContent = new Composite(group, SWT.FILL);
		groupContent.setLayout(new GridLayout(3, false));
		groupContent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		modelFileLabel = new Label(groupContent, SWT.NONE);
		modelFileLabel.setText("Model file: ");

		modelFileText = new Text(groupContent, SWT.BORDER);
		modelFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new
		BrowseWorkspaceForModelsListener(modelFileText));
		
		Label emptyLabel = new Label(groupContent, SWT.NONE);
		emptyLabel.setText("");

		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}

	class BrowseWorkspaceForModelsListener implements Listener {

		private Text text = null;

		public BrowseWorkspaceForModelsListener(Text text) {
			this.text = text;
		}

		public void handleEvent(Event event) {
			String file = BrowseWorkspaceUtil.browseFilePath(getShell(),
					"Z models in the workspace", "Select a Z model", "",
					null);
			if (file != null) {
				text.setText(file);
			}
		}
	}

	class BrowseWorkspaceForMetaModelsListener implements Listener {

		private Text text = null;

		public BrowseWorkspaceForMetaModelsListener(Text text) {
			this.text = text;
		}

		public void handleEvent(Event event) {
			String file = BrowseWorkspaceUtil.browseFilePath(getShell(),
					"Z meta-models in the workspace",
					"Select a Z meta-model (ECore)", "ecore", null);
			if (file != null) {
				text.setText(file);
			}
		}
	}

	protected String getModelType() {
		return "ZModel";
	}

	protected void loadProperties() {
		super.loadProperties();
		if (properties == null)
			return;
		
		modelFileText.setText(properties.getProperty("modelFile"));
	}

	protected void storeProperties() {
		super.storeProperties();
		properties.put("modelFile", modelFileText.getText());
		properties.put("metamodelUri","http://czt.sourceforge.net/zml");
	}

	@Override
	protected void createGroups(Composite control) {
		createNameAliasGroup(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
	}

	@Override
	protected String getModelName() {
		return "Z specification";
	}

}
