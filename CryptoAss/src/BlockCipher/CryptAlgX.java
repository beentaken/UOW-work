/*
 * CryptAlgX.java
 * Adam Al-Jumaily
 *
 * Description and Functionality:
 *
 * Uses generated key to perform a simple, constant arithmatic operation on each
 * character and then performs a bitwise XOR operation on the result to produce 
 * ciphertext. Decryption is simply a bitwise XOR with the known key and then a
 * arithmatic operation. 
 * 
 * Key sizes are 16 bits long (2 bytes) so there are 2^16 possible keys. 
 * Our cipher has block size 16, so we operate on 2 characters at a time.
 * We pad to the left with leading zeros for consistency in bitstreams. 
 * We insert the original message length encrypted as the first 2 bytes. 
 */
package BlockCipher;

import java.util.Random;

public class CryptAlgX {

    private Random rand;
    private String plainText;
    private String cipherText;
    private int[] keys = new int[10];

    public CryptAlgX() {
        plainText = "";
        cipherText = "";
        generateKeys();
    }

    // Outputs a list of keys and their corresponding letters.
    public void displayKeys() {
        String key;
        for (int i = 0; i < 10; i++) {
            key = String.format("%05d", keys[i]);
            System.out.print(String.format("%-4s", (i + 1) + ")"));
            System.out.print(key + "\n");
        }
    }

    // Encrypts a single line of plaintext.
    public void encryptPlainText(String plainText, int key) {
        // Create a 16-bit binary string of our encryption key.
        String keyString = String.format("%16s",
                Integer.toBinaryString(key)).replace(' ', '0');
        
        // Add a 16bit representation of the plaintext original length to 
        // beginning of the binary string.
        String binaryPlainText = String.format("%16s",
                Integer.toBinaryString(plainText.length())).replace(' ', '0');
        
        // For each character in the pt, concat its binary rep to the string
        for (int i = 0; i < plainText.length(); i++) {
            binaryPlainText = binaryPlainText.concat((String.format("%8s",
                    Integer.toBinaryString((int) plainText.charAt(
                            i)))).replace(' ', '0'));
        }
        
        // If length of pt is odd, concat an empty byte
        if (plainText.length() % 2 != 0) {
            binaryPlainText = binaryPlainText.concat("00000000");
        }
        // Create an encrypted bitstream by XORing 16 bit blocks with the key.
        String temp;
        String cipherBinaryString = "";
        for (int i = 0; i < binaryPlainText.length(); i += 16) {
            temp = "";
            for (int j = i; j < i + 16; j++) {
                temp = temp.concat(Integer.toString(keyString.charAt(j - i) ^ binaryPlainText.charAt(j)));
            }
            cipherText = cipherText.concat(String.format("%3s", Integer.toString(Integer.parseInt(temp.substring(0, 8), 2))).replace(' ', '0'));
            cipherText = cipherText.concat(String.format("%3s", Integer.toString(Integer.parseInt(temp.substring(8, 16), 2))).replace(' ', '0'));
            cipherBinaryString = cipherBinaryString.concat(temp);
        }
    }

    // Returns the current CipherText
    public String getCipherText() {
        return cipherText;
    }

    // Decrypts ciphertext using a desired known key.
    public void decryptCipherText(String cipherText, int key) 
            throws java.lang.ArrayIndexOutOfBoundsException{
        // Create the 16 bit key bitstream
        String keyString = String.format("%16s",
                Integer.toBinaryString(key)).replace(' ', '0');
        
        // Use every three numbers to create a byte and create a bitstream.
        String cipherTextBinary = "";
        for(int i = 0; i < cipherText.length(); i+=3){
            cipherTextBinary = cipherTextBinary.concat(String.format("%8s", 
                    Integer.toBinaryString(Integer.parseInt(
                            cipherText.substring(i, i+3))), 2).replace(' ', '0'));
        }
        
        // XOR 16 bit blocks with the key to produce the unencrypted bitstream.
        String temp;
        String unencryptedBitStream = "";
        for (int i = 0; i < cipherTextBinary.length(); i += 16) {
            temp = "";
            for (int j = i; j < i + 16; j++) {
                temp = temp.concat(Integer.toString(keyString.charAt(j - i) ^ cipherTextBinary.charAt(j)));
            }
            unencryptedBitStream = unencryptedBitStream.concat(temp);
        }
        
        // Obtain the original message length from the first 16-bit word then
        // remove it.
        int messageLength = Integer.parseInt(unencryptedBitStream.substring(
            0, 16), 2);
        unencryptedBitStream = unencryptedBitStream.substring(16);
        
        // If the message is odd, remove the last empty byte.
        if(messageLength%2 != 0){
            unencryptedBitStream = unencryptedBitStream.substring(0, 8*messageLength);
        }
        
        // Iterate through each byte and create the plaintext by converting the
        // byte to an ascii character.
        for(int i = 0; i < messageLength; i++){
            plainText = plainText.concat(Character.toString((char)Integer.parseInt(
                    unencryptedBitStream.substring(i*8, i*8+8), 2)));
        }
    }

    // Returns the current plaintext
    public String getPlainText() {
        return plainText;
    }

    // Generates a set of keys that the user can choose to encrypt with.
    private void generateKeys() {
        rand = new Random();
        for (int i = 0; i < 10; i++) {
            keys[i] = rand.nextInt(65536) + 1;
        }
    }

}