package edu.purduecal.compiler.parser;

import edu.purduecal.compiler.scanner.TokenType;

public interface IParseTable {
	public ProductionRule get(NonTerminal nonTerminal, TokenType terminal);
}
