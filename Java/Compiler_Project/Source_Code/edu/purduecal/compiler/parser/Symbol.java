/*
 * Symbol objects are symbols that can be used in production rules.
 * They can be terminal or nonterminal.
 */

package edu.purduecal.compiler.parser;

public interface Symbol {
	public abstract boolean isTerminal();
	public abstract String getValue();
}
