package fr.jcontrol.java.javaparser.num;

public class NumMinusStrict extends NumAbs {
	
	private NumAbs args1;
	
	public NumMinusStrict(NumAbs args1){
		this.args1 = args1;	
	}

	@Override
	public String toString(){
		return " ( - " + args1.toString()  + " ) ";
	}
	
}
