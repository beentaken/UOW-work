package oldknapsack;

/*****************************
 * CSCI361 Assignment 2 
 * File name: Knapsack.java 
 * Author:Ang Wei Wen 
 * Student Number: 5710790
 ******************************/

import java.math.BigInteger;
import java.util.*;

public class TaskTwo {

	int knapSize = 0, modulusM = 0;
	ArrayList<Integer> publicKey = new ArrayList<Integer>();
	ArrayList<Integer> privateKey = new ArrayList<Integer>();
	BigInteger inverseW = BigInteger.ZERO;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaskTwo knp = new TaskTwo();

		knp.KeyGen();
		knp.Encrypt();
		knp.Decrypt();
	}

	static int readInt(String title) {
		Scanner sc = new Scanner(System.in);
		System.out.print(title);
		while (true)
			try {
				return Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.print("Must be Whole number: ");
			}
	}

	private String readString(String prompt) {
		System.out.print(prompt);
		return new java.util.Scanner(System.in).nextLine();
	}

	public void KeyGen() {
		// Size and Values of AU
		int currtotal = 0;
		while (knapSize < 2) {
			knapSize = readInt("Please enter the size of super-increasing knapsack ( MUST BE MORE THAN 2 ): ");
		}

		for (int i = 1; i <= knapSize; i++) {
			int valueA = readInt("Enter value for Private key A" + (i) + " (Value must bigger than total number): ");
			while (valueA <= currtotal) {
				System.out.println("Value of A" + (i) + " must be bigger than total sum of value A");
				valueA = readInt("Enter value of A" + (i) + "(Value must bigger than total number): ");
			}

			privateKey.add(valueA);
			currtotal += valueA;
			System.out.println("All values of A inputed.");
			System.out.println("Values of private key: " + privateKey);
			System.out.println("Total Sum: " + currtotal);
		}

		// Choose modulus m.
		while (modulusM <= currtotal) {
			modulusM = readInt("Modulus M must be a integer bigger than total Sum.\nEnter modulus M: ");
		} // end of while-loop

		// Choose mutiplier W
		int mutiplierW = 0;
		BigInteger gcd = BigInteger.valueOf(modulusM).gcd(BigInteger.valueOf(mutiplierW));
		while (gcd.intValue() != 1 || mutiplierW <= 1 || mutiplierW > modulusM) {
			mutiplierW = readInt(
					"Mutiplier W must be a integer smaller than M and co-prime to M when gcd(M,W) = 1\nEnter your W value (range between 1 to M) : ");
			gcd = BigInteger.valueOf(modulusM).gcd(BigInteger.valueOf(mutiplierW));
			if (gcd.intValue() != 1) {
				System.out.println("gcd(m,w) is not equals to 1");
			}
			if (mutiplierW <= 1) {
				System.out.println("Mutiplier W must be more than 1");
			}
			if (mutiplierW <= 1) {
				System.out.println("Mutiplier W must be less than M");
			}
		}
		System.out.println("GCD (" + modulusM + "," + mutiplierW + ") : " + gcd);
		System.out.println("Value of W : " + mutiplierW);

		inverseW = BigInteger.valueOf(mutiplierW).modInverse(BigInteger.valueOf(modulusM)); // Inverse of W
		System.out.println(mutiplierW + "^-1 mod " + modulusM + " : " + inverseW + "(W^-1)");

		// Compute public key
		for (int AKey : privateKey) {
			BigInteger b = (BigInteger.valueOf(mutiplierW).multiply(BigInteger.valueOf(AKey)))
					.mod(BigInteger.valueOf(modulusM));
			// System.out.println(bB);
			publicKey.add(b.intValue());
		}
		System.out.println("Public key list : " + publicKey);
	}

	// Encryption
	public void Encrypt() {
		System.out.println();
		System.out.println("Enter your msg for encryption: ");
		Scanner scan = new Scanner(System.in);
		String msg = scan.nextLine();
		char[] msgs = msg.toCharArray();
		ArrayList<Integer> decimal = new ArrayList<Integer>();
		for (char m : msgs) {
			decimal.add((int) m);
		}

		System.out.println("Decimal value for each letter: " + decimal);

		// Append 0 in front of binary if binary is less than knapsack size(8).
		ArrayList<String> binary = new ArrayList<String>();
		for (int d : decimal) {
			String bin = "0";
			String bin2 = "";
			// String binarySize = Integer.toBinaryString(d);
			int difference = 0;
			if (knapSize > 8) {
				difference = knapSize - Integer.toBinaryString(d).length();
			}
			if (knapSize < 8) {
				difference = knapSize - Integer.toBinaryString(d).length();
			}
			for (int i = 0; i < difference; i++) {
				if (i > 0) {
					bin2 += bin;
				} 
			}
			String binarySize = bin2 + Integer.toBinaryString(d);
			binary.add(binarySize);
		}
		System.out.println(binary);

		// Convert binary into char array // Compute cipher // Encryption
		ArrayList<Integer> cipher = new ArrayList<Integer>();
		for (String bB : binary) {
			char[] computeT = bB.toCharArray();
			int sumOfT = 0;
			for (int c = 0; c < computeT.length; c++) {
				if (c >= computeT.length) {
					System.out.println("Error: Number of knapsnack is shorter than msg for encryption");
					break;
				}

				if (computeT[c] == '1') {
					sumOfT += publicKey.get(c);
				}
			}
			cipher.add(sumOfT); // cipher text
		}
		System.out.println("Cipher Text :" + cipher);
	}

	// Decryption
	public void Decrypt() {

		System.out.println("Decrypt using private key");
		System.out.println("=========================");
		System.out.println("");
		ArrayList<Integer> cipher = new ArrayList<Integer>();
		while (true) {
			String snum = readString("Please enter Cypher text (eg. 5546 3243 34234 ) > ");
			ArrayList<String> strcipher = new ArrayList<String>(Arrays.asList(snum.split(" ")));
			try {
				for (String s : strcipher) {
					cipher.add(Integer.parseInt(s));
				}
				break;
			} catch (Exception e) {
				System.out.println("The input must follow this format (eg. 5546 3243 34234 )");
			}
		}
		byte[] decrypted_binary = new byte[knapSize]; // the decrypted message in binary
		StringBuilder sb = new StringBuilder();
		int counter = 1;

		for (Integer c : cipher) {
			BigInteger y = inverseW.multiply(BigInteger.valueOf(c)).mod(BigInteger.valueOf(modulusM));
			System.out.println("Compute Y" + counter++ + ": " + y);

			for (int i = privateKey.size() - 1; i >= 0; i--) {
				if (BigInteger.valueOf(privateKey.get(i)).compareTo(y) <= 0) { // found the largest element in w
																				// which is
																				// less than or equal to tmp
					y = y.subtract(BigInteger.valueOf(privateKey.get(i)));
					decrypted_binary[i] = 1;
				} else {
					decrypted_binary[i] = 0;
				}
			}

			// convert byte[] to string
			for (int i = 0; i < decrypted_binary.length; i++) {
				sb.append(decrypted_binary[i]);
			}
		}
		System.out.println("Decrypted String : " + new String(new BigInteger(sb.toString(), 2).toByteArray()));
	}
}
