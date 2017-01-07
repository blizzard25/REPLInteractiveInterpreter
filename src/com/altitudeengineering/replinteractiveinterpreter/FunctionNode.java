package com.altitudeengineering.replinteractiveinterpreter;

import java.util.Objects;

public class FunctionNode implements AbstractSyntaxTree {
	final String lhs, rhs;
	final TreeNodeType type;
	FunctionNode(TreeNodeType type, String lhs, String rhs) {
		this.type = type;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	@Override
	public TreeNodeType getNodeType() { return type; }
	//@Override
	//public boolean equals(Object obj) { return this == obj ? true : (obj == null || getClass() != obj.getClass()) ? false : Objects.equals(lhs, ((BinOpNode)obj).lhs) && Objects.equals(rhs, ((BinOpNode)obj).rhs) && type == ((BinOpNode)obj).type; }
}
