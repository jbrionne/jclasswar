package fr.jcontrol.java.javaparser.booleen.instance;

public class BooleenOr extends BooleenAbs {
	
	private BooleenAbs args1;
	private BooleenAbs args2;
	
	public BooleenOr(BooleenAbs args1, BooleenAbs args2){
		this.args1 = args1;
		this.args2 = args2;
	}

	@Override
	public boolean result(){
		return args1.result()  ||  args2.result();
	}
	
}
