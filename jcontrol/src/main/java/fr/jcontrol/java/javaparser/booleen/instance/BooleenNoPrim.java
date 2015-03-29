package fr.jcontrol.java.javaparser.booleen.instance;

import fr.jcontrol.java.execute.UtilsReflexivity;


public class BooleenNoPrim<T> extends BooleenAbs {

	private Object args1;
	
	final Class<T> typeParameterClass1;
	
	public BooleenNoPrim(Class<T> typeParameterClass1, Object args1){
		this.args1 = args1;
		this.typeParameterClass1 = typeParameterClass1;		
	}

	@Override
	public boolean result(){		
		return !UtilsReflexivity.instanceofMy(args1, typeParameterClass1);
	}
   

}
