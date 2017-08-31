package org.eclipse.epsilon.emc.czt;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sourceforge.czt.print.util.LatexString;
import net.sourceforge.czt.print.util.UnicodeString;
import net.sourceforge.czt.print.util.XmlString;
import net.sourceforge.czt.session.FileSource;
import net.sourceforge.czt.session.Key;
import net.sourceforge.czt.session.SectionManager;

public class ParseUtils {

	private SectionManager manager;
	private FileSource source;

	public ParseUtils() {
		manager = new SectionManager("z");

	}

	// Parse latex, utf8, utf16 to XML
	public XmlString toXML(String fileName) throws Throwable {
		if (fileName.endsWith("tex") || fileName.endsWith("utf8")
				|| fileName.endsWith("utf16")) {
			source = new FileSource(fileName);

			XmlString xml = (XmlString) manager.get(new Key(source.getName(),
					XmlString.class));

			return xml;
		} else {
			System.err.println("Unsupport file format " + fileName);
			return null;
		}
	}

	// parse XML to latex
	public LatexString toLatex(String fileName) throws Throwable {
		source = new FileSource(fileName);

		LatexString latex = (LatexString) manager.get(new Key(source.getName(),
				LatexString.class));

		return latex;
	}

	// parse XML to utf
	public UnicodeString toUTF(String fileName) throws Throwable {
		source = new FileSource(fileName);

		UnicodeString unicode = (UnicodeString) manager.get(new Key(source
				.getName(), UnicodeString.class));

		return unicode;

	}

	public static void main(String[] args) throws Throwable {
		// parsing xml to latex
		ParseUtils parse = new ParseUtils();

		// LatexString latex = parse.toLatex("birthdaybook.xml");
		//
		// FileOutputStream stream = new FileOutputStream("birthdaybook.tex");
		// Writer writer = new OutputStreamWriter(stream);
		// writer.write(latex.toString());
		// writer.close();

		// parsing latex to xml
		XmlString xml = parse.toXML("birthdaybook.tex");

		FileOutputStream stream = new FileOutputStream("birthdaybook.xml");
		Writer writer = new OutputStreamWriter(stream, "UTF-8");
		writer.write(xml.toString());
		writer.close();

		// parsing latex to utf
		// UnicodeString unicode = parse.toUTF("birthdaybook.tex");
		//		
		// FileOutputStream stream = new FileOutputStream("birthdaybook.utf8");
		// //use "UTF-8" parse to utf8, use "UTF-16" parse to utf16
		// Writer writer = new OutputStreamWriter(stream, "UTF-8");
		// writer.write(unicode.toString());
		// writer.close();

		System.out.println("Done");

	}
}
