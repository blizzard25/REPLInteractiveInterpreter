package com.altitudeengineering.replinteractiveinterpreter;

import java.util.Map;

public class ExpressionEvaluator {
	protected final Map<String, Double> variables;
	ExpressionEvaluator(Map<String, Double> vars) { this.variables = vars; }
	public double evaluate(AbstractSyntaxTree expr) { return expr instanceof BinOpNode ? evaluate((BinOpNode)expr) : expr instanceof NumberNode ? ((NumberNode)expr).number : evaluate((IdentifierNode)expr); }
	private double evaluate(BinOpNode binOpNode) {
		double rhs = evaluate(binOpNode.rhs);
		double lhs = binOpNode.getNodeType() != TreeNodeType.NODE_ASSIGN ? lhs = evaluate(binOpNode.lhs) : 0.0;
		TreeNodeType node = binOpNode.getNodeType();
		if(node != TreeNodeType.NODE_ASSIGN) return node == TreeNodeType.NODE_ADD ? lhs + rhs : node == TreeNodeType.NODE_SUBTRACT ? lhs - rhs : node == TreeNodeType.NODE_MULTIPLY ? lhs * rhs : node == TreeNodeType.NODE_DIVIDE ? lhs / rhs : lhs % rhs;
		variables.put(((IdentifierNode) binOpNode.lhs).identifier, rhs);
		return rhs;
	}
	private double evaluate(IdentifierNode identifierNode) {
		String identifier = identifierNode.identifier;
		if (!variables.containsKey(identifier)) throw new RuntimeException("Error: No variable with name : " + identifier);
		return variables.get(identifier);
	}
}
