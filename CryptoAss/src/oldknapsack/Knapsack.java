package oldknapsack;

//Merkle-Hellman Trapdoor knapsack encryption implimentation 
//Student Name: Abhi Jay Krishnan
//Student ID: 5025448
//written in java (jdk 1.8)
import java.util.*;
import java.math.BigInteger;
public class Knapsack {
	private String msg;
	private ArrayList <Integer> ctext;
	private ArrayList <Integer> secKey;
	private ArrayList <Integer> pubKey;
	private ArrayList <Integer> sol;
	private String ptext;
	private int secSize;
	private int p;
	private int w;
	private int inverse;
	public Knapsack()
	{
		secKey = new ArrayList<Integer>();
		pubKey = new ArrayList<Integer>();
		msg = new String();
		ctext = new ArrayList<Integer>();
		sol = new ArrayList<Integer>();
	}
	private String readString(String prompt) {
	    System.out.print(prompt);
	    return new java.util.Scanner(System.in).nextLine();
	  }
	private int readInt(String prompt) {
	    int input = 0;
	    boolean valid = false;
	    while (!valid) {
	      try {
	        input = Integer.parseInt(readString(prompt));
	        valid = true;
	      } catch (NumberFormatException e) {
	        System.out.println("*** Please enter an integer ***");
	      }
	    }
	    return input;
	}
	private int mod(int x, int y)
	{
	    int result = x % y;
	    if (result < 0)
	    {
	        result += y;
	    }
	    return result;
	}
	private static int gcdOf(int a, int b) {
	    BigInteger first = BigInteger.valueOf(a);
	    BigInteger second = BigInteger.valueOf(b);
	    BigInteger gcd = second.gcd(first);
	    return gcd.intValue();
	}
	private int ExndEuclid(long num1, long num2)
    {
        long x = 0, y = 1, a = 1, b = 0, temp;
        while (num2 != 0)
        {
            long q = num1 / num2;
            long r = num1 % num2;
            num1 = num2;
            num2 = r;
            temp = x;
            x = a - q * x;
            a = temp;
            temp = y;
            y = b - q * y;
            b = temp;            
        }
        return (int) a;
    }
	//Makes public key for alice 
	private void makePublicKey()
	{
		for(int num : secKey)
		{
			pubKey.add((num*w) % p);
		}
		System.out.println("");
		System.out.println(" Public Key (public key of Bob)  = " + pubKey);
		System.out.println("");
		
	}
	//Find the subset when we know the sum in a super increasing knapsack
	private void getAllSubsets(int[] elements, int i, int sum, Stack<Integer> currentSol) { 
	    //stop clauses:
	    if (sum == 0 && i == elements.length) 
	    	StackToList(currentSol);
	    //if elements must be positive, you can trim search here if sum became negative
	    if (i == elements.length) return;
	    //"guess" the current element in the list:
	    currentSol.add(elements[i]);
	    getAllSubsets(elements, i+1, sum-elements[i], currentSol);
	    //"guess" the current element is not in the list:
	    currentSol.pop();
	    getAllSubsets(elements, i+1, sum, currentSol);
	}
	//convert stack to array list
	private void StackToList(Stack <Integer> s)
	{
		for(int i: s)
		{
			sol.add(i);
		}
	}
	public void ReadInput()
	{
		secSize = readInt("Please enter the size of super-increasing knapsack: ");
		for(int i = 0;i < secSize; i++)
		{
			int n = readInt("Please enter the value a"+(i+1)+" of private key: ");
			secKey.add(n);
		}
		do
		{
			p = readInt("Please enter the modulus value (p) : ");
			w = readInt("Please enter the multiplier value (w) : ");
		}while(gcdOf(w,p) != 1);
		inverse = ExndEuclid((long) w, (long) p);
		makePublicKey();
	}
	public void Encrypt()
	{
		System.out.println("");
		System.out.println("Encrypt using public key");
		System.out.println("=========================");
		System.out.println("");
		msg = readString("Please enter a message > ");
		System.out.println("");
		System.out.println("Encrypting...");
		System.out.println("");
		ArrayList <Integer> loc = new  ArrayList <Integer>();
		String bin;
		for(int i = 0; i < msg.length(); i++)
		{
			int n = (int) msg.charAt(i);
			bin = Integer.toString(n,2);
			for(int j = 0; j < bin.length();j++)
			{
				if(bin.charAt(j) == '1')
				{
					loc.add(j + 1);
				}
			}
			int key = 0;
			if(pubKey.size() < bin.length()) {
				
				ArrayList <Integer> tempubKey = new ArrayList<Integer>();
				int p = 0;
				
				for(int t = 0; t < bin.length(); t++) {					
					if(p == pubKey.size())
						p = 0;
					tempubKey.add(pubKey.get(p++));
				}
				
				for(int m : loc)
				{
					key += tempubKey.get(m);
				}
				
				System.out.println(" Bigger Text = ");
			}
			if(pubKey.size() >= bin.length()) {
				System.out.println(" Samller Text = ");
				for(int m : loc)
				{
					key += pubKey.get(m);
				}
			}

			ctext.add(key);
			loc.clear();
		}
		System.out.println(" Cypher Text = " + ctext);
		System.out.println("");
	}
	public void Decrypt()
	{
		System.out.println("Decrypt using private key");
		System.out.println("=========================");
		System.out.println("");
		ArrayList<Integer> textc = new ArrayList<Integer>();
		while (true) {
			try {
		String snum = readString("Please enter Cypher text (eg. 5546 3243 34234 ) > ");
		System.out.println("");
		System.out.println("Decrypting...");
		ArrayList<String> sList = new ArrayList<String>(Arrays.asList(snum.split(" ")));

		for(String s : sList)
		{
			textc.add(Integer.parseInt(s));
		}
		break;
		
			}catch (NumberFormatException nfe) {
				
			}
		}
		int [] set = new int[secKey.size()]; 
		for (int i=0; i < set.length; i++)
	    {
	        set[i] = secKey.get(i).intValue();
	    }
		int sum; 
		ptext = new String();
		for(int t : textc)
		{
			sum = 0;
			sum = mod(mod(inverse,p) * mod(t,p),p);
			getAllSubsets(set, 0, sum, new Stack<Integer>());
			String b = "00000000";
			int i = 0;
			
			for(int n :secKey)
			{
				if(i == 8)
				{
					break;
				}
				if(sol.contains(n))
				{
					b = b.substring(0,i)+"1"+b.substring(i+1);
				}
				i++;
			}
			int charCode = Integer.parseInt(b, 2);
			try
			{
				String str = new Character((char)charCode).toString();
				ptext += str;
			}catch(Exception e)
			{
				System.out.println(e.toString());
			}
			
			sol.clear();
		}
		System.out.println("");
		System.out.println("Plain Text: " + ptext);
		System.out.println("");
	}
	
	public static void main(String [ ] args)
	{
		Knapsack knp = new Knapsack();
		//Read Input
		knp.ReadInput();
		//Encrypt Data
		knp.Encrypt();
		//Decrypt Data
		knp.Decrypt();
	}
}
