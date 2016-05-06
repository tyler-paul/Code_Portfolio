/*
 * Exception to be thrown when an invalid token is encountered
 */

package edu.purduecal.compiler.error_handling;

public class InvalidTokenException extends Exception {
	public InvalidTokenException(String message) {
		super(message);
	}
}
