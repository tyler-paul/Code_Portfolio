/*
 * TokenType is used to represent the type of a token.
 */

package edu.purduecal.compiler.scanner;

import edu.purduecal.compiler.parser.Symbol;

public enum TokenType implements Symbol {
	IDENTIFIER,
	INSTANT_NO,
	ADDITION_OP,
	SUBTRACTION_OP,
	MULTIPLICATION_OP,
	DIVISION_OP,
	LEFT_PAREN,
	RIGHT_PAREN,
	EPSILON,
	INVALID,
	EOF;

	@Override
	public boolean isTerminal() {
		return true;
	}

	@Override
	public String getValue() {
		return this.name();
	}
}
