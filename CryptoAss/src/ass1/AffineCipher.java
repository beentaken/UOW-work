package ass1;
import java.math.BigInteger;
/*****************************
* CSCI361 Part 1
* --------------------------
* File name: AffineCipher.java
* Author: Ang Wei Wen
* Student Number: 5710790
******************************/
import java.io.*;
import java.util.Scanner;

public class AffineCipher {
	private static String alphabet = "";
	
	public static void alphabetready() {
	       for(char c = 'A'; c <= 'Z'; ++c)
				alphabet += c;
	}
	
	public static void isvalid(int a, int b) {
		// check a is 1 and b = 0
		if(a == 1 && b == 0) {
			System.out.println("Input Key a = 1 and b = 0 cant be used as text will the same either after or before encryption and decryption! Please restart the program");
			System.exit(0);
		}
		// check a if a is a co prime number of 26
		if (!BigInteger.valueOf(a).gcd(BigInteger.valueOf(alphabet.length())).equals(BigInteger.ONE)) { 
			System.out.println("Input Key a is not a co prime number of 26! Please restart the program");
			System.exit(0);
		} 
			
		if (!((a > 0 && a < 26) &&// a is range from 1 to 25
				(b >= 0) && (b < 26))) { // b is range from 0 to 25
			System.out.println("Input Key a is not range from 1 to 25 or key b is not range from 0 to 25! Please restart the program");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		alphabetready();
		
		int a = 0, b = 0; 
		String input = "";

		// String input = "ILOVEyou ssd";
		if (args.length != 8) {
			System.out.println("*** Invalid number of arguments *** Please restart the program");
			System.exit(0);
		}

		try {
			a = Integer.parseInt(args[1]);
			b = Integer.parseInt(args[2]);
					
		isvalid( a,  b);
		//check a co prime number of 26
			
		} catch (NumberFormatException ex) {
			System.out.println("a or b is not a number! Please restart the program");
			System.exit(0);
		}
		
		input = importfromFile(args[5]);

		if (args[3].equals("-encrypt")) {

			input = encrypt(a, b, input);
			System.out.println("Encryption: " + input);

		}else if (args[3].equals("-decrypt")) {
			input = decrypt(a, b, input);
			System.out.println("Decryption: " + input);
		}else {
			System.out.println("*** Invalid number of arguments *** Please restart the program");
			System.exit(0);
		}

		exportToFile(args[7], input);
	}

	static void exportToFile(String filename, String complete) {
		try {
			// PrintWriter output = new PrintWriter(new FileOutputStream(new File(database),
			// true));
			BufferedWriter output = new BufferedWriter(new FileWriter(filename));

			output.append(complete);
			output.newLine();
			output.close();
		} catch (IOException e) {
			System.out.println("Unable to open " + filename + " for writing.");
			System.exit(0);
		}
	}
	
	static String importfromFile(String filename) { 
		String input = "";
		 try {
				Scanner inputScanner = new Scanner(new File(filename));
				input = inputScanner.useDelimiter("\\A").next();
			inputScanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println(filename + " not found.");
			System.exit(0);System.exit(0);
		}
		return input;
	 }
	
	static String encrypt(int a, int b, String input) {
		BigInteger biga = BigInteger.valueOf(a);
		BigInteger bigb = BigInteger.valueOf(b);
		BigInteger alphlen = BigInteger.valueOf(alphabet.length());
		
		StringBuilder builder = new StringBuilder();
		for (char c : input.toCharArray()) {
			if (Character.isLetter(c)) {
				BigInteger bigc = BigInteger.valueOf(alphabet.indexOf(Character.toUpperCase(c)));
				BigInteger n = biga.multiply(bigc).add(bigb).mod(alphlen);
				if (c == Character.toUpperCase(c)) {
					c = Character.toUpperCase(alphabet.charAt(n.intValue()));
				}
				if (c == Character.toLowerCase(c)) {
					c = Character.toLowerCase(alphabet.charAt(n.intValue()));
				}
			}
			builder.append(c);
		}
		return builder.toString();
	}

	static String decrypt(int a, int b, String input) {
		BigInteger alphlen = BigInteger.valueOf(alphabet.length());
		
		
		StringBuilder builder = new StringBuilder();
		// compute firstKey^-1 aka "modular inverse"
		BigInteger inverse = BigInteger.valueOf(a).modInverse(BigInteger.valueOf(alphabet.length()));
		for (char c : input.toCharArray()) {
			if (Character.isLetter(c)) {
				BigInteger bigc = BigInteger.valueOf(alphabet.indexOf(Character.toUpperCase(c)) - b + alphabet.length());
				
				BigInteger decoded = inverse.multiply(bigc);
				if (c == Character.toUpperCase(c)) {
					c = Character.toUpperCase(alphabet.charAt(decoded.mod(alphlen).intValue()));
				}
				if (c == Character.toLowerCase(c)) {
					c = Character.toLowerCase(alphabet.charAt(decoded.mod(alphlen).intValue()));
				}
			}
			builder.append(c);
		}
		return builder.toString();
	}
}