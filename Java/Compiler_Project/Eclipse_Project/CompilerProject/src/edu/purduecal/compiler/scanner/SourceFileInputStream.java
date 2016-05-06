/*
 * SourceFileInputStream reads characters from a source file. It has the capability
 * of rolling back the input stream up to PUSHBACK_BUFFER_SIZE characters
 */

package edu.purduecal.compiler.scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Stack;

public class SourceFileInputStream {
	private PushbackInputStream stream;
	private Stack<Character> rollBackStack;
	private static final int PUSHBACK_BUFFER_SIZE = 20; 
	private int lineNumber;
	/*
	 * Construct a SourceFileInputStream instance for the source file at the specified fileName.
	 * Input:
	 *    fileName : path to the file of the source file
	 * Throws: FileNotFoundException if the file could not be found
	 */
	public SourceFileInputStream(String fileName) throws FileNotFoundException {
		stream = new PushbackInputStream(new FileInputStream(fileName), PUSHBACK_BUFFER_SIZE);
		rollBackStack = new Stack<Character>();
		lineNumber = 1;
	}
	
	/*
	 * Read the next character of the input stream.
	 * Throws: IOException if there was an error reading from the stream
	 */
	public char nextChar() throws IOException {
		char c = (char)stream.read();
		if (c == '\n') {
			lineNumber++;
		}
		rollBackStack.push(c);
		return c;
	}
	
	/*
	 * Roll back the input stream a single character
	 * Throws: IOException if there was an error rolling back the stream
	 */ 
	public void rollBack() throws IOException {
		Character c = rollBackStack.pop();
		if (c.charValue() == '\n')
			lineNumber--;
		stream.unread(c);
	}
	
	/*
	 * Close the stream.
	 * Throws: IOException if there was an error closing the stream
	 */
	public void close() throws IOException {
		rollBackStack.clear();
		stream.close();
	}

	/*
	 * Get the line number the stream is currently at
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	
	
}
