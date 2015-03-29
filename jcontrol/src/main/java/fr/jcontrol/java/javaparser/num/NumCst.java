package fr.jcontrol.java.javaparser.num;

public class NumCst extends NumAbs {

	private String cst;
	
	public NumCst(String cst){
		this.cst = cst;
	}
	
	@Override
	public String toString(){
		return cst;
	}
}
