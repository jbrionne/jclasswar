package fr.jclasswar;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.jcontrol.java.execute.PrepareQualif;
import fr.jcontrol.java.javaparser.IExpr;
import fr.jcontrol.java.javaparser.QualifMethod;
import fr.jcontrol.java.javaparser.RandomCustom;

public class PrepareQualifTestCustomOnString implements PrepareQualif {

	private final static Logger LOG = LoggerFactory
			.getLogger(PrepareQualifTestCustomOnString.class);

	private static final double EPSILON = 0.00001;
	private int nbGeneration = 1;
	private List<QualifMethod> lstQ = new ArrayList<QualifMethod>();

	public PrepareQualifTestCustomOnString() {

		QualifMethod q1 = new QualifMethod();
		q1.setName("myInterface");
		q1.setNbGeneration(1);
		q1.getLstparameterTypes().add(String.class);
		q1.getLstparameterTypes().add(String.class);
		q1.setExpr(new Test4());
		lstQ.add(q1);

		QualifMethod q2 = new QualifMethod();
		q2.setName("myInterface");
		q2.setNbGeneration(2);
		q2.getLstparameterTypes().add(String.class);
		q2.getLstparameterTypes().add(String.class);
		q2.setExpr(new Test5());
		lstQ.add(q2);		

	}

	private class Test1 implements IExpr {
		@Override
		public boolean eval(List<Object> val, List<Object> lst) {
			String str = val.get(0).toString();
			String stra = lst.get(0).toString();
			String strb = lst.get(1).toString();
			double d = Double.valueOf(str).doubleValue();
			double a = Double.valueOf(stra).doubleValue();
			double b = Double.valueOf(strb).doubleValue();
			if (Math.abs(d - (a + b)) < EPSILON) {
				return true;
			}
			return false;
		}
	}

	private class Test2 implements IExpr {
		@Override
		public boolean eval(List<Object> val, List<Object> lst) {
			String str = val.get(0).toString();
			String stra = lst.get(0).toString();
			String strb = lst.get(1).toString();
			double d = Double.valueOf(str).doubleValue();
			double a = Double.valueOf(stra).doubleValue();
			double b = Double.valueOf(strb).doubleValue();
			if (Math.abs(d - (a - b)) < EPSILON) {
				return true;
			}
			return false;
		}
	}

	private class Test3 implements IExpr {
		@Override
		public boolean eval(List<Object> val, List<Object> lst) {
			String str = val.get(0).toString();
			String stra = lst.get(0).toString();
			String strb = lst.get(1).toString();
			double d = Double.valueOf(str).doubleValue();
			double a = Double.valueOf(stra).doubleValue();
			double b = Double.valueOf(strb).doubleValue();
			if (Math.abs(d - (a * b)) < EPSILON) {
				return true;
			}
			return false;
		}
	}

	private class Test4 implements IExpr {
		@Override
		public boolean eval(List<Object> val, List<Object> lst) {
			String str = val.get(0).toString();
			String stra = lst.get(0).toString();
			String strb = lst.get(1).toString();
			if (str.equals(stra + strb)) {
				return true;
			}
			return false;
		}
	}

	private class Test5 implements IExpr {
		@Override
		public boolean eval(List<Object> val, List<Object> lst) {
			String str = val.get(0).toString();
			String stra = lst.get(0).toString();
			String strb = lst.get(1).toString();
			if (str.equals(strb + stra)) {
				return true;
			}
			return false;
		}
	}

	@Override
	public List<Object> eval(List<Object> lstInstances) {
		List<Object> toRemove = new ArrayList<Object>();

		for (int i = 0; i < 10; i++) {
			int ele = nbGeneration - 1;
			if (!lstInstances.isEmpty()) {
				Object a = null;
				Object b = null;
				if (lstQ.get(ele).getLstparameterTypes().size() >= 1) {
					if (lstQ.get(ele).getLstparameterTypes().get(0)
							.equals(double.class)) {
						a = (int) (Math.random() * 50);
					} else if (lstQ.get(ele).getLstparameterTypes().get(0)
							.equals(String.class)) {
						a = RandomCustom.getExpressionStringHasard();
					}
					if (lstQ.get(ele).getLstparameterTypes().size() >= 2) {
						if (lstQ.get(ele).getLstparameterTypes().get(1)
								.equals(double.class)) {
							b = (int) (Math.random() * 50 + 1);
						} else if (lstQ.get(ele).getLstparameterTypes().get(1)
								.equals(String.class)) {
							b = RandomCustom.getExpressionStringHasard();
						}
					}
				}

				analyzeObject(lstInstances, toRemove, ele, a, b);
			}

			for (Object o1 : toRemove) {
				lstInstances.remove(o1);
			}
		}
		return toRemove;
	}

	private void analyzeObject(List<Object> lstInstances,
			List<Object> toRemove, int ele, Object a, Object b) {
		for (Object o1 : lstInstances) {
			try {
				Object r = null;
				List<Class> lstC = lstQ.get(ele).getLstparameterTypes();
				if (lstC.isEmpty()) {
					Method m = o1.getClass().getMethod(
							lstQ.get(ele).getName() + nbGeneration);
					r = m.invoke(o1);
				} else if (lstC.size() == 1) {
					Method m = o1.getClass()
							.getMethod(lstQ.get(ele).getName() + nbGeneration,
									lstC.get(0));
					if (lstC.get(0).equals(double.class)) {
						r = m.invoke(o1, Double.valueOf(a.toString())
								.doubleValue());
					} else if (lstC.get(0).equals(String.class)) {
						r = m.invoke(o1, a.toString());
					}
				} else if (lstC.size() == 2) {
					Method m = o1.getClass().getMethod(
							lstQ.get(ele).getName() + nbGeneration,
							lstC.get(0), lstC.get(1));
					if (lstC.get(0).equals(double.class)) {
						if (lstC.get(1).equals(double.class)) {
							r = m.invoke(o1, Double.valueOf(a.toString())
									.doubleValue(), Double
									.valueOf(b.toString()).doubleValue());
						} else if (lstC.get(1).equals(String.class)) {
							r = m.invoke(o1, Double.valueOf(a.toString())
									.doubleValue(), b.toString());
						}
					} else if (lstC.get(0).equals(String.class)) {
						if (lstC.get(1).equals(double.class)) {
							r = m.invoke(o1, a.toString(),
									Double.valueOf(b.toString()).doubleValue());
						} else if (lstC.get(1).equals(String.class)) {
							r = m.invoke(o1, a.toString(), b.toString());
						}
					}
				}
				List<Object> val = new ArrayList<Object>();
				val.add(r);
				List<Object> lst = new ArrayList<Object>();
				lst.add(a);
				lst.add(b);
				if (!lstQ.get(ele).getExpr().eval(val, lst)) {
					toRemove.add(o1);
				}
			} catch (Exception e) {
				LOG.error("eval", e);
				toRemove.add(o1);
			}
		}
	}

	@Override
	public int getNbGeneration() {
		return nbGeneration;
	}

	public void setNbGeneration(int nbGeneration) {
		this.nbGeneration = nbGeneration;
	}

	@Override
	public List<QualifMethod> getLstQ() {
		return lstQ;
	}
}
