package ass1;

public class NbitsOFB {

	private final static int DELTA = 0x9e3779b9;
	private final static int DECRYPT_SUM_INIT = 0xC6EF3720;
	private final static long MASK32 = (1L << 32) - 1;
	public static long encrypt(long in , int[] k) {
	  int v1 = (int) in ;
	  int v0 = (int)( in >>> 32);
	  int sum = 0;
	  for (int i = 0; i < 32; i++) {
	   sum += DELTA;
	   v0 += ((v1 << 4) + k[0]) ^ (v1 + sum) ^ ((v1 >>> 5) + k[1]);
	   v1 += ((v0 << 4) + k[2]) ^ (v0 + sum) ^ ((v0 >>> 5) + k[3]);
	  }
	  return (v0 & MASK32) << 32 | (v1 & MASK32);
	}
	
	public NbitsOFB(long in , int[] k) {
		long out = encrypt(in , k);
	}

	public static void main(String args[]) {
		
	}
}
