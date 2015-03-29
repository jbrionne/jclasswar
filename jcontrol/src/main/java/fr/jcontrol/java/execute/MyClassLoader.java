package fr.jcontrol.java.execute;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClassLoader extends ClassLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(MyClassLoader.class);

	private static final String SEP = "/";

	private String cible;

	public MyClassLoader(String cible) {
		super();
		this.cible = cible;
	}

	@Override
	public Class loadClass(String className) throws ClassNotFoundException {
		Class result = findLoadedClass(className);
		if (result != null) {
			return result;
		}
		if (className.startsWith("java.")) {
			result = super.findSystemClass(className);
		}
		if (result != null) {
			return result;
		}
		try {
			result = forceloadClass(className);
		} catch (ClassFormatError e) {
			LOG.error("erreur ClassFormatError", e);
		}
		return result;
	}
	
	public Class forceloadClass(String className)
			throws ClassNotFoundException {
		Class result;
		byte[] data = null;
		try {
			data = getData(className);
		} catch (FileNotFoundException e) {
			LOG.error("forceloadClass", e);
		}
		if (data == null) {
			LOG.error(className);
			throw new ClassNotFoundException(className);
		}

		result = defineClass(className, data, 0, data.length);
		if (result == null) {
			LOG.error("not define " + className);
			throw new ClassNotFoundException();
		}
		return result;
	}

	private byte[] getData(String className) throws FileNotFoundException {
		FileInputStream file = null;
		String fileName = className.replace('.', File.separatorChar) + ".class";
		String repertoireCourant = System.getProperty("user.dir");

		file = new FileInputStream(repertoireCourant + SEP + cible + SEP
				+ fileName);

		byte[] r = null;
		try (BufferedInputStream bis = new BufferedInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			int c = bis.read();
			while (c != -1) {
				out.write(c);
				c = bis.read();
			}
			r = out.toByteArray();
		} catch (IOException e) {
			LOG.error("getData", e);
		}
		return r;
	}
}
