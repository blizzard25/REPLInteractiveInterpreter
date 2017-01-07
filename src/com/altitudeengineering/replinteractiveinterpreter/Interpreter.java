package com.altitudeengineering.replinteractiveinterpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Interpreter {	
	protected final ExpressionEvaluator evaluator;
	Scanner scanner;
	boolean shouldBreak = false;
	public Interpreter() {
		this.evaluator = new ExpressionEvaluator(new HashMap<>(), new HashMap<>()); 
	}
	public Double input(String expression) {
		String exp = expression;
		String[] enteredVals = exp.split(" ");
		Set<ArrayList<String>> declaredFunctions = this.evaluator.functions.keySet();
		for(ArrayList<String> df : declaredFunctions) {
			if(df.get(0).equals(enteredVals[0])) {
				String funcExpr = this.evaluator.functions.get(df);
				for(int i = 1; i < df.size(); i++){
					funcExpr = funcExpr.replaceAll(df.get(i), enteredVals[i]);
				}
				exp = funcExpr;
			}
		}
		return exp.trim().isEmpty() ? null : this.evaluator.evaluate(new InputParser(exp).parse()); 
	}
	public void input(String function, String expression, ArrayList<String> innerVars) {
		this.evaluator.evaluate(function, expression, innerVars);
	}
	public void checkExpression(String codeLine) {
		String printVarsCmd = "Console.printAllVars";
		String printFuncsCmd = "Console.printAllFunctions";
		String printCmd = "Console.printLast";
		String lastLine = "";
		if(codeLine.charAt(0) == 'f' && codeLine.charAt(1) == 'n' && codeLine.charAt(2) == ' ') {
			StringBuilder sb = new StringBuilder();
			int opIndex = codeLine.indexOf('=');
			ArrayList<String> lhs = new ArrayList<String>();
			for(int c = 3; c < opIndex; c++) {
				StringBuilder lhsVals = new StringBuilder();
				while(codeLine.charAt(c) != ' ') {
					lhsVals.append(codeLine.charAt(c));
					c++;
				}
				lhs.add(lhsVals.toString());
			}
			opIndex += 2;
			for(int c = opIndex; c < codeLine.length(); c++) {
				sb.append(codeLine.charAt(c));
			}
			input(lhs.get(0), sb.toString(), lhs);
		} else if(codeLine.equals(printVarsCmd)) {
			Set<String> vars = this.evaluator.variables.keySet();
			for(String v : vars) {
				System.out.print(String.format("%s = %s", v, input(v)) + " ");
			}
			System.out.println();
		} else if(codeLine.equals(printFuncsCmd)) {
			Set<ArrayList<String>> funcs = this.evaluator.functions.keySet();
			for(ArrayList<String> fns : funcs) {
				for(String f : fns) {
					System.out.print(f + " ");
				}
				System.out.println("=> " + this.evaluator.functions.get(fns));
			}
		} else if(codeLine.equals(printCmd)) {
			System.out.println(input(lastLine));
		} else if(codeLine.equals("END")) {
			shouldBreak = true;
		} else input(codeLine);		
		lastLine = codeLine;
	}
	public static void main(String[] args) {
		Interpreter i = new Interpreter();
		i.scanner = new Scanner(System.in).useDelimiter("\\n");
		System.out.println("Enter your code below.");
		while(true) {
			String codeLine = i.scanner.next();
			i.checkExpression(codeLine);
			if(i.shouldBreak) break;
		}
		i.scanner.close();
	}
}
