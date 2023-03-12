package ofb_TEA;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Scanner;


public class ofb_TEA {
	
	//private static int _key[];  // The 128 bit key.
	static byte key[] = new BigInteger("12d68700", 16).toByteArray();
	private static byte _keyBytes[];  // original key as found
	private static int _padding;      // amount of padding added in byte --> integer conversion.
	private static byte opt[];
	static int counter = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long seed = 125965687;
		//byte key[] = new BigInteger("39e858f86df9b909a8c87cb8d9ad599", 16).toByteArray();
	     
	      
	    String plain_text = "plaintext.txt";
		String cipher_text = "ciphertext.txt";
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose the Operation you want to perform:");
		System.out.println("1. Encryption");
		System.out.println("2. Decryption");
		int choice = sc.nextInt(); 
	
		switch (choice) {
		case 1:
			ofb_cipher(encipher(getIV(seed),key), plain_text, cipher_text);
			break;
			
		case 2:
			ofb_decipher(encipher(getIV(seed),key),cipher_text, plain_text);
			break;
			
		default:
			break;
		}
		
		sc.close();
	}
	
	//This function will be used for encryption 
	public static void ofb_cipher(byte[] b ,String text, String text2){
		
		int len;
		String str = null;
		boolean flag;
		flag=true;
		File plain_text = new File(text);
		File cipher_text = new File(text2);
		StringBuffer sb = null;
		String s = null;
		String s2= null;
		String s3 = null;
		String line = null;
		
		try {
            // FileReader reads text files in the default encoding.
			FileInputStream fip = new FileInputStream(plain_text); 

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(new InputStreamReader(fip));
            
            FileOutputStream fout = new FileOutputStream(cipher_text);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(fout));
            
            //The whole string will be divided into 64 bit block and will send to TEA encryption function
            //for encryption and encrypted text will be stored in the cipherText.txt file
            while((line = bufferedReader.readLine()) != null) {
            	
            	sb = new StringBuffer (line);
            	s = sb.toString();
            	len = s.length();
            	sb.setLength(8);
            	
            	//The string will be divided into exactly 8 bytes blocks
            	while((len % 8)>=0){
            		
            		//For each 8 bytes block the encryption will be done
        			if(len>=8){
        				str = s.substring(0, 8);
        				s2 = encipher(b, key).toString();
    					for(int i = 0; i<str.length();i++){
        					sb.setCharAt(i, (char)(str.charAt(i) ^ s2.charAt(i)));
        					
        				}
        				s3 = sb.toString();
    	                s3 = URLEncoder.encode(s3,"UTF-8");
        				bufferedWriter.write(s3);
        				s = s.substring(8,len);
        				len-=8;
        			}
        			
        			else if(len<0){
        				break;
        			}
        			
        			//If the block size is less than 8 then the encryption will be done
        			else{
        				str = text.substring(0, len);
        				s2 = encipher(b, key).toString();
        				
    					for(int i = 0; i<str.length();i++){
        					sb.setCharAt(i, (char)(str.charAt(i) ^ s2.charAt(i)));
        					flag=false;
        				}
    					//This flag is required if the remainig block size is 0 bytes then it will not 
    					//write it to the file 
    					if(flag==false)
    					{
	        				s3 = sb.toString();
	    	                s3 = URLEncoder.encode(s3,"UTF-8");
	        				bufferedWriter.write(s3);
    					}
        				len = -1;
        			}
        			
        		}
            } 

            // Always close files.
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                text + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + text + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }	
	}
	
	//This function will be used for decryption 
	public static void ofb_decipher(byte[] b ,String text, String text2){
		int len;
		String str = null;
		boolean flag;
		flag=true;
		File plain_text = new File(text2);
		File cipher_text = new File(text);
		StringBuffer sb = null;
		String s = null;
		String s2= null;
		String s3 = null;
		String line = null;
		
		try {
            // FileReader reads text files in the default encoding.
			FileInputStream fip = new FileInputStream(cipher_text); 

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(new InputStreamReader(fip));
            
            FileOutputStream fout = new FileOutputStream(plain_text);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(fout));
            
          //Reading the file and logic will start here
            while((line = bufferedReader.readLine()) != null) {
            	s = URLDecoder.decode(line,"UTF-8");
            	sb = new StringBuffer (s);
            	s = sb.toString();
            	len = s.length();
            	sb.setLength(8);
            	
            	//The string will be divided into exactly 8 bytes blocks
            	while((len % 8)>=0){
            		
            		//For each 8 bytes block the encryption will be done
        			if(len>=8){
        				str = s.substring(0, 8);
        				s2 = encipher(b, key).toString();
    					for(int i = 0; i<str.length();i++){
        					sb.setCharAt(i, (char)(str.charAt(i) ^ s2.charAt(i)));
        					
        				}
        				s3 = sb.toString();
        				bufferedWriter.write(s3);
        				s = s.substring(8,len);
        				len-=8;
        			}

        			else if(len<0){
        				break;
        			}
        			
        			//If the block size is less than 8 then the decryption will be done
        			else{
        				str = text.substring(0, len);
        				s2 = encipher(b, key).toString();
    					for(int i = 0; i<str.length();i++){
        					sb.setCharAt(i, (char)(str.charAt(i) ^ s2.charAt(i)));
        					flag=false;
        				}
    					//This flag is required if the remainig block size is 0 bytes then it will not 
    					//write it to the file
    					if(flag==false)
    					{
	        				s3 = sb.toString();
	    					System.out.println(s3);
	        				bufferedWriter.write(s3);
    					}
        				len = -1;
        			}
        		}
            } 

            // Always close files.
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                text + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + text + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }	
	}
	
	public static byte [] encipher(byte v[], byte _key[]){
	      byte y=v[0];
	      byte z=v[1];
	      int sum=0;
	      int delta=0x9E3779B9;
	      int a=_key[0];
	      int b=_key[1];
	      int c=_key[2];
	      int d=_key[3];
	      int n=32;

	      while(n-->0)
	      {
	         sum += delta;
	         y += (z << 4)+a ^ z+sum ^ (z >> 5)+b;
	         z += (y << 4)+c ^ y+sum ^ (y >> 5)+d;
	      }

	      v[0] = y;
	      v[1] = z;

	      return v;
	}
	
	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}

	public static int byteToInt(byte[] buf, int pos) {
		int i = 0;

		i += unsignedByteToInt(buf[pos++]) << 24;
		i += unsignedByteToInt(buf[pos++]) << 16;
		i += unsignedByteToInt(buf[pos++]) << 8;
		i += unsignedByteToInt(buf[pos++]) << 0;

		return i;
	}

	public static byte[] intToByteArray(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
				: integer)) / 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++) {
			byteArray[3 - n] = (byte) (integer >>> (n * 8));
		}

		return (byteArray);
	}

	
	public static byte[] getIV(long messageNo) {
		  ByteBuffer bb = ByteBuffer.allocate(16);
		  bb.putLong(0, messageNo);
		  return bb.array();
	}

}