package com.altitudeengineering.replinteractiveinterpreter;

public class InputParser {
	private final InputTokenizer tokenizer;
	InputParser(InputTokenizer tokenizer) { this.tokenizer = tokenizer; }
	InputParser(String source) { this.tokenizer = new InputTokenizer(source); }
	AbstractSyntaxTree parse() {
		AbstractSyntaxTree body = addOrSubtractExpression();
		assertAndConsume(TokenType.END_OF_FUNCTION);
		return body;
	}
	private InputToken assertAndConsume(TokenType tokenType) {
		InputToken next = tokenizer.getToken();
		if (next.type != tokenType) throw new IllegalStateException(String.format("Expected : " + tokenType + "\nFound : " + next.type));
		return tokenizer.consume();
	}
	private TokenType getTokenType() { return tokenizer.getToken().type; }
	private AbstractSyntaxTree addOrSubtractExpression() {
		AbstractSyntaxTree lhs = multiplyOrDivideExpression();
		while(true) {
			if(getTokenType() == TokenType.TOKEN_PLUS) {
				assertAndConsume(TokenType.TOKEN_PLUS);
				lhs = new BinOpNode(TreeNodeType.NODE_ADD, lhs, multiplyOrDivideExpression());
				continue;
			} else if(getTokenType() == TokenType.TOKEN_MINUS) {
				assertAndConsume(TokenType.TOKEN_MINUS);
				lhs = new BinOpNode(TreeNodeType.NODE_SUBTRACT, lhs, multiplyOrDivideExpression());
				continue;
			}
			break;
		}
		return lhs;
	}
	private AbstractSyntaxTree multiplyOrDivideExpression() {
		AbstractSyntaxTree lhs = term();
		while(true) {
			if(getTokenType() == TokenType.TOKEN_MULTIPLY) {
				assertAndConsume(TokenType.TOKEN_MULTIPLY);
				lhs = new BinOpNode(TreeNodeType.NODE_MULTIPLY, lhs, term());
				continue;
			} else if (getTokenType() == TokenType.TOKEN_DIVIDE) {
				assertAndConsume(TokenType.TOKEN_DIVIDE);
				lhs = new BinOpNode(TreeNodeType.NODE_DIVIDE, lhs, term());
				continue;
			} else if (getTokenType() == TokenType.TOKEN_MOD) {
				assertAndConsume(TokenType.TOKEN_MOD);
				lhs = new BinOpNode(TreeNodeType.NODE_MOD, lhs, term());
				continue;
			}
			break;
		}
		return lhs;
	}
	private AbstractSyntaxTree term() {
		TokenType tt = getTokenType();
		if(tt == TokenType.TOKEN_OPEN_PARENTHESES) {
			assertAndConsume(TokenType.TOKEN_OPEN_PARENTHESES);
			AbstractSyntaxTree parenExpr = addOrSubtractExpression();
			assertAndConsume(TokenType.TOKEN_CLOSED_PARENTHESES);
			return parenExpr;
		} else if(tt == TokenType.TOKEN_IDENTIFIER) {
			InputToken identifierToken = assertAndConsume(TokenType.TOKEN_IDENTIFIER);
			IdentifierNode identifier = new IdentifierNode(identifierToken.value);
			if(getTokenType() == TokenType.TOKEN_ASSIGN) {
				assertAndConsume(TokenType.TOKEN_ASSIGN);
				AbstractSyntaxTree expr = addOrSubtractExpression();
				return new BinOpNode(TreeNodeType.NODE_ASSIGN, identifier, expr);
			}
			return identifier;
		} else if(tt == TokenType.TOKEN_INTEGER) {
			InputToken intToken = assertAndConsume(TokenType.TOKEN_INTEGER);
			int intValue = Integer.parseInt(intToken.value);
			return new NumberNode(intValue);
		} else if(tt == TokenType.TOKEN_DOUBLE) {
			InputToken dblToken = assertAndConsume(TokenType.TOKEN_DOUBLE);
			double dblValue = Double.parseDouble(dblToken.value);
			return new NumberNode(dblValue);
		} else throw new RuntimeException("Unexpected token " + tokenizer.getToken());		
	}
}
