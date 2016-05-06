/*
 * ProductionRule is used to represent production rules in a context-free grammar.
 * The left hand side of a production rule is a non-terminal symbol and the RHS
 * is a list of symbols (terminals or non-terminals) 
 */

package edu.purduecal.compiler.parser;

import java.util.List;

public class ProductionRule {
	private NonTerminal lhs;
	private List<Symbol> rhs;
	
	public ProductionRule(NonTerminal lhs, List<Symbol> rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public NonTerminal getLhs() {
		return lhs;
	}
	
	public void setLhs(NonTerminal lhs) {
		this.lhs = lhs;
	}
	
	public List<Symbol> getRhs() {
		return rhs;
	}
	
	public void setRhs(List<Symbol> rhs) {
		this.rhs = rhs;
	}
}
