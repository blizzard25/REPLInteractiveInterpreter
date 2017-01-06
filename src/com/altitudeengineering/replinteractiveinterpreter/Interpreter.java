package com.altitudeengineering.replinteractiveinterpreter;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Interpreter {	
	private final ExpressionEvaluator evaluator;
	Scanner scanner;
	public Interpreter() { this.evaluator = new ExpressionEvaluator(new HashMap<>()); }
	public Double input(String expression) { return expression.trim().isEmpty() ? null : this.evaluator.evaluate(new InputParser(expression).parse()); }	
	public static void main(String[] args) {
		Interpreter i = new Interpreter();
		i.scanner = new Scanner(System.in).useDelimiter("\\n");
		System.out.println("Enter your code below.");
		String printCmd = "Console.printAllVars";
		while(true) {
			String codeLine = i.scanner.next();
			if(codeLine.equals("END")) break;
			if(codeLine.equals(printCmd)) {
				Set<String> vars = i.evaluator.variables.keySet();
				for(String v : vars) {
					System.out.println(String.format("%s = %s", v, i.input(v)));
				}
				continue;
			}
			i.input(codeLine);
		}
		i.scanner.close();
	}
}
