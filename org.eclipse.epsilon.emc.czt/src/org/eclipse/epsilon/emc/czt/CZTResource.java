package org.eclipse.epsilon.emc.czt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
	
	protected String encoding = "UTF-8";
	
	public static void main(String[] args) throws Exception {
		
		EmfUtil.register(URI.createFileURI("z.ecore"), EPackage.Registry.INSTANCE);		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("zed", new CZTResourceFactory());
		Resource resource = resourceSet.createResource(URI.createFileURI("samples/birthdaybook.zed"));
		resource.load(null);
		resource.save(null);
	}
	
	public CZTResource(URI uri) {
		super(uri);
	    setEncoding(encoding);

	    getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
	    getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
	    
	    XMLOptions xmlOptions = new XMLOptionsImpl();
	    xmlOptions.setProcessSchemaLocations(false);
	    
	    getDefaultLoadOptions().put(XMLResource.OPTION_XML_OPTIONS, xmlOptions);
	}
	
	@Override
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		
		SectionManager manager = new SectionManager(Dialect.Z);
		Scanner scanner = new Scanner(inputStream);
		scanner.useDelimiter("\\A");
		Source source = new StringSource(scanner.next());
		scanner.close();
		manager.put(new Key<Source>(source.getName(), Source.class), source);
		
		try {
			XmlString xmlString = (XmlString) manager.get(new Key<XmlString>(source.getName(), XmlString.class));
			super.doLoad(new ByteArrayInputStream(xmlString.toString().getBytes()), options);
		} catch (CommandException e) {
			throw new IOException(e);
		}
		
	}
	
	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		super.doSave(bos, options);
		
		SectionManager manager = new SectionManager(Dialect.Z);
		Source source = new StringSource(new String(bos.toByteArray(), encoding));
		source.setMarkup(Markup.ZML);
		manager.put(new Key<Source>(source.getName(), Source.class), source);
		
		try {
			LatexString latexString = (LatexString) manager.get(new Key<LatexString>(source.getName(), LatexString.class));
			outputStream.write(latexString.toString().getBytes());
		} catch (CommandException e) {
			throw new IOException(e);
		}
 
	}
	
}
