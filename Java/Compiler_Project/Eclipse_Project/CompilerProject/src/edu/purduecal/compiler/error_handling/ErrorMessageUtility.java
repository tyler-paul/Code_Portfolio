/*
 * The class creates an error message for a given token.
 */

package edu.purduecal.compiler.error_handling;

import edu.purduecal.compiler.scanner.Token;

public class ErrorMessageUtility {
	/*
	 * Create an error message string which shows the previous tokens
	 * on the same line as the passed token followed by and ^^^ to 
	 * indicate the location of the error
	 * Input:
	 *    token : Token to create an error message for
	 */
	public static String createErrorMessage(Token token) {
		if (token == null)
			return "^^^";
		
		String errorMessage = "^^^";
		for (int i = token.getNumberOnLine(); i >= 1; i--) {
			errorMessage = token.getValue() + " " + errorMessage;
			token = token.getPrevToken();
		}
		return errorMessage;
	}
}
