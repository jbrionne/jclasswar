package fr.jcontrol.java.javaparser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassReaderParser {

	private final static Logger LOG = LoggerFactory
			.getLogger(ClassReaderParser.class);

	public static CompilationUnit parse(String path) {
		// creates an input stream for the file to be parsed
		CompilationUnit cu = null;
		try (FileInputStream in = new FileInputStream(path);) {
			// parse the file
			cu = JavaParser.parse(in);
		} catch (ParseException | IOException e) {
			LOG.error("ClassReaderParser", e);
		}
		return cu;
	}

}
