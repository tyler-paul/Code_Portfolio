/*
 * TopDownParser is used to parse tokens obtained from a Scanner 
 * according to a parse table.
 */

package edu.purduecal.compiler.parser;

import java.io.IOException;
import java.util.Stack;

import edu.purduecal.compiler.error_handling.ErrorMessageUtility;
import edu.purduecal.compiler.error_handling.InvalidTokenException;
import edu.purduecal.compiler.error_handling.ParsingException;
import edu.purduecal.compiler.scanner.IScanner;
import edu.purduecal.compiler.scanner.Token;
import edu.purduecal.compiler.scanner.TokenType;

public class TopDownParser implements IParser {
	private IScanner scanner;
	private Stack<Symbol> stack;
	private IParseTable parseTable;
	
	/*
	 * Construct a top down parser.
	 * Input:
	 *   scanner : a scanner object to get the tokens from the source file
	 *   parseTable : a table to 
	 */
	public TopDownParser(IScanner scanner, IParseTable parseTable) {
		this.scanner = scanner;
		this.parseTable = parseTable;
		this.stack = new Stack<Symbol>();
	}
	
	/*
	 * Parse the source file.
	 * Returns:
	 *    If parse() returns without throwing an exception then the source file
	 *    is accepted by the parser.
	 * Throws: 
	 *    IOException if there was a problem reading the source file
	 *    InvalidTokenException if an invalid token was detected by the scanner
	 *    ParsingException if there was a syntax error detected by the parser
	 */
	public void parse() throws IOException, InvalidTokenException, ParsingException {
		
		Token token = scanner.nextToken();
		stack.push(TokenType.EOF); 
		stack.push(NonTerminal.GOAL); //start symbol
		Symbol focus = stack.peek();
		
		while (true) {
			if (focus.getValue().equals(TokenType.EOF.getValue()) && token.getTokenType().getValue().equals(TokenType.EOF.getValue())) //if focus == EOF and token = EOF
				return;
			else if (focus.isTerminal() || focus.getValue().equals(TokenType.EOF.getValue())) { //if focus is terminal or focus = EOF
				if ((focus.getValue().equals(token.getTokenType().getValue()))) { //if focus matches token
					stack.pop();
					token = scanner.nextToken();
				}
				else {
					throw new ParsingException("Error: Expected " + focus.getValue() + " but got " + token.getTokenType().getValue() + " on line " + token.getLineNumber()
							+ " at location:\n" + ErrorMessageUtility.createErrorMessage(token.getPrevToken()) + " " + token.getValue() );
				}
			}
			else {
				ProductionRule rule = parseTable.get(NonTerminal.valueOf(focus.getValue()), token.getTokenType());
				stack.pop();

				if (rule != null)
					for (int i =  rule.getRhs().size() - 1; i >= 0; i--) {
						if (rule.getRhs().get(i).getValue() != TokenType.EPSILON.getValue())
							stack.push(rule.getRhs().get(i));
					}
				else {
					throw new ParsingException("Unexpected " + token.getTokenType().getValue() + " on line " + token.getLineNumber()
							+ " at location:\n" + ErrorMessageUtility.createErrorMessage(token.getPrevToken()) + " " + token.getValue() );
				}
			}
			focus = stack.peek();
		}
	}
}
