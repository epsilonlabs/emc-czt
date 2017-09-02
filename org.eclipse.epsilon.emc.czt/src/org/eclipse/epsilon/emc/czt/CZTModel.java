package org.eclipse.epsilon.emc.czt;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class CZTModel extends EmfModel {
	
	protected CZTResourceFactory resourceFactory = new CZTResourceFactory();
	
	
	public static void main(String[] args) throws Exception {
		
		EmfUtil.register(URI.createFileURI("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/z.ecore"), EPackage.Registry.INSTANCE);		
		
		CZTModel model = new CZTModel();
		model.setName("M");
		model.setModelFile("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/samples/birthdaybook.zed");
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(false);
		model.load();
		
		EolModule module = new EolModule();
		module.parse("ZName.all.selectOne(n|n.word='FindBirthday').word.println();");
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		module.getContext().getModelRepository().dispose();
	}
	
	@Override
	protected ResourceSet createResourceSet() {
		return new CachedResourceSet() {
			@Override
			public Resource createNewResource(URI uri, String contentType) {
				return resourceFactory.createResource(uri);
			}
		};
	}
	
	@Override
	protected void loadModel() throws EolModelLoadingException {
		setMetamodelUri("http://czt.sourceforge.net/zml");
		super.loadModel();
	}
	
}
