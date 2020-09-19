package fr.jclasswar;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.jcontrol.java.execute.PrepareQualif;
import fr.jcontrol.java.execute.UtilsReflexivity;
import fr.jcontrol.java.javaparser.ClassCreator;
import fr.jcontrol.java.javaparser.Parent;

public class EnvironmentRandom extends Environment {

	private static final Logger LOG = LoggerFactory
			.getLogger(EnvironmentRandom.class);

	private List<String> parentMethod = new ArrayList<String>();
	private List<Object> res = new ArrayList<Object>();

	private Parent currentParent = null;

	public EnvironmentRandom() {
		super();
	}

	public void run() {

		PrepareQualifTestCustomOnString q = new PrepareQualifTestCustomOnString();

		boolean stop = false;
		while (!stop) {
			List<Object> lst = compilationObject(q);
			List<Object> toRemove = testEvaluation(lst, q);

			if (lst.isEmpty()) {
				reload(toRemove);
			} else {
				LOG.debug("ok " + q.getNbGeneration() + " / "
						+ q.getLstQ().size());
				for (Object o : lst) {
					LOG.debug(o.getClass().getName());
				}

				if (q.getNbGeneration() < q.getLstQ().size()) {
					Class cParent = lst.get(0).getClass();
					// The winners merge
					parentMethod.add(METHOD + q.getNbGeneration());
					q.setNbGeneration(q.getNbGeneration() + 1);
					reload(toRemove);
					currentParent = new Parent(cParent.getName(),
							cParent.getSimpleName());
				} else {
					reload(toRemove);
					stop = true;
				}
			}
		}
		LOG.debug("end");
	}

	private List<Object> compilationObject(PrepareQualif q) {
		List<Object> lstInstances = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String shortName = "Entity" + Address.getNewIdentifiant();
			try {
				getC().doCompilation(
						getPkg("") + "." + shortName,
						ClassCreator.createCUAdditionDouble(getPkg(""), shortName,
								METHOD + q.getNbGeneration(), currentParent,
								parentMethod, q).toString());
				Class c1 = getC().getLoader().loadClass(getPkg("") + shortName);
				if (c1 != null) {
					Object o1 = UtilsReflexivity.getInstance(c1);
					lstInstances.add(o1);
				} else {
					LOG.error("class null " + getPkg("") + shortName);
				}
			} catch (ClassNotFoundException e) {
				LOG.error("compilationObject", e);
			}
		}
		return lstInstances;
	}

	public List<Object> testEvaluation(List<Object> lstInstances,
			PrepareQualif q) {
		return q.eval(lstInstances);
	}

	private void reload(List<Object> toRemove) {
		List<Object> toRemoveLocal = new ArrayList<Object>();
		for (Object o1 : res) {
			boolean delete = getC().deleteFileClassAndJava(o1);
			if (delete) {
				toRemoveLocal.add(o1);
			}
		}

		for (Object o1 : toRemoveLocal) {
			res.remove(o1);
		}

		for (Object o1 : toRemove) {
			boolean delete = getC().deleteFileClassAndJava(o1);
			if (!delete) {
				res.add(o1);
			}
		}
	}

}
