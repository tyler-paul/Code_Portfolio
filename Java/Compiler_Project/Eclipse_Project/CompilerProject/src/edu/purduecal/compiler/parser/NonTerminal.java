/*
 * NonTerminal is used to represent non-terminal symbols in production rules.
 */

package edu.purduecal.compiler.parser;

public enum NonTerminal implements Symbol {
	GOAL,
	EXPR,
	EXPR_PRIME,
	TERM,
	TERM_PRIME,
	FACTOR;

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public String getValue() {
		return this.name();
	}
}
