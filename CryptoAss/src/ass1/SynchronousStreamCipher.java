package ass1;

/*****************************
* CSCI361 Part 6
* --------------------------
* File name: SynchronousStreamCipher.java
* Author: Ang Wei Wen
* Student Number: 5710790
******************************/
import java.io.IOException;
import java.math.BigInteger;

public class SynchronousStreamCipher {
	static String alphabet = "";
	static int mk = 3;
	public static void alphabetready() {
		for (char c = 'A'; c <= 'Z'; ++c)
			alphabet += c;
	}

	// get key MOD(POWER($C$1,2)+B6,26)
	public static int genkey(int c) {
		//System.out.println("K " + c);
		int k = mk;
		for (int s = 1; s <= c; ++s) {
			k = ((k * k) + s) % 26;
		}
		//System.out.println("newkey " + k);
		return k;
	}

	// get the encrypt MOD((E$11+B$7),26)
	public static char en(char p, int k) {
		//	System.out.println("plain: " + p);
		int m = alphabet.indexOf(p);
		//	System.out.print("calculated ");
		//	System.out.println(BigInteger.valueOf(m + k).mod(BigInteger.valueOf(26)));
		// get the char
		return alphabet.charAt((BigInteger.valueOf(m + k).mod(BigInteger.valueOf(26))).intValue());
	}

	// get the decrypt MOD(E$24-B$7,26)
	public static char de(char e, int k) {
		//	System.out.println("cipher: " + e);
		int c = alphabet.indexOf(e);
		//	System.out.print("calculated ");
		//	System.out.println(BigInteger.valueOf(c - k).mod(BigInteger.valueOf(26)));
		// get the char
		return alphabet.charAt((BigInteger.valueOf(c - k).mod(BigInteger.valueOf(26))).intValue());
	}

	public static String changestr(String str, boolean en) {
		char[] ca = str.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			// get key
			int k = genkey(i + 1);
			ca[i] = en ? en(str.charAt(i), k) : de(str.charAt(i), k);
			//			System.out.println("changed char" + ca[i]);
		}
		return String.valueOf(ca);
	}

	public static void main(String args[]) {
		alphabetready();

		if (args.length != 3) {
			
			System.out.println("*** Invalid number of arguments *** Please restart the program");
			System.out.println("example SynchronousStreamCipher key input en");
			System.out.println("example SynchronousStreamCipher key input de");
			System.exit(0);
		}	
		try {
		mk = Integer.parseInt(args[0]);
		}catch (Exception e) {
			System.out.println("key must be number");
			System.exit(0);
		}
		
		
		String I = args[1];
		String isen = args[2];
		
		if(isen.equals("en")) {
			System.out.println("plain :" + I);
		}else if(isen.equals("de")) {
			System.out.println("cipher :" + I);	
		}else {
			System.out.println("last arguments must be en or de");
			System.exit(0);
		}
		System.out.println("cipher :" + changestr( I, true));


	}
}
