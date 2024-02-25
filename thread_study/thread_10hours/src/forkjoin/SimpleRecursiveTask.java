package src.forkjoin;

import java.util.concurrent.RecursiveTask;

public class SimpleRecursiveTask extends RecursiveTask<Integer> {

    private int num;

    public SimpleRecursiveTask(int num) {
        this.num = num;
    }

    @Override
    protected Integer compute() {
        if (num > 100) {
            //parallelization : we split the problem into 2 sub-problems
            System.out.println(STR."Parallel execution so split the task...\{num}");

            SimpleRecursiveTask task1 = new SimpleRecursiveTask(num / 2);
            SimpleRecursiveTask task2 = new SimpleRecursiveTask(num / 2);

            // we add the tasks to the thread pool (parallel)
            task1.fork();
            task2.fork();

            // wait for these tasks to be finished
            int subSolution = 0;
            subSolution += task1.join();
            subSolution += task2.join();

            return subSolution;
        }else {
            // the problem is too small - we can use sequential approach
            return 2*num;
        }

    }
}
