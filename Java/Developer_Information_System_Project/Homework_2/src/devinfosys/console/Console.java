package devinfosys.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console implements IConsole {
	private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public void write(String string) {
		System.out.println(string);
	}

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
	
//	public static void main(String[] args) {
//		Console console = new Console();
//		console.write("Enter something");
//		String ans = console.read();
//		console.write(ans);
//	}

}
