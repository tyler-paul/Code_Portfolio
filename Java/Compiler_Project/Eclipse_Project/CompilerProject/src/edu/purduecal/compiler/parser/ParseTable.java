/*
 * ParseTable is used by the TopDownParser to facilitate parsing. Given a 
 * non-terminal to expand and a lookahead terminal symbol, the table holds
 * an entry for the production rule to expand the non-terminal.
 */

package edu.purduecal.compiler.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.purduecal.compiler.scanner.TokenType;

public class ParseTable implements IParseTable {
	Map<ParseTableKey, ProductionRule> map;
	
	/*
	 * ParseTableKey is a static nested class to act as a key
	 * for the hashmap used to implement the parse table.
	 */
	private static class ParseTableKey {
		NonTerminal nonTerminal;
		TokenType terminal;
		
		public ParseTableKey(NonTerminal nonTerminal, TokenType terminal) {
			super();
			this.nonTerminal = nonTerminal;
			this.terminal = terminal;
		}
		
		@Override
		public boolean equals(Object key) {
			if (!(key instanceof ParseTableKey))
				return false;
			if (this.nonTerminal.getValue().equals(((ParseTableKey)key).nonTerminal.getValue()) 
					&&
					this.terminal.getValue().equals(((ParseTableKey)key).terminal.getValue()) 
					)
					return true;
			else
				return false;
		}
		
		@Override
		public int hashCode() {
			return nonTerminal.hashCode() + terminal.hashCode();
		}
	}
	
	/*
	 * Construct and initialize the parse table according to the specified production rules.
	 */
	public ParseTable() {
		map = new HashMap<ParseTableKey, ProductionRule>();
		
		ProductionRule pr0 = new ProductionRule(NonTerminal.GOAL, Arrays.asList(NonTerminal.EXPR));
		ProductionRule pr1 = new ProductionRule(NonTerminal.EXPR, Arrays.asList(NonTerminal.TERM, NonTerminal.EXPR_PRIME));
		ProductionRule pr2 = new ProductionRule(NonTerminal.EXPR_PRIME, Arrays.asList(TokenType.ADDITION_OP, NonTerminal.TERM, NonTerminal.EXPR_PRIME));
		ProductionRule pr3 = new ProductionRule(NonTerminal.EXPR_PRIME, Arrays.asList(TokenType.SUBTRACTION_OP, NonTerminal.TERM, NonTerminal.EXPR_PRIME));
		ProductionRule pr4 = new ProductionRule(NonTerminal.EXPR_PRIME, Arrays.asList(TokenType.EPSILON));
		ProductionRule pr5 = new ProductionRule(NonTerminal.TERM, Arrays.asList(NonTerminal.FACTOR, NonTerminal.TERM_PRIME));
		ProductionRule pr6 = new ProductionRule(NonTerminal.TERM_PRIME, Arrays.asList(TokenType.MULTIPLICATION_OP, NonTerminal.FACTOR, NonTerminal.TERM_PRIME));
		ProductionRule pr7 = new ProductionRule(NonTerminal.TERM_PRIME, Arrays.asList(TokenType.DIVISION_OP, NonTerminal.FACTOR, NonTerminal.TERM_PRIME));
		ProductionRule pr8 = new ProductionRule(NonTerminal.TERM_PRIME, Arrays.asList(TokenType.EPSILON));
		ProductionRule pr9 = new ProductionRule(NonTerminal.FACTOR, Arrays.asList(TokenType.LEFT_PAREN, NonTerminal.EXPR, TokenType.RIGHT_PAREN));
		ProductionRule pr10 = new ProductionRule(NonTerminal.FACTOR, Arrays.asList(TokenType.INSTANT_NO));
		ProductionRule pr11 = new ProductionRule(NonTerminal.FACTOR, Arrays.asList(TokenType.IDENTIFIER));
		
		map.put(new ParseTableKey(NonTerminal.GOAL, TokenType.IDENTIFIER), pr0);
		map.put(new ParseTableKey(NonTerminal.GOAL, TokenType.INSTANT_NO), pr0);
		map.put(new ParseTableKey(NonTerminal.GOAL, TokenType.LEFT_PAREN), pr0);
		
		map.put(new ParseTableKey(NonTerminal.EXPR, TokenType.IDENTIFIER), pr1);
		map.put(new ParseTableKey(NonTerminal.EXPR, TokenType.INSTANT_NO), pr1);
		map.put(new ParseTableKey(NonTerminal.EXPR, TokenType.LEFT_PAREN), pr1);
		
		map.put(new ParseTableKey(NonTerminal.EXPR_PRIME, TokenType.ADDITION_OP), pr2);
		map.put(new ParseTableKey(NonTerminal.EXPR_PRIME, TokenType.SUBTRACTION_OP), pr3);
		map.put(new ParseTableKey(NonTerminal.EXPR_PRIME, TokenType.RIGHT_PAREN), pr4);
		map.put(new ParseTableKey(NonTerminal.EXPR_PRIME, TokenType.EOF), pr4);
		
		map.put(new ParseTableKey(NonTerminal.TERM, TokenType.IDENTIFIER), pr5);
		map.put(new ParseTableKey(NonTerminal.TERM, TokenType.INSTANT_NO), pr5);
		map.put(new ParseTableKey(NonTerminal.TERM, TokenType.LEFT_PAREN), pr5);
		
		map.put(new ParseTableKey(NonTerminal.TERM_PRIME, TokenType.ADDITION_OP), pr8);
		map.put(new ParseTableKey(NonTerminal.TERM_PRIME, TokenType.SUBTRACTION_OP), pr8);
		map.put(new ParseTableKey(NonTerminal.TERM_PRIME, TokenType.MULTIPLICATION_OP), pr6);
		map.put(new ParseTableKey(NonTerminal.TERM_PRIME, TokenType.DIVISION_OP), pr7);
		map.put(new ParseTableKey(NonTerminal.TERM_PRIME, TokenType.RIGHT_PAREN), pr8);
		map.put(new ParseTableKey(NonTerminal.TERM_PRIME, TokenType.EOF), pr8);
		
		map.put(new ParseTableKey(NonTerminal.FACTOR, TokenType.IDENTIFIER), pr11);
		map.put(new ParseTableKey(NonTerminal.FACTOR, TokenType.INSTANT_NO), pr10);
		map.put(new ParseTableKey(NonTerminal.FACTOR, TokenType.LEFT_PAREN), pr9);
	}
	
	/*
	 * Get the production rule need to expand a non-terminal symbol
	 * Input:
	 *    nonTerminal : The non-terminal symbol to expand
	 *    terminal : the look ahead terminal symbol
	 * Returns: 
	 *    the production rule to expand the non-terminal or null
	 *    if one doesn't exist
	 */
	public ProductionRule get(NonTerminal nonTerminal, TokenType terminal) {
		return map.get(new ParseTableKey(nonTerminal, terminal));
	}
}


