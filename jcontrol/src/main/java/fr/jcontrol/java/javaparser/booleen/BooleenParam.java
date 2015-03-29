package fr.jcontrol.java.javaparser.booleen;

public class BooleenParam extends BooleenAbs {	

	private String param;
	
	public BooleenParam(String param){
		this.param = param;
	}
	
	@Override
	public String toString(){
		return param;
	}	
}
