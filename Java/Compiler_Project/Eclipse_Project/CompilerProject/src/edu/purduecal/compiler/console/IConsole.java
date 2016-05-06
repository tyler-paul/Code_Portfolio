/*
 * The interface is from reading from and writing to the console
 */

package edu.purduecal.compiler.console;

public interface IConsole {
	public abstract void write(String string);
	public abstract String read();
}
