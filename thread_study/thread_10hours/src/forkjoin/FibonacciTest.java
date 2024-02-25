package src.forkjoin;

import java.util.concurrent.ForkJoinPool;

public class FibonacciTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(pool.invoke(new FibonacciTask(25)));

    }
}
