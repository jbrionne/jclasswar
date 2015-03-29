package fr.jclasswar;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {

	private static AtomicInteger idMMint = new AtomicInteger(0);

	private Address() {
		throw new AssertionError("Don't instantiated this class");
	}

	public static String getNewIdentifiant() {
		return String.valueOf(idMMint.incrementAndGet());
	}
}
