package fr.jcontrol.java.javaparser;

import java.util.ArrayList;
import java.util.List;

public class QualifMethod {

	private String name;
	private int nbGeneration;
	private List<Class> lstparameterTypes = new ArrayList<Class>();
	private IExpr expr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Class> getLstparameterTypes() {
		return lstparameterTypes;
	}

	public void setLstparameterTypes(List<Class> lstparameterTypes) {
		this.lstparameterTypes = lstparameterTypes;
	}

	public int getNbGeneration() {
		return nbGeneration;
	}

	public void setNbGeneration(int nbGeneration) {
		this.nbGeneration = nbGeneration;
	}

	public IExpr getExpr() {
		return expr;
	}

	public void setExpr(IExpr expr) {
		this.expr = expr;
	}

}
