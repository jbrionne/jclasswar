package fr.jcontrol.java.javaparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteReader {

	private static final Logger LOG = LoggerFactory
			.getLogger(WriteReader.class);

	private WriteReader() {
		throw new AssertionError("Don't instantiated this class");
	}

	public static void read(String fileNamePath) {
		InputStreamReader flog = null;
		LineNumberReader llog = null;
		String myLine = null;
		try {
			flog = new InputStreamReader(new FileInputStream(fileNamePath));
			llog = new LineNumberReader(flog);
			while ((myLine = llog.readLine()) != null) {
				LOG.debug(myLine);
			}
		} catch (Exception e) {
			LOG.error("read", e);
		}
	}

	public static void writeFile(String name, String sourceCode) {

		try (FileWriter fstream = new FileWriter(name);
				BufferedWriter out = new BufferedWriter(fstream);) {
			out.write(sourceCode);
			LOG.error("writeFile " + name);
		} catch (Exception e) {
			LOG.error("writeFile", e);
		}
	}

	public static void makeDir(String cibleJava) {
		String[] b = cibleJava.split("/");
		StringBuilder strB = new StringBuilder();
		subMakeDir(b, strB, b.length);
	}

	public static void subMakeDir(String[] b, StringBuilder strB, int max) {
		for (int i = 0; i < max; i++) {
			if (strB.length() != 0) {
				strB.append("/");
			}
			strB.append(b[i]);
			LOG.debug("mkdir {}", strB.toString());
			new File(strB.toString()).mkdir();
		}
	}
}
