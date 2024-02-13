package src;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    /**
     * Reentrant lock
     *
     * - it has the same behavior as the "synchronized approach"
     * - of course it has some additional features
     *
     * new ReentrantLock(boolean fairness)
     *      If the fairness parameter is set to be TRUE then the longest waiting thread will get the lock
     *          // if fairness is FALSE then there is no access order
     *
     *        IMPORTANT : a good approach is to use try-catch-finally blocks when doing the critical section and call unlock() in the finally block
     *
     */

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    private static void increment() {
        //if we add lock() and unlock() method, there are no inconsistencies no matter how many times we run the application.
        lock.lock();
        try {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void add() {
        lock.unlock();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("counter = " + counter);

    }
}
