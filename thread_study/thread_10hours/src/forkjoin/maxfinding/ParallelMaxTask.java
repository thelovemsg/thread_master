package src.forkjoin.maxfinding;

import java.util.concurrent.RecursiveTask;

public class ParallelMaxTask extends RecursiveTask<Long> {

    private long[] nums;
    private int lowIndex;
    private int highIndex;

    public ParallelMaxTask(long[] nums, int lowIndex, int highIndex) {
        this.nums = nums;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
    }

    @Override
    protected Long compute() {

        // if the array is small - then we use sequential approach
        if(highIndex - lowIndex < 3000) {
            return sequentialMaxFinding();
        } else {
            // we have to use parallelization
            int middleIndex = (highIndex+lowIndex)/2;

            ParallelMaxTask task1 = new ParallelMaxTask(nums, lowIndex, middleIndex);
            ParallelMaxTask task2 = new ParallelMaxTask(nums, middleIndex+1, highIndex);
            invokeAll(task1, task2);

            return Math.max(task1.join(), task2.join());
        }
    }

    private Long sequentialMaxFinding() {
        long max = nums[lowIndex];

        for (int i = lowIndex; i < highIndex; i++) {
            if(nums[i] > max) {
                max = nums[i];
            }
        }

        return max;
    }
}
