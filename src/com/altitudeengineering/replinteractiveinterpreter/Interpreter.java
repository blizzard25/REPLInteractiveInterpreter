package com.altitudeengineering.replinteractiveinterpreter;

import java.util.HashMap;
import java.util.Scanner;

public class Interpreter {	
	private final ExpressionEvaluator evaluator;
	Scanner scanner;
	public Interpreter() { this.evaluator = new ExpressionEvaluator(new HashMap<>()); }
	public Double input(String expression) { return expression.trim().isEmpty() ? null : this.evaluator.evaluate(new InputParser(expression).parse()); }	
	public static void main(String[] args) {
		Interpreter i = new Interpreter();
		i.scanner = new Scanner(System.in);
		System.out.println("Enter your code below, each line should be followed by a semi-colon. In a new line type END to execute.");
		while(true) {
			String scIn = i.scanner.next();
			String test = scIn;
			System.out.println(test);
			break;
		}
		i.input("y = 8");
		i.input("z = y + 2");
		i.input("x = (z + 5) * 2");
		System.out.print(i.input("x"));
	}
}
