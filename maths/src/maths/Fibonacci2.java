package maths;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * By Alexey Solodovnikov
 */
public class Fibonacci2 {

    private static Map<BigInteger, BigInteger> cache = new HashMap<BigInteger, BigInteger>();
    private static BigInteger MINUS_ONE = BigInteger.ONE.negate();

    static {
        cache.put(BigInteger.ONE,  BigInteger.ONE);
        cache.put(BigInteger.ZERO, BigInteger.ZERO);
    }

    public static BigInteger fibonacci(BigInteger n) {

        if (cache.containsKey(n)) return cache.get(n);

        if (n.testBit(0)) {
            BigInteger n2 = n.shiftRight(1);
            BigInteger n3 = n2.add(BigInteger.ONE);
            BigInteger result = fibonacci(n2).multiply(fibonacci(n2)).add(fibonacci(n3).multiply(fibonacci(n3)));
            cache.put(n, result);
            return result;
        }
        else {
            BigInteger n2 = n.shiftRight(1);
            BigInteger n3 = n2.add(MINUS_ONE);
            BigInteger result = fibonacci(n2).multiply(fibonacci(n2).add(fibonacci(n3).add(fibonacci(n3))));
            cache.put(n, result);
            return result;
        }
    }

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

    /**
     * Number of cores:8
     Number of bits:  694241913
     First 10 bytes:  01 62 80 b8 2d 8c be 0e dc 1b
     Last 10 bytes :  a9 53 2d f4 d2 d2 5b 5d b6 3b
     Mill time = 610884

     Number of bits:  694241913
     First 10 bytes:  01 62 80 b8 2d 8c be 0e dc 1b
     Last 10 bytes :  a9 53 2d f4 d2 d2 5b 5d b6 3b
     Time to calculate 691981 ms
     * @param args
     */
    public static void main(String[] args) {
        long time = System.currentTimeMillis();

        BigInteger number = new BigInteger("1000000000");// 0000000");

        BigInteger res = fibonacci(number);
        printResult(res);
        System.out.printf("  Time to calculate %d ms%n%n", System.currentTimeMillis() - time);

    }
}
