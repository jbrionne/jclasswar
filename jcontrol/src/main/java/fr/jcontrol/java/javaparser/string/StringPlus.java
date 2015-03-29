package fr.jcontrol.java.javaparser.string;

public class StringPlus extends StringAbs {

	private StringAbs args1;
	private StringAbs args2;
	
	public StringPlus(StringAbs args1, StringAbs args2){
		this.args1 = args1;
		this.args2 = args2;
	}

	@Override
	public String toString(){
		return  args1.toString() + " + " + args2.toString();
	}

	
}
