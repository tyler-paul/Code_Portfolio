/*
 * State is used to represent the state of the DFA used by the Scanner. 
 * A state can be accepting and if it is accepting then it contains the token type of the
 * token that it accepted.
 */

package edu.purduecal.compiler.scanner;

public enum State {
	S_0(false, TokenType.INVALID),
	S_1(true, TokenType.IDENTIFIER),
	S_2(true, TokenType.INSTANT_NO),
	S_3(true, TokenType.ADDITION_OP),
	S_4(true, TokenType.SUBTRACTION_OP),
	S_5(true, TokenType.MULTIPLICATION_OP),
	S_6(true, TokenType.DIVISION_OP),
	S_7(true, TokenType.LEFT_PAREN),
	S_8(true, TokenType.RIGHT_PAREN),
	S_error(false, TokenType.INVALID),
	S_bad(false, TokenType.INVALID);
	
	private boolean isAccepting;
	private TokenType tokenType;
	
	State(boolean isAccepting, TokenType tokenType) {
		this.isAccepting = isAccepting;
		this.tokenType = tokenType;
	}
	
	public boolean isAccepting() {
		return isAccepting;
	}

	public TokenType getTokenType() {
		return tokenType;
	}
}
