/*
 * The scanner class is responsible for splitting up an input source file into tokens.
 */

package edu.purduecal.compiler.scanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

import edu.purduecal.compiler.error_handling.ErrorMessageUtility;
import edu.purduecal.compiler.error_handling.InvalidTokenException;

public class Scanner implements IScanner {
	private SourceFileInputStream stream;
	private ITransitionTable transitionTable;
	private Stack<State> stateStack;
	private boolean eof;
	private int tokenNumberInLine;
	private Token prevToken;
	/*
	 * Construct a Scanner instance for the source file at the
	 * specified file path.
	 * Throws: FileNotFoundException if the file can not be found
	 */
	public Scanner(String fileName, ITransitionTable transitionTable) throws FileNotFoundException {
		stream = new SourceFileInputStream(fileName);
		this.transitionTable = transitionTable;
		stateStack = new Stack<State>();
		eof = false;
		tokenNumberInLine = 1;
		prevToken = null;
	}
	
	/*
	 * Skip over white space in the input file. After a call
	 * to this method the next character from the input stream will 
	 * not be white space.
	 * Throws: IOException if there was an error reading from the input file
	 */
	private void skipOverWhiteSpace() throws IOException {
		char c = stream.nextChar();
		if (c == '\n')
			tokenNumberInLine = 1;
		while (Character.isWhitespace(c)) {
			c = stream.nextChar();
			if (c == '\n')
				tokenNumberInLine = 1;
		}
		stream.rollBack();
	}
	
	/*
	 * This token gets the next token in the source file.
	 * Returns: The next token or EOF if the end of file was reached
	 * Throws: 
	 *    IOException if there was an error reading from the input file
	 *    InvalidTokenException if the token is invalid
	 */
	public Token nextToken() throws IOException, InvalidTokenException {
		if (isEOF())
			return new Token("EOF", TokenType.EOF, stream.getLineNumber(), tokenNumberInLine, prevToken);
		
		skipOverWhiteSpace();
		
		State state = State.S_0;
		StringBuffer lexeme = new StringBuffer();
		stateStack.clear();
		
		stateStack.push(State.S_bad);
		
		while(state != State.S_error) {
			char c = stream.nextChar();
			if (c == '\n')
				tokenNumberInLine = 1;
			if (c == (char)-1)
				eof = true;
			lexeme.append(c);
			if (state.isAccepting())
				stateStack.clear();
			stateStack.push(state);
			
			CharacterCategory category = CharacterCategory.getCategory(c);
			state = transitionTable.getNextState(state, category);
		}
		
		while (state != State.S_0 && !state.isAccepting()) {
			state = stateStack.pop();
			lexeme.deleteCharAt(lexeme.length() - 1); //truncate
			stream.rollBack();
		}
		
		Token token;
		if (state.isAccepting()) {
			token = new Token(lexeme.toString(), state.getTokenType(), stream.getLineNumber(), tokenNumberInLine, prevToken);
			prevToken = token;
		}
		else
			throw new InvalidTokenException("Invalid token on line " + stream.getLineNumber() + " at location:\n\"" 
					+ ErrorMessageUtility.createErrorMessage(prevToken) +"\"");
		tokenNumberInLine++;
		return token;
	}
	
	/*
	 * Determine if the eof is reached
	 * Returns: True if EOF was reached, false otherwise
	 */
	public boolean isEOF() {
		return eof;
	}
}
