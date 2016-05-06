package edu.purduecal.compiler.scanner;

import java.io.IOException;

import edu.purduecal.compiler.error_handling.InvalidTokenException;

public interface IScanner {
	public Token nextToken() throws IOException, InvalidTokenException;
	public boolean isEOF();
}
