package maths;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Ivan on 11/10/2016.
 */
public class FibStream {

    public static void main(String args[]) {
        Stream.generate(new Supplier<BigInteger>() {
            private BigInteger n1 = BigInteger.ONE;
            private BigInteger n2 = BigInteger.ONE;

            @Override
            public BigInteger get() {
                BigInteger fibonacci = n1;
                BigInteger n3 = n2.add(n1);
                n1 = n2;
                n2 = n3;
                return fibonacci;
            }
        }).limit(50).forEach(System.out::println);
    }
}
