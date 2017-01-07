package com.altitudeengineering.replinteractiveinterpreter;

import java.util.Arrays;

public class InputTokenizer {
	private final char[] source;
	private int pos = 0;
	private InputToken currentToken = null;
	public InputTokenizer(String src) {
		source = src.toCharArray();
	}
	public InputToken getToken() {
		currentToken = currentToken == null ? nextToken() : currentToken;
		return currentToken;
	}
	public InputToken consume() {
		InputToken result = getToken();
		currentToken = null;
		return result;
	}
	private InputToken nextToken() {
		consumeWhitespace();
		if (pos == source.length) {
			pos += 1;
			return InputToken.END;
		}
		if(pos > source.length) throw new IllegalStateException("The source is fully consumed");
		switch (source[pos]) {
			case '(': pos += 1; return InputToken.OP;
			case ')': pos += 1; return InputToken.CP;
			case '+': pos += 1; return InputToken.PLUS;
			case '-': pos += 1; return InputToken.MINUS;
			case '*': pos += 1; return InputToken.MULT;
			case '/': pos += 1; return InputToken.DIVIDE;
			case '%': pos += 1; return InputToken.MOD;
			case '=': 
				pos += 1;
				if(source[pos] == '>') {
					pos += 1;
					return InputToken.FUNC;
				}
				return InputToken.ASSIGN;
			default:
				char c = source[pos];
				if(isNumeric(c)) return nextNumber();
				if(identifierStart(c)) return nextVariable();
				throw new IllegalArgumentException("Unknown char " + c + " at " + pos);
		}
	}
	private InputToken nextNumber() {
		final int numberStart = pos;
		acceptDigits();
		TokenType type;
		if (pos < source.length && source[pos] == '.') {
			pos += 1;
			if (pos == source.length || !isNumeric(source[pos])) throw new RuntimeException("Invalid syntax");
			acceptDigits();
			type = TokenType.TOKEN_DOUBLE;
		} else type = TokenType.TOKEN_INTEGER;
		return new InputToken(type, new String(Arrays.copyOfRange(source, numberStart, pos)));
	}
	private void acceptDigits() {
		while(pos < source.length && isNumeric(source[pos])) pos += 1;
	}
	private InputToken nextVariable() {
		final int varStart = pos;
		pos += 1;
		while(pos < source.length && identifier(source[pos])) pos += 1;
		return new InputToken(TokenType.TOKEN_IDENTIFIER, new String(Arrays.copyOfRange(source, varStart, pos)));
	}
	private void consumeWhitespace() { while (pos < source.length && isWhitespace(source[pos])) pos += 1; }
	private static boolean isNumeric(final char c) { return '0' <= c && c <= '9'; }
	private static boolean identifierStart(final char c) { return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || c == '_'; }
	private static boolean identifier(final char c) { return identifierStart(c) || isNumeric(c); }
	private static boolean isWhitespace(final char c) { return c == ' ' || c == '\r' || c == '\n' || c == '\t'; }
}
