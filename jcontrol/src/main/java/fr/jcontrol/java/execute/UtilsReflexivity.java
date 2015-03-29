package fr.jcontrol.java.execute;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsReflexivity {

	private final static Logger LOG = LoggerFactory
			.getLogger(UtilsReflexivity.class);

	private UtilsReflexivity() {
		throw new AssertionError("Don't instantiated this class");
	}

	public static void showMethods(Class c) {
		Method[] m = c.getMethods();

		LOG.debug("there are " + m.length + " methods in this class");
		for (int i = 0; i < m.length; i++) {
			LOG.debug("" + m[i]);

			Class[] p = m[i].getParameterTypes();
			for (int j = 0; j < p.length; j++)
				LOG.debug(p[j].getName());

			LOG.debug("----------------------------------------\n");
		}
	}

	public static void showFields(Class c) {
		Field[] m = c.getFields();

		LOG.debug("there are " + m.length + " fields in this class");
		for (int i = 0; i < m.length; i++)
			LOG.debug(m[i].getName());

	}

	public static void showConstructors(Class c) {
		Constructor[] construc = c.getConstructors();
		LOG.debug("there are " + construc.length
				+ " constructors in this class");
		for (int i = 0; i < construc.length; i++) {
			LOG.debug(construc[i].getName());

			Class[] param = construc[i].getParameterTypes();

			for (int j = 0; j < param.length; j++) {
				LOG.debug("" + param[j]);
			}
		}
	}

	public static Object getInstance(Class c) {
		try {
			return c.newInstance();
		} catch (InstantiationException e) {
			LOG.error("getInstance", e);
		} catch (IllegalAccessException e) {
			LOG.error("getInstance", e);
		}
		return null;
	}

	public static Object getInstance(Class c, Class[] types, Object[] values) {
		Constructor ct;
		try {
			ct = c.getConstructor(types);
			return ct.newInstance(values);
		} catch (NoSuchMethodException | SecurityException e) {
			LOG.error("getInstance", e);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			LOG.error("getInstance", e);
		}
		return null;
	}

	public static <T> Collection<T> extract(Collection<?> coll, Class<T> type) {
		Collection<T> result = new ArrayList<T>();
		for (Object obj : coll) {
			if (type.isInstance(obj)) {
				result.add((T) obj);
			}
		}
		return result;
	}

	public static boolean instanceofMy(Object obj, Class type) {
		if (type.isInstance(obj)) {
			return true;
		}
		return false;
	}
}
