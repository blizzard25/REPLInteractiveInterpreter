package com.altitudeengineering.replinteractiveinterpreter;

import java.util.HashMap;

public class Interpreter {
	
	private final ExpressionEvaluator evaluator;
	public Interpreter() { this.evaluator = new ExpressionEvaluator(new HashMap<>()); }
	public Double input(String expression) { return expression.trim().isEmpty() ? null : this.evaluator.evaluate(new InputParser(expression).parse()); }
	
	public static void main(String[] args) {
		Interpreter i = new Interpreter();
		i.input("y = 8");
		i.input("z = y + 2");
		i.input("x = (z + 5) * 2");
		System.out.print(i.input("x"));
	}
	
}
