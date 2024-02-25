package src.stream;

import java.util.stream.LongStream;

public class Parallelization1 {
    public static void main(String[] args) {

        // parallel() - because we have to make sure that the given stream can be parallelized
        // under the hood the fork-join framework is used
        long start = System.currentTimeMillis();
        System.out.println(sum(100000000));
        System.out.println(STR."Time taken sequential: \{System.currentTimeMillis() - start}");

        start = System.currentTimeMillis();
        System.out.println(parallelSum(100000000));
        System.out.println(STR."Time taken parallel: \{System.currentTimeMillis() - start}");
    }

    private static long sum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    private static long parallelSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }
}
