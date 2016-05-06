/*
 * CharacterCategory represents various character categories that
 * a character can be in. Some categories contain only a single character,
 * while others contain many.
 */

package edu.purduecal.compiler.scanner;

public enum CharacterCategory {
	LETTER,
	DIGIT,
	ADDITION,
	SUBTRACTION,
	MULTIPLICATION,
	DIVISION,
	LEFT_PAREN,
	RIGHT_PAREN,
	OTHER;
	
	/*
	 * Determine what character category a given character is in.
	 * Input: c : character to determine the character category of
	 * Returns: The character category of character c
	 */
	public static CharacterCategory getCategory(char c) {
		CharacterCategory category;
		if (Character.isLetter(c))
			category = LETTER;
		else if (Character.isDigit(c))
			category = DIGIT;
		else if (c == '+')
			category = ADDITION;
		else if (c == '-')
			category = SUBTRACTION;
		else if (c == '*')
			category = MULTIPLICATION;
		else if (c == '/')
			category = DIVISION;
		else if (c == '(')
			category = LEFT_PAREN;
		else if (c == ')')
			category = RIGHT_PAREN;
		else
			category = OTHER;
		
		return category;
	}
}
