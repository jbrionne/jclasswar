package fr.jcontrol.java.javaparser.booleen.instance;

import fr.jcontrol.java.execute.UtilsReflexivity;


public class BooleenAndPrim<T, Z> extends BooleenAbs {

	private Object args1;
	private Object args2;
	
	final Class<T> typeParameterClass1;
	final Class<Z> typeParameterClass2;
	
	public BooleenAndPrim(Class<T> typeParameterClass1, Class<Z> typeParameterClass2, Object args1, Object args2){
		this.args1 = args1;
		this.args2 = args2;
		this.typeParameterClass1 = typeParameterClass1;
		this.typeParameterClass2 = typeParameterClass2;
	}

	@Override
	public boolean result(){		
		return UtilsReflexivity.instanceofMy(args1, typeParameterClass1)  &&  UtilsReflexivity.instanceofMy(args2, typeParameterClass2);
	}
   

}
