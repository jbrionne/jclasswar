package fr.jcontrol.java.javaparser.booleen.instance;

public class BooleenParam extends BooleenAbs {	

	private boolean param;
	
	public BooleenParam(boolean param){
		this.param = param;
	}
	
	@Override
	public boolean result(){
		return param;
	}	
}
