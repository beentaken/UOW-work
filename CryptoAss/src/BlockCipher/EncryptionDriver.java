/*
 * EncryptionDriver.java
 * Adam Al-Jumaily
 */
package BlockCipher;

import java.util.Scanner;

/**
 *
 * @author adam
 */
public class EncryptionDriver {

    public static Scanner kbd = new Scanner(System.in);

    public static void main(String[] args) {
        CryptAlgX cryptAlg = new CryptAlgX();
        System.out.println("Welcome to CAX (Cryptographic Algorithm X.");
        int key;
        
        while (true) {
            System.out.println("Press 'K' for a list of keys.");
            System.out.println("Press 'E' to encrypt a message.");
            System.out.println("Press 'D' to decrypt a message.");
            System.out.println("Press 'Q' to quit.");
            char input1 = kbd.nextLine().charAt(0);
            switch (input1) {
                case 'k':
                case 'K':
                    cryptAlg.displayKeys();
                    break;
                case 'e':
                case 'E':
                        System.out.print("Please enter the key to use for "
                                + "this encryption:  ");
                        key = kbd.nextInt();
                        kbd.nextLine();
                        System.out.println("Enter the message to be encrypted "
                                + "below.");
                        String plainText = kbd.nextLine();
                        cryptAlg.encryptPlainText(plainText, key);
                        System.out.println("Your encrypted message is below.");
                        System.out.println(cryptAlg.getCipherText());
                        cryptAlg = new CryptAlgX();
                    break;
                case 'd':
                case 'D':
                        System.out.print("Please enter the key to use for "
                                + "this decryption:  ");
                        key = kbd.nextInt();
                        kbd.nextLine();
                        System.out.println("Enter the message to be decrypted "
                                + "below.");
                        String cipherText = kbd.nextLine();
                        try{
                        cryptAlg.decryptCipherText(cipherText, key);
                        System.out.println("Your decrypted message is below.");
                        System.out.println(cryptAlg.getPlainText());
                        } catch (Exception e){
                            System.out.println("Decryption failed. Invalid " +
                                    "key-ciphertext pair.");
                        } finally {
                            cryptAlg = new CryptAlgX();
                        }
                    break;
                case 'q':
                case 'Q':
                    System.exit(0);
            }
        }
    }
}