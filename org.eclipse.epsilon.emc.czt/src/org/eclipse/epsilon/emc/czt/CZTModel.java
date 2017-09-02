package org.eclipse.epsilon.emc.czt;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.EolModule;

public class CZTModel extends EmfModel {
	
	protected CZTResourceFactory resourceFactory = new CZTResourceFactory();
	
	
	public static void main(String[] args) throws Exception {
		
		EmfUtil.register(URI.createFileURI("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/z.ecore"), EPackage.Registry.INSTANCE);		
		
		CZTModel model = new CZTModel();
		model.setName("M");
		model.setModelFile("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/birthdaybook.tex");
		model.setMetamodelUri("http://czt.sourceforge.net/zml");
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(true);
		model.load();
		
		EolModule module = new EolModule();
		module.parse("ZName.all.first().word = 'BARB';");
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		
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
	
}
