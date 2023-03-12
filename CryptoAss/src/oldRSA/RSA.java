package oldRSA;
/*
 * CSCI361 Assignment 2
 * --------------------------
 * Programming language: Java
 * Language version: 8.0
 * File name: RSA.java
 * Author: Tan Bing Song
 * Student Number: 5364024
 * Task Four. RSA implementation
 */
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
    
public class RSA {
   private final static BigInteger one = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();
   private final static String DELIMITER = ", ";

   private static ArrayList<BigInteger> pkValue = new ArrayList<BigInteger>();
   private static ArrayList<BigInteger> sValue = new ArrayList<BigInteger>();
   private static ArrayList<BigInteger> mValue = new ArrayList<BigInteger>();
   
   private static BigInteger privateKey;
   private static BigInteger publicKey;
   private static BigInteger modulus;
   
   public static void displayMenu () {
		System.out.println("\n--------------------------------------------------------------------");
		System.out.println("            > RSA Implementation - Options Menu <                   ");
		System.out.println("--------------------------------------------------------------------");
		System.out.println( "1. Keygen" );
		System.out.println( "2. Sign" );
		System.out.println( "3. Verify" );
		System.out.println( "4. Exit" );
	}
   
   // generate an 64-bit (roughly) public and private key
   RSA(int N) {
	   // Generate random primes
	  System.out.print("\nGenerating p and q..."); 
      BigInteger p = BigInteger.probablePrime(N/2, random); 
      System.out.print("\np: " + p);
      BigInteger q = BigInteger.probablePrime(N/2, random);
      System.out.print("\nq: " + q);
      
      // Calculate products
      System.out.print("\n\np * q = N"); 
      modulus = p.multiply(q); // N   
      System.out.print("\n" + p + " * " + q + " = " + modulus); 
      
      System.out.print("\n\n(p-1)(q-1) = phi");
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
      System.out.print("\n(" + p + "-1) * (" + q + "-1) = " + phi); 
      
      // Generate public key (e) and private (d) key
      System.out.print("\n\nGenerating public key (e)"); 
      System.out.print("\n=> e > 1 "); 
      System.out.print("\n=> e coprime to phi (" + phi + ")" ); 
      Random rand = new SecureRandom();
      do 
    	  publicKey = new BigInteger(phi.bitLength(), rand); // e
      while (publicKey.compareTo(BigInteger.ONE) <= 0
          || publicKey.compareTo(phi) >= 0
          || !publicKey.gcd(phi).equals(BigInteger.ONE));
      System.out.print("\nGenerated e: " + publicKey);
      
      System.out.print("\n\nGenerating private key (d)");
      System.out.print("\n=> e * phi ^ -1 = d");
      privateKey = publicKey.modInverse(phi); // d     
      System.out.print("\nGenerated d: " + privateKey);
      
      // Output
      System.out.print("\n\n >>> Final Output <<<"); 
      System.out.print("\np: " + p);
      System.out.print("\nq: " + q);
      System.out.print("\nN: " + modulus);
      System.out.print("\ne: " + publicKey);
      System.out.print("\nd: " + privateKey);
      
      System.out.print("\n\nOutput public key (N, e) to pk.txt"); 
      // Output public key (N, e) to pk.txt
      try {  
        PrintWriter pkFileOut = new PrintWriter("pk.txt");
        pkFileOut.print(modulus + ", " + publicKey); // write to file 
        pkFileOut.close();
      } catch(IOException e) { // cannot open
          System.out.println("Failed to open pk.txt!");
      }
      System.out.print("\nCreated pk.txt."); 
      
      System.out.print("\n\nOutput private key (N, p, q, d) to sk.txt"); 
      // Output private key (N, p, q, d) to sk.txt
      try {  
          PrintWriter skFileOut = new PrintWriter("sk.txt");
          skFileOut.print(modulus + ", " + p + ", " + q + ", " + privateKey); // write to file 
          skFileOut.close();
      } catch(IOException e) { // cannot open
            System.out.println("Failed to open sk.txt!");
      }
      System.out.print("\nCreated sk.txt."); 
   }
   
   public static void verificationMenu () {
	   System.out.println("\n--------------------------------------------------------------------");
	   System.out.println("            > Verification <                                        ");
	   System.out.println("--------------------------------------------------------------------");
	   
		System.out.println( "1. Take the public key from pk.txt." );
		System.out.println( "2. Take signature S (a positive integer smaller than N) from sig.txt." );
		System.out.println( "3. Take message M." );
		System.out.println( "4. Verify" );
		System.out.println( "5. Exit" );
   }
   
   public static void verify () {
	   int optionNum;
 	   boolean ended = false;
       
	   do {
 			do {
 				verificationMenu(); // display menu
 				optionNum = Keyboard.readInt("\nPlease enter a number: ");
 			} while (optionNum < 1 && optionNum > 3 && optionNum != 4);
 			
 			// 1. Take the public key from pk.txt.
 			if (optionNum == 1) {
 				File inputFile = new File("pk.txt");
 				ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();
 				try	{
 					Scanner scanner = new Scanner(inputFile);
 					scanner.useDelimiter(DELIMITER);
 					
 					System.out.print("\nReading pk.txt...");
 					while(scanner.hasNext()) {
 						String value = scanner.next();
 						BigInteger num = new BigInteger(value);
 						numbers.add(num);
 					}
 					scanner.close();
 					
 					// take public key from pk.txt
 					BigInteger publicKey = numbers.get(1);
 					
 					// push value to vector	
 					if (pkValue.size() != 1) {
 						pkValue.clear();
 						pkValue.add(publicKey);
 					} 
 					
 					System.out.print("\nPublic key found: " + publicKey);
 					System.out.print("\nTaken public key from pk.txt.");

 				} catch (FileNotFoundException e) {
 					e.printStackTrace();
 				}
 			// 2. Take signature S (a positive integer smaller than N) from sig.txt.
 			} else if (optionNum == 2) {
 				File inputFile = new File("sig.txt");
 				ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();
 				try	{
 					Scanner scanner = new Scanner(inputFile);
 					scanner.useDelimiter(DELIMITER);
 					
 					System.out.print("\nReading sig.txt...");
 					while(scanner.hasNext()) {
 						String value = scanner.next();
 						BigInteger num = new BigInteger(value);
 						numbers.add(num);
 					}
 					scanner.close();
 					
 					// take public key from pk.txt
 					BigInteger S = numbers.get(0);
 					
 					// push value to vector		
 					if (sValue.size() != 1) {
 						sValue.clear();
 						sValue.add(S);
 					}
 					
 					System.out.print("\nS found: " + S);
 					System.out.print("\nTaken signature S from sig.txt.");
 				} catch (FileNotFoundException e) {
 					e.printStackTrace();
 				}
 			// 3. Take message M from mssg.txt
 			} else if (optionNum == 3) {
 				File inputFile = new File("mssg.txt");
 				ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();
 				try	{
 					Scanner scanner = new Scanner(inputFile);
 					scanner.useDelimiter(DELIMITER);
 					
 					System.out.print("\nReading mssg.txt...");
 					while(scanner.hasNext()) {
 						String value = scanner.next();
 						BigInteger num = new BigInteger(value);
 						numbers.add(num);
 					}
 					scanner.close();
 					
 					// take public key from pk.txt
 					BigInteger M = numbers.get(0);
 					
 					// push value to vector
 					if (mValue.size() != 1) {
 						mValue.clear();
 						mValue.add(M);
 					}

 					System.out.print("\nM found: " + M);
 					System.out.print("\nTaken message M from mssg.txt.");
 				} catch (FileNotFoundException e) {
 					e.printStackTrace();
 				}
 				
 			} else if (optionNum == 4 && pkValue.size() == 1 && sValue.size() == 1 && mValue.size() == 1) {	
 				BigInteger publicKey = pkValue.get(0);
 	 			BigInteger S = sValue.get(0);
 	 			BigInteger M = mValue.get(0);
 	 			
 	 			System.out.print("\nPublic Key (e): " + publicKey);
 	 			System.out.print("\nSignature S: " + S);
 	 			System.out.print("\nMessage M: " + M);
 	 			
 	 			System.out.print("\n\nVerifying... ");
 	 			BigInteger calculatedM = encrypt(S); // M = S^e (mod N)
 	 			
 	 			System.out.print("\n\nS ^ e (mod N) ");
 	 			System.out.print("\n" + S + " ^ " + publicKey + " + (mod + " + modulus + ") ");
 	 			
 	 			// Output (display) verification result "True" or "False"
 	 			System.out.print("\nCalculated Message: " + calculatedM);
 	 			System.out.print("\nOriginal Message: " + M);
 	 			
 	 			System.out.print("\n\nVerification result: ");
 	 			if (calculatedM.equals(M)) {
 	 				System.out.print("True");
 	 			} else {
 	 				System.out.print("False");
 	 			}
 	 		
 			} else if (pkValue.size() != 1 || sValue.size() != 1 || mValue.size() != 1) {
 	 			System.out.print("\nEnsure that you have retrieved public key, signature and message from the files!");
 	 			System.out.print("\nSelect option 1, 2, 3 THEN 4 (to verify)!");
 	 				
 			} else if (optionNum == 5) {
 				ended = true;
 			}		
 	  } while(!ended);	  
   }
   
   public static BigInteger encrypt(BigInteger message) {
      return message.modPow(publicKey, modulus); // m^e mod n 
   }

   public static BigInteger decrypt(BigInteger encrypted) {
      return encrypted.modPow(privateKey, modulus); // c^d mod n
   }

   public static void sign (int N) {
	File inputFile = new File("sk.txt");
	
	ArrayList<BigInteger> numbers = new ArrayList<BigInteger>();
	try	{
		Scanner scanner = new Scanner(inputFile);
		scanner.useDelimiter(DELIMITER);
		
		System.out.print("\nReading sk.txt...");
		while(scanner.hasNext()) {
			String value = scanner.next();
			BigInteger num = new BigInteger(value);
			numbers.add(num);
		}
		scanner.close();
		
		// take modulus from sk.txt
		modulus = numbers.get(0);
		
		// take private (secret) key from sk.txt
		privateKey = numbers.get(3);
		System.out.print("\nPrivate key found: " + privateKey);
		System.out.print("\nTaken private key from sk.txt.");
		
		// Output a positive integer smaller than N to mssg.txt
		// create positive integer smaller than N
		BigInteger M = new BigInteger(N-1, random);
		try {  
			  System.out.print("\n\nGenerated a positive integer smaller than N as message.");
			  System.out.print("\nMessage (M): " + M);	
			  System.out.print("\nWriting message to mssg.txt...");		
	          PrintWriter mssgFileOut = new PrintWriter("mssg.txt");
	          mssgFileOut.print(M); // write to file 
	          System.out.print("\nCompleted writing message to mssg.txt");		
	          mssgFileOut.close();
	    } catch(IOException e) { // cannot open
	          System.out.println("Failed to open mssg.txt!");
	    }
		
		// Output the corresponding signature
		// S = Md (mod N) into another file sig.txt.		
		BigInteger S = decrypt(M); 
		System.out.print("\n\nS = (M ^ d) mod N");
		System.out.print("\n(" + M + " ^ " + privateKey + ") mod " + modulus);
		System.out.print("\nS: " + S);
			
		try {  
			  System.out.print("\nWriting signature to sig.txt...");	
			  PrintWriter mssgFileOut = new PrintWriter("sig.txt");
			  mssgFileOut.print(S); // write to file 
			  System.out.print("\nCompleted writing signature to sig.txt");	
			  mssgFileOut.close();
		} catch(IOException e) { // cannot open
		      System.out.println("Failed to open sig.txt!");
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
   }
   
   public static void main(String[] args) {
	   	int N = 64; // 64 bits
  		int optionNum;
  		boolean ended = false;
  		
  		do {
  			do {
  				displayMenu (); // display menu
  				optionNum = Keyboard.readInt("\nPlease enter a number: ");
  			} while (optionNum < 1 && optionNum > 3 && optionNum != 4);
  	
  			// 1. Keygen
  			if (optionNum == 1) {
  				RSA key = new RSA(N); 
  				// 2. Sign
  			} else if (optionNum == 2) {
  				sign(N); 
  				// 3. Verify
  			} else if (optionNum == 3) {
  				verify();
  				// 4. Exit
  			} else if (optionNum == 4) {
  				System.out.println("--------------------------------------------------------------------------------");
  				System.out.println("	   Have a nice day!                                                         ");
  				System.out.println("--------------------------------------------------------------------------------");
  				ended = true;
  			}
  	  } while(!ended);
   }
   
   
   public static BigInteger RandomRange(BigInteger min, BigInteger max) {
	    BigInteger bigInteger1 = max.subtract(min);
	    SecureRandom rnd = new SecureRandom();
	    int maxNumBitLength = max.bitLength();

	    BigInteger aRandomBigInt;

	    aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
	    if (aRandomBigInt.compareTo(min) < 0)
	      aRandomBigInt = aRandomBigInt.add(min);
	    if (aRandomBigInt.compareTo(max) >= 0)
	      aRandomBigInt = aRandomBigInt.mod(bigInteger1).add(min);
	    return aRandomBigInt;
   }
}
