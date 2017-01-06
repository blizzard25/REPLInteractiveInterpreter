package com.altitudeengineering.replinteractiveinterpreter;

import java.util.Objects;

public class BinOpNode implements AbstractSyntaxTree {
	final AbstractSyntaxTree lhs, rhs;
	final TreeNodeType type;
	BinOpNode(TreeNodeType type, AbstractSyntaxTree lhs, AbstractSyntaxTree rhs) {
		this.type = type;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	@Override
	public TreeNodeType getNodeType() { return type; }
	@Override
	public boolean equals(Object obj) { return this == obj ? true : (obj == null || getClass() != obj.getClass()) ? false : Objects.equals(lhs, ((BinOpNode)obj).lhs) && Objects.equals(rhs, ((BinOpNode)obj).rhs) && type == ((BinOpNode)obj).type; }
}
