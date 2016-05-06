/*
 * The class reads and writes from the console.
 */

package edu.purduecal.compiler.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console implements IConsole {
	private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	/*
	 * Write a line to the console
	 * Input:
	 *    string: line to write to console
	 */
	@Override
	public void write(String string) {
		System.out.println(string);
	}
	
	/*
	 * Read a line from the console
	 * Returns:
	 *    string: line read from the console
	 */
	@Override
	public String read() {
		String line = "";
		try {
			line = bufferedReader.readLine();
		}
		catch (Exception e) {	
		}
		return line;
	}
}
