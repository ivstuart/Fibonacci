package maths;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Fibonacci {

	public static BigInteger TWO = BigInteger.valueOf(2);

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		
		// 1,000,000,000) Challenge in ... 38 minutes.

		BigInteger number = new BigInteger("1000000"); // new BigInteger("1000000000");

		long time = 0;
		
        // Fibonacci m = new Fibonacci();
		// time = System.currentTimeMillis();
		// System.out.println("Fib " + number + ":" +
		// m.fastDoubleFibonacci(number).bitLength());
		// time = System.currentTimeMillis() - time;
		// System.out.println("Mill time = "+time);

		// time = System.currentTimeMillis();
		// System.out.println("Fib " + number + ":" +
		// m.fastFibonacciDoubling(number.longValue()).bitLength());
		// time = System.currentTimeMillis() - time;
		// System.out.println("Mill time = "+time);

		// time = System.currentTimeMillis();
		// System.out.println("Fib " + number + ":"
		// + m.fibMatrix(number).bitLength());
		// time = System.currentTimeMillis() - time;
		// System.out.println("Mill time = " + time);

		time = System.currentTimeMillis();
		// Fastest method
		FibRecursiveTask fibRecursive = new FibRecursiveTask(number);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(fibRecursive);

		System.out.println("Fib " + number + ":"
				+ fibRecursive.get().bitLength());
		time = System.currentTimeMillis() - time;
		System.out.println("Time s = " + time / 1000.0);

	}

	BigInteger fibMatrix(BigInteger n) {
		BigInteger F[][] = { { BigInteger.ONE, BigInteger.ONE },
				{ BigInteger.ONE, BigInteger.ZERO } };
		if (n.compareTo(BigInteger.ZERO) == 0)
			return BigInteger.ZERO;
		power(F, n.subtract(BigInteger.ONE));
		return F[0][0];
	}

	void power(BigInteger F[][], BigInteger n) {
		if (n.compareTo(BigInteger.ZERO) == 0
				|| n.compareTo(BigInteger.ONE) == 0)
			return;

		BigInteger M[][] = { { BigInteger.ONE, BigInteger.ONE },
				{ BigInteger.ONE, BigInteger.ZERO } };

		final BigInteger TWO = BigInteger.valueOf(2);
		power(F, n.divide(TWO));
		multiply2(F, F);

		if (n.mod(TWO).compareTo(BigInteger.ZERO) != 0)
			multiply2(F, M);

	}

	void multiply(BigInteger F[][], BigInteger M[][]) {

		BigInteger a = multiply(F[0][0], M[0][0]).add(
				multiply(F[0][1], M[1][0]));
		BigInteger b = multiply(F[0][0], M[0][1]).add(
				KaratsubaMultiplication.multiply(F[0][1], M[1][1]));
		BigInteger c = multiply(F[1][0], M[0][0]).add(
				KaratsubaMultiplication.multiply(F[1][1], M[1][0]));
		BigInteger d = multiply(F[1][0], M[0][1]).add(
				multiply(F[1][1], M[1][1]));

		F[0][0] = a;
		F[0][1] = b;
		F[1][0] = c;
		F[1][1] = d;
	}

	void multiply2(BigInteger F[][], BigInteger M[][]) {
		BigInteger[][] R = { { BigInteger.ZERO, BigInteger.ZERO },
				{ BigInteger.ZERO, BigInteger.ZERO } };
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new Multiplier(F, M, R, 0));

		F[0][0] = R[0][0];
		F[0][1] = R[0][1];
		F[1][0] = R[1][0];
		F[1][1] = R[1][1];
	}

	public static int fibUsingRecursion(int n) {
		if (n < 2) {
			return n;
		} else {
			return fibUsingRecursion(n - 1) + fibUsingRecursion(n - 2);
		}
	}

	public static int fibUsingForLoop(int n) {
		int prev1 = 0, prev2 = 1;
		for (int i = 0; i < n; i++) {
			int savePrev1 = prev1;
			prev1 = prev2;
			prev2 = savePrev1 + prev2;
		}
		return prev1;
	}

	/*
	 * Fast doubling method. Faster than the matrix method. F(2n) = F(n) *
	 * (2*F(n+1) - F(n)). F(2n+1) = F(n+1)^2 + F(n)^2. This implementation is
	 * the non-recursive version.
	 */
	@SuppressWarnings("unused")
	private BigInteger fastFibonacciDoubling(long n) {
		BigInteger a = BigInteger.ZERO;
		BigInteger b = BigInteger.ONE;

		for (int i = 63 - Long.numberOfLeadingZeros(n); i >= 0; i--) {
			// Loop invariant: a = F(m), b = F(m+1)

			// Double it
			BigInteger d = multiply(a, b.shiftLeft(1).subtract(a));
			BigInteger e = multiply(a, a).add(multiply(b, b));
			a = d;
			b = e;

			// Advance by one conditionally
			// The unsigned right shift operator ">>>" shifts a zero into the
			// leftmost position, while the leftmost position after ">>" depends
			// on sign extension.
			if (((n >>> i) & 1) != 0) {
				BigInteger c = a.add(b);
				a = b;
				b = c;

			}
		}
		return a;
	}
	
	@SuppressWarnings("unused")
	private BigInteger fastDoubleFibonacci(BigInteger n) {
		if (n.compareTo(TWO) < 1) {
			return BigInteger.ONE;
		}

		BigInteger k = n.shiftRight(1); // n.divide(TWO);
		BigInteger a = fastDoubleFibonacci(k.add(BigInteger.ONE));
		BigInteger b = fastDoubleFibonacci(k);
		if (n.testBit(0))
		// if (n.mod(TWO).compareTo(BigInteger.ONE) == 0)
		{
			// return a*a + b*b;
			return a.multiply(a).add(b.multiply(b));
		}
		// return b*(2*a - b);
		return b.multiply(a.shiftLeft(1).subtract(b));
	}

	// Multiplies two BigIntegers. This function makes it easy to swap in a
	// faster algorithm like Karatsuba multiplication.
	private static BigInteger multiply(BigInteger x, BigInteger y) {
		return x.multiply(y);
	}

	@SuppressWarnings("unused")
	private static int scientific(int n) {
		if (n == 0)
			return 0;
		if (n < 3)
			return 1;
		double root5 = Math.sqrt(5);
		double fib = 1 / root5
				* (Math.pow((1 + root5) / 2, n) - Math.pow((1 - root5) / 2, n));
		return (int) Math.round(fib);
	}

}
