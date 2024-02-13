package src;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
In conclusion, this codes is in a blocked state because the threads are waiting for the other thread to finish execution.

 */
public class DeadlockEx {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        DeadlockEx deadlock = new DeadlockEx();

        //after Java 8 it is possible to create threads like this.
        new Thread(deadlock::worker1, "worker1").start();
        new Thread(deadlock::worker2, "worker2").start();

    }

    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 acquires the lock1 ...");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock2.lock();
        System.out.println("Worker1 acquired the lock2 ...");

        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        lock1.lock();
        System.out.println("Worker2 acquires the lock1 ...");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock2.lock();
        System.out.println("Worker2 acquired the lock2 ...");

        lock1.unlock();
        lock2.unlock();
    }

}
