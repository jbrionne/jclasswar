package fr.jcontrol.java.javaparser;

import java.util.List;

public interface IExpr {

	boolean eval(List<Object> val, List<Object> lst);
}
