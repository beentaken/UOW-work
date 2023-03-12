package ass1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*****************************
* CSCI361 Part 2
* --------------------------
* File name: IC.java
* Author: Ang Wei Wen
* Student Number: 5710790
******************************/

public class IC {
	//read file
	static String importfromFile(String filename) {
		String input = "";
		try {
			Scanner inputScanner = new Scanner(new File(filename));
			input = inputScanner.useDelimiter("\\A").next();
			inputScanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println(filename + " not found.");
			System.exit(0);
			System.exit(0);
		}
		return input;
	}
	
	
	/**
	 * returns the IC of the input string
	 */
	public double calculate(String s) {
		int i;
		int N = 0;
		double sum = 0.0;
		double total = 0.0;
		s = s.toUpperCase();

		// initialize array of values to count frequency of each letter
		int[] values = new int[26];
		for (i = 0; i < 26; i++) {
			values[i] = 0;
		}

		// calculate frequency of each letter in s
		int ch;
		for (i = 0; i < s.length(); i++) {
			System.out.print((char)s.charAt(i) + " ");
			ch = s.charAt(i) - 65;
			if (ch >= 0 && ch < 26) {
				values[ch]++;
				N++;
			}
		}

		// calculate the sum of each frequency
		for (i = 0; i < 26; i++) {
			ch = values[i];
			System.out.println(values[ch]);
			sum += (ch * (ch - 1));
		}

		// divide by N(N-1)
		total = sum / (N * (N - 1));

		// return the result
		return total;

	}

	/**
	 * used for testing purposes only
	 * 
	 * @param void
	 * @return void
	 */
	public static void main(String[] args) {
		IC test = new IC();
		if (args.length != 1) {
			System.out.println("*** Invalid number of arguments *** Please restart the program");
			System.out.println("example IC TextA.txt");
			System.exit(0);
		}
		String text = importfromFile(args[0]);
		System.out.println(test.calculate(text));
	}
}
