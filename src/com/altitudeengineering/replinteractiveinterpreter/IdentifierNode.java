package com.altitudeengineering.replinteractiveinterpreter;

import java.util.Objects;

public class IdentifierNode implements AbstractSyntaxTree {
	final String identifier;
	IdentifierNode(String identifier) { this.identifier = identifier; }
	@Override
	public TreeNodeType getNodeType() { return TreeNodeType.NODE_IDENTIFIER; }
	@Override
	public boolean equals(Object obj) { return this == obj ? true : obj == null || getClass() != obj.getClass() ? false : Objects.equals(identifier, ((IdentifierNode)obj).identifier); }
}
