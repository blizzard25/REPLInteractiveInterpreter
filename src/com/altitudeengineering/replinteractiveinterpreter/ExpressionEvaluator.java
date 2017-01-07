package com.altitudeengineering.replinteractiveinterpreter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ExpressionEvaluator {
	protected final Map<String, Double> variables;
	protected final Map<ArrayList<String>, String> functions;
	ExpressionEvaluator(Map<String, Double> vars, Map<ArrayList<String>, String> funcs) { 
		this.variables = vars;
		this.functions = funcs;
	}
	public double evaluate(AbstractSyntaxTree expr) { 
		return expr instanceof BinOpNode ? evaluate((BinOpNode)expr) : expr instanceof NumberNode ? ((NumberNode)expr).number : expr instanceof IdentifierNode ? evaluate((IdentifierNode)expr) : evaluate((FunctionNode)expr); 
	}
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
		if(!variables.containsKey(identifier)) throw new RuntimeException("Error: No variable with name : " + identifier);
		return variables.get(identifier);
	}
	private double evaluate() {
		return 0;
	}
	public void evaluate(String functionName, String expression, ArrayList<String> lhs) {
		Set<ArrayList<String>> declaredFunctions = functions.keySet();
		for(ArrayList<String> func : declaredFunctions) {
			if(lhs.get(0).equals(func.get(0)) && lhs.size() == func.size()) {
				throw new RuntimeException("Error: Function " + func.get(0) + " has already been declared");
			}
		}
		functions.put(lhs, expression);
	}
}
