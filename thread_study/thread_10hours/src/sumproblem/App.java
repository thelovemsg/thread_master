package src.sumproblem;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        Random random = new Random();

        int[] nums = new int[1000000000];
        for (int i = 0; i < 1000000000; i++) {
            nums[i] = random.nextInt(100);
        }
        int n = Runtime.getRuntime().availableProcessors();

        // sequential sum algorithm
        SequentialSum sequential = new SequentialSum();
        long start = System.currentTimeMillis();
        System.out.println(STR."Sum: \{sequential.sum(nums)}");
        long end = System.currentTimeMillis();
        System.out.println("Time : " + (end-start));

        // parallel algorithm
        ParallelSum parallel = new ParallelSum(n);
        start = System.currentTimeMillis();
        System.out.println(parallel.sum(nums));
        end = System.currentTimeMillis();
        System.out.println("Time : " + (end-start));
    }
}
