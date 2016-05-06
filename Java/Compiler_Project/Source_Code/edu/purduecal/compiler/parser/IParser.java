package edu.purduecal.compiler.parser;

import java.io.IOException;

import edu.purduecal.compiler.error_handling.InvalidTokenException;
import edu.purduecal.compiler.error_handling.ParsingException;

public interface IParser {
	public void parse() throws IOException, InvalidTokenException, ParsingException;
}
