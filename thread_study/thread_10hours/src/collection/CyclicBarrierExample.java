package src.collection;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CyclicBarrierWorker implements Runnable {

    private int id;
    private Random random;
    private CyclicBarrier barrier;

    public CyclicBarrierWorker(int id, CyclicBarrier barrier) {
        this.id = id;
        this.random = new Random();
        this.barrier = barrier;
    }

    @Override
    public void run() {
        doWork();
    }

    private void doWork() {
        System.out.println("Thread with ID " + this.id + " starts the work...");
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // When all the threads call await(), this is when the "barrier is broken"
            barrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        // After all the threads have been finished, it is going to continue with the execution with the given method.
        System.out.println("After the await()...");
    }
}

public class CyclicBarrierExample {
    /**
     * *Latch -> a single thread can wait for other threads*
     *
     * *CyclicBarrier -> multiple threads can wait for each otherA CyclicBarrier is used in situations where you want to create a group of
     * tasks to perform work in a concurrent manner + wait until they are all finished before moving on to the next step
     *
     * → Something like join()
     * → Something like CountDownLatch

     * CountDownLatch: one-shot event
     * CyclicBarrier: it can be reused over and over again
     *
     *  + cyclicBarrier has a barrier action: a runnable, that will run automatically when the count reaches 0 !!
     *
     * new CyclicBarrier(N) → N threads will wait for each other
     *
     * WE CAN NOT REUSE LATCHES BET WE CAN REUSE CyclicBarriers —> reset() !!!!
     */

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        //5 is the number of parties (number of threads)
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("All tasks have benn finished ...");
            }
        });

        for (int i = 0; i < 5; i++) {
            service.execute(new CyclicBarrierWorker(i+1, barrier));
        }

        service.shutdown();
    }
}
