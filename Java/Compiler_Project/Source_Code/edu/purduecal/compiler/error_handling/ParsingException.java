/*
 * Exception to be thrown when an error occurs during parsing
 */

package edu.purduecal.compiler.error_handling;

public class ParsingException extends Exception {
	public ParsingException(String message) {
		super(message);
	}
}
