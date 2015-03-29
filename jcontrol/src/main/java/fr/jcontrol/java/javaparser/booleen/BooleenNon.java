package fr.jcontrol.java.javaparser.booleen;

public class BooleenNon extends BooleenAbs {
	
	private BooleenAbs args1;
	
	public BooleenNon(BooleenAbs args1){
		this.args1 = args1;
	}

	@Override
	public String toString(){
		return  " ( ! " + args1.toString() + " ) ";
	}
}
