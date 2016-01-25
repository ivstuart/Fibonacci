package maths;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

class FibRecursiveTask extends RecursiveTask<BigInteger> {

	public static BigInteger TWO = BigInteger.valueOf(2);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final BigInteger n;

	FibRecursiveTask(BigInteger n) {
		this.n = n;
	}

	protected BigInteger compute() {
		
	    if (n.compareTo(TWO) < 1)
	    {
	        return BigInteger.ONE;
	    }

	    BigInteger k = n.shiftRight(1); // n.divide(TWO);
	    FibRecursiveTask f1 = new FibRecursiveTask(k.add(BigInteger.ONE));
	    f1.fork();
	    FibRecursiveTask f2 = new FibRecursiveTask(k);	    
	    BigInteger b = f2.compute();
	    BigInteger a = f1.join();
	    // is n odd
	    if (n.testBit(0))
	    {
	        // return a*a + b*b;
	    	// return KaratsubaMultiplication.multiply(a, a).add(KaratsubaMultiplication.multiply(b, b));
	        return a.multiply(a).add(b.multiply(b));
	    }
	    // return b*(2*a - b);
	    // return KaratsubaMultiplication.multiply(b,a.shiftLeft(1).subtract(b));
	    return b.multiply(a.shiftLeft(1).subtract(b));
	}
	
}