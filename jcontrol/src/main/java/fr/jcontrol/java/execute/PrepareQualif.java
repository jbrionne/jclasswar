package fr.jcontrol.java.execute;

import java.util.List;

import fr.jcontrol.java.javaparser.QualifMethod;

public interface PrepareQualif {

	List<QualifMethod> getLstQ();
	
	int getNbGeneration();
	
	List<Object> eval(List<Object> lstInstances);
}
