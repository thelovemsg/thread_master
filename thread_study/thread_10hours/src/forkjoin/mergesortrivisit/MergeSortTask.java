package src.forkjoin.mergesortrivisit;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {

    private int[] nums;

    public MergeSortTask(int[] nums) {
        this.nums = nums;
    }

    @Override
    protected void compute() {
        if (nums.length <= 10) {
            //sequential merge sort
            mergeSort(nums);
            return;
        }

        int middleIndex = nums.length / 2;

        int[] left = Arrays.copyOfRange(nums, 0, middleIndex);
        int[] right = Arrays.copyOfRange(nums, middleIndex, nums.length);

        MergeSortTask task1 = new MergeSortTask(left);
        MergeSortTask task2 = new MergeSortTask(right);

        invokeAll(task1, task2);

        merge(left, right, nums);
    }


    private void merge(int[] leftSubarray, int[] rightSubarray, int[] originalArray) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < leftSubarray.length && j < rightSubarray.length) {
            if (leftSubarray[i] < rightSubarray[j])
                originalArray[k++] = leftSubarray[i++];
            else
                originalArray[k++] = rightSubarray[j++];

            while (i < leftSubarray.length)
                originalArray[k++] = leftSubarray[i++];
            while (j < rightSubarray.length)
                originalArray[k++] = rightSubarray[j++];
        }
    }

    private void mergeSort(int[] nums) {
        if (nums.length <= 1)
            return;

        int middleIndex = nums.length / 2;

        int[] left = Arrays.copyOfRange(nums, 0, middleIndex);
        int[] right = Arrays.copyOfRange(nums, middleIndex, nums.length);

        mergeSort(left);
        mergeSort(right);

        merge(left, right, nums);

    }
}
