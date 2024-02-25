package src.forkjoin;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Integer> {

    private int n;

    public FibonacciTask(int n){
        this.n = n;
    }

    @Override
    protected Integer compute() {

        // F(0) = F(1) = 0
        if(n <= 1)
            return n;

        FibonacciTask fib1 = new FibonacciTask(n-1);
        FibonacciTask fib2 = new FibonacciTask(n-2);

//        fib1.fork();
        fib2.fork();

        // the actual thread executes the fib1
        // and we create another thread(insert it into the pool)
        // associated with fib2
        return fib1.compute()+ fib2.join();
    }
}
