package hashfunction;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;
import java.util.Base64;

public class asecbc {

	private static final String ALGO = "AES";
	private static final String ALGOMODE = "AES/CBC/PKCS5Padding";
	//"AES/CBC/NoPadding"
	private static final byte[] keyValue = "TheBestSecretKey".getBytes();
	private static final byte[] initVector = "encryptionIntVec".getBytes();
	/**
	 * Encrypt a string with AES algorithm.
	 *
	 * @param data is a string
	 * @return the encrypted string
	 */
	public static byte[] encrypt(String data) throws Exception {
		IvParameterSpec iv = new IvParameterSpec(initVector);
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGOMODE);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getBytes());
		


	//	byte[] base64DecodedData = Base64.getEncoder().encode(encVal);
		
		

	//	System.out.println("base64DecodedData" + Arrays.toString(base64DecodedData));
		
		
		
		return encVal;

	//	System.out.println(new String(encVal));
		
	//	return new BASE64Encoder().encode(encVal);
		
		//	return new String(encVal);
	}

	/**
	 * Decrypt a string with AES algorithm.
	 *
	 * @param encryptedData is a string
	 * @return the decrypted string
	 */
	public static String decrypt(String encryptedData) throws Exception {
		IvParameterSpec iv = new IvParameterSpec(initVector);
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGOMODE);
		c.init(Cipher.DECRYPT_MODE, key, iv);
		

		byte[] base64DecodedData = Base64.getDecoder().decode(encryptedData);
		
		
		//	System.out.println("base64DecodedData" + Arrays.toString(base64DecodedData));
		
		
		byte[] decValue = c.doFinal(base64DecodedData);
		return new String(decValue);
	}

	/**
	 * Generate a new encryption key.
	 */
	private static Key generateKey() throws Exception {
		return new SecretKeySpec(keyValue, ALGO);
	}

	public static void main(String[] args) {
		
		
		
byte[] base64DecodedData = Base64.getDecoder().decode("1111");
try {
	
    String utf16String = "1111"; // Unicode character U+1111
    byte[] utf16Bytes = utf16String.getBytes(StandardCharsets.UTF_16);
    String base64String = Base64.getEncoder().encodeToString(utf16Bytes);
    System.out.println(base64String); // Output: "DQASAQ=="
	
	
	
	
	String value = new String(base64DecodedData, "UTF-16");
	
	String base64Decoded = new String(base64DecodedData, StandardCharsets.UTF_16);

	
	byte[] bytes = "1111".getBytes("UTF-16");
	System.out.println("value" + base64Decoded);	System.out.println("value" + value);
} catch (UnsupportedEncodingException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
		
		System.out.println("base64DecodedData" + Arrays.toString(base64DecodedData));
		
		try {
			System.out.println(encrypt("test"));
			
			
			System.out.println(encrypt("test"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void test(){
		Cipher cipher = null;
		String sKey = "";
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES/CBC/PKCS5Padding");
			kgen.init(128, new SecureRandom(sKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		} catch (NoSuchPaddingException e) {
			System.out.println(e);
		} catch (InvalidKeyException e) {
			System.out.println(e);
		}
	}

}
