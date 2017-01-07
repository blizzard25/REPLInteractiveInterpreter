package com.altitudeengineering.replinteractiveinterpreter;

public class InputToken {
	static final InputToken OP = new InputToken(TokenType.TOKEN_OPEN_PARENTHESES);
	static final InputToken CP = new InputToken(TokenType.TOKEN_CLOSED_PARENTHESES);
	static final InputToken PLUS = new InputToken(TokenType.TOKEN_PLUS);
	static final InputToken MINUS = new InputToken(TokenType.TOKEN_MINUS);
	static final InputToken MULT = new InputToken(TokenType.TOKEN_MULTIPLY);
	static final InputToken DIVIDE = new InputToken(TokenType.TOKEN_DIVIDE);
	static final InputToken MOD = new InputToken(TokenType.TOKEN_MOD);
	static final InputToken ASSIGN = new InputToken(TokenType.TOKEN_ASSIGN);
	static final InputToken FUNC = new InputToken(TokenType.TOKEN_FUNCTION);
	static final InputToken END = new InputToken(TokenType.END_OF_FUNCTION);	
	final TokenType type;
	final String value;
	InputToken(TokenType type) { this(type, null); }	
	InputToken(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	@Override
	public boolean equals(Object obj) {
		return this == obj ? true : obj == null || getClass() != obj.getClass() ? false : type == ((InputToken)obj).type && value.equals(((InputToken)obj).value);
	}
}
