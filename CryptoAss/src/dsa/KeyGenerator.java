package dsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class KeyGenerator {

    private BigInteger p, g, q, b, a;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private int p_length;

    private static final int CERTAINTY = 10;
    
    
	private static boolean checkmultiple(BigInteger m, BigInteger f) {
		if(m.remainder(f).equals(BigInteger.ZERO))
			return false;
		return true;
	}
    
	public static void main(String[] args) {
		
		int multi64 = getRandomNInRange(512, 1024);
		while(checkmultiple(BigInteger.valueOf(multi64), BigInteger.valueOf(64)))
		{
			multi64 = getRandomNInRange(512, 1024);
		}
		 KeyGenerator keygen = new KeyGenerator(multi64);
		 keygen.generate();
		 PublicKey publicKey = keygen.getPublicKey();
		 PrivateKey privateKey = keygen.getPrivateKey();
 
		System.out.println("getP is " + privateKey.getP());
		System.out.println("getQ is " + privateKey.getQ());
	}
    
   
	private static int getRandomNInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return new SecureRandom().nextInt((max - min) + 1) + min;
	}

    public KeyGenerator(int p_length) {
        this.p_length = p_length;
    }

    public void generate() {
        q = generateQ();
        p = generateP();
        g = generateG();
        a = generateA();
        b = generateB();

        publicKey = new PublicKey(p, g, q, b);
        privateKey = new PrivateKey(a, p, q, g);
    }

    public BigInteger generateQ() {
        BigInteger result;
        do {
            result = BigInteger.probablePrime(160, new SecureRandom()); 
        } while (result.bitLength() != 160);
        return result;
    }

    public BigInteger generateP() {
        BigInteger tempP;
        BigInteger tempP2;
        do {
        	tempP = BigInteger.probablePrime(p_length, new SecureRandom()); 
            tempP2 = tempP.subtract(BigInteger.ONE);
            tempP = tempP.subtract(tempP2.remainder(q));
        } while(!tempP.isProbablePrime(CERTAINTY) || tempP.bitLength() != p_length);
        return tempP;
    }


    public  BigInteger generateG() {
        BigInteger h = generateH();
        return h.modPow(p.subtract(BigInteger.ONE).divide(q), p);
    }

    public BigInteger generateA() {
        Random rng = new Random();
        BigInteger result;
        int range = 1 + rng.nextInt(q.bitLength() - 2);
        do {
            result = new BigInteger(range, rng);
        } while (result.equals(BigInteger.ZERO));

        return result;
    }

    public BigInteger generateH() {
        SecureRandom rng = new SecureRandom();
        BigInteger result;
        do {
            result = new BigInteger(p.bitLength() - 1, CERTAINTY, rng);
        } while (result.modPow(p.subtract(BigInteger.ONE).divide(q), p).compareTo(BigInteger.ONE) != 1);
        return result;
    }

    public BigInteger generateB() {
        return g.modPow(a, p);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getB() {
        return b;
    }

    public BigInteger getA() {
        return a;
    }
}
