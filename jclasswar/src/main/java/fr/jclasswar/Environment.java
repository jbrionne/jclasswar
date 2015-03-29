package fr.jclasswar;

import fr.jcontrol.java.execute.Compiler;
import fr.jcontrol.java.javaparser.ClassCreator;
import fr.jcontrol.java.javaparser.ClassReaderParser;

public class Environment {

	private Compiler c;

	public static final String METHOD = "myInterface";
	private static final String DIRBIN = "target/bin";
	private static final String DIRJAVA = "target/java";

	private ClassReaderParser cr = new ClassReaderParser();
	private String pkg = "fr";

	public Environment() {
		c = new Compiler(DIRBIN, DIRJAVA);
		c.newClassLoader();
	}	

	private String getNameToPkg(String name) {

		StringBuilder strBPkg = new StringBuilder();
		String str = name.replaceAll("^(.*\\D)[\\d]+$", "$1");

		for (char ch : str.toCharArray()) {
			if (strBPkg.length() > 0 && !Character.isDigit(ch)) {
				strBPkg.append(".");
			}
			strBPkg.append(ch);
		}
		return strBPkg.toString();
	}

	public String getPkg(String name) {
		return pkg + "." + getNameToPkg(name);
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public Compiler getC() {
		return c;
	}

	public ClassReaderParser getCr() {
		return cr;
	}

	public void setCr(ClassReaderParser cr) {
		this.cr = cr;
	}

}
