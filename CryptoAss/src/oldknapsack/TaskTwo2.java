package oldknapsack;

/*****************************
 * CSCI361 Assignment 2 
 * File name: Knapsack.java 
 * Author:Ang Wei Wen 
 * Student Number: 5710790
 ******************************/

import java.math.BigInteger;
import java.util.*;

public class TaskTwo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		knaps();
	}
	
	static int inputstring(String title) {
	     Scanner sc = new Scanner(System.in);
	     System.out.print(title);
	        while (true)
	            try {
	                return Integer.parseInt(sc.nextLine());
	            } catch (NumberFormatException nfe) {
	                System.out.print("Must be Whole number: ");
	            }
	}

	static void knaps() {
		// Size and Values of AU
		int totalValue = 0, knapSize = 0;
		while(knapSize != 8) {
			 knapSize = inputstring("Enter size of knapsack ( MUST BE 8 ): ");
		}
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < knapSize; i++) {
			System.out.println("Total Sum of value A: " + totalValue);
			int valueA = inputstring("Enter value of A" + (i + 1) + ": ");
			while (i > 0 && valueA <= totalValue) {
				System.out.println("Value of A" + (i + 1) + " must be bigger than total sum of value A");
				valueA = inputstring("Enter value of A" + (i + 1) + ": ");
				if (valueA > totalValue) {
					break;
				}
			}
			totalValue += valueA;
			a.add(valueA);
		}
		System.out.println("All values of A inputed.");
		System.out.println("Values of A: " + a);
		System.out.println("Total of A: " + totalValue);
		
		// Choose modulus m.
		int modulusM = 0;
		while (modulusM < totalValue) {
			modulusM = inputstring("Modulus M must be a random integer bigger than total Sum of value A.\nEnter modulus M: ");
		} // end of while-loop

		System.out.println("Value of prime P : " + modulusM);

		// Choose mutiplier W
		System.out.println("Choose W , where W is random integer co-prime to p and gcd(m,w) = 1");
		int mutiplierW = 0;
		BigInteger gcd = BigInteger.valueOf(modulusM).gcd(BigInteger.valueOf(mutiplierW));
		
		while (gcd.intValue() != 1) {
			mutiplierW = inputstring("Enter your W value : ");
			gcd = BigInteger.valueOf(modulusM).gcd(BigInteger.valueOf(mutiplierW));
			if (gcd.intValue() == 1) {
				break;
			}
			System.out.println("gcd(m,w) is not equals to 1");
		}

		System.out.println("GCD (" + modulusM + "," + mutiplierW + ") : " + gcd);
		System.out.println("Value of W : " + mutiplierW);

		BigInteger inverseW = BigInteger.valueOf(mutiplierW).modInverse(BigInteger
				.valueOf(modulusM)); // Inverse of W
		System.out.println(mutiplierW + "^-1 mod " + modulusM + " : " + inverseW
				+ "(W^-1)");

		// Compute public key
		ArrayList<Integer> b = new ArrayList<Integer>(); // public key value
		for (int aA : a) {
			BigInteger bB = (BigInteger.valueOf(mutiplierW).multiply(BigInteger.valueOf(aA))).mod(BigInteger.valueOf(modulusM));
			// System.out.println(bB);
			b.add(bB.intValue());
		}
		System.out.println("Public key list : " + b);

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
			if (Integer.toBinaryString(d).length() != knapSize) {
				difference = knapSize
						- Integer.toBinaryString(d).length();
			}
			for (int i = 0; i < difference; i++) {
				if (i > 0) {
					bin2 += bin;
				} else {
					bin2 = bin;
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
					sumOfT += b.get(c);
				}
			}
			cipher.add(sumOfT); // cipher text
		}

		System.out.println("Cipher Text :" + cipher);

		// Decryption
        byte[] decrypted_binary = new byte[knapSize];  // the decrypted message in binary
        StringBuilder sb = new StringBuilder();
		int counter = 1;
        
		for (Integer c : cipher) {
			BigInteger y = inverseW.multiply(BigInteger.valueOf(c)).mod(BigInteger.valueOf(modulusM));
			System.out.println("Compute Y" + counter++ + ": " + y);

	        for (int i = a.size() - 1; i >= 0; i--) {
	            if (BigInteger.valueOf(a.get(i)).compareTo(y) <= 0) {  // found the largest element in w which is less than or equal to tmp
	                y = y.subtract(BigInteger.valueOf(a.get(i)));
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
