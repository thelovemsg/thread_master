package src.forkjoin.mergesortrivisit;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class App {
    public static void main(String[] args) {
        int[] nums1 = createNumbers(100000000);
        int[] nums2 = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            nums2[i] = nums1[i];
        }

        SequentialMergeSort sequential = new SequentialMergeSort(nums1);
        long start = System.currentTimeMillis();
        sequential.mergeSort(nums1);
        System.out.println("Time taken : " + (System.currentTimeMillis() - start) + "ms");

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        MergeSortTask parallel = new MergeSortTask(nums2);
        System.out.println("+++PARALLEL SORT+++");
        start = System.currentTimeMillis();
        pool.invoke(parallel);
        System.out.println("Time taken : " + (System.currentTimeMillis() - start) + "ms");
    }

    private static int[] createNumbers(int n) {
        Random random = new Random();
        int[] nums = new int[n];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(10000);
        }
        return nums;
    }
}
