/*
 * Token objects represent a token of the source file. They have
 * a value and a token type as well as a line number and the number that 
 * the token is in the line of source code.
 */

package edu.purduecal.compiler.scanner;

public class Token {
	private String value;
	private TokenType tokenType;
	private int lineNumber;
	private int numberOnLine;
	private Token prevToken;
	
	public Token(String value, TokenType tokenType, int lineNumber, int numberOnLine, Token prevToken) {
		super();
		this.value = value;
		this.tokenType = tokenType;
		this.lineNumber = lineNumber;
		this.numberOnLine = numberOnLine;
		this.prevToken = prevToken;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public TokenType getTokenType() {
		return tokenType;
	}
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public int getNumberOnLine() {
		return numberOnLine;
	}
	public void setNumberOnLine(int numberOnLine) {
		this.numberOnLine = numberOnLine;
	}
	public Token getPrevToken() {
		return prevToken;
	}
	public void setPrevToken(Token prevToken) {
		this.prevToken = prevToken;
	}
	public String toString() {
		return "<" + value +", " + tokenType + ">";
	}
}
