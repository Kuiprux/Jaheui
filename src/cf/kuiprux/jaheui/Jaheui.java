package cf.kuiprux.jaheui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Jaheui {

	public static void main(String[] args) {
		if(args.length == 1) {
			try {
				FileHandler fh = new FileHandler();
				String code = fh.readFile(new File(args[0]));
				Interpreter in = new Interpreter();
				in.interprete(code);
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
			} catch (IOException e) {
				System.err.println("Unknows error occurred.");
				System.err.println(e.getMessage());
			}
		} else {
			System.err.println("1 Parameter is needed: aheui file.");
		}
	}
}
