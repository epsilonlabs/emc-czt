package org.eclipse.epsilon.emc.czt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sourceforge.czt.print.util.LatexString;
import net.sourceforge.czt.print.util.XmlString;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class CZTModel extends EmfModel {
	
	protected String modelFile;
	
	public CZTModel() {
		super();
	}
	
	public void load(StringProperties properties, String basePath)
			throws EolModelLoadingException {
		this.name = properties.getProperty("name");
		String[] aliases = properties.getProperty("aliases").split(",");
		for (int i = 0; i < aliases.length; i++) {
			this.aliases.add(aliases[i].trim());
		}

		this.modelFile = StringUtil.toString(basePath)
				+ properties.getProperty(PROPERTY_MODEL_FILE);
		//this.metamodelUri = properties.getProperty("metamodelUri");
		//this.modelFileUri = URI.createPlatformResourceURI(properties
				.getProperty(PROPERTY_MODEL_FILE) + ".xml", true);
		this.isMetamodelFileBased = false;
		
		load();
	}

	public void load() throws EolModelLoadingException {
		//ResourceSet resourceSet = new ResourceSetImpl();
		ParseUtils parse = new ParseUtils();
		FileOutputStream tempStream = null;
		XmlString xml = null;
		
		try {
			File tempFile = new File(modelFile + ".xml"); //File.createTempFile("temp", "xml");
			xml = parse.toXML(this.modelFile.toString());
			tempStream = new FileOutputStream(tempFile);
			Writer writer = new OutputStreamWriter(tempStream, "UTF-8");
			writer.write(xml.toString());
			writer.close();
			super.load();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	public boolean store(String fileName) {
		super.store(fileName + ".xml");
		ParseUtils parse = new ParseUtils();
		LatexString latex;
		
		try {
			latex = parse.toLatex(fileName + ".xml");			
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
		
		try {
			//FileOutputStream stream = new FileOutputStream(modifiedName + ".tex");
			FileOutputStream stream = new FileOutputStream(fileName);
			Writer writer = new OutputStreamWriter(stream);
			writer.write(latex.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
