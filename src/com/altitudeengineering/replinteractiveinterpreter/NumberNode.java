package com.altitudeengineering.replinteractiveinterpreter;

public class NumberNode implements AbstractSyntaxTree {	
	final double number;
	NumberNode(int number) { this.number = number; }
	NumberNode(double number) { this.number = number; }
	@Override
	public TreeNodeType getNodeType() { return TreeNodeType.NODE_NUMBER; }
	@Override
	public boolean equals(Object obj) { return this == obj ? true : obj == null ? false : Double.compare(((NumberNode)obj).number, number) == 0; }
}
