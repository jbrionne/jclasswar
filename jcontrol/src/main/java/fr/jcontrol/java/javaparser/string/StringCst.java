package fr.jcontrol.java.javaparser.string;

public class StringCst extends StringAbs {

	private String cst;
	
	public StringCst(String cst){
		this.cst = cst;
	}
	
	@Override
	public String toString(){
		return cst;
	}
}
