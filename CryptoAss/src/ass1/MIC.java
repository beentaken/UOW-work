package ass1;
import java.io.File;
import java.io.FileNotFoundException;
/*****************************
* CSCI361 Part 2
* --------------------------
* File name: MIC.java
* Author: Ang Wei Wen
* Student Number: 5710790
******************************/
import java.util.Scanner;

public class MIC {
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
		}
		return input;
	}
	
	/**
	 * returns the MIC of the input string
	 */
	public double calculate(String pare, String pare2) {
		int i;
		int N = 0, M = 0;
		double sum = 0.0, total = 0.0;
		pare = pare.toUpperCase();
		pare2 = pare2.toUpperCase();
		// initialize array of values to count frequency of each letter for both

		int[] s1freq = new int[26],s2freq = new int[26];
		for (i = 0; i < 26; i++) {
			s1freq[i] = 0;
			s2freq[i] = 0;
		}

		// calculate frequency of each letter in s
		int ch;
		for (i = 0; i < pare.length(); i++) {
			ch = pare.charAt(i) - 65;
			if (ch >= 0 && ch < 26) {
				s1freq[ch]++;
				// all the letter in s
				N++;
			}
		}

		// calculate frequency of each letter in s2
		for (i = 0; i < pare2.length(); i++) {
			ch = pare2.charAt(i) - 65;
			if (ch >= 0 && ch < 26) {
				s2freq[ch]++;
				// all the letter s2
				M++;
			}
		}

		// calculate the sum of all multiply between each letter frequency in s1 and s2
		for (i = 0; i < 26; i++) {
			ch = s1freq[i] * s2freq[i];
			sum += ch;
			System.out.println();
		}
		System.out.println("N * M :" + N * M);
		// divide by N * M)
		total = sum / (N * M);

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
		MIC test = new MIC();
		if (args.length == 2) {
			String text1 = importfromFile(args[0]);
			String text2 = importfromFile(args[1]);
			System.out.println(test.calculate(text1, text2));
		}else {
			System.out.println("*** Invalid number of arguments *** Please restart the program");
			System.out.println("example MIC TextA.txt TextB.txt");
			System.exit(0);
		}
	
	}
}
