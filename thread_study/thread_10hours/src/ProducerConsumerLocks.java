package src;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker_ProducerConsumerLock {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        System.out.println("Producer method...");
        // quite similar with wait method of Object.
        condition.await();
        System.out.println("Again the producer method...");
        lock.unlock();
    }

    public void consume() throws InterruptedException {
        //we want to make sure that we start with the producer()
        Thread.sleep(2000);
        lock.lock();
        System.out.println("Consumer method...");
        Thread.sleep(3000);
        //notify()
        condition.signal();
        lock.unlock();
    }
}

public class ProducerConsumerLocks {

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
        Worker_ProducerConsumerLock worker = new Worker_ProducerConsumerLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
