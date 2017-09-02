package org.eclipse.epsilon.emc.czt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;

import net.sourceforge.czt.print.util.LatexString;
import net.sourceforge.czt.print.util.XmlString;
import net.sourceforge.czt.session.CommandException;
import net.sourceforge.czt.session.Dialect;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.Markup;
import net.sourceforge.czt.session.SectionManager;
import net.sourceforge.czt.session.Source;
import net.sourceforge.czt.session.StringSource;

public class CZTResource extends XMLResourceImpl {
	
	public static void main(String[] args) throws Exception {
		
		EmfUtil.register(URI.createFileURI("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/z.ecore"), EPackage.Registry.INSTANCE);		
		ResourceSet resourceSet = new ResourceSetImpl();
		//resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new GenericXMLResourceFactoryImpl());
		//Resource resource = resourceSet.createResource(URI.createFileURI("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/birthdaybook.xml"));
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("zed", new CZTResourceFactory());
		Resource resource = resourceSet.createResource(URI.createFileURI("/Users/dkolovos/git/emc-czt/org.eclipse.epsilon.emc.czt/samples/birthdaybook.zed"));
		resource.load(null);
		InMemoryEmfModel m = new InMemoryEmfModel(resource);
		System.out.println(m.getAllOfKind("ZSect"));
		
	}
	
	public CZTResource(URI uri) {
		super(uri);
	    setEncoding("UTF-8");

	    getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
	    getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

	    XMLOptions xmlOptions = new XMLOptionsImpl();
	    xmlOptions.setProcessSchemaLocations(false);
	    
	    getDefaultLoadOptions().put(XMLResource.OPTION_XML_OPTIONS, xmlOptions);
	}
	
	@Override
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		
		SectionManager manager = new SectionManager(Dialect.Z);
		Source source = new StringSource(new Scanner(inputStream).useDelimiter("\\A").next());
		//source.setName(UUID.randomUUID().toString());
		manager.put(new Key(source.getName(), Source.class), source);
		
		try {
			XmlString xmlString = (XmlString) manager.get(new Key(source.getName(), XmlString.class));
			super.doLoad(new ByteArrayInputStream(xmlString.toString().getBytes()), options);
		} catch (CommandException e) {
			throw new IOException(e);
		}
		
	}
	
	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		
		if (1>0) throw new UnsupportedOperationException();
		
		StringOutputStream stringOutputStream = new StringOutputStream();
		super.doSave(stringOutputStream, options);
		SectionManager manager = new SectionManager(Dialect.Z);
		Source source = new StringSource(stringOutputStream.toString());
		source.setMarkup(Markup.ZML);
		//source.setName(UUID.randomUUID().toString());
		manager.put(new Key(source.getName(), Source.class), source);
		
		try {
			LatexString latexString = (LatexString) manager.get(new Key(source.getName(), LatexString.class));
			outputStream.write(latexString.toString().getBytes());
		} catch (CommandException e) {
			throw new IOException(e);
		}
 
	}
	
}
