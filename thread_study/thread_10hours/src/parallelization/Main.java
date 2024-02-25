package src.parallelization;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
//        int[] nums = {5,-1,0,7,2,3,2,1,0,1,2};
//
//        MergeSort sort = new MergeSort(nums);
//        sort.sort();
//        sort.showArray();

        int numOfThreads = Runtime.getRuntime().availableProcessors();
        int[] numbers1 = createArray(10000000);
        int[] numbers2 = createArray(numbers1.length);

        for (int i = 0; i < numbers1.length; i++) {
            numbers2[i] = numbers1[i];
        }

        //PARALLEL MERGE SORT
        ParallelMergeSort parallelMergeSort = new ParallelMergeSort(numbers1);

        long startTime1 = System.currentTimeMillis();
        parallelMergeSort.parallelMergeSort(0, numbers2.length-1, numOfThreads);
        long endTime1 = System.currentTimeMillis();
        System.out.printf("Time taken with parallel: %d ms\n", endTime1-startTime1);

        //SEQUENTIAL MERGE SORT
        MergeSort mergeSort = new MergeSort(numbers2);

        long startTime2 = System.currentTimeMillis();
        mergeSort.sort(0, numbers2.length-1);
        long endTime2 = System.currentTimeMillis();
        System.out.printf("Time taken with sequential: %d ms\n", endTime2-startTime2);

    }

    private static int[] createArray(int n) {

        Random random = new Random();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt(n);
        }
        return a;
    }
}
