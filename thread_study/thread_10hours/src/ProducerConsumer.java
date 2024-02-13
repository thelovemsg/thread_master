package src;

import java.util.ArrayList;
import java.util.List;

class Processor {

    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void producer() throws InterruptedException {
        synchronized (lock) {
            while(true) {
                if(list.size() == UPPER_LIMIT) {
                    System.out.println("Waiting fort removing items...");
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value++);
                    // we can call the notify - because the other thread will be notified
                    // only when it is in a waiting state
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while(true) {
                if(list.size() == LOWER_LIMIT) {
                    System.out.println("Waiting fort adding items...");
                    value = 0;
                    lock.wait();
                } else {
                    System.out.println("Removing: " + list.remove(list.size() - 1));
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
