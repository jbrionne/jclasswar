package fr.jcontrol.java.javaparser.num;

public class NumParam extends NumAbs {

	private String param;
	
	public NumParam(String param){
		this.param = param;
	}
	
	@Override
	public String toString(){
		return param;
	}
}
