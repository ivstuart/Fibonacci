package maths;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

final class FibRecursiveTask extends RecursiveTask<BigInteger> {

	public final static BigInteger TWO = BigInteger.valueOf(2);

	private final static Map<BigInteger, BigInteger> cache = new HashMap<>();
	static {
		cache.put(BigInteger.ONE,  BigInteger.ONE);
		cache.put(BigInteger.ZERO, BigInteger.ZERO);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final BigInteger n;

	FibRecursiveTask(BigInteger n) {
		this.n = n;
	}

	protected BigInteger compute() {

		if (cache.containsKey(n)) return cache.get(n);

	    if (n.compareTo(TWO) < 1)
	    {
	        return BigInteger.ONE;
	    }

	    BigInteger k = n.shiftRight(1); // n.divide(TWO);
		BigInteger kplusone = k.add(BigInteger.ONE);
	    FibRecursiveTask f1 = new FibRecursiveTask(kplusone);

	    f1.fork();
	    FibRecursiveTask f2 = new FibRecursiveTask(k);	    
	    BigInteger b = f2.compute();
	    BigInteger a = f1.join();

		cache.put(k, b);
		cache.put(kplusone, a);

	    // is n odd
	    if (n.testBit(0))
	    {
	        // return a*a + b*b;
	        return a.multiply(a).add(b.multiply(b));
	    }
	    // n is even
	    // return b*(2*a - b);
	    return b.multiply(a.shiftLeft(1).subtract(b));
	}


	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Fibonacci m = new Fibonacci();
		// 1,000,000,000) Challenge in ... 38 minutes.

		BigInteger number = new BigInteger("1000000000"); // 0000000");

		long time = System.currentTimeMillis();

		int cores = Runtime.getRuntime().availableProcessors();

		System.out.println("Number of cores:"+cores);

		FibRecursiveTask fibRecursive = new FibRecursiveTask(number);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(fibRecursive);

		printResult(fibRecursive.get());

		time = System.currentTimeMillis() - time;
		System.out.println("Mill time = "+time);

	}

	/**
	 *  Number of bits:  694241913
	 *  First 10 bytes:  01 62 80 b8 2d 8c be 0e dc 1b
	 *  Last 10 bytes :  a9 53 2d f4 d2 d2 5b 5d b6 3b
	 *  Mill time = 610884
	 * @param num
     */
	private static void printResult(BigInteger num){
		System.out.printf("  Number of bits:  %d%n", num.bitLength());
		byte[] numHex = num.toByteArray();
		System.out.print("  First 10 bytes: ");
		for (int i = 0; i < (numHex.length < 10 ? numHex.length : 10); i++) {
			System.out.printf(" %02x", numHex[i]);
		}
		System.out.println();
		if (numHex.length > 20){
			System.out.print("  Last 10 bytes : ");
			for (int i = numHex.length - 10; i < numHex.length; i++) {
				System.out.printf(" %02x", numHex[i]);
			}
			System.out.println();
		}
	}
}