package fr.jcontrol.java.javaparser.num;

public class NumPlus extends NumAbs {

	private NumAbs args1;
	private NumAbs args2;
	
	public NumPlus(NumAbs args1, NumAbs args2){
		this.args1 = args1;
		this.args2 = args2;
	}

	@Override
	public String toString(){
		return  " ( " + args1.toString()  + " + " + args2.toString() + " ) " ;
	}

	
}
