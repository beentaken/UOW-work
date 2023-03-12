package hashfunction;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

public class EncodeUTF16 {
	
	static String DeBase64Unicode()  {
	      String base64String = "MTExMQ==";
	        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
	        
	  
	        
	        String utf16String = new String(decodedBytes, StandardCharsets.UTF_16);
		System.out.println(utf16String); 
		return utf16String;
	}
	
	static void EnBase64Unicode(String utf16String)  {
		  byte[] utf16Bytes = utf16String.getBytes(StandardCharsets.UTF_16);
		  

		  
	        String base64String = Base64.getEncoder().encodeToString(utf16Bytes);
	        System.out.println(base64String); 
	}

	static void Base64Unicode()  {
		String str = "Hello, world!";
	    byte[] bytes = str.getBytes(StandardCharsets.UTF_16);
	    String str2 = new String(bytes, StandardCharsets.UTF_16);
	    System.out.println(str2);    
	}

	
	static void Base64Unicodes()  {
		String str = "MTExMQ==";
		
		byte[] bytes = str.getBytes(StandardCharsets.UTF_16);
		String base64String = Base64.getEncoder().encodeToString(bytes);
		
		String str2 = new String(bytes, StandardCharsets.UTF_16);
		 byte[] decodedBytes = Base64.getDecoder().decode(str);

	 
	    
	    
	    System.out.println(str2);    
	}
	
	static void Base64Unicode2()  {
	       String str = "Hello, world!";
	        
	        // Encode the string in base64
	        String base64Str = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_16));
	        System.out.println(base64Str);    // Output: SGVsbG8sIHdvcmxkIQ==

	        // Decode the base64 string back to Unicode
	        byte[] bytes = Base64.getDecoder().decode(base64Str);
	        String str2 = new String(bytes, StandardCharsets.UTF_16);
	        System.out.println(str2);    // Output: Hello, world!
	}
	
	
	public static void main(String[] args) {
		EncodeUTF16.Base64Unicode2() ;
		Base64Unicodes() ;
		//EncodeUTF16.EnBase64Unicode(EncodeUTF16.DeBase64Unicode());
	}
}
