package MDES2;
import java.io.*;
import java.math.*;
import java.util.*;
/*****************************
* CSCI361 Part 1
* --------------------------
* File name: AffineCipher.java
* Author: Ang Wei Wen
* Student Number: 5710790
******************************/
class MDESf {

	public void echo(int key) {
		System.out.println(Integer.toBinaryString(3));
	}

	final int BLOCK_MAX = 15, BIT_MAX = 4;
	private static int KEY = 0;
	private int CURSub = 0;

	public MDESf(int key) throws Exception {
		if (key > 3) {
			throw new Exception("Key can not more than 3");
		}
		KEY = key;
	}

	/* Function to left rotate n by d bits */
	static int leftRotate(int n, int d, int MAX) {
		int l = new BigInteger(Integer.toString(MAX)).bitLength();
		int r = d % l;
		return ((n << r) | (n >>> (l - r))) & MAX;
	}

	/* Function to right rotate n by d bits */
	static int rightRotate(int n, int d, int MAX) {
		int l = new BigInteger(Integer.toString(MAX)).bitLength();
		int r = d % l;
		return ((n >>> r) | (n << (l - r))) & MAX;
	}

	void setcurSub(int block) throws Exception {

		if (block > BLOCK_MAX) {
			throw new Exception("Block is invalid");
		}
		CURSub = block;
	}

	int getcurSub() {
		return CURSub;
	}

	void rotateLeft() {
		// CURSub = (CURSub << 1) | (CURSub >> (BIT_MAX - 1));

		CURSub = leftRotate(CURSub, 1, 15);
	}

	void rotateRight() {
		// CURSub = (CURSub >> 1) | (CURSub << (BIT_MAX - 1));

		CURSub = rightRotate(CURSub, 1, 15);
	}

	void split(int num, int[] arr, int i) {
		int off = arr.length;
		int t = 0;
		int c = new BigInteger(Integer.toString(i)).bitCount();
		for (int j = 0; j < off; j++) {
			arr[j] = (num & i) >> t;
			t += c;
			i <<= c;
		}
	}

	int combinebinary(int num1, int num2, int place) {
		return (num1 << place) | (num2);
	}

	static int getKey(int keyIndex) throws Exception {
		int value = KEY;

		switch (keyIndex) {
		case 0:
			value >>= 1;
			break;
		case 1:
			value &= 1;
			break;
		default:
			throw new Exception("Invalid Key Index");
		}
		// Check the 2 possible outcomes
		if (value == 0) {
			return 0;
		} else if (value == 1) {
			return 7;
		} else {
			throw new Exception("Invalid Bit");
		}
	}

	int functionF(int[] ab, int Y) {
		int[] eX2 = new int[2];

		// Expand X
		split(ab[0], eX2, 1);
		int eX = (ab[0] << 1) + eX2[1];

		// sbox
		int J = sBox((eX ^ Y));

		// Rotate left Get Z
		int Z = leftRotate(J, 1, 3);
		return Z;
	}

	int sBox(int input) {
		if (input == 3)
			return 1;
		if (input == 6)
			return 2;
		if (input == 7)
			return 3;
		return 0;
	}

	void crypt(boolean e) throws Exception {
		rotateLeft();
		final int rounds = 2;
		// Initial Split
		int[] ab = new int[2];
		split(CURSub, ab, 3);

		if (e) { // encrypt
			for (int i = 0; i < rounds; i++) {

				// Get Key
				int key = getKey(i);

				// Function F
				int fResult = functionF(ab, key);

				// XOR
				xOr(ab, fResult);

			}
		} else { // decrypt
			for (int i = (rounds - 1); i >= 0; i--) {

				// Get Key
				int key = getKey(i);

				// Function F
				int fResult = functionF(ab, key);

				// XOR
				xOr(ab, fResult);
			}
		}

		// Swap the last split
		CURSub = combinebinary(ab[0], ab[1], 2);

		// Rotate Right
		rotateRight();

	}

	void xOr(int[] arr, int F) {
		int tempB1 = arr[0];
		arr[0] = arr[1] ^ F;
		arr[1] = tempB1;
	}
}

public class MDES {
	public static String leadingzero(int num, int lead) {
		return String.format("%0" + lead + "d", Integer.parseInt(Integer.toBinaryString(num)));
	}

	public int BlockCipher(int value) {

		return value;
	}

	public static void main(String[] args) {
		int key = 0;

		try {
			while (key <= 3) {
				for (int j = 0; j <= 15; j++) {
					int plainText = j;
					System.out.print("Key : " + leadingzero(key, 2) + " | E(" + leadingzero(plainText, 4) + ") ");
					MDESf mdes = new MDESf(key);

					// Encrypt
					mdes.setcurSub(plainText);
					mdes.crypt(true);

					int cipherText = mdes.getcurSub();
					System.out.print("= " + leadingzero(cipherText, 4) + " ");

					// Decrypt
					mdes.setcurSub(cipherText);
					mdes.crypt(false);

					int plainTextRevert = mdes.getcurSub();
					System.out.println(" | D(" + leadingzero(cipherText, 4) + ") = " + leadingzero(plainTextRevert, 4));

				}

				System.out.println();
				key++;
			}

		} catch (Exception msg) {
			System.out.println(msg);
		}
	}

}