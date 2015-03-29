package fr.jcontrol.java.javaparser;

import java.util.List;

import fr.jcontrol.java.javaparser.booleen.BooleenAbs;
import fr.jcontrol.java.javaparser.booleen.BooleenEt;
import fr.jcontrol.java.javaparser.booleen.BooleenFalse;
import fr.jcontrol.java.javaparser.booleen.BooleenNon;
import fr.jcontrol.java.javaparser.booleen.BooleenOu;
import fr.jcontrol.java.javaparser.booleen.BooleenParam;
import fr.jcontrol.java.javaparser.booleen.BooleenTrue;
import fr.jcontrol.java.javaparser.num.NumAbs;
import fr.jcontrol.java.javaparser.num.NumCst;
import fr.jcontrol.java.javaparser.num.NumDiv;
import fr.jcontrol.java.javaparser.num.NumMinus;
import fr.jcontrol.java.javaparser.num.NumMinusStrict;
import fr.jcontrol.java.javaparser.num.NumMult;
import fr.jcontrol.java.javaparser.num.NumParam;
import fr.jcontrol.java.javaparser.num.NumPi;
import fr.jcontrol.java.javaparser.num.NumPlus;
import fr.jcontrol.java.javaparser.string.StringAbs;
import fr.jcontrol.java.javaparser.string.StringCst;
import fr.jcontrol.java.javaparser.string.StringParam;
import fr.jcontrol.java.javaparser.string.StringPlus;

public class RandomCustom {
	
	private RandomCustom() {
		throw new AssertionError("Don't instantiated this class");
	}

	public static boolean getBoolean() {
		double rand = Math.random();
		if (rand > 0.5) {
			return false;
		} else {
			return true;
		}
	}

	public static String getOneParameter(List<String> myParametre) {
		int rand = (int) (Math.random() * myParametre.size());
		return myParametre.get(rand);
	}

	public static String getExpressionBooleen(List<String> myParametre) {
		return getExpressionBool(myParametre, 0).toString();
	}

	public static String getExpressionNumerique(List<String> myParametre) {
		return getExpressionNum(myParametre, 0).toString();
	}

	public static String getExpressionString(List<String> myParametre) {
		return getExpressionString(myParametre, 0).toString();
	}

	public static String getExpressionStringHasard() {
		double rand = Math.random();
		if (rand < 0.5) {
			return "a";
		} else {
			return "b";
		}
	}

	public static StringAbs getExpressionString(List<String> myParametre,
			int indexOri) {
		int index = indexOri + 1;
		StringAbs toAdd = null;
		double rand = Math.random() * (1 + 4);
		if (index > 3) {
			rand = 12;
		}
		if (rand < 1) {
			toAdd = new StringPlus(getExpressionString(myParametre, index),
					getExpressionString(myParametre, index));
		} else {
			if (myParametre == null || myParametre.isEmpty()) {
				toAdd = new StringCst("d");
			} else {
				int rand2 = (int) (Math.random() * myParametre.size());
				toAdd = new StringParam(myParametre.get(rand2));
			}
		}
		return toAdd;

	}

	public static BooleenAbs getExpressionBool(List<String> myParametre,
			int indexOri) {
		int index = indexOri + 1;
		BooleenAbs toAdd = null;
		double rand = Math.random() * (1 + 15);
		if (index > 3) {
			rand = 12;
		}
		if (rand < 1) {
			toAdd = new BooleenEt(getExpressionBool(myParametre, index),
					getExpressionBool(myParametre, index));
		} else if (rand < 2) {
			toAdd = new BooleenOu(getExpressionBool(myParametre, index),
					getExpressionBool(myParametre, index));
		} else if (rand < 3) {
			toAdd = new BooleenNon(getExpressionBool(myParametre, index));
		} else if (rand < 4) {
			toAdd = new BooleenTrue();
		} else if (rand < 5) {
			toAdd = new BooleenFalse();
		} else {
			if (myParametre == null || myParametre.isEmpty()) {
				toAdd = new BooleenTrue();
			} else {
				int rand2 = (int) (Math.random() * myParametre.size());
				toAdd = new BooleenParam(myParametre.get(rand2));
			}
		}
		return toAdd;
	}

	public static NumAbs getExpressionNum(List<String> myParametre, int indexOri) {
		int index = indexOri + 1;		
		NumAbs toAdd = null;
		double rand = Math.random() * (1 + 15);
		if (index > 3) {
			rand = 12;
		}
		if (rand < 1) {
			toAdd = new NumPlus(getExpressionNum(myParametre, index),
					getExpressionNum(myParametre, index));
		} else if (rand < 2) {
			toAdd = new NumMinus(getExpressionNum(myParametre, index),
					getExpressionNum(myParametre, index));
		} else if (rand < 3) {
			toAdd = new NumCst(String.valueOf((int) (Math.random() * 500)));
		} else if (rand < 4) {
			toAdd = new NumMult(getExpressionNum(myParametre, index),
					getExpressionNum(myParametre, index));
		} else if (rand < 5) {
			toAdd = new NumDiv(getExpressionNum(myParametre, index),
					getExpressionNum(myParametre, index));
		} else if (rand < 6) {
			toAdd = new NumPi();
		} else if (rand < 7) {
			toAdd = new NumMinusStrict(getExpressionNum(myParametre, index));
		} else {
			if (myParametre == null || myParametre.isEmpty()) {
				toAdd = new NumCst(String.valueOf((int) (Math.random() * 500)));
			} else {
				int rand2 = (int) (Math.random() * myParametre.size());
				toAdd = new NumParam(myParametre.get(rand2));
			}
		}
		return toAdd;
	}

}
