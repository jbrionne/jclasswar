package fr.jcontrol.java.javaparser.booleen.instance;

public class BooleenNo extends BooleenAbs {
	
	private BooleenAbs args1;
	
	public BooleenNo(BooleenAbs args1){
		this.args1 = args1;
	}

	@Override
	public boolean result(){
		return  ! args1.result();
	}
}
