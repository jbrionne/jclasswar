package fr.jclasswar;

public class AppEnvironmentRandom {

	private AppEnvironmentRandom() {
		throw new AssertionError("Don't instantiated this class");
	}
	
	public static void main(String[] args) throws Exception {
		EnvironmentRandom e = new EnvironmentRandom();
		e.run();
	}

}
