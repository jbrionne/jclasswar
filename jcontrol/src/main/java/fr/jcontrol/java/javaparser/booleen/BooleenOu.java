package fr.jcontrol.java.javaparser.booleen;

public class BooleenOu extends BooleenAbs {
	
	private BooleenAbs args1;
	private BooleenAbs args2;
	
	public BooleenOu(BooleenAbs args1, BooleenAbs args2){
		this.args1 = args1;
		this.args2 = args2;
	}

	@Override
	public String toString(){
		return " ( " + args1.toString()  + " || " +  args2.toString() +  " ) ";
	}
	
}
