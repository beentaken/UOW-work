package logic;
import java.math.BigInteger;
import java.security.SecureRandom;

public class TaskFour {


//	h a number less than p-1 such that g= hp-1/q mod p > 1
// u < q
// y=gu mod p
//	Public parameters: p, q, g. (Multiple people could use these).
//	Private key: u.
//	Public key: y.
	static BigInteger P, Q, G, Y;
	private BigInteger U;

	private static int getRandomNInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return new SecureRandom().nextInt((max - min) + 1) + min;
	}
	
	public static void main(String[] args) {

		genkey();
	}

	public static void genkey() {
//		q a 160-bit prime is chosen
		Q = BigInteger.probablePrime(160, new SecureRandom());
		//Q.bitLength();
		
		while(!Q.isProbablePrime(1))
		{
			Q = BigInteger.probablePrime(160, new SecureRandom());
		}
		System.out.println("Q " + Q);
		P =  checkP(Q);	
	}
	
	private static boolean checkmultiple(BigInteger m, BigInteger f) {
		if(m.remainder(f).equals(BigInteger.ZERO))
			return false;
		return true;
	}
	 
	private static BigInteger checkP(BigInteger q) {
		
		BigInteger p = q;
		System.out.println("q : " + q);
		while(!Q.add(BigInteger.ONE).isProbablePrime(1)) {
	//	p = p.add(val);
		System.out.println("p : " + p);
		}
		
//		int multi64 = getRandomNInRange(512, 1024);
//		512-1024 bits long with the length a multiple of 64. q is a factor of p-1.
//		q much be smaller than p-1 because it is a factor
//		while(checkmultiple(BigInteger.valueOf(multi64), BigInteger.valueOf(64)))
//		{
//			multi64 = getRandomNInRange(512, 1024);
//		}
//		System.out.println("multi64 " + multi64);
////		p a prime. 
//		BigInteger p = BigInteger.probablePrime(multi64, new SecureRandom());
//		// (q divides (p-1)) is prime q is a factor of p-1.
//		while(checkmultiple(p.subtract(BigInteger.ONE), q))
//		{
//			System.out.println("remainder " + p.subtract(BigInteger.ONE).remainder(q));
//			System.out.println("compareTo " + p.subtract(BigInteger.ONE).remainder(q));
//			p = BigInteger.probablePrime(multi64, new SecureRandom());
//		}
//		
//		System.out.println("P " + p); 
		return p;
	}
}
