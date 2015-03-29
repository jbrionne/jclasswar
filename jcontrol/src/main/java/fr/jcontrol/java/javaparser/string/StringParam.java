package fr.jcontrol.java.javaparser.string;

public class StringParam extends StringAbs {

	private String param;
	
	public StringParam(String param){
		this.param = param;
	}
	
	@Override
	public String toString(){
		return param;
	}
}
