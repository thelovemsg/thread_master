package src.stream;

import java.util.stream.IntStream;

public class Parallelization2 {
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();

        // sequential streams
        long count = IntStream.rangeClosed(2, Integer.MAX_VALUE / 100).filter(Parallelization2::isPrime).count();

        //parallel stream
        long count1 = IntStream.rangeClosed(2, Integer.MAX_VALUE / 100).parallel().filter(Parallelization2::isPrime).count();


    }

    public static boolean isPrime(long num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num%2==0) return false;

        //we  can check the numbers in the range [N, sqrt(N)] N

        long maxDivisor = (long) Math.sqrt(num);
        for (int i = 3; i <= maxDivisor; i+=2) {
            if(num%i==0) {
                return false;
            }
        }

        return true;

    }
}
