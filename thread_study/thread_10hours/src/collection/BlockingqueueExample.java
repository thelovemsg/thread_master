package src.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class FirstWorker implements Runnable {
    private BlockingQueue<Integer> queue;
    public FirstWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int counter = 0;
        while(true) {
            try {
                queue.put(counter);
                System.out.println(STR."Putting item into the queue ... \{counter}");
                counter++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SecondWorker implements Runnable {
    private BlockingQueue<Integer> queue;
    public SecondWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Integer counter = queue.take();
                System.out.println(STR."Taking item into the queue ... \{counter}");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BlockingqueueExample {
    /**
     * Queue has a FIFO structure (first in first out) but it is not a synchronized data stucture!!!
     *
     * BLockingQueue → an interface that represents a queue that is thread safe.
     *
     * Put items or take items from it..
     *
     * For example : one thread putting items into the queue and another thread taking items from it at the same time !!!
     *
     * We can do the producer-consumer pattern !!!
     *
     * put() putting items to the queue
     *
     * take() taking items from the queue
     */

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        FirstWorker firstWorker = new FirstWorker(queue);
        SecondWorker secondWorker = new SecondWorker(queue);

        new Thread(firstWorker).start();
        new Thread(secondWorker).start();


    }
}
