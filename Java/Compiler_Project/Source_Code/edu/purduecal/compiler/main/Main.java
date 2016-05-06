/*
 * Main is the entry point to the system. It asks the user to enter
 * a path for a source file to parse, and then parses the file at
 * that path. An error message is displayed to the console if an
 * error occurs during parsing.
 */

package edu.purduecal.compiler.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.purduecal.compiler.console.Console;
import edu.purduecal.compiler.console.IConsole;
import edu.purduecal.compiler.error_handling.InvalidTokenException;
import edu.purduecal.compiler.error_handling.ParsingException;
import edu.purduecal.compiler.parser.IParser;
import edu.purduecal.compiler.parser.ParseTable;
import edu.purduecal.compiler.parser.TopDownParser;
import edu.purduecal.compiler.scanner.IScanner;
import edu.purduecal.compiler.scanner.Scanner;
import edu.purduecal.compiler.scanner.TransitionTable;

public class Main {

	public static void main(String[] args) {
		IConsole console = new Console();
		String path = "";
		
		if (args.length == 0) { //read path from user
			console.write("Enter the path of the file to parse:");
			path = console.read();
		}
		else if (args.length == 1) //get path as a command line argument
			path = args[0];
		else {
			console.write("Usage: Either run the program with no arguments or supply a single path to the source file via a command line argument");
		}
		
		IScanner scanner = null;
		try {
			scanner = new Scanner(path,  new TransitionTable());
		}
		catch (FileNotFoundException e) {
			console.write("File not found");
			return;
		}
		
		
		IParser parser = new TopDownParser(scanner, new ParseTable());
		try {
			parser.parse();
			console.write("Source file accepted by parser");
		} catch (IOException e) {
			console.write("Failed to read file");
		} catch (InvalidTokenException e) {
			console.write(e.getMessage());
		} catch (ParsingException e) {
			console.write(e.getMessage());
		}
		
	}

}
